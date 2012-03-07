package org.zq.cmpp.event;

import org.zq.cmpp.message.CmppMessage;

public class CmppMessageEvent extends java.util.EventObject {
	private static final long serialVersionUID = 1L;

	private CmppMessage message;

	public CmppMessageEvent(Object source, CmppMessage message) {
		super(source);
		this.message = message;
	}

	public Object getCmppMessage() {
		return message;
	}
}
