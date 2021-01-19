--------------------------------
并发冲突-悲观锁					|
--------------------------------
	# 常见于关系型数据库中,不多解释
		 SELECT .. FROM ... WHERE ... FOR UPDATE;
	
	# 并发能力差,但是方便
		


--------------------------------
并发冲突-乐观锁					|
--------------------------------
	# CAS 算法
	# 可以通过version字段来控制
	# 场景
		1,
		  * 线程a读取商品,剩余100,version=1
		  * 线程b读取商品,剩余100,version=1
		 
		2,
		  * 线程a下单,把库存 -1
		  * 回写的时候检查当前数据的version是是否跟es中的version一样
		  * 一样则回写,不一样,则重新读取最新数据后 -1,再回写
	
	# 并发能力好,但是麻烦
	# 关于_version
		* 每次创建新的document的时候,它的内部 _version 内部版本号就是1
		* 以后每次对该doucment进行修改/删除的时候,该_version都会自动+1(就算是删除,也会+1)
		* 删除document的时候,并不会立即的进行物理删除,还保留着它们的元信息(_version等...)
		
		* 先删除一个document,再重新put(创建)的时候,_version 会在delete的记录上+1(玄学)
	
	# es内部多线程异步并发执行修改时,是基于_version版本号进行乐观锁控制的
		* 在同步修改的时候,会比较一下当前es的_version是否与当前的_version一样
		* 不一样就丢掉,一样则更新
	
	# 基于 _version 来进行乐观锁进行并发冲突的控制
		POST /{index_name}/{type_name}/{id}?version=1
		POST /{index_name}/{type_name}/{id}/_update?version=1
		PUT  /{index_name}/{type_name}/{id}?version=1

		* 说白了,就是在进行修改的时候需要带上当前版本号
		* 在uri添加查询参数 version,该值为当前document的version字段
		* 在更新的时候系统会对进行乐观锁控制,如果version一样则修改,否则返回错误信息,不修改
	
	# 基于乐观锁的的demo
		1,创建数据
			PUT /user/1
			{
				"name":"KevinBlandy"
			}
		
		2,线程1,更新
			PUT /user/1?version=1
			{
				"name":"KevinBlandy1"
			}
			* 更新成功,_version = 2

		3,线程2,更新
			PUT /user/1?version=1
			{
				"name":"KevinBlandy1"
			}
			* 异常,因为线程1已经修改成功,version已经是2了,
				{
				  "error": {
					"root_cause": [
					  {
						"type": "version_conflict_engine_exception",
						"reason": "[coder][666]: version conflict, current version [2] is different than the one provided [1]",
						"index_uuid": "hmtk8vwASdCPpQENyyLwqw",
						"shard": "0",
						"index": "user"
					  }
					],
					"type": "version_conflict_engine_exception",
					"reason": "[coder][666]: version conflict, current version [2] is different than the one provided [1]",
					"index_uuid": "hmtk8vwASdCPpQENyyLwqw",
					"shard": "0",
					"index": "user"
				  },
				  "status": 409
				}
						
	# external version
		* es提供了一个feature,可以不使用内容部的_version版本号来进行并发控制
		* 可以基于自己维护的'version版本号'来进行并发控制
		* 使用场景
			在mysql中也存在一份数据,应用系统本身就维护了一个版本号,此时使用乐观锁控制的时候,不想使用es的version,而是想使用应用系统中的version
		
		* version控制语法
			?version=1&version_type=external

		* 当 version_type=external 的时候,version参数必须要大于当前的_version才能更新成功
		* 在修改成功后,并且会把document的_version修改为version参数的值
		

			