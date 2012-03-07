package org.zq.cmpp.message.concrete;

import org.zq.cmpp.message.AbstractCmppRequest;

public class CmppActiveTestRequest extends AbstractCmppRequest {

	private static final String MESSAGE_TYPE = "CmppActiveTestRequest";
	
	public void initDefaults() {
		totalLength = HEADER_LENGTH;
		commandId = CMPP_ACTIVE_TEST;
		sequenceId = 0;
	}

	public String getMessageType() {
		return MESSAGE_TYPE;
	}

	public byte[] getRequestBodyAsBytes() {
		return new byte[0];
	}

}
