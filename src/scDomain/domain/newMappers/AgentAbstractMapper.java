/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.newMappers;

import scDomain.domain.newObjects.Agent;
import scDomain.data.pools.NewPool;

/**
 *
 * @author Morgan
 */
public abstract class AgentAbstractMapper extends DomainAbstractMapper<Agent, Agent.Key> {
    protected AgentAbstractMapper() {
        super(NewPool.getInstance(NewPool.Type.AGENT));
    }
}