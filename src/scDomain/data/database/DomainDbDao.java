/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.data.database;

import java.sql.*;
import java.util.ArrayList;
import morgan.database.DbField;
import morgan.database.DbField.StatementArray;
import scDomain.domain.dao.*;
import scDomain.domain.objects.*;

/**
 *
 * @author Morgan
 */
abstract class DomainDbDao <O extends DomainObject<O>, K extends DomainObject.Key<O>> implements DomainDao<O, K> {
    final Connection connection;
    
    DomainDbDao(Connection connection) {
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
    //TODO:  Determine if ArrayList is best collection to use.
    //TODO:  Use class variable define in subclasses to initialize ArrayList to appropriate size.
    ArrayList<O> findMany(DbField.Values[] pairings) {
        ArrayList<O> objects = new ArrayList<>();
        
        try (StatementArray statements = DbField.Values.getStatements(connection, pairings);) {
            for (PreparedStatement statement : statements) {
                try (ResultSet rs = statement.executeQuery();) {
                    while (rs.next()) {
                        objects.add(load(rs));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    //TODO ERROR HANDLING
                }
            }
        } catch (SQLException e) {
            //something
        }
        return objects;
    }
    abstract PreparedStatement findStatement(K key) throws SQLException;
    abstract O load(ResultSet rs) throws SQLException;
}