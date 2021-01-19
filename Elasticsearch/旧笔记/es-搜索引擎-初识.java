------------------------
search 结果				|
------------------------
	{
	  "took": 19,
		  * 整个搜索请求花费了多少毫秒
	  "timed_out": false,
			* 是否超时
	  "_shards": {
			* 关于shard的一些信息
		"total": 6,
				* 默认来说说,一个搜索请求,会打到该index所有的primary shard上去
		"successful": 6,
		"skipped": 0,
		"failed": 0
	  },
	  "hits": {
			* 搜索结果
		"total": 6,
				* 本次搜索的返回的结果
		"max_score": 1,
				* 本次搜索所有结果中,最大相关度分数是多少
				* 每一跳document对于search的相关度,越相关,_score分数越大,排位越靠前
		"hits": [
				* 数据
		  {
			"_index": ".kibana",
			"_type": "doc",
			"_id": "config:6.3.0",
			"_score": 1,
			"_source": {
			  "type": "config",
			  "updated_at": "2018-06-20T13:41:22.461Z",
			  "config": {
				"buildNum": 17230,
				"telemetry:optIn": true
			  }
			}
		  }
		]
	  }
	}

------------------------
timeout机制				|
------------------------

	# timeout机制
		* 默认情况下,没有timeout
		* 该机制,指定了每个shard就只能在timeout时间范围内,将收索到的数据响应给客户端
		* 在timeout时间内,可能已经搜索出来了,也可能没搜索全,反正不会等,立即把已经搜索到的数据响应
	
	# 语法
		GET /_search?timeout=10ms
		GET /_search?timeout=1m

		* 使用timeout参数来指定秒,毫秒...

------------------------
multi-index和multi-type	|
------------------------
	GET /_search
		* 检索所有index,及其index下所有type的数据
	
	GET /index/type/_search
		* 检索index下所有type的数据
	
	GET /index/type1,type2/_search
		* 一个index下多个type
		* 搜索index下type1,type2下的所有数据
	
	GET /index1,index2/_search
		* 多个index
		* 检索index1,index2及其下所有type的数据
	
	GET /index1,index2/type1,type2/_search
		* 多个index,多个type
		* 检索index1下的type1,index2下的type,的所有数据
	
	GET /index_*/_search
		* 支持通配符
		* 检索所有名称以 'index_' 开头的index,及其下所有type的数据
	
	GET /_all/type1,type2/_search
		* 检索所有index下,指定type的数据

	
------------------------
搜索原理				|
------------------------
	# clinet的一个请求,会分布到index的所有的primary shard上,因为它可能在任何一个primary shard上
		* 如果primary shard还有replica shard,那么还会请求到replica shard上
	

------------------------
分页搜索				|
------------------------
	# 分页检索
		GET /_search?size=10
		GET /_search?size=10&from=0
		GET /_search?size=10&from=20

		* size,每页显示的记录数量
		* from,从第几条数据开始检索,0表示第一条
	
	# deep paging问题
		* deep paging,简单来说,就是搜索得特别深,比如1000000条数据,每页显示10条,此时需要检索最后一页的数据
		* 符合请求的数据,可能存在于多个primary shard,replica shard,于是就要把所有数据汇总到 coordinating node(协调节点)
		* 由协调节点进行排序,取出最符合条件的数据,按照分页返回
		* 这个过程耗费带宽,内存,网络计算,这个就是deep paging问题,我们的开发尽量要避免这种情况



