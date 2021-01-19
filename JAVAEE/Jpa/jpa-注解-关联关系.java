---------------------
关联关系
---------------------
	@JoinColumn
		String name() default "";
			* 外键列的名称
		String referencedColumnName() default "";
			* 当前实体的字段, 非必填, 默认当表的id
		boolean unique() default false;
			* 外键是否唯一
		boolean nullable() default true;
			* 是否可以为null
		boolean insertable() default true;
			* 是否跟随一起新增
		boolean updatable() default true;
			* 是否跟随一起修改
		String columnDefinition() default "";
			* 列约束
		String table() default "";
		ForeignKey foreignKey() default @ForeignKey(PROVIDER_DEFAULT);
			* 外键的配置, 

		* 它要配合 @OneToOne,@OneToMany,@ManyToOne,@ManyToMany 使用, 不然没意义

	@JoinColumns
		JoinColumn[] value();
		ForeignKey foreignKey() default @ForeignKey(PROVIDER_DEFAULT);

		* 用于同时定义多个 @JoinColumn
	
	@JoinTable
		String name() default "";
			* 中间表的名称

		String catalog() default "";
		String schema() default "";

		JoinColumn[] joinColumns() default {};
			* 当前对象在中间表中的外键信息
				@JoinColumn
					|-name					中间表中, 当前对象的id字段名称
					|-referencedColumnName	当前对象的id字段名称


		JoinColumn[] inverseJoinColumns() default {};
			* 对方对象在中间表中的外键信息
				@JoinColumn
					|-name					中间表中, 对方对象的id字段名称
					|-referencedColumnName	对方对象的id字段名称

		ForeignKey foreignKey() default @ForeignKey(PROVIDER_DEFAULT);
			@ForeignKey
				|-String name()
				|-ConstraintMode value() default CONSTRAINT;
					CONSTRAINT
					NO_CONSTRAINT
					PROVIDER_DEFAULT
				|-String foreignKeyDefinition() default "";

		ForeignKey inverseForeignKey() default @ForeignKey(PROVIDER_DEFAULT);

		UniqueConstraint[] uniqueConstraints() default {};
			@UniqueConstraint
				|-String name() default "";
				|-String[] columnNames();

		Index[] indexes() default {};
			@Index
				|-String name()
				|-String columnList();
				|-boolean unique() default false;
		
	@OrderBy
		String value() default "";
			
		* 一般和 @OneToMany 一起使用
			@OneToMany
			@OrderBy(value = "role_name DESC")
		

	
	@NamedEntityGraph
		String name() default "";
		NamedAttributeNode[] attributeNodes() default {};	

			@NamedAttributeNode
				|-String value();	
					* 关联对象的属性名称
				|-String subgraph() default "";
				|-String keySubgraph() default "";

			* 指定要join检索的属性

		boolean includeAllAttributes() default false;

		NamedSubgraph[] subgraphs() default {};
			@NamedSubgraph
				|-String name();
				|-Class type() default void.class;
				|-NamedAttributeNode[] attributeNodes();
		
		NamedSubgraph[] subclassSubgraphs() default {};
			@NamedSubgraph
					|-String name();
					|-Class type() default void.class;
					|-NamedAttributeNode[] attributeNodes();
		

		* 标识在实体类上
		* 在关联检索的时候, FetchType怎么配置LAZY或者EAGER。SQL真正执行的时候是由一条主表查询和N条子表查询组成的
		* 这种查询效率一般比较低下, 比如子对象有N个就会执行N+1条SQL

		* 可以通过 @NamedEntityGraphs 配置多个
			NamedEntityGraph[] value();
		
	@ForeignKey
		String name() default "";
		ConstraintMode value() default CONSTRAINT;
			CONSTRAINT
			NO_CONSTRAINT
			PROVIDER_DEFAULT
		String foreignKeyDefinition() default "";
		 
		* 主外键的关系设置
		* 如果需要屏蔽实体之间的主外键关系, 可以设置它的 ConstraintMode 值为 NO_CONSTRAINT
			
			@JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))

			@JoinTable(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT), inverseForeignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
	
	@ElementCollection
		Class targetClass() default void.class;
		FetchType fetch() default LAZY;

	@CollectionTable
		String name() default "";
		String catalog() default "";
		String schema() default "";
		JoinColumn[] joinColumns() default {};
		ForeignKey foreignKey() default @ForeignKey(PROVIDER_DEFAULT);
		UniqueConstraint[] uniqueConstraints() default {};
		Index[] indexes() default {};

	
	@PrimaryKeyJoinColumn
		String name() default "";
		String referencedColumnName() default "";
		String columnDefinition() default "";
		ForeignKey foreignKey() default @ForeignKey(PROVIDER_DEFAULT);

		* 在关联关系中, 一对一关系中, 使用对方的id, 作为自己的主键