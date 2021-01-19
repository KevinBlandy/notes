-----------------------------
一对多的关系
-----------------------------
	# 用户
		@Entity
		@Table(name = "user")
		public class User implements Serializable {
			@Id
			@Column(name = "id", columnDefinition = "INT(11) UNSIGNED COMMENT '用户id'")
			@GeneratedValue(strategy = GenerationType.IDENTITY)
			private Integer id;
			
			@Column(columnDefinition = "varchar(20) COMMENT '昵称'")
			private String name;

			@OneToMany(targetEntity = Address.class)
			// 关联对方表的字段, 当前表的字段
			@JoinColumn(name = "user_id", referencedColumnName = "id")
			private Set<Address> addresses = new HashSet<>();
		}


	# 用户地址
		@Entity
		@Table(name = "address")
		public class Address implements Serializable{
			
			@Id
			@Column(name = "id", columnDefinition = "INT(11) UNSIGNED COMMENT '地址id'")
			@GeneratedValue(strategy = GenerationType.IDENTITY)
			private Integer id;
			
			@Column(columnDefinition = "VARCHAR(500) COMMENT '详细地址'")
			private String name;
			
			@ManyToOne(targetEntity = User.class)
			// 当前表的字段 , 关联对方表的字段
			@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
			private User user;
		}
	
	# 单向的配置1
		// 用户
		@OneToMany(targetEntity = Address.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
		@JoinColumn(name = "user_id", referencedColumnName = "id")
		private Set<Address> addresses = new HashSet<>();


		// 地址
		@Column(name = "user_id", columnDefinition = "INT(11) unsigned COMMENT '用户id'", unique = false, nullable = false)
		private Integer userId;

		* 此时, 地址表中, 有设置 Foreign Key 
	
	# 单向的配置2
		

		// 仅仅在地址配置映射关系
		@ManyToOne(targetEntity = User.class)
		@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
		private User user;


		* 此时, 地址表中, 有设置 Foreign Key 
			
		
-----------------------------
一对多的关系注解
-----------------------------
	@OneToMany
		Class targetEntity() default void.class;
			* 对方实体类型(多的一方的类型)

		CascadeType[] cascade() default {};
			* 级联操作策略, 枚举
					ALL				关联所有操作
					PERSIST			级联保存
					MERGE			级联更新
					REMOVE			级联删除
					REFRESH			级联刷新
					DETACH			级联分离
				* 默认为空, 不会产生关联操作
		
		FetchType fetch() default LAZY;
			* 关联数据的获取方式, 枚举
				LAZY	延迟加载
				EAGER	立即加载
		
		String mappedBy() default "";
			* 关联关系被谁维护, 一般不用填写该字段, 表示当前实体在维护
			* 如果要放弃维护权, 则该属性指的是, 另一方的实体里面 @JoinColumn 或者 @JoinTable 的属性字段名称, 而不是数据库字段, 也不是实体对象的名称

			* 只有关系维护方才能操作两者关系, 被维护方即使设置了维护方属性进行存储也不会更新外键关联(避免级联保存的时候, 多一个update操作)
			* 当使用了该注解的时候, 该字段不能添加 @JoinColumn 或者 @JoinTable (不能同时使用)

		boolean orphanRemoval() default false;
			* 是否级联删除, 跟 cascade = CascadeType.REMOVE 效果一样
			* 只要是配置了两种类型中的其中一个, 都会被级联删除
		
		* 一对多关系

	@ManyToOne
		Class targetEntity() default void.class;
		CascadeType[] cascade() default {};
		FetchType fetch() default EAGER;
		boolean optional() default true;
			* 是否可以为null
		
		* 多对一关系, 属性同上


-----------------------------
一对多的保存
-----------------------------
	# 先保存主, 再保存从
		// 创建用户
		User user = new User();
		user.setName("KevinBlandy");
		
		this.userRepository.save(user);
		
		// 创建地址
		Address address = new Address();
		address.setUser(user);  // user 必须已经是被序列化了的, 并且有id属性
		address.setName("重庆");
		
		this.addressRepository.save(address);

		* 直接执行2条insert语句
	
	# 主级联保存从
		* 主的一方设置了级联保存: cascade = CascadeType.PERSIST 
			@OneToMany(targetEntity = Address.class, cascade = CascadeType.PERSIST)
			@JoinColumn(name = "user_id", referencedColumnName = "id")
		
		// 创建用户
		User user = new User();
		user.setName("Litch");
		
		// 创建地址
		Address address = new Address();
		address.setName("四川");
		
		address.setUser(user);  			// 设置关系
		user.getAddresses().add(address);  	// 设置关系
		
		this.userRepository.save(user);		// 创建用户的同时, 会保存地址


		* 先insert了2条语句, 然后update从表记录
	
	# 从级联保存主
		* 从的一方设置了级联保存: cascade = CascadeType.PERSIST 
			@ManyToOne(targetEntity = User.class, cascade = CascadeType.PERSIST)
			@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
		
		// 创建地址
		Address address = new Address();
		address.setName("战争学院");
		
		// 创建用户
		User user = new User();
		user.setName("德玛西亚");
		
		
		address.setUser(user);
		user.getAddresses().add(address);
		
		this.addressRepository.save(address); // 创建地址的同时, 会保存用户

		* 先insert了2条语句, 然后update从表记录
	
	# 解决级联保存的时候多出的一条 update 语句, 主表放弃维护权
		@OneToMany(mappedBy = "user", targetEntity = Address.class)
		// @JoinColumn(name = "user_id", referencedColumnName = "id")
		private Set<Address> addresses = new HashSet<>();

-----------------------------
一对多的删除
-----------------------------
	# 删除主表
		* 如果从表有数据, 它会把外键设置为null, 然后再删除主表
		* 如果从表有非空约束, 那么会抛出异常

		* 如果配置了放弃维护关联表的权利, 则不能删除(与外键字段是否允许为null, 没有关系)
		* 因为在删除的时候, 它不会去更新从表的外键字段

		* 如果从表没有关联数据, 随便删除

	# 删除从表
		* 随便删除
	
	
	# 删除主表, 级联删除从表
		* 主对象设置级联删除: cascade = {CascadeType.REMOVE}
			@OneToMany(mappedBy = "user", targetEntity = Address.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
		
		this.userRepository.deleteById(8); // 直接根据id删除主表

		* 先执行1条查询, 根据主表id, 查询从表的记录
		* 再执行1条删除, 根据主表id删除主表记录
		* 再执行n条删除, 根据从表id删除从表记录

	# 删除从表, 级联删除主表
		* 从对象设置级联删除: cascade = {CascadeType.REMOVE}
			@ManyToOne(targetEntity = User.class, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
		
		this.addressRepository.deleteById(7);

		* 先执行1条查询, 主表 inner join 从表, 根据从表id检索记录
		* 再执行1条查询, 根据主表id, 检索从表记录
		* 再执行n条删除, 根据从表id删除从表记录
		* 再执行1条删除, 根据主表id删除主表记录
		

