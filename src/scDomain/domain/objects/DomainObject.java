/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.objects;

import java.util.ArrayList;
import java.util.WeakHashMap;

/**
 *
 * @author Morgan
 * @param <O>
 */
public interface DomainObject<O extends DomainObject<O>> {
    public Key<O> getKey();
    
    public static interface Key<O extends DomainObject<O>> {}
    public static interface Builder<O extends DomainObject<O>> {
        public O getObject();
    }
    
    public static enum Type {
        AGENT(new Pool<Agent>(false)),
        DEPARTMENT(new Pool<Department>(true)),
        ROLE(new Pool<Role>(true)),
        TIME_SLOT(new Pool<TimeSlot>(false));
        
        private static final ArrayList<DomainObject> permPool = new ArrayList<>(50);
        
        public final Pool pool;
        
        private <O extends DomainObject<O>> Type(Pool<O> pool) { this.pool = pool; }
        
        public static final class Pool<O extends DomainObject<O>> {
            private final WeakHashMap<Key<O>, O> objectMap = new WeakHashMap<>();
            private final boolean permanent;
            
            private Pool(boolean permanent) {
                this.permanent = permanent;
            }
            
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
            public void put(O object) {
                if (object == null) { throw new NullPointerException(); }
                Key<O> key = object.getKey();
                if (key == null) { throw new NullPointerException(); }
                if (!objectMap.containsKey(key)) {
                    objectMap.put(key, object);
                } else if (objectMap.get(key) == null) {
                    objectMap.put(key, object);
                }
                if (permanent && !permPool.contains(object)) {
                    permPool.add(object);
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