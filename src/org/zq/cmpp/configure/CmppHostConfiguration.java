package org.zq.cmpp.configure;

public class CmppHostConfiguration {

	private String host;

	private int port;

	private int timeout;

	private int connectTimeout;

	public CmppHostConfiguration() {
		CmppConfiguration config = CmppConfiguration.getInstance();
		this.host = config.getProperty("cmpp.connection.host");
		this.port = config.getIntProperty("cmpp.connection.port");
		this.connectTimeout = config
				.getIntProperty("cmpp.connection.connectTimeout");
		this.timeout = config.getIntProperty("cmpp.connnection.timeout");
	}

	public CmppHostConfiguration(String host, int port, int connectTimeout,
			int timeout) {
		this.host = host;
		this.port = port;
		this.connectTimeout = connectTimeout;
		this.timeout = timeout;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
