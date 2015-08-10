/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.dao;

import scDomain.domain.objects.DomainObject;
import scDomain.domain.objects.DomainBuilder;
import scDomain.domain.objects.DomainKey;

/**
 *
 * @author Morgan
 */
abstract class DomainDaoWrapper<O extends DomainObject<O>, K extends DomainKey<O>> implements DomainDao<O, K> {
    private final DomainDao<O, K> mapper;
    protected final DomainPool<O> pool;
    
    private DomainDaoWrapper() { throw new AbstractMethodError(); }
    DomainDaoWrapper(DomainDao<O, K> mapper, DomainPool<O> pool) {
        this.mapper = mapper;
        this.pool = pool;
    }
    @Override
    public final O find(K key) {
        O object = pool.get(key);
        
        if (object == null) { object = mapper.find(key); }
        
        return object;
    }
    //can't think of a way to port the load or doLoad up here to enforce this functionality.
    //I was told to check again after pulling data but before loading.. i forget why.  Look up and document.
    protected final O loadCheck(K key) { return pool.get(key); }
    
    static abstract class FindAll<O extends DomainObject<O>, K extends DomainKey<O>> extends DomainDaoWrapper<O, K> implements DomainDaoFindAll<O, K> {
        private final DomainDaoFindAll<O, K> finder;
        
        FindAll(DomainDaoFindAll<O, K> mapper, DomainPool<O> pool) {
            super(mapper, pool);
            this.finder = mapper;
        }
        
        @Override
        public O[] findAll() {
            return finder.findAll();
        }
    }
    static abstract class Updater<O extends DomainObject<O>, K extends DomainKey<O>, B extends DomainBuilder<O>>
            extends DomainDaoWrapper<O, K> implements DomainDaoUpdater<O, K, B>
    {
        private final DomainDaoUpdater<O, K, B> updater;
        
        private Updater() { throw new AbstractMethodError(); }
        Updater(DomainDaoUpdater<O, K, B> mapper, DomainPool<O> pool) {
            super(mapper, pool);
            this.updater = mapper;
        }
        
        @Override
        public O add(O object) {
            //pool stuff here.
            return updater.add(object);
        }
        @Override
        public O add(B builder) {
            //pool stuff here.
            return updater.add(builder);
        }
        @Override
        public O update(O object) {
            //pool stuff here.
            return updater.update(object);
        }
        @Override
        public O update(K key, B builder) {
            //pool stuff here.
            return updater.update(key, builder);
        }
        @Override
        public void delete(K key) {
            //pool stuff here.
            updater.delete(key);
        }
        @Override
        public void delete(O object) {
            //pool stuff here.
            updater.delete(object);
        }
    }
}