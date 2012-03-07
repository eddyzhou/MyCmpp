package org.zq.cmpp.db;


import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Database exception
 * SQLException can convert to this exception
 * 
 * @author EddyZhou(zhouqian1103@gmail.com)
 *
 */
public class DbException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	protected Throwable throwable;
	
	public DbException() {
		super();
	}
	
	public DbException(String message) {
		super(message);
	}
	
	public DbException(Throwable cause) {
		this.throwable = cause;
	}
	
	public DbException(String message, Throwable cause) {
		super(message);
		this.throwable = cause;
	}
	
	public void printStackTrace(PrintStream ps) {
		super.printStackTrace(ps);
		if (throwable != null) {
			ps.println("with nested Exception:" + throwable);
			throwable.printStackTrace(ps);
		}
	}
	
	public void printStackTrace(PrintWriter pw) {
		super.printStackTrace(pw);
		if (throwable != null) {
			pw.println("with nested Exception:" + throwable);
			throwable.printStackTrace(pw);
		}
	}
	
	public String toString() {
		if (throwable == null) {
			return super.toString();
		} else {
			return super.toString() + "with nested exception:" + throwable;
		}
	}

	public Throwable getCause() {
		return throwable;
	}
}
