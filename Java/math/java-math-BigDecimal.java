------------------------
BigDecimal				|
------------------------
	# ��̬����
		public final static int ROUND_UP =           0;
		public final static int ROUND_DOWN =         1;
		public final static int ROUND_CEILING =      2;
		public final static int ROUND_FLOOR =        3;
		public final static int ROUND_HALF_UP =      4;
		public final static int ROUND_HALF_DOWN =    5;
		public final static int ROUND_HALF_EVEN =    6;
		public final static int ROUND_UNNECESSARY =  7;

			* ��������ģʽ
	# ���캯��
		BigDecimal(char[] in)
		BigDecimal(char[] in, int offset, int len)
		BigDecimal(char[] in, int offset, int len, MathContext mc)
		BigDecimal(char[] in, MathContext mc)
		BigDecimal(double val)
		BigDecimal(double val, MathContext mc)
		BigDecimal(int val)
		BigDecimal(int val, MathContext mc)
		BigDecimal(String val)
		BigDecimal(String val, MathContext mc)
		BigDecimal(BigInteger val)
		BigDecimal(BigInteger unscaledVal, int scale)
		BigDecimal(BigInteger unscaledVal, int scale, MathContext mc)
		BigDecimal(BigInteger val, MathContext mc)
		BigDecimal(long val)
		BigDecimal(long val, MathContext mc)
	

------------------------
BigDecimal-��̬����		|
------------------------
	BigDecimal valueOf(double val)
		* �����ͨ�� double ���� BigDecimal, ��ô����ֱ��ʹ�ù��캯��������, ���ܻ��о��ȶ�ʧ������
			BigDecimal val = new BigDecimal(0.1D); // 0.1000000000000000055511151231257827021181583404541015625
		
		* ����ʹ�øþ�̬����������
			BigDecimal val = BigDecimal.valueOf(0.1D) // 0.1
		
		* Դ��, �����Ͼ���ת��Ϊ�ַ��������� BigDecimal 
			public static BigDecimal valueOf(double val) {
				return new BigDecimal(Double.toString(val));
			}

	BigDecimal valueOf(long val)
	BigDecimal valueOf(long unscaledVal, int scale)


------------------------
BigDecimal-ʵ������		|
------------------------
	BigDecimal add(BigDecimal augend)
	BigDecimal add(BigDecimal augend, MathContext mc)
	
	BigDecimal subtract(BigDecimal subtrahend)
	BigDecimal subtract(BigDecimal subtrahend, MathContext mc)

	BigDecimal multiply(BigDecimal multiplicand)
	BigDecimal multiply(BigDecimal multiplicand, MathContext mc)

	BigDecimal divide(BigDecimal divisor)
	BigDecimal divide(BigDecimal divisor, int roundingMode)
	BigDecimal divide(BigDecimal divisor, int scale, int roundingMode)
	BigDecimal divide(BigDecimal divisor, int scale, RoundingMode roundingMode)
	BigDecimal divide(BigDecimal divisor, MathContext mc)
	BigDecimal divide(BigDecimal divisor, RoundingMode roundingMode)
		* �Ӽ��˳�
			newScale					��ʾ������С��λ��
			roundingMode				ָ������ģʽ(������int����ö��)
			mc							ͨ��ָ���� MathContext �����Ⱥ�����
	
	BigDecimal remainder(BigDecimal divisor, MathContext mc)
	BigDecimal remainder(BigDecimal divisor) 
	BigDecimal[] divideAndRemainder(BigDecimal divisor) 
		* ���ص������������BigDecimal, �ֱ����̺�����, ��������������, ����������ڳ���
		* ����������������ж�����BigDecimal�Ƿ�����������
			BigDecimal n = new BigDecimal("12.75");
			BigDecimal m = new BigDecimal("0.15");
			BigDecimal[] dr = n.divideAndRemainder(m);
			if (dr[1].signum() == 0) {
				// n��m��������
			}

	boolean equals(Object x) 
		* �Ƚ���ȵ�ʱ��, ������Ҫ�Ƚ�ֵ, ��ĩβ��0����ҲҪһ��
			new BigDecimal("123.456").equals(new BigDecimal("123.45600")) // false

	int compareTo(BigDecimal val)
		* �Ƚϴ�С

	int scale()
		* ����С��λ��, ��ֵ������������0
			new BigDecimal("123.4500").scale() // 4

	BigDecimal setScale(int newScale, int roundingMode)
		* ��ʽ��С��
			newScale		��ʾ������С��λ��
			roundingMode	����ģʽ
		
	
	BigDecimal stripTrailingZeros()
		* ���Խ�һ��BigDecimal��ʽ��Ϊһ����ȵ�, ��ȥ����ĩβ0��BigDecimal
		* ֵ�����ܵ�Ӱ��, �ܵ�Ӱ����� scale

	long longValue()
		* ת��Ϊlong, �����С���ᱻ����, ������Χ�����

	long longValueExact()
		* ת����long��, �����С��, ���߳����˷�Χ, ���׳��쳣	
	
	String toString();
		* �б�Ҫʱʹ�ÿ�ѧ������

	String toPlainString();
		* ��ʹ���κ�ָ��

	String toEngineeringString();
		* �б�Ҫʱʹ�ù��̼�����
		* ���̼�������һ�ֹ��̼����о���ʹ�õļ�¼���ֵķ���, ���ѧ����������. ��Ҫ��10���ݱ�����3�ı���
 
	BigInteger unscaledValue()
	BigDecimal ulp()
	int signum()
	int precision()
	BigDecimal plus(MathContext mc)
	BigDecimal plus()
	BigDecimal negate(MathContext mc)
	BigDecimal negate()


-------------------
Ҫ��
-------------------
	# BigDecimal �Ƚ������ compareTo
		BigDecimal v1 = new BigDecimal("5.0");
		BigDecimal v2 = new BigDecimal("5");
		System.out.println(v1.equals(v2));			// false
		System.out.println(v1.compareTo(v2));		// 0