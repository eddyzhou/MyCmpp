package org.zq.cmpp.codec.impl;

import org.zq.cmpp.codec.Codec;


public class StringCodec implements Codec<String>{

	public byte[] encode(String value) {
		return value.getBytes();
	}

	public String decode(byte[] data) {
		return new String(data);
	}
}
