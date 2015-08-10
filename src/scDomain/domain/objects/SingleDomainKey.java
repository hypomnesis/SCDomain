package scDomain.domain.objects;

public abstract class SingleDomainKey<K> implements DomainKey {
    protected K id;
    
    protected SingleDomainKey() {}
    
    @Override
    public int getFieldCount() {
        return 1;
    }
    public abstract K getID();
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object object) {
        if (this == object ) { return true; }
        if (object == null || object.getClass() != this.getClass() ) { return false; }
        
        return true;
    }
    @Override
    public abstract int hashCode();
}