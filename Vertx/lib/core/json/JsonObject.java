---------------------------
JsonObject
---------------------------
	# Json对象的实现
		JsonObject implements Iterable<Map.Entry<String, Object>>, ClusterSerializable, Shareable
	
---------------------------
构造
---------------------------
	public JsonObject()
	public JsonObject(String json)
	public JsonObject(Map<String, Object> map)
	public JsonObject(Buffer buf)

---------------------------
实例
---------------------------
	public <T> T mapTo(Class<T> type)
		* 把Json数据转换为自定义对象

	public String getString(String key)
	public Number getNumber(String key)
	public Integer getInteger(String key)
	public Long getLong(String key)
	public Double getDouble(String key)
	public Float getFloat(String key)
	public Boolean getBoolean(String key)
	public JsonObject getJsonObject(String key)
	public JsonArray getJsonArray(String key)
	public byte[] getBinary(String key)
	public Buffer getBuffer(String key)
	public Instant getInstant(String key)
	public Object getValue(String key)
	public String getString(String key, String def)
	public Number getNumber(String key, Number def)
	public Integer getInteger(String key, Integer def)
	public Long getLong(String key, Long def)
	public Double getDouble(String key, Double def)
	public Float getFloat(String key, Float def)
	public Boolean getBoolean(String key, Boolean def)
	public JsonObject getJsonObject(String key, JsonObject def)
	public JsonArray getJsonArray(String key, JsonArray def)
	public byte[] getBinary(String key, byte[] def)
	public Buffer getBuffer(String key, Buffer def)
	public Instant getInstant(String key, Instant def)
	public Object getValue(String key, Object def)
	public boolean containsKey(String key)
	public Set<String> fieldNames()
	public JsonObject putNull(String key)
	public JsonObject put(String key, Object value)
	public Object remove(String key)
	public JsonObject mergeIn(JsonObject other)
	public JsonObject mergeIn(JsonObject other, boolean deep)
	public JsonObject mergeIn(JsonObject other, int depth)

	public String encode()
	public String encodePrettily()
		* 输出json字符串，encodePrettily 会格式化输出

	public Buffer toBuffer()
	public JsonObject copy()
	public JsonObject copy(Function<Object, ?> cloner)
	public Map<String, Object> getMap()
	public Stream<Map.Entry<String, Object>> stream()
	public Iterator<Map.Entry<String, Object>> iterator()
	public int size()
	public JsonObject clear()
	public boolean isEmpty()
	public String toString()
	public boolean equals(Object o)
	public int hashCode()
	public void writeToBuffer(Buffer buffer)
	public int readFromBuffer(int pos, Buffer buffer)

	

---------------------------
静态
---------------------------
	public static JsonObject mapFrom(Object obj)
