/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.dao;
/**
 *
 * @author Morgan
 */
import scDomain.domain.objects.DomainObject;
import scDomain.domain.objects.DomainKey;
import java.util.WeakHashMap;

final class DomainPool<O extends DomainObject<O>> {
    private final WeakHashMap<DomainKey<O>, O> objectMap = new WeakHashMap<DomainKey<O>, O>();
    
    DomainPool() {}
    
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
    
    @Override
    public boolean equals(Object object) { return this == object; }
    @Override
    public int hashCode() { return objectMap.hashCode(); }
}