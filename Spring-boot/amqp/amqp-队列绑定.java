------------------
队列绑定
------------------
	# 通过 bean 声明绑定
		@Bean
		public Queue queue() {
			return new Queue("my-queue", true, false, false, null);
		}

		@Bean
		public DirectExchange exchange() {
			return new DirectExchange("my-exchange", true, false);
		}
		
		@Bean
		public Binding binding () {
			return BindingBuilder.bind(this.queue()).to(this.exchange()).with("my-key");
		}

------------------
BindingBuilder
------------------
	# 用户编程式绑定交换机和队列
		* 绑定队列到交换机
			BindingBuilder.bind(this.queue()).to(this.exchange()).with("my-key");
		
	