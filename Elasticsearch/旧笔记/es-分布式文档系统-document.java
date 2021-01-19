--------------------
document的更新		|
--------------------
	# 强制更新
		POST /{index_name}/{type_name}/{id}	
		{
		  "_index": "test_index",
		  "_type": "product",
		  "_id": "1",
		  "_version": 2,			
		  "result": "updated",
		  "_shards": {
			"total": 2,
			"successful": 1,
			"failed": 0
		  },
		  "_seq_no": 1,
		  "_primary_term": 1
		}

		* 请求体需要提交所有字段,不存在的字段会被删除
		* 不管本次提交,是否有成功修改字段,result值永远为:'updated'
		* 不管是有修改,_version字段必会加1
		* 可以理解为强制更新
	
	# 非强制更新
		POST /{index_name}/{type_name}/{id}/_update
		* 该种方式,提交的JSON体有所变化
			{
				"doc":{
					//需要修改的字段
				}
			}
		* 可以仅仅提交更新需要更新的字段
		* 如果本次提交未修改数据的话,那么result字段值为:'noop',并且没有:'_seq_no'和'_primary_term'字段,
		* 本次提交有修改数据的是,跟强制更新的返回结果是一样的
		* 只有在数据有修改的时候,version +1
		* 可以理解为非强制更新
		* partial update(部分更新)

	# 全量替换
		PUT  /{index_name}/{type_name}/{id}	

		* 如果id已经存在,那么原来的document不会被立即删除,而是会被标记为: delete
		* 当es中数据越来越多的时候,es会在后台自己动的把标记为:delete 的document物理删除掉
		* _version 始终会 +1
	
		
--------------------
document的强制创建	|
--------------------
	# 创建文档域全量替换的语法是一样的
	# 如果只想新建文档,不想替换文档,那么就需要强制创建(两种方式)
		PUT /index/type/id?op_type=create
		PUT /index/type/id/_create
			* 这种方式比较常见
	
		* 如果该id的document已经存在,那么不会PUT成功,会抛出异常

--------------------
document的删除		|
--------------------
	# document不会被立即的物理删除,只会被标记为delete,当数据越来越多的试试,在后台自动的删除

		DELETE /index/type/id

----------------------------
partial update 详解			|
----------------------------
	# partial update
	
		POST /index/type/id/_update
		{
			"doc":{
				"仅仅需要修改的数据,不需要全量的数据"
			}
		}
		* 看起来比较方便,仅仅需要传递修改修改的参数即可
		* '不需要先读取数据,再修改,直接提交需要修改的字段即可'
		
	# 内部原理
		* 其实es对 partial update 的执行,其实跟全量替换几乎是一样的
		* 在执行 partial update 的时候,内部还是会偷偷的先查询出所有document的数据,然后'更新需要更新的字段'
		* 更新完成后,把旧的document标记为delete,再写入新的doucment
	
	# 优点
		1,所有查询,修改,回写操作,都发生在一个shard内部,避免了所有网络数据传输的开销
			* 读取开销,回写开销
		2,减少了修改和查询中的网络间隔,可以有效减少冲突的情况
			* 当前用户在修改界面,占用时间过长,其实该document已经被其他的用户发生了修改,当前用户执行更新会发生冲突
	

----------------------------
partial update 乐观锁并发控制|
----------------------------
	# 默认会使用乐观锁的并发控制策略
		* partial update 提交到shard后,会先去内容读取该document的所有field,以及version
		* 修改partial update提交的部分field,然后回写,在回写的时候,使用version来处理并发控制

	# retry策略
		* 在执行修改时,发现version不对
		* 再一次读取documnet的最新版本号
		* 基于最新的版本号去更新document
		* 如果失败,则重复上述俩步骤,重复的次数可以通过 retry_on_conflict 值来控制

		POST /user/coder/1/_update?retry_on_conflict=5
	
	# 也可以手动通过请求参数 _version 来控制并发,当version不一致时,会给出异常


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
			> number_of_primary_shards ：primary shad的数量

		* 手动指定 routing
			PUT /index/type/id?routing=15
			GET  /index/type/id?routing=15

			> 通过参数手动指定routing value很有用,可以保证,某一类document一定被路由到一个shard上
			> 那么在后续进行应用级别的负载均衡,以及提升批量读取性能的时候,是很有帮助的

	# primary shard 数量不可变
		* 一旦发生变化了,那么在取模算法时,就会有问题,会间接导致数据丢失
		* 但是 replica shard可以随意增删

----------------------------
document 增删改内部实现原理	|
----------------------------
	1,客户端选择一个node发送请求,该node就是 coordinating node(协调节点)
		* 每个node,都知道document在哪个node上

	2,coordinating node,对document进行路由,将请求转发给对应的node(primary shard)
		* 增删改,只能由primary shard处理

	3,实际node上的primary shard处理请求,然后把数据同步到replica node
		

	4,coordinating node,如果发现primary node和所有replicat node都完成之后,响应客户端
	


-------------------------------
document写一致性	consistency	|
--------------------------------
	# 在执行增删改的时候,可以提交一个 consistency 参数,来指定写一致性的级别
		PUT /index/type/id?consistency=one
	
	# 该值是一个枚举值
		one
			* 只要有一个primary shard是active活跃可用,就可以执行

		all
			* 所有的primary shard和replica shard都是活跃的,才可以执行

		quorum(默认)
			* 当 number_of_replicas > 1时该机制才会生效
			* shard中活跃的shard数量超过/等于 quorum, 才可以执行写操作
			* 计算公式: quorum = int((primary + number_of_replicas) / 2) + 1
				primary				-> primary shard的数量
				number_of_replicas	-> 每个primary shard有几个replica shard

			* 如果活跃节点数量少于 quorum 数量,可能会导致quorum不齐全,进而导致无法执行写操作
			* quorum不齐全时,wait 默认1分钟(期望活跃的shard可以增加),可以通过 timeout 参数来控制(单位默认是毫秒,可以通过添加s来设置为秒)
				PUT /index/type/id?timeout=60s


-------------------------------
document 查询原理				|
--------------------------------
	1,客户端发送请求到任意node,该node成为:coordinate node

	2,coordinate node 对 document 进行路由,将请求结果转发到对应的node
		* 会使用 round-robin 随机轮询算法,在primary shard 和 replica shard 中随机选择一个
		* 让读,负载均衡

	3,处理请求的node返回document到 coordinate node

	4,coordinate node 返回document 给客户端

	5,特殊情况
		* document还在建立索引过程中,可能只有primary shard有,任何一个 replica shard都没有
		* 如果负载到了 relicat shard,那么可能会读取不到document


	