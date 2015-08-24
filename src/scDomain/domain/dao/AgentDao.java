/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.dao;

import java.util.ArrayList;
import scDomain.domain.objects.*;

/**
 *
 * @author Morgan
 */

public interface AgentDao extends DomainDao.Updater<Agent, Agent.Key, Agent.Builder> {
    public ArrayList<Agent> findByDepartment(Department.Key department);
    public ArrayList<Agent> findByDepartment(Department.Key[] departments);
    public ArrayList<Agent> findByDepartment(Department.Key department, boolean status);
    public ArrayList<Agent> findByDepartment(Department.Key[] departments, boolean status);
    
    public ArrayList<Agent> findByDeptRole(Department.Key department, Role.Key role);
    public ArrayList<Agent> findByDeptRole(Department.Key[] departments, Role.Key role);
    public ArrayList<Agent> findByDeptRole(Department.Key department, Role.Key[] roles);
    public ArrayList<Agent> findByDeptRole(Department.Key[] departments, Role.Key[] roles);
    public ArrayList<Agent> findByDeptRole(Department.Key department, Role.Key role, boolean status);
    public ArrayList<Agent> findByDeptRole(Department.Key[] departments, Role.Key role, boolean status);
    public ArrayList<Agent> findByDeptRole(Department.Key department, Role.Key[] roles, boolean status);
    public ArrayList<Agent> findByDeptRole(Department.Key[] departments, Role.Key[] roles, boolean status);
}