---------------------------
SessionStore
---------------------------
	# Session存储实现接口： static SessionStore create(Vertx vertx)
	
	int DEFAULT_SESSIONID_LENGTH = 16;
	long retryTimeout();
	static SessionStore create(Vertx vertx, JsonObject options) 

	SessionStore init(Vertx vertx, JsonObject options)

	Session createSession(long timeout);
	Session createSession(long timeout, int length);
		* 创建新的session

	void get(String cookieValue, Handler<AsyncResult<@Nullable Session>> resultHandler);
	default Future<@Nullable Session> get(String cookieValue)
		* 根据cookie值读取session

	void delete(String id, Handler<AsyncResult<Void>> resultHandler)
	default Future<Void> delete(String cookieValue)
		* 删除session

	void put(Session session, Handler<AsyncResult<Void>> resultHandler)
	default Future<Void> put(Session session)
		* 存入session

	void clear(Handler<AsyncResult<Void>> resultHandler)
	default Future<Void> clear()
		* 删除所有session

	void size(Handler<AsyncResult<Integer>> resultHandler)
	default Future<Integer> size()
		* 获取存储的session的总数量

	void close();