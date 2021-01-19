----------------------------
JobStore					|
----------------------------
	# JobStore负责跟踪, 提供给调度程序的所有"工作数据": jobs, triggers, Calendar... 等

	# 子类
		AbstractTerracottaJobStore
			|-TerracottaJobStore
		JobStoreSupport
			|-JobStoreCMT
				|-LocalDataSourceJobStore
			|-JobStoreTX
		RAMJobStore
		ClusteredJobStore
			|-DefaultClusteredJobStore
		TerracottaJobStoreExtensions
			|-PlainTerracottaJobStore
	
	# 通过配置设置实现
		org.quartz.jobStore.class=org.quartz.simpl.RAMJobStore
	

	