----------------------------
RateLimiter					|
----------------------------
	# 工厂函数
		RateLimiter create(double permitsPerSecond) 
		RateLimiter create(double permitsPerSecond, long warmupPeriod, TimeUnit unit)

		* permitsPerSecond 表示一秒产生多少个令牌
		* warmupPeriod
		* unit表示warmupPeriod的时间单位


----------------------------
RateLimiter					|
----------------------------
	# 令牌桶算法实现
		//令牌桶每秒产生2.5个令牌
		RateLimiter rateLimiter = RateLimiter.create(2.5,1,TimeUnit.SECONDS);
		//消费一个令牌
		System.out.println(rateLimiter.acquire());
		//手动设置，需要消费多少个令牌，如果桶里面没有令牌,则当前线程阻塞，返回值为阻塞了多久才得到的令牌
		System.out.println(rateLimiter.acquire(5));
		//如果一次性拿走的令牌，超过了剩余的令牌，那么下一次获取是要还的（也就是，取的速率可以超过令牌产生的速率，但是下一次再次去取的时候，需要阻塞等待）
		System.out.println(rateLimiter.acquire(1));
		
		
		//非阻塞的获取令牌，实时返回结果。是否有剩余的令牌
		boolean result = rateLimiter.tryAcquire();
		System.out.println(result);
		
		//非阻塞的获取令牌，实时返回结果。是否有剩余的令牌。通过参数设置超时时间，如果超时了执行返回 false
		result = rateLimiter.tryAcquire(2,TimeUnit.SECONDS);
		System.out.println(result);
	
	# 令牌可以超额获取,但是下一个获取令牌的人是要还的
		//令牌桶每秒产生1个令牌
		RateLimiter rateLimiter = RateLimiter.create(1,1,TimeUnit.SECONDS);
		//直接获取5个令牌，因为只有1个令牌，透支了4个
		System.out.println(rateLimiter.acquire(5));        //0.0
		//获取1一个令牌，会被多阻塞4s，因为需要补上，上一个透支的令牌数量
		System.out.println(rateLimiter.acquire(1));        //5.498552
	
	# Controller 里面使用
		//每1s产生0.5个令牌，也就是说该接口2s只允许调用1次
		private RateLimiter rateLimiter = RateLimiter.create(0.5,1,TimeUnit.SECONDS);
		
		@GetMapping(produces = "text/plain;charset=utf-8")
		public String foo() throws InterruptedException {
			//尝试获取1一个令牌
			if(rateLimiter.tryAcquire()) {
				//获取到令牌，进行逻辑处理
				return "处理成功";
			}else {
				//未获取到令牌
				return "请求频繁";
			}
		}