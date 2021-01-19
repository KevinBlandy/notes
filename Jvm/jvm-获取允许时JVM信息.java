-----------------------
获取虚拟机信息
-----------------------
RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

// 名称
runtimeMXBean.getVmName();
// 供应商
runtimeMXBean.getVmVendor();
// 版本号
runtimeMXBean.getVmVersion();
// 启动时间
runtimeMXBean.getStartTime();
// 运行时间
runtimeMXBean.getUptime();


-----------------------
获取内存信息
-----------------------

MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

// 堆内存使用信息
MemoryUsage headMemory = memoryMXBean.getHeapMemoryUsage();
headMemory.getInit(); // 初始化内存
headMemory.getMax(); // 最大内存
headMemory.getUsed(); // 已使用的内存
headMemory.getCommitted(); // 已经申请的内存

// 非堆内存的使用信息
MemoryUsage nonheadMemory = memoryMXBean.getNonHeapMemoryUsage();
nonheadMemory.getInit(); // 初始化内存
nonheadMemory.getMax(); // 最大内存
nonheadMemory.getUsed(); // 已使用的内存
nonheadMemory.getCommitted(); // 已经申请的内存

// 堆内存中各个分区信息
List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();
for (MemoryPoolMXBean memoryPoolMXBean : memoryPoolMXBeans) {
	memoryPoolMXBean.getName();						// 分区名称
	memoryPoolMXBean.getUsage();					// 使用信息
	memoryPoolMXBean.getMemoryManagerNames();		// 内存管理器（GC）
	memoryPoolMXBean.getType();						// 类型枚举。HEAP，NON_HEAP
}