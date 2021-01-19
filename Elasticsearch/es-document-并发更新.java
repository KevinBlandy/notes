----------------------------
 乐观锁并发控制				|
----------------------------
	# 文档
		https://www.elastic.co/guide/en/elasticsearch/reference/current/optimistic-concurrency-control.html


	# 检索的结果中会存在两个元数据
		{
		  ...
		  "_seq_no" : 28,
		  "_primary_term" : 1
		}
	
		
	# CAS更新
		PUT /<index>/_doc/<id>?if_seq_no=<_seq_no>&if_primary_term=<_primary_term>
		
		POST /<index>/_doc/<id>?if_seq_no=<_seq_no>&if_primary_term=<_primary_term>

		POST /<index>/_update/<id>?if_seq_no=<_seq_no>&if_primary_term=<_primary_term>


		* 通过查询参数: if_seq_no 和 if_primary_term 来控制版本号,如果更新失败, 抛出异常
			{
			  "error": {
				"root_cause": [
				  {
					"type": "version_conflict_engine_exception",
					"reason": "[1]: version conflict, required seqNo [8], primary term [1]. current document has seqNo [9] and primary term [1]",
					"index_uuid": "NhO0l2JpRW-MwwnQpjexcA",
					"shard": "0",
					"index": "goods"
				  }
				],
				"type": "version_conflict_engine_exception",
				"reason": "[1]: version conflict, required seqNo [8], primary term [1]. current document has seqNo [9] and primary term [1]",
				"index_uuid": "NhO0l2JpRW-MwwnQpjexcA",
				"shard": "0",
				"index": "goods"
			  },
			  "status": 409
			}
		
		* 如果是部分更新, 那么只有在更新成功(更新的内容有修改)的时候才会去判断版本号

----------------------------
版本控制					|
----------------------------
	# 每个doc都有一个 version 字段
		{
		  "_version" : 20,
		}

		 * 默认从1开始, 并在每次更新时递增, 包括删除
		 
	# 执行修改时, 使用版本号
		PUT /<index>/_doc/<id>?version=<_version>&version_type=<version_type>
		{
			"message" : "elasticsearch now has versioning support, double cool!"
		}

		* CAS更新,如果使用doc内部的版本号则, 使用 if_seq_no 和 if_primary_term 
		* CAS更新,如果使用外部的版本号, 则使用 version
	
	# version_type ,枚举值
		internal
			* 仅在给定版本与存储文档的版本相同时才对文档编制索引
			* 已经不支持了: internal versioning can not be used for optimistic concurrency control. Please use `if_seq_no` and `if_primary_term` instead

		external / external_gt
		external_gte

	# 可以把版本号交给外部程序控制, external version
		* es提供了一个feature,可以不使用内容部的_version版本号来进行并发控制
		* 可以基于自己维护的'version版本号'来进行并发控制
		* 使用场景
			在mysql中也存在一份数据,应用系统本身就维护了一个版本号,此时使用乐观锁控制的时候,不想使用es的version,而是想使用应用系统中的version
		
		* version控制语法
			?version=<_version>&version_type=external

		* 当 version_type=external 的时候,version参数必须要大于当前的_version才能更新成功
		* 在修改成功后,并且会把document的_version修改为version参数的值