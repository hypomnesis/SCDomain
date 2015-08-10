/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scDomain.domain.dao;

import scDomain.domain.objects.Department;
import scDomain.domain.objects.Role;
import scDomain.domain.objects.Agent;

/**
 *
 * @author hayes
 */
public enum DomainDaoFactory implements DomainDaoProvider {
    INSTANCE;
    
    private DomainDaoProvider provider;
    
    public enum Type {
        AGENT, DEPARTMENT, ROLE;
        
        private final DomainPool pool;
        
        Type() {
            switch (this) {
                case AGENT:
                    pool = new DomainPool<Agent>();
                    break;
                case DEPARTMENT:
                    pool = new DomainPool<Department>();
                    break;
                case ROLE:
                    pool = new DomainPool<Role>();
                    break;
                default:
                    pool = null;
            }
        }
    }
    
    private static class AgentDaoWrapper extends DomainDaoWrapper.Updater<Agent, Agent.Key, Agent.Builder> implements AgentDao {
        private AgentDaoWrapper(AgentDao mapper, DomainPool<Agent> pool) {
            super(mapper, pool);
        }
    }
    private static class RoleDaoWrapper extends DomainDaoWrapper.FindAll<Role, Role.Key> implements RoleDao {
        private RoleDaoWrapper(RoleDao mapper, DomainPool<Role> pool) {
            super(mapper, pool);
        }
    }
    
    public void setProvider(DomainDaoProvider provider) {
        if (provider == null) { throw new NullPointerException(); }
        
        this.provider = provider;
    }
    
    @Override
    public AgentDao getAgentMapper() {
        return new AgentDaoWrapper(provider.getAgentMapper(), Type.AGENT.pool);
    }
    @Override
    public RoleDao getRoleMapper() {
        return new RoleDaoWrapper(provider.getRoleMapper(), Type.ROLE.pool);
    }
}