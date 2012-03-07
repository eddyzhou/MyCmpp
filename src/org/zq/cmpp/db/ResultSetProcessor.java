package org.zq.cmpp.db;


import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface of resultSet processor
 * 
 * @author EddyZhou(zhouqian1103@gmail.com)
 *
 * @param <T>
 */
public interface ResultSetProcessor<T> {
	public T process(ResultSet rs) throws SQLException;
}
