/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.data.database;

import javax.sql.DataSource;
import scDomain.domain.dao.*;

/**
 *
 * @author Morgan
 */
public enum DbDaoProvider implements DomainDaoProvider {
    INSTANCE;
    
    private DataSource datasource = null;
    
    public DomainDaoProvider setDataSource(DataSource datasource) {
        if (datasource == null) { throw new NullPointerException(); }
        this.datasource = datasource;
        return this;
    }
    
    @Override
    public AgentDao getAgentDao() {
        if (datasource == null) { throw new NullPointerException(); }
        return new AgentDbDao(datasource);
    }
    @Override
    public RoleDao getRoleDao() {
        if (datasource == null) { throw new NullPointerException(); }
        return new RoleDbDao(datasource);
    }
}