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
    
    public void setProvider(DomainDaoProvider provider) {
        if (provider == null) { throw new NullPointerException(); }
        this.provider = provider;
    }
    
    @Override
    public AgentDao getAgentDao() {
        if (provider == null) { throw new NullPointerException(); }
        return new AgentDaoWrapper(provider.getAgentDao(), DomainObject.Type.AGENT.pool);
    }
    @Override
    public RoleDao getRoleDao() {
        if (provider == null) { throw new NullPointerException(); }
        return new RoleDaoWrapper(provider.getRoleDao(), DomainObject.Type.ROLE.pool);
    }
}