---------------------
JsonArray
---------------------
	# Json数组的实现
		public class JsonArray implements Iterable<Object>, ClusterSerializable, Shareable

---------------------
构造
---------------------
	public JsonArray(String json)
	public JsonArray()
	public JsonArray(List list)
	public JsonArray(Buffer buf)


---------------------
实例
---------------------
	 public String getString(int pos)
	 public Number getNumber(int pos)
	 public Integer getInteger(int pos)
	 public Long getLong(int pos)
	 public Double getDouble(int pos)
	 public Float getFloat(int pos)
	 public Boolean getBoolean(int pos)
	 public JsonObject getJsonObject(int pos)
	 public JsonArray getJsonArray(int pos)
	 public byte[] getBinary(int pos)
	 public Buffer getBuffer(int pos)
	 public Instant getInstant(int pos)
	 public Object getValue(int pos)
	 public boolean hasNull(int pos)
	 public JsonArray addNull()
	 public JsonArray add(Object value)
	 public JsonArray add(int pos, Object value)
	 public JsonArray addAll(JsonArray array)
	 public JsonArray setNull(int pos)
	 public JsonArray set(int pos, Object value)
	 public boolean contains(Object value
	 public boolean remove(Object value)
	 public Object remove(int pos)
	 public int size()
	 public boolean isEmpty()
	 public List getList()
	 public JsonArray clear()
	 public Iterator<Object> iterator()
	 public String encode()
	 public Buffer toBuffer()
	 public String encodePrettily()
	 public JsonArray copy()
	 public JsonArray copy(Function<Object, ?> cloner)
	 public Stream<Object> stream()
	 public String toString()
	 public boolean equals(Object o)
	 public int hashCode()
	 public void writeToBuffer(Buffer buffer)
	 public int readFromBuffer(int pos, Buffer buffer)


---------------------
静态
---------------------