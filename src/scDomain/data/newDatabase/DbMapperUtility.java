/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.data.newDatabase;

import java.sql.Connection;

/**
 *
 * @author Morgan
 */
class DbMapperUtility {
    private final AbstractDbMapper mapper;
    private final Connection connection;
    
    private DbMapperUtility() {
        throw new IllegalAccessError();
    }
    public DbMapperUtility(AbstractDbMapper mapper) {
        this.mapper = mapper;
        this.connection = mapper.getConnection();
    }
    
    
}