/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.data.database;

import morgan.database.DbField;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import scDomain.domain.objects.*;

/**
 *
 * @author Morgan
 */
abstract class DomainField<K extends DomainObject.Key> extends DbField<K> {
    DomainField(String label) { super(label); }
    DomainField(String label, K fillValue) { super(label, fillValue); }
    
    static final class AgentField extends DomainField<Agent.Key> {
        AgentField(String label) { super(label); }
        AgentField(String label, Agent.Key fillValue) { super(label, fillValue); }
        @Override
        public void setParam(PreparedStatement statement, int index, Agent.Key value) throws SQLException {
            statement.setString(index, value.getID());
        }
        @Override
        public Agent.Key getValue(ResultSet rs) throws SQLException {
            String username = rs.getString(this.getLabel());
            
            if (username == null) {
                return null;
            } else {
                return new Agent.Key(username);
            }
        }
    }
    static final class RoleField extends DomainField<Role.Key> {
        RoleField(String label) { super(label); }
        RoleField(String label, Role.Key fillValue) { super(label, fillValue); }
        @Override
        public void setParam(PreparedStatement statement, int index, Role.Key value) throws SQLException {
            statement.setString(index, value.getID());
        }
        @Override
        public Role.Key getValue(ResultSet rs) throws SQLException {
            return new Role.Key(rs.getString(this.getLabel()));
        }
    }
    static final class DeptField extends DomainField<Department.Key> {
        DeptField(String label) { super(label); }
        DeptField(String label, Department.Key fillValue) { super(label, fillValue); }
        @Override
        public void setParam(PreparedStatement statement, int index, Department.Key value) throws SQLException {
            statement.setString(index, value.getID());
        }
        @Override
        public Department.Key getValue(ResultSet rs) throws SQLException {
            return new Department.Key(rs.getString(this.getLabel()));
        }
    }
}