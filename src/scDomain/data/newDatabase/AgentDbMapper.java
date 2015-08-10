/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.data.newDatabase;

/**
 *
 * @author Morgan
 */
import java.sql.*;
import scDomain.data.pools.NewPool;
import scDomain.domain.newMappers.*;
import scDomain.domain.newObjects.*;

public final class AgentDbMapper extends AgentAbstractMapper implements AbstractDbMapper<Agent> {
    private final Connection connection;
    private DbMapperUtility util;
    
    static String getTable() {
        return "scweb_sc_agents";
    }
    
    static Agent populateAgent(ResultSet rs) throws SQLException {
        //TODO:  so much so much.  Add in Role and Dept and figure out where to get lead keys from.
        Agent agent = new Agent(new Agent.Key(rs.getString("sa_username")),
                new Agent.Builder()
                        .username(rs.getString("sa_username"))
                        .firstName(rs.getString("sa_firstname"))
                        .lastName(rs.getString("sa_lastname"))
                        .email(rs.getString("sa_email"))
                        .teamLead(new Agent.Key(rs.getString("sa_lead")))
                        .supervisor(new Agent.Key(rs.getString("sa_supervisor")))
        );
        
        return agent;
    }
    
    protected Agent doFind(Agent.Key key) {
        return null;
    }
    
    //Need to find a way to force each concrete mapper to assign a value to newPool.
    public AgentDbMapper(Connection connection) {
        super();
    }

    protected PreparedStatement findStatement(Agent.Key key) throws SQLException {
        PreparedStatement findStatement = connection.prepareStatement(
                "SELECT a.*, d.*, r.*\n"
                        + "FROM (" + getTable() + " a INNER JOIN scweb_sc_departments d\n"
                        + "\tON a.sa_department = d.sd_department)\n"
                        + "\tINNER JOIN scweb_sc_roles r ON a.sa_role = r.sr_role\n"
                        + "WHERE sa_username = ?"
        );
        findStatement.setString(1, key.getID());
        return findStatement;
    }

    protected Agent doLoad(ResultSet rs) throws SQLException {
        return populateAgent(rs);
    }
}