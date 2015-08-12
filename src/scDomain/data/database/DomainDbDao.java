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
    protected abstract O doLoad(ResultSet rs) throws SQLException;
    
    //Eventually let it get its own connection?
    protected DomainDbDao(DataSource datasource) {
        if (datasource == null) { throw new NullPointerException(); }
        try {
            this.connection = datasource.getConnection();
        } catch(SQLException e) {
            //No clue if this is the best way of dealing with this.
            throw new IllegalArgumentException(e.getMessage(), e.getCause());
        }
    }
    protected DomainDbDao(Connection connection) {
        if (connection == null) { throw new NullPointerException(); }
        this.connection = connection;
    }
    
    //Need to review exceptions.
    @Override
    public O find(K key) {
        //Check for already loaded objects here.
        O object = null;
        
        try {
            PreparedStatement findStatement = findStatement(key);
            try {
                ResultSet rs = findStatement.executeQuery();
                try {
                    rs.next();

                    object = load(key, rs);
                } catch (SQLException e) {
                    //something
                } finally {
                    rs.close();
                }
            } catch (SQLException e) {
                //something
            } finally {
                findStatement.close();
        }
        //should I close the connection or keep it alive for other processes?
        connection.close();		//Don't want to close connection pool connection.  Work with datasource?
        } catch (SQLException e) {
            //Better solution needed.
            e.printStackTrace();
        }
        return object;
    }
    //Don't know how to abstract load up to the top.
    private O load(K key, ResultSet rs) throws SQLException {
        /*O object = newPool.get(key);
        
        if (object != null) { return null; }*/
        
        return doLoad(rs);
    }
}