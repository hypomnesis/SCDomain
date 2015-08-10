package scDomain.domain.objects;

public class Role extends DomainObject<Role.RoleKey> {
    private String name;
    private short level;
    private boolean onScorecard;
    
    public Role(Role.RoleKey key) { super(key); }
    
    public String getRoleId() { return key.id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public short getLevel() { return level; }
    public void setLevel(short level) { this.level = level; }
    
    public boolean getOnScorecard() { return onScorecard; }
    public void setOnScorecard(boolean onScorecard) { this.onScorecard = onScorecard; }
    
    @Override
    public String toString() { return name; }
    public String getFullName() { return name + " (" + level + ")"; }
    
    public static class RoleKey extends StringDomainKey {		
        public RoleKey(String roleId) { super(roleId); }
        @Override
        public Class<Role> getDomainObjectClass() { return Role.class; }
    }
}