------------------------
MathContext				|
------------------------
	# ��Ҫ�������Ǵ�����������еľ�������
	# ��̬����
		MathContext UNLIMITED = new MathContext(0, RoundingMode.HALF_UP)
			* �����Ƴ���, ʹ����������ķ�ʽ������С�� 

		MathContext DECIMAL32 = new MathContext(7, RoundingMode.HALF_EVEN)
		MathContext DECIMAL64 = new MathContext(16, RoundingMode.HALF_EVEN)
		MathContext DECIMAL128 = new MathContext(34, RoundingMode.HALF_EVEN)
	
	# ���췽��
		MathContext(int setPrecision)
		MathContext(int setPrecision,RoundingMode setRoundingMode)
		MathContext(String val)

		setPrecision
			* ����,һ������ٳ���(����С��)
		
		setRoundingMode
			* ����ģʽ
	
	# ʵ������

		int getPrecision()
		RoundingMode getRoundingMode()
	

	# �ڳ˷��У���һ���ܱ�֤��ȷ�ľ�������

		BigDecimal number1 = new BigDecimal("0.01");
		BigDecimal number2 = new BigDecimal("0.2");

		// ���� 2 λ����
		BigDecimal result = number1.multiply(number2, new MathContext(2, RoundingMode.DOWN));

		// ���ȴ�� 3 λ
		// Result: 0.002
		System.out.println("Result: " + result);
		
		// ����ֱ���� setScale �޸ľ���
		// 0.00
		System.out.println(result.setScale(2, RoundingMode.DOWN));