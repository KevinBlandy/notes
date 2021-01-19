-------------------------
concurrent				 |
-------------------------
	* 这是一个面向并发的包
	* 里面有很多面向并发的类库
		concurrent.futures.thread.ThreadPoolExecutor
			* 线程池


------------------------------------------------
concurrent.futures.thread.ThreadPoolExecutor			|
------------------------------------------------
	* 线程池类
	* 构造函数
		ThreadPoolExecutor(max_workers=50)
			* 池中默认创建 cpu 核心数 * 5 个线程
			* 关键字参数
				thread_name_prefix
					* 线程名称的前缀
				max_workers
					* 最大线程数量

	* 实例方法
		 concurrent.futures._base.Future submit(fn, *args, **kwargs)
			* 执行一个任务
			* fn:就是任务函数
			* *args, **kwargs 是运行该函数时,传递的参数
			* 返回值是一个 Future 对象

			* Future 方法
				add_done_callback()
					* 添加线程结束,执行的回调函数
					* 会自动的把当前 future(this),传递给该函数的第一个参数
					* future.add_done_callback(lambda x: print('线程执行完毕,结果是:%s'%(x.result())))
				
				result()
					* 获取线程执行完毕后,返回(return)的数据