package scDomain.domain.mappers;

import scDomain.domain.objects.Role;

public interface RoleMapper extends DomainMapper<Role.RoleKey, Role> {
	public Role find(Role.RoleKey key);
}