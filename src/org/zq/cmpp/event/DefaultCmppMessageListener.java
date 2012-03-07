package org.zq.cmpp.event;

import java.util.concurrent.BlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zq.cmpp.message.CmppRequest;
import org.zq.cmpp.message.concrete.CmppDeliverRequest;
import org.zq.cmpp.message.concrete.CmppDeliverResponse;

public class DefaultCmppMessageListener implements CmppMessageListener {

	private static final Log logger = LogFactory.getLog(DefaultCmppMessageListener.class);

	private final BlockingQueue<CmppDeliverRequest> moQueue;
	private final BlockingQueue<CmppRequest> messageQueue;

	public DefaultCmppMessageListener(BlockingQueue<CmppDeliverRequest> moQueue, BlockingQueue<CmppRequest> messageQueue) {
		this.moQueue = moQueue;
		this.messageQueue = messageQueue;
	}

	@Override
	public void messageReceived(CmppMessageEvent event) {
		if (event.getCmppMessage() instanceof CmppDeliverRequest) {
			CmppDeliverRequest request = (CmppDeliverRequest) event
					.getCmppMessage();
			CmppDeliverResponse response = new CmppDeliverResponse();
			response.setMessageId(request.getMessageId());
			response.setResult(0);
			moQueue.add(request);
			messageQueue.add(response);
		}

		logger.info(Thread.currentThread().getName()
				+ "--Received message ........." + event.getCmppMessage());

	}

	@Override
	public void messageSent(CmppMessageEvent event) {
		// TODO Auto-generated method stub

	}

}
