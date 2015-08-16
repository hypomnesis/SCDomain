/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.data.database;

import java.sql.Connection;

/**
 *
 * @author Morgan
 */
public interface ConnectionProvider {
    public Connection getConnection();
    public void closeConnection(Connection connection);
    public void commit(Connection connection);
    public void rollback(Connection connection);
}