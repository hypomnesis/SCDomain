package scDomain.domain.objects;

public class Agent extends DomainObject<Agent.AgentKey> {
    private String firstName;
    private String lastName;
    private String displayName = null;
    private Department department;
    private Role role;
    private String email;
    //Do I want them to have full on Agent objects as well and lazy load them?
    private AgentKey teamLead;
    private AgentKey supervisor;
    
    public Agent(Agent.AgentKey key) { super(key); }
    
    public String getUsername() { return key.id; }
    
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    
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
    
    public Agent.AgentKey getTeamLead() { return teamLead; }
    public String getTeamLeadId() { return teamLead.id; }
    public void setTeamLead(Agent.AgentKey teamLead) { this.teamLead = teamLead; }
    
    public Agent.AgentKey getSupervisor() { return supervisor; }
    public String getSupervisorId() { return supervisor.id; }
    public void setSupervisor(Agent.AgentKey supervisor) { this.teamLead = supervisor; }
    
    @Override
    public String toString() {
        return key.id + ": " + getDisplayName() + ", " + role.getFullName();
    }
    
    public static class AgentKey extends StringDomainKey {		
        public AgentKey(String username) { super(username); }
        @Override
        public Class<Agent> getDomainObjectClass() { return Agent.class; }
    }	
}