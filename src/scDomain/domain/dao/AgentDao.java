/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.dao;

/**
 *
 * @author Morgan
 */
import scDomain.domain.objects.Agent;

public interface AgentDao extends DomainDao.Updater<Agent, Agent.Key, Agent.Builder> {}