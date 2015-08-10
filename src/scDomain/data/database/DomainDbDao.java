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
import scDomain.domain.objects.DomainObject;
import scDomain.domain.objects.DomainKey;
import java.sql.*;
import scDomain.domain.dao.*;

public abstract class DomainDbDao <O extends DomainObject<O>, K extends DomainKey<O>> implements DomainDao<O, K> {
    protected final Connection connection;
    
    protected abstract PreparedStatement findStatement(K key) throws SQLException;
    protected abstract O doLoad(ResultSet rs) throws SQLException;
    
    //Eventually let it get its own connection?
    protected DomainDbDao(Connection connection) {
        this.connection = connection;
    }
    
    //Need to review exceptions.
    public O find(K key) {
        //Check for already loaded objects here.
        O object = null;
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        
        try {
            findStatement = findStatement(key);
            rs = findStatement.executeQuery();
            rs.next();

            object = load(key, rs);
            //clean resources der I ferget how. I want to put this in finally block.
            //also, I should close this with an object clean up in case more db actions are required...
            //or do i keep them alive?  who knows?
            findStatement.close();
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