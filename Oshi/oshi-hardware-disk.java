HWDiskStore 
	private String model = "";
    private String name = "";
    private String serial = "";
    private long size = 0L;
    private long reads = 0L;
    private long readBytes = 0L;
    private long writes = 0L;
    private long writeBytes = 0L;
    private long currentQueueLength = 0L;
    private long transferTime = 0L;
    private HWPartition[] partitions = new HWPartition[0];
    private long timeStamp = 0L;

	boolean updateAtrributes()		// 更新数据

HWPartition
	private String identification;
    private String name;
    private String type;
    private String uuid;
    private long size;
    private int major;
    private int minor;
    private String mountPoint;