/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.dao;

import java.util.ArrayList;
import java.util.Collection;
import scDomain.domain.objects.DomainObject;

/**
 *
 * @author Morgan
 * @param <O>
 * @param <K>
 */
public interface DomainDao <O extends DomainObject<O>, K extends DomainObject.Key<O>> {
    public O find(K key);
    public ArrayList<O> find(Collection<K> key);
    
    interface FindAll <O extends DomainObject<O>, K extends DomainObject.Key<O>> extends DomainDao<O, K> {
        public ArrayList<O> findAll();
    }
    
    interface Updater <O extends DomainObject<O>, K extends DomainObject.Key<O>, B extends DomainObject.Builder<O>>
            extends DomainDao<O, K>
    {
        public O add(O object);
        public O add(B builder);
        public O update(O object);
        public O update(K key, B builder);
        public void delete(K key);
        public void delete(O object);
    }
}