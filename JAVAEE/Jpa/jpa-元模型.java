--------------------------
元模型
--------------------------
	# 用于描述实体类的属性
		* 必须也要被 EntityManager 管理, 跟 Entity 放在同一个包下
		* 跟 Entity 类同名, 最后添加_
		* 规范元模型类的属性全部是 static 和 public 的
	
	# Demo
		* 实体类
			@Entity
			@Table
			public class Employee {  
				private int id;   
				private String name;
				private int age;
				@OneToMany
				private List<Address> addresses;
			}
	
		* 实体类的元模型
			import javax.annotation.Generated;
			import javax.persistence.metamodel.SingularAttribute;
			import javax.persistence.metamodel.ListAttribute;
			import javax.persistence.metamodel.StaticMetamodel;

			@StaticMetamodel(Employee.class)
			public abstract class Employee_ {     
				public static volatile SingularAttribute<Employee, Integer> id;   
				public static volatile SingularAttribute<Employee, Integer> age;   
				public static volatile SingularAttribute<Employee, String> name;    
				public static volatile ListAttribute<Employee, Address> addresses;
			}
	
		* 不能对 @MappedSuperclass 继承的属性设置静态的描述属性


	# 其他的属性描述接口
		SingularAttribute
			* 普通单属性
		CollectionAttribute
		MapAttribute
		SetAttribute
		ListAttribute
			* 一对多/多对多属性

--------------------------
自动生成元模型
--------------------------
	# 自动生成元模型的maven插件
		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-jpamodelgen</artifactId>
			<version>5.4.10.Final</version>
			<scope>compile</scope>
		</dependency>
		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-validator</artifactId>
			<version>6.1.0.Final</version>
		</dependency>
		<plugin>
			<artifactId>maven-compiler-plugin</artifactId>
			<configuration>
				<source>1.8</source>
				<target>1.8</target>
				<compilerArguments>
					<processor>org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor</processor>
				</compilerArguments>
			</configuration>
		</plugin>


