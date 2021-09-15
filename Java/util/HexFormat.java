----------------------
HexFormat
----------------------
	# 十六进制和二进制的格式化工具
		public final class HexFormat
	
	# 方法
		public static HexFormat of()
		public static HexFormat ofDelimiter(String delimiter)

		public static boolean isHexDigit(int ch)
		public static int fromHexDigit(int ch)
		public static int fromHexDigits(CharSequence string)
		public static int fromHexDigits(CharSequence string, int fromIndex, int toIndex)
		public static long fromHexDigitsToLong(CharSequence string)
		public static long fromHexDigitsToLong(CharSequence string, int fromIndex, int toIndex)


		public HexFormat withDelimiter(String delimiter) 
		public HexFormat withPrefix(String prefix)
		public HexFormat withSuffix(String suffix) 
		public HexFormat withUpperCase() 
		public HexFormat withLowerCase()


		public String delimiter()
		public String prefix()
		public String suffix()
		public boolean isUpperCase()

		public String formatHex(byte[] bytes)
		public String formatHex(byte[] bytes, int fromIndex, int toIndex) 
		public <A extends Appendable> A formatHex(A out, byte[] bytes)
		public <A extends Appendable> A formatHex(A out, byte[] bytes, int fromIndex, int toIndex)

		public byte[] parseHex(CharSequence string)
		public byte[] parseHex(CharSequence string, int fromIndex, int toIndex)
		public byte[] parseHex(char[] chars, int fromIndex, int toIndex)
		
		public char toLowHexDigit(int value)
		public char toHighHexDigit(int value)

		public <A extends Appendable> A toHexDigits(A out, byte value)
		public String toHexDigits(byte value) 
		public String toHexDigits(char value)
		public String toHexDigits(short value)
		public String toHexDigits(int value)

		public String toHexDigits(long value)
		public String toHexDigits(long value, int digits)

	




