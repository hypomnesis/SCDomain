package scDomain.domain.objects;

public interface DomainKey {
    public Class<? extends DomainObject<?>> getDomainObjectClass();
    public int getFieldCount();
}