/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scDomain.domain.objects;

import java.util.HashMap;
import scDomain.domain.dao.DomainDaoFactory;
import scDomain.domain.dao.AgentDao;
import scDomain.domain.dao.DomainDaoProvider;
import scDomain.domain.objects.*;

/**
 *
 * @author hayes
 */
public class User {
    private static User instance = null;
    private final Agent agent;
    private final HashMap<Department.Key, Integer> deptLevels;
    public final DomainDaoProvider factory = DomainDaoFactory.INSTANCE;
    
    public static final User setUser(String username, DomainDaoProvider provider) {
        if (instance != null) { throw new IllegalStateException(); } // or just return user?
        instance = new User(username, provider);
        return instance;
    }
    public static final User getUser() {
        if (instance == null) { throw new IllegalStateException(); }
        return instance;
    }
    
    private User(String username, DomainDaoProvider provider) {
        if (provider == null || username == null || username.length() == 0) {
            throw new IllegalArgumentException();
        }
        DomainDaoFactory.INSTANCE.setProvider(provider);
        
        AgentDao dao = factory.getAgentDao();
        Agent.Key key = new Agent.Key(username);
        agent = dao.find(key);
        
        if (agent == null) {
            throw new IllegalArgumentException();
        }
        deptLevels = dao.getDeptLevels(key);
        factory.close();
    }
    
    public Integer getDeptLevel(Department.Key department) {
        return deptLevels.get(department);
    }
    public <O extends DomainObject<O>, B extends DomainObject.Builder<O>> O getObjectFromBuilder(B builder) {
        return builder.getObject();
    }
}