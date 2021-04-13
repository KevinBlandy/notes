----------------------------
JsonPointerIterator
----------------------------
	# 自定义实现的接口	interface JsonPointerIterator 

----------------------------
抽象
----------------------------
	boolean isObject(@Nullable Object currentValue);
	boolean isArray(@Nullable Object currentValue);
	boolean isNull(@Nullable Object currentValue);
	boolean objectContainsKey(@Nullable Object currentValue, String key);
	Object getObjectParameter(@Nullable Object currentValue, String key, boolean createOnMissing);
	Object getArrayElement(@Nullable Object currentValue, int i);
	boolean writeObjectParameter(@Nullable Object currentValue, String key, @Nullable Object value);
	boolean writeArrayElement(@Nullable Object currentValue, int i, @Nullable Object value);
	boolean appendArrayElement(@Nullable Object currentValue, @Nullable Object value);


----------------------------
静态
----------------------------
	JsonPointerIterator JSON_ITERATOR = new JsonPointerIteratorImpl();
		* 预定义的实现