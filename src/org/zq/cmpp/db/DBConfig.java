package org.zq.cmpp.db;

import org.zq.cmpp.configure.CmppConfiguration;




/**
 * Database Configure parameters
 * 
 * @author EddyZhou(zhouqian1103@gmail.com)
 *
 */
public class DBConfig {
	public static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	public static String JDBC_URL;
	public static String JDBC_USER;
	public static String JDBC_PASSWORD;
	
	static {
		init();
	}
	
	public static void init() {
		CmppConfiguration config = CmppConfiguration.getInstance();
		JDBC_USER = config.getProperty("jdbc.user");
		JDBC_PASSWORD = config.getProperty("jdbc.password");
		JDBC_URL = config.getProperty("jdbc.url");
		
	}
	
	public static void reload() {
		init();
	}
	
	public static String getUser(){
		return JDBC_USER;
	}
	
	public static String getPassword(){
		return JDBC_PASSWORD;
	}
	
	public static String getUrl(){
		return JDBC_URL;
	}
	
	public static String getDriver() {
		return JDBC_DRIVER;
	}
}
