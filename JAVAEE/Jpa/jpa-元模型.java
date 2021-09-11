--------------------------
Ԫģ��
--------------------------
	# ��������ʵ���������
		* ����ҲҪ�� EntityManager ����, �� Entity ����ͬһ������
		* �� Entity ��ͬ��, ������_
		* �淶Ԫģ���������ȫ���� static �� public ��
	
	# Demo
		* ʵ����
			@Entity
			@Table
			public class Employee {  
				private int id;   
				private String name;
				private int age;
				@OneToMany
				private List<Address> addresses;
			}
	
		* ʵ�����Ԫģ��
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
	
		* ���ܶ� @MappedSuperclass �̳е��������þ�̬����������


	# ���������������ӿ�
		SingularAttribute
			* ��ͨ������
		CollectionAttribute
		MapAttribute
		SetAttribute
		ListAttribute
			* һ�Զ�/��Զ�����

--------------------------
�Զ�����Ԫģ��
--------------------------
	# �Զ�����Ԫģ�͵�maven���
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
����
--------------------------

	// ����
	public class _Base {
	
		@Id
		@Column(name = "id", columnDefinition = "INT UNSIGNED COMMENT 'ID'")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		@Column(name = "nickname", columnDefinition = "VARCHAR(20) COMMENT '�ǳ�'")
		private String nickname;
		
		@Column(name = "enabled", columnDefinition = "TINYINT UNSIGNED COMMENT '״̬��0�����ã�1����'", nullable = false)
		private Boolean enabled;
		
		@Column(name = "create_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��'", nullable = false)
		private LocalDateTime createAt;
		
		@Column(name = "update_at", columnDefinition = "TIMESTAMP NULL DEFAULT NULL COMMENT '�޸�ʱ��'")
		private LocalDateTime updateAt;
		
		@Column(name = "`describe`", columnDefinition = "VARCHAR(200) COMMENT '˵��'")
		private String describe;
		
		@Column(columnDefinition = "INT COMMENT '����Ȩ��'")
		private Integer weight;
	}

	/**
	 *
	 * �̻��˻�
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
	@org.hibernate.annotations.Table(appliesTo = "merchant_account", comment = "�̻��˻�")
	public class MerchantAccount implements Serializable {

		private static final long serialVersionUID = 2170584635680290003L;

		@Id
		@Column(name = "id", columnDefinition = "INT UNSIGNED COMMENT 'ID'")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;

		// �̻�ID
		@Column(name = "merchant_id", columnDefinition = "INT UNSIGNED COMMENT '�̻�ID'", nullable = false)
		private Integer merchantId;

		// �����˻�ID
		@Column(name = "parent_id", columnDefinition = "INT UNSIGNED COMMENT '�������˻��ĸ����˻�ID��0����ʾ�����˻�'", nullable = false)
		private Integer parentId;
		
		// Ա������
		@Column(name = "`name`", columnDefinition = "VARCHAR(50) COMMENT 'Ա������'", nullable = false)
		private String name;
		
		// ְλ
		@Column(name = "`position`", columnDefinition = "VARCHAR(50) COMMENT 'ְλ'", nullable = false)
		private String position;
		
		// ���������ַ
		@Column(name = "email", columnDefinition = "VARCHAR(50) COMMENT '���������ַ'", nullable = false)
		private String email;

		// �ֻ�����
		@Column(name = "`phone_number`", columnDefinition = "VARCHAR(11) COMMENT '�ֻ����룬Ҳ���ǵ�½�˻�'", nullable = false)
		private String phoneNumber;

		// ��½����
		@Column(name = "`password`", columnDefinition = "VARCHAR(200) COMMENT '��½���롣���Ϊ�գ���ʾʹ����ʱ�����½��'")
		@GsonIgnore
		private String password;

		// �Ƿ�����
		@Column(name = "enabled", columnDefinition = "TINYINT UNSIGNED COMMENT '״̬��0�����ã�1����'", nullable = false)
		private Boolean enabled;

		// �߼�ɾ��
		@Column(name = "deleted", columnDefinition = "INT UNSIGNED COMMENT '�߼�ɾ���ֶΡ�0��δɾ����1����ɾ��'", nullable = false)
		private Boolean deleted;

		// ����ʱ��
		@Column(name = "create_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��'", nullable = false)
		private LocalDateTime createAt;

		// �޸�ʱ��
		@Column(name = "update_at", columnDefinition = "TIMESTAMP NULL DEFAULT NULL COMMENT '�޸�ʱ��'")
		private LocalDateTime updateAt;

		// ˵����Ϣ
		@Column(name = "`remark`", columnDefinition = "VARCHAR(200) COMMENT '��ע��Ϣ'")
		private String remark;

	}

	/**
	 *
	 * �̻��˻���������Ȩ
	 *
	 */
	@Entity
	@Table(name = "merchant_account_department", indexes = {
		@Index(columnList = "merchant_department_id", name = "merchant_department_id")
	})
	@IdClass(MerchantAccountDepartment.Id.class)
	@org.hibernate.annotations.Table(appliesTo = "merchant_account_department", comment = "�̻��˻� & ������Ȩ")
	public class MerchantAccountDepartment implements Serializable {

		private static final long serialVersionUID = 618144093186469124L;

		@javax.persistence.Id
		@Column(name = "merchant_account_id", columnDefinition = "INT UNSIGNED COMMENT '�̻��˻�ID'")
		private Integer merchantAccountId;

		@javax.persistence.Id
		@Column(name = "merchant_department_id", columnDefinition = "INT UNSIGNED COMMENT '�̻�����ID'")
		private Integer merchantDepartmentId;

		// ����ʱ��
		@Column(name = "create_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��'", nullable = false)
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
