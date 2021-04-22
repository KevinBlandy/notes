--------------------------
SockJSHandlerOptions
--------------------------
	# Socketjs配置类： class SockJSHandlerOptions

--------------------------
Constructor
--------------------------
	public SockJSHandlerOptions(SockJSHandlerOptions other)
	public SockJSHandlerOptions()
	public SockJSHandlerOptions(JsonObject json) 

--------------------------
this
--------------------------
	public long getSessionTimeout()
	public SockJSHandlerOptions setSessionTimeout(long sessionTimeout)
	public boolean isInsertJSESSIONID()
	public SockJSHandlerOptions setInsertJSESSIONID(boolean insertJSESSIONID)
	public long getHeartbeatInterval()
	public SockJSHandlerOptions setHeartbeatInterval(long heartbeatInterval)
	public int getMaxBytesStreaming()
	public SockJSHandlerOptions setMaxBytesStreaming(int maxBytesStreaming)
	public String getLibraryURL()
	public SockJSHandlerOptions setLibraryURL(String libraryURL) 
	public SockJSHandlerOptions addDisabledTransport(String subProtocol)
	public Set<String> getDisabledTransports()
	public boolean isRegisterWriteHandler()
	public SockJSHandlerOptions setRegisterWriteHandler(boolean registerWriteHandler)
	public boolean isLocalWriteHandler()
	public SockJSHandlerOptions setLocalWriteHandler(boolean localWriteHandler)

--------------------------
static
--------------------------
  public static final long DEFAULT_SESSION_TIMEOUT = 5L * 1000;
  	* session 超时时间

  public static final boolean DEFAULT_INSERT_JSESSIONID = true;

  public static final long DEFAULT_HEARTBEAT_INTERVAL = 25L * 1000;	
	* 心跳间隔

  public static final int DEFAULT_MAX_BYTES_STREAMING = 128 * 1024;
  public static final String DEFAULT_LIBRARY_URL = "//cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js";
  public static final boolean DEFAULT_REGISTER_WRITE_HANDLER = false;
  public static final boolean DEFAULT_LOCAL_WRITE_HANDLER = true;