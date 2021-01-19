------------------
criteria
------------------
	# 核心的接口
		Predicate
		Root	
			* 查询的根对象, 从根对象中获取到要查询的属性
			* 通过 AbstractQuery <T> 抽象类的方法获取
				<X> Root<X> from(Class<X> entityClass);
				<X> Root<X> from(EntityType<X> entity);
		
		CriteriaBuilder
			* 查询的构造器, 封装了很多的查询方式, 条件
			* 从 EntityManager 的方法获取
				CriteriaBuilder getCriteriaBuilder();

		CriteriaQuery
			* 顶层查询对象
				从 CriteriaBuilder 的方法获取
			
			* 包含了 select, from, where, group by, order by 等操作


---------------------
常用
---------------------
	# 检索所有
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		List<User> users = entityManager.createQuery(criteriaQuery).getResultList();

		select
			user0_.id as id1_0_,
			user0_.create_date as create_d2_0_,
			user0_.version as version3_0_,
			user0_.name as name4_0_ 
		from
			user user0_

	# 多个条件关系
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		// 设置条件之间的一组关系, 关系是or
		Predicate p1 = criteriaBuilder.or(criteriaBuilder.lt(root.get(User_.id), 1), criteriaBuilder.like(root.get(User_.name), "K"));
		// 设置条件之前的一组关系, 关系是and
		Predicate p2 = criteriaBuilder.and(p1, criteriaBuilder.lt(root.get(User_.id), 2));
		List<User> users = entityManager.createQuery(criteriaQuery.where(p2)).getResultList();

		select
			user0_.id as id1_0_,
			user0_.create_date as create_d2_0_,
			user0_.version as version3_0_,
			user0_.name as name4_0_ 
		from
			user user0_ 
		where
			(
				user0_.id<1 
					or 
				user0_.name like ?
			) 
		and 
			user0_.id<2
	
	
	# COUNT 统计查询
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<User> root = criteriaQuery.from(User.class);
		Expression<Long> expression = criteriaBuilder.count(root);
		criteriaQuery.select(expression);
		Long count = entityManager.createQuery(criteriaQuery).getSingleResult();
	
	# 检索单个字段
		CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.select(root.get(User_.name));  // 如果多次执行 select, 只有最后一个生效
		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));
		String result = entityManager.createQuery(criteriaQuery).getSingleResult();
	
	# 检索多个字段, 结果封装为对象
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.multiselect(root.get("id"), root.get("name"));
		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));
		User user = entityManager.createQuery(criteriaQuery).getSingleResult();

		select
			user0_.id as col_0_0_,
			user0_.name as col_1_0_ 
		from
			user user0_ 
		where
			user0_.id=1
		
		* 对象必须要有指定的构造函数(根据检索的属性定义)
	
	# 检索多个字段, 结果封装为 List<Object[]>
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.multiselect(root.get("id"), root.get("name"));
		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));
		List<Object> results = entityManager.createQuery(criteriaQuery).getResultList();
	
	# IN 查询
		In<Integer> in = criteriaBuilder.in(root.get("id"));
		in.value(1);		// 添加多个字段
		in.value(2);		// 添加多个in
		

	# 子查询

	
	
