--------------------------
LimitedInputStream
--------------------------
	# 限制 Read数量
		import java.io.IOException;
		import java.io.InputStream;
		import java.util.Objects;

		/**
		 * 
		 * 限制InputStream可读字节数量，流可能会提前结束 这个类线程不安全
		 * 
		 * @author KevinBlandy
		 *
		 */
		public class LimitedInputStream extends InputStream {

			private static final int EOF = -1;

			private InputStream inputStream;

			private long maxBytes;

			public LimitedInputStream(InputStream inputStream, long maxBytes) {
				super();
				
				Objects.requireNonNull(inputStream);
				if (maxBytes < 0) {
					throw new IllegalArgumentException();
				}
				
				this.inputStream = inputStream;
				this.maxBytes = maxBytes;
			}

			@Override
			public int read() throws IOException {
				if (maxBytes <= 0) {
					return EOF;
				}
				int ret = this.inputStream.read();
				if (ret != EOF) {
					maxBytes --;
				}
				return ret;
			}

			@Override
			public int read(byte[] b) throws IOException {
				return this.read(b, 0, b.length);
			}
			
			@Override
			public int read(byte[] b, int off, int len) throws IOException {
				if (maxBytes <= 0) {
					return EOF;
				}
				
				if ((off + len) > b.length) {
					throw new IllegalArgumentException();
				}
				
				int ret = this.inputStream.read(b, off, (int) Math.min(maxBytes, len));
				if (ret != EOF) {
					maxBytes -= ret;
				}
				return ret;
			}

			@Override
			public byte[] readAllBytes() throws IOException {
				if (maxBytes <= 0) {
					return new byte[0];
				}
				byte[] byteArray = this.inputStream.readAllBytes(); 
				
				maxBytes -= byteArray.length;
				
				return byteArray;
			}

			@Override
			public byte[] readNBytes(int len) throws IOException {
				if (maxBytes <= 0) {
					return new byte[0];
				}
				
				byte[] byteArray = this.inputStream.readNBytes((int) Math.min(maxBytes, len));
				
				maxBytes -= byteArray.length;
				
				return byteArray;
			}

			@Override
			public int readNBytes(byte[] b, int off, int len) throws IOException {
				if (maxBytes <= 0) {
					return EOF;
				}
				
				if ((off + len) > b.length) {
					throw new IllegalArgumentException();
				}
				
				int ret = this.inputStream.readNBytes(b, off, (int) Math.min(maxBytes, len));
				
				if (ret != EOF) {
					maxBytes -= ret;
				}
				
				return ret;
			}
			
		//	@Override
		//	public long skip(long n) throws IOException {
		//		if (maxBytes <= 0 || n <= 0) {
		//			return 0;
		//		}
		//		
		//		long ret = this.inputStream.skip(n);
		//		
		//		maxBytes -= ret;
		//		
		//		return ret;
		//	}
		//	
		//	@Override
		//	public void skipNBytes(long n) throws IOException {
		//		if (maxBytes <= 0 || n <= 0) {
		//			return;
		//		}
		//		
		//		this.inputStream.skipNBytes(Math.min(maxBytes, n));
		//		
		//		maxBytes -= n;
		//	}
		//	
		//	@Override
		//	public boolean markSupported() {
		//		return this.inputStream.markSupported();
		//	}
			
			/**
			 * 
			 * 可读字节数量
			 * @return
			 */
			public long readableBytes() {
				return maxBytes;
			}
		}
