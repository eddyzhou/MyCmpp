package org.zq.cmpp;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zq.cmpp.codec.CodecToolkit;
import org.zq.cmpp.configure.CmppHostConfiguration;
import org.zq.cmpp.event.CmppMessageEventCaster;
import org.zq.cmpp.event.DefaultCmppMessageListener;
import org.zq.cmpp.exception.CmppException;
import org.zq.cmpp.exception.ConnectException;
import org.zq.cmpp.i18n.LocalMessages;
import org.zq.cmpp.message.AbstractCmppResponse;
import org.zq.cmpp.message.CmppRequest;
import org.zq.cmpp.message.CmppResponse;
import org.zq.cmpp.message.concrete.CmppActiveTestRequest;
import org.zq.cmpp.message.concrete.CmppConnectRequest;
import org.zq.cmpp.message.concrete.CmppConnectResponse;
import org.zq.cmpp.message.concrete.CmppDeliverRequest;
import org.zq.cmpp.message.concrete.CmppConnectResponse.Status;

public class CmppClient {
	private static final Log logger = LogFactory.getLog(CmppClient.class);

	private final BlockingQueue<CmppDeliverRequest> moQueue;
	private final BlockingQueue<CmppRequest> messageQueue;

	private CmppConnection connection;
	private CmppHostConfiguration config;
	private CmppMessageEventCaster eventCaster = new CmppMessageEventCaster();
	private long lastExecuteTimeMillis = 0;
	private final ScheduledExecutorService sendExecutor;
	private final ScheduledExecutorService receiveExecutor;
	private final ScheduledExecutorService activeExecutor;
	private final ReentrantLock writeLock = new ReentrantLock();
	private final ReentrantLock readLock = new ReentrantLock();

	/** Boolean to check if the cmpp client is already runed. */
	private volatile boolean isRun = false;

	/** Boolean to check if the cmpp client is attached ISMG. */
	private volatile boolean isAttached = false;

	private void initListeners() {
		eventCaster.addCmppListener(new DefaultCmppMessageListener(moQueue, messageQueue));
	}

	private void initSentWorkers() throws CmppException {
		if (!isAttached)
			throw new ConnectException(LocalMessages
					.getString("cmppClient.notAttached"));

		sendExecutor.execute(new Runnable() {
			public void run() {
				while (connection.isConnected()) {
					try {
						CmppRequest request = messageQueue.take();

						if (logger.isDebugEnabled())
							logger.debug("CmppRequest: " + request);

						byte[] head = request.getRequestHeaderAsBytes();
						byte[] body = request.getRequestBodyAsBytes();
						try {
							writeLock.lock();
							connection.sendData(head);
							connection.sendData(body);
							connection.flush();
							lastExecuteTimeMillis = System
									.currentTimeMillis();
						} catch (CmppException e) {
							logger.error(LocalMessages
									.getString("exception.sendMessage"), e);
						} finally {
							writeLock.unlock();
						}
						
						eventCaster.fireMessageSent(request);
					} catch (InterruptedException e) {
						// ignore
					}
				}
			}
		});
	}

	private void initReceiveWorkers() throws CmppException {
		if (!isAttached)
			throw new ConnectException(LocalMessages
					.getString("cmppClient.notAttached"));

		receiveExecutor.execute(new Runnable() {
			public void run() {
				while (connection.isConnected()) {
					byte[] responseHeaderAsBytes = new byte[12];
					byte[] responseBodyAsBytes = new byte[0];
					int commandId = 0;
					int totalLength = 0;
					int wantLen = 0;
					CmppResponse response = null;
					try {
						readLock.lock();
						connection.receiveData(responseHeaderAsBytes);
						totalLength = CodecToolkit.getIntegerCoder()
								.decode(
										Arrays.copyOf(
												responseHeaderAsBytes, 4));
						wantLen = totalLength
								- AbstractCmppResponse.HEADER_LENGTH;
						responseBodyAsBytes = new byte[wantLen];
						connection.receiveData(responseBodyAsBytes);
					} catch (CmppException ex) {
						logger.error(LocalMessages
								.getString("exception.receiveMessage"), ex);
					} finally {
						readLock.unlock();

					}
					commandId = CodecToolkit.getIntegerCoder()
							.decode(
									Arrays.copyOfRange(
											responseHeaderAsBytes, 4, 8));
					response = AbstractCmppResponse.get(commandId);
					response
							.setResponseHeaderAsBytes(responseHeaderAsBytes);
					response.parseResponseHeader();
					response.setResponseBodyAsBytes(responseBodyAsBytes);
					response.parseResponseBody();
					
					eventCaster.fireMessageReceived(response);
				}
			}
		});
	}

	private void initActiveWorker() throws CmppException {
		if (!isAttached)
			throw new ConnectException(LocalMessages
					.getString("cmppClient.notAttached"));

		activeExecutor.execute(new Runnable() {
			public void run() {
				while (connection.isConnected()) {
					try {
						long currentTimeMillis = System.currentTimeMillis();
						if (currentTimeMillis - lastExecuteTimeMillis >= 3000) {
							messageQueue.offer(new CmppActiveTestRequest());
						}
						Thread.sleep(100);
					} catch (InterruptedException e) {
						;
					}
				}
			}
		});
	}

	private void connect() throws CmppException {
		if (connection != null && !connection.isConnected()) {
			connection.connect();

			CmppRequest request = new CmppConnectRequest();
			connection.sendData(request.getRequestHeaderAsBytes());
			connection.sendData(request.getRequestBodyAsBytes());
			connection.flush();

			byte[] responseHeaderAsBytes = new byte[CmppConnectResponse.HEADER_LENGTH];
			connection.receiveData(responseHeaderAsBytes);

			CmppConnectResponse response = new CmppConnectResponse();
			response.setResponseHeaderAsBytes(responseHeaderAsBytes);
			response.parseResponseHeader();

			int totalLength = response.getTotalLength();
			int wantLen = totalLength - AbstractCmppResponse.HEADER_LENGTH;
			byte[] responseBodyAsBytes = new byte[wantLen];
			connection.receiveData(responseBodyAsBytes);
			response.setResponseBodyAsBytes(responseBodyAsBytes);
			response.parseResponseBody();

			Status status = response.getAttachStatus();
			if (status == Status.ATTACHED)
				this.isAttached = true;
			else {
				logger.fatal(LocalMessages.getString("cmppClient.notAttached",
						status.getDescription()));
			}
		}
	}

	public synchronized void run() throws CmppException {
		if (isRun)
			throw new IllegalStateException(LocalMessages
					.getString("cmppClient.initialized"));

		connect();
		initListeners();
		initActiveWorker();
		initSentWorkers();
		initReceiveWorkers();

		isRun = true;

	}

	public synchronized void disconnect() throws CmppException {
		if (!isRun)
			return;

		if (connection != null && connection.isConnected())
			connection.disconnect();
		if (activeExecutor != null && !activeExecutor.isShutdown())
			activeExecutor.shutdownNow();
		if (sendExecutor != null && !sendExecutor.isShutdown())
			sendExecutor.shutdownNow();
		if (receiveExecutor != null && !receiveExecutor.isShutdown())
			receiveExecutor.shutdownNow();

		isRun = false;
	}

	public CmppClient(BlockingQueue<CmppDeliverRequest> moQueue,
			BlockingQueue<CmppRequest> messageQueue) {
		this(moQueue, messageQueue, new CmppHostConfiguration());
	}

	public CmppClient(BlockingQueue<CmppDeliverRequest> moQueue,
			BlockingQueue<CmppRequest> messageQueue,
			CmppHostConfiguration config) {
		this.moQueue = moQueue;
		this.messageQueue = messageQueue;
		this.config = config;
		this.activeExecutor = Executors.newSingleThreadScheduledExecutor();
		this.sendExecutor = Executors.newScheduledThreadPool(1);
		this.receiveExecutor = Executors.newScheduledThreadPool(1);
		this.connection = new SocketConnection(config.getHost(), config
				.getPort());
	}

//	private void sentCommand() throws InterruptedException {
//		for (int i = 0; i < 1; i++) {
//			CmppSubmitRequest request = new CmppSubmitRequest();
//			request.setMessageContent("ÖÐºÍ");
//			Thread.sleep(2000);
//			messageQueue.offer(request);
//		}
//
//	}

	public CmppHostConfiguration getConfig() {
		return config;
	}

	public static void main(String[] args) throws Exception {
//		CmppClient cmppClient = new CmppClient();
//		cmppClient.connect();
//		cmppClient.sentCommand();
	}

}
