--------------------
Base64				|
--------------------
	# java.util.Base64
	# 注意,这个是JDK8的新特性,在JDK8中,Base64已经是 JDK的标准类库
	# 加密:传入的是字节数组,返回的是String
	# 解密:传入的是String,返回的是字节数组


--------------------
Base64实例方法/字段	|
--------------------


--------------------
Base64静态方法/字段	|
--------------------


--------------------
Base64-Demo			|
--------------------
	String text = "Base64 finally in Java 8!";
	String encoded = Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));		//加密
	System.out.println( encoded );	

	String decoded = new String(Base64.getDecoder().decode( encoded ),StandardCharsets.UTF_8 );			//解密
	System.out.println( decoded );
