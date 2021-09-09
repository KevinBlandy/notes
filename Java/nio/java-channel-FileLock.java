--------------------------
FileLock
--------------------------
	# �ļ���������
		abstract class FileLock implements AutoCloseable
	
	# ���Ļ�ȡ
		FileLock tryLock(long position, long size, boolean shared)
		FileLock fileChannel.tryLock();
			* �õ��ò�������,ֱ�ӻ�ȡ��,�����������,����null
			* ��ȡ�ļ�������
				position
				size
					* ��������ָ��Ҫ�����Ĳ��֣�����ֻ���ļ��Ĳ������ݼ���
				shared
					* ָ���Ƿ��ǹ�������
					* ���ָ��Ϊ�����������������̿ɶ����ļ������н��̾�����д���ļ������ĳ������ͼ�Դ��ļ�����д���������׳��쳣��
			* demo
				FileLock lock = fileChanne.lock();		//��ȡ�ļ���
				lock.release();							//�ͷ���
		
		FileLock lock(long position, long size, boolean shared)throws IOException;
		FileLock fileChannel.lock(); 
			* ͬ��
			* �õ��û�����,ֱ����ȡ����
	
	protected FileLock(FileChannel channel, long position, long size, boolean shared)
	protected FileLock(AsynchronousFileChannel channel, long position, long size, boolean shared)

	public final FileChannel channel()
	public Channel acquiredBy() 
	public final long position()
	public final long size()
	public final boolean isShared()
		* �Ƿ��ǹ�����

	public final boolean overlaps(long position, long size)
	public abstract boolean isValid();
		* ���Ƿ���Ч

	public abstract void release() throws IOException;
	public final void close() throws IOException {
	
