--------------------
资源
--------------------
		
	# 注意：SphU.entry(xxx) 需要与 entry.exit() 方法成对出现，匹配调用，否则会导致调用链记录异常，抛出 ErrorEntryFreeException 异常。
		* 常见的错误：
			自定义埋点只调用 SphU.entry()，没有调用 entry.exit()
			顺序错误，比如：entry1 -> entry2 -> exit1 -> exit2，应该为 entry1 -> entry2 -> exit2 -> exit1
		
		* 格式，先进后出
			func(func(func()))
		
	# try catch 格式的资源
		// 1.5.0 版本开始可以利用 try-with-resources 特性（使用有限制）
		// 资源名可使用任意有业务语义的字符串，比如方法名、接口名或其它可唯一标识的字符串。
		try (Entry entry = SphU.entry("resourceName")) {
		  // 被保护的业务逻辑
		  // do something here...
		} catch (BlockException ex) {
		  // 资源访问阻止，被限流或被降级
		  // 在此处进行相应的处理操作
		}
	
	# 手动exit
		Entry entry = null;
		// 务必保证 finally 会被执行
		try {
		  // 资源名可使用任意有业务语义的字符串，注意数目不能太多（超过 1K），超出几千请作为参数传入而不要直接作为资源名
		  // EntryType 代表流量类型（inbound/outbound），其中系统规则只对 IN 类型的埋点生效
		  entry = SphU.entry("自定义资源名");
		  // 被保护的业务逻辑
		  // do something...
		} catch (BlockException ex) {
		  // 资源访问阻止，被限流或被降级
		  // 进行相应的处理操作
		} catch (Exception ex) {
		  // 若需要配置降级规则，需要通过这种方式记录业务异常
		  // 异常降级仅针对业务异常，对 Sentinel 限流降级本身的异常（BlockException）不生效。为了统计异常比例或异常数，需要通过 Tracer.trace(ex) 记录业务异常
		  Tracer.traceEntry(ex, entry);
		} finally {
		  // 务必保证 exit，务必保证每个 entry 与 exit 配对
		  if (entry != null) {
			entry.exit();
		  }
		}
	
	# 热点参数埋点
		* 若 entry 的时候传入了热点参数，那么 exit 的时候也一定要带上对应的参数（exit(count, args)），否则可能会有统计错误。
		
		Entry entry = null;
		try {
			// 若需要配置例外项，则传入的参数只支持基本类型。
			// EntryType 代表流量类型，其中系统规则只对 IN 类型的埋点生效
			// count 大多数情况都填 1，代表统计为一次调用。
			entry = SphU.entry(resourceName, EntryType.IN, 1, paramA, paramB);
			// Your logic here.
		} catch (BlockException ex) {
			// Handle request rejection.
		} finally {
			// 注意：exit 的时候也一定要带上对应的参数，否则可能会有统计错误。
			if (entry != null) {
				entry.exit(1, paramA, paramB);
			}
		}
	
	#  if-else 风格的 API
		  // 资源名可使用任意有业务语义的字符串
		  if (SphO.entry("自定义资源名")) {
			// 务必保证finally会被执行
			try {
			  /**
			  * 被保护的业务逻辑
			  */
			} finally {
			  SphO.exit();
			}
		  } else {
			// 资源访问阻止，被限流或被降级
			// 进行相应的处理操作
		  }
	

	# 注解方式
		* 通过 @SentinelResource 注解定义资源并配置 blockHandler 和 fallback 函数来进行限流之后的处理

		// 原本的业务方法.
		@SentinelResource(blockHandler = "blockHandlerForGetUser")
		public User getUserById(String id) {
			throw new RuntimeException("getUserById command failed");
		}

		// blockHandler 函数，原方法调用被限流/降级/系统保护的时候调用
		public User blockHandlerForGetUser(String id, BlockException ex) {
			return new User("admin");
		}