--------------------------------
 Session
 --------------------------------
 	# Session接口： interface Session 

	Session regenerateId();
	String id();
	Session put(String key, Object obj);
	Session putIfAbsent(String key, Object obj);
	Session computeIfAbsent(String key, Function<String, Object> mappingFunction);
	<T> T get(String key);
	<T> T remove(String key);
	Map<String, Object> data();
	boolean isEmpty();
	long lastAccessed();
	void destroy();
	boolean isDestroyed();
	boolean isRegenerated();
	String oldId();
	long timeout();
	void setAccessed();
		* 手动标记Session已经访问过
		* Session被查找以及响应完成且会话被存储回存储中时，会话将被自动标记为已访问。

	default String value()

