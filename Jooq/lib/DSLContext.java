------------------------------------
DSLContext
------------------------------------
	# 核心库
		interface DSLContext extends Scope


------------------------------------
DSLContext
------------------------------------
	Configuration configuration();
		* 获取到配置信息
	
	SelectSelectStep<Record1<Integer>> selectCount();
		* 执行count查询