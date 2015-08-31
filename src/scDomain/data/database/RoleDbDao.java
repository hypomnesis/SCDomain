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
import java.util.ArrayList;
import java.util.Collection;
import morgan.database.DbField;

final class RoleDbDao extends DomainDbDao.FindAll<Role, Role.Key> implements RoleDao {
    static final String TABLE = "scweb_sc_roles";
    private static final String STRING_FILL = "DUMMY";
    
    static final DomainField.RoleField ID = new DomainField.RoleField("sr_role");
    static final DbField.StringField NAME = new DbField.StringField("sr_name", STRING_FILL);
    static final DbField.IntegerField LEVEL = new DbField.IntegerField("sr_level", -1);
    static final DomainField.YesNoField SCORECARD = new DomainField.YesNoField("sr_scorecard", false);
    
    private static final String SELECT_START = "SELECT * FROM " + TABLE;
    
    RoleDbDao(Connection connection) { super(connection); }
    
    @Override
    Role load(ResultSet rs) throws SQLException {
        return new Role.Builder().
                id(ID.getValue(rs).toString()).
                name(NAME.getValue(rs)).
                level(LEVEL.getValue(rs)).
                onScorecard(SCORECARD.getValue(rs)).
                getObject();
    }
    
    @Override
    public ArrayList<Role> find(Collection<Role.Key> keys) {
        return findMany(new DbField.Values[] { new DbField.Values<Role.Key>(ID, keys, SELECT_START + " WHERE") });
    }
    @Override
    PreparedStatement getFindAllStatement() throws SQLException {
        return connection.prepareStatement(SELECT_START);
    }
}