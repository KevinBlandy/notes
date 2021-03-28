--------------------------------
AbstractVerticle
--------------------------------
	# abstract class AbstractVerticle implements Verticle

--------------------------------
Method
--------------------------------
	public Vertx getVertx() 
	public void init(Vertx vertx, Context context)
	public String deploymentID()
	public JsonObject config()
	public List<String> processArgs(
	public void start(Promise<Void> startPromise) throws Exception
	public void stop(Promise<Void> stopPromise) throws Exception
		

	public void start() throws Exception
	public void stop() throws Exception

