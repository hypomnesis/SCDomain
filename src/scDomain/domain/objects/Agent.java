package scDomain.domain.objects;

public class Agent extends DomainObject<Agent> {
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String displayName;
    private final Department department;
    private final Role role;
    private final String email;
    //Do I want them to have full on Agent objects as well and lazy load them?
    private final Agent.Key teamLead;
    private final Agent.Key supervisor;
        
    public static class Key extends StringDomainKey<Agent> {
        Key(Agent.Builder builder) { 
            super(builder);
            id = builder.username;
        }
    }
    
    //public static class Builder extends DomainObject.Builder<Agent> {
    public static class Builder extends DomainBuilder<Agent> {
        private String username;
        private String firstName;
        private String lastName;
        private Department department;
        private Role role;
        private String email;
        private Agent.Key teamLead;
        private Agent.Key supervisor;
        
        boolean isValid() {
            //TODO
            return true;
        }
        
        Agent.Key getKey() { return new Agent.Key(this); }
        public Builder username(String username) {
            this.username = username;
            return this;
        }
        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        public Builder department(Department department) {
            this.department = department;
            return this;
        }
        public Builder role(Role role) {
            this.role = role;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        public Builder teamLead(Agent.Key teamLead) {
            this.teamLead = teamLead;
            return this;
        }
        public Builder supervisor(Agent.Key supervisor) {
            this.supervisor = supervisor;
            return this;
        }
    }
    
    public Agent(Agent.Builder builder) {
        super(builder);
        
        username = builder.username;
        firstName = builder.firstName;
        lastName = builder.lastName;
        department = builder.department;
        role = builder.role;
        email = builder.email;
        teamLead = builder.teamLead;
        supervisor = builder.supervisor;
        displayName = firstName
                    + (firstName.length() > 0 & lastName.length() > 0 ? ' ' : "")
                    + lastName;
    }
    
    public String getUsername() { return username; }
    
    public Role getRole() { return role; }
    public Department getDepartment() { return department; }
    public String getFirstName() { return firstName; }
    public String getLastName() {return lastName; }
    public String getDisplayName() { return displayName; }
    public String getEmail() { return email; }
    public Agent.Key getTeamLead() { return teamLead; }
    public String getTeamLeadId() { return teamLead.id; }
    public Agent.Key getSupervisor() { return supervisor; }
    public String getSupervisorId() { return supervisor.id; }
    
    @Override
    public String toString() {
        return username + ": " + getDisplayName() + ", " + role.getFullName();
    }
    
}