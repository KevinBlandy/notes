----------------------
HexFormat
----------------------
	# 十六进制和二进制的格式化工具
		public final class HexFormat
	
	# 方法
		public static HexFormat of()
		public static HexFormat ofDelimiter(String delimiter)
			* 创建实例，可以指定分隔符

		public static boolean isHexDigit(int ch)
			* 是否是一个十六进制数值

		public static int fromHexDigit(int ch)
		public static int fromHexDigits(CharSequence string)
		public static int fromHexDigits(CharSequence string, int fromIndex, int toIndex)

		public static long fromHexDigitsToLong(CharSequence string)
		public static long fromHexDigitsToLong(CharSequence string, int fromIndex, int toIndex)


		public HexFormat withDelimiter(String delimiter) 
			* 设置分隔符

		public HexFormat withPrefix(String prefix)
			* 前缀
		public HexFormat withSuffix(String suffix) 
			* 后缀
		public HexFormat withUpperCase() 
		public HexFormat withLowerCase()
			* 大小写


		public String delimiter()
		public String prefix()
		public String suffix()
		public boolean isUpperCase()

		public String formatHex(byte[] bytes)
		public String formatHex(byte[] bytes, int fromIndex, int toIndex) 
		public <A extends Appendable> A formatHex(A out, byte[] bytes)
		public <A extends Appendable> A formatHex(A out, byte[] bytes, int fromIndex, int toIndex)
			* 编码为字符串

		public byte[] parseHex(CharSequence string)
		public byte[] parseHex(CharSequence string, int fromIndex, int toIndex)
		public byte[] parseHex(char[] chars, int fromIndex, int toIndex)
			* 解码为字节数组
		
		public char toLowHexDigit(int value)
		public char toHighHexDigit(int value)

		public <A extends Appendable> A toHexDigits(A out, byte value)
		public String toHexDigits(byte value) 
		public String toHexDigits(char value)
		public String toHexDigits(short value)
		public String toHexDigits(int value)

		public String toHexDigits(long value)
		public String toHexDigits(long value, int digits)

	

----------------------
HexFormat
----------------------
	# 编码
		MessageDigest sha256 = MessageDigest.getInstance("SHA256");
		
		sha256.update("123456".getBytes());
		
		byte[] sign = sha256.digest();
		
		String signHex = HexFormat.of()
					.withUpperCase()	// 大写
					.formatHex(sign);
		
		System.out.println(signHex);