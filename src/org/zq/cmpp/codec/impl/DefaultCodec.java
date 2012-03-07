package org.zq.cmpp.codec.impl;

import org.zq.cmpp.codec.Codec;

public class DefaultCodec implements Codec<Object> {
	private static final char DIGITS[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

	public byte[] encode(Object value) {
		if (value.getClass() == EMPTY_BYTE_ARRAY.getClass()) {
			return (byte[]) value;
		}
		return value.toString().getBytes();
	}

	public static char[] encodeHex(byte data[]) {
		int l = data.length;
		char[] out = new char[l << 1];
		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS[0x0F & data[i]];
		}
		return out;
	}

	public Object decode(byte[] data) {
		return new String(data);
	}

	public static byte[] split(byte[] arr, int wantLen) {
		byte[] last = new byte[wantLen];
		for (int i = 0; i < arr.length; i++) {
			if (i >= wantLen)
				break;
			last[i] = arr[i];
		}
		return last;
	}

	public static void main(String[] args) {
		byte[] bytes = "abc我的中国".getBytes();
		System.out.println(encodeHex(bytes));
	}
}
