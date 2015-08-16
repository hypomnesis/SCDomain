/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scDomain.domain.dao;

import scDomain.domain.objects.*;

/**
 *
 * @author hayes
 */
public enum DomainDaoFactory implements DomainDaoProvider {
    INSTANCE;
    
    private DomainDaoProvider provider = null;
    
    @Override
    public void startTransaction() { provider.startTransaction(); }
    @Override
    public void commit() { provider.commit(); }
    @Override
    public void rollback() { provider.rollback(); }
    @Override
    public void close() { provider.close(); }
    
    private static class AgentDaoWrapper extends DomainDaoWrapper.Updater<Agent, Agent.Key, Agent.Builder> implements AgentDao {
        private AgentDaoWrapper(AgentDao mapper, DomainObject.Type.Pool<Agent> pool) {
            super(mapper, pool);
        }
    }
    private static class RoleDaoWrapper extends DomainDaoWrapper.FindAll.Only<Role, Role.Key, Role.Builder> implements RoleDao {
        private RoleDaoWrapper(RoleDao mapper, DomainObject.Type.Pool<Role> pool) {
            super(mapper, pool);
        }
    }
    
    public DomainDaoProvider setProvider(DomainDaoProvider provider) {
        if (provider == null) { throw new NullPointerException(); }
        this.provider = provider;
        return this;
    }
    
    @Override
    public AgentDao getAgentDao() {
        if (provider == null) { throw new NullPointerException(); }
        AgentDao dao = provider.getAgentDao();
        return new AgentDaoWrapper(dao, DomainObject.Type.AGENT.pool);
    }
    @Override
    public RoleDao getRoleDao() {
        if (provider == null) { throw new NullPointerException(); }
        return new RoleDaoWrapper(provider.getRoleDao(), DomainObject.Type.ROLE.pool);
    }
}