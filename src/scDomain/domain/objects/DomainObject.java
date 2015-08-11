/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.objects;

import java.util.WeakHashMap;

/**
 *
 * @author Morgan
 */
public abstract class DomainObject<O extends DomainObject<O>> {
    //protected final DomainObject.Key<O> key;
    protected final DomainKey<O> key;
    
    @SuppressWarnings("unused")
    private DomainObject() { throw new NullPointerException(); }
    //public DomainObject(DomainObject.Builder<O> builder) {
    public DomainObject(DomainBuilder<O> builder) {
        if (builder == null) { throw new NullPointerException(); }
        if (!builder.isValid()) { throw new IllegalArgumentException(); }
        
        this.key = builder.getKey();
    }
    
    //public DomainObject.Key<O> getKey() { return key; }
    public DomainKey<O> getKey() { return key; }
    
    public enum Type {
        AGENT, DEPARTMENT, ROLE;
        
        private final DomainPool pool;
        
        Type() {
            switch (this) {
                case AGENT:
                    pool = new DomainPool<Agent>();
                    break;
                case DEPARTMENT:
                    pool = new DomainPool<Department>();
                    break;
                case ROLE:
                    pool = new DomainPool<Role>();
                    break;
                default:
                    pool = null;
            }
        }
        
        public static final class DomainPool<O extends DomainObject<O>> {
            private final WeakHashMap<DomainKey<O>, O> objectMap = new WeakHashMap<DomainKey<O>, O>();
            //private final WeakHashMap<K, O> objectMap = new WeakHashMap<K, O>();
            
            private DomainPool() {}
            //Switch between DomainObject.Key and DomainKey
            public O get(DomainKey<O> key) { return objectMap.get(key); }
            public int count() { return objectMap.size(); }
            public boolean containsKey(DomainKey<O> key) {
                return objectMap.containsKey(key);
            }
            public boolean containsObjectKey(O object) {
                return objectMap.containsKey(object.getKey());
            }
            public boolean containsValue(O object) {
                return objectMap.containsValue(object);
            }
            public void put(DomainKey<O> key) {
                if (key == null) { throw new NullPointerException(); }
                if (!objectMap.containsKey(key)) {
                    objectMap.put(key, null);
                }
            }
            public void put(O object) {
                DomainKey<O> key = object.getKey();
                if (key == null) { throw new NullPointerException(); }
                if (!objectMap.containsKey(key)) {
                    objectMap.put(key, object);
                } else if (object != null && objectMap.get(key) == null) {
                    objectMap.put(key, object);
                }
            }
            public void put(O[] objects) {
                for (O object : objects) {
                    put(object);
                }
            }
        }
    }
    /*
    public static abstract class Key<O extends DomainObject<O>, K extends DomainObject.Key<O, K>> {
        private Key() {}
        Key(Builder<O, K> builder) {}
        
        public abstract Class<O> getDomainObjectClass();
        public abstract int getFieldCount();
    }
    
    public static abstract class Builder<O extends DomainObject<O>, K extends DomainObject.Key<O, K>> {
        abstract K getKey();
        abstract boolean isValid();
    }*/
}