---------------------------------
ByteBufUtil						 |
---------------------------------
	# �ṩ�˺ܶ�ľ�̬API���Բ���buf

	byte[] getBytes(ByteBuf buf, int start, int length, boolean copy)
		* ��ȡ�ֽ����飬�����޸�readerIndex
		* start ��ʼ�Ǳ꣬lengt ��ȡ����
		* copy Ĭ��true�������µ����顣���false�᳢��ͨ�����䣬����ͬһ�����飬�ײ㹲���������ʧ�ܣ���Ȼ��copy���飩

	String hexDump(ByteBuf buffer)
		* ����buffer��16�����ַ���,�����rindexȥ��ȡ�������޸�ReaderIndex

	String hexDump(byte[] array)
		* ���� byte[] ��16�����ַ���

	byte[] decodeHexDump(CharSequence hexDump)
		* ��16�����ַ���ת��Ϊ�ֽ�����
	
	
