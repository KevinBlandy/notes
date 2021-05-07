---------------------
beanstalkd
---------------------
	# C开发的高性能消息队列框架，可以用于实现延时队列

	# 地址
		* Github
			https://github.com/beanstalkd/beanstalkd
		
		* 中文文档
			https://github.com/beanstalkd/beanstalkd/blob/master/doc/protocol.zh-CN.md

		* Go客户端
			https://github.com/beanstalkd/go-beanstalk
	


	# 核心概念
		* Job
			可以理解为消息

		* tube 
			就是Topic，一个主题，用于存放N多Job
	
	# Job生命周期
		put			将一个任务放置进 tube 中
		deayed		这个任务现在再等待中，需要若干秒才能准备完毕【延迟队列】
		ready		这个任务已经准备好了，可以消费了。所有的消费都是要从取 ready 状态的 job
		reserved	这个任务已经被消费者消费
		release		这个 job 执行失败了，把它放进 ready 状态队列中。让其他队列执行
		bury		这个 job 执行失败了，但不希望其他队列执行，先把它埋起来
	

---------------------
beanstalkd 安装
---------------------
	# Centos安装
		yum -y install beanstalkd --enablerepo=epel
	
	# 维护
		* 启动
			beanstalkd -l 0.0.0.0 -p 11300 -b /home/software/binstalkd/binlogs
				-l 指定IP
				-p 指定端口
				-b 指定序列化文件存储路径
		

		