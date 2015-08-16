/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.commands;

import scDomain.domain.dao.*;
import scDomain.domain.objects.DomainObject;

/**
 *
 * @author Morgan
 * @param <O>
 * @param <K>
 */
public abstract class DomainCommand<O extends DomainObject<O>, K extends DomainObject.Key<O>> {
    protected final DomainDaoProvider provider = DomainDaoFactory.INSTANCE;
    
    abstract DomainDao<O, K> getDao();
    
    public O find(K key) {
        DomainDao<O, K> dao = getDao();
        O object = getDao().find(key);
        provider.close();
        
        return object;
    }
}