/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.data.pools;
/**
 *
 * @author Morgan
 */
import scDomain.domain.newObjects.*;
import java.util.WeakHashMap;

public class NewPool<O extends DomainObject<O>> {
    private WeakHashMap<DomainKey<O>, O> objectMap;
    
    public static NewPool getInstance(NewPool.Type type) {
        return type.pool;
    }
    
    public enum Type {
        AGENT, DEPARTMENT, ROLE;

        //is it okay to make this public?
        public final NewPool<?> pool;

        Type() {
            switch (this) {
                case AGENT:
                    pool = new NewPool<Agent>();
                    break;
                case DEPARTMENT:
                    pool = new NewPool<Department>();
                    break;
                case ROLE:
                    pool = new NewPool<Role>();
                    break;
                default:
                    pool = null;
            }
        }
    }
    
    private NewPool() {}
    
    public O get(DomainKey<O> key) {
        return objectMap.get(key);
    }
    
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
        }
    }
    
    @Override
    public boolean equals(Object object) { return this == object; }
    @Override
    public int hashCode() { return objectMap.hashCode(); }
}