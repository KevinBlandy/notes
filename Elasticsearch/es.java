-----------------------------
Elasticsearch			 	 |
-----------------------------
	# 官网
		https://www.elastic.co
		https://www.elastic.co/cn/
		https://www.elastic.co/guide/en/elasticsearch/reference/index.html

	# Github
		https://github.com/elastic/elasticsearch
	
	# 参考
		https://blog.csdn.net/laoyang360/article/details/79293493
		https://github.com/laoyang360/deep_elasticsearch
		http://www.ruanyifeng.com/blog/2017/08/elasticsearch.html
		https://elasticsearch.apachecn.org/#/

		https://elastic.blog.csdn.net/
		https://elasticsearch.cn/

	

-----------------------------
Elasticsearch-目录结构		 |
-----------------------------
	bin
		|-elasticsearch
		|-elasticsearch-plugin
	config
		|-elasticsearch.yml
		|-jvm.options
		|-log4j2.properties
		|-role_mapping.yml
		|-roles.yml
		|-users
		|-users_roles
	jdk
	lib
	logs
	modules
	plugins

------------------------------
Linux安装启动					|
------------------------------
	# 下载指定的版本
		https://www.elastic.co/cn/downloads/elasticsearch
	
	# 解压到目录

	# 创建运行用户, 执行目录授权
		* ES不允许直接使用root账户进行启动, 会给出异常:can not run elasticsearch as root

		useradd -r elasticsearch

		chown elasticsearch:elasticsearch [path] -R

		* 如果指定了其他的志数据目录, 也需要进行授权

	
	# 启动脚本
		/bin/elasticsearch
			-d
				* 在后台执行
			-E
				* 设置配置参数, 覆盖 elasticsearch.yml
					-E path.data=node1_data
				* 可以出现多个
		
	
	# 访问:http://127.0.0.1:9200/
		{
			"name": "KEVINBLANDY",
			"cluster_name": "elaticsearch",
			"cluster_uuid": "wCaZ0Z6rSmmFpFjQFSWjDw",
			"version": {
			"number": "7.1.1",
			"build_flavor": "default",
			"build_type": "zip",
			"build_hash": "7a013de",
			"build_date": "2019-05-23T14:04:00.380842Z",
			"build_snapshot": false,
			"lucene_version": "8.0.0",
			"minimum_wire_compatibility_version": "6.8.0",
			"minimum_index_compatibility_version": "6.0.0-beta1"
			},
			"tagline": "You Know, for Search"
		}

-----------------------------
Elasticsearch-核心概念		 |
-----------------------------
	Near Realtime(NRT)
		# 近实时,两个意思,从写入数据到可以搜索大约会有1s的延迟,基于es进行搜索和分析可以达到秒级
	
	Cluster
		# 节点,集群中的一个节点,节点会有一个名称,默认是随机分配的
		# 节点名称很重要,节点默认会去加入一个名为:elaticsearch 的集群
		# 如果启动一堆节点,那么它们会自动组成一个es集群,当然,一个节点也可以组成一个es集群
	
	Index
		# 索引,包含一堆有相似结构的文档数据,比如可以有一个客户索引,商品分类索引,订单索引,索引是有一个名称的
	
	Type
		# 类型,每个索引里都可以有一个或者多个type,type是index中的另一个逻辑分类
		# 一个type下的document都有相同的field,比如博客系统,有一个索引,可以定义用户数据type,博客数据type,评论数据type
		# 在7里面已经被沉底的删除了
	
	Documen
		# 文档,es种的最小数据单元,一个document可以是一条客户数据,一条商品分类数据
		# 通常使用JSON数据结构表示,每个index下的type中,都可以存储多个document
	
	Shard
		# 单台机器无法存储大量数据,es可以吧一个索引数据分为多个shard,分布式在多台服务器上存储
		# 有了Shard就可以横向扩展,存储更多数据,让搜索和分析等操作分布到多台服务器上去执行,提升吞吐量和性能
		# 每个shard都是一个lucene index
	
	Replica
		# 任何一个服务器都有可能会宕机,此时shard就会丢失,因此可以为每个shard创建n个replica副本
		# replia可以在shard故障时,提供服务,保证shard的不丢失,多个replica还可以提升搜索操作的吞吐量和性能
		# Shard   -> primary shard(建立索引时一次设置,不能修改,默认5个)
		# Replica -> replica shard(随时修改数量,默认1个)
		# 默认每个索引有10个shard,5个primary shard,5个replica shard
		# 最小的高可用peizhi,是两台服务器
	

	# 传统关系型数据库的对比
		Relational DB ->	Databases	-> Tables	-> Rows			-> Columns
		Elasticsearch ->	Indices		-> Types	-> Documents	-> Fields



-----------------------------
Elasticsearch-征途			 |
-----------------------------
倒排索引
打分机制
全文检索原理
分词原理