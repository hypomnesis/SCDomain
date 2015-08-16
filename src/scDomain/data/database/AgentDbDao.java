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
import scDomain.domain.objects.Agent;
import scDomain.domain.dao.AgentDao;
import java.sql.*;
import scDomain.domain.objects.Role;

final class AgentDbDao extends DomainDbDao<Agent, Agent.Key> implements AgentDao {
    static final String TABLE = "scweb_sc_agents";
    
    AgentDbDao(Connection connection) { super(connection); }
    
    @Override
    protected PreparedStatement findStatement(Agent.Key key) throws SQLException {
        PreparedStatement findStatement = connection.prepareStatement(
                "SELECT a.*, d.*, r.*\n"
                        + "FROM (" + TABLE + " a INNER JOIN scweb_sc_departments d\n"
                        + "\tON a.sa_department = d.sd_department)\n"
                        + "\tINNER JOIN scweb_sc_roles r ON a.sa_role = r.sr_role\n"
                        + "WHERE sa_username = ?"
        );
        findStatement.setString(1, key.getID());
        return findStatement;
    }

    @Override
    Agent load(ResultSet rs) throws SQLException {
        RoleDbDao roleDao = new RoleDbDao(connection);
        Role role = roleDao.find(new Role.Key(rs.getString("sa_role")));
        
        Agent agent = new Agent.Builder().
                username(rs.getString("sa_username")).
                firstName(rs.getString("sa_firstname")).
                lastName(rs.getString("sa_lastname")).
                email(rs.getString("sa_email")).
                teamLead(new Agent.Key(rs.getString("sa_lead"))).
                supervisor(new Agent.Key(rs.getString("sa_supervisor"))).
                role(role).
                getObject();
        
        return agent;
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