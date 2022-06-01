----------------------------
RateLimiter					|
----------------------------
	# 工厂函数
		RateLimiter create(double permitsPerSecond) 
			* 平滑突发限流
			* 1秒内不会给超过permitsPerSecond个令牌，并且以固定速率进行放置，达到平滑输出的效果。

		RateLimiter create(double permitsPerSecond, long warmupPeriod, TimeUnit unit)
			* 平滑预热限流
			* 它启动后会有一段预热期，逐步将分发频率提升到配置的速率。 
			* 比如下面代码中的例子，创建一个平均分发令牌速率为2，预热期为3秒。
				 RateLimiter r = RateLimiter.create(2, 3, TimeUnit.SECONDS);
				
			* 由于设置了预热时间是3秒，令牌桶一开始并不会0.5秒发一个令牌，而是形成一个平滑线性下降的坡度，频率越来越高，在3秒钟之内达到原本设置的频率，以后就以固定的频率输出。
			* 这种功能适合系统刚启动需要一点时间来“热身”的场景。
		
	
	# 实例方法
		public double acquire()
		public double acquire(int permits)
		public final double getRate()
		public final void setRate(double permitsPerSecond)
		public boolean tryAcquire()
		public boolean tryAcquire(int permits)
		public boolean tryAcquire(long timeout, TimeUnit unit)
		


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