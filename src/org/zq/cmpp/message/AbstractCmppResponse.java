package org.zq.cmpp.message;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.zq.cmpp.codec.CodecToolkit;
import org.zq.cmpp.message.concrete.CmppActiveTestResponse;
import org.zq.cmpp.message.concrete.CmppCancelResponse;
import org.zq.cmpp.message.concrete.CmppConnectResponse;
import org.zq.cmpp.message.concrete.CmppDeliverRequest;
import org.zq.cmpp.message.concrete.CmppSubmitResponse;

public abstract class AbstractCmppResponse extends AbstractCmppMessage
		implements CmppResponse {

	private static final Map<Integer, Class<? extends CmppResponse>> cmppMessageMap = new HashMap<Integer, Class<? extends CmppResponse>>();

	protected byte[] responseHeaderAsBytes;

	protected byte[] responseBodyAsBytes;

	static {
		cmppMessageMap.put(CMPP_CONNECT_RESP, CmppConnectResponse.class);
		cmppMessageMap.put(CMPP_SUBMIT_RESP, CmppSubmitResponse.class);
		cmppMessageMap.put(CMPP_ACTIVE_TEST_RESP, CmppActiveTestResponse.class);
		cmppMessageMap.put(CMPP_CANCEL_RESP, CmppCancelResponse.class);
		cmppMessageMap.put(CMPP_DELIVER, CmppDeliverRequest.class);
	}

	public static CmppResponse get(int commandId) {
		Class<? extends CmppResponse> clazz = cmppMessageMap.get(commandId);
		CmppResponse response = null;
		
		try {
			response = clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return response;
	}

	public void parseResponseHeader() {
		if (this.responseHeaderAsBytes == null)
			return;
		totalLength = CodecToolkit.getCoder(Integer.class).decode(
				Arrays.copyOf(responseHeaderAsBytes, 4));
		commandId = CodecToolkit.getCoder(Integer.class).decode(
				Arrays.copyOfRange(responseHeaderAsBytes, 4, 8));
		sequenceId = CodecToolkit.getCoder(Integer.class).decode(
				Arrays.copyOfRange(responseHeaderAsBytes, 8, 12));

	}

	public void setResponseBodyAsBytes(byte[] responseBodyAsBytes) {
		this.responseBodyAsBytes = responseBodyAsBytes;
	}

	public void setResponseHeaderAsBytes(byte[] responseHeaderAsBytes) {
		this.responseHeaderAsBytes = responseHeaderAsBytes;
	}

	public int getResponseCode() {
		return 0;
	}
}
