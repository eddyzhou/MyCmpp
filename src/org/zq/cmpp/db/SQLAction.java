package org.zq.cmpp.db;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

/**
 * This class implements base operation of database
 * Class of dao implement should extend it to use these methods
 * 
 * @author EddyZhou(zhouqian1103@gmail.com)
 *
 */
public class SQLAction {
	private DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public SQLAction(){
		super();
		this.dataSource = DataSourceFactory.getDataSource();
	}
	
	public SQLAction(DataSource dataSource){
		this.setDataSource(dataSource);
	}
	
	protected void fillSQLParamter(PreparedStatement stmt, Object... objs) throws SQLException{
		if (objs == null){
			throw new IllegalArgumentException("NO SQL Paramter");
		}
		for (int i = 0; i < objs.length; i++){
			stmt.setObject(i+1, objs[i]);
		}
	}
	
	public int[] batch(Connection conn, String sql, List<Object[]> paramList) throws SQLException{
		if (paramList == null){
			throw new IllegalArgumentException("NO SQL Paramters");
		}
		
		int[] rows = null;
		PreparedStatement stmt = null;
		try{
			stmt = this.prepare(conn, sql);
			int count = 0;
			for (Object[] objs : paramList){
				this.fillSQLParamter(stmt, objs);
				stmt.addBatch();
				count++;
				if (count == 100){
					stmt.executeBatch();
					count = 0;
				}
			}
			if(count != 0){
				rows = stmt.executeBatch();
			}
		} finally {
			SQLUtil.close(stmt);
		}
		
		return rows;
	}
	
	public int[] batch(String sql, List<Object[]> paramList) throws SQLException{
		Connection conn = this.getConnection();
		try{
			return this.batch(conn, sql, paramList);
		}finally{
			SQLUtil.close(conn);
		}
	}
	
	protected PreparedStatement prepare(Connection conn, String sql) throws SQLException{
		PreparedStatement stmt = null;
    	if (sql.trim().startsWith("{")){
    		stmt = conn.prepareCall(sql);
		}else{
			stmt = conn.prepareStatement(sql);
		}	
    	return stmt;
	}
	
	protected Connection getConnection() throws SQLException{
		if (this.getDataSource() == null){
			throw new SQLException("SQLAction cannot get database connection");
		}
		return this.dataSource.getConnection();
	}
	
	public <T> T queryObject(Connection conn, ResultSetProcessor<T> rsProcessor, String sql, Object... params) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		T result = null;
		try{
			stmt = this.prepare(conn, sql);
			this.fillSQLParamter(stmt, params);
			rs = stmt.executeQuery();
			if (rs.next()){
				result = rsProcessor.process(rs);
			}
		}finally{
			SQLUtil.close(rs);
			SQLUtil.close(stmt);
		}
		return result;
	}
	
	public <T> T queryObject(ResultSetProcessor<T> rsProcessor, String sql, Object... params) throws SQLException{
		Connection conn = this.getConnection();
		try{
			return this.queryObject(conn, rsProcessor, sql, params);
		}finally{
			SQLUtil.close(conn);
		}
	}
	
	public <T> List<T> queryObjects(Connection conn, ResultSetProcessor<T> rsProcessor, String sql, Object... params) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<T> list = new ArrayList<T>();
		try{
			stmt = this.prepare(conn, sql);
			this.fillSQLParamter(stmt, params);
			rs = stmt.executeQuery();
			while(rs.next()){
				list.add(rsProcessor.process(rs));
			}
		}finally{
			SQLUtil.close(rs);
			SQLUtil.close(stmt);
		}
		return list;
	}
	
	public <T> List<T> queryObjects(ResultSetProcessor<T> rsProcessor, String sql, Object... params) throws SQLException {
		Connection conn = this.getConnection();
		try {
			return this.queryObjects(conn, rsProcessor, sql, params);
		} finally {
			SQLUtil.close(conn);
		}
	}
	
	public Map<String, Object> toMap(ResultSet rs) throws SQLException{
		Map<String, Object> map = new HashMap<String, Object>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int cols = rsmd.getColumnCount();
		for (int i = 1; i< cols; i++) {
			map.put(rsmd.getCatalogName(i), rs.getObject(i));
		}
		return map;
	}
	
	public int update(Connection conn, String sql, Object... params) throws SQLException {
		PreparedStatement stmt = null;
	    int rows = 0;
	    try {
	        stmt = this.prepare(conn, sql);
	        fillSQLParamter(stmt, params);
	        rows = stmt.executeUpdate();
	    } catch (SQLException e) {
	    	throw e;
	    } finally {
	        SQLUtil.close(stmt);
	    }
	    return rows;
	}
	
	public int update(String sql, Object... params) throws SQLException {
		Connection conn = this.getConnection();
		try {
			return this.update(conn, sql, params);
		} finally {
			SQLUtil.close(conn);
		}
	}
}
