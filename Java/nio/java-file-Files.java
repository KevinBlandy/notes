----------------------------
Files						|
----------------------------
	# �����ļ��ľ�̬������,��ţ��


----------------------------
Files-��̬����				|
----------------------------
	//=======================�ļ���Ϣ
	String			probeContentType(Path path)
						* ��ȡ�ļ�����(ContentType)
	boolean			isHidden(Path path);
						* �ж�ָ����path�Ƿ��������ļ�/Ŀ¼
	boolean			exists(Path path, LinkOption... options);
						* �ж�ָ�����ļ��Ƿ����
	boolean			notExists(Path path, LinkOption... options)
						* �ж�ָ�����ļ��Ƿ����
	boolean			isDirectory(Path path, LinkOption... options);
						* �Ƿ���Ŀ¼
	boolean			isReadable(Path path);
						* �Ƿ�ɶ�
	boolean			isWritable(Path path);
						* �Ƿ��д
	boolean			isRegularFile(Path path);
						* �ж��ļ��Ƿ��������ļ�
	boolean			isExecutable(Path path);
						* �ж��ļ��Ƿ��ִ��
	boolean			isSameFile(Path path, Path path2);
						* �ж��ļ��Ƿ���ͬһ���ļ�
	long			size(Path path);
						* ����ָ���ļ��Ĵ�С
	FileStore		getFileStore(Path path);
						* ��ȡָ�� path ���ڵ��̷�

	//=======================�ļ���Ϣ(�������)
	<V extends FileAttributeView> V getFileAttributeView(Path path,Class<V> type,LinkOption... options)
						* ��ȡpath���ļ���ָ�����͵�������������
						* ���� type �ӿ������ز�ͬ���͵�������������
							* ��������			BasicFileAttributeView
							* �ļ�������		FileOwnerAttributeView	
							* �Զ�������		UserDefinedFileAttributeView
							...

	Path			setAttribute(Path path, String attribute, Object value,LinkOption... options)
						* ��ָ���ļ���ָ����������ֵ
	Object			getAttribute(Path path, String attribute,LinkOption... options)
						* ��ȡָ���ļ���ָ�����Ƶ�����

	FileTime		getLastModifiedTime(Path path, LinkOption... options)
						* ��ȡ�ļ������޸�ʱ��
	Path			setLastModifiedTime(Path path, FileTime time);
						* ��������޸�ʱ��

	UserPrincipal	getOwner(Path path, LinkOption... options);
						* �����ļ�������
						* ���ؽӿ�:UserPrincipal ��ʵ��
	Path			setOwner(Path path, UserPrincipal owner);
						* �����ļ�������
	
	Path			setPosixFilePermissions(Path path,Set<PosixFilePermission> perms)
						* �����ļ���POSIXȨ�ޡ�
	Set<PosixFilePermission>	getPosixFilePermissions(Path path, LinkOption... options);
						* ��ȡ�ļ���Ȩ����Ϣ

	//=======================����
	boolean			isSymbolicLink(Path path);
						* �ж��Ƿ��������Ӷ�
	Path			readSymbolicLink(Path path);
						* ͨ�������ӻ�ȡ��ʵ���ļ�����

	Path			createSymbolicLink(Path link,Path target, FileAttribute<?>... attrs)
						* ����������
	Path			createLink(Path link,Path existing);
						* ����Ӳ����
						* link ��ݷ�ʽ��ַ,existing �Ѿ����ڵ��ļ�

	//=======================��ȡ
	byte[]			readAllBytes(Path path);	
						* ��ָ��Path��ʾ���ļ���ȡ���ֽ�����
	List<String>	readAllLines(Path path,Charset charset);
						* ��ָ�����ı��ļ�,��ָ�����ַ���һ��һ�еĶ�ȡ��List<String>��
	
	String readString(Path path) throws IOException
	String readString(Path path, Charset cs) throws IOException
					* ��ȡΪ�ַ���
	Stream<Path> list(Path dir) throws IOException 
					* ��ȡ�ļ��б�
	
	//=======================д��
	Path			write(Path path,byte[] bute,OpenOption penOption...);
						* ���ֽ�д�뵽 path
						* penOption��һ����ǽӿ�,����ָ��д���ģʽ

	Path			write(Path path, Iterable<? extends CharSequence> lines,OpenOption penOption...);
						* ͬ��,д����� lines �����е�����
						* ����:List<String> �е�����
	
	Path writeString(Path path, CharSequence csq, OpenOption... options) throws IOException
	Path writeString(Path path, CharSequence csq, Charset cs, OpenOption... options) throws IOException
	
	//=======================ɾ��
	void			delete(Path path);
						* ɾ��,����ǿ��ļ���.��ɾ�׳��쳣

	boolean			deleteIfExists(Path path)
						* ɾ���ļ�,���������.�����׳��쳣

	//=======================��ȡ��
	InputStream		newInputStream(Path path,OpenOption penOption...);
						* ��ȡָ��Path��InputStream
	OutputStream	newOutputStream(Path path,OpenOption penOption...);
						* ��ȡָ����OutputStream
	BufferedReader	newBufferedReader(Path path,OpenOption penOption...);
						* ��ȡָ���� BufferedReader
						* �и����ط���,����ָ�� Charset
	BufferedWriter	newBufferedWriter(Path path,OpenOption penOption...);
						* ��ȡBufferedWriter
						* �и�����,����ָ�� Charset
	Stream<String>	lines(Path path);
						* ������
						* �и�����,�������� Charset
	SeekableByteChannel newByteChannel(Path path, OpenOption... options);
						* ��ȡ SeekableByteChannel �ܵ�
						* SeekableByteChannel �� FileChannel �ĸ���
	SeekableByteChannel newByteChannel(Path path,Set<? extends OpenOption> options,FileAttribute<?>... attrs);
						* ͬ��,������������
	DirectoryStream<Path>	newDirectoryStream(Path dir);
						* ��ȡĿ¼��,��������.�������ַ�����ʽ�ͺ����ӿڵķ�ʽ���ù�����
	Stream<Path>	find(Path start, int maxDepth, BiPredicate<Path,BasicFileAttributes> matcher, FileVisitOption... options)
						* �� start Ŀ¼�м����ļ�,������Ϊ:maxDepth
						* ͨ������ matcher �����ӿ��������ļ�/�ļ���
	
	//=======================����
	DirectoryStream<Path> newDirectoryStream(Path dir);
							* ��ȡ dir �µ�����һ��Ŀ¼���ļ�
	DirectoryStream<Path> newDirectoryStream(Path dir,String parrten);
							* ��ȡ dir �µ�����һ��Ŀ¼���ļ�,����ͨ�� parrten ���ʽ�������ļ�����Ŀ¼
	DirectoryStream<Path> newDirectoryStream(Path dir,DirectoryStream.Filter<? super Path> filter)
							* ��ȡ dir �µ�����һ��Ŀ¼���ļ�,����ͨ�� filter ���ʽ�������ļ�����Ŀ¼

	Stream<Path>	walk(Path start, FileVisitOption... options)
						* �ݹ�Ŀ¼,���� Stream
	Stream<Path>	walk(Path start, int maxDepth, FileVisitOption... options)
						* ͬ��,��һ�� maxDepth �����Ʊ����Ĳ��
						* options �������Ƿ�Ҫ��������

	Path			walkFileTree(Path start, FileVisitor<? super Path> visitor);
						* ͨ���Լ�ʵ�� FileVisitor ����ɱ���ָ����Ŀ¼,�ݹ�,
	Path			walkFileTree(Path start, Set<FileVisitOption> options,int maxDepth,FileVisitor<? super Path> visitor)
						* ͬ��,��һ�� maxDepth �����Ʊ����Ĳ��
						* options �������Ƿ�Ҫ��������

	//=======================����
	long			copy(Path path,OutputStream in);
						* ���� Path ��in��
	Path			copy(InputStream in,Path target,CopyOption... options);
						* ���� in �е����ݵ�  target
	Path			copy(Path source, Path target, CopyOption... options)
						* ���� source �� target
						* CopyOption ָ�����ƵĹ���
	
	//=======================�ƶ�
	Path			move(Path source, Path target, CopyOption... options)
						* �ƶ� source �� target
						* ����ͨ��options������Ŀ�����ʱ�Ǹ��ǣ������쳣

	//=======================ɾ��
	void			delete(Path path);
						* ɾ��,�ļ������ڻ��׳��쳣
	boolean			deleteIfExists(Path path);
						* ����ļ�����,��ִ��ɾ��

	//=======================����(Ŀ¼)
	Path			createDirectory(Path dir, FileAttribute<?>... attrs)
						* ����,�������һ����Բ�����,�����ı������,��Ȼ�쳣
						* ˵����,ֻ�ܴ���һ��Ŀ¼
						* attrs ��ʾ�ļ�������
	Path			createDirectories(Path dir, FileAttribute<?>... attrs);
						* �м�����ж���㶼������,˵����,���Դ���N��Ŀ¼
						* attrs ��ʾ�ļ�������
	//=======================����(�ļ�)
	Path			createFile(Path path, FileAttribute<?>... attrs);
						* �����ļ�,����ļ��Ѿ�����,���׳��쳣
	//=======================����(��ʱ�ļ�)
	Path			createTempFile(String prefix,String suffix,FileAttribute<?>... attrs)
						* ������ʱ�ļ���
						* prefix �� suffix ������Ϊnull���ַ�����
	Path			createTempFile(Path dir,String prefix,String suffix,FileAttribute<?>... attrs)
						* ������ʱ�ļ���,���� dir
	//=======================����(��ʱ�ļ���)
	Path			createTempDirectory(String prefix,FileAttribute<?>... attrs)
						* ������ʱ�ļ���
	Path			createTempDirectory(Path dir,String prefix,FileAttribute<?>... attrs)
						* ������ʱ�ļ���,���� dir
						
	//=======================�Ƚ�
	long mismatch(Path path, Path path2)
				* ���Ҳ����������е�һ����ƥ���ֽڵ�λ��
				* ����Ҳ������� -1

----------------------------
Files-Options				|
----------------------------
	CopyOption(�ӿ�)
		StandardCopyOption
			REPLACE_EXISTING,		//���Ŀ���Ѿ�����,�򸲸�Ŀ��
			COPY_ATTRIBUTES,		//�����ļ�������
			ATOMIC_MOVE				//ԭ���Բ���,Ҫô�ƶ��ɹ�,Ҫô�ļ�����ԭ��
	
	OpenOption(�ӿ�)
		StandardOpenOption(ö��)
			READ,					//���Ķ�Ȩ��
			WRITE,					//��дȨ��
			APPEND,					//��ĩβ���,��������ǰ������
			TRUNCATE_EXISTING,		//����ļ��Ѿ����ڣ����Ҵ򿪽���WRITE ���ʣ����䳤�Ƚ����ض�Ϊ0��
			CREATE,					//���������,�򴴽��µ�
			CREATE_NEW,				//����һ���µ��ļ���������ļ��Ѿ�����ʧ�ܡ�
			DELETE_ON_CLOSE,		//��jvm�رյ�ʱ��ɾ���ļ�
			SPARSE,					//ϡ���ļ�
			SYNC,					//Ҫ���ļ����ݻ�Ԫ���ݵ�ÿ�θ��¶�ͬ��д��ײ�洢�豸��
			DSYNC;					//Ҫ���ļ����ݵ�ÿ�θ��¶���ײ�洢�豸ͬ��д��

		LinkOption
			* ʵ����
			* 'ʵ����OpenOption �� CopyOption'
			NOFOLLOW_LINKS;
	
	FileVisitOption
		FOLLOW_LINKS

----------------------------
Files-FileAttribute			|
----------------------------
	# ��ʾ�ļ������Խӿ�,��Ҫ�Լ�ʵ������

----------------------------
PosixFilePermission			|
----------------------------
	# �ļ�Ȩ��ö����
		* ������Ȩ��
			OWNER_READ,
			OWNER_WRITE,
			OWNER_EXECUTE,
		* ������Ȩ��
			GROUP_READ,
			GROUP_WRITE,
			GROUP_EXECUTE,
		* ������Ȩ��
			OTHERS_READ,
			OTHERS_WRITE,
			OTHERS_EXECUTE;