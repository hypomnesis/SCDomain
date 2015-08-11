/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.dao;

import scDomain.domain.objects.*;

/**
 *
 * @author Morgan
 */
public abstract class DomainAbstractDao<O extends DomainObject<O>, K extends DomainKey<O>, B extends DomainBuilder<O>>
        implements DomainDao<O, K>
{
    public abstract O find(K key);
    protected abstract B doFind(K key);
    protected abstract O load(B builder);
}