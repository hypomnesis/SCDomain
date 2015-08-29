/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scDomain.domain.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import scDomain.domain.dao.AgentDao;
import scDomain.domain.dao.DomainDaoProvider;
import scDomain.domain.dao.RoleDao;
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
    
    public DomainDaoProvider setProvider(DomainDaoProvider provider) {
        if (provider == null) { throw new NullPointerException(); }
        this.provider = provider;
        return this;
    }
    
    @Override
    public AgentDaoWrapper getAgentDao() {
        if (provider == null) { throw new NullPointerException(); }
        AgentDao dao = provider.getAgentDao();
        return new AgentDaoWrapper(dao, DomainObject.Type.AGENT.pool);
    }
    @Override
    public RoleDaoWrapper getRoleDao() {
        if (provider == null) { throw new NullPointerException(); }
        return new RoleDaoWrapper(provider.getRoleDao(), DomainObject.Type.ROLE.pool);
    }
    
    public final static class AgentDaoWrapper extends DomainDaoWrapper.Updater<Agent, Agent.Key, Agent.Builder> implements AgentDao {
        private final AgentDao mapper;
        
        private AgentDaoWrapper(AgentDao mapper, DomainObject.Type.Pool<Agent> pool) {
            super(mapper, pool);
            this.mapper = mapper;
        }
        @Override
        AgentDao getMapper() { return mapper; }
        @Override
        public ArrayList<Agent> findByDepartment(Collection<Department.Key> departments) {
            if (departments == null || departments.isEmpty()) {
                throw new IllegalArgumentException();
            }
            return mapper.findByDepartment(departments);
        }
        /*public ArrayList<Agent> findByDepartment(Department.Key department) {
            if (department == null) { throw new NullPointerException(); }
            
            return findByDepartment(new Department.Key[] { department });
        }*/
        @Override
        public ArrayList<Agent> findByDepartment(Collection<Department.Key> departments, boolean status) {
            if (departments == null || departments.isEmpty()) {
                throw new IllegalArgumentException();
            }
            return mapper.findByDepartment(departments, status);
        }
        /*public ArrayList<Agent> findByDepartment(Department.Key department, boolean status) {
            if (department == null) { throw new NullPointerException(); }
            
            return findByDepartment(new Department.Key[] { department }, status);
        }*/
        @Override
        public ArrayList<Agent> findByDeptRole(Collection<Department.Key> departments, Collection<Role.Key> roles) {
            if (departments == null || departments.isEmpty() || roles == null || roles.isEmpty()) {
                throw new IllegalArgumentException();
            }
            return mapper.findByDeptRole(departments, roles);
        }
        /*public ArrayList<Agent> findByDeptRole(Department.Key department, Role.Key[] roles) {
            if (department == null) { throw new NullPointerException(); }
            
            return findByDeptRole(new Department.Key[] { department }, roles);
        }
        public ArrayList<Agent> findByDeptRole(Department.Key[] departments, Role.Key role) {
            if (role == null) { throw new NullPointerException(); }
            
            return findByDeptRole(departments, new Role.Key[] { role });
        }
        public ArrayList<Agent> findByDeptRole(Department.Key department, Role.Key role) {
            if (department == null || role == null) {
                throw new NullPointerException();
            }
            return findByDeptRole(new Department.Key[] { department }, new Role.Key[] { role });
        }*/
        @Override
        public ArrayList<Agent> findByDeptRole(Collection<Department.Key> departments, Collection<Role.Key> roles, boolean status) {
            if (departments == null || departments.isEmpty() || roles == null || roles.isEmpty()) {
                throw new IllegalArgumentException();
            }
            return mapper.findByDeptRole(departments, roles, status);
        }
        /*public ArrayList<Agent> findByDeptRole(Department.Key department, Role.Key[] roles, boolean status) {
            if (department == null) { throw new NullPointerException(); }
            
            return findByDeptRole(new Department.Key[] { department }, roles, status);
        }
        public ArrayList<Agent> findByDeptRole(Department.Key[] departments, Role.Key role, boolean status) {
            if (role == null) { throw new NullPointerException(); }
            
            return findByDeptRole(departments, new Role.Key[] { role }, status);
        }
        public ArrayList<Agent> findByDeptRole(Department.Key department, Role.Key role, boolean status) {
            if (department == null || role == null) {
                throw new NullPointerException();
            }
            return findByDeptRole(new Department.Key[] { department }, new Role.Key[] { role }, status);
        }*/
        @Override
        public HashMap<Department.Key, Integer> getDeptLevels(Agent.Key key) {
            if (key == null) { throw new NullPointerException(); }
            return mapper.getDeptLevels(key);
        }
    }
    public final static class RoleDaoWrapper extends DomainDaoWrapper.FindAll.Only<Role, Role.Key, Role.Builder> implements RoleDao {
        private final RoleDao mapper;
        
        @Override
        RoleDao getMapper() { return mapper; }
        private RoleDaoWrapper(RoleDao mapper, DomainObject.Type.Pool<Role> pool) {
            super(mapper, pool);
            this.mapper = mapper;
        }
    }
}