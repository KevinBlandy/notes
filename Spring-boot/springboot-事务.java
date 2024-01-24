---------------------------
Spring-boot ����			|
---------------------------
	# spring ����������ṩ��һ��ͳһ�Ľӿ�
		PlatformTransactionManager 
	
	# �ӿڵ�ʵ��
		JDBC		-->		DataSourceTransactionManager		//ͬ�����ʺ�MyBatis
		JPA			-->		JpaTransactionManager				//JPA
		Hibernate	-->		HibernateTransactionManager			//�ʺ�Hibernate3.0
		JDO			-->		JdoTransactionManager
		�ֲ�ʽ����	-->		JtaTransactionManager				//JTA

	# �ڳ����ж������������
		@Bean
		public PlatformTransactionManager platformTransactionManager(){
			DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
			dataSourceTransactionManager.setDataSource(dataSource());
			return dataSourceTransactionManager;
		}
	
	# ����ʽ����
		* spring ֧������ʽ����.ʹ��ע�� @Transactional
		* ��ע�����ϸ����,��spring������
	
	# ��������ʽ����
		* spring����һ��ע�� @EnableTransactionManagement
		* ע������������,����ǹ����ʽ�����֧��
		* ʹ�ø�ע���,spring�������Զ�ɨ��ע�� @Transactional �ķ�������
			@Configuration
			@EnableTransactionManagement
			public class transactionManagementAutoConfiguration		{
			}
		* ��Ҫ��������
			<dependency>
				<groupId>org.springframework</groupId>
				<artifactId>spring-tx</artifactId>
			</dependency>

		* ��ע����Ա�ʶ�ڷ�����
		* Ҳ���Ա�ʶ������,��ô���������е� public ����,����������
		
	# spring boot������֧��
		* ��ʹ��JDBC��Ϊ���ݷ��ʼ�����ʱ��,spring bootΪ���Ƕ����� PlatformTransactionManager ��ʵ�� DataSourceTransactionManager,Դ��:DataSourceTransactionManagerAutoConfiguration
		* ��ʹ��JPA....
		* spring bootר���û��������������:org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration
		* 'spring boot�в���Ҫ��ʾ����ʹ��'@EnableTransactionManagement ע��,'ֱ���������ע'
	
		* �������
			 <dependency>
				<groupId>org.springframework</groupId>
				<artifactId>spring-tx</artifactId>
			</dependency>
		
		* ���ע��
			@EnableTransactionManagement
	
	# ʹ�ü�����, ���������״̬
		* ʹ��:@TransactionalEventListener ע��, ��ʶ�ڼ�������
			@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
			@Retention(RetentionPolicy.RUNTIME)
			@Documented
			@EventListener
			public @interface TransactionalEventListener {

				TransactionPhase phase() default TransactionPhase.AFTER_COMMIT;

				boolean fallbackExecution() default false;

				@AliasFor(annotation = EventListener.class, attribute = "classes")
				Class<?>[] value() default {};

				 */
				@AliasFor(annotation = EventListener.class, attribute = "classes")
				Class<?>[] classes() default {};

				String condition() default "";

			}
			
			* phase ö��, ��ʾ����Ȥ���¼�
				BEFORE_COMMIT,
				AFTER_COMMIT,
				AFTER_ROLLBACK,
				AFTER_COMPLETION
			
			* fallbackExecution 


---------------------------
TransactionTemplate
---------------------------
	# �ֶ�ִ�����������ģ��

		@Autowired
		TransactionTemplate transactionTemplate;
		
		// ��������������뼶��
		this.transactionTemplate.setIsolationLevel(TransactionTemplate.ISOLATION_REPEATABLE_READ);
		// �����������񴫲�����
		this.transactionTemplate.setPropagationBehavior(TransactionTemplate.PROPAGATION_REQUIRED);
		// �������������Ƿ�ֻ��
		this.transactionTemplate.setReadOnly(false);

		// ����jieguo
		this.transactionTemplate.execute(status -> {
			// TODO ִ�����񷽷�������jieguo
			// ��������쳣���Զ��ع�����֮������Զ��ύ
			return null;
		});

		// ������ִ�н��
		this.transactionTemplate.executeWithoutResult(status -> {
			
		});
		