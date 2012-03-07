package org.zq.cmpp.codec.impl;

import org.zq.cmpp.codec.Codec;

public class IntegerCodec implements Codec<Integer> {

	public byte[] encode(Integer value) {
		int offset = 0;
		byte[] data = new byte[4];
		data[offset++] = (byte) (value >> 24 & 255L);
		data[offset++] = (byte) (value >> 16 & 255L);
		data[offset++] = (byte) (value >> 8 & 255L);
		data[offset++] = (byte) (value >> 0 & 255L);
		return data;
	}

	public Integer decode(byte[] data) {
		int offset = 0;
		int retVal = (data[offset++] & 255) << 24;
		retVal += (data[offset++] & 255) << 16;
		retVal += (data[offset++] & 255) << 8;
		retVal += (data[offset++] & 255) << 0;
		return retVal;
	}

	public static void main(String[] args) {
		IntegerCodec codec = new IntegerCodec();
		byte[] bytes = codec.encode(127);
		for (byte b : bytes) {
			System.out.print(b);
		}
		System.out.println();
		long l = 1;
		System.out.println(l<<32);
	}

}
