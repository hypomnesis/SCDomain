/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.data.database;

import java.sql.Connection;
import scDomain.domain.dao.*;

/**
 *
 * @author Morgan
 */
public enum DbDaoProvider implements DomainDaoProvider {
    INSTANCE;
    
    private ConnectionProvider provider = null;
    private Connection connection;
    private boolean inTransaction = false;
    
    private void newConnection() {
        if (provider == null) { throw new IllegalStateException(); }
        if (connection != null || inTransaction) { throw new IllegalStateException(); }
        
        connection = provider.getConnection();
    }
    private void validateConnection() {
        if (inTransaction && connection == null) {
            throw new IllegalStateException();
        } else if (!inTransaction) {
            //Not sure if need to throw error if not in transaction and connection exists.
            if (connection == null) { newConnection(); }
        }
    }
    
    public DomainDaoProvider setConnectionProvider(ConnectionProvider provider) {
        if (provider == null) { throw new NullPointerException(); }
        this.provider = provider;
        return this;
    }
    @Override
    public void startTransaction() {
        newConnection();
        inTransaction = true;
    }
    @Override
    public void commit() {
        provider.commit(connection);
        inTransaction = false;
    }
    @Override
    public void rollback() {
        provider.rollback(connection);
        inTransaction = false;
    }
    @Override
    public void close() {
        provider.closeConnection(connection);
        inTransaction = false;
    }
    
    @Override
    public AgentDao getAgentDao() {
        validateConnection();
        return new AgentDbDao(connection);
    }
    @Override
    public RoleDao getRoleDao() {
        validateConnection();
        return new RoleDbDao(connection);
    }
    @Override
    public DepartmentDao getDepartmentDao() {
        validateConnection();
        return new DepartmentDbDao(connection);
    }
    @Override
    public TimeSlotDao getTimeSlotDao() {
        validateConnection();
        return new TimeSlotDbDao(connection);
    }
    public TimeSlotDao.CategoryDao getTimeSlotCategoryDao() {
        validateConnection();
        return new TimeSlotDbDao.CategoryDbDao(connection);
    }
    public TimeSlotDao.TypeDao getTimeSlotTypeDao() {
        validateConnection();
        return new TimeSlotDbDao.TypeDbDao(connection);
    }
}