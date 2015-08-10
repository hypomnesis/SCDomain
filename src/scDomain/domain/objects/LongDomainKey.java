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
public abstract class LongDomainKey extends SingleDomainKey<Long> {
    private int hashCode;
    
    private LongDomainKey() {}
    public LongDomainKey(Long id) { this.id = id; }
    @Override
    public Long getID() { return id; }
    @Override
    //Super checks parameter type.
    public boolean equals(Object object) {
        if (!super.equals(object)) { return false; }
        
        LongDomainKey key = (LongDomainKey) object;
        
        return id.equals(key.id);
    }
    @Override
    public int hashCode() {
        if (hashCode == 0) {
            hashCode = (31 * ((31 * 17) + id.hashCode())) + getDomainObjectClass().hashCode();
        }
        return hashCode;
    }
}