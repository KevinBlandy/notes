-------------------------------
ZipEntry
-------------------------------
	# 压缩文件的压缩项
		public class ZipEntry implements ZipConstants, Cloneable

	# 构造函数
		ZipEntry(String name)
		ZipEntry(ZipEntry e) 

	# 实例方法
		String getComment()
		Object clone()
		long getCompressedSize()
		long getCrc()
		FileTime getCreationTime()
		byte[] getExtra()
		FileTime getLastAccessTime()
		FileTime getLastModifiedTime()
		int getMethod()
		String getName()
		long getSize()
		long getTime()
		boolean isDirectory()

		void setComment(String comment)
		void setCompressedSize(long csize)
		void setCrc(long crc)
		ZipEntry setCreationTime(FileTime time)
		void setExtra(byte[] extra)
		ZipEntry setLastAccessTime(FileTime time)
		ZipEntry setLastModifiedTime(FileTime time)
		void setMethod(int method)
		void setSize(long size)
		void setTime(long time)
		