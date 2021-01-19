------------------------------------
kafka-preferred-replica-election.sh	|
------------------------------------
	# 手动执行优先副本的leader选举脚本

	kafka-preferred-replica-election.sh --zookeeper localhost:2181	
		
		--path-to-json-file
			* 可以通过该参数指定一个JSON文件
			* 该JSON文件保存需要执行优先副本选举的parition清单(而不是执行所有分区)
				{
					"paritions":[{
						"parition":0,
						"topic":"topic-paritions"
					},{
						"parition":1,
						"topic":"topic-paritions"
					}]
				}