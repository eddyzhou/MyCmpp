package org.zq.cmpp.event;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.event.EventListenerList;

import org.zq.cmpp.message.CmppMessage;
import org.zq.cmpp.util.CmppUtils;


/**
 * Cmpp event caster<br>
 * Used to publish cmpp events and manage listeners.
 * 
 * @author EddyZhou(zhouqian1103@gmail.com)
 * 
 */
public class CmppMessageEventCaster {

	/** Collection of event listeners. */
	private EventListenerList listeners = new EventListenerList();
	
	/** Thread pooled executor */
    private ExecutorService executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 20, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {
        private final AtomicInteger threadNumber = new AtomicInteger();

        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "CmppMessageReceiver-" + threadNumber.getAndIncrement());
            thread.setDaemon(true);
            return thread;
        }
    });

	/**
	 * add a listener. The listener will be triggered when a event happens.
	 * 
	 * @param listener
	 *            The listener to add.
	 */
	public final void addCmppListener(final CmppMessageListener listener) {
		CmppUtils.checkNotNull("listener", listener);
		listeners.add(CmppMessageListener.class, listener);
	}

	/**
	 * Remove a listener from the collection of listeners. The listener will no
	 * longer be triggered when a event happens.
	 * 
	 * @param listener
	 *            The listener to remove.
	 */
	public final void removeCmppListener(final CmppMessageListener listener) {
		CmppUtils.checkNotNull("listener", listener);
		listeners.remove(CmppMessageListener.class, listener);
	}

	/**
     * Event trigger called when a message is send to ISMG.
     * @param message the message that has been send.
     */
    public void fireMessageSent(final CmppMessage message) {
    	CmppUtils.checkNotNull("CmppMessage", message);
        CmppMessageListener[] fireListeners = listeners.getListeners(CmppMessageListener.class);
        if (fireListeners.length == 0) {
            return;
        }
        CmppMessageEvent event = new CmppMessageEvent(this, message);
        for (CmppMessageListener listener : fireListeners) {
            listener.messageSent(event);
        }
    }
	
	/**
	 * Fire a message received event.
	 * 
	 * @param message
	 *            the message that triggered the event.
	 */
	public final void fireMessageReceived(final CmppMessage message) {
		CmppUtils.checkNotNull("CmppMessage", message);
		executor.execute(new Runnable() {
			public void run() {
				CmppMessageListener[] fireListeners = listeners
						.getListeners(CmppMessageListener.class);
				if (fireListeners.length == 0) {
					return;
				}
				CmppMessageEvent event = new CmppMessageEvent(this,
						message);
				for (int i = fireListeners.length - 1; 0 <= i; i--) {
					fireListeners[i].messageReceived(event);
				}
			}
		});
	}

}
