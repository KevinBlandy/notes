------------------------
CloseStatus
------------------------
	# 关闭状态封装
	
	# 预定义
		public static final CloseStatus NORMAL = new CloseStatus(1000);
		public static final CloseStatus GOING_AWAY = new CloseStatus(1001);
		public static final CloseStatus PROTOCOL_ERROR  = new CloseStatus(1002);
		public static final CloseStatus NOT_ACCEPTABLE = new CloseStatus(1003);
		public static final CloseStatus NO_STATUS_CODE = new CloseStatus(1005);
		public static final CloseStatus NO_CLOSE_FRAME = new CloseStatus(1006);
		public static final CloseStatus BAD_DATA = new CloseStatus(1007);
		public static final CloseStatus POLICY_VIOLATION = new CloseStatus(1008);
		public static final CloseStatus TOO_BIG_TO_PROCESS = new CloseStatus(1009);
		public static final CloseStatus REQUIRED_EXTENSION = new CloseStatus(1010);
		public static final CloseStatus SERVER_ERROR = new CloseStatus(1011);
		public static final CloseStatus SERVICE_RESTARTED = new CloseStatus(1012);
		public static final CloseStatus SERVICE_OVERLOAD = new CloseStatus(1013);
		public static final CloseStatus TLS_HANDSHAKE_FAILURE = new CloseStatus(1015);
		public static final CloseStatus SESSION_NOT_RELIABLE = new CloseStatus(4500);
	
	# 构造方法
		public CloseStatus(int code)
		public CloseStatus(int code, @Nullable String reason) 

