package org.zq.cmpp.exception;

public class ConnectException extends CmppException {
	private static final long serialVersionUID = -8413715979049073156L;

//	public ConnectException(String msg, Throwable cause, int errorCode) {
//		super(msg, cause, errorCode);
//	}
//	
//	public ConnectException(String msg, int errorCode) {
//		super(msg, errorCode);
//	}
	
	public ConnectException(String msg) {
		super(msg);
	}
	
	public ConnectException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
