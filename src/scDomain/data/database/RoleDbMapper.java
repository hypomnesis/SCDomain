package scDomain.data.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import scDomain.domain.mappers.RoleMapper;
import scDomain.domain.objects.Role;

public final class RoleDbMapper extends DomainDbMapper<Role.RoleKey, Role>
		implements RoleMapper {
	private static final String STATUS_YES = "Y";
	
	protected static Role populateRole(ResultSet rs) throws SQLException {
		Role role = new Role(new Role.RoleKey(rs.getString("sr_role")));

		role.setName(rs.getNString("sr_name"));
		role.setLevel(rs.getShort("sr_level"));
		role.setOnScorecard(rs.getString("sr_scorecard").equals(STATUS_YES));
		
		return role;
	}
	
	protected static String getTable() {
		return "scweb_sc_roles";
	}
	
	//I don't know why I had to explicitly code this -- research.
	public RoleDbMapper(Connection connection) {
		super(connection);
	}
	
	protected PreparedStatement findStatement(Role.RoleKey key) throws SQLException {
		PreparedStatement findStatement = connection.prepareStatement(
					"SELECT r.* FROM " + getTable() + " r WHERE sr_role = ?"
				);
		findStatement.setString(1, key.getID());
		return findStatement;
	}
	
	protected Role doLoad(ResultSet rs) throws SQLException {
		return populateRole(rs);
	}
}
