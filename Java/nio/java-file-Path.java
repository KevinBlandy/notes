------------------------
Path					|
------------------------
	# ��һ���ӿ�,jdk1.7���¶���
	# ��ʾһ���ļ�����Ŀ¼,Ҳ����������
	# ����(���ַ�ʽ)
		1, FileSystems ��̬����
			Path path = FileSystems.getDefault().getPath("F:/test/jdk7", "test.txt");  

		2,ͨ�� File �����ȡ
			File file = new File("F:/test/jdk7/test.txt");  
			Path path = file.toPath();  

		3,Paths ��̬����
			Path path = Paths.get("F:/test/jdk7", "test.txt"); 
				* �ڶ��������ǿɱ䳤����,��ʶ�㼶Ŀ¼
		
------------------------
��̬����
------------------------
	public static Path of(String first, String... more)
		* ��ȡPath
			 return FileSystems.getDefault().getPath(first, more);
	
	public static Path of(URI uri)


------------------------
Path-ʵ������			|
------------------------
	boolean			endsWith(Path other)
	boolean			endsWith(String other)
						* �Ƿ���ָ������ʽ��β
	boolean			startsWith(Path other)
	boolean			startsWith(String other)
						* �Ƿ���ָ������ʽ��β
	boolean			isAbsolute();
						* �ж��Ƿ��Ǿ���·��
	Iterator<Path>	iterator();
						* ���ص�����
						* δ֪
	Path			resolve(Path path);
	Path			resolve(String path);
						* ��������Ǿ���·��,�ͷ��ز���,��Ȼ���ص�ǰ����+����·���Ķ���

	Path			resolveSibling(Path path);
	Path			resolveSibling(String path);
						* ��� path �Ǿ���·��,�ͷ���path��ʾ�Ķ���
						* ��Ȼ���� this �ĸ�·�� + path ��ʾ��·������

	Path			relativize(Path path);
						* ʹ�� this ���н���,�൱�ڵ� path ��·��

	Path			normalize();
						* ɾ��һЩ����[.],[..]
						* �����·����ʾԪ��

	Path			toAbsolutePath();
						* ���ؾ���·��
	File			toFile();
						* ת��Ϊfile����
	URI				toUri();
						* ת��ΪURI
	int				compareTo(Path path)
						* �ж������ļ������Ƿ���ͬ
	int				getNameCount();
						* ��ȡ�ļ����ڵĽڵ����,�������Ϊ��Ŀ¼�ĵڼ�����
	Path			getFileName();
						* ��ȡ·�����ļ�������
	Path			getRoot();
						* ���ظ�Ŀ¼,���û�и�Ŀ¼,����null
	FileSystem		getFileSystem();
						* ��ȡ�ļ�ϵͳ����
	Path			getParent();
						* ��ȡ��ǰPath�ĸ���path
	Path			getName(int x );
						* ��ȡָ����ε�Path����
						* ��������˻���Ϊ����,���׳��쳣
	Path			subpath(int beginIndex,int endIndex);
						* ��ȡ��·����Path
						* demo
							Path path = Paths.get("E:\\ext-6.2.0\\index.html");
					    	System.out.println(path.subpath(1, 2));		//index.html
	Path			toAbsolutePath();
						* ת��Ϊ����·��
	Path			toRealPath(LinkOption... options);
						* ��ȡ��ʵ·��
	
	Spliterator<Path>	path.spliterator();
						* δ֪
	WatchKey register(WatchService watcher,WatchEvent.Kind<?>... events)
						* �󶨼���	
	@Override
    WatchKey register(WatchService watcher,
                      WatchEvent.Kind<?>[] events,
                      WatchEvent.Modifier... modifiers)
        throws IOException;

------------------------
Path-��̬����			|
------------------------
	