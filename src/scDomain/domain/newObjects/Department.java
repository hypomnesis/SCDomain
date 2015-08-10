/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.newObjects;

/**
 *
 * @author Morgan
 */
public class Department extends DomainObject<Department> {
    private String id;
    private String name;
    private String email;
    private Agent.Key head;
    private String hrPartner;
    
    public static class Key extends StringDomainKey<Department> {		
        public Key(String id) { super(id); }
        @Override
        public Class<Department> getDomainObjectClass() { return Department.class; }
    }
    public static class Builder implements DomainBuilder<Department> {
        private String id;
        private String name;
        private String email = null;
        private Agent.Key head = null;
        private String hrPartner = null;
        
        public Builder id(String id) {
            this.id = id;
            return this;
        }
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        public Builder head(Agent.Key head) {
            this.head = head;
            return this;
        }
        public Builder hrPartner(String hrPartner) {
            this.hrPartner = hrPartner;
            return this;
        }
    }
    
    public Department(Department.Key key, Department.Builder builder) {
        super(key, builder);
        
        id = builder.id;
        name = builder.name;
        email = builder.email;
        head = builder.head;
        hrPartner = builder.hrPartner;
    }
    
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Agent.Key getDepartmentHead() { return head; }
    public String getHrPartner() { return hrPartner; }
    
    @Override
    public String toString() { return name; }
}