---------------------
annotation
---------------------
	# 注解所在包: javax.persistence

---------------------
一般注解
---------------------
	@Entity
		* 表示类, 被EntityManager管理
		String name() default "";
			* 实体的名称, 可以用于JPQL检索
			* 默认是当前类名

	@Table
		String name() default "";
			* 数据表的名称

		String catalog() default "";

		String schema() default "";

		UniqueConstraint[] uniqueConstraints() default {};

		Index[] indexes() default {};
			* 表索引的设置
			* Index 注解的属性
				String name				索引名称
				String columnList		索引的列
				boolean unique			是否唯一约束

	@Column
		String name() default "";
			* 字段的名称, 如果和属性名称一样, 可以省略

		boolean unique() default false;

		boolean nullable() default true;
			* 是否可以为null

		boolean insertable() default true;
			* 执行插入的时候, 是否插入该字段

		boolean updatable() default true;
			* 执行更新的时候, 是否更新该字段

		String columnDefinition() default "";
			* 设置列类型以及约束以及注释, 例如: 
				columnDefinition = "int(20) unsigned COMMENT 'id'"
				columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'"

		String table() default "";

		int length() default 255;
			* 字段约束的长度

		int precision() default 0;

		int scale() default 0;
	
	@MapKeyColumn
		String name() default "";
		boolean unique() default false;
		boolean nullable() default false;
		boolean insertable() default true;
		boolean updatable() default true;
		String columnDefinition() default "";
		String table() default "";
		int length() default 255;
		int precision() default 0;
		int scale() default 0;

		* 映射map属性

	@TemporalType
		TemporalType value();
			* 指定日期字段的日期类型
			* 枚举值
				DATE
				TIME
				TIMESTAMP

	@Id
		* 标识id字段, 每个实体类都必须至少有一个 @id 字段
	
	@GeneratedValue
		* 标识ID字段,并且指定其生成策略
		* strategy ,指定生成策略
			GenerationType.TABLE		使用一个特定的数据库表格来保存主键。 
			GenerationType.SEQUENCE		根据底层数据库的序列来生成主键,条件是数据库支持序列
			GenerationType.IDENTITY		主键由数据库自动生成(主要是自动增长型）)
			GenerationType.AUTO			主键由程序控制

	@Enumerated
		EnumType value() default ORDINAL;
			* 枚举
				ORDINAL		使用 ordinal 映射为 int
				STRING		使用 枚举的 name() 映射为 varchar

		* 定义枚举字段和数据库的映射方式
	
	@Lob
		* 映射字段为数据库所支持的大对象数据类型
		* 它支持如下的类型
			Clob		长字符串类型
			Blob		长字节类型

		* Clob 和 Blob 占用内存都比较大, 可以考虑配合: @Basic(fetch = FetchType.LAZY) 延迟加载
		


	@Basic
		* 标识字段, 是映射到数据库的(不标识默认就是)

		FetchType fetch() default EAGER;
			* 该字段的抓取策略
				LAZY		延迟加载
				EAGER		立即加载
		boolean optional() default true;
			* 该字段是否可以为null

	@Transient
		* 表示该字段不是数据表的映射字段, 在映射的时候会忽略它

	@Version
		* 标识版本号字段
	
	@NamedStoredProcedureQuery
		String name();
			* JPA中定义的存储过程名称
		String procedureName();
			* 数据库里面的存储过程的名称
		StoredProcedureParameter[] parameters() default {};
			* 存储过程的IN/OUT参数
		Class[] resultClasses() default {}; 
		String[] resultSetMappings() default {};
		QueryHint[] hints() default {};

		* 存储过程的定义, 它必须定义在 Entity 类上

	@NamedQuery
		String name();
			* query的名称:实体.方法
		String query();
			* 具体的JPQL语句
		LockModeType lockMode() default NONE;
		QueryHint[] hints() default {};

		* 预定义查询, 定义在实体类上
		* 可以通过	@NamedQueries 注解同时定义多个
			NamedQuery [] value ();

	@NamedNativeQuery
		String name();
		String query();
		QueryHint[] hints() default {};
		Class resultClass() default void.class; 
		String resultSetMapping() default "";
			* 使用指定的 @@SqlResultSetMapping 封装结果集
			* 指定@SqlResultSetMapping 的name属性

		* 根据 @NamedQuery 一样, 只是使用的是本地SQL
	
	@QueryHint
		 String name(); 
		 String value();

		 * query hint 检索
	


	
		
