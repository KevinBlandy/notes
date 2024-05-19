-------------------------------
ByteBuffer						|
-------------------------------
	# Buffer ����


-------------------------------
ByteBuffer-API					|
-------------------------------
	# ����
		

	# ʵ������
		//========================�����
		byte get();
			* ����һ��byte������
			* ÿ��ִ�� get(),������ position ��ǰ�ƶ�һλ

		ByteBuffer get(byte[] dst);
			* ��ȡN(dst.length)��Ԫ�ص� dst, position ����ǰ�ƶ�,����:'����ĳ���,���ܳ���:remaining,���׳��쳣'
			* ���鳤�ȴ��� buf �Ŀɶ�/дԪ��(remaining)����,�׳��쳣
			* ���鳤��С�� buf �Ŀɶ�/дԪ��(remaining)����,����װ������

		ByteBuffer get(byte[] dst, int offset, int length);
			* ��ȡlength��Ԫ�ص� dst, position ����ǰ�ƶ�,����:'length,���ܳ���:remaining,���׳��쳣'
			* offset ���鿪ʼд���λ��
			* length �ӵ�ǰposition��ȡ���ٸ��ֽ�����
		
		ByteBuffer get(int index, byte[] dst, int offset, int length) 
		ByteBuffer get(int index, byte[] dst)

		byte get(int index);
			* ���Զ�,��ȡbyteBuffer�ײ��bytes���±�Ϊindex��byte�����ı�position			
		
		byte[] array();
			* ��ȡ�������ڲ���������������

		//========================д���޸����
		ByteBuffer put(byte b);
			* д��һ���ֽ�,position��ǰ�ƶ�

		ByteBuffer put(int index, byte b);
			* ����д��,�����ƶ�postion,�����.�ᱻ�滻

		ByteBuffer put(byte[] src);
			* д��(src.length)��Ԫ�ص� buf, position ����ǰ�ƶ�,����:'����ĳ���,���ܳ���:remaining,���׳��쳣'

		ByteBuffer put(byte[] src, int offset, int length);
			* д��(length)��Ԫ�ص� buf, position ����ǰ�ƶ�,����:'length�ĳ���,���ܳ���:remaining,���׳��쳣'
			* offset �������ĸ��±꿪ʼд
			* length д���������

		ByteBuffer put(ByteBuffer src);
			* �� src �е�����д�뵽��ǰ buf,ָ����ǰ�ƶ�,����:'src��remaining,���ܳ�����ǰbuf:remaining,���׳��쳣'
			* ��ǰremaining����:����д����ٸ�����
			* src remaining����:���Զ�ȡ���ٸ�����
		
		ByteBuffer put(int index, ByteBuffer src, int offset, int length)
		ByteBuffer put(int index, byte[] src, int offset, int length)
		ByteBuffer put(int index, byte[] src) 

		//========================λ�������
		int remaining();
			* ���ص�ǰλ��������֮���Ԫ������
			* ��дģʽ��,����'ʣ���д'�Ŀռ�
			* �ڶ�ģʽ��,����'ʣ��ɶ�'�Ŀռ�
			* Դ��:limit - position;
		boolean	hasRemaining();
			* �ڶ�ģʽ��,�ж��Ƿ��л��пɶ���Ԫ��
			* ��дģʽ��,�ж��Ƿ񻹿���д���µ�Ԫ��
			* Դ��:position < limit;
		
		int	limit();
			* ����lmit
			* ��дģʽ��,���صľ���'������д�����'
			* �ڶ�ģʽ��,���صľ���'�����Զ�ȡ����'
		Buffer	limit(int newLimit);
			* �����µ� limit
		
		int	position();
			* ��ȡָ���λ��
		Buffer	position(int newPosition);
			* ��������ָ���λ��
		
		int	capacity();
			* ���ؿ��ٵ��ڴ��С

		Buffer clear();
			* ���,��û��ɾ������,�������޸���ָ��
			* Դ��:position = 0;limit = capacity;mark = -1;
			* '�������ڴӶ�ģʽ,�л���дģʽ'

		Buffer flip();
			* ��λ,�޸�дģʽΪ��
			* Դ��:limit = position;position = 0;mark = -1;
			* '�������ڴ�дģʽ,�л�����ģʽ'
		
		Buffer rewind();
			* ��position���0������������ض�Buffer�е��������ݡ�
			* limit���ֲ��䣬��Ȼ��ʾ�ܴ�Buffer�ж�ȡ���ٸ�Ԫ�أ�byte��char�ȣ���

		Buffer mark();
			* ��ǵ�ǰλ��,���Ա��Buffer�е�һ���ض�position��
			* ֮�����ͨ������Buffer.reset()�����ָ������position��

		Buffer reset();
			* ͨ������Buffer.mark()���������Ա��Buffer�е�һ���ض�position��
			* ֮�����ͨ������Buffer.reset()�����ָ������position��
		
		int mismatch(ByteBuffer that)

		int alignmentOffset(int index, int unitSize)

		ByteBuffer alignedSlice(int unitSize)


		//========================����
		byte[] array(); 
			* �Ѹ�Buffereת��Ϊͬ�ȳ��ȵ��ֽ�����
			* �յ�Ԫ��,Ĭ��ֵΪ0
		
		boolean	isDirect();
			* �жϵ�ǰ�Ļ������Ƿ���ֱ����ϵͳ�����ڴ�
		
		boolean	isReadOnly();
			* �жϻ������Ƿ���ֻ����

		ByteBuffer compact();
			* ��ջ�����,ֻ������Ѿ����������ݡ�
			* �κ�δ�������ݶ����Ƶ�����������ʼ��,��д������ݽ��ŵ�������δ�����ݵĺ��档
			* ������δ�������ݿ�����Buffer��ʼ����Ȼ��position�赽���һ��δ��Ԫ�������档
			* limit������Ȼ �� clear()����һ�������ó�capacity��
			* ����Buffer׼����д�����ˣ����ǲ��Ḳ��δ�������ݡ�
	
		int compareTo(ByteBuffer that);
			* compareTo()�����Ƚ�����Buffer��ʣ��Ԫ��(byte��char��),���������������������Ϊһ��Buffer'С��'��һ��Buffer��
			1,��һ������ȵ�Ԫ��С����һ��Buffer�ж�Ӧ��Ԫ�� ��
			2,����Ԫ�ض���ȣ�����һ��Buffer����һ���Ⱥľ�(��һ��Buffer��Ԫ�ظ�������һ����)

		boolean equals(Object ob);
			* ��������������ʱ����ʾ����Buffer��ȣ�
			* ����ͬ�����ͣ�byte��char��int�ȣ���
			* Buffer��ʣ���byte��char�ȵĸ�����ȡ�
			* Buffer������ʣ���byte��char�ȶ���ͬ��
			* ����������equalsֻ�ǱȽ�Buffer��һ���֣�����ÿһ�����������Ԫ�ض��Ƚϡ�ʵ���ϣ���ֻ�Ƚ�Buffer�е�ʣ��Ԫ�ء�
		
		ByteBuffer asReadOnlyBuffer();
			* ӳ���һ��ֻ���Ļ�����,'��������,���Զ���һ����'(�����޸�����,�����޸�����)
			* ��������������ݽ���ֻ�����ĸ����޸Ķ��޸�
			* ���ǵ�����ֵ����
		
		ByteBuffer duplicate();
			* ���Ƴ���һ��������,'��������,���Զ���һ����'(�����޸����Ժ�����)
			* �޸����ݶ�˫�����ǿɼ���
			* ���������ֻ��,��ô����Ҳ��ֻ��
			* ���ǵ�����ֵ����
		
		ByteBuffer slice(int index, int length);
		ByteBuffer slice();
			* ���Ƴ���һ���µĻ�����,(�����޸����Ժ�����)
			* �����޸�
				pos = 0;
				lim = 'ԭbuffer��limit';
				cap = 'ԭbuffer��limit'
			* �޸����ݶ�˫�����ǿɼ���
			* ���������ֻ��,��ô����Ҳ��ֻ��
			* ���ǵ�����ֵ����

		boolean hasArray();
			* 'δ֪'
		int arrayOffset(); 
			* 'δ֪'

		ByteOrder order();
			* ��ȡд��˳��
		ByteBuffer order(ByteOrder bo)��
			* ����д��˳��

		int hashCode();
		String toString(); 
		
		int getInt();
			* һ���Զ�ȡ�ĸ��ֽ�,���һ�� int ����
			* ָ�����ǰ�ƶ�4

		char getChar();
		char getChar(int index);
		double getDouble();
		double getDouble(int index);
		float getFloat(); 
		float getFloat(int index);
		int getInt(int index);
		long getLong();
		long getLong(int index);
		short getShort();
		short getShort(int index);
		
		ByteBuffer putInt(int value);
			* �� int ֵ����,һ���������ĸ��ֽ�
			* ָ�����ǰ�ƶ�4

		ByteBuffer putChar(char value);
		ByteBuffer putChar(int index, char value);
		ByteBuffer putDouble(double value);
		ByteBuffer putDouble(int index, double value);
		ByteBuffer putFloat(float value);
		ByteBuffer putFloat(int index, float value);
		ByteBuffer putInt(int index, int value);
		ByteBuffer putLong(int index, long value);
		ByteBuffer putLong(long value);
		ByteBuffer putShort(int index, short value);
		ByteBuffer putShort(short value);

		CharBuffer asCharBuffer();
		DoubleBuffer asDoubleBuffer();
		FloatBuffer asFloatBuffer();
		IntBuffer asIntBuffer();
		LongBuffer asLongBuffer();
		ShortBuffer asShortBuffer(); 


	# ��̬
		ByteBuffer allocateDirect(int capacity);
			* ����ָ����С�Ļ�����,��JVM�ڴ�,ֱ��ʹ��ϵͳ�ڴ�.
			* '���������ﵽһ����С��ʱ��Ż����ֳ��ٶ��ϵ�����'
		
		ByteBuffer allocate(int capacity);
			* ����ָ����С�Ļ�����
			* '�û���������JVM���з���'
		
		ByteBuffer wrap(byte[] array);
			* ��һ���ֽ�����װΪ ByteBuffer
			* �����bytes�ͻ������õ���һ���ڴ棬����޸Ļ��������������������Ҳ�����仯��
			* po : 0
			  li : array.length
			  ca : array.length

		ByteBuffer wrap(byte[] array, int offset, int length);
			* ��һ���ֽ�����װΪ ByteBuffer,���ֽ������ offset ��ʼ��ȡ length �����ݴ���
			* �����bytes�ͻ������õ���һ���ڴ棬����޸Ļ��������������������Ҳ�����仯��
			* '��AIP������Ϊ���޸�limit��λ��'
			* po : 0
			  li : length
			  ca : array.length

