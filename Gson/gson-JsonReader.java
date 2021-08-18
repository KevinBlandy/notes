----------------------
JsonReader
----------------------
	# public class JsonReader implements Closeable 
		* json的解析读取
	
	# 方法
		public JsonReader(Reader in) 
		public final void setLenient(boolean lenient)
		public final boolean isLenient()
		public void beginArray() throws IOException 
		public void endArray() throws IOException 
		public void beginObject() throws IOException
		public void endObject() throws IOException 
		public boolean hasNext() throws IOException 
		public JsonToken peek() throws IOException 
		public String nextName() throws IOException
		public String nextString() throws IOException 
		public boolean nextBoolean() throws IOException
		public void nextNull() throws IOException
		public double nextDouble() throws IOException 
		public long nextLong() throws IOException
		public int nextInt() throws IOException
		public void close() throws IOException
		public void skipValue() throws IOException
		public String getPath()
