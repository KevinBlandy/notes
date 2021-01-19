---------------------------
BigInteger				   |
---------------------------
	# 静态方法
		BigInteger probablePrime(int bitLength, Random rnd)
		BigInteger valueOf(long val)

	# 构造函数
		BigInteger(byte[] val)
		BigInteger(int signum, byte[] magnitude)
		BigInteger(int bitLength, int certainty, Random rnd)
		BigInteger(int numBits, Random rnd)
		BigInteger(String val)
		BigInteger(String val, int radix)
	
	# 实例方法
		BigInteger abs()

		BigInteger add(BigInteger val)
		BigInteger subtract(BigInteger val)
		BigInteger multiply(BigInteger val)
		BigInteger divide(BigInteger val)
			* 加减乘除

		BigInteger and(BigInteger val)
		BigInteger andNot(BigInteger val)
		int bitCount()
		int bitLength()
		byte byteValueExact()
		BigInteger clearBit(int n)
		BigInteger[] divideAndRemainder(BigInteger val)
		double doubleValue()
		float floatValue()
		BigInteger flipBit(int n)
		BigInteger gcd(BigInteger val)
		int getLowestSetBit()
		int intValue()
		int intValueExact()
		boolean isProbablePrime(int certainty)
		long longValue()
		long longValueExact()

		BigInteger max(BigInteger val)
		BigInteger min(BigInteger val)
		BigInteger mod(BigInteger m)
		BigInteger modInverse(BigInteger m) 
		BigInteger modPow(BigInteger exponent, BigInteger m) 
		BigInteger negate()
		BigInteger nextProbablePrime()
		BigInteger not()
		BigInteger or(BigInteger val)
		BigInteger pow(int exponent) 
		BigInteger remainder(BigInteger val)
		BigInteger setBit(int n)
		BigInteger shiftLeft(int n)
		BigInteger shiftRight(int n)
		short shortValueExact() 
		int signum()
		boolean testBit(int n)
		byte[] toByteArray()
		String toString(int radix)
		BigInteger xor(BigInteger val)