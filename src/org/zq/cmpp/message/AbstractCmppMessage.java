package org.zq.cmpp.message;

public abstract class AbstractCmppMessage implements CmppMessage {

	public static final int HEADER_LENGTH = 12;
	
	protected int commandId;
	protected int totalLength;
	protected int sequenceId;

	public int getCommandId() {
		return commandId;
	}

	public int getSequenceId() {
		return sequenceId;
	}

	public int getTotalLength() {
		return totalLength;
	}

}
