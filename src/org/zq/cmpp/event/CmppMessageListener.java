package org.zq.cmpp.event;


public interface CmppMessageListener extends java.util.EventListener {

	public void messageSent(CmppMessageEvent event);

    public void messageReceived(CmppMessageEvent event);
}
