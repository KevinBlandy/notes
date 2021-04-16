----------------------
NetworkOptions
----------------------
	# 网络服务的基本抽象配置类： abstract class NetworkOptions
		* 应该是基本通信协议中定义的配置

----------------------
构造
----------------------
	public NetworkOptions() 
	public NetworkOptions(NetworkOptions other)
	public NetworkOptions(JsonObject json) 


----------------------
实例
----------------------
	public JsonObject toJson()
	public int getSendBufferSize()
	public NetworkOptions setSendBufferSize(int sendBufferSize)
	public int getReceiveBufferSize()
	public NetworkOptions setReceiveBufferSize(int receiveBufferSize)
	public boolean isReuseAddress()
	public NetworkOptions setReuseAddress(boolean reuseAddress)
	public int getTrafficClass()
	public NetworkOptions setTrafficClass(int trafficClass)
	public boolean getLogActivity()
	public NetworkOptions setLogActivity(boolean logActivity)
	public boolean isReusePort(
	public NetworkOptions setReusePort(boolean reusePort)


----------------------
静态
----------------------
	public static final int DEFAULT_SEND_BUFFER_SIZE = -1;
	public static final int DEFAULT_RECEIVE_BUFFER_SIZE = -1;
	public static final int DEFAULT_TRAFFIC_CLASS = -1;
	public static final boolean DEFAULT_REUSE_ADDRESS = true;
	public static final boolean DEFAULT_REUSE_PORT = false;
	public static final boolean DEFAULT_LOG_ENABLED = false;
