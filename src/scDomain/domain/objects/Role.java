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
public class Role extends DomainObject<Role> {
    private String id;
    private String name;
    private short level;
    private boolean onScorecard;
    
    public static class Key extends StringDomainKey<Role> {		
        public Key(String roleId) { super(roleId); }
        @Override
        public Class<Role> getDomainObjectClass() { return Role.class; }
    }
    public static class Builder extends DomainObject.Builder<Role> {
        private String id;
        private String name;
        private short level;
        private boolean onScorecard;
        
        Role.Key getKey() { return new Role.Key(id); }
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
    
    public Role(Role.Builder builder) {
        super(builder);
        
        id = builder.id;
        name = builder.name;
        level = builder.level;
        onScorecard = builder.onScorecard;
    }
    
    public String getRoleId() { return id; }
    public String getName() { return name; }
    public short getLevel() { return level; }
    public boolean getOnScorecard() { return onScorecard; }
    
    @Override
    public String toString() { return name; }
    public String getFullName() { return name + " (" + level + ")"; }
}