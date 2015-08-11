/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scDomain.domain.dao;

/**
 *
 * @author hayes
 */
public interface DomainDaoProvider {
    public AgentDao getAgentDao();
    public RoleDao getRoleDao();
}