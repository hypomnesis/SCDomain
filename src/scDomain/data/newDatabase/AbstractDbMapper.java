/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.data.newDatabase;

import java.sql.*;
import scDomain.domain.newObjects.DomainObject;

/**
 *
 * @author Morgan
 */
interface AbstractDbMapper<O extends DomainObject<O>> {
    public Connection getConnection();
    public PreparedStatement findStatement();
    public O doLoad(ResultSet rs);
}