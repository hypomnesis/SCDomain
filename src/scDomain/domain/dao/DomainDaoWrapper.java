/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.dao;

import java.util.ArrayList;
import java.util.Collection;
import scDomain.domain.dao.DomainDao;
import scDomain.domain.objects.DomainObject;

/**
 *
 * @author Morgan
 */
abstract class DomainDaoWrapper<O extends DomainObject<O>, K extends DomainObject.Key<O>, B extends DomainObject.Builder<O>>
        implements DomainDao<O, K>
{
    protected final DomainObject.Type.Pool<O> pool;
    
    private DomainDaoWrapper() { throw new AbstractMethodError(); }
    DomainDaoWrapper(DomainDao<O, K> mapper, DomainObject.Type.Pool<O> pool) {
        if (mapper == null || pool == null) {
            throw new NullPointerException();
        }
        this.pool = pool;
    }
    
    @Override
    public O find(K key) {
        if (key == null) { throw new NullPointerException(); }
        O object = pool.get(key);
        
        if (object == null) { object = this.getMapper().find(key); }
        
        return object;
    }
    @Override
    public ArrayList<O> find(Collection<K> keys) {
        if (keys == null || keys.isEmpty()) { throw new NullPointerException(); }
        
        ArrayList<O> objects = new ArrayList<>(keys.size());
        ArrayList<K> notFound = new ArrayList<>(keys.size());
        
        for (K key : keys) {
            O object = pool.get(key);
            
            if (object == null) {
                notFound.add(key);
            } else {
                objects.add(object);
            }
        }
        if (!notFound.isEmpty()) {
            objects.addAll(this.getMapper().find(notFound));
        }
        return objects;
    }
    abstract DomainDao<O, K> getMapper();
    
    static abstract class FindAll<O extends DomainObject<O>, K extends DomainObject.Key<O>, B extends DomainObject.Builder<O>>
            extends DomainDaoWrapper<O, K, B> implements DomainDao.FindAll<O, K>
    {
        private final DomainDao.FindAll<O, K> finder;
        
        FindAll(DomainDao.FindAll<O, K> mapper, DomainObject.Type.Pool<O> pool) {
            super(mapper, pool);
            this.finder = mapper;
        }
        
        @Override
        public ArrayList<O> findAll() {
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