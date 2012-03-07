package org.zq.cmpp.db;


import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * DataSource factory
 * This class can be replace by DBCP or C3P0 to get DataSource if needed
 * 
 * @author EddyZhou(zhouqian1103@gmail.com)
 *
 */
public class DataSourceFactory {
	private static final Log logger = LogFactory.getLog(DataSourceFactory.class);
	
	private static DataSource mysqlDataSource = getDataSource(DBConfig.getDriver(), 
			DBConfig.getUser(),
			DBConfig.getPassword(), 
			DBConfig.getUrl());
	
	/**
	 * default factory method to get DataSource
	 * @return DataSource of mysql
	 */
	public static DataSource getDataSource() {
		return mysqlDataSource;
	}
	
	public static DataSource getDataSource(String driver, String user, String password, String url){
		return new DirectDataSource(driver, user, password, url);
	}
	
	/**
	 * Inner class of DataSource
	 * @author zhouqian(Eddy Zhou)
	 *
	 */
    protected static class DirectDataSource implements DataSource {
    	private String driver;
    	private String user;
    	private String password;
    	private String url;
    	
    	public DirectDataSource(String driver, String user, String password, String url){
    		this.driver = driver;
    		this.user = user;
    		this.password = password;
    		this.url = url;
    		try {
				Class.forName(driver).newInstance();
			} catch (Exception e) {
				final String message = "Caught exception while try to connect to database";
				logger.error(message, e);
				throw new DbException(message, e);
			} 
    	}

		public Connection getConnection() throws SQLException {
			return DriverManager.getConnection(this.url, this.user, this.password);
		}

		public Connection getConnection(String user, String password)
				throws SQLException {
			return DriverManager.getConnection(this.url, user, password);
		}

		public PrintWriter getLogWriter() throws SQLException {
			return new PrintWriter(System.out);
		}

		public int getLoginTimeout() throws SQLException {
			// TODO Auto-generated method stub
			return 0;
		}

		public void setLogWriter(PrintWriter out) throws SQLException {
			// TODO Auto-generated method stub
			
		}

		public void setLoginTimeout(int seconds) throws SQLException {
			// TODO Auto-generated method stub
			
		}
		
		public String toString(){
			return "driver:" + this.driver + ",user:" + this.user + ",password:" + this.password + ",url" + this.url;
		}

		@Override
		public boolean isWrapperFor(Class<?> arg0) throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public <T> T unwrap(Class<T> arg0) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
    	
    }
}
