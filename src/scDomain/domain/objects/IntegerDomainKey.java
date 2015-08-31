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
abstract class IntegerDomainKey<O extends AbstractDomainObject<O>> extends SingleDomainKey<Integer, O> {
    public IntegerDomainKey(Integer id) { super(id); }
    IntegerDomainKey(AbstractDomainObject.Builder<O> builder) { super(builder); }
    
    public Integer getID() { return id; }
    @Override
    //Super checks parameter type.
    public final boolean equals(Object object) {
        if (!super.equals(object)) { return false; }
        
        return this.id.equals(((IntegerDomainKey) object).id);
    }
}