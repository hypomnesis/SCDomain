/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scDomain.domain.dao;

import scDomain.domain.objects.DomainObject;
import scDomain.domain.objects.DomainKey;

/**
 *
 * @author hayes
 */
public interface DomainDaoFindAll<O extends DomainObject<O>, K extends DomainKey<O>> extends DomainDao<O, K> {
    //do i want array?
    public O[] findAll();
}