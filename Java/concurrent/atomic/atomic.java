----------------------------
Atomic						|
----------------------------
	# java.util.concurrent.atomic �����ṩ��һЩԭ����

	# ʹ��ԭ�ӵķ�ʽ���»�������
		AtomicInteger
			* ����ԭ����
		AtomicLong
			* ������ԭ����
		AtomicBoolean 
			* ������ԭ����
		
		LongAdder
		LongAccumulator


	# ��������
		* ʹ��ԭ�ӵķ�ʽ�����������ĳ��Ԫ��

		AtomicIntegerArray
			* ��������ԭ����
		AtomicLongArray
			* ����������ԭ����
		AtomicReferenceArray 
			* ������������ԭ����

	# ��������
		AtomicReference
			* ��������ԭ����

		AtomicMarkableReference
			* ԭ�Ӹ��´��б��λ����������
		
		AtomicReferenceFieldUpdater
			* ԭ�Ӹ���������������ֶ�

	# ���������/�ֶ��޸�����

		AtomicIntegerFieldUpdater
			* ԭ�Ӹ��������ֶεĸ�����
		AtomicLongFieldUpdater
			* ԭ�Ӹ��³������ֶεĸ�����
		AtomicStampedReference 
			* ԭ�Ӹ��´��а汾�ŵ���������
			* ���ཫ����ֵ�����ù�������,�����ڽ��ԭ�ӵĸ������ݺ����ݵİ汾�ţ����Խ��ʹ�� CAS ����ԭ�Ӹ���ʱ���ܳ��ֵ� ABA ���⡣

