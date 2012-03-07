package org.zq.cmpp.message;


public interface CmppRequest extends CmppMessage{
	
	public String[] getParameterNames();
	
	public byte[] getRequestBodyAsBytes();
	
	public byte[] getRequestHeaderAsBytes();
	
}
