package org.zq.cmpp.message.concrete;

import java.lang.reflect.Field;

import org.zq.cmpp.annotation.Param;
import org.zq.cmpp.codec.Codec;
import org.zq.cmpp.codec.CodecToolkit;
import org.zq.cmpp.message.AbstractCmppRequest;

public final class CmppSubmitRequest extends AbstractCmppRequest {

	private static final String MESSAGE_TYPE = "CmppSubmitRequest";

	private static final int NO_CONTENT_BODY = 183;

	private static final byte[] DEFAULT_BYTES = new byte[1];

	private static final String[] PARAMETER_NAMES = { "messageId", "pkTotal",
			"pkNumber", "registeredDelivery", "messageLevel", "serviceId",
			"feeUserType", "feeterminalid", "feeTerminalType", "tppid",
			"tpudhi", "messageFormat", "messageSource", "feeType", "feeCode",
			"validTime", "atTime", "srcTerminalId", "destUserCount",
			"destTerminalId", "destTerminalType", "messageLength",
			"messageContent", "linkId" };

	@Param(length = 8, encode = true)
	private long messageId;

	@Param(length = 1)
	private int pkTotal;

	@Param(length = 1)
	private int pkNumber;

	@Param(length = 1)
	private int registeredDelivery;

	@Param(length = 1)
	private int messageLevel;

	@Param(length = 10, encode = true)
	private String serviceId;

	@Param(length = 1)
	private int feeUserType;

	@Param(length = 32, encode = true)
	private String feeterminalid;

	@Param(length = 1)
	private int feeTerminalType;

	@Param(length = 1)
	private int tppid;

	@Param(length = 1)
	private int tpudhi;

	@Param(length = 1)
	private int messageFormat;

	@Param(length = 6, encode = true)
	private String messageSource;

	@Param(length = 2, encode = true)
	private String feeType;

	@Param(length = 6, encode = true)
	private String feeCode;

	@Param(length = 17, encode = true)
	private String validTime;

	@Param(length = 17, encode = true)
	private String atTime;

	@Param(length = 21, encode = true)
	private String srcTerminalId;

	@Param(length = 1)
	private int destUserCount = 1;

	@Param(length = 32, encode = true)
	private String destTerminalId;

	@Param(length = 1)
	private int destTerminalType;

	@Param(length = 1)
	private int messageLength;

	@Param(encode = true, dynamic = true)
	private String messageContent;

	@Param(length = 20, encode = true)
	private String linkId;

	public void initDefaults() {
		totalLength = 219;
		commandId = CMPP_SUBMIT;
		sequenceId = 186;
		messageId = 1000;
		pkTotal = 0;
		pkNumber = 0;
		registeredDelivery = 0;
		messageLevel = 1;
		serviceId = "960023";
		feeUserType = 3;
		feeterminalid = "8613823389186";
		feeTerminalType = 0;
		tppid = 0;
		tpudhi = 0;
		messageFormat = 15;
		messageSource = "960023";
		feeType = "01";
		feeCode = "000000";
		validTime = "";
		atTime = "";
		srcTerminalId = "1862010058";
		destUserCount = 1;
		destTerminalId = "8613823389186";
		destTerminalType = 0;
		messageLength = 0;
		messageContent = "";
		linkId = "123";
	}

	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public int getPkTotal() {
		return pkTotal;
	}

	public void setPkTotal(int pkTotal) {
		this.pkTotal = pkTotal;
	}

	public int getPkNumber() {
		return pkNumber;
	}

	public void setPkNumber(int pkNumber) {
		this.pkNumber = pkNumber;
	}

	public int getRegisteredDelivery() {
		return registeredDelivery;
	}

	public void setRegisteredDelivery(int registeredDelivery) {
		this.registeredDelivery = registeredDelivery;
	}

	public int getMessageLevel() {
		return messageLevel;
	}

	public void setMessageLevel(int messageLevel) {
		this.messageLevel = messageLevel;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public int getFeeUserType() {
		return feeUserType;
	}

	public void setFeeUserType(int feeUserType) {
		this.feeUserType = feeUserType;
	}

	public String getFeeterminalid() {
		return feeterminalid;
	}

	public void setFeeterminalid(String feeterminalid) {
		this.feeterminalid = feeterminalid;
	}

	public int getFeeTerminalType() {
		return feeTerminalType;
	}

	public void setFeeTerminalType(int feeTerminalType) {
		this.feeTerminalType = feeTerminalType;
	}

	public int getTppid() {
		return tppid;
	}

	public void setTppid(int tppid) {
		this.tppid = tppid;
	}

	public int getTpudhi() {
		return tpudhi;
	}

	public void setTpudhi(int tpudhi) {
		this.tpudhi = tpudhi;
	}

	public int getMessageFormat() {
		return messageFormat;
	}

	public void setMessageFormat(int messageFormat) {
		this.messageFormat = messageFormat;
	}

	public String getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(String messageSource) {
		this.messageSource = messageSource;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

	public String getValidTime() {
		return validTime;
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	public String getAtTime() {
		return atTime;
	}

	public void setAtTime(String atTime) {
		this.atTime = atTime;
	}

	public String getSrcTerminalId() {
		return srcTerminalId;
	}

	public void setSrcTerminalId(String srcTerminalId) {
		this.srcTerminalId = srcTerminalId;
	}

	public int getDestUserCount() {
		return destUserCount;
	}

	public void setDestUserCount(int destUserCount) {
		this.destUserCount = destUserCount;
	}

	public String getDestTerminalId() {
		return destTerminalId;
	}

	public void setDestTerminalId(String destTerminalId) {
		this.destTerminalId = destTerminalId;
	}

	public int getDestTerminalType() {
		return destTerminalType;
	}

	public void setDestTerminalType(int destTerminalType) {
		this.destTerminalType = destTerminalType;
	}

	public int getMessageLength() {
		return messageLength;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		if (messageContent == null)
			messageContent = "";
		int msgLength = messageContent.getBytes().length;
		messageLength = msgLength;
		totalLength = NO_CONTENT_BODY + HEADER_LENGTH + msgLength;
		this.messageContent = messageContent;
	}

	public String getLinkId() {
		return linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	@Override
	public String[] getParameterNames() {
		return PARAMETER_NAMES;
	}

	public String getMessageType() {
		return MESSAGE_TYPE;
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
				byte[] encodeAsBytes = DEFAULT_BYTES;
				if (cmpp.encode()) {
					@SuppressWarnings("rawtypes")
					Codec coder = CodecToolkit.getCoder(field.getType());
					encodeAsBytes = coder.encode(value);
				} else {
					encodeAsBytes[0] = (byte) ((Integer) value).intValue();
				}
				if (cmpp.dynamic())
					length = encodeAsBytes.length;
				byte[] valueAsBytes = new byte[length];
				System.arraycopy(encodeAsBytes, 0, valueAsBytes, 0,
						encodeAsBytes.length);
				System.arraycopy(valueAsBytes, 0, body, position, length);
				position += length;
			} catch (Exception e) {
				;
			}
		}
		return body;
	}

	public String toString() {
		return messageContent;
	}

	public static void main(String[] args) throws Exception {
		CmppSubmitRequest request = new CmppSubmitRequest();
		System.out.println(request.getTotalLength());
		request.setMessageContent("中华人民共和国");
		System.out.println(request.getMessageLength());
		request.setMessageContent("");
		System.out.println(request.getTotalLength());
	}
}
