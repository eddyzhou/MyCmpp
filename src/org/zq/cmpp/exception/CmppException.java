package org.zq.cmpp.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * <p>
 * Base class for exceptions thrown by the CMPP client.
 * </p>
 * 
 * <p>
 * CmppExceptions may contain a reference to another Exception, which was the
 * underlying cause of the CmppException.
 * </p>
 * 
 * @author EddyZhou(zhouqian1103@gmail.com)
 */
public class CmppException extends Exception {

	private static final long serialVersionUID = 843489793284041236L;

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Constants.
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	public static final int ERR_UNSPECIFIED = 0;

	// System Exception Code 99901000 - 99901999
	public static final int BAD_CONFIGURATION_CODE = 99901000;
	public static final int OPERATION_EXCEPTION_CODE = 99901001;

	// Network Exception Code 99902000 - 99902999
	public static final int NETWORK_EXCEPTION_CODE = 99902000;
	public static final int SESSION_TIMEOUT_EXCEPTION_CODE = 99902001;

	public static final int BADVERSION_EXCEPTION_CODE = 1001;

	public static final int MESSAGE_STRUCTURE_EXCEPTION_CODE = 1002;

	public static final int SOURCEADDRESS_EXCEPTION_CODE = 1003;

	public static final int AUTHENTICATOR_EXCEPTION_CODE = 1004;

	public static final int UNKOWN_EXCEPTION_CODE = 1005;

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Data members.
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	private Throwable cause;

	private int errorCode = ERR_UNSPECIFIED;

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Constructors.
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	public CmppException() {
		super();
	}

	public CmppException(String msg) {
		super(msg);
	}

	public CmppException(String msg, int errorCode) {
		super(msg);
		setErrorCode(errorCode);
	}

	public CmppException(Throwable cause) {
		super(cause.toString());
		setCause(cause);
	}

	public CmppException(String msg, Throwable cause) {
		super(msg);
		setCause(cause);
	}

	public CmppException(String msg, Throwable cause, int errorCode) {
		super(msg);
		setCause(cause);
		setErrorCode(errorCode);
	}

	private void setCause(Throwable cause) {
		this.cause = cause;
	}

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Interface.
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	public Throwable getUnderlyingException() {
		return this.cause;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public boolean isSystemError() {
		return (errorCode >= BAD_CONFIGURATION_CODE && errorCode <= BAD_CONFIGURATION_CODE + 999);
	}

	public boolean isNetworkError() {
		return (errorCode >= NETWORK_EXCEPTION_CODE && errorCode <= NETWORK_EXCEPTION_CODE + 999);
	}

	public String toString() {
		Throwable cause = getUnderlyingException();
		if (cause == null || cause == this) {
			return super.toString();
		} else {
			return super.toString() + " [See nested exception: " + cause + "]";
		}
	}

	public void printStackTrace() {
		printStackTrace(System.err);
	}

	public void printStackTrace(PrintStream out) {
		super.printStackTrace(out);

		if (cause != null) {
			synchronized (out) {
				out
						.println("* Nested Exception (Underlying Cause) ---------------");
				cause.printStackTrace(out);
			}
		}
	}

	public void printStackTrace(PrintWriter out) {
		super.printStackTrace(out);

		if (cause != null) {
			synchronized (out) {
				out
						.println("* Nested Exception (Underlying Cause) ---------------");
				cause.printStackTrace(out);
			}
		}
	}

}
