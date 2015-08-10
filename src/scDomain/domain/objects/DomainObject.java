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
public abstract class DomainObject<O extends DomainObject<O>> {
    protected final DomainKey<O> key;
    
    @SuppressWarnings("unused")
    private DomainObject() { this.key = null; }
    public DomainObject(DomainKey<O> key, DomainBuilder<O> builder) {
        if (key == null || builder == null) {
            throw new NullPointerException();
        }
        this.key = key;
    }
    
    public DomainKey<O> getKey() { return key; }
}