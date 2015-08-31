/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.data.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import morgan.database.DbField;
import scDomain.domain.dao.DepartmentDao;
import scDomain.domain.objects.Agent;
import scDomain.domain.objects.Department;

/**
 *
 * @author Morgan
 */
public class DepartmentDbDao extends DomainDbDao.FindAll<Department, Department.Key> implements DepartmentDao {
    static final String TABLE = "scweb_sc_departments";
    private static final String STRING_FILL = "DUMMY";
    
    static final DomainField.DeptField ID = new DomainField.DeptField("sd_department");
    static final DbField.StringField NAME = new DbField.StringField("sd_name", STRING_FILL);
    static final DomainField.AgentField HEAD = new DomainField.AgentField("sd_head");
    static final DomainField.StringField EMAIL = new DomainField.StringField("sd_email", STRING_FILL);
    static final DomainField.StringField HR_PARTNER = new DomainField.StringField("sd_hr_partner", STRING_FILL);
    
    private static final String SELECT_START = "SELECT * FROM " + TABLE;
    
    DepartmentDbDao(Connection connection) { super(connection); }
    
    @Override
    Department load(ResultSet rs) throws SQLException {
        return new Department.Builder().
                id(ID.getValue(rs).toString()).
                name(NAME.getValue(rs)).
                head(HEAD.getValue(rs)).
                email(EMAIL.getValue(rs)).
                hrPartner(HR_PARTNER.getValue(rs)).
                getObject();
    }
    
    @Override
    public ArrayList<Department> find(Collection<Department.Key> keys) {
        return findMany(new DbField.Values[] { new DbField.Values<Department.Key>(ID, keys, SELECT_START + " WHERE") });
    }
    @Override
    PreparedStatement getFindAllStatement() throws SQLException {
        return connection.prepareStatement(SELECT_START);
    }
}