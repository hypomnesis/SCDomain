package scDomain.domain.mappers;

import scDomain.domain.objects.Agent;

public interface AgentMapper extends DomainMapper<Agent.AgentKey, Agent> {
	public Agent find(Agent.AgentKey key);
}