/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.newMappers;

/**
 *
 * @author Morgan
 */
import scDomain.domain.newObjects.*;

public interface DomainMapper<O extends DomainObject<O>, K extends DomainKey<O>> {
	public O find(K key);
}