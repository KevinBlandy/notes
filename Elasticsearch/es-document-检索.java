-------------------
基本的根据条件检索 |
-------------------
	# 可以一次性检索多个index
		* 直接检索多个index
			GET /<index>,<index>/_search
	
		* 一次性检索所有的index
			GET /_all/_search
	
		* 通过 * 来匹配多个index
			GET /<prefix>*,*<sufix>/_search
	
-------------------
基本的根据条件检索 |
-------------------
	# 请求
		GET /<index>/_search?q=<query>
		
		{
		  "took" : 2,
				* 执行搜索的时间（以毫秒为单位）
		  "timed_out" : false,
				* 搜索是否超时
		  "_shards" : {
			"total" : 1,
				* 搜索了多少个分片
			"successful" : 1,
				* 搜索成功的分片数量
			"skipped" : 0,
				* 跳过的分片数量
			"failed" : 0
				* 搜索失败的分片数量
		  },
		  "hits" : {
			"total" : {
			  "value" : 13,
			  "relation" : "eq"
			},
			"max_score" : 1.0,
			"hits" : [
			  {
				"_index" : "customer",
				"_type" : "_doc",
				"_id" : "TsVMQGsBor31qRgUxQmS",
				"_score" : 1.0,
				"_source" : {
				  "name" : "KevinBlandy"
				}
			  }
			]
		  }
		}


-------------------
query参数		   |
-------------------
	# 过滤参数:q
		q=* 
			* 检索所有, q=*

		q=<value>
			* 任何字段, 只要包含该值就满足条件
			* 搜索的是_all field

		q=<field>:<value>
			* 全文检索, 只要是指定字段中有关键字的都OK, :q=name:KevinBlandy
			* 有多个匹配value值, 使用逗号分隔
		
		q=<field>:<+/-><value>
			* + 表示必须包含, - 表示必须不包含: q=-author.name:Litch 
		
	
	# 排序擦数:sort
		sort=<field>:<asc/desc>

		* 如果有多个, 使用逗号分隔:sort=age:asc,_id:desc
	
	# 分页参数:size & from
		* size,每页显示的记录数量, 默认10
		* from,从第几条数据开始检索,默认0(表示第一条)
	
		* deep paging问题
			* deep paging,简单来说,就是搜索得特别深,比如1000000条数据,每页显示10条,此时需要检索最后一页的数据
			* 符合请求的数据,可能存在于多个primary shard,replica shard,于是就要把所有数据汇总到 coordinating node(协调节点)
			* 由协调节点进行排序,取出最符合条件的数据,按照分页返回
			* 这个过程耗费带宽,内存,网络计算,这个就是deep paging问题,我们的开发尽量要避免这种情

	
	# _source 数据过滤参数:
		_source
			* 检索数据是否要携带 _source 数据, 值可以是 true/false
			* 也可以通过该参数来指定要检索的字段
				GET /goods/_doc/1?_source=author.name,author.age
			
		_source_includes
		_source_excludes
			* 过滤/包含指定的 _source 数据
		
		* 支持有多个值, 使用否号分割
		* 支持通配符:*
				GET /goods/_doc/1?_source=*.name
	
	# stored_fields
		//TODO
	
	# timeout 超时参数
		* 默认无超时
	
	# df
	# analyzer
	# analyze_wildcard
	# batched_reduce_size
	# default_operator
	# lenient
	# explain
	# track_scores
	# track_total_hits
	# terminate_after
	# search_type
	# allow_partial_search_results
	# scroll
		* 滚动搜索(有点像迭代器的感觉)
		* 每次仅仅响应一批数据, 直到响应完毕所有
		* 第一次检索的时候, 会生成快照, 在响应完毕之前, doc的修改不可见(可重复读的感觉)

	
	# filter_path
		* 可以过滤整个JSON结果的字段, 包括元数据信息
		* 支持对象导航
			GET /<index>/_search?filter_path=hits.hits  // 仅仅显示hits信息
				

	# error_trace
		* true/false, 是否在异常的时候, 响应堆栈信息
	
