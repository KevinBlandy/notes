GlobalMemory 内存
	long getTotal();		// 总大小
	long getAvailable();	// 可用的
	long getPageSize();		// 页大小
	VirtualMemory getVirtualMemory(); // 虚拟内存信息
	PhysicalMemory[] getPhysicalMemory(); // 物理内存信息

VirtualMemory
    long getSwapTotal();		// 总大小
    long getSwapUsed();			// 已经使用的
    long getSwapPagesIn();
    long getSwapPagesOut();

PhysicalMemory
	private final String bankLabel;
    private final long capacity;
    private final long clockSpeed;
    private final String manufacturer;
    private final String memoryType;

