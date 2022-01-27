-------------
CRC32
-------------
	# CRC32 校验器
		public class CRC32 implements Checksum
	

	public CRC32()
	public void update(int b)
	public void update(byte[] b, int off, int len)
	public void update(ByteBuffer buffer)
	public void reset()
	public long getValue()


