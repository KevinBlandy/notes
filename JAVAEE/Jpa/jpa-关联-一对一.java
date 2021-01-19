-----------------------------
一对一的关系
-----------------------------
	# 用户
		@Entity
		@Table(name = "user")
		public class User implements Serializable {
			
			private static final long serialVersionUID = 8175166175439387541L;
			
			@Id
			@Column(name = "id", columnDefinition = "INT(11) UNSIGNED COMMENT '用户id'")
			@GeneratedValue(strategy = GenerationType.IDENTITY)
			private Integer id;
			
			@Column(columnDefinition = "varchar(20) COMMENT '昵称'")
			private String name;
			
			@OneToOne(targetEntity = UserSeting.class)
			// 当前对象的关联字段, 对方对象的字段
			@JoinColumn(name = "id", referencedColumnName = "user_id", unique = true, nullable = false)
			private UserSeting userSeting;
		
		}

	# 用户设置
		@Entity
		@Table(name = "user_seting")
		public class UserSeting implements Serializable {
			private static final long serialVersionUID = -1007318207008996614L;
			@Id
			@Column(name = "id", columnDefinition = "INT(11) unsigned COMMENT '设置id'")
			@GeneratedValue(strategy = GenerationType.IDENTITY)
			private Integer id;
			
			@Column(columnDefinition = "TINYINT(1) unsigned COMMENT '是否接受通知'")
			private Boolean notify;
			
			@OneToOne(targetEntity = User.class)
			// 当前对象的关联字段, 对方对象的字段
			@JoinColumn(name = "user_id", referencedColumnName = "id", unique = true, nullable = false)
			private User user;
		}
	
	# 关系只绑定一方1
		// 用户
		@OneToOne(targetEntity = UserSeting.class, cascade = CascadeType.PERSIST)
		@JoinColumn(name = "id", columnDefinition = "user_id", unique = true, nullable = false)
		private UserSeting userSeting;
		

		// 用户设置
		@Column(columnDefinition = "INT(11) unsigned COMMENT '用户id'", unique = true, nullable = false)
		private Integer userId;
		
		* 此时, 用户设置表中, 没有设置 Foreign Key 
	
	# 关系只绑定一方2
		// 用户

		// 用户设置
		@OneToOne(targetEntity = User.class)
		@JoinColumn(name = "user_id", referencedColumnName = "id", unique = true, nullable = false)
		private User user;

		* 此时, 用户设置表中, 有设置 Foreign Key 



	
-----------------------------
一对一的关系注解
-----------------------------
	@OneToOne
		Class targetEntity() default void.class;
		CascadeType[] cascade() default {};
			* 级联操作策略, 枚举
				ALL				关联所有操作
				PERSIST			级联保存
				MERGE			级联更新
				REMOVE			级联删除
				REFRESH			级联刷新
				DETACH			级联分离
			* 默认为空, 不会产生关联操作
				
		FetchType fetch() default EAGER;
			* 关联数据的获取方式, 枚举
				LAZY	延迟加载
				EAGER	立即加载

		boolean optional() default true;
			* 是否允许为空

		String mappedBy() default "";
			* 关联关系被谁维护, 一般不用填写该字段, 表示当前实体在维护
			* 如果要放弃维护权, 则该属性指的是, 另一方的实体里面 @JoinColumn 或者 @JoinTable 的属性字段名称, 而不是数据库字段, 也不是实体对象的名称

			* 只有关系维护方才能操作两者关系, 被维护方即使设置了维护方属性进行存储也不会更新外键关联(避免级联保存的时候, 多一个update操作)
			* 当使用了该注解的时候, 该字段不能添加 @JoinColumn 或者 @JoinTable (不能同时使用)

		boolean orphanRemoval() default false;
			* 是否级联删除, 跟 cascade = CascadeType.REMOVE 效果一样
			* 只要是配置了两种类型中的其中一个, 都会被级联删除
		
		* 一对一的映射关系, 可以双向关联, 也可以只配置一方

-----------------------------
一对一的保存
-----------------------------
	# 分别保存
		* 先保存其中一个, 再保存另外一个
	
		// 先存储用户
		User user = new User();
		user.setName("KevinBlandy");
		this.userRepository.save(user);
		
		// 再存储设置
		UserSeting userSeting = new UserSeting();
		userSeting.setNotify(Boolean.FALSE);
		userSeting.setUser(user);
		
		this.userSetingRepository.save(userSeting);
		
		* 两个insert, 先insert用户, 再insert设置

	# 级联保存
		* 执行保存的一方需要设置了级联保存: cascade = CascadeType.PERSIST 
			@OneToOne(targetEntity = UserSeting.class, cascade = CascadeType.PERSIST)
			@JoinColumn(name = "id", columnDefinition = "user_id", unique = true, nullable = false)

		// 创建用户
		User user = new User();
		user.setName("KevinBlandy");
		
		// 创建设置
		UserSeting userSeting = new UserSeting();
		userSeting.setNotify(Boolean.FALSE);
		
		
		// 设置关系
		userSeting.setUser(user);
		user.setUserSeting(userSeting);
		
		this.userRepository.save(user);

		* 两个insert, 先insert用户, 再insert设置
	

-----------------------------
一对一的删除
-----------------------------
		

