package org.zq.cmpp.i18n;

import java.util.ResourceBundle;

public class LocalMessages extends AbstractMessages {

	private static final String BUNDLE_NAME = "org/zq/cmpp/i18n/message";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private LocalMessages() {
	}

	public static String getString(String key) {
		return getString(key, RESOURCE_BUNDLE);
	}

	public static String getString(String key, Object... args) {
		return getString(key, RESOURCE_BUNDLE, args);
	}

	public static void main(String args[]) {
		String str = LocalMessages.getString("CmppSubmitResponse.9");
		System.out.println(str);
	}
}
