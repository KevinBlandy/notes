-----------------------
FileSystems				|
-----------------------
	# �ļ�ϵͳ�Ĺ���(��)��
	# ����ʵ����
	# ���Ǿ�̬����

-----------------------
FileSystems	-����		|
-----------------------
	public static FileSystem getDefault()
		* ��ȡĬ�ϵ��ļ�ϵͳ����,���ǵ�ǰϵͳ���ļ�ϵͳ

	public static FileSystem getFileSystem(URI uri) 
	public static FileSystem newFileSystem(URI uri, Map<String,?> env)
	public static FileSystem newFileSystem(URI uri, Map<String,?> env, ClassLoader loader)throws IOException
	public static FileSystem newFileSystem(Path path) throws IOException
	public static FileSystem newFileSystem(Path path, ClassLoader loader)
	public static FileSystem newFileSystem(Path path, Map<String,?> env)
	public static FileSystem newFileSystem(Path path, Map<String,?> env, ClassLoader loader)

	