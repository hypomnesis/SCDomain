/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morgan.database;

import java.sql.*;
import java.util.*;

/**
 *
 * @author Morgan
 * @param <T>
 */
public abstract class DbField<T> {
    private final T fillValue;
    
    public DbField() { fillValue = null; }
    public DbField(T fillValue) {
        this.fillValue = fillValue;
    }
    /*@Override
    public Class<T> getType() { return type; }*/
    public abstract String getLabel();
    public abstract void setParam(PreparedStatement statement, int index, T value) throws SQLException;
    public abstract T getValue(ResultSet rs) throws SQLException;
    
    public static class StatementArray implements AutoCloseable, Iterable<PreparedStatement> {
        private final ArrayList<PreparedStatement> statements;
        
        public StatementArray(PreparedStatement[] statements) {
            if (statements == null) {
                this.statements = new ArrayList<>();
            } else {
                this.statements = new ArrayList<>(Arrays.asList(statements));
            }
        }
        @Override
        public void close() throws SQLException {
            for (PreparedStatement statement : statements) {
                if (statement != null) { statement.close(); }
            }
        }
        @Override
        public Iterator<PreparedStatement> iterator() {
            return statements.iterator();
        }
    }
    
    public static class Values<T> {
        private final DbField<T> field;
        private final Collection<T> values;
        private final String sqlPrepend;
        private int placeHolder;
        private int lastBatch;

        public Values(DbField<T> field, Collection<T> values, String sqlPrepend) {
            this.field = field;
            this.values = values;
            this.sqlPrepend = sqlPrepend;
            placeHolder = 0;
            lastBatch = 0;
        }
        
        public static StatementArray getStatements(Connection connection, Values[] pairings) throws SQLException
        {
            ArrayList<PreparedStatement> statements = new ArrayList<>();
            
            addStatements(statements, connection, pairings, 0);
            
            return new StatementArray(statements.toArray(new PreparedStatement[statements.size()]));
        }
        private static void addStatements(ArrayList<PreparedStatement> statements, Connection connection, Values[] pairings, int currIndex) throws SQLException {
            Values pairing = pairings[currIndex];
            
            if (currIndex == pairings.length - 1) {
                while (!pairing.isComplete()) {
                    statements.add(doFill(connection, pairings));
                    pairing.increment();
                }
            } else {
                while(!pairings[currIndex].isComplete()) {
                    addStatements(statements, connection, pairings, currIndex + 1);
                    pairing.increment();
                }
            }
            pairing.reset();
        }
        private static PreparedStatement doFill(Connection connection, Values[] pairings) throws SQLException
        {
            BatchCriterion[] batches = new BatchCriterion[pairings.length];
            StringBuilder builder = new StringBuilder();
            int paramIndex = 1;
            
            for (int i = 0; i < pairings.length; i++) {
                batches[i] = pairings[i].getBatch();
                builder.append(pairings[i].sqlPrepend).append(" ").append(pairings[i].field.getLabel()).append(batches[i].toString());
            }
            PreparedStatement statement = connection.prepareStatement(builder.toString());
            
            for (int i = 0; i < pairings.length; i++) {
                paramIndex += pairings[i].setStatementParams(statement, batches[i], paramIndex);
            }
            
            return statement;
        }
        
        private BatchCriterion getBatch() {
            int targetSize = values.size() - placeHolder;

            return new BatchCriterion(( targetSize < 0 ? 0 : targetSize ));
        }
        private int setStatementParams(PreparedStatement statement, BatchCriterion batch, int paramStart) throws SQLException {
            int i = 0;
            
            for (T value : values) {
                field.setParam(statement, i + paramStart, value);
                i++;
            }
            for (int j = i; j < batch.size; j++) {
                field.setParam(statement, j + paramStart, field.fillValue);
            }
            lastBatch = batch.size;
            return lastBatch;
        }
        public boolean isComplete() { return placeHolder >= values.size(); }
        public void increment() { placeHolder += lastBatch; }
        public void reset() { placeHolder = 0; }
    }
    
    private static class BatchCriterion {
        public final int size;
        
        private static final int SINGLE_BATCH = 1;
        private static final int SMALL_BATCH = 13;
        private static final int MEDIUM_BATCH = 53;
        private static final int LARGE_BATCH = 137;
        
