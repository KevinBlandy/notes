import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class StreamUtils {
	
	public static final int EOF = -1;
	
	// 4Kb
	public static final int defaultBufferSize = 1024 << 2;
	
	/**
	 *  从src，至少复制n个字节数据到dest
	 * @param src
	 * @param dest
	 * @param n
	 * @return
	 * @throws IOException
	 */
	public static int copyN (ReadableByteChannel src, WritableByteChannel dest, int n) throws IOException {
		
		if (n < 0) {
			throw new IllegalArgumentException();
		}

		ByteBuffer buffer = ByteBuffer.allocate(Math.min(n, defaultBufferSize));

		int readCount = 0;

		while (readCount < n) {
			
			if ((n - readCount) < buffer.capacity()) {
				buffer.limit(n - readCount);
			}
			
			int count = src.read(buffer);
			if (count == EOF) {
				break;
			}
			
			readCount += count;
			
			buffer.flip();
			
			while (buffer.hasRemaining()) {
				dest.write(buffer);	
			}
			
			buffer.clear();
		}
		
		return readCount;
	}
	
	/**
	 * 从src至少复制n个字节到dest
	 * @param src
	 * @param dest
	 * @param n
	 * @return
	 * @throws IOException
	 */
	public static int copyN (InputStream src, OutputStream dest, int n) throws IOException {
		if (n < 0) {
			throw new IllegalArgumentException();
		}
		
		byte[] buffer = new byte[Math.min(n, defaultBufferSize)];

		int readCount = 0;
		
		int limit = buffer.length;

		while (readCount < n) {
			if ((n - readCount) < buffer.length) {
				limit = n - readCount;
			}
			int count = src.read(buffer, 0, limit);
			if (count == EOF) {
				break;
			}
			
			dest.write(buffer, 0, count);
			
			readCount += count;
		}
		
		return readCount;
	}
	
	/**
	 * 读满字节数组
	 * @param src
	 * @param dest
	 * @return
	 * @throws IOException
	 */
	public static int readFull (InputStream src, byte[] dest) throws IOException {
		int readCount = 0;
		while (readCount < dest.length) {
			int count = src.read(dest, readCount, dest.length - readCount);
			if (count == EOF) {
				break;
			}
			readCount += count;
		}
		return readCount;
	}
}
