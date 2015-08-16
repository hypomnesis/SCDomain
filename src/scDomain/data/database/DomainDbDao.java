/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.data.database;

import java.sql.*;
import javax.sql.DataSource;
import scDomain.domain.dao.*;
import scDomain.domain.objects.*;

/**
 *
 * @author Morgan
 */
abstract class DomainDbDao <O extends DomainObject<O>, K extends DomainObject.Key<O>> implements DomainDao<O, K> {
    protected final Connection connection;
    
    protected abstract PreparedStatement findStatement(K key) throws SQLException;
    
    protected DomainDbDao(Connection connection) {
        if (connection == null) { throw new NullPointerException(); }
        this.connection = connection;
    }
    
    //Need to review exceptions.
    @Override
    public O find(K key) {
        //Check for already loaded objects here.
        O object = null;
        
        try (PreparedStatement findStatement = findStatement(key);
            ResultSet rs = findStatement.executeQuery();) {
            rs.next();

            object = load(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            //Better solution needed.
        }
        return object;
    }
    abstract O load(ResultSet rs) throws SQLException;
}