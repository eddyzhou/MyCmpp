package org.zq.cmpp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.zq.cmpp.configure.CmppHostConfiguration;
import org.zq.cmpp.exception.ConnectException;
import org.zq.cmpp.exception.RetrieveMessageException;
import org.zq.cmpp.exception.SendMessageException;
import org.zq.cmpp.i18n.LocalMessages;

public class SocketConnection implements CmppConnection {

	private int timeout;

	private Socket socket = null;

	private String host = null;

	private int port;

	private InputStream input = null;

	private OutputStream output = null;

	private int connectTimeout;

	private CmppHostConfiguration cmppConfigure = new CmppHostConfiguration();

	public SocketConnection() {
		this.timeout = cmppConfigure.getConnectTimeout();
		this.host = cmppConfigure.getHost();
		this.port = cmppConfigure.getPort();
	}

	public SocketConnection(String host, int port) {
		timeout = cmppConfigure.getConnectTimeout();
		this.host = host;
		this.port = port;
	}

	public void disconnect() throws ConnectException {
		try {
			if (socket != null) {
				socket.close();
				socket = null;
			}
			if (input != null) {
				input.close();
				input = null;
			}
			if (output != null) {
				output.close();
				output = null;
			}
		} catch (IOException e) {
			throw new ConnectException(LocalMessages
					.getString("connection.disconnect"), e);
		}

	}

	public void connect() throws ConnectException {
		socket = new Socket();
		InetSocketAddress remoteAddr = new InetSocketAddress(host, port);
		try {
			socket.connect(remoteAddr, connectTimeout);
			socket.setSoTimeout(timeout);
			input = socket.getInputStream();
			output = socket.getOutputStream();
		} catch (IOException e) {
			throw new ConnectException(LocalMessages
					.getString("connection.timeout"), e);
		}
	}

	public boolean isConnected() {
		if (socket == null)
			return false;
		return socket.isConnected();
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void receiveData(byte[] data) throws RetrieveMessageException {
		try {
			int read = input.read(data);
			if (read < 0)
				throw new RetrieveMessageException(LocalMessages
						.getString("retrieveMessage.noData"));
		} catch (IOException e) {
			throw new RetrieveMessageException(LocalMessages
					.getString("retrieveData.ioException"), e);
		}
	}

	public void flush() throws SendMessageException {
		try {
			output.flush();
		} catch (IOException e) {
			throw new SendMessageException(LocalMessages
					.getString("sendData.flushFailure"), e);
		}
	}

	public void sendData(byte[] data) throws SendMessageException {
		try {
			output.write(data);
		} catch (IOException e) {
			throw new SendMessageException(LocalMessages
					.getString("sendData.writeFailure"), e);
		}
	}

	public static void main(String[] args) throws Exception {
		SocketConnection con = new SocketConnection();
		System.out.println(con.isConnected());
		con.connect();
		System.out.println(con.isConnected());

		for (int i = 0; i < 10; i++) {
			Thread.sleep(2000);

			System.out.println(con.isConnected());
		}

	}

}
