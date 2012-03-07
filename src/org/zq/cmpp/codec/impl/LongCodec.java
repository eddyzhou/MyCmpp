package org.zq.cmpp.codec.impl;

import org.zq.cmpp.codec.Codec;

public class LongCodec implements Codec<Long> {

	public byte[] encode(Long value) {
		int offset = 0;
		byte[] data = new byte[8];
		data[offset++] = (byte) (int) (value >> 0 & 255L);
		data[offset++] = (byte) (int) (value >> 8 & 255L);
		data[offset++] = (byte) (int) (value >> 16 & 255L);
		data[offset++] = (byte) (int) (value >> 24 & 255L);
		data[offset++] = (byte) (int) (value >> 32 & 255L);
		data[offset++] = (byte) (int) (value >> 40 & 255L);
		data[offset++] = (byte) (int) (value >> 48 & 255L);
		data[offset++] = (byte) (int) (value >> 56 & 255L);
		return data;
	}

	public Long decode(byte[] data) {
		int offset = 0;
		long low = ((data[offset++] & 255) << 0)
				+ ((data[offset++] & 255) << 8)
				+ ((data[offset++] & 255) << 16)
				+ ((data[offset++] & 255) << 24);
		long high = ((data[offset++] & 255) << 0)
				+ ((data[offset++] & 255) << 8)
				+ ((data[offset++] & 255) << 16)
				+ ((data[offset++] & 255) << 24);
		return (high << 32) + (4294967295L & low);
	}

}
