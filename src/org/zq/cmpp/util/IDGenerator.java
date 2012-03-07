package org.zq.cmpp.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class IDGenerator {
	private static final int MAX_ID_VALUE = Integer.MAX_VALUE;

	private static final int STEP = 1;

	private static int BASE_ID = 1;

	private static final Lock LOCK = new ReentrantLock();

	public static int next() {
		LOCK.lock();
		try {
			int next = (BASE_ID + STEP) % MAX_ID_VALUE;
			return next;
		} finally {
			LOCK.unlock();
		}
	}
}
