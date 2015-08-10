/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.data.pools;

import scDomain.domain.objects.*;
import java.util.WeakHashMap;

/**
 *
 * @author Morgan
 */
public class DomainPool<K extends DomainKey, O extends DomainObject<K>> {
    private WeakHashMap<K, O> objectMap;
    
    public static DomainPool getInstance(DomainPool.Type type) {
        return type.pool;
    }
    
    public enum Type {
        AGENT, DEPARTMENT, ROLE;

        //is it okay to make this public?
        private final DomainPool<?, ?> pool;

        Type() {
            switch (this) {
                case AGENT:
                    pool = new DomainPool<Agent.AgentKey, Agent>();
                    break;
                case DEPARTMENT:
                    pool = new DomainPool<Department.DepartmentKey, Department>();
                    break;
                case ROLE:
                    pool = new DomainPool<Role.RoleKey, Role>();
                    break;
                default:
                    pool = null;
            }
        }
    }
    
    private DomainPool() {}
    
    public O get(K key) {
        return objectMap.get(key);
    }
    
    public boolean containsKey(K key) {
        return objectMap.containsKey(key);
    }
    public boolean containsObjectKey(O object) {
        return objectMap.containsKey(object.getKey());
    }
    public boolean containsValue(O object) {
        return objectMap.containsValue(object);
    }
    public void put(K key) {
        if (key == null) { throw new NullPointerException(); }
        if (!objectMap.containsKey(key)) {
            objectMap.put(key, null);
        }
    }
    public void put(O object) {
        K key = object.getKey();
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