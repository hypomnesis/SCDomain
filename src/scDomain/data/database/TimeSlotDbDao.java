/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.data.database;

import java.sql.*;
import java.util.*;
import morgan.database.DbField;
import scDomain.domain.dao.TimeSlotDao;
import scDomain.domain.objects.*;

/**
 *
 * @author Morgan
 */
public class TimeSlotDbDao extends DomainDbDao<TimeSlot, TimeSlot.Key> implements TimeSlotDao {
    static final String TABLE = "scweb_ts_slots";
    private static final String STRING_FILL = "DUMMY";
    
    static final DomainField.TimeSlotField ID = new DomainField.TimeSlotField("ts_slot");
    static final DomainField.TimeSlotField.CategoryField CATEGORY = new DomainField.TimeSlotField.CategoryField("ts_category");
    static final DomainField.TimeSlotField.TypeField TYPE = new DomainField.TimeSlotField.TypeField("ts_type");
    static final DomainField.SplitScheduleDateField START = new DomainField.SplitScheduleDateField("ts_date", "ts_begin");
    static final DomainField.SplitScheduleDateField END = new DomainField.SplitScheduleDateField("ts_date", "ts_end");
    //STATUS (status is a type)  NEED BID TOO
    static final DomainField.DomainDateField CREATED = new DomainField.DomainDateField("ts_created");
    static final DomainField.AgentField CREATED_BY = new DomainField.AgentField("ts_created_by");
    static final DomainField.DomainDateField ASSIGNED = new DomainField.DomainDateField("ts_assigned");
    static final DomainField.AgentField ASSIGNED_BY = new DomainField.AgentField("ts_assigned_by");
    static final DomainField.YesNoField CONFIRMED = new DomainField.YesNoField("ts_confirmed");
    static final DomainField.DomainDateField CONFIRMATION_TIME = new DomainField.DomainDateField("wtf is this field called");
    static final DomainField.DomainDateField LAST_EMAIL = new DomainField.DomainDateField("ts_last_email");
    static final DomainField.DomainDateField LAST_CONF_EMAIL = new DomainField.DomainDateField("ts_last_conf_email");
    static final DomainField.YesNoField EXACT_MATCH = new DomainField.YesNoField("name/??");
    static final DomainField.YesNoField IS_FRAGMENT = new DomainField.YesNoField("ts_is_fragment?");
    
    private static final String SELECT_START = "SELECT * FROM " + TABLE;
    
    TimeSlotDbDao(Connection connection) { super(connection); }
    
    @Override
    TimeSlot load(ResultSet rs) throws SQLException {
        TimeSlot slot = new TimeSlot.Builder().
                id(ID.getValue(rs).getID()).
                category(CATEGORY.getValue(rs)).
                type(TYPE.getValue(rs)).
                start(START.getValue(rs)).
                end(END.getValue(rs)).
                creationTime(CREATED.getValue(rs)).
                createdBy(CREATED_BY.getValue(rs)).
                assignTime(ASSIGNED.getValue(rs)).
                assignedBy(ASSIGNED_BY.getValue(rs)).
                confirmed(CONFIRMED.getValue(rs)).
                confirmTime(CONFIRMATION_TIME.getValue(rs)).
                lastEmail(LAST_EMAIL.getValue(rs)).
                lastConfirmEmail(LAST_CONF_EMAIL.getValue(rs)).
                requireExactMatch(EXACT_MATCH.getValue(rs)).
                isFragment(IS_FRAGMENT.getValue(rs)).
                getObject();
                
        return slot;
    }
    
    @Override
    public ArrayList<TimeSlot> find(Collection<TimeSlot.Key> keys) {
        return findMany(new DbField.Values[] { new DbField.Values<TimeSlot.Key>(ID, keys, SELECT_START + " WHERE") });
    }
    
    @Override
    public ArrayList<TimeSlot> findByAgent(Agent.Key agent) {
        return null;
    }
    @Override
    public ArrayList<TimeSlot> findByDepartment(Department.Key department) {
        return null;
    }
    
    //All this TODO!!!
    @Override
    public TimeSlot add(TimeSlot object) {
        //pool stuff here.
        return null;
    }
    @Override
    public TimeSlot add(TimeSlot.Builder builder) {
        //pool stuff here.
        return null;
    }
    @Override
    public TimeSlot update(TimeSlot object) {
        //pool stuff here.
        return null;
    }
    @Override
    public TimeSlot update(TimeSlot.Key key, TimeSlot.Builder builder) {
        //pool stuff here.
        return null;
    }
    @Override
    public void delete(TimeSlot.Key key) {
        //pool stuff here.
    }
    @Override
    public void delete(TimeSlot object) {
    }
    public static class CategoryDbDao extends DomainDbDao.FindAll<TimeSlot.Category, TimeSlot.Category.Key> implements TimeSlotDao.CategoryDao {
        static final String TABLE = "scweb_ts_categories";
        private static final String STRING_FILL = "DUMMY";

        static final DomainField.TimeSlotField.CategoryField ID = new DomainField.TimeSlotField.CategoryField("tc_category");
        static final DbField.StringField DESCRIPTION = new DbField.StringField("tc_description", STRING_FILL);
        //Schedule Type Field
        static final DbField.IntegerField DAY_RANGE = new DbField.IntegerField("tc_day_range", -1);
        //Need rank key etc. OMG
        static final DbField.IntegerField RANK_TYPE = new DbField.IntegerField("tc_rank_type", -1);
        static final DomainField.YesNoField MANDATORY = new DomainField.YesNoField("tc_mandatory");
        static final DbField.IntegerField MAND_RANK_TYPE = new DbField.IntegerField("name???", -1);
        static final DomainField.YesNoField REQ_CONFIRM = new DomainField.YesNoField("name???");
        static final DomainField.YesNoField CAN_FRAGMENT = new DomainField.YesNoField("name???");
        static final DbField.IntegerField ALLOWED_GAP = new DbField.IntegerField("tc_allowed_gap", -1);
        static final DbField.FloatField WORKTIME_VALUE = new DbField.FloatField("name", -1F);
        static final DbField.FloatField OVERTIME_VALUE = new DbField.FloatField("name", -1F);
        
        private static final String SELECT_START = "SELECT * FROM " + TABLE;

        CategoryDbDao(Connection connection) { super(connection); }

        @Override
        TimeSlot.Category load(ResultSet rs) throws SQLException {
            return new TimeSlot.Category.Builder().
                    id(ID.getValue(rs).getID()).
                    description(DESCRIPTION.getValue(rs)).
                    dayRange(DAY_RANGE.getValue(rs)).
                    rankType(RANK_TYPE.getValue(rs)).
                    isMandatory(MANDATORY.getValue(rs)).
                    mandatoryRank(MAND_RANK_TYPE.getValue(rs)).
                    requiresConfirmation(REQ_CONFIRM.getValue(rs)).
                    canFragment(CAN_FRAGMENT.getValue(rs)).
                    allowedGap(ALLOWED_GAP.getValue(rs)).
                    worktimeValue(WORKTIME_VALUE.getValue(rs)).
                    overtimeValue(OVERTIME_VALUE.getValue(rs)).
                    getObject();
        }

        @Override
        public ArrayList<TimeSlot.Category> find(Collection<TimeSlot.Category.Key> keys) {
            return findMany(new DbField.Values[] { new DbField.Values<TimeSlot.Category.Key>(ID, keys, SELECT_START + " WHERE") });
        }
        @Override
        PreparedStatement getFindAllStatement() throws SQLException {
            return connection.prepareStatement(SELECT_START);
        }
    }
    public static class TypeDbDao extends DomainDbDao.FindAll<TimeSlot.Type, TimeSlot.Type.Key> implements TimeSlotDao.TypeDao {
        static final String TABLE = "scweb_ts_categories";
        private static final String STRING_FILL = "DUMMY";

        static final DomainField.TimeSlotField.TypeField ID = new DomainField.TimeSlotField.TypeField("tt_type");
        static final DomainField.TimeSlotField.CategoryField CATEGORY = new DomainField.TimeSlotField.CategoryField("tt_category");
        static final DbField.StringField DESCRIPTION = new DbField.StringField("tt_description", STRING_FILL);

        private static final String SELECT_START = "SELECT * FROM " + TABLE;

        TypeDbDao(Connection connection) { super(connection); }

        @Override
        TimeSlot.Type load(ResultSet rs) throws SQLException {
            return new TimeSlot.Type.Builder().
                    id(ID.getValue(rs).getID()).
                    description(DESCRIPTION.getValue(rs)).
                    category(CATEGORY.getValue(rs)).
                    getObject();
        }

        @Override
        public ArrayList<TimeSlot.Type> find(Collection<TimeSlot.Type.Key> keys) {
            return findMany(new DbField.Values[] { new DbField.Values<TimeSlot.Type.Key>(ID, keys, SELECT_START + " WHERE") });
        }
        @Override
        PreparedStatement getFindAllStatement() throws SQLException {
            return connection.prepareStatement(SELECT_START);
        }
    }
}