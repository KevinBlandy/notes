-----------------------------
多对多的关系
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
			
			@ManyToMany(targetEntity = Role.class)
			@JoinTable(name = "user_role", joinColumns = {
				// 当前对象在中间表中关联字段的名称, 当前对象的关联id属性
				@JoinColumn(name = "user_id", referencedColumnName = "id")
			}, inverseJoinColumns = {
				// 对方对象在中间表中关联字段的名称, 对方对象的关联id属性
				@JoinColumn(name = "role_id", referencedColumnName = "id")
			})
			private Set<Role> roles = new HashSet<>();
		}

	# 角色
		@Entity
		@Table(name = "role")
		public class Role {
			@Id
			@Column(name = "id", columnDefinition = "INT(11) UNSIGNED COMMENT '角色id'")
			@GeneratedValue(strategy = GenerationType.IDENTITY)
			private Integer id;
			
			@Column(columnDefinition = "varchar(20) COMMENT '角色名称'")
			private String name;
			
			@ManyToMany(targetEntity = User.class)
			@JoinTable(name = "user_role", joinColumns = {
				// 当前对象在中间表中关联字段的名称, 当前对象的关联id属性
				@JoinColumn(name = "role_id", referencedColumnName = "id")
			}, inverseJoinColumns = {
				// 对方对象在中间表中关联字段的名称, 对方对象的关联id属性
				@JoinColumn(name = "user_id", referencedColumnName = "id")
			})
			private Set<User> users = new HashSet<>();
		}

	# 中间表结构
		CREATE TABLE `user_role` (
		  `user_id` int(11) unsigned NOT NULL COMMENT '用户id',
		  `role_id` int(11) unsigned NOT NULL COMMENT '角色id',
		  PRIMARY KEY (`role_id`,`user_id`),
		  KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`),
		  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
		  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
		) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
	
	# 可以只单向的配置一方
		// 用户不配置

		// 角色配置
		@ManyToMany(targetEntity = User.class, cascade = CascadeType.PERSIST)
		@JoinTable(name = "user_role", joinColumns = {
			@JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
		}, inverseJoinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
		})
		private Set<User> users = new HashSet<>();

		
		* 此时创建的表, 还是会创建FOREIGN KEY外键

-----------------------------
多对多的关系注解
-----------------------------
	@ManyToMany
		Class targetEntity() default void.class;
		CascadeType[] cascade() default {};
		FetchType fetch() default LAZY;
		String mappedBy() default "";
			* 关联关系被谁维护, 一般不用填写该字段, 表示当前实体在维护
			* 如果要放弃维护权, 则该属性指的是, 另一方的实体里面 @JoinColumn 或者 @JoinTable 的属性字段名称, 而不是数据库字段, 也不是实体对象的名称

			* 只有关系维护方才能操作两者关系, 被维护方即使设置了维护方属性进行存储也不会更新外键关联(避免级联保存的时候, 多一个update操作)
			* 当使用了该注解的时候, 该字段不能添加 @JoinColumn 或者 @JoinTable (不能同时使用)
		

-----------------------------
多对多的保存
-----------------------------
	# 具有维护权的一方, 需要设置级联保存: cascade = CascadeType.PERSIST
		User user = new User();
		user.setName("Vin");
		
		Role role = new Role();
		role.setName("ADC");
		
		// 设置对象关系
		role.getUsers().add(user);
		
		this.roleRepository.save(role);

		* 会执行3条insert, 角色, 用户, 中间表

		* 如果双方同时维护了对象关系, 必须有一方放弃维护权:@ManyToMany(targetEntity = Role.class, mappedBy = "users")
			user.getRoles().add(role);
			role.getUsers().add(user);

		* 如果都有维护权, 那么在执行级联保存的时候会抛出key重复异常, 因为双方都尝试插入相同id的记录

		

-----------------------------
多对多的删除
-----------------------------
	# 删除记录的时候, 肯定会去删除中间表的记录
	# 是否要删除另一方的记录, 需要看删除方是否配置了级联删除: cascade = CascadeType.REMOVE

		@ManyToMany(targetEntity = Role.class, cascade = CascadeType.REMOVE)
		@JoinTable(name = "user_role", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
		}, inverseJoinColumns = {
			@JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
		})
		private Set<Role> roles = new HashSet<>();

		this.userRepository.deleteById(6);
	
		
