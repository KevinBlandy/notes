--------------------------------
事务
--------------------------------
	# MongoDB 支持跨多个操作、集合、数据库、文档和分片的 ACID 事务
		
		* MongoDB 4.2 或更高版本才支持。
		* 在事务中只能对已存在的集合或数据库执行读写，在事务中不允许创建、删除集合或者进行索引操作。
	
	# 事务的超时时间
		* 事务的默认最大运行时间是 1 分钟。可以通过在 mongod 实例级别上修改 transactionLifetimeLimitSeconds 的限制来增加。
		* 对于分片集群，必须在所有分片副本集成员上设置该参数。超过此时间后，事务将被视为已过期，并由定期运行的清理进程中止。
		* 清理进程每 60 秒或每 transactionLifetimeLimitSeconds/2 运行一次，以较小的值为准。

		* 要显式设置事务的时间限制，建议在提交事务时指定 maxTimeMS 参数。
		* 如果 maxTimeMS 没有设置，那么将使用 transactionLifetimeLimitSeconds
		* 如果设置了 maxTimeMS，但这个值超过了 transactionLifetimeLimitSeconds，那么还是会使用 transactionLifetimeLimitSeconds。
	
	
	# 获取锁超时时间
		* 事务等待获取其操作所需锁的默认最大时间是 5 毫秒
		* 可以通过修改由 maxTransactionLockRequestTimeoutMillis 参数控制的限制来增加。
		* 如果事务在此期间无法获得锁，则该事务会被中止。
		
		* maxTransactionLockRequestTimeoutMillis 可以设置为 0、-1 或大于 0 的数字。
		* 将其设置为 0 意味着，如果事务无法立即获得所需的所有锁，则该事务会被中止。
		* 设置为 -1 将使用由 maxTimeMS 参数所指定的特定于操作的超时时间。
		* 任何大于 0 的数字都将等待时间配置为该时间（以秒为单位）以作为事务尝试获取所需锁的指定时间段。
	
	# oplog 大小限制
		* MongoDB 会创建出与事务中写操作数量相同的 oplog 条目。但是，每个oplog 条目必须在 16MB 的 BSON 文档大小限制之内。
		
