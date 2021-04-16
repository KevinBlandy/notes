--------------------
WebSocketFrame
--------------------
	# Websocket ÏûÏ¢Ö¡£º  interface WebSocketFrame

	
	static WebSocketFrame binaryFrame(Buffer data, boolean isFinal)
	static WebSocketFrame textFrame(String str, boolean isFinal)
	static WebSocketFrame pingFrame(Buffer data)
	static WebSocketFrame pongFrame(Buffer data)
	static WebSocketFrame continuationFrame(Buffer data, boolean isFinal)

	WebSocketFrameType type();
	boolean isText();
	boolean isBinary();
	boolean isContinuation();
	boolean isClose();
	boolean isPing();
	String textData();
	Buffer binaryData();
	boolean isFinal();
	short closeStatusCode();
	String closeReason();
