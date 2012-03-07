package org.zq.cmpp.message.concrete;

import org.zq.cmpp.message.AbstractCmppResponse;

public class CmppCancelResponse extends AbstractCmppResponse {

	private static final String MESSAGE_TYPE = "CmppCancelResponse";

	private int successId;

	public String getMessageType() {
		return MESSAGE_TYPE;
	}

	public void parseResponseBody() {
		if (this.responseBodyAsBytes == null)
			return;
		this.successId = responseBodyAsBytes[3];
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("totalLength:" + totalLength + "  " + "commandId:"
				+ commandId + "  " + "sequenceId:" + sequenceId);
		sb.append(" body:" + successId);
		return sb.toString();
	}

	public int getSuccessId() {
		return successId;
	}
}
