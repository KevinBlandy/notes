----------------------------
bulk批量增删改				|
----------------------------
	# _bulk 请求对json的语法,要求相当严格
		* 每个json串儿,不能换行,只能放在同一行
		* 两个json串儿之间要换一行

	# create,update操作,需要俩json串
		* 第一个json指定操作,以及元数据
		* 第二个json指定提交的业务数据

	# json格式
		{"create":{...元数据}}
		{...业务数据}

		{"index":{...元数据}}
		{...业务数据}

		{"delet":{...元数据}}

		{"update":{...元数据}}
		{...业务数据}
		

		* create 属于强制创建		PUT /index/type/id/_create
		* index 属于普通的put操作,可以是替换文档,或者创建文档	
		* update 属于 partial update 操作,可以使用 retry_on_conflict 来控制乐观锁的重试次数
		*  创建操作如果不在元数据中定义id那么会自动生成id
		* 一个批量操作可以同时提交N多个 create,delete,update 请求,只需要符合json格式即可
		* 批量操作中的其中一个操作失败,不会影响到其他的批量操作,但是会在返回结果中提示失败的日志

	# 不同index的批量操作
		POST /_bulk
		{"create":{"_index":"user","_type":"coder",	"_id":3}}
		{"id":3,"name":"Batch Name","age":24}
		{"delete":{"_index":"user","_type":"coder","_id":2}}
		{"update":{"_index":"user","_type":"coder","_id":1,"retry_on_conflict":3}}
		{"doc":{"name":"Batch Uapdate Name"}}
	
	# 相同index,不同type的批量操作
		POST /user/_bulk
		{"create":{"_type":"coder","_id":3}}
		{"id":3,"name":"Batch Name","age":24}
		{"delete":{"_type":"coder","_id":2}}
		{"update":{"_type":"coder","_id":1,"retry_on_conflict":3}}
		{"doc":{"name":"Batch Uapdate Name"}}
		
		* 元数据仅仅需要声明 _type 和 id 即可,因为index已经在url中定义
	
	# 相同index,相同type的批量操作
		POST /user/coder/_bulk
		{"create":{"_id":3}}
		{"id":3,"name":"Batch Name","age":24}
		{"delete":{"_id":2}}
		{"update":{"_id":1,"retry_on_conflict":3}}
		{"doc":{"name":"Batch Uapdate Name"}}

		* 元数据仅仅需要声明 id 即可,因为 _type 和 _index 已经在url中定义
		
	
	# bulk 性能优化
		* bulk request 会加载到内容,如果太大的话,性能反而会下降,因此需要反复尝试一个最佳的bulk size
		* 一般从 1000 - 5000条开始,尝试逐渐增加
		* 如果从大小上看的话,最好是在5 - 15MB之间
	
	# bulk奇特的JSON格式与底层性能优化关系
		1,bulk中的每个操作,都可能要转发到不同的node的shard去执行
		2,如果采用比较良好的json数组格式
			(1),将json数组解析为 JSONArray 对象
			(2),对每个请求中的document进行路由
			(3),为路由到同一个shard上的多个请求创建一个请求数组
			(4),将这个请求数组序列化
			(5),把序列化的数据发送到对应的节点

			* 耗费更多的内存,更多的jvm gc开销
	
		3,特殊的json请求格式
			(1),按照换行符切割json
			(2),对每两个一组的json,读取meta,进行document路由
			(3),直接把对应的json发送到node上去

			* 最大的优势在于,不需要将json数组解析为一个JSONArray对象,形成一份大数据的拷贝,浪费内存空间