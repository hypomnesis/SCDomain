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
abstract class StringDomainKey<O extends AbstractDomainObject<O>> extends SingleDomainKey<String, O> {
    public StringDomainKey(String id) {
        super(id);
        //do i want to do this?
        if (id.length() == 0) { throw new IllegalArgumentException(); }
    }
    StringDomainKey(AbstractDomainObject.Builder<O> builder) { super(builder); }
    
    public String getID() { return id; }
    @Override
    //Super checks parameter type.
    public final boolean equals(Object object) {
        if (!super.equals(object)) { return false; }
        
        return this.id.equals(((StringDomainKey) object).id);
    }
}