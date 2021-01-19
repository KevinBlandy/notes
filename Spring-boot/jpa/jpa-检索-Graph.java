---------------
EntityGraph
---------------
	@NamedEntityGraph
		String name() default "";
		NamedAttributeNode[] attributeNodes() default {};	

			@NamedAttributeNode
				|-String value();	
					* 属性名称
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
		
	@EntityGraph
		String value() default "";
			* 指定 Entity 实体上指定name的 @NamedEntityGraph
		
		EntityGraphType type() default EntityGraphType.FETCH;
			* 加载方式, 枚举

			LOAD	
			FETCH	

		String[] attributePaths() default {};
			* 定义临时实体提供的内容 attributePaths 将转换为, EntityGraph
			* 无需显式添加 @NamedEntityGraph 到实体, 也不需要标识 value 属性


		* 配合 @NamedEntityGraph 完成join检索, 避免在关联关系中进行n+1次检索的问题
		* 在实体类上标识 @NamedEntityGraph 指定要join检索的字段
	

---------------
demo
---------------
	# 同过实体标识 @NamedEntityGraph
		// 实体标识
		@NamedEntityGraph(name = "User.loadAll", attributeNodes = {
			@NamedAttributeNode("addresses"),			// 指定要join检索的 User 实体中的关联属性
			@NamedAttributeNode("roles")
		})
		@Entity
		@Table(name = "user")
		public class User


		// Repository  检索方法标识
		@EntityGraph(value = "User.loadAll",type = EntityGraph.EntityGraphType.FETCH)
		@Override
		List<User> findAll();

		// 生成的SQL
		select
			user0_.id as id1_2_0_,
			addresses1_.id as id1_0_1_,
			role3_.id as id1_1_2_,
			user0_.name as name2_2_0_,
			addresses1_.name as name2_0_1_,
			addresses1_.user_id as user_id3_0_1_,
			addresses1_.user_id as user_id3_0_0__,
			addresses1_.id as id1_0_0__,
			role3_.name as name2_1_2_,
			roles2_.user_id as user_id1_3_1__,
			roles2_.role_id as role_id2_3_1__ 
		from
			user user0_ 
		left outer join
			address addresses1_ 
				on user0_.id=addresses1_.user_id 
		left outer join
			user_role roles2_ 
				on user0_.id=roles2_.user_id 
		left outer join
			role role3_ 
				on roles2_.role_id=role3_.id


	# 直接标识 @EntityGraph
		@EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"roles"})
		@Query("FROM User")
		List<User> findTest();

	   select
			user0_.id as id1_2_0_,
			role2_.id as id1_1_1_,
			user0_.gender as gender2_2_0_,
			user0_.name as name3_2_0_,
			user0_.version as version4_2_0_,
			role2_.name as name2_1_1_,
			roles1_.user_id as user_id1_3_0__,
			roles1_.role_id as role_id2_3_0__ 
		from
			user user0_ 
		left outer join
			user_role roles1_ 
				on user0_.id=roles1_.user_id 
		left outer join
			role role2_ 
				on roles1_.role_id=role2_.id