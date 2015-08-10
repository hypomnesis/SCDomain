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
import scDomain.domain.newObjects.Role;

public interface RoleMapper extends DomainMapper<Role, Role.Key> {
        @Override
	public Role find(Role.Key key);
}