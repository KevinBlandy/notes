----------------------------
Instant						|
----------------------------
	# ʱ���������
	# ��̬�Ĺ��췽��
		Instant now();
			* ���ص�ǰʱ�������
			* Ĭ��ʱ���ȡ����UTCʱ��(��������ʱ��)Ϊ������

		Instant ofEpochSecond(long sencend);
		Instant ofEpochSecond(long epochSecond, long nanoAdjustment)
			 * ͨ���룬����������
		
		Instant ofEpochMilli(long epochMilli)
			* ͨ������������

----------------------------
Instant-API					|
----------------------------
	OffsetDateTime atOffset(ZoneOffset zoneOffset);
		* ���ش�ƫ����(ʱ��)��ʱ������

	long toEpochMilli();
		* ����ʱ�����Ӧ�ĺ���ֵ
	
	public long getEpochSecond()
		* ����ʱ�����Ӧ����

	public int getNano()
		* ���ص�ǰ���Ժ��������
		* ��������������19λ���ȣ�java��long�治��
		* ����һ����ʱ�䡱������Ϊ2���֣��룬����������
			
	

