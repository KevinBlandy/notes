----------------------------------
JsonWriter
----------------------------------
	# public class JsonWriter implements Closeable, Flushable 
		* json写接口
	
	# 方法
		public JsonWriter(Writer out) 

		public final void setIndent(String indent) 
		public final void setLenient(boolean lenient) 
		public boolean isLenient()
		public final void setHtmlSafe(boolean htmlSafe) 
		public final boolean isHtmlSafe()
		public final void setSerializeNulls(boolean serializeNulls)
		public final boolean getSerializeNulls() 

		public JsonWriter beginArray() throws IOException
		public JsonWriter endArray() throws IOException
		public JsonWriter beginObject() throws IOException
		public JsonWriter endObject() throws IOException 
		public JsonWriter name(String name) throws IOException
		public JsonWriter value(String value) throws IOException 
		public JsonWriter jsonValue(String value) throws IOException
		public JsonWriter nullValue() throws IOException
		public JsonWriter value(boolean value) throws IOException
		public JsonWriter value(Boolean value) throws IOException
		public JsonWriter value(double value) throws IOException 
		public JsonWriter value(long value) throws IOException
		public JsonWriter value(Number value) throws IOException

		public void flush() throws IOException
		public void close() throws IOException
