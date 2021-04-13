-----------------------
JsonPointer
-----------------------
	# RFC6901 的实现接口： interface JsonPointer
		https://tools.ietf.org/html/rfc6901


	
-----------------------
抽象
-----------------------
	boolean isRootPointer();
	boolean isLocalPointer();
	boolean isParent(JsonPointer child);
	String toString();
	URI toURI();
	URI getURIWithoutFragment();
	JsonPointer append(String token);
	JsonPointer append(int index);
	JsonPointer append(List<String> tokens);
	JsonPointer append(JsonPointer pointer);
	JsonPointer parent();
	default @Nullable Object query(Object objectToQuery, JsonPointerIterator iterator)
	Object queryOrDefault(Object objectToQuery, JsonPointerIterator iterator, Object defaultValue);

	default @Nullable Object queryJson(Object jsonElement)
	default @Nullable Object queryJsonOrDefault(Object jsonElement, Object defaultValue)

	List<Object> tracedQuery(Object objectToQuery, JsonPointerIterator iterator)

	Object write(Object objectToWrite, JsonPointerIterator iterator, Object newElement, boolean createOnMissing)
	default Object writeJson(Object jsonElement, Object newElement) { return writeJson(jsonElement, newElement, false)
	default Object writeJson(Object jsonElement, Object newElement, boolean createOnMissing)

	JsonPointer copy();


-----------------------
静态
-----------------------
	static JsonPointer create()
	JsonPointer from(String pointer)
	JsonPointer fromURI(URI uri) 
