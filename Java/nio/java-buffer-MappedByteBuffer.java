-------------------------------
MappedByteBuffer				|
-------------------------------
	# public abstract sealed class MappedByteBuffer extends ByteBuffer permits DirectByteBuffer
		* ���ļ���ĳһ���ֻ�ȫ��ӳ�䵽�ڴ���, ӳ���ڴ滺�����Ǹ�ֱ�ӻ�����
		* д��ʱ����д�����ڴ��е��ֽ����飬����ͬ�����ļ��ϵ�ʱ���ǲ�ȷ���ģ��ɲ���ϵͳ����
		* ������ֻҪ����ϵͳ������������ϵͳ�ᱣ֤ͬ�����ļ��ϣ���ʹӳ������ļ���Ӧ���Ѿ��˳���
	
	# �ļ���д�Ŀ���
		* ��һ����ļ���д�У������������ݸ��ƣ�һ���Ǵ�Ӳ�̸��Ƶ�����ϵͳ�ںˣ���һ���ǴӲ���ϵͳ�ں˸��Ƶ��û�̬��Ӧ�ó���
		* �����ڴ�ӳ���ļ��У�һ������£�ֻ��һ�θ��ƣ����ڴ�����ڲ���ϵͳ�ںˣ�Ӧ�÷��ʵľ��ǲ���ϵͳ���ں��ڴ�ռ䣬����ȻҪ����ͨ�Ķ�дЧ�ʸ��ߡ�
	
	# ���Ա����������
		* ����������ӳ��ͬһ���ļ���ӳ�䵽ͬһ���ڴ�����һ��������ڴ���޸ģ���������������Ҳ������
		* �������ڲ�ͬӦ�ó���֮���ͨ�š�

	# ����
		* ��̫�ʺϴ���С�ļ������ǰ�ҳ�����ڴ�ģ�����С�ļ������˷ѿռ�
		* ӳ���ļ�Ҫ����һ���Ĳ���ϵͳ��Դ����ʼ���Ƚ�����

	# ͨ�� FileChannel �� api ��ȡ
		MappedByteBuffer map(MapMode mode,long position, long size);

			mode��ö��
				READ_ONLY
					* ��ͼ�޸ĵõ��Ļ������������׳� ReadOnlyBufferException
					* ���ģʽ�ʹ��� Channel �Ķ�дȨ���йأ����ܳ�ͻ
						// Channel ���˶�дȨ��
						FileChannel.open(Paths.get("C:\\Users\\Laptop\\Desktop\\map.txt"), StandardOpenOption.READ, StandardOpenOption.WRITE);
				
				READ_WRITE
					* �Եõ��Ļ������ĸ������ս��������ļ�
					* �ø��Ķ�ӳ�䵽ͬһ�ļ�����������һ���ǿɼ���
				
				PRIVATE
					* ˽��ģʽ�����Ĳ���ӳ���ļ���Ҳ�����������򿴵�
			
			position
				* ��ʼ
			size
				* ����
			
			* ���ӳ������򳬹��������ļ��ķ�Χ�����ļ����Զ���չ����չ���������ֽ�����Ϊ 0��
		
	# ����
		public final MappedByteBuffer clear()
		public abstract MappedByteBuffer compact();
		public abstract MappedByteBuffer duplicate();
		public final MappedByteBuffer flip()
		public final MappedByteBuffer force()
		public final MappedByteBuffer force(int index, int length)
			* �� EAD_WRITE ģʽ�£�ǿ�а��޸�д�뵽�ļ���
		
		public final MappedByteBuffer limit(int newLimit)

		public final boolean isLoaded()
			* �Ƿ��Ѿ������ݼ��ص��ڴ��У�ֻ��һ���ο�ֵ����һ��׼ȷ

		public final MappedByteBuffer load()
			* �������ļ����ݼ��ص��ڴ���
		
		public final MappedByteBuffer mark()

		public final int position()
		public final MappedByteBuffer position(int newPosition)
			* ��ȡ/�޸�ָ��λ��

		public final MappedByteBuffer reset()
		public final MappedByteBuffer rewind()
		public abstract MappedByteBuffer slice()
		public abstract MappedByteBuffer slice(int index, int length)
