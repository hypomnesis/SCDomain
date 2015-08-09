package scDomain.domain.mappers;

import scDomain.domain.objects.*;

public interface DomainMapper<K extends DomainKey, O extends DomainObject<K>> {
	public O find(K key);
	//K update(O object);
}