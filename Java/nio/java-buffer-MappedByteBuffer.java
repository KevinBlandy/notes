-------------------------------
MappedByteBuffer				|
-------------------------------
	# ���ļ���ĳһ���ֻ�ȫ��ӳ�䵽�ڴ���, ӳ���ڴ滺�����Ǹ�ֱ�ӻ�����
	# ���̳�:ByteBuffer
	# ͨ�� FileChannel �� api ��ȡ
		MappedByteBuffer map(MapMode mode,long position, long size);

		MapMode
			READ_ONLY
				* ��ͼ�޸ĵõ��Ļ������������׳� ReadOnlyBufferException
			
			READ_WRITE
				* �Եõ��Ļ������ĸ������ս��������ļ�
				* �ø��Ķ�ӳ�䵽ͬһ�ļ�����������һ���ǿɼ���
			
			PRIVATE
				* �Եõ��Ļ������ĸ��Ĳ��ᴫ�����ļ�, ���Ҹø��Ķ�ӳ�䵽ͬһ�ļ�����������Ҳ���ǿɼ���
				* �ᴴ�����������޸Ĳ��ֵ�ר�ø���
		
	
	# ����
		public final MappedByteBuffer clear()
		public abstract MappedByteBuffer compact();
		public abstract MappedByteBuffer duplicate();
		public final MappedByteBuffer flip()
		public final MappedByteBuffer force()
		public final MappedByteBuffer force(int index, int length)
			* ��������READ_WRITEģʽ��, �˷����Ի��������ݵ��޸�ǿ��д���ļ�
		
		public final boolean isLoaded()
			* ����������������������ڴ���, �򷵻���, ���򷵻ؼ�
		
		public final MappedByteBuffer limit(int newLimit)
		public final MappedByteBuffer load()
			* load()�������������������ڴ�, �����ظû�����������
		
		public final MappedByteBuffer mark()
		public final MappedByteBuffer position(int newPosition)
		public final MappedByteBuffer reset()
		public final MappedByteBuffer rewind()
		public abstract MappedByteBuffer slice()
		public abstract MappedByteBuffer slice(int index, int length)

