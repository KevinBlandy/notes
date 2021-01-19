--------------------
ZipFile
--------------------
	# 压缩文件对象
		public class ZipFile implements ZipConstants, Closeable 

	# 构造函数
		ZipFile(File file)
		ZipFile(File file, int mode)
		ZipFile(File file, int mode, Charset charset)
		ZipFile(File file, Charset charset)
		ZipFile(String name)
		ZipFile(String name, Charset charset)
	
	# 实例方法
		String getComment();
		Enumeration<? extends ZipEntry> entries()
		ZipEntry getEntry(String name)
		InputStream getInputStream(ZipEntry entry)
		String getName()
		int size()
		Stream<? extends ZipEntry> stream()
