/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.data.database;

/**
 *
 * @author Morgan
 */
import morgan.database.DbField;
import scDomain.domain.objects.*;
import scDomain.domain.dao.AgentDao;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

final class AgentDbDao extends DomainDbDao<Agent, Agent.Key> implements AgentDao {
    static final String TABLE = "scweb_sc_agents";
    private static final String STRING_FILL = "DUMMY";
    
    static final DomainField.AgentField USERNAME = new DomainField.AgentField("sa_username", new Agent.Key(STRING_FILL));
    static final DbField.StringField FIRSTNAME = new DbField.StringField("sa_firstname", STRING_FILL);
    static final DbField.StringField LASTNAME = new DbField.StringField("sa_lastname", STRING_FILL);
    static final DbField.StringField EMAIL = new DbField.StringField("sa_email", STRING_FILL);
    static final DomainField.AgentField TEAMLEAD = new DomainField.AgentField("sa_lead", new Agent.Key(STRING_FILL));
    static final DomainField.AgentField SUPERVISOR = new DomainField.AgentField("sa_supervisor", new Agent.Key(STRING_FILL));
    static final DomainField.DeptField DEPARTMENT = new DomainField.DeptField("sa_department", new Department.Key(STRING_FILL));
    static final DomainField.RoleField ROLE = new DomainField.RoleField("sa_role", new Role.Key(STRING_FILL));
    
    private static final String SELECT_START = "SELECT * FROM " + TABLE;
    
    AgentDbDao(Connection connection) { super(connection); }
    
    @Override
    Agent load(ResultSet rs) throws SQLException {
        Agent agent = new Agent.Builder().
                username(USERNAME.getValue(rs).getID()).
                firstName(FIRSTNAME.getValue(rs)).
                lastName(LASTNAME.getValue(rs)).
                email(EMAIL.getValue(rs)).
                teamLead(TEAMLEAD.getValue(rs)).
                supervisor(SUPERVISOR.getValue(rs)).
                department(DEPARTMENT.getValue(rs)).
                role(ROLE.getValue(rs)).
                getObject();
                
        return agent;
    }
    
    @Override
    public ArrayList<Agent> find(Collection<Agent.Key> keys) {
        return findMany(new DbField.Values[] { new DbField.Values<Agent.Key>(USERNAME, keys, SELECT_START + " WHERE") });
    }
    
    private ArrayList<Agent> privateFindByDeptRoleStatus(Collection<Department.Key> departments, Collection<Role.Key> roles, Boolean status) {
        DbField.Values<Department.Key> deptValues = null;
        DbField.Values<Role.Key> roleValues = null;
        StringBuilder sqlStart = new StringBuilder(SELECT_START + " WHERE");
        
        if (status != null) {
            sqlStart.append(" sa_status = ").append(( status ? "'A'" : "'I'" )).append(" AND");
        }
        //TODO can arrays be null?  Can they be empty?  Should I throw exceptions here?
        if (departments != null && !departments.isEmpty()) {
            deptValues = new DbField.Values<>(DEPARTMENT, departments, sqlStart.toString());
        }
        if (roles != null && !roles.isEmpty()) {
            roleValues = new DbField.Values<>(ROLE, roles, ( deptValues == null ? sqlStart.toString() : " AND" ));
        }
        if (deptValues == null && roleValues == null) {
            throw new IllegalArgumentException();
        }
        DbField.Values[] values;
        
        if (deptValues != null && roleValues != null) {
            values = new DbField.Values[] { deptValues, roleValues };
        } else if (deptValues != null) {
            values = new DbField.Values[] { deptValues };
        } else  {
            values = new DbField.Values[] { roleValues };
        }
        return findMany(values);
    }
    @Override
    public ArrayList<Agent> findByDepartment(Collection<Department.Key> departments) {
        return privateFindByDeptRoleStatus(departments, null, null);
    }
    @Override
    public ArrayList<Agent> findByDepartment(Collection<Department.Key> departments, boolean status) {
        return privateFindByDeptRoleStatus(departments, null, status);
    }
    
    @Override
    public ArrayList<Agent> findByDeptRole(Collection<Department.Key> departments, Collection<Role.Key> roles) {
        return privateFindByDeptRoleStatus(departments, roles, null);
    }
    @Override
    public ArrayList<Agent> findByDeptRole(Collection<Department.Key> departments, Collection<Role.Key> roles, boolean status) {
        return privateFindByDeptRoleStatus(departments, roles, status);
    }
    @Override
    public HashMap<Department.Key, Integer> getDeptLevels(Agent.Key key) {
        HashMap<Department.Key, Integer> deptLevels = new HashMap<>();
        
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT vdr_department, MAX(vdr_level) max_level FROM scweb_vw_dept_roles WHERE vdr_username = ?;"
        );) {
            statement.setString(1, key.getID());
            
            try (ResultSet rs = statement.executeQuery();) {
                while (rs.next()) {
                    deptLevels.put(new Department.Key(rs.getString("vdr_department")), rs.getInt("max_level"));
                }
            } catch (SQLException e) {
                //something.
            }
        } catch (SQLException e) {
            //something
        }
        return deptLevels;
    }
    
    //All this TODO!!!
    @Override
    public Agent add(Agent object) {
        //pool stuff here.
        return null;
    }
    @Override
    public Agent add(Agent.Builder builder) {
        //pool stuff here.
        return null;
    }
    @Override
    public Agent update(Agent object) {
        //pool stuff here.
        return null;
    }
    @Override
    public Agent update(Agent.Key key, Agent.Builder builder) {
        //pool stuff here.
        return null;
    }
    @Override
    public void delete(Agent.Key key) {
        //pool stuff here.
    }
    @Override
    public void delete(Agent object) {
    }
}