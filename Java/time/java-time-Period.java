------------------------------
Period							|
------------------------------
	# ������������֮��ļ��
		* �� ISO-8601 ����ϵͳ��������Ϊ������ʱ�������� "2 �� 3 ������ 4 ��"��
		* ���������ꡢ�¡���Ϊ��λ��ʱ����ģ�͡�


		
	# ����(ʹ��Preiod��̬����)
		Preiod between(LocalDate locaDate1,LocalDate localDate2);
			* ������������֮��ļ��
				Period period = Period.between(LocalDate.of(2024, 01, 01), LocalDate.of(2025, 04, 03));
		
				// ��� 1 �� 3 �� 2 ��
				System.out.printf("��� %d �� %d �� %d ��\n", period.getYears(), period.getMonths(), period.getDays());
	

------------------------------
Period-API						|
------------------------------
	getYears();
		* ��ȡ���
	getMonths();
		* ��ȡ�·�
	getDays();
		* ��ȡ����

	