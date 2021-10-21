package io.springcloud.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class IOUtils {
	
	/**
	 * 从流中读取最少字节数量，流可能会提前结束，导致读取数量不足最少字节，需要通过返回的字节数组长度来判断
	 * 
	 * @param inputStream
	 * @param min
	 * @return
	 * @throws IOException
	 */
	public static byte[] readAtLeast(InputStream inputStream, int min) throws IOException {
		return readAtLeast(Channels.newChannel(inputStream), min);
	}

	/**
	 * 从通道中读取最少字节数量，通道可能会提前结束，导致读取数量不足最少字节，需要通过返回的字节数组长度来判断
	 * 
	 * @param channel
	 * @param min
	 * @return
	 * @throws IOException
	 */
	public static byte[] readAtLeast(ReadableByteChannel channel, int min) throws IOException {

		if (min < 0) {
			throw new IllegalArgumentException();
		}

		ByteBuffer buffer = ByteBuffer.allocate(min);

		int readCount = 0;

		while (readCount < min) {
			int count = channel.read(buffer);
			if (count == -1) {
				break;
			}
			readCount += count;
		}

		buffer.flip();

		byte[] ret = new byte[readCount]; // buffer.remaining() == readCount

		buffer.get(ret, 0, readCount);

		return ret;
	}
	
	/**
	 * 最多 Copy maxBytes个字节
	 * @param in
	 * @param out
	 * @param maxBytes
	 * @return
	 * @throws IOException
	 */
	public static long copyN(InputStream in, OutputStream out, long maxBytes) throws IOException {
		if (maxBytes < 0L) {
			throw new IllegalArgumentException();
		}
		
		final long finalBytes = maxBytes;
		
		int bufferSize = 8096; // 8Kb
		
		while (maxBytes > 0) {
			
			byte[] buffer = new byte[(int) Math.min(bufferSize, maxBytes)];
			
			int readBytes = in.read(buffer);
			
			if (readBytes == -1) {
				break;
			}
			
			out.write(buffer);
			
			maxBytes -= readBytes;
		}
		return finalBytes - maxBytes;
	}
}
