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
import scDomain.domain.objects.Role;
import scDomain.domain.dao.RoleDao;
import java.sql.*;
import javax.sql.DataSource;

final class RoleDbDao extends DomainDbDao<Role, Role.Key> implements RoleDao {
    static final String TABLE = "scweb_sc_roles";
    
    static Role populateRole(ResultSet rs) throws SQLException {
        //TODO:  so much so much.  Add in Role and Dept and figure out where to get lead keys from.
        return new Role.Builder().
                id(rs.getString("sr_role")).
                name(rs.getString("sr_name")).
                level(rs.getShort("sr_level")).
                onScorecard(rs.getString("sr_scorecard").equalsIgnoreCase("Y")).
                getObject();
    }
    
    RoleDbDao(DataSource datasource) { super(datasource); }
    RoleDbDao(Connection connection) { super(connection); }
    
    @Override
    protected PreparedStatement findStatement(Role.Key key) throws SQLException {
        PreparedStatement findStatement = connection.prepareStatement(
                "SELECT r.* FROM " + TABLE + " WHERE sr_role = ?"
        );
        findStatement.setString(1, key.getID());
        return findStatement;
    }

    @Override
    protected Role doLoad(ResultSet rs) throws SQLException {
        return populateRole(rs);
    }
    //All this TODO!!!
    @Override
    public Role[] findAll() {
        //pool stuff here.
        return null;
    }
}