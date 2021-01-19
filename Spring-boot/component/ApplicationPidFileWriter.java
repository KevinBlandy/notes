
-------------------------
ApplicationPidFileWriter |
-------------------------
	# 在sb应用启动后, 用于把进程id写入到文件的监听器

	# 配置监听器
		SpringApplication springApplication = new SpringApplication(MyApplication.class);
        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
	
	# 属性配置
		spring.pid.file=D://app.pid
			* 写入pid的文件

		spring.pid.fail-on-write-error=true
			* 当无法写入pid文件的时候, 是否抛出异常
		

		
	