/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.dao;

import scDomain.domain.objects.DomainObject;

/**
 *
 * @author Morgan
 */
abstract class DomainDaoWrapper<O extends DomainObject<O>, K extends DomainObject.Key<O>, B extends DomainObject.Builder<O>>
        implements DomainDao<O, K>
{
    private final DomainDao<O, K> mapper;
    protected final DomainObject.Type.Pool<O> pool;
    
    private DomainDaoWrapper() { throw new AbstractMethodError(); }
    DomainDaoWrapper(DomainDao<O, K> mapper, DomainObject.Type.Pool<O> pool) {
        if (mapper == null || pool == null) {
            throw new NullPointerException();
        }
        this.mapper = mapper;
        this.pool = pool;
    }
    @Override
    public O find(K key) {
        if (key == null) { throw new NullPointerException(); }
        O object = pool.get(key);
        
        if (object == null) { object = mapper.find(key); }
        
        return object;
    }
    DomainDao<O, K> getMapper() { return mapper; }
    //can't think of a way to port the load or doLoad up here to enforce this functionality.
    //I was told to check again after pulling data but before loading.. i forget why.  Look up and document.
    /*@Override
    protected B doFind(K key) { return mapper.doFind(key); }
    @Override
    protected O load(B builder) { return mapper.load(builder); }*/
    
    static abstract class FindAll<O extends DomainObject<O>, K extends DomainObject.Key<O>, B extends DomainObject.Builder<O>>
            extends DomainDaoWrapper<O, K, B> implements DomainDao.FindAll<O, K>
    {
        private final DomainDao.FindAll<O, K> finder;
        
        FindAll(DomainDao.FindAll<O, K> mapper, DomainObject.Type.Pool<O> pool) {
            super(mapper, pool);
            this.finder = mapper;
        }
        
        @Override
        public O[] findAll() {
            return finder.findAll();
        }
        static abstract class Only<O extends DomainObject<O>, K extends DomainObject.Key<O>, B extends DomainObject.Builder<O>>
                extends DomainDaoWrapper.FindAll<O, K, B> implements DomainDao.FindAll<O, K>
        {
            Only(DomainDao.FindAll<O, K> mapper, DomainObject.Type.Pool<O> pool) {
                super(mapper, pool);
            }
            
            @Override
            public O find(K key) {
                if (pool.count() == 0) { findAll(); }
                
                return super.find(key);
            }
        }
    }
    static abstract class Updater<O extends DomainObject<O>, K extends DomainObject.Key<O>, B extends DomainObject.Builder<O>>
            extends DomainDaoWrapper<O, K, B> implements DomainDao.Updater<O, K, B>
    {
        private final DomainDao.Updater<O, K, B> updater;
        
        private Updater() { throw new AbstractMethodError(); }
        Updater(DomainDao.Updater<O, K, B> mapper, DomainObject.Type.Pool<O> pool) {
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