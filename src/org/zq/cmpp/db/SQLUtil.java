package org.zq.cmpp.db;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

/**
 * Utility class of database operation
 * 
 * @author EddyZhou(zhouqian1103@gmail.com)
 *
 */
public class SQLUtil {
	public static void close(Connection conn) throws SQLException {
		if (conn != null){
			conn.close();
		}
	}
	public static void close(ResultSet rs) throws SQLException  {
		if (rs != null){
			rs.close();
		}
	}
	public static void close(Statement stmt) throws SQLException  {
		if (stmt != null){
			stmt.close();
		}
	}

	public static void close(Connection conn, Statement stmt,ResultSet rs) throws SQLException {
		close(rs);
		close(stmt);
		close(conn);
	}

	public static void commit(Connection conn) throws SQLException {
		if (conn != null){
			conn.commit();
		}
	}

	public static void commitAndClose(Connection conn) throws SQLException  {
		 try {
			if (conn != null)conn.commit();
		} finally {
			close(conn);
		}
	}

	public static void rollback(Connection conn) throws SQLException {
		if (conn != null){
			conn.rollback();
		}
	}
	
	public static void rollback(Connection conn,Savepoint savepoint) throws SQLException {
		if (conn != null){
			conn.rollback(savepoint);
		}
	}

	public static void rollbackAndClose(Connection conn) throws SQLException {
		try {
			if (conn != null) conn.rollback();
		} finally {
			close(conn);
		}
	}
}
