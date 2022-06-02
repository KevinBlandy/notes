---------------------------
FileSystem					|
---------------------------
	# �ļ�ϵͳ��
		* Zip Ҳ����һ���ļ�ϵͳ������ͨ�� FileSystem ����
			FileSystem fileSystem = FileSystems.newFileSystem(Paths.get("C:\\eclipse-jee-2021-09-R-win32-x86_64.zip"));
			Path zipFile = fileSystem.getPath("/");
			// ����zip�ļ��е������ļ�
			Files.walk(zipFile).forEach(System.out::println);

	# ������ʽ
		FileSystem fileSystem = FileSystems.getDefault();
			* ͨ�� FileSystems ������ȡ
		FileSystem newFileSystem(Path path, ClassLoader loader);
			* ͨ�� FileSystems ����ָ���� path �����µ��ļ�ϵͳ
		FileSystem	getFileSystem();
			* ͨ�� path ʵ����ȡ
			
---------------------------
FileSystem-ʵ������			|
---------------------------
		public abstract void close() throws IOException;
		public abstract Iterable<FileStore> getFileStores();
			* ��ȡ��·���Ĵ���(���̷���)
		
		public abstract Path getPath(String first, String... more);	
			* ����·��,�ӵ�ǰ�ļ�ϵͳ��ȡPath����
		
		public abstract PathMatcher getPathMatcher(String syntaxAndPattern);
		public abstract Iterable<Path> getRootDirectories();
			Iterable<Path> getRootDirectories();
		
		public abstract String getSeparator();
			* ��ȡ��ƽ̨�ļ��ָ���
		
		public abstract UserPrincipalLookupService getUserPrincipalLookupService();
		public abstract boolean isOpen();
		public abstract boolean isReadOnly();
		public abstract Set<String> supportedFileAttributeViews();
		public abstract FileSystemProvider provider();
		public abstract WatchService newWatchService() throws IOException;
		


