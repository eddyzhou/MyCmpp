package org.zq.cmpp.message;

public interface CmppResponse extends CmppMessage {

	public void parseResponseBody();
	
	public void setResponseBodyAsBytes(byte[] responseBodyAsBytes);

	public void setResponseHeaderAsBytes(byte[] responseHeaderAsBytes);
	
	public void parseResponseHeader();
	
	public int getResponseCode();
	
}
