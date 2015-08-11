package scDomain.domain.objects;

//public abstract class SingleDomainKey<T, O extends DomainObject<O>> extends DomainObject.Key<O> {
public abstract class SingleDomainKey<T, O extends DomainObject<O>> extends DomainKey<O> {
    protected T id;
    
    //SingleDomainKey(DomainObject.Builder<O> builder) { super(builder); }
    SingleDomainKey(DomainBuilder<O> builder) { super(builder); }
    
    //@Override
    public int getFieldCount() {
        return 1;
    }
    public abstract T getID();
    
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