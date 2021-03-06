/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.objects;

/**
 *
 * @author Morgan
 * @param <O>
 */
abstract class AbstractDomainObject<O extends DomainObject<O>> implements DomainObject<O> {
    private final AbstractDomainObject.Key<O> key;
    
    @SuppressWarnings("unused")
    private AbstractDomainObject() { throw new NullPointerException(); }
    AbstractDomainObject(Builder<O> builder) {
        if (builder == null) { throw new NullPointerException(); }
        
        key = builder.getKey();
    }
    
    @Override
    public final DomainObject.Key<O> getKey() { return key; }
    @Override
    public abstract String toString();
    
    static abstract class Key<O extends DomainObject<O>> implements DomainObject.Key<O> {
        protected int hashCode;
        
        Key() {}
        Key(Builder<O> builder) {
            if (builder == null) { throw new NullPointerException(); }
        }
        
        abstract DomainObject.Type getDomainType();
        @Override
        public boolean equals(Object object) {
            if (object == null || !(object instanceof Key)) { return false; }

            Key key = (Key) object;

            return (this.getDomainType() == key.getDomainType());
        }
        @Override
        public int hashCode() {
            if (hashCode == 0) {
                hashCode = (31 * 17) + this.getDomainType().hashCode();
            }
            return hashCode;
        }
    }
    
    static abstract class Builder<O extends DomainObject<O>> implements DomainObject.Builder<O> {
        abstract AbstractDomainObject.Key<O> getKey();
        abstract DomainObject.Type getDomainType();
        abstract boolean isValid();
        
        /*
        This function 
        */
        @Override
        public O getObject() {
            if (!this.isValid()) { throw new IllegalStateException(); }
            
            DomainObject.Type.Pool<O> pool = this.getDomainType().pool;
            DomainObject.Key<O> key = this.getKey();
            
            if(pool == null || key == null) {
                throw new IllegalStateException();
            }
            O object = pool.get(this.getKey());
            
            if (object != null) { return object; }
            
            object = this.doGetObject();
            pool.put(object);
            
            return object;
        }
        abstract O doGetObject();
    }
}