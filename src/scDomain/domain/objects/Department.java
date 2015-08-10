package scDomain.domain.objects;

public class Department extends DomainObject<Department.DepartmentKey> {
    private String name;
    
    public Department(Department.DepartmentKey key) { super(key); }
    
    public String getDepartmentId() { return key.id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    @Override
    public String toString() { return name; }
    public String getFullName() { return name; }
    
    public static class DepartmentKey extends StringDomainKey {		
        public DepartmentKey(String deptId) { super(deptId); }
        @Override
        public Class<Department> getDomainObjectClass() { return Department.class; }
    }
}