-------------------------
KafkaAdminClient		 |
-------------------------
	# 继承自:AdminClient
	# 实例的创建
		KafkaAdminClient KafkaAdminClient.create(Map<String,Object> config);
		KafkaAdminClient KafkaAdminClient.create(Properties properties)
	
	# 方法
		AlterConfigsResult alterConfigs(Map<ConfigResource, Config> configs, final AlterConfigsOptions options)
			* 修好主题的配置

		AlterReplicaLogDirsResult alterReplicaLogDirs(Map<TopicPartitionReplica, String> replicaAssignment, final AlterReplicaLogDirsOptions options)

		void close(long duration, TimeUnit unit);

		CreateAclsResult createAcls(Collection<AclBinding> acls, CreateAclsOptions options)
		CreateDelegationTokenResult createDelegationToken(final CreateDelegationTokenOptions options)
		CreatePartitionsResult createPartitions(Map<String, NewPartitions> newPartitions,final CreatePartitionsOptions options)
			* 修改parition数量和副本的数量

		CreateTopicsResult createTopics(final Collection<NewTopic> newTopics,final CreateTopicsOptions options)
			* 创建topic

		DeleteAclsResult deleteAcls(Collection<AclBindingFilter> filters, DeleteAclsOptions options)
		DeleteConsumerGroupsResult deleteConsumerGroups(Collection<String> groupIds, DeleteConsumerGroupsOptions options)
		DeleteRecordsResult deleteRecords(final Map<TopicPartition, RecordsToDelete> recordsToDelete, final DeleteRecordsOptions options)
		DeleteTopicsResult deleteTopics(Collection<String> topicNames, DeleteTopicsOptions options)
			* 删除topic

		DescribeAclsResult describeAcls(final AclBindingFilter filter, DescribeAclsOptions options)
		DescribeClusterResult describeCluster(DescribeClusterOptions options) 
		DescribeConfigsResult describeConfigs(Collection<ConfigResource> configResources, final DescribeConfigsOptions options)
		DescribeConsumerGroupsResult describeConsumerGroups(final Collection<String> groupIds, final DescribeConsumerGroupsOptions options)
		DescribeDelegationTokenResult describeDelegationToken(final DescribeDelegationTokenOptions options)
		DescribeLogDirsResult describeLogDirs(Collection<Integer> brokers, DescribeLogDirsOptions options)
		DescribeReplicaLogDirsResult describeReplicaLogDirs(Collection<TopicPartitionReplica> replicas, DescribeReplicaLogDirsOptions options)
		DescribeTopicsResult describeTopics(final Collection<String> topicNames, DescribeTopicsOptions options)
			* 获取指定topic的相信信息
			* 包括分区,isr,leader等信息

		ExpireDelegationTokenResult expireDelegationToken(final byte[] hmac, final ExpireDelegationTokenOptions options)
		ListConsumerGroupOffsetsResult listConsumerGroupOffsets(final String groupId, final ListConsumerGroupOffsetsOptions options)
		ListConsumerGroupsResult listConsumerGroups(ListConsumerGroupsOptions options)
		ListTopicsResult listTopics(final ListTopicsOptions options)
			* 获取所有的topic数量

		Map<MetricName, ? extends Metric> metrics()
		RenewDelegationTokenResult renewDelegationToken(final byte[] hmac, final RenewDelegationTokenOptions options)
	
	# 基本的一些操作
		public static void addPartition() throws InterruptedException, ExecutionException {
			Properties properties = new Properties();
			properties.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
			KafkaAdminClient kafkaAdminClient = (KafkaAdminClient) KafkaAdminClient.create(properties);
			
			/**
			 * 把指定的partition数量增加到5个
			 */
			// NewPartitions newPartitions = NewPartitions.increaseTo(5); // 参数表示修改后的数量
			
			/**
			 * 把指定的parition数量增加到6个
			 * 并且重新指定其副本的分配方案
			 */
			List<List<Integer>> newAssignments = new ArrayList<>();
			newAssignments.add(Arrays.asList(1,2));		// 第1个分区,有两个副本,在broker 1 和 2上	leader节点在broker2上
			newAssignments.add(Arrays.asList(2,3));		// 第2个分区,有两个副本,在broker 2 和 3上	leader节点在broker3上
			newAssignments.add(Arrays.asList(3,1));		// 第3个分区,有两个副本,在broker 3 和 1上	leader节点在broker1上
			NewPartitions newPartitions = NewPartitions.increaseTo(6,newAssignments);
			
			// 执行修改操作
			CreatePartitionsResult createPartitionsResult = kafkaAdminClient.createPartitions(Collections.singletonMap("topic_1", newPartitions));
			createPartitionsResult.all().get();
		}

		public static void alterConfig() throws InterruptedException, ExecutionException {
			Properties properties = new Properties();
			properties.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
			KafkaAdminClient kafkaAdminClient = (KafkaAdminClient) KafkaAdminClient.create(properties);
			// ConfigEntry 表示一个配置项
			ConfigEntry configEntry = new ConfigEntry("cleanup.policy","compact");
			// 多个配置项构造成 Config
			Config config = new Config(Arrays.asList(configEntry));
			// 根据 topic/broker 和Config 构建map
			Map<ConfigResource, Config> configMap = Collections.singletonMap(new ConfigResource(ConfigResource.Type.TOPIC, "topic_1"), config);
			// 执行修改
			 AlterConfigsResult alterConfigsResult = kafkaAdminClient.alterConfigs(configMap);
			 alterConfigsResult.all().get(); // 阻塞，直到所有的修改都完成
		}
		public static void describe() throws InterruptedException, ExecutionException {
			Properties properties = new Properties();
			properties.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
			KafkaAdminClient kafkaAdminClient = (KafkaAdminClient) KafkaAdminClient.create(properties);
			DescribeConfigsResult describeConfigsResult = kafkaAdminClient.describeConfigs(Arrays.asList(new ConfigResource(ConfigResource.Type.TOPIC,"topic_1")));
			Map<ConfigResource, Config> configMap = describeConfigsResult.all().get();
			for(Map.Entry<ConfigResource, Config> entry : configMap.entrySet()) {
				// 类型和名称
				System.out.println("type=" + entry.getKey().type() + " name=" + entry.getKey().name());
				// 配置详情
				System.out.println(entry.getValue());
			}
		}

		public static void createTopic() throws InterruptedException, ExecutionException {
			Properties properties = new Properties();
			properties.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
			KafkaAdminClient kafkaAdminClient = (KafkaAdminClient) KafkaAdminClient.create(properties);
			NewTopic newTopic = new NewTopic("topic_1", 1, (short)1);
			CreateTopicsResult createTopicsResult = kafkaAdminClient.createTopics(Arrays.asList(newTopic));
			createTopicsResult.all().get();// 阻塞线程，直到所有主题创建成功
		}

		public static void deleteTopic() throws InterruptedException, ExecutionException {
			Properties properties = new Properties();
			properties.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
			KafkaAdminClient kafkaAdminClient = (KafkaAdminClient) KafkaAdminClient.create(properties);
			DeleteTopicsResult deleteTopicsResult = kafkaAdminClient.deleteTopics(Arrays.asList("demo1","demo2","demo3","test"));
			deleteTopicsResult.all().get(); // 阻塞线程，直到所有主题删除成功
		}

		public static void listTopics() throws InterruptedException, ExecutionException {
			Properties properties = new Properties();
			properties.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
			KafkaAdminClient kafkaAdminClient = (KafkaAdminClient) KafkaAdminClient.create(properties);
			ListTopicsResult listTopicsResult = kafkaAdminClient.listTopics();
			Collection<TopicListing>  topicListings = listTopicsResult.listings().get();
			for(TopicListing topicListing : topicListings) {
				System.out.println("主题名称:" + topicListing.name() + " 是否是内部的:" + topicListing.isInternal());
			}
		}
		
-------------------------
AdminClientConfig		 |
-------------------------
	# 管理客户端的配置项
	bootstrap.servers
		* broker节点的地址
	client.id
	metadata.max.age.ms
	send.buffer.bytes
	receive.buffer.bytes
	reconnect.backoff.ms
	reconnect.backoff.max.ms
	retry.backoff.ms
	request.timeout.ms
	connections.max.idle.ms
	retries
	metrics.sample.window.ms
	metrics.num.samples
	metric.reporters
	metrics.recording.level
	client.dns.lookup
	security.protocol
	ssl.protocol
	ssl.provider
	ssl.cipher.suites
	ssl.enabled.protocols
	ssl.keystore.type
	ssl.keystore.location
	ssl.keystore.password
	ssl.key.password
	ssl.truststore.type
	ssl.truststore.location
	ssl.truststore.password
	ssl.keymanager.algorithm
	ssl.trustmanager.algorithm
	ssl.endpoint.identification.algorithm
	ssl.secure.random.implementation
	sasl.kerberos.service.name
	sasl.kerberos.kinit.cmd
	sasl.kerberos.ticket.renew.window.factor
	sasl.kerberos.ticket.renew.jitter
	sasl.kerberos.min.time.before.relogin
	sasl.login.refresh.window.factor
	sasl.login.refresh.window.jitter
	sasl.login.refresh.min.period.seconds
	sasl.login.refresh.buffer.seconds
	sasl.mechanism
	sasl.jaas.config
	sasl.client.callback.handler.class
	sasl.login.callback.handler.class
	sasl.login.class
