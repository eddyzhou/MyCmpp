package org.zq.cmpp.message;


public interface CmppMessage {

	public static final int CMPP_CONNECT = 0x00000001;
	public static final int CMPP_TERMINATE = 0x00000002;
	public static final int CMPP_SUBMIT = 0x00000004;
	public static final int CMPP_DELIVER = 0x00000005;
	public static final int CMPP_QUERY = 0x00000006;
	public static final int CMPP_CANCEL = 0x00000007;
	public static final int CMPP_ACTIVE_TEST = 0x00000008;
	public static final int CMPP_CONNECT_RESP = 0x80000001;
	public static final int CMPP_TERMINATE_RESP = 0x80000002;
	public static final int CMPP_SUBMIT_RESP = 0x80000004;
	public static final int CMPP_DELIVER_RESP = 0x80000005;
	public static final int CMPP_QUERY_RESP = 0x80000006;
	public static final int CMPP_CANCEL_RESP = 0x80000007;
	public static final int CMPP_ACTIVE_TEST_RESP = 0x80000008;

	public int getTotalLength();

	public int getCommandId();

	public int getSequenceId();
	
	public String getMessageType();
	
}
