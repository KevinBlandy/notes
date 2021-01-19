NetworkIF 
	private NetworkInterface networkInterface;
    private int mtu;
    private String mac;
    private String[] ipv4;
    private Short[] subnetMasks;
    private String[] ipv6;
    private Short[] prefixLengths;
    private long bytesRecv;
    private long bytesSent;
    private long packetsRecv;
    private long packetsSent;
    private long inErrors;
    private long outErrors;
    private long inDrops;
    private long collisions;
    private long speed;
    private long timeStamp;

	
	boolean updateAttributes()  // 更新数据


NetworkInterface 网络接口
	private String name;
    private String displayName;
    private int index;
    private InetAddress addrs[];
    private InterfaceAddress bindings[];
    private NetworkInterface childs[];
    private NetworkInterface parent = null;
    private boolean virtual = false;
    private static final NetworkInterface defaultInterface;
    private static final int defaultIndex; /* index of defaultInterface */
	