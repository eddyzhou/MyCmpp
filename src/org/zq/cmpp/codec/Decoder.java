package org.zq.cmpp.codec;

public interface Decoder<T> {
	T decode(byte[] data);
}
