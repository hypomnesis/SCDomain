package scDomain.data.database;

import java.sql.*;

import scDomain.domain.mappers.DomainMapper;
import scDomain.domain.objects.*;

public abstract class DomainDbMapper <K extends DomainKey, O extends DomainObject<K>> implements DomainMapper<K, O> {
	protected Connection connection = null;
	
	protected abstract PreparedStatement findStatement(K key) throws SQLException;
	protected abstract O doLoad(ResultSet rs) throws SQLException;
	
	//Eventually let it get its own connection.
	public DomainDbMapper(Connection connection) {
		this.connection = connection;
	}
	
	//Need to review exceptions.
	public O find(K key) {
		//Check for already loaded objects here.
		O object = null;
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try {
			findStatement = findStatement(key);
			rs = findStatement.executeQuery();
			rs.next();

			object = load(rs);
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
	
	private O load(ResultSet rs) throws SQLException {
		//The reason for this function is to check Identity Maps and load them if necessary.
		return doLoad(rs);
	}
}