------------------------------------
一对一关系下懒加载的问题
------------------------------------
	# 用户
		@Entity
		@Table(name = "user")
		public class User implements Serializable {
			
			private static final long serialVersionUID = 8175166175439387541L;
			
			@Id
			@Column(columnDefinition = "INT(11) UNSIGNED COMMENT '用户id'")
			@GeneratedValue(strategy = GenerationType.IDENTITY)
			private Integer id;
			
			@Column(columnDefinition = "varchar(20) COMMENT '昵称'")
			private String name;
			
			// 设置
			@OneToOne(fetch = FetchType.LAZY)
			@JoinColumn(name = "id", referencedColumnName = "user_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
			private UserSeting userSeting;
		}

	# 用户设置
		@Entity
		@Table(name = "user_seting")
		public class UserSeting implements Serializable {
			
			private static final long serialVersionUID = -1007318207008996614L;

			@Id
			@Column(columnDefinition = "INT(11) unsigned COMMENT '设置id'")
			@GeneratedValue(strategy = GenerationType.IDENTITY)
			private Integer id;
			
			@Column(columnDefinition = "TINYINT(1) unsigned COMMENT '是否接受通知'")
			private Boolean notify;
			
			@OneToOne(fetch = FetchType.LAZY)
			@JoinColumn(name = "user_id", referencedColumnName = "id", unique = true, nullable = false, 
					foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
			private User user;
		}
	
	# 表关系
		CREATE TABLE `user` (
		  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id',
		  `gender` tinyint(1) unsigned NOT NULL COMMENT '性别。0：女，1：男',
		  `name` varchar(20) DEFAULT NULL COMMENT '昵称',
		  `version` int(11) unsigned NOT NULL COMMENT '版本号',
		  PRIMARY KEY (`id`)
		) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

		CREATE TABLE `user_seting` (
		  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '设置id',
		  `notify` tinyint(1) unsigned DEFAULT NULL COMMENT '是否接受通知',
		  `user_id` int(11) unsigned NOT NULL COMMENT '用户id',
		  PRIMARY KEY (`id`),
		  UNIQUE KEY `UK_rdchrax5rp1m1y4kpax285krw` (`user_id`)
		) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
	
	# 懒加载失效
		* 检索 UserSeting , user 可以懒加载

		* 检索 User, UserSeting 懒加载失效
		* 依然会立即发出一条对 UserSeting 的检索, 而且是根据id检索, 不是根据关联id
			select
				userseting0_.id as id1_4_0_,
				userseting0_.notify as notify2_4_0_,
				userseting0_.user_id as user_id3_4_0_ 
			from
				user_seting userseting0_ 
			where
				userseting0_.id=?
	
	# 原因
		* 检索 UserSeting 的时候, 因为 UserSeting 有个 user_id 外键, 清楚的知道 UserSeting 有一个 User, 可以懒加载
		* 检索 User 的时候, 因为没有外键关联, 不知道拥有该 User 的 UserSeting 是谁, 不确定 UserSeting 中是否有记录


------------------------------------
解决方案
------------------------------------
	# 在 User, 添加 UserSeting 的关联外键
		* 缺点就是冗余设置了关系
	
	# 在 UserSeting 中, 使用 User 的id, 作为自己的id, 不使用额外的 userId 字段

