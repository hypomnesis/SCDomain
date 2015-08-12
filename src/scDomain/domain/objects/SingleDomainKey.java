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
abstract class SingleDomainKey<T, O extends AbstractDomainObject<O>> extends AbstractDomainObject.Key<O> {
    protected T id;
    
    public SingleDomainKey(T id) {
        if (id == null) { throw new NullPointerException(); }
        this.id = id;
    }
    SingleDomainKey(AbstractDomainObject.Builder<O> builder) { super(builder); }
    
    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) { return false; }
        if ( !(object instanceof SingleDomainKey)) { return false; }
        
        return this.id.getClass() == ((SingleDomainKey) object).id.getClass();
    }
    @Override
    public final int hashCode() {
        if (hashCode == 0) {
            hashCode += (31 * super.hashCode()) + id.hashCode();
        }
        return hashCode;
    }
}