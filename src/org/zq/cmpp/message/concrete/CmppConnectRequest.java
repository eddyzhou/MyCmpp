package org.zq.cmpp.message.concrete;

import java.lang.reflect.Field;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zq.cmpp.annotation.Param;
import org.zq.cmpp.codec.Codec;
import org.zq.cmpp.codec.CodecToolkit;
import org.zq.cmpp.configure.CmppConfiguration;
import org.zq.cmpp.message.AbstractCmppRequest;
import org.zq.cmpp.util.IDGenerator;

public class CmppConnectRequest extends AbstractCmppRequest {

	private static final String MESSAGE_TYPE = "CmppConnectRequest";

	private static final byte[] BYTE_ARRAY = new byte[9];

	private static final Format DATE_FORMAT = new SimpleDateFormat("MMddHHmmss");

	private static final String[] PARAMETER_NAMES = { "sourceAddr",
			"authenticatorSource", "version", "timestamp" };

	@Param(length = 6, encode = true)
	private String sourceAddr;

	@Param(length = 16, encode = true, encrypt = true)
	private String authenticatorSource;

	@Param(length = 1)
	private int version;

	@Param(length = 4, encode = true)
	private int timestamp;

	private String password;

	public CmppConnectRequest() {
		super();
	}

	public void initDefaults() {
		CmppConfiguration config = CmppConfiguration.getInstance();
		
		totalLength = 39;
		commandId = CMPP_CONNECT;
		sequenceId = IDGenerator.next();
		sourceAddr = config.getProperty("cmpp.connection.sourceAddr"); //"960023"
		authenticatorSource = config.getProperty("cmpp.connection.authenticatorSource"); //"960023"
		version = 3;
		Date currentTime = new Date(System.currentTimeMillis());
		timestamp = Integer.parseInt(DATE_FORMAT.format(currentTime));
		password = config.getProperty("cmpp.connection.sharedSecret"); //"960023";

	}

	public String getSourceAddr() {
		return sourceAddr;
	}

	public void setSourceAddr(String sourceAddr) {
		this.sourceAddr = sourceAddr;
	}

	public String getAuthenticatorSource() {
		return authenticatorSource;
	}

	public void setAuthenticatorSource(String authenticatorSource) {
		this.authenticatorSource = authenticatorSource;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
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
				byte[] encodeAsBytes = new byte[1];
				if (cmpp.encode()) {
					@SuppressWarnings("rawtypes")
					Codec coder = CodecToolkit.getCoder(field.getType());
					encodeAsBytes = coder.encode(value);
					if (cmpp.encrypt()) {
						String md5Str = sourceAddr + new String(BYTE_ARRAY)
								+ password + timestamp;
						encodeAsBytes = CodecToolkit.getMd5Encoder().encode(
								md5Str);
					}
				} else {
					encodeAsBytes[0] = (byte) ((Integer) value).intValue();
				}
				byte[] valueAsBytes = new byte[length];
				System.arraycopy(encodeAsBytes, 0, valueAsBytes, 0,
						encodeAsBytes.length);
				System.arraycopy(valueAsBytes, 0, body, position, length);
				position += length;
			} catch (Exception e) {
				// ignore
			}
		}
		return body;
	}

	public String[] getParameterNames() {
		return PARAMETER_NAMES;
	}

	public static void main(String[] args) {
	}
}
