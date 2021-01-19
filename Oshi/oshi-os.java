----------------------------
oshi - 获取操作系统信息
----------------------------
	OperatingSystem os = systemInfo.getOperatingSystem();

		String String getFamily();				// 操作系统类型（Windows/Linux）
		String String getManufacturer();
		getSystemBootTime();					// 系统启动时间
		getSystemUptime();						// 系统使用时间
		isElevated();							// 是否可以提权？
		OSVersionInfo getVersionInfo();			// 版本号信息
		
		FileSystem getFileSystem();				// 文件系统

		OSProcess[] getProcesses();				// 进程信息
		OSProcess[] getProcesses(int limit, ProcessSort sort);
		OSProcess[] getProcesses(int limit, ProcessSort sort, boolean slowFields);
		List<OSProcess> getProcesses(Collection<Integer> pids);
		List<OSProcess> getProcesses(Collection<Integer> pids, boolean slowFields);
		OSProcess getProcess(int pid);
		OSProcess getProcess(int pid, boolean slowFields);
		OSProcess[] getChildProcesses(int parentPid, int limit, ProcessSort sort);
		int getProcessCount();		// 获取进程数量
			* limit		限制结果数量
			* sort		排序枚举
				CPU, MEMORY, OLDEST, NEWEST, PID, PARENTPID, NAME
			* slowFields	如果为 true, 则检索进程的所有字段(会比较慢)

		long getProcessAffinityMask(int processId);		// 检索指定进程的进程关联掩码
			
		int getProcessId();								// 获取当前进程的进程id
		int getThreadCount();							// 获取当前正在执行的线程数量
		int getBitness();								// 获取系统位数（32/64）
		NetworkParams getNetworkParams();				// 获取网络参数
		OSService[] getServices();						// 获取系统服务


	OSVersionInfo 版本信息
		private final String version;				// 版本号				10
        private final String codeName;				// 编号					HOME
        private final String buildNumber;			// 构建版本				build 18362
        private final String versionStr;			// 拼接的字符串			10 (Home) build 18362

	FileSystem 文件系统
		OSFileStore[] getFileStores();			// 获取盘符
		OSFileStore[] getFileStores(boolean localOnly); // 仅仅获取本地盘符
		long getOpenFileDescriptors();		// 已打开的文件描述符
		long getMaxFileDescriptors();		// 一共可以打开的文件描述符
	
	OSFileStore 盘符（分区）
		private String name;				// 磁盘名称
		private String volume;				// 
		private String logicalVolume = "";
		private String mount;				// 挂载
		private String description;			// 描述
		private String fsType;				// 文件系统类型
		private String options;				// 
		private String uuid;				
		private long freeSpace;				// 空闲的空间
		private long usableSpace;			// 未使用的空间大小
		private long totalSpace;			// 总空间大小
		private long freeInodes;			// 文件系统的索引数量
		private long totalInodes;			// 文件系统的最大索引数量


		boolean updateAtrributes()			// 更新数据
	
	OSProcess 进程信息
		private String name = "";
		private String path = "";
		private String commandLine = "";
		private String currentWorkingDirectory = "";
		private String user = "";
		private String userID = "";
		private String group = "";
		private String groupID = "";
		private State state = State.OTHER;
		private int processID;
		private int parentProcessID;
		private int threadCount;
		private int priority;
		private long virtualSize;
		private long residentSetSize;
		private long kernelTime;
		private long userTime;
		private long startTime;
		private long upTime;
		private long bytesRead;
		private long bytesWritten;
		private long openFiles;
		private int bitness;
		// cache calculation for sorting
		private double cpuPercent = -1d;
	
	
	NetworkParams 网络参数
		String getHostName();				// 主机名称
		String getDomainName();				// 主机名称
		String[] getDnsServers();			// dns服务器地址
		String getIpv4DefaultGateway();		// ipv4网关地址
		String getIpv6DefaultGateway();		// ipv6网关地址
	

	OSService 系统服务
		private final String name;		// 服务名称
		private final int processID;	// 进程id
		private final State state;		// 状态枚举
			RUNNING,	运行
			STOPPED,	停止
			OTHER		其他
	

