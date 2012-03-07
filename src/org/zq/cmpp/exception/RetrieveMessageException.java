package org.zq.cmpp.exception;

public class RetrieveMessageException extends CmppException {

	private static final long serialVersionUID = 8849778410958480909L;
	
//	public RetrieveMessageException(String msg, Throwable cause, int errorCode) {
//		super(msg, cause, errorCode);
//	}
//	
//	public RetrieveMessageException(String msg, int errorCode) {
//		super(msg, errorCode);
//	}
	
	public RetrieveMessageException(String msg) {
		super(msg);
	}
	
	public RetrieveMessageException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
