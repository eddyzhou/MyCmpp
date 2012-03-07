package org.zq.cmpp.message.concrete;

import java.util.Arrays;

import org.zq.cmpp.codec.CodecToolkit;
import org.zq.cmpp.i18n.LocalMessages;
import org.zq.cmpp.message.AbstractCmppResponse;


public class CmppConnectResponse extends AbstractCmppResponse {

	private static final String MESSAGE_TYPE = "CmppConnectResponse";

	private int status;
	private int version;
	private String authenticatorISMG = "";

	public String getMessageType() {
		return MESSAGE_TYPE;
	}

	public void parseResponseBody() {
		if (this.responseBodyAsBytes == null)
			return;
		this.status = CodecToolkit.getCoder(Integer.class).decode(
				Arrays.copyOf(responseBodyAsBytes, 4));
		this.authenticatorISMG = CodecToolkit.getCoder(String.class).decode(
				Arrays.copyOfRange(responseBodyAsBytes, 4, 20));
		this.version = responseBodyAsBytes[20];
	}

	public int getStatus() {
		return status;
	}

	public int getVersion() {
		return version;
	}

	public String getAuthenticatorISMG() {
		return authenticatorISMG;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("totalLength:" + totalLength + "  " + "commandId:"
				+ commandId + "  " + "sequenceId:" + sequenceId);
		sb.append(" body: status:" + status + "  " + "authenticatorISMG:"
				+ authenticatorISMG + "    version:" + version);
		return sb.toString();
	}

	@Override
	public int getResponseCode() {
		return status;
	}
	
	public Status getAttachStatus() {
		return Status.getStatus(this.status);
	}
	
	/**
	 * Enumeration of the cmpp client status.
	 */
	public static enum Status {
		ATTACHED(0, LocalMessages.getString("CmppConnectResponse.0")),
		BADVERSION(1, LocalMessages.getString("CmppConnectResponse.1")), 
		WRONG_MESSAGE_STRUCTURE(2, LocalMessages.getString("CmppConnectResponse.2")), 
		ILLEGAL_SOURCEADDRESS(3, LocalMessages.getString("CmppConnectResponse.3")), 
		AUTHENTICATOR_WRONG(4, LocalMessages.getString("CmppConnectResponse.4")), 
		UNKOWN_PROBLEM(5, LocalMessages.getString("CmppConnectResponse.5"));
		
		private int status;
		private String description;
		
		private Status(int status, String description) {
			this.status = status;
			this.description = description;
		}

		public int getStatus() {
			return status;
		}

		public String getDescription() {
			return description;
		}

		public static Status getStatus(int status) {
			for (Status s : Status.values()) {
				if (s.getStatus() == status) {
					return s;
				}
			}

			return UNKOWN_PROBLEM;
		}
	}
}
