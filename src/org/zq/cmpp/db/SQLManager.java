package org.zq.cmpp.db;

import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SQLManager {
	private static final String SUFFIX = "_SQL";

	private ResourceBundle bundle;

	private static final Log log = LogFactory.getLog(SQLManager.class);

	private SQLManager(String packageName) {
		String bundleName = packageName + SUFFIX + ".properties";
		
		try {
			bundle = ResourceBundle.getBundle(bundleName);
			return;
			
		} catch (MissingResourceException ex) {
			// Try from the current loader
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			if (cl != null) {
				try {
					bundle = ResourceBundle.getBundle(bundleName, Locale
							.getDefault(), cl);
					return;
				} catch (MissingResourceException ex2) {
				}
			}
			if (cl == null)
				cl = this.getClass().getClassLoader();

			if (log.isDebugEnabled())
				log.debug("Can't find resource " + bundleName + " " + cl);
			
			if (cl instanceof URLClassLoader) {
				if (log.isDebugEnabled())
					log.debug(((URLClassLoader) cl).getURLs());
			}
		}
	}

	private static final Map<String, SQLManager> managers = new HashMap<String, SQLManager>();

	public synchronized static SQLManager getManager(String packageName) {
		SQLManager gr = managers.get(packageName);
		if (gr == null) {
			gr = new SQLManager(packageName);
			managers.put(packageName, gr);
		}

		return gr;
	}


	public String getSQL(String key) {
		if (key == null) 
			throw new NullPointerException("key is null");

		String str = null;

		if (bundle == null)
			return "";
		try {
			str = bundle.getString(key);
		} catch (MissingResourceException mre) {
			log.warn("Cannot find SQL associated with key '" + key + "'");
			str = "";
		}

		return str;
	}

}
