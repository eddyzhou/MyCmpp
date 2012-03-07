package org.zq.cmpp.util;

/**
 * Cmpp helper class. <br>
 * Generic helper methods for all classes.
 * 
 * @author EddyZhou(zhouqian1103@gmail.com)
 * 
 */
public final class CmppUtils {

	/**
	 * The methods of this class should be used staticly. That is why the
	 * constructor is private.
	 */
	private CmppUtils() {
	}

	/**
	 * Check an object if its not null. If it is a NullPointerException will be
	 * thrown.
	 * 
	 * @param name
	 *            Name of the object, used in the exception message.
	 * @param value
	 *            The object to check.
	 */
	public static void checkNotNull(String name, Object value) {

		if (value == null) {
			throw new NullPointerException("The" + name + " must not be null.");
		}

	}
}
