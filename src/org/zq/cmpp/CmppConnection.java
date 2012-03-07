package org.zq.cmpp;

import org.zq.cmpp.exception.ConnectException;
import org.zq.cmpp.exception.RetrieveMessageException;
import org.zq.cmpp.exception.SendMessageException;

public interface CmppConnection {

	public void connect() throws ConnectException;

	public void disconnect() throws ConnectException;

	public boolean isConnected();

	public int getTimeout();

	public void setTimeout(int timeout);

	public int getConnectTimeout();

	public void flush() throws SendMessageException;

	public void setConnectTimeout(int connectTimeout);

	public void sendData(byte[] data) throws SendMessageException;

	public void receiveData(byte[] data) throws RetrieveMessageException;
}
