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
public abstract class StringDomainKey extends SingleDomainKey<String> {
    private int hashCode;
    
    private StringDomainKey() {}
    public StringDomainKey(String id) { this.id = id; }
    @Override
    public String getID() { return id; }
    @Override
    //Super checks parameter type.
    public boolean equals(Object object) {
        if (!super.equals(object)) { return false; }
        
        StringDomainKey key = (StringDomainKey) object;
        
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