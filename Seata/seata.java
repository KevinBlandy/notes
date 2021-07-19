-------------------------
Seata
-------------------------
	# 地址
		https://github.com/seata/seata
		http://seata.io/zh-cn
	

-------------------------
术语
-------------------------
	# TC (Transaction Coordinator) - 事务协调者
		* 独立的服务，掌控全局
	
	# TM (Transaction Manager) - 事务管理器
		* 本质就是执行全局事务的入口方法
		* 它负责开启，提交，关闭事务
	
	# RM (Resource Manager) - 资源管理器
		* 各个服务的本地事务，也就是全局事务中的分支事务
		* 听候TC差遣，开启/提交/回滚
	
