CentralProcessor CPU
	ProcessorIdentifier getProcessorIdentifier(); 
	long getMaxFreq();			// 最大频率，单位是HZ，不支持返回 -1
	long[] getCurrentFreq();			// 返回每个核的最大频率，单位是HZ
	LogicalProcessor[] getLogicalProcessors();		// 返回逻辑单元

	long[] getSystemCpuLoadTicks();		// 获取系统范围的CPU负载计时计数器。返回包含七个元素的数组
	double getSystemCpuLoadBetweenTicks(long[] oldTicks);	// 获取负载信息
	double[] getSystemLoadAverage(int nelem);		// 系统平均负载
	double[] getProcessorCpuLoadBetweenTicks(long[][] oldTicks); // cpu的最新使用情况
	long[][] getProcessorCpuLoadTicks();  // CPU负载计时器

	int getLogicalProcessorCount();			// 逻辑核心数量
	int getPhysicalProcessorCount();		// 物理核心数量
	int getPhysicalPackageCount();			// 系统套接字数量
	long getContextSwitches();				// 上下文切换数量
	long getInterrupts();					// 中断次数

ProcessorIdentifier 处理器标识符
	private final String cpuVendor;
	private final String cpuName;
	private final String cpuFamily;
	private final String cpuModel;
	private final String cpuStepping;
	private final String processorID;
	private final String cpuIdentifier;
	private final boolean cpu64bit;
	private final long cpuVendorFreq;

LogicalProcessor 逻辑处理器信息
	private final int processorNumber;
	private final int physicalProcessorNumber;
	private final int physicalPackageNumber;
	private final int numaNode;
	private final int processorGroup;


TickType 的枚举
	USER(0),			用户
	NICE(1),			
	SYSTEM(2),			系统
	IDLE(3),			空闲
	IOWAIT(4),			
	IRQ(5),				硬件中断
	SOFTIRQ(6),			软件中断
	STEAL(7);			