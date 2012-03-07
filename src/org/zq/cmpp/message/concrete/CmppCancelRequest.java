package org.zq.cmpp.message.concrete;

import org.zq.cmpp.message.AbstractCmppRequest;
import org.zq.cmpp.util.IDGenerator;

public class CmppCancelRequest extends AbstractCmppRequest {

	private static final String MESSAGE_TYPE = "CmppCancelRequest";

	private byte[] messageId;

	public void initDefaults() {
		totalLength = HEADER_LENGTH + 8;
		commandId = CMPP_CANCEL;
		sequenceId = IDGenerator.next();

	}

	public String getMessageType() {
		return MESSAGE_TYPE;
	}

	public byte[] getRequestBodyAsBytes() {
		return messageId;
	}

	public CmppActiveTestResponse build() {
		return new CmppActiveTestResponse();
	}

	public static void main(String[] args) {
	}

	public byte[] getMessageId() {
		return messageId;
	}

	public void setMessageId(byte[] messageId) {
		this.messageId = messageId;
	}

}
