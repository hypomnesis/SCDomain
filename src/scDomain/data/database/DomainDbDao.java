/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.data.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import morgan.database.DbField;
import morgan.database.DbField.StatementArray;
import scDomain.domain.dao.*;
import scDomain.domain.objects.*;

/**
 *
 * @author Morgan
 */
abstract class DomainDbDao<O extends DomainObject<O>, K extends DomainObject.Key<O>> implements DomainDao<O, K> {
    final Connection connection;
    
    DomainDbDao(Connection connection) {
        if (connection == null) { throw new NullPointerException(); }
        this.connection = connection;
    }
    
    //Need to review exceptions.
    @Override
    public O find(K key) {
        ArrayList<O> objects = find(Arrays.asList(key));
        
        if (objects == null || objects.isEmpty()) {
            return null;
        } else {
            return objects.get(0);
        }
    }
    //TODO:  Determine if ArrayList is best collection to use.
    //TODO:  Use class variable define in subclasses to initialize ArrayList to appropriate size.
    ArrayList<O> findMany(DbField.Values[] pairings) {
        ArrayList<O> objects = new ArrayList<>();
        
        try (StatementArray statements = DbField.Values.getStatements(connection, pairings);) {
            for (PreparedStatement statement : statements) {
                objects = runStatement(statement);
            }
        } catch (SQLException e) {
            //something
        }
        return objects;
    }
    ArrayList<O> runStatement(PreparedStatement statement) throws SQLException {
        ArrayList<O> objects = new ArrayList<>();
        
        try (ResultSet rs = statement.executeQuery();) {
            while (rs.next()) {
                objects.add(load(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //TODO ERROR HANDLING
        }
        return objects;
    }
    abstract O load(ResultSet rs) throws SQLException;
    
    static abstract class FindAll<O extends DomainObject<O>, K extends DomainObject.Key<O>> extends DomainDbDao<O, K> {
        FindAll (Connection connection) { super(connection); }
        
        public ArrayList<O> findAll() {
            ArrayList<O> objects = new ArrayList<>();
            try {
                objects = this.runStatement(getFindAllStatement());
            } catch (SQLException e) {
                //something
            }
            return objects;
        }
        abstract PreparedStatement getFindAllStatement() throws SQLException;
    }
}