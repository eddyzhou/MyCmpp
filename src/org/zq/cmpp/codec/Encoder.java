package org.zq.cmpp.codec;

public interface Encoder<T> {
	byte[] encode(T value);
}
