--------------------
document			|
--------------------


--------------------
document - 创建		|
--------------------
	# 基本的创建
		PUT /<index>/_doc/<id>?pretty
		{...}

		{
		  "_index" : "customer",
		  "_type" : "_doc",
		  "_id" : "1",
		  "_version" : 4,
		  "result" : "updated",
		  "_shards" : {
			"total" : 2,
			"successful" : 1,
			"failed" : 0
		  },
		  "_seq_no" : 3,
		  "_primary_term" : 1
		}
		
		_shards.total
			* 执行索引操作的分片副本(主分片和副本分片)的数量

		_shards.successful
			* 操作成功的副本数量

		_shards.failed
			* 操作失败的副本数量

		

		* 如果Index不存在, 会自动的创建
		* PUT方式, 必须手动的指定id属性

	# 使用POST创建
		POST /<index>/_doc
		{...}

		* POST方式, 如果不指定id的话, 系统自动为doc生成一个uuid(VMVNQGsBor31qRgUZwnr)

	# 强制创建
		* 如果只想新建文档,不想替换文档,那么就需要强制创建(两种方式)

		PUT /<index>/_doc/<id>?op_type=create

		PUT /<index>/_doc/<id>/_create
			* 这种方式比较常见
	
		* 如果该id的document已经存在,那么不会PUT成功,会抛出异常


--------------------
document - 检索		|
--------------------
	# 基本的根据id检索
		GET /<index>/_doc/<id>

		{
		  "_index" : "customer",
		  "_type" : "_doc",
		  "_id" : "1",
		  "_version" : 4,
		  "_seq_no" : 3,
		  "_primary_term" : 1,
		  "found" : true,
		  "_source" : {
			...
		  }
		}
	
	# 根据id判断是否存在, 使用 HEAD 请求
		HEAD /<index>/_doc/<id>

		存在:200 - OK
		不在:404 - Not Found

	
------------------------------------
document 路由						|
------------------------------------
	# document 路由到 shard
		* index 数据会被分片到多少shard找那个,所以一个document只会存在一个shard
		* 计算出 document 应该在存在哪个shard,其实就是路由

	# 路由算法
		* shard = hash(routing) % number_of_primary_shards
			> routing 默认为 document的 id(可以手动指定)
			> hash算法
			> number_of_primary_shards : primary shad的数量

		* 手动指定 routing
			PUT /<index>/<id>?routing=15
			GET /<index>/<id>?routing=15

			> 通过参数手动指定routing value很有用,可以保证,某一类document一定被路由到一个shard上
			> 那么在后续进行应用级别的负载均衡,以及提升批量读取性能的时候,是很有帮助的

	# primary shard 数量不可变
		* 一旦发生变化了,那么在取模算法时,就会有问题,会间接导致数据丢失
		* 但是 replica shard可以随意增删

---------------------------
活动分片				   |
---------------------------
	# 文档
		https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-create-index.html

	# 执行增删改的时候, 可以通过分片的活跃度来保证写一致性
	# ElasticSearch 5.0之后使用 wait_for_active_shards 代替了consistency 来指定写一致性级别
		POST /<index>/_doc/<id>?wait_for_active_shards=1

	# 有效值是all或任何正整数
		all
			* 所有的primary shard和replica shard都是活跃的,才可以执行

		任何正整数
			* TODO

	# 如果活跃节点数量少于指定的数量,可能会导致无法执行写操作
		* 跃节点数量少于指定的数量时,默认 wait 1分钟(期望活跃的shard可以增加)
		* 可以通过 timeout 参数来控制(单位默认是毫秒,可以通过添加s来设置为秒)
			PUT /<index>/_doc/<id>?timeout=60s

---------------------------
立即刷新				   |
---------------------------
	# 写操作只能由主分片执行, 主分片写入后, 会同步到副本分配
		* 可能导致的问题就是, 主分片还未同步到副本分片, 就已经响应给客户端成功信息
		* 客户端再次检索到副本分片, 由于还未同步完成, 所以检索不到数据
		
	# 可以通过参数来控制, 是否要等到副本同步完成, 才返回给客户端成功

	# 通过query参数控制:refresh
		true (空字符串也可以)
			* 强制执行刷新
			* 不会阻塞客户端, 而是在客户端的操作响应后立即刷新相关的主分片和副本分片

		wait_for
			* 等待刷新的发生
			* 会阻塞客户端, 在响应之前, 等待刷新请求所做的更改, 这不会强制立即刷新, 而是等待刷新发生
			* ES 会每 index.refresh_interval(默认值为1秒)自动刷新已经更改的碎片, 这个设置是动态的
			* 调用Refresh API或在任何支持它的API上将refresh设置为true也会导致刷新, 从而导致已经运行的带有refresh=wait_for的请求响应


		false (默认)
			* 不等待刷新, 立即返回
	
		PUT  /<index>/_doc/<id>?refresh=false
	
	# refresh=wait_for 也可以强制刷新
		* 如果存在配置 index.max_refresh_listener(默认数量为1000)
		* 请求等待刷新的情况下, 那个碎片上出现refresh=wait_for请求, 那么该请求的行为就好像它已经将refresh设置为true一样
		* 它将强制刷新, 这保证了当refresh=wait_for请求返回时, 它的更改对于搜索是可见的, 同时防止对被阻塞的请求使用未检查的资源

		* 如果一个请求因为耗尽了监听器插槽而强制刷新, 那么它的响应将包含"forced_refresh": true

--------------------
_all metadata		|
--------------------
	# 新建一个doc, ES把会doc的所有字段值, 都拆开, 组成一个长的字符串
		* 该字符串就是doc的 _all field 的值

	# 对该字符串建立索引, 如果搜索的时候, 没有指定搜索的field, 那么默认就是搜索 _all field
	

--------------------
数据结构的改变		|
--------------------
	# 一个json的doc, 会发生一下数据结构的改变
		* 会变成列式存储, 都会被转换为一列

	# 对象的改变
		{
			"name":"Java入门到入土",
			"author":{
				"name":"KevinBlandy",
				"age":23
			}
		}

		{
			"name":"Java入门到入土",
			"author.name":"KevinBlandy",
			"author.age":"KevinBlandy"
		}

	# 数组的改变
		{
			"name":"KevinBlandy",
			"skill":[{
				"name":"Java",
				"proficiency": 90
			},{
				"name":"Python",
				"proficiency": 80
			}]
		}

		{
			"name":"KevinBlandy",
			"skill.name":["Java", "Python"],
			"skill.proficiency":[90, 80]
		}


--------------------------------------------
对field索引两次来解决字符串排序的问题		|
--------------------------------------------
	# 对 String field 进行排序, 往往结果不准确, 因为分词后是是多个单词, 再次进行排序的时候, 结果不是我们想要的
	# 通常的解决方案是, 把一个 String field 建立两次索引, 一个分词, 用于搜索, 一个不分词, 用于排序

		{
			"title":{
				"type":"string",
				"analyzer":"english",
				"fields": {
					"raw": {
						"type": "string",
						"index": "not_analyzed",
						"fielddata": true
					}
				}
			}
		}

		GET /_search
		{
			"query":{
				"match":{
					"title":"Hello"
				}
			},
			"sort": "title.raw"
		}
	
