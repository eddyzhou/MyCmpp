package org.zq.cmpp.configure;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zq.cmpp.util.PropertiesParser;

public class CmppConfiguration {
	private static final Log logger = LogFactory
			.getLog(CmppConfiguration.class);

	/** 默认配文件 */
	private static final String DEFAULT_CONFIG_FILE = System
			.getProperty("mecs_conf_path")
			+ System.getProperty("file.separator") + "config.ini";

	private static CmppConfiguration configure;

	private Properties props;
	private PropertiesParser parser;

	public synchronized static CmppConfiguration getInstance(String fileName) {
		if (configure == null)
			configure = new CmppConfiguration(fileName);
		return configure;
	}

	public static CmppConfiguration getInstance() {
		return getInstance(DEFAULT_CONFIG_FILE);
	}

	public CmppConfiguration() {
		this.init(DEFAULT_CONFIG_FILE);
	}

	public CmppConfiguration(String fileName) {
		this.init(fileName);
	}

	private void init(String fileName) {
		InputStream is = null;
		try {
			is = getInputStream(fileName);
			if (is == null) {
				throw new Exception("Could not open file " + fileName);
			}
			props = new Properties();
			props.load(is);
			parser = new PropertiesParser(props);
		} catch (Exception e) {
			final String s = "Caught exception while loading file " + fileName;
			logger.error(s, e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error("Unable to close input stream", e);
				}
			}
		}
	}

	public String getProperty(String key) {
		return parser.getProperty(key);
	}

	public String getStringProperty(String name) {
		return parser.getStringProperty(name);
	}

	public String getStringProperty(String name, String def) {
		return parser.getStringProperty(name, def);
	}

	public String[] getStringArrayProperty(String name) {
		return parser.getStringArrayProperty(name);
	}

	public String[] getStringArrayProperty(String name, String[] def) {
		return parser.getStringArrayProperty(name, def);
	}

	public boolean getBooleanProperty(String name) {
		return parser.getBooleanProperty(name);
	}

	public boolean getBooleanProperty(String name, boolean def) {
		return parser.getBooleanProperty(name, def);
	}

	public byte getByteProperty(String name) throws NumberFormatException {
		return parser.getByteProperty(name);
	}

	public byte getByteProperty(String name, byte def)
			throws NumberFormatException {
		return parser.getByteProperty(name, def);
	}

	public char getCharProperty(String name) {
		return parser.getCharProperty(name);
	}

	public char getCharProperty(String name, char def) {
		return parser.getCharProperty(name, def);
	}

	public double getDoubleProperty(String name) throws NumberFormatException {
		return parser.getDoubleProperty(name);
	}

	public double getDoubleProperty(String name, double def)
			throws NumberFormatException {
		return parser.getDoubleProperty(name, def);
	}

	public float getFloatProperty(String name) throws NumberFormatException {
		return parser.getFloatProperty(name);
	}

	public float getFloatProperty(String name, float def)
			throws NumberFormatException {
		return parser.getFloatProperty(name, def);
	}

	public int getIntProperty(String name) throws NumberFormatException {
		return parser.getIntProperty(name);
	}

	public int getIntProperty(String name, int def)
			throws NumberFormatException {
		return parser.getIntProperty(name, def);
	}

	public int[] getIntArrayProperty(String name) throws NumberFormatException {
		return parser.getIntArrayProperty(name);
	}

	public int[] getIntArrayProperty(String name, int[] def)
			throws NumberFormatException {
		return parser.getIntArrayProperty(name, def);
	}

	public long getLongProperty(String name) throws NumberFormatException {
		return parser.getLongProperty(name);
	}

	public long getLongProperty(String name, long def)
			throws NumberFormatException {
		return parser.getLongProperty(name, def);
	}

	public short getShortProperty(String name) throws NumberFormatException {
		return parser.getShortProperty(name);
	}

	public short getShortProperty(String name, short def)
			throws NumberFormatException {
		return parser.getShortProperty(name, def);
	}

	public String[] getPropertyGroups(String prefix) {
		return parser.getPropertyGroups(prefix);
	}

	public Properties getPropertyGroup(String prefix) {
		return parser.getPropertyGroup(prefix);
	}

	public Properties getPropertyGroup(String prefix, boolean stripPrefix) {
		return parser.getPropertyGroup(prefix, stripPrefix);
	}

	public Properties getPropertyGroup(String prefix, boolean stripPrefix,
			String[] excludedPrefixes) {
		return parser.getPropertyGroup(prefix, stripPrefix, excludedPrefixes);
	}

	/**
	 * 获取指定文件的输入流
	 * 
	 * @param fileName
	 * @return
	 */
	private InputStream getInputStream(String fileName) {
		InputStream is;

		URL fileUrl = Thread.currentThread().getContextClassLoader()
				.getResource(fileName);

		if (fileUrl != null) {
			try {
				is = fileUrl.openStream();
			} catch (IOException e) {
				throw new IllegalArgumentException("No file '" + fileName
						+ "' found as a resource");
			}

		} else {
			try {
				is = new FileInputStream(fileName);
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException("No file " + fileName
						+ "found as a resource");
			}
		}

		return is;
	}
}
