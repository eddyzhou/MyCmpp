package org.zq.cmpp.message;

import org.zq.cmpp.codec.CodecToolkit;

public abstract class AbstractCmppRequest extends AbstractCmppMessage implements
		CmppRequest {

	private static final String[] EMPTY_PARAMETERS = new String[0];

	public AbstractCmppRequest() {
		initDefaults();
	}

	public abstract void initDefaults();

	public byte[] getRequestHeaderAsBytes() {
		byte[] totalLengthAsBytes = CodecToolkit.getIntegerCoder().encode(
				totalLength);
		byte[] commandIdAsBytes = CodecToolkit.getIntegerCoder().encode(
				commandId);
		byte[] sequenceIdAsBytes = CodecToolkit.getIntegerCoder().encode(
				sequenceId);

		byte[] requestHeaderAsBytes = new byte[HEADER_LENGTH];

		System.arraycopy(totalLengthAsBytes, 0, requestHeaderAsBytes, 0, 4);
		System.arraycopy(commandIdAsBytes, 0, requestHeaderAsBytes, 4, 4);
		System.arraycopy(sequenceIdAsBytes, 0, requestHeaderAsBytes, 8, 4);

		return requestHeaderAsBytes;
	}

	public String[] getParameterNames() {
		return EMPTY_PARAMETERS;
	}

	public int getParameterLength(String parameterName) {
		return 0;
	}
}
