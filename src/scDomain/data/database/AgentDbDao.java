/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.data.database;

import static scDomain.data.database.AgentDbDao.Field.*;

/**
 *
 * @author Morgan
 */
import scDomain.domain.objects.*;
import scDomain.domain.dao.AgentDao;
import java.sql.*;

final class AgentDbDao extends DomainDbDao<Agent, Agent.Key> implements AgentDao {
    static final String TABLE = "scweb_sc_agents";
    
    enum Field {
        USERNAME("sa_username"),
        FIRSTNAME("sa_firstname"),
        LASTNAME("sa_lastname"),
        EMAIL("sa_email"),
        TEAMLEAD("sa_lead"),
        SUPERVISOR("sa_supervisor"),
        DEPARTMENT("sa_department"),
        ROLE("sa_role");
        
        private final String id;
        
        private Field(String id) { this.id = id; }
    }
    
    AgentDbDao(Connection connection) { super(connection); }
    
    @Override
    protected PreparedStatement findStatement(Agent.Key key) throws SQLException {
        PreparedStatement findStatement = connection.prepareStatement(
                "SELECT * FROM " + TABLE + " WHERE sa_username = ?"
        );
        findStatement.setString(1, key.getID());
        return findStatement;
    }

    @Override
    Agent load(ResultSet rs) throws SQLException {
        Agent agent = new Agent.Builder().
                username(rs.getString(USERNAME.id)).
                firstName(rs.getString(FIRSTNAME.id)).
                lastName(rs.getString(LASTNAME.id)).
                email(rs.getString(EMAIL.id)).
                teamLead(new Agent.Key(rs.getString(TEAMLEAD.id))).
                supervisor(new Agent.Key(rs.getString(SUPERVISOR.id))).
                department(new Department.Key(rs.getString(DEPARTMENT.id))).
                role(new Role.Key(rs.getString(ROLE.id))).
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