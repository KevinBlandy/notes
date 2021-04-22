-----------------------
SockJSBridgeOptions
-----------------------
	# ≈‰÷√¿‡£∫ class SockJSBridgeOptions extends BridgeOptions

	public static final int DEFAULT_MAX_ADDRESS_LENGTH = 200;
	public static final int DEFAULT_MAX_HANDLERS_PER_SOCKET = 1000;
	public static final long DEFAULT_PING_TIMEOUT = 10 * 1000;
	public static final long DEFAULT_REPLY_TIMEOUT = 30 * 1000;

	public SockJSBridgeOptions(SockJSBridgeOptions other) 
	public SockJSBridgeOptions()
	public SockJSBridgeOptions(JsonObject json)
	public int getMaxAddressLength()

	public SockJSBridgeOptions setMaxAddressLength(int maxAddressLength)
	public int getMaxHandlersPerSocket()
	public SockJSBridgeOptions setMaxHandlersPerSocket(int maxHandlersPerSocket) 
	public long getPingTimeout()
	public SockJSBridgeOptions setPingTimeout(long pingTimeout)
	public long getReplyTimeout()
	public SockJSBridgeOptions setReplyTimeout(long replyTimeout)
	public SockJSBridgeOptions addInboundPermitted(PermittedOptions permitted)
	public SockJSBridgeOptions setInboundPermitteds(List<PermittedOptions> inboundPermitted)
	public SockJSBridgeOptions addOutboundPermitted(PermittedOptions permitted)
	public SockJSBridgeOptions setOutboundPermitteds(List<PermittedOptions> outboundPermitted)
	public JsonObject toJson()
