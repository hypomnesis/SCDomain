/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.data.newDatabase;

/**
 *
 * @author Morgan
 */
import java.sql.*;
import scDomain.data.pools.NewPool;
import scDomain.domain.newMappers.DomainAbstractMapper;
import scDomain.domain.newObjects.*;

public abstract class DomainDbMapper <O extends DomainObject<O>, K extends DomainKey<O>> extends DomainAbstractMapper<O, K> {
	protected final Connection connection;
	
	protected abstract PreparedStatement findStatement(K key) throws SQLException;
	protected abstract O doLoad(ResultSet rs) throws SQLException;
	
	//Eventually let it get its own connection?
	protected DomainDbMapper(Connection connection, NewPool<O> newPool) {
            super(newPool);
            this.connection = connection;
	}
	
	//Need to review exceptions.
	public O doFind(K key) {
                O object = super.find(key);
                
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try {
			findStatement = findStatement(key);
			rs = findStatement.executeQuery();
			rs.next();

			object = load(key, rs);
			//clean resources der I ferget how. I want to put this in finally block.
			//also, I should close this with an object clean up in case more db actions are required...
			//or do i keep them alive?  who knows?
			findStatement.close();
			connection.close();		//Don't want to close connection pool connection.  Work with datasource?
		} catch (SQLException e) {
			//Better solution needed.
			e.printStackTrace();
		}
		return object;
	}
	
	private O load(K key, ResultSet rs) throws SQLException {
                //I was told to check again.. i forget why.  Look up and document.
                O object = newPool.get(key);
                
                if (object != null) { return null; }
                
		return doLoad(rs);
	}
}