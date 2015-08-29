/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import scDomain.domain.objects.*;

/**
 *
 * @author Morgan
 */

public interface AgentDao extends DomainDao.Updater<Agent, Agent.Key, Agent.Builder> {
    public ArrayList<Agent> findByDepartment(Collection<Department.Key> departments);
    public ArrayList<Agent> findByDepartment(Collection<Department.Key> departments, boolean status);
    
    public ArrayList<Agent> findByDeptRole(Collection<Department.Key>departments, Collection<Role.Key>roles);
    public ArrayList<Agent> findByDeptRole(Collection<Department.Key> departments, Collection<Role.Key> roles, boolean status);
    public HashMap<Department.Key, Integer> getDeptLevels(Agent.Key key);
}