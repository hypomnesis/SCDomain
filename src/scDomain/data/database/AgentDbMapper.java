package scDomain.data.database;

import java.sql.*;

import scDomain.domain.mappers.*;
import scDomain.domain.objects.*;

public class AgentDbMapper extends DomainDbMapper<Agent.AgentKey, Agent> implements AgentMapper {
	protected static Agent populateAgent(ResultSet rs) throws SQLException {
		Agent agent = new Agent(new Agent.AgentKey(rs.getString("sa_username")));
		
		agent.setFirstName(rs.getString("sa_firstname"));
		agent.setLastName(rs.getString("sa_lastname"));
		agent.setFirstName(rs.getString("sa_firstname"));
		agent.setRole(RoleDbMapper.populateRole(rs));
		agent.setEmail(rs.getString("sa_email"));
		agent.setTeamLead(rs.getString("sa_lead"));
		agent.setSupervisor(rs.getString("sa_supervisor"));
		
		return agent;
	}
	
	protected static String getTable() {
		return "scweb_sc_agents";
	}
	
	//I don't know why I had to explicitly code this -- research.
	public AgentDbMapper(Connection connection) {
		super(connection);
	}
	
	protected PreparedStatement findStatement(Agent.AgentKey key) throws SQLException {
		PreparedStatement findStatement = connection.prepareStatement(
					"SELECT a.*, d.*, r.*\n" + 
					"FROM (" + getTable() + " a INNER JOIN scweb_sc_departments d\n" +
						"\tON a.sa_department = d.sd_department)\n" +
						"\tINNER JOIN scweb_sc_roles r ON a.sa_role = r.sr_role\n" +
					"WHERE sa_username = ?"
				);
		findStatement.setString(1, key.getID());
		return findStatement;
	}
	
	protected Agent doLoad(ResultSet rs) throws SQLException {
		return populateAgent(rs);
	}
}