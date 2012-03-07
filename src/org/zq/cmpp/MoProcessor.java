package org.zq.cmpp;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.zq.cmpp.message.concrete.CmppDeliverRequest;

public class MoProcessor {
	private final ExecutorService executor = Executors.newSingleThreadExecutor();
	
	private final BlockingQueue<CmppDeliverRequest> moQueue;
	
	public MoProcessor(BlockingQueue<CmppDeliverRequest> moQueue) {
		this.moQueue = moQueue;
	}
	
	public void run() {
		executor.execute(new Runnable() {
			public void run() {
				while(true) {
					try {
						CmppDeliverRequest request = moQueue.take();
						
					} catch (InterruptedException e) {
						;
					}
				}
			}
		});
	}
}
