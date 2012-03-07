package org.zq.cmpp;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.zq.cmpp.db.dao.CmppDao;
import org.zq.cmpp.db.dao.CmppDaoImpl;
import org.zq.cmpp.domain.Mt;
import org.zq.cmpp.message.CmppRequest;
import org.zq.cmpp.message.concrete.CmppSubmitRequest;

public class MtSender {
	private final ExecutorService executor = Executors.newSingleThreadExecutor();
	
	private CmppDao cmppDao = CmppDaoImpl.getInstance();
	
	private final BlockingQueue<CmppRequest> messageQueue;
	
	public MtSender(BlockingQueue<CmppRequest> messageQueue) {
		this.messageQueue = messageQueue;
	}
	
	public void run() {
		executor.execute(new Runnable() {
			public void run() {
				while(true) {
					List<Mt> mts = cmppDao.retrieveMts();
					for (Mt mt : mts) {
						CmppSubmitRequest request = new CmppSubmitRequest();
						request.setMessageContent(mt.getContent());
						request.setDestTerminalId(mt.getMsisdn());
						
						cmppDao.updateMtStatusTo(Mt.Status.Processing);
						
						messageQueue.offer(request);
					}
					
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						;
					}
				}
			}
		});
	}
}
