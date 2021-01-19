------------------------------
PartitionAssignor			  |
------------------------------
	# 消费者的分区分配策略
	# 抽象方法
		Subscription subscription(Set<String> topics);

		Map<String, Assignment> assign(Cluster metadata, Map<String, Subscription> subscriptions);

		void onAssignment(Assignment assignment);

		String name();

		class Subscription {
			private final List<String> topics;
			private final ByteBuffer userData;

			public Subscription(List<String> topics, ByteBuffer userData) {
				this.topics = topics;
				this.userData = userData;
			}

			public Subscription(List<String> topics) {
				this(topics, ByteBuffer.wrap(new byte[0]));
			}

			public List<String> topics() {
				return topics;
			}

			public ByteBuffer userData() {
				return userData;
			}

			@Override
			public String toString() {
				return "Subscription(" +
						"topics=" + topics +
						')';
			}
		}

		class Assignment {
			private final List<TopicPartition> partitions;
			private final ByteBuffer userData;

			public Assignment(List<TopicPartition> partitions, ByteBuffer userData) {
				this.partitions = partitions;
				this.userData = userData;
			}

			public Assignment(List<TopicPartition> partitions) {
				this(partitions, ByteBuffer.wrap(new byte[0]));
			}

			public List<TopicPartition> partitions() {
				return partitions;
			}

			public ByteBuffer userData() {
				return userData;
			}

			@Override
			public String toString() {
				return "Assignment(" +
						"partitions=" + partitions +
						')';
			}
		}
