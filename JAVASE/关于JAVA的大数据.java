JAVA中提供了操作大数字，的类。
java.math.BigInteger     java.math.BigDecimal
用于操作大正整数的类	 用于操作大小数的类
-------------------------------------------BigInteger
BigInteger b = new BigInteger(String);
	|--把整形数字作为字符串。当参数传递给构造器产生这个整数的十进制对象。
在对大数据进行加减乘除的操作的时候。被操作的数据类型。都是要是 BigInteger 。
BigDecimal d = new BigDecimal(String);
--------------------------------------
add(BigInteger vaule);			//加
subtract(BigInteger value);		//减
multiply(BigInteger value);		//乘
divide(BigInteger value);		//除
devideAndPemainder(BigInteger value);//返回商和余数的数组
