-------------------------
SpringBoot batch		 |
-------------------------
	# 结构
	  Step
		↓
	  Tasklet
		↓
	  Chunk
		↓
	  read
	  process
	  write
	 
	
	# 结构
		JobRepository	
			* 用来注册job的容器,基础组件,用来持久化Job的元数据,默认使用内存
		JobLauncher
			* 用来启动Job的接口,基础组件
		Job
			* 实际执行的任务,包含一个或多个Step,是Batch操作的基础执行单元
		Step    
			* 表示Job的一个阶段,Job由一组Step组成
			* Step包含ItemReader、ItemProcessor和ItemWriter
		Item
			* 从数据源读出或者写入的一条记录
		Chunk
			* 给定数量的Item集合(Item数量)
		ItemReader
			* 用来读取数据的接口
		ItemProcessor
			* 用来处理数据的接口(Consumer)
		ItemWriter
			* 用来输出数据的接口(把Chunk包含的数据写入数据源)

	# 注解开启
		@EnableBatchProcessing
			boolean modular() default false;
			* 要实现多Job,需要把modular设置为true
			* 让每个Job使用自己的ApplicationConext
		
	
		

		