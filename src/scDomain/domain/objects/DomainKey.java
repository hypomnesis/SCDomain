/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.objects;

/**
 *
 * @author Morgan
 */
public interface DomainKey<O extends DomainObject<O>> {
    public Class<O> getDomainObjectClass();
    public int getFieldCount();
}