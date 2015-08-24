/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morgan.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author Morgan
 * @param <T>
 */
public abstract class DbField<T> {
    private final String label;
    private final T fillValue;
    
    public DbField(String label, T fillValue) {
        this.label = label;
        this.fillValue = fillValue;
    }
    public DbField(String label) { this(label, null); }
    
    public String getLabel() {  return label; }
    /*@Override
    public Class<T> getType() { return type; }*/
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
        private final T[] values;
        private final String sqlPrepend;
        private int placeHolder;
        private int lastBatch;

        public Values(DbField<T> field, T[] values, String sqlPrepend) {
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
                builder.append(pairings[i].sqlPrepend).append(" ").append(pairings[i].field.label).append(batches[i].toString());
            }
            PreparedStatement statement = connection.prepareStatement(builder.toString());
            
            for (int i = 0; i < pairings.length; i++) {
                paramIndex += pairings[i].setStatementParams(statement, batches[i], paramIndex);
            }
            
            return statement;
        }
        
        private BatchCriterion getBatch() {
            int targetSize = values.length - placeHolder;

            return new BatchCriterion(( targetSize < 0 ? 0 : targetSize ));
        }
        private int setStatementParams(PreparedStatement statement, BatchCriterion batch, int paramStart) throws SQLException {
            for (int i = 0; i < batch.size; i++) {
                field.setParam(statement, i + paramStart, ( (i + placeHolder) >= values.length ? field.fillValue : values[i + placeHolder] ));
            }
            lastBatch = batch.size;
            return lastBatch;
        }
        public boolean isComplete() { return placeHolder >= values.length; }
        public void increment() { placeHolder += lastBatch; }
        public void reset() { placeHolder = 0; }
    }
    
    private static class BatchCriterion {
        public final int size;
        
        private static final int SINGLE_BATCH = 1;
        private static final int SMALL_BATCH = 17;
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
            StringBuilder builder = new StringBuilder(" In ( ");
            
            for (int i = 0; i < size; i++) {
                if (i > 0) { builder.append(", "); }
                builder.append("?");
            }
            return builder.append(" )").toString();
        }
    }
    
    public static final class StringField extends DbField<String> {
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
}
