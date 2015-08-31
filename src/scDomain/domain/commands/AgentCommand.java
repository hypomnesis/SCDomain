/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.commands;

import scDomain.domain.dao.DomainDaoFactory;
import scDomain.domain.dao.AgentDao;
import scDomain.domain.objects.User;
import scDomain.domain.objects.Agent;

/**
 *
 * @author Morgan
 */
public class AgentCommand extends DomainCommand<Agent, Agent.Key, Agent.Builder> {
    private final AgentDao mapper;
    
    private AgentCommand() { mapper = provider.getAgentDao(); }
    
    @Override
    DomainDaoFactory.AgentDaoWrapper getDao() { return provider.getAgentDao(); }
}