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
public interface DomainObject<O extends DomainObject<O>> {
    public Key<O> getKey();
    
    public static interface Key<O extends DomainObject<O>> {}
    public static interface Builder<O extends DomainObject<O>> {
        public O getObject();
    }
    
    public enum Type {
        AGENT, DEPARTMENT, ROLE;
        
        public final Pool pool;
        
        Type() {
            switch (this) {
                case AGENT:
                    pool = new Pool<Agent>();
                    break;
                case DEPARTMENT:
                    pool = new Pool<Department>();
                    break;
                case ROLE:
                    pool = new Pool<Role>();
                    break;
                default:
                    pool = null;
            }
        }
        
        public static final class Pool<O extends DomainObject<O>> {
            private final WeakHashMap<Key<O>, O> objectMap = new WeakHashMap<Key<O>, O>();
            
            private Pool() {}
            
            public O get(Key<O> key) { return objectMap.get(key); }
            public int count() { return objectMap.size(); }
            public boolean containsKey(Key<O> key) {
                return objectMap.containsKey(key);
            }
            public boolean containsObjectKey(O object) {
                return objectMap.containsKey(object.getKey());
            }
            public boolean containsValue(O object) {
                return objectMap.containsValue(object);
            }
            public void put(Key<O> key) {
                if (key == null) { throw new NullPointerException(); }
                if (!objectMap.containsKey(key)) {
                    objectMap.put(key, null);
                }
            }
            public void put(O object) {
                if (object == null) { throw new NullPointerException(); }
                Key<O> key = object.getKey();
                if (key == null) { throw new NullPointerException(); }
                if (!objectMap.containsKey(key)) {
                    objectMap.put(key, object);
                } else if (objectMap.get(key) == null) {
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
}