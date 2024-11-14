------------------------
DecimalFormat			|
------------------------
	# ǿ������ָ�ʽ������, �̳�:NumberFormat
	# DecimalFormat ����Ҫ�� # �� 0 ����ռλ������ָ�����ֳ��ȡ�
	# 0 ��ʾ���λ���������� 0 ��䣬# ��ʾֻҪ�п��ܾͰ������������λ�á�

	# ����
	  // �����޲������죬�в������� �������﷨������ײ����ο�"0.0"��"0.0��"��"0.0%"��
		DecimalFormat df = new DecimalFormat();
	  // ���ù��һ��ҷ��� ����ΪISO 4217��׼��������������ӡ��衯���ţ��ο�--> 5211314��
		df.setCurrency(Currency.getInstance("CNY"));
	  // ������ౣ����λ.�ο�--> 5211314.00
		df.setMaximumFractionDigits(2);
	  // ���÷����С.�ο�--> 5,211,314
		df.setGroupingSize(3);
	  // ���ó��Եı���.�ο�--> 521131400
		df.setMultiplier(100);
	  // ��������ǰ׺,�ο�--> @5211314
		df.setPositivePrefix("@");
	  // ����������׺ �����滻�� �����ַ� �ο�--> 5211314@
		df.setPositiveSuffix("@");
	  // ���ø���ǰ׺,ֻ�Ը�����Ч   �ο�-->@-1
		df.setNegativePrefix("@");
	  // ���ø�����׺ �����滻�� �����ַ�  ֻ�Ը�����Ч  �ο�--> -1@
		df.setNegativeSuffix("@");
	  // �������������ģʽ ��� RoundingMode �� д�� �ǳ���ϸ
		df.setRoundingMode(RoundingMode.DOWN);
	  // ��ʽ�� �� ��Ҫ�Ľ��
		df.format(5211314);

------------------------
demo					|
------------------------
		double pi=3.1415927;��//Բ����  
��������//ȡһλ����  
��������System.out.println(new DecimalFormat("0").format(pi));������//3  

��������//ȡһλ��������λС��  
��������System.out.println(new DecimalFormat("0.00").format(pi));��//3.14  

��������//ȡ��λ��������λС�����������㲿����0���  
��������System.out.println(new DecimalFormat("00.000").format(pi));// 03.142  

��������//ȡ������������  
��������System.out.println(new DecimalFormat("#").format(pi));������//3  

��������//�԰ٷֱȷ�ʽ��������ȡ��λС��  
��������System.out.println(new DecimalFormat("#.##%").format(pi));��//314.16%  
����  
		long c = 299792458;����//����  

��������//��ʾΪ��ѧ����������ȡ��λС��  
��������System.out.println(new DecimalFormat("#.#####E0").format(c));��//2.99792E8  

��������//��ʾΪ��λ�����Ŀ�ѧ����������ȡ��λС��  
��������System.out.println(new DecimalFormat("00.####E0").format(c));��//29.9792E7  

��������//ÿ��λ�Զ��Ž��зָ���  
��������System.out.println(new DecimalFormat(",###").format(c));������//299,792,458  

��������//����ʽǶ���ı�  
��������System.out.println(new DecimalFormat("���ٴ�СΪÿ��,###�ס�").format(c));  

	# DecimalFormat ����Ҫ�� # �� 0 ����ռλ������ָ�����ֳ��ȡ�
	# 0 ��ʾ���λ���������� 0 ��䣬# ��ʾֻҪ�п��ܾͰ������������λ�á�

	

	# ������ָ���ָ���

		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator('.');  // �ָ���
        symbols.setDecimalSeparator('.');		// С����ָ���
		return new DecimalFormat("#,###", symbols).format(val);