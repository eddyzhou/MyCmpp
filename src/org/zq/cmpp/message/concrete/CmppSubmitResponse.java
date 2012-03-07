package org.zq.cmpp.message.concrete;

import java.util.Arrays;

import org.zq.cmpp.codec.CodecToolkit;
import org.zq.cmpp.message.AbstractCmppResponse;

public class CmppSubmitResponse extends AbstractCmppResponse {
	
	private static final String MESSAGE_TYPE = "CmppSubmitResponse";

	private long messageId;

	private int result;

	public String getMessageType() {
		return MESSAGE_TYPE;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("totalLength:" + totalLength + "  " + "commandId:"
				+ commandId + "  " + "sequenceId:" + sequenceId);
		sb.append(" body: messageId:" + messageId + "  " + "result:" + result);
		return sb.toString();
	}

	public void parseResponseBody() {
		if (responseBodyAsBytes == null)
			return;
		messageId = CodecToolkit.getCoder(Long.class).decode(
				Arrays.copyOf(responseBodyAsBytes, 8));
		result = CodecToolkit.getCoder(Integer.class).decode(
				Arrays.copyOfRange(responseBodyAsBytes, 8, 12));

	}

	public long getMessageId() {
		return messageId;
	}

	public int getResult() {
		return result;
	}

	@Override
	public int getResponseCode() {
		return result;
	}
}
