package org.zq.cmpp.message.concrete;

import java.lang.reflect.Field;

import org.zq.cmpp.annotation.Param;
import org.zq.cmpp.codec.Codec;
import org.zq.cmpp.codec.CodecToolkit;
import org.zq.cmpp.message.AbstractCmppResponse;

public final class CmppDeliverRequest extends AbstractCmppResponse {

	private static final String MESSAGE_TYPE = "CmppDeliverRequest";

	@Param(length = 8, decode = true)
	private long messageId;

	@Param(length = 21, decode = true)
	private String destnationId;

	@Param(length = 10, decode = true)
	private String serviceId;

	@Param(length = 1)
	private int tppid;

	@Param(length = 1)
	private int tpudhi;

	@Param(length = 1)
	private int messageFormat;

	@Param(length = 32, decode = true)
	private String srcTerminalId;

	@Param(length = 1)
	private int srcTerminalType;

	@Param(length = 1)
	private int registeredDelivery;

	@Param(length = 1)
	private int messageLength;

	@Param(dynamic = true, decode = true)
	private String messageContent;

	@Param(length = 20, decode = true)
	private String linkId;

	public long getMessageId() {
		return messageId;
	}

	public String getDestnationId() {
		return destnationId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public int getTppid() {
		return tppid;
	}

	public int getTpudhi() {
		return tpudhi;
	}

	public int getMessageFormat() {
		return messageFormat;
	}

	public String getSrcTerminalId() {
		return srcTerminalId;
	}

	public int getSrcTerminalType() {
		return srcTerminalType;
	}

	public int getRegisteredDelivery() {
		return registeredDelivery;
	}

	public int getMessageLength() {
		return messageLength;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public String getLinkId() {
		return linkId;
	}

	public String getMessageType() {
		return MESSAGE_TYPE;
	}

	public String toString() {
		return this.messageId + ":" + ":" + this.messageLength + ":"
				+ messageContent;
	}

	public void parseResponseBody() {
		if (responseBodyAsBytes == null)
			return;

		Field[] fields = getClass().getDeclaredFields();
		try {
			for (int i = 0, position = 0; i < fields.length; i++) {
				Param cmpp = fields[i].getAnnotation(Param.class);
				int length = cmpp.length();
				if (cmpp.dynamic()) {
					messageLength = messageLength < 0 ? messageLength & 0xff
							: messageLength;
					length = messageLength;
				}
				byte[] data = java.util.Arrays.copyOfRange(responseBodyAsBytes,
						position, position + length);
				position += length;
				@SuppressWarnings("rawtypes")
				Codec coder = CodecToolkit.getCoder(fields[i].getType());
				Object value = null;
				if (cmpp.decode())
					value = coder.decode(data);
				else
					value = (int) data[0];
				fields[i].set(this, value);
			}
		} catch (IllegalArgumentException e) {
			;
		} catch (IllegalAccessException e) {
			;
		}

	}

	public static void main(String[] args) throws Exception {
	}
}
