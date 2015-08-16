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
public final class Role extends AbstractDomainObject<Role> implements DomainObject<Role> {
    private String id;
    private String name;
    private short level;
    private boolean onScorecard;
    private String fullName;  //name + " (" + level + ")"
    
    private Role(Role.Builder builder) { super(builder); }
    
    public String getId() { return id; }
    public String getName() { return name; }
    public short getLevel() { return level; }
    public boolean getOnScorecard() { return onScorecard; }
    public String getFullName() { return fullName; }
    
    @Override
    public String toString() { return name; }
    
    public static final class Key extends StringDomainKey<Role> implements DomainObject.Key<Role> {
        public Key(String id) { super(id); }
        Key(Role.Builder builder) { 
            super(builder);
            if (builder.id == null) { throw new NullPointerException(); }
            
            id = builder.id;
        }
        @Override
        public DomainObject.Type getDomainType() { return DomainObject.Type.ROLE; }
    }
    
    public static final class Builder extends AbstractDomainObject.Builder<Role> implements DomainObject.Builder<Role> {
        private String id;
        private String name;
        private short level;
        private boolean onScorecard;
        
        @Override
        DomainObject.Type getDomainType() { return DomainObject.Type.ROLE; }
        @Override
        Role.Key getKey() { return new Role.Key(this); }
        @Override
        public Role doGetObject() {
            Role role = new Role(this);
            
            role.id = id;
            role.name = name;
            role.level = level;
            role.onScorecard = onScorecard;
            
            return role;
        }
        @Override
        boolean isValid() {
            //TODO!!!
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
        public Builder level(short level) {
            this.level = level;
            return this;
        }
        public Builder onScorecard(boolean onScorecard) {
            this.onScorecard = onScorecard;
            return this;
        }
    }
}