/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.newObjects;

/**
 *
 * @author Morgan
 */
public interface TestInterface<O extends DomainObject<O>> {
    public O get(DomainKey<O> key);
}
