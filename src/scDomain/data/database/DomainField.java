/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.data.database;

import java.math.BigDecimal;
import morgan.database.DbField;
import java.sql.*;
import java.util.GregorianCalendar;
import scDomain.domain.objects.*;
import scDomain.domain.valueObjects.*;

/**
 *
 * @author Morgan
 */
abstract class DomainField<K extends DomainObject.Key<?>> extends DbField.SingleField<K> {
    DomainField(String label) { super(label); }
    DomainField(String label, K fillValue) { super(label, fillValue); }
    
    static final class AgentField extends DomainField<Agent.Key> {
        AgentField(String label) { super(label, new Agent.Key("<<DUMMY>>")); }
        @Override
        public void setParam(PreparedStatement statement, int index, Agent.Key value) throws SQLException {
            statement.setString(index, value.getID());
        }
        @Override
        public Agent.Key getValue(ResultSet rs) throws SQLException {
            return new Agent.Key(rs.getString(this.getLabel()));
        }
    }
    static final class RoleField extends DomainField<Role.Key> {
        RoleField(String label) { super(label, new Role.Key("<<DUMMY>>")); }
        @Override
        public void setParam(PreparedStatement statement, int index, Role.Key value) throws SQLException {
            statement.setString(index, value.getID());
        }
        @Override
        public Role.Key getValue(ResultSet rs) throws SQLException {
            return new Role.Key(rs.getString(this.getLabel()));
        }
    }
    static final class DeptField extends DomainField<Department.Key> {
        DeptField(String label) { super(label, new Department.Key("<<DUMMY>>")); }
        @Override
        public void setParam(PreparedStatement statement, int index, Department.Key value) throws SQLException {
            statement.setString(index, value.getID());
        }
        @Override
        public Department.Key getValue(ResultSet rs) throws SQLException {
            return new Department.Key(rs.getString(this.getLabel()));
        }
    }
    static final class TimeSlotField extends DomainField<TimeSlot.Key> {
        TimeSlotField(String label) { super(label, new TimeSlot.Key(-1L)); }
        @Override
        public void setParam(PreparedStatement statement, int index, TimeSlot.Key value) throws SQLException {
            statement.setLong(index, value.getID());
        }
        @Override
        public TimeSlot.Key getValue(ResultSet rs) throws SQLException {
            return new TimeSlot.Key(rs.getLong(this.getLabel()));
        }
        
        static final class CategoryField extends DomainField<TimeSlot.Category.Key> {
            CategoryField(String label) { super(label, new TimeSlot.Category.Key(-1)); }
            public void setParam(PreparedStatement statement, int index, TimeSlot.Category.Key value) throws SQLException {
                statement.setInt(index, value.getID());
            }
            public TimeSlot.Category.Key getValue(ResultSet rs) throws SQLException {
                return new TimeSlot.Category.Key(rs.getInt(this.getLabel()));
            }
        }
        static final class TypeField extends DomainField<TimeSlot.Type.Key> {
            TypeField(String label) { super(label, new TimeSlot.Type.Key(-1)); }
            @Override
            public void setParam(PreparedStatement statement, int index, TimeSlot.Type.Key value) throws SQLException {
                statement.setInt(index, value.getID());
            }
            @Override
            public TimeSlot.Type.Key getValue(ResultSet rs) throws SQLException {
                return new TimeSlot.Type.Key(rs.getInt(this.getLabel()));
                //TODO:  Change DomainFields to WRAP single DbFields and use rs.wasNull() to return nulls. Ugh.
            }
        }
    }
    
    static final class ActiveInactiveField extends DbField.SingleField<Boolean> {
        ActiveInactiveField(String label) { super(label); }
        ActiveInactiveField(String label, Boolean fillValue) { super(label, fillValue); }
        @Override
        public void setParam(PreparedStatement statement, int index, Boolean value) throws SQLException {
            statement.setString(index, ( value ? "A" : "I"));
        }
        @Override
        public Boolean getValue(ResultSet rs) throws SQLException {
            return (rs.getString(this.getLabel()).equals("A"));
        }
    }
    static final class YesNoField extends DbField.SingleField<Boolean> {
        YesNoField(String label) { super(label); }
        YesNoField(String label, Boolean fillValue) { super(label, fillValue); }
        @Override
        public void setParam(PreparedStatement statement, int index, Boolean value) throws SQLException {
            statement.setString(index, ( value ? "Y" : "N"));
        }
        @Override
        public Boolean getValue(ResultSet rs) throws SQLException {
            return (rs.getString(this.getLabel()).equals("Y"));
        }
    }
    
    static final class SplitScheduleDateField extends DbField<ScheduleDate> {
        private final String dateLabel;
        private final String timeLabel;
        
        SplitScheduleDateField(String dateLabel, String timeLabel) {
            super(new ScheduleDate(new GregorianCalendar(0, 0, 0)));
            this.dateLabel = dateLabel;
            this.timeLabel = timeLabel;
        }
        @Override
        public String getLabel() { return "TODO"; }
        @Override
        public void setParam(PreparedStatement statement, int index, ScheduleDate value) throws SQLException {
            statement.setTimestamp(index, new Timestamp(value.getTimeInMillis()));
        }
        @Override
        public ScheduleDate getValue(ResultSet rs) throws SQLException {
            //There HAS to be a better way to do all of all of this.
            GregorianCalendar date = new GregorianCalendar();
            date.setTime(rs.getDate(dateLabel));
            
            BigDecimal timePart = rs.getBigDecimal(timeLabel);
            int hours = timePart.intValue();
            int minutes = timePart.subtract(new BigDecimal(hours)).intValue() * 60;
            
            return new ScheduleDate(date, new ScheduleTime(hours, minutes));
        }
        public void setDateParam(PreparedStatement statement, int index, ScheduleDate value) throws SQLException {
            //TODO:  Verify this returns the relative date.
            statement.setDate(index, new Date(value.getDateOnly().getTimeInMillis()));
        }
        public void setTimeParam(PreparedStatement statement, int index, ScheduleDate value) throws SQLException {
            //TODO Test to make sure this handles negative minutes okay.  I.e. confusion between -10.5 vs -11.5 when hour = -11
            statement.setBigDecimal(index, new BigDecimal(value.getHour() + "." + value.getMinute()));
        }
    }
    static final class DomainDateField extends DbField.SingleField<DomainDate> {
        private final DbField.GregCalField calField;
        
        DomainDateField(String label) {
                super(label);
                calField = new DbField.GregCalField(label);
        }
        DomainDateField(String label, DomainDate fillValue) {
            super(label, fillValue);
            //Might need to check for null here when translating fillValue.
            calField = new DbField.GregCalField(label, fillValue.getDatetime());
        }
        @Override
        public void setParam(PreparedStatement statement, int index, DomainDate value) throws SQLException {
            calField.setParam(statement, index, value.getDatetime());
        }
        @Override
        public DomainDate getValue(ResultSet rs) throws SQLException {
            return new DomainDate(calField.getValue(rs));
        }
    }
}