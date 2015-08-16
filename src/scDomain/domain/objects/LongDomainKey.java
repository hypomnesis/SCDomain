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
abstract class LongDomainKey<O extends AbstractDomainObject<O>> extends SingleDomainKey<Long, O> {
    LongDomainKey(AbstractDomainObject.Builder<O> builder) { super(builder); }
    LongDomainKey(LongDomainKey<O> key) { super(key); }
    
    public Long getID() { return id; }
    @Override
    //Super checks parameter type.
    public final boolean equals(Object object) {
        if (!super.equals(object)) { return false; }
        
        return this.id.equals(((LongDomainKey) object).id);
    }
}