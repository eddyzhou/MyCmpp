package org.zq.cmpp;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zq.cmpp.exception.CmppException;
import org.zq.cmpp.i18n.LocalMessages;
import org.zq.cmpp.message.CmppRequest;
import org.zq.cmpp.message.concrete.CmppDeliverRequest;

public class Bootstrap {
	private static final Log logger = LogFactory.getLog(Bootstrap.class);
	
	private static final int INIT_CAPACITY = 1000;

	private final BlockingQueue<CmppRequest> messageQueue = new ArrayBlockingQueue<CmppRequest>(
			INIT_CAPACITY);
	private final BlockingQueue<CmppDeliverRequest> moQueue = new ArrayBlockingQueue<CmppDeliverRequest>(
			INIT_CAPACITY);
	
	public void start() {
		CmppClient cmppClient = new CmppClient(moQueue, messageQueue);
		MoProcessor moPersistenter = new MoProcessor(moQueue);
		MtSender mtSender = new MtSender(messageQueue);
		
		try {
			cmppClient.run();
		} catch (CmppException e) {
			logger.error(LocalMessages.getString("cmppClient.runException"), e);
			return;
		}
		moPersistenter.run();
		mtSender.run();
	}
	
	public static void main(String[] args) {
			
	}
}
