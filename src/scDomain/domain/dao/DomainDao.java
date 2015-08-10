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

public interface DomainDao<O extends DomainObject<O>, K extends DomainKey<O>> {
	public O find(K key);
}