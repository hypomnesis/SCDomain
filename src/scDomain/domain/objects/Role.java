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
    private final String id;
    private final String name;
    private final short level;
    private final boolean onScorecard;
    private final String fullName;
    
    private Role(Role.Builder builder) {
        super(builder);
        
        id = builder.id;
        name = builder.name;
        level = builder.level;
        onScorecard = builder.onScorecard;
        fullName = name + " (" + level + ")";
    }
    
    public String getId() { return id; }
    public String getName() { return name; }
    public short getLevel() { return level; }
    public boolean getOnScorecard() { return onScorecard; }
    public String getFullName() { return fullName; }
    
    @Override
    public String toString() { return name; }
    
    public static final class Key extends StringDomainKey<Role> implements DomainObject.Key<Role> {
        public Key(String id) { super(id); }
        Key(Role.Builder builder) { this(builder.id); }
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
        public Role doGetObject() { return new Role(this); }
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