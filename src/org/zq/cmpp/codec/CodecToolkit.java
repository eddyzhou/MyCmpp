package org.zq.cmpp.codec;

import org.zq.cmpp.codec.Codec;
import org.zq.cmpp.codec.Encoder;
import org.zq.cmpp.codec.impl.DefaultCodec;
import org.zq.cmpp.codec.impl.IntegerCodec;
import org.zq.cmpp.codec.impl.LongCodec;
import org.zq.cmpp.codec.impl.MD5Encoder;
import org.zq.cmpp.codec.impl.StringCodec;

public final class CodecToolkit {
	private static final CodecToolkit INSTANCE = new CodecToolkit();

	private final Codec<Long> longCoder = new LongCodec();
	private final Codec<Integer> integerCoder = new IntegerCodec();
	private final Codec<String> stringCoder = new StringCodec();
	private final Codec<Object> defaultCoder = new DefaultCodec();
	private final Encoder<String> md5Encoder = new MD5Encoder();

	private CodecToolkit() {
	}

	@SuppressWarnings("unchecked")
	public static <T> Codec<T> getCoder(Class<T> clazz) {
		if (clazz == int.class || clazz == Integer.class)
			return (Codec<T>) INSTANCE.integerCoder;
		else if (clazz == long.class || clazz == Long.class)
			return (Codec<T>) INSTANCE.longCoder;
		else if (clazz == String.class)
			return (Codec<T>) INSTANCE.stringCoder;
		return (Codec<T>) INSTANCE.defaultCoder;
	}

	public static Encoder<String> getMd5Encoder() {
		return INSTANCE.md5Encoder;
	}

	public static Codec<Integer> getIntegerCoder() {
		return INSTANCE.integerCoder;
	}

	public static Codec<Long> getLongCoder() {
		return INSTANCE.longCoder;
	}

	public static Codec<String> getStringCoder() {
		return INSTANCE.stringCoder;
	}

	public static void main(String[] args) {
		Codec<Long> coder = CodecToolkit.getCoder(Long.class);

		long value = coder
				.decode(new byte[] { 77, 0, -23, 3, 64, 123, 66, 116 });
		System.out.println(value);
		byte[] bytes = CodecToolkit.getCoder(Long.class).encode(
				8377393771736268877L);
		for (int i = 0; i < bytes.length; i++) {
			System.out.print(bytes[i] + " ");
		}
	}
}