--------------------------
常用
--------------------------

	// 基类
	public class _Base {
	
		@Id
		@Column(name = "id", columnDefinition = "INT UNSIGNED COMMENT 'ID'")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		@Column(name = "nickname", columnDefinition = "VARCHAR(20) COMMENT '昵称'")
		private String nickname;
		
		@Column(name = "enabled", columnDefinition = "TINYINT UNSIGNED COMMENT '状态。0：禁用，1正常'", nullable = false)
		private Boolean enabled;
		
		@Column(name = "create_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'", nullable = false)
		private LocalDateTime createAt;
		
		@Column(name = "update_at", columnDefinition = "TIMESTAMP NULL DEFAULT NULL COMMENT '修改时间'")
		private LocalDateTime updateAt;
		
		@Column(name = "`describe`", columnDefinition = "VARCHAR(200) COMMENT '说明'")
		private String describe;
		
		@Column(columnDefinition = "INT COMMENT '排序权重'")
		private Integer weight;
	}

	/**
	 *
	 * 商户账户
	 *
	 */
	@Entity
	@Table(name = "merchant_account", uniqueConstraints = {
	   //  @UniqueConstraint(columnNames = {"phone_number", "deleted"}, name = "phone_number_deleted")
	}, indexes = {
		@Index(columnList = "phone_number", name = "phone_number"),
		@Index(columnList = "parent_id", name = "parent_id"),
		@Index(columnList = "merchant_id", name = "merchant_id")
	})
	@org.hibernate.annotations.Table(appliesTo = "merchant_account", comment = "商户账户")
	public class MerchantAccount implements Serializable {

		private static final long serialVersionUID = 2170584635680290003L;

		@Id
		@Column(name = "id", columnDefinition = "INT UNSIGNED COMMENT 'ID'")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;

		// 商户ID
		@Column(name = "merchant_id", columnDefinition = "INT UNSIGNED COMMENT '商户ID'", nullable = false)
		private Integer merchantId;

		// 父级账户ID
		@Column(name = "parent_id", columnDefinition = "INT UNSIGNED COMMENT '创建此账户的父级账户ID。0：表示顶级账户'", nullable = false)
		private Integer parentId;
		
		// 员工姓名
		@Column(name = "`name`", columnDefinition = "VARCHAR(50) COMMENT '员工姓名'", nullable = false)
		private String name;
		
		// 职位
		@Column(name = "`position`", columnDefinition = "VARCHAR(50) COMMENT '职位'", nullable = false)
		private String position;
		
		// 电子邮箱地址
		@Column(name = "email", columnDefinition = "VARCHAR(50) COMMENT '电子邮箱地址'", nullable = false)
		private String email;

		// 手机号码
		@Column(name = "`phone_number`", columnDefinition = "VARCHAR(11) COMMENT '手机号码，也就是登陆账户'", nullable = false)
		private String phoneNumber;

		// 登陆密码
		@Column(name = "`password`", columnDefinition = "VARCHAR(200) COMMENT '登陆密码。如果为空，表示使用临时密码登陆。'")
		@GsonIgnore
		private String password;

		// 是否启用
		@Column(name = "enabled", columnDefinition = "TINYINT UNSIGNED COMMENT '状态。0：禁用，1正常'", nullable = false)
		private Boolean enabled;

		// 逻辑删除
		@Column(name = "deleted", columnDefinition = "INT UNSIGNED COMMENT '逻辑删除字段。0：未删除，1：已删除'", nullable = false)
		private Boolean deleted;

		// 创建时间
		@Column(name = "create_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'", nullable = false)
		private LocalDateTime createAt;

		// 修改时间
		@Column(name = "update_at", columnDefinition = "TIMESTAMP NULL DEFAULT NULL COMMENT '修改时间'")
		private LocalDateTime updateAt;

		// 说明信息
		@Column(name = "`remark`", columnDefinition = "VARCHAR(200) COMMENT '备注信息'")
		private String remark;

	}

	/**
	 *
	 * 商户账户，部门授权
	 *
	 */
	@Entity
	@Table(name = "merchant_account_department", indexes = {
		@Index(columnList = "merchant_department_id", name = "merchant_department_id")
	})
	@IdClass(MerchantAccountDepartment.Id.class)
	@org.hibernate.annotations.Table(appliesTo = "merchant_account_department", comment = "商户账户 & 部门授权")
	public class MerchantAccountDepartment implements Serializable {

		private static final long serialVersionUID = 618144093186469124L;

		@javax.persistence.Id
		@Column(name = "merchant_account_id", columnDefinition = "INT UNSIGNED COMMENT '商户账户ID'")
		private Integer merchantAccountId;

		@javax.persistence.Id
		@Column(name = "merchant_department_id", columnDefinition = "INT UNSIGNED COMMENT '商户部门ID'")
		private Integer merchantDepartmentId;

		// 创建时间
		@Column(name = "create_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'", nullable = false)
		private LocalDateTime createAt;

		public Integer getMerchantAccountId() {
			return merchantAccountId;
		}

		public void setMerchantAccountId(Integer merchantAccountId) {
			this.merchantAccountId = merchantAccountId;
		}

		public Integer getMerchantDepartmentId() {
			return merchantDepartmentId;
		}

		public void setMerchantDepartmentId(Integer merchantDepartmentId) {
			this.merchantDepartmentId = merchantDepartmentId;
		}

		public LocalDateTime getCreateAt() {
			return createAt;
		}

		public void setCreateAt(LocalDateTime createAt) {
			this.createAt = createAt;
		}

		public static class Id implements Serializable{

			private static final long serialVersionUID = 7728487405706211954L;
			private Integer merchantAccountId;
			private Integer merchantDepartmentId;

			@Override
			public boolean equals(Object o) {
				if (this == o) return true;
				if (!(o instanceof Id)) return false;
				Id id = (Id) o;
				return Objects.equals(getMerchantAccountId(), id.getMerchantAccountId()) && Objects.equals(getMerchantDepartmentId(), id.getMerchantDepartmentId());
			}

			@Override
			public int hashCode() {
				return Objects.hash(getMerchantAccountId(), getMerchantDepartmentId());
			}

			public Integer getMerchantAccountId() {
				return merchantAccountId;
			}

			public void setMerchantAccountId(Integer merchantAccountId) {
				this.merchantAccountId = merchantAccountId;
			}

			public Integer getMerchantDepartmentId() {
				return merchantDepartmentId;
			}

			public void setMerchantDepartmentId(Integer merchantDepartmentId) {
				this.merchantDepartmentId = merchantDepartmentId;
			}
		}
	}