        public BatchCriterion(int targetSize) {
            if (targetSize <= 0) { throw new IllegalArgumentException(); }
            else if (targetSize == 1) { size = SINGLE_BATCH; }
            else if (targetSize <= SMALL_BATCH) { size = SMALL_BATCH; }
            else if (targetSize <= MEDIUM_BATCH) { size = MEDIUM_BATCH; }
            else { size = LARGE_BATCH; }
        }
        @Override
        public String toString() {
            if (size == 1) { return " = ?"; }
            
            StringBuilder builder = new StringBuilder(" In ( ");
            
            for (int i = 0; i < size; i++) {
                if (i > 0) { builder.append(", "); }
                builder.append("?");
            }
            return builder.append(" )").toString();
        }
    }
    
    public static abstract class SingleField<T> extends DbField<T> {
        private final String label;
        
        public SingleField(String label) { this(label, null); }
        public SingleField(String label, T fillValue) {
            super(fillValue);
            this.label = label;
        }
        
        public String getLabel() {  return label; }
    }
    
    public static final class StringField extends SingleField<String> {
        public StringField(String label) { super(label); }
        public StringField(String label, String fillValue) { super(label, fillValue); }
        @Override
        public void setParam(PreparedStatement statement, int index, String value) throws SQLException {
            statement.setString(index, value);
        }
        @Override
        public String getValue(ResultSet rs) throws SQLException {
            return rs.getString(this.getLabel());
        }
    }
    public static final class IntegerField extends SingleField<Integer> {
        public IntegerField(String label) { super(label); }
        public IntegerField(String label, Integer fillValue) { super(label, fillValue); }
        @Override
        public void setParam(PreparedStatement statement, int index, Integer value) throws SQLException {
            if (value == null) { throw new NullPointerException(); }
            statement.setInt(index, value);
        }
        @Override
        public Integer getValue(ResultSet rs) throws SQLException {
            return rs.getInt(this.getLabel());
        }
    }
    public static final class LongField extends SingleField<Long> {
        public LongField(String label) { super(label); }
        public LongField(String label, Long fillValue) { super(label, fillValue); }
        @Override
        public void setParam(PreparedStatement statement, int index, Long value) throws SQLException {
            if (value == null) { throw new NullPointerException(); }
            statement.setLong(index, value);
        }
        @Override
        public Long getValue(ResultSet rs) throws SQLException {
            return rs.getLong(this.getLabel());
        }
    }
    public static final class FloatField extends SingleField<Float> {
        public FloatField(String label) { super(label); }
        public FloatField(String label, Float fillValue) { super(label, fillValue); }
        @Override
        public void setParam(PreparedStatement statement, int index, Float value) throws SQLException {
            if (value == null) { throw new NullPointerException(); }
            statement.setFloat(index, value);
        }
        @Override
        public Float getValue(ResultSet rs) throws SQLException {
            return rs.getFloat(this.getLabel());
        }
    }
    public static final class BooleanField extends SingleField<Boolean> {
        public BooleanField(String label) { super(label); }
        public BooleanField(String label, Boolean fillValue) { super(label, fillValue); }
        @Override
        public void setParam(PreparedStatement statement, int index, Boolean value) throws SQLException {
            if (value == null) { throw new NullPointerException(); }
            statement.setBoolean(index, value);
            //When do I want to check for nulls.  may put insistNotNull in constructor.
        }
        @Override
        public Boolean getValue(ResultSet rs) throws SQLException {
            return rs.getBoolean(this.getLabel());
        }
    }
    public static final class GregCalField extends SingleField<GregorianCalendar> {
        public GregCalField(String label) { super(label); }
        public GregCalField(String label, GregorianCalendar fillValue) { super(label, fillValue); }
        @Override
        public void setParam(PreparedStatement statement, int index, GregorianCalendar value) throws SQLException {
            statement.setTimestamp(index, new Timestamp(value.getTimeInMillis()));
        }
        @Override
        public GregorianCalendar getValue(ResultSet rs) throws SQLException {
            GregorianCalendar date = new GregorianCalendar();
            date.setTimeInMillis(rs.getTime(this.getLabel()).getTime());
            return date;
        }
    }
}
