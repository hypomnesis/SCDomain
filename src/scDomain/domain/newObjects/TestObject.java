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
public class TestObject extends DomainObject<TestObject> {
    public static class Builder implements DomainBuilder<TestObject> {
    
    }
    public static class Key extends StringDomainKey<TestObject> {
        public Class<TestObject> getDomainObjectClass() { return TestObject.class; }
        public Key(String id) { super(id); }
    }
    public TestObject(Key key, Builder builder) { super(key, builder); }
}