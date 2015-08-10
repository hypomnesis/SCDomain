package scDomain.domain.objects;

public abstract class DomainObject<K extends DomainKey> {
    protected K key;
    
    @SuppressWarnings("unused")
    private DomainObject() {}
    public DomainObject(K key) {
        //Should probably Clone this key.
        this.key = key;
    }
    
    public K getKey() { return key; }
}