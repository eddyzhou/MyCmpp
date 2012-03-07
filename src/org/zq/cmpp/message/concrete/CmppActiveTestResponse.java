package org.zq.cmpp.message.concrete;

import org.zq.cmpp.message.AbstractCmppResponse;

public class CmppActiveTestResponse extends AbstractCmppResponse {
	
	private static final String MESSAGE_TYPE = "CmppActiveTestResponse";

	private int reserved;

	public String getMessageType() {
		return MESSAGE_TYPE;
	}

	public void parseResponseBody() {
		if (this.responseBodyAsBytes == null)
			return;
		this.reserved = responseBodyAsBytes[0];
	}

	public int getReserved() {
		return reserved;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("totalLength:" + totalLength + "  " + "commandId:"
				+ commandId + "  " + "sequenceId:" + sequenceId);
		sb.append(" body:" + reserved);
		return sb.toString();
	}
}
