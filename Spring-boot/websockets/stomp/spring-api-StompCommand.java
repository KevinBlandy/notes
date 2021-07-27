----------------------
StompCommand
----------------------
	# ��Ϣ����ö��

	# ʵ��
		// client
		STOMP(SimpMessageType.CONNECT),
		CONNECT(SimpMessageType.CONNECT),
		DISCONNECT(SimpMessageType.DISCONNECT),
		SUBSCRIBE(SimpMessageType.SUBSCRIBE, true, true, false),
		UNSUBSCRIBE(SimpMessageType.UNSUBSCRIBE, false, true, false),
		SEND(SimpMessageType.MESSAGE, true, false, true),
		ACK(SimpMessageType.OTHER),
		NACK(SimpMessageType.OTHER),
		BEGIN(SimpMessageType.OTHER),
		COMMIT(SimpMessageType.OTHER),
		ABORT(SimpMessageType.OTHER),

		// server
		CONNECTED(SimpMessageType.OTHER),
		RECEIPT(SimpMessageType.OTHER),
		MESSAGE(SimpMessageType.MESSAGE, true, true, true),
		ERROR(SimpMessageType.OTHER, false, false, true);

	
	# ����
		private final SimpMessageType messageType;
		private final boolean destination;
		private final boolean subscriptionId;
		private final boolean body;

----------------------
SimpMessageType
----------------------
	# ��Ϣ����
		CONNECT,
		CONNECT_ACK,
		MESSAGE,
		SUBSCRIBE,
		UNSUBSCRIBE,
		HEARTBEAT,
		DISCONNECT,
		DISCONNECT_ACK,
		OTHER