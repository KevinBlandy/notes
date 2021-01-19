----------------------------
kafka-reassign-partitions.sh|
----------------------------
	# 负责处理分区的重新分配
		bin/kafka-reassign-partitions.sh  --zookeeper localhost:2181  --generate --topics-to-move-json-file  reassign.json --broker-list 0,2
			* 生成分区的分配方案

		bin/kafka-reassign-partitions.sh  --zookeeper localhost:2181  --execute --reassignment-json-file  project.json
			* 执行分区的分配方案

		bin/kafka-reassign-partitions.sh  --zookeeper localhost:2181  --verify --reassignment-json-file  project.json
			* 查看指定方案的分配进度

			--generate
				* 是当前脚本的指令,用来生成一个重新分配的方案

			--topics-to-move-json-file
				* 指定主题清单JSON文件

			--broker-list
				* 指定要分配的broker节点列表(broker.id)

			--execute
				* 是当前脚本的指令,用来执行一个重新分配的方案

			--reassignment-json-file
				* 指定分配方案的JSON文件
			
			--verify
				* 查看指定方案的分配进度
			
			--throttle
				* 在执行分区分配的时候,限制副本的数据传输速度
				* byte/s