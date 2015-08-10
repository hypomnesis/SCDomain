/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scDomain.domain.dao;

/**
 *
 * @author hayes
 */
import scDomain.domain.objects.DomainObject;
import scDomain.domain.objects.DomainBuilder;
import scDomain.domain.objects.DomainKey;

public interface DomainDaoUpdater<O extends DomainObject<O>, K extends DomainKey<O>, B extends DomainBuilder<O>> extends DomainDao<O, K> {
    public O add(O object);
    public O add(B builder);
    public O update(O object);
    public O update(K key, B builder);
    public void delete(K key);
    public void delete(O object);
}