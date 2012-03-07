package org.zq.cmpp.i18n;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public abstract class AbstractMessages {

	private static final String KEY_NOT_FOUND_PREFIX = "!!!";

	private static final String KEY_NOT_FOUND_SUFFIX = "!!!";

	public static String getString(String key, ResourceBundle resourceBundle) {
		if (resourceBundle == null)
			return KEY_NOT_FOUND_PREFIX + key + KEY_NOT_FOUND_SUFFIX;
		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			return KEY_NOT_FOUND_PREFIX + key + KEY_NOT_FOUND_SUFFIX;
		}
	}

	public static String getString(String key, ResourceBundle resourceBundle,
			Object... args) {
		return MessageFormat.format(getString(key, resourceBundle), args);
	}
}
