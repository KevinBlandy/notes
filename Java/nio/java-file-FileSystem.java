---------------------------
FileSystem					|
---------------------------
	# �ļ�ϵͳ��
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
		Iterable<Path> getRootDirectories();
			* ��ȡ��·���Ĵ���(���̷���)

		Iterable<FileStore>	getFileStores();
			* ��ȡ��·���Ĵ���(���̷���)

		Path getPath(String first, String... more);
			* ����·��,�ӵ�ǰ�ļ�ϵͳ��ȡPath����

		String getSeparator();
			* ��ȡ��ƽ̨�ļ��ָ���
		


