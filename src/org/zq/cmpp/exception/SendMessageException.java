package org.zq.cmpp.exception;

public class SendMessageException extends CmppException {
	private static final long serialVersionUID = 2243735903433885302L;
	
//	public SendMessageException(String msg, Throwable cause, int errorCode) {
//		super(msg, cause, errorCode);
//	}
//	
//	public SendMessageException(String msg, int errorCode) {
//		super(msg, errorCode);
//	}
	
	public SendMessageException(String msg) {
		super(msg);
	}
	
	public SendMessageException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
