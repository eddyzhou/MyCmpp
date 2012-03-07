package org.zq.cmpp.message.concrete;

import java.lang.reflect.Field;

import org.zq.cmpp.annotation.Param;
import org.zq.cmpp.codec.Codec;
import org.zq.cmpp.codec.CodecToolkit;
import org.zq.cmpp.message.AbstractCmppRequest;
import org.zq.cmpp.util.IDGenerator;

public class CmppDeliverResponse extends AbstractCmppRequest {

	private static final String MESSAGE_TYPE = "CmppDeliverResponse";

	private static final String[] PARAMETER_NAMES = { "messageId", "result" };

	@Param(length = 8, encode = true)
	private long messageId = 0;

	@Param(length = 4, encode = true)
	private int result = 0;

	public String getMessageType() {
		return MESSAGE_TYPE;
	}

	public void initDefaults() {
		this.commandId = CMPP_DELIVER_RESP;
		this.totalLength = 24;
		this.sequenceId = IDGenerator.next();
	}

	@SuppressWarnings("unchecked")
	public byte[] getRequestBodyAsBytes() {
		byte[] body = new byte[totalLength - HEADER_LENGTH];
		for (int i = 0, position = 0; i < PARAMETER_NAMES.length; i++) {
			try {
				String name = PARAMETER_NAMES[i];
				Field field = getClass().getDeclaredField(name);
				Object value = field.get(this);
				Param cmpp = field.getAnnotation(Param.class);
				int length = cmpp.length();
				byte[] encodeAsBytes = new byte[length];
				if (cmpp.encode()) {
					@SuppressWarnings("rawtypes")
					Codec coder = CodecToolkit.getCoder(field.getType());
					encodeAsBytes = coder.encode(value);
				}
				System.arraycopy(encodeAsBytes, 0, body, position, length);
				position += length;
			} catch (Exception e) {
				;
			}
		}
		return body;
	}

	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

}
