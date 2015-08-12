package scDomain.domain.objects;

public final class Agent extends AbstractDomainObject<Agent> {
    private String username;
    private String firstName;
    private String lastName;
    private String displayName;
    private Department department;
    private Role role;
    private String email;
    //Do I want them to have full on Agent objects as well and lazy load them?
    private Agent.Key teamLead;
    private Agent.Key supervisor;
    
    private Agent(Agent.Builder builder) { super(builder); }
    
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
    
    public final static class Key extends StringDomainKey<Agent> {
        Key(Agent.Builder builder) {
            super(builder);
            if (builder.username == null) { throw new NullPointerException(); }
            
            id = builder.username;
        }
        @Override
        Class<Agent> getDomainClass() { return Agent.class; }
    }
    
    public final static class Builder extends AbstractDomainObject.Builder<Agent> {
        private String username;
        private String firstName;
        private String lastName;
        private Department department;
        private Role role;
        private String email;
        private Agent.Key teamLead;
        private Agent.Key supervisor;
        
        @Override
        DomainObject.Type getType() { return DomainObject.Type.AGENT; }
        @Override
        Agent.Key getKey() { return new Agent.Key(this); }
        @Override
        Agent doGetObject() {
            Agent agent = new Agent(this);
            
            agent.username = username;
            agent.firstName = firstName;
            agent.lastName = lastName;
            agent.department = department;
            agent.role = role;
            agent.email = email;
            agent.teamLead = teamLead;
            agent.supervisor = supervisor;
            agent.displayName = firstName
                        + (firstName.length() > 0 & lastName.length() > 0 ? ' ' : "")
                        + lastName;
            return agent;
        }
        @Override
        boolean isValid() {
            //TODO
            return true;
        }
        
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
}