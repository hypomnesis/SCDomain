/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.commands;

import scDomain.domain.dao.AgentDao;
import scDomain.domain.objects.Agent;

/**
 *
 * @author Morgan
 */
public class AgentCommand extends DomainCommand<Agent, Agent.Key> {
    @Override
    public AgentDao getDao() { return provider.getAgentDao(); }
}