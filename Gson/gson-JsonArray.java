------------------
JonsArray
------------------
	# JSON数组
		final class JsonArray extends JsonElement implements Iterable<JsonElement> 
		
		* 内部维护了一个 ArrayList<JsonElement>() 来存储对象

	# 构造方法
		JsonArray()
		JsonArray(int capacity)

	# 实例方法
		JsonElement get(int i)
			* 获取指定位置的元素
			* 可能会 IndexOutOfBoundsException
		
		
		Number getAsNumber()
		String getAsString()
		double getAsDouble()
		BigDecimal getAsBigDecimal()
		BigInteger getAsBigInteger()
		float getAsFloat()
		long getAsLong()
		int getAsInt()
		byte getAsByte()
		char getAsCharacter()
		short getAsShort()
		boolean getAsBoolean()
			* 这堆转换方法对于 JsonArray 来说
			* 只有在 size == 1 的试试, 会进行尝试转换
				if (elements.size() == 1) {
					return elements.get(0).getAsXxxx();
				}
				throw new IllegalStateException();
		
