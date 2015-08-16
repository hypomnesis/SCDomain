/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.objects;

/**
 *
 * @author Morgan
 */
public final class Department extends AbstractDomainObject<Department> {
    private String id;
    private String name;
    private String email;
    private Agent.Key head;
    private String hrPartner;
    
    private Department(Department.Builder builder) { super(builder); }
    
    public String getId() { return id; }
    public String getName() { return name; }
    public String email() { return email; }
    public Agent.Key getDeptHead() { return head; }
    public String hrPartner() { return hrPartner; }
    
    @Override
    public String toString() { return name; }
    
    public static final class Key extends StringDomainKey<Department> {
        Key(Department.Builder builder) { 
            super(builder);
            if (builder.id == null) { throw new NullPointerException(); }
            
            id = builder.id;
        }
        @Override
        public DomainObject.Type getDomainType() { return DomainObject.Type.DEPARTMENT; }
    }
    public static final class Builder extends AbstractDomainObject.Builder<Department> {
        private String id;
        private String name;
        private String email = null;
        private Agent.Key head = null;
        private String hrPartner = null;
        
        @Override
        DomainObject.Type getDomainType() { return DomainObject.Type.DEPARTMENT; }
        @Override
        Department.Key getKey() { return new Department.Key(this); }
        @Override
        Department doGetObject() {
            Department dept = new Department(this);
            
            dept.id = id;
            dept.name = name;
            dept.email = email;
            dept.head = head;
            dept.hrPartner = hrPartner;
            
            return dept;
        }
        @Override
        boolean isValid() {
            //TODO
            return true;
        }
        
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
}