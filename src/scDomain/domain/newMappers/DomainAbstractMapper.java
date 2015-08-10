/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.newMappers;

import scDomain.data.pools.NewPool;
import scDomain.domain.newObjects.DomainKey;
import scDomain.domain.newObjects.DomainObject;

/**
 *
 * @author Morgan
 */
public abstract class DomainAbstractMapper<O extends DomainObject<O>, K extends DomainKey<O>> {
    protected final NewPool<O> newPool;
    
    private DomainAbstractMapper() {
        throw new AbstractMethodError();
    }
    protected DomainAbstractMapper(NewPool<O> newPool) {
        this.newPool = newPool;
    }
    //can't think of a way to port the load or doLoad up here to enforce this functionality.
    protected abstract O doFind(K key);
    public final O find(K key) {
        O object = newPool.get(key);
        
        if (object == null) { object = doFind(key); }
        
        return object;
    }
}