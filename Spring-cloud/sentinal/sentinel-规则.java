----------------------------
规则
----------------------------
	# 支持的规则
		流量控制规则
		熔断降级规则
		系统保护规则
		来源访问控制规则 
		热点参数规则
	
		* 所有规则都可以在内存态中动态地查询及修改，修改之后立即生效
	
	# 触发规则后抛出的异常
		* 都是 BlockException 的子类
			BlockException 
				|-FlowException			流控异常
				|-DegradeException		熔断降级异常
				|-SystemBlockException	系统保护异常
				|-ParamFlowException	热点参数限流异常
		
		* 可以判断指定异常是否是 BlockException 或者及其子类
			BlockException.isBlockException(Throwable t);
	
	# block事件
		* Sntinel 提供以下扩展接口，可以通过 StatisticSlotCallbackRegistry 向 StatisticSlot 注册回调函数
		
		* StatisticSlotCallbackRegistry 处理回调相关的方法
			 public static void clearEntryCallback()
			 public static void clearExitCallback()

			 public static void addEntryCallback(String key, ProcessorSlotEntryCallback<DefaultNode> callback)
			 public static void addExitCallback(String key, ProcessorSlotExitCallback callback)

			 public static ProcessorSlotEntryCallback<DefaultNode> removeEntryCallback(String key)
			 public static Collection<ProcessorSlotEntryCallback<DefaultNode>> getEntryCallbacks()

			 public static ProcessorSlotExitCallback removeExitCallback(String key) 
			 public static Collection<ProcessorSlotExitCallback> getExitCallbacks()
		
		* ProcessorSlotEntryCallback<T>  回调接口
			void onPass(Context context, ResourceWrapper resourceWrapper, T param, int count, Object... args) throws Exception;
			void onBlocked(BlockException ex, Context context, ResourceWrapper resourceWrapper, T param, int count, Object... args);
		
		* ProcessorSlotExitCallback 回调接口
			void onExit(Context context, ResourceWrapper resourceWrapper, int count, Object... args);
		


----------------------------
流量控制规则 FlowRule
----------------------------
	# FlowRule 参数
		resource
			* 资源名，资源名是限流规则的作用对象	

		limitApp
			* 流控针对的调用来源
			* 默认：default，代表不区分调用来源

		grade
			* 限流阈值类型，QPS 模式（1）或并发线程数模式（0）
			* 默认：QPS 模式

		count
			* 限流阈值

		strategy
			* 调用关系限流策略：直接、链路、关联
			* 默认：根据资源本身（直接）
		
		refResource
		controlBehavior
			* 流控效果（直接拒绝/WarmUp/匀速+排队等待），不支持按调用关系限流
			* 默认：直接拒绝

		warmUpPeriodSec
		maxQueueingTimeMs
		clusterMode
			* 是否集群限流
			* 默认：否

		clusterConfig
		controller
	
	# 加载规则
		DegradeRuleManager.loadRules(rules);

----------------------------
熔断降级规则 DegradeRule
----------------------------
	# DegradeRule 参数
		resource
			* 资源名，即规则的作用对象
		grade
			* 熔断策略，支持慢调用比例/异常比例/异常数策略
			* 默认：慢调用比例
		count
			* 慢调用比例模式下为慢调用临界 RT（超出该值计为慢调用）；异常比例/异常数模式下为对应的阈值
		limitApp
		timeWindow
			* 熔断时长，单位为 s
		minRequestAmount
			* 熔断触发的最小请求数，请求数小于该值时即使异常比率超出阈值也不会熔断（1.7.0 引入）
			* 默认：5
		slowRatioThreshold
			* 慢调用比例阈值，仅慢调用比例模式有效（1.8.0 引入）
		statIntervalMs
			* 统计时长（单位为 ms），如 60*1000 代表分钟级（1.8.0 引入）
			* 默认：1000 ms
		

	# 加载
		DegradeRuleManager.loadRules(rules);
	
----------------------------
系统保护规则 SystemRule
----------------------------
	# SystemRule 参数
		highestSystemLoad
			* load1 触发值，用于触发自适应控制阶段
			* 默认：-1 (不生效)

		highestCpuUsage
			* 当前系统的 CPU 使用率（0.0-1.0）	-
			* 默认：-1 (不生效)

		qps
			* 所有入口资源的 QPS
			* 默认：-1 (不生效)

		avgRt
			* 所有入口流量的平均响应时间
			* 默认：-1 (不生效)

		maxThread
			* 入口流量的最大并发数
			* 默认：-1 (不生效)
		

	
	# 加载
		SystemRuleManager.loadRules(rules);

----------------------------
访问控制规则 AuthorityRule
----------------------------
	# 参数
		resource
			* 资源名，即规则的作用对象
		limitApp
			* 对应的黑名单/白名单，不同 origin 用 , 分隔，如 appA,appB
		strategy
			* 限制模式，AUTHORITY_WHITE 为白名单模式，AUTHORITY_BLACK 为黑名单模式
			* 默认：白名单模式

			* 白名单模式，只有limitApp才可以访问
			* 黑名单模式，非limitApp才可以访问
		
	# 加载
		AuthorityRuleManager.loadRules((rule);

----------------------------
热点规则 ParamFlowRule
----------------------------
	# 需要添加依赖
		<dependency>
			<groupId>com.alibaba.csp</groupId>
			<artifactId>sentinel-parameter-flow-control</artifactId>
		</dependency>
	
	# 参数
		
	# 加载
		ParamFlowRuleManager.loadRules(rule);