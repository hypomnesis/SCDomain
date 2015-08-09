package scDomain.domain.objects;

public class Agent extends DomainObject<Agent.AgentKey> {
	private String firstName;
	private String lastName;
	private String displayName = null;
	private Role role;
	private String email;
	//Do I want them to have full on Agent objects as well and lazy load them?
	private AgentKey teamLead;
	private AgentKey supervisor;
	
	public String getUsername() { return key.id; }
	
	public Role getRole() { return role; }
	public void setRole(Role role) { this.role = role; }
	
	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }
	
	public String getLastName() {return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	
	public String getDisplayName() {
		if (displayName == null) {
			displayName = firstName + (firstName.length() > 0 & lastName.length() > 0 ? ' ' : "") + lastName;
		}
		return displayName;
	}
	
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	public String getTeamLead() { return teamLead.id; }
	public void setTeamLead(String teamLead) { this.teamLead = new Agent.AgentKey(teamLead); }
	//Clone?
	public Agent.AgentKey getTeamLeadKey() { return teamLead; }
	public void setTeamLead(Agent.AgentKey teamLead) { this.teamLead = teamLead; }

	public String getSupervisor() { return supervisor.id; }
	public void setSupervisor(String supervisor) { this.supervisor = new Agent.AgentKey(supervisor); }
	//Clone?
	public Agent.AgentKey getSupervisorKey() { return supervisor; }
	public void setSupervisor(Agent.AgentKey supervisor) { this.teamLead = supervisor; }
	
	public Agent(Agent.AgentKey key) {
		super(key);
	}
	
	public String toString() {
		return key.id + ": " + getDisplayName() + ", " + role.getFullName();
	}
	
	public static class AgentKey extends SingleDomainKey<String> {		
		public Class<Agent> getDomainObjectClass() {
			return Agent.class;
		}
		
		public String getID() {
			return id;
		}
		
		public AgentKey(String username) {
			super(username);
		}
	}
	
}