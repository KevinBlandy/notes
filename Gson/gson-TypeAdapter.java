--------------------
TypeAdapter
--------------------
	# public abstract class TypeAdapter<T>

	# ·½·¨
		public abstract void write(JsonWriter out, T value) throws IOException;
		public final void toJson(Writer out, T value) throws IOException 
		public final TypeAdapter<T> nullSafe()
		public final String toJson(T value)
		public final JsonElement toJsonTree(T value)

		public abstract T read(JsonReader in) throws IOException;

		public final T fromJson(Reader in) throws IOException
		public final T fromJson(String json) throws IOException	
		public final T fromJsonTree(JsonElement jsonTree)
