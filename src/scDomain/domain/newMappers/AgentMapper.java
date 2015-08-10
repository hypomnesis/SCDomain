/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.newMappers;

/**
 *
 * @author Morgan
 */
import scDomain.domain.newObjects.*;

public interface AgentMapper extends DomainMapper<Agent, Agent.Key> {
    //Eventually want to add custom Agent finder methods (by dept etc).
    //Can I get rid of the below?
    @Override
    public Agent find(Agent.Key key);
}