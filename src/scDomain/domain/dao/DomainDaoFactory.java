/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scDomain.domain.dao;

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
    @Override
    public DepartmentDao getDepartmentDao() {
        if (provider == null) { throw new NullPointerException(); }
        return new DepartmentDaoWrapper(provider.getDepartmentDao(), DomainObject.Type.DEPARTMENT.pool);
    }
    @Override
    public TimeSlotDao getTimeSlotDao() {
        if (provider == null) { throw new NullPointerException(); }
        return new TimeSlotDaoWrapper(provider.getTimeSlotDao(), DomainObject.Type.TIME_SLOT.pool);
    }
    @Override
    public TimeSlotDao.CategoryDao getTimeSlotCategoryDao() {
        if (provider == null) { throw new NullPointerException(); }
        return new TimeSlotDaoWrapper.CategoryWrapper(provider.getTimeSlotCategoryDao(), DomainObject.Type.TIME_SLOT.pool);
    }
    @Override
    public TimeSlotDao.TypeDao getTimeSlotTypeDao() {
        if (provider == null) { throw new NullPointerException(); }
        return new TimeSlotDaoWrapper.TypeWrapper(provider.getTimeSlotTypeDao(), DomainObject.Type.TIME_SLOT.pool);
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
    public final static class DepartmentDaoWrapper extends DomainDaoWrapper.FindAll<Department, Department.Key, Department.Builder>
    implements DepartmentDao {
        private final DepartmentDao mapper;
        
        @Override
        DepartmentDao getMapper() { return mapper; }
        private DepartmentDaoWrapper(DepartmentDao mapper, DomainObject.Type.Pool<Department> pool) {
            super(mapper, pool);
            this.mapper = mapper;
        }
    }
    public final static class TimeSlotDaoWrapper extends DomainDaoWrapper.Updater<TimeSlot, TimeSlot.Key, TimeSlot.Builder>
    implements TimeSlotDao {
        private final TimeSlotDao mapper;
        
        @Override
        TimeSlotDao getMapper() { return mapper; }
        private TimeSlotDaoWrapper(TimeSlotDao mapper, DomainObject.Type.Pool<TimeSlot> pool) {
            super(mapper, pool);
            this.mapper = mapper;
        }
        @Override
        public ArrayList<TimeSlot> findByAgent(Agent.Key agent) {
            if (agent == null) { throw new NullPointerException(); }
            return mapper.findByAgent(agent);
        }
        @Override
        public ArrayList<TimeSlot> findByDepartment(Department.Key department) {
            if (department == null) { throw new NullPointerException(); }
            return mapper.findByDepartment(department);
        }
            public final static class CategoryWrapper extends DomainDaoWrapper.FindAll.Only<TimeSlot.Category, TimeSlot.Category.Key, TimeSlot.Category.Builder>
            implements TimeSlotDao.CategoryDao {
                private final TimeSlotDao.CategoryDao mapper;

                @Override
                TimeSlotDao.CategoryDao getMapper() { return mapper; }
                private CategoryWrapper(TimeSlotDao.CategoryDao mapper, DomainObject.Type.Pool<TimeSlot.Category> pool) {
                    super(mapper, pool);
                    this.mapper = mapper;
                }
            }
            public final static class TypeWrapper extends DomainDaoWrapper.FindAll.Only<TimeSlot.Type, TimeSlot.Type.Key, TimeSlot.Type.Builder>
            implements TimeSlotDao.TypeDao {
                private final TimeSlotDao.TypeDao mapper;

                @Override
                TimeSlotDao.TypeDao getMapper() { return mapper; }
                private TypeWrapper(TimeSlotDao.TypeDao mapper, DomainObject.Type.Pool<TimeSlot.Type> pool) {
                    super(mapper, pool);
                    this.mapper = mapper;
                }
            }
    }
}