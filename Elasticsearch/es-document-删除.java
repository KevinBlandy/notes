--------------------
document - 删除		|
--------------------
	# document不会被立即的物理删除,只会被标记为delete,当数据越来越多的试试,在后台自动的删除
		* 尝试删除一个doc后, 再次根据这个id创建一个doc, 发现_version字段会增加

	
--------------------
document - 删除		|
--------------------
	# 基本的根据id删除
		DELETE /<index>/_update/<id>?pretty

		{
		  "_index" : "customer",
		  "_type" : "_doc",
		  "_id" : "11",
		  "_version" : 3,
		  "result" : "deleted",
		  "_shards" : {
			"total" : 2,
			"successful" : 1,
			"failed" : 0
		  },
		  "_seq_no" : 23,
		  "_primary_term" : 1
		}

		* 如果删除的数据不存在, 则"result" = "not_found"
		* 如果需要删除所有的doc, 建议直接删除整个索引,这样更高效
	

--------------------
document - API删除	|
--------------------
	# 使用 _delete_by_query 对匹配到的文档进行删除
		POST /<index>/_delete_by_query
		{"query":{...}}
	
		{
		  "took" : 0,
				* 从整个操作的开始到结束的毫秒数。

		  "timed_out" : false,
				* 在通过查询执行删除期间执行的请求是否有超时

		  "total" : 0,
				* 匹配到的文档数量
			  
		  "deleted" : 0,
				* 成功删除的文档数量		

		  "batches" : 0,
		  "version_conflicts" : 0,
				* 版本冲突数量

		  "noops" : 0,
				* 对于删除来说, 它始终是0
		  "retries" : {
			"bulk" : 0,
				* 批量操作的重试次数
			"search" : 0
				* 搜索操作的重试次数
		  },
		  "throttled_millis" : 0,
		  "requests_per_second" : -1.0,
				* 在通过查询删除期间有效执行的每秒请求数

		  "throttled_until_millis" : 0,
				* 该字段在_delete_by_query响应中应始终等于零
		  "failures" : [ ]  
				* 匹配出的, 删除失败的结果
		}
	
	# _delete_by_query执行期间, 顺序执行多个搜索请求以便找到要删除的所有匹配文档
		* 每次找到一批文档时, 都会执行相应的批量请求以删除所有这些文档
		* 如果搜索或批量请求被拒绝, 则_delete_by_query 依赖于默认策略来重试被拒绝的请求(最多10次), 达到最大重试次数限制会导致_delete_by_query 中止
		* 并failures在响应中返回所有失败, 已执行的删除仍然有效
		
		* 也就说删除不是原子性的, 过程不会回滚, 只会中止
		* 当第一个失败导致中止时, 失败的批量请求返回的所有失败都将在 failures 元数据中

	
	
	# _delete_by_query 使用internal版本控制删除它找到的内容
		* 这意味着如果文档在拍摄快照的时间和处理删除请求之间发生更改, 则会出现版本冲突
		* 当版本匹配时, 文档才会被删除
	
		* 计算版本冲突, 不停止删除操作, 可以使用检索参数
			conflicts=proceed
	
	# 默认情况下, _delete_by_query使用1000的滚动批次来进行删除,可以使用scroll_size 参数更改批量大小
		scroll_size=5000 
	
	# 删除通过查询API也支持很多的功能参数
		refresh
		wait_for_completion
			* 如果 wait_for_completion=false, 则Elasticsearch将执行一些预检检查
			* 启动请求, 然后返回task 可与Tasks API 一起使用以取消或获取任务状态的请求
			* Elasticsearch还将创建此任务的记录作为文档 .tasks/task/${taskId}

		wait_for_active_shards
		timeout
		scroll
			* _delete_by_query 采用滚动搜索, 还可以指定scroll参数来控制多长时间保持"搜索上下文"活着(默认5分钟)
				scroll=10m

		requests_per_second
		
	
--------------------
使用Task API		|
--------------------
	# Task API是新功能, 目前仍视为测试版功能


--------------------
切片				|
--------------------

	
