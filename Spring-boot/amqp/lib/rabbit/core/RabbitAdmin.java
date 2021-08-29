------------------------
RabbitAdmin
------------------------
	# Rabbitmq对于AmqpAdmin的实现，可以从IOC获取到
		public class RabbitAdmin implements AmqpAdmin, ApplicationContextAware, ApplicationEventPublisherAware, BeanNameAware, InitializingBean
	
	public static final String DEFAULT_EXCHANGE_NAME = "";
	public static final Object QUEUE_NAME = "QUEUE_NAME";
	public static final Object QUEUE_MESSAGE_COUNT = "QUEUE_MESSAGE_COUNT";
	public static final Object QUEUE_CONSUMER_COUNT = "QUEUE_CONSUMER_COUNT";

	public RabbitAdmin(ConnectionFactory connectionFactory)
	public RabbitAdmin(RabbitTemplate rabbitTemplate)
	public void setAutoStartup(boolean autoStartup)
	public void setApplicationContext(ApplicationContext applicationContext)
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher)
	public void setIgnoreDeclarationExceptions(boolean ignoreDeclarationExceptions)
	public DeclarationExceptionEvent getLastDeclarationExceptionEvent()
	public void setTaskExecutor(TaskExecutor taskExecutor)
	public RabbitTemplate getRabbitTemplate()
	public void declareExchange(final Exchange exchange) 
	public boolean deleteExchange(final String exchangeName) 
	public String declareQueue(final Queue queue) 
	public Queue declareQueue()
	public boolean deleteQueue(final String queueName)
	public void deleteQueue(final String queueName, final boolean unused, final boolean empty) 
	public void purgeQueue(final String queueName, final boolean noWait)
	public int purgeQueue(final String queueName)
	public void declareBinding(final Binding binding) 
	public void removeBinding(final Binding binding)
	public Properties getQueueProperties(final String queueName)
	public QueueInformation getQueueInfo(String queueName)
	public void setExplicitDeclarationsOnly(boolean explicitDeclarationsOnly)
	public void setRetryTemplate(@Nullable RetryTemplate retryTemplate)
	public void setBeanName(String name)
	public String getBeanName()

	public boolean isAutoStartup() 
	public void afterPropertiesSet()
	public void initialize(
