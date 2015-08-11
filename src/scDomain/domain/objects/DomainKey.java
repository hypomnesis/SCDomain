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
public abstract class DomainKey<O extends DomainObject<O>> {
    private DomainKey() {}
    DomainKey(DomainBuilder<O> builder) {}
    
    /*public abstract Class<O> getDomainObjectClass();
    public abstract int getFieldCount();*/
}