package scDomain.domain.objects;

public final class Agent extends AbstractDomainObject<Agent> {
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String displayName;
    private final Department.Key department;
    private final Role.Key role;
    private final String email;
    //Do I want them to have full on Agent objects as well and lazy load them?
    private final Agent.Key teamLead;
    private final Agent.Key supervisor;
    
    private Agent(Agent.Builder builder) {
        super(builder);
        
        username = builder.username;
        firstName = builder.firstName;
        lastName = builder.lastName;
        department = builder.department;
        role = builder.role;
        email = builder.email;
        teamLead = builder.teamLead;
        supervisor = builder.supervisor;
        displayName = builder.firstName
                    + (firstName.length() > 0 & lastName.length() > 0 ? ' ' : "")
                    + lastName;
    }
    
    public String getUsername() { return username; }
    public Role.Key getRole() { return role; }
    public Department.Key getDepartment() { return department; }
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
        return username + ": " + displayName + ", " + department.getID() + " " + role.getID();
    }
    
    public final static class Key extends StringDomainKey<Agent> {
        public Key(String id) { super(id); }
        Key(Agent.Builder builder) { this(builder.username); }
        @Override
        DomainObject.Type getDomainType() { return DomainObject.Type.AGENT; }
    }
    
    public final static class Builder extends AbstractDomainObject.Builder<Agent> {
        private String username;
        private String firstName;
        private String lastName;
        private Department.Key department;
        private Role.Key role;
        private String email;
        private Agent.Key teamLead;
        private Agent.Key supervisor;
        
        @Override
        DomainObject.Type getDomainType() { return DomainObject.Type.AGENT; }
        @Override
        Agent.Key getKey() { return new Agent.Key(this); }
        @Override
        Agent doGetObject() { return new Agent(this); }
        @Override
        boolean isValid() {
            if (username == null ||
                    firstName == null ||
                    lastName == null ||
                    //department == null ||
                    role== null ||
                    email == null ||
                    teamLead == null ||
                    supervisor == null) {
                return false;
            } else {
                return true;
            }
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
        public Builder department(Department.Key department) {
            this.department = department;
            return this;
        }
        public Builder role(Role.Key role) {
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