package scDomain.domain.mappers;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import scDomain.data.database.*;

public class MapperFactory {
	public static AgentMapper getAgentMapper(DataSource dataSource) {
		AgentMapper agentMapper = null;
		//TODO:  Better error handling, naturally.
		try {
			agentMapper = new AgentDbMapper(dataSource.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return agentMapper;
	}

	public static AgentMapper getAgentMapper(Connection connection) {
		return new AgentDbMapper(connection);
	}
	public static RoleMapper getRoleMapper(Connection connection) {
		return new RoleDbMapper(connection);
	}
}