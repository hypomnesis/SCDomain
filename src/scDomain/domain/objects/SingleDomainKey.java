package scDomain.domain.objects;

public abstract class SingleDomainKey<K> implements DomainKey {
	protected K id;
	
	@Override
	public int getFieldCount() {
		return 1;
	}

	public K getID() {
		return id;
	}
	
	public SingleDomainKey(K id) {
		this.id = id;
	}
	
	@SuppressWarnings("unchecked")
	public boolean equals(Object object) {
		if (this == object ) { return true; }
		if (object == null || object.getClass() != this.getClass() ) { return false; }
		
		SingleDomainKey<K> key = (SingleDomainKey<K>) object;
		
		return this.id.equals(key.getID());
	}
}