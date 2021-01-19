---------------------------
通用的结果集
---------------------------
	# Tuple 返回值作为通用的结果集
	
	# 同时检索多个对象记录
		List<Tuple> tuples = queryFactory.select(QUser.user, QAddress.address)
			.innerJoin(QAddress.address).on(QUser.user.id.eq(QAddress.address.user.id))
			.fetch();
		
		* 此时, Tuple 转换为数组的类型是[User, Address]
	
	# 也可以主动声明类型为 QTuple
		List<Tuple> result = query.select(new QTuple(employee.firstName, employee.lastName)).from(employee).fetch();
		for (Tuple row : result) {
			 System.out.println("firstName " + row.get(employee.firstName));
			 System.out.println("lastName " + row.get(employee.lastName));
		}}

---------------------------
复杂结果映射为自定义对象
---------------------------
	# 通过构造函数封装为自定义的对象

		List<UserModel> userModels = queryFactory.select(Projections.constructor(UserModel.class, QUser.user.id, QUser.user.name))
					.from(QUser.user)
					.fetch();
			
		
		* 仅仅只会检索指定的字段, 对象必须要有对应的构造函数
	


		<T> ConstructorExpression<T> constructor(Class<? extends T> type, Expression<?>... exprs)
		<T> ConstructorExpression<T> constructor(Class<? extends T> type, Class<?>[] paramTypes, Expression<?>... exprs)
		<T> ConstructorExpression<T> constructor(Class<? extends T> type, Class<?>[] paramTypes, ImmutableList<Expression<?>> exprs)

		type
			* 自定义的类
		paramTypes
			* 构造函数的类型
		exprs
			* 实体的属性
		
	# 通过属性封装为自定义对象
		List<UserModel> list = queryFactory.select(Projections.bean(UserModel.class, QUser.user.name))
					.from(QUser.user)
					.fetch()
		

		* 仅仅只会检索指定的字段, 对象必须要有空的构造函数
		* 会自动根据属性名称封装属性, 如果属性名称不符合, 可以使用别名
			QUser.user.name.as("name")
		

		* 多表结果集的情况下, 使用 Projections.bean 封装部分字段, 必须要声明出需要检索的列(属性), 不能直接写一个Q查询对象, 不能没法查询出东西

			// 明星表
            QStar qStar = QStar.star;
            
            // 明星关联的视频表
            QVideoStar qVideoStar = QVideoStar.videoStar;
            
            // 把部分明星数据封装为 StartDTO 对象
            QBean<StarDTO> qBean = Projections.bean(StarDTO.class, qStar.id, qStar.name, qStar);
			// QBean<StarDTO> qBean = Projections.bean(StarDTO.class, qStar);	错误
            
            JPAQuery<Tuple> jpaQuery = jpaQueryFactory.select(qBean,
                        JPAExpressions.select(qVideoStar.starId.count()).from(qVideoStar).where(qVideoStar.starId.eq(qStar.id)))
                    .from(qStar);

            QueryResults<Tuple> queryResults = jpaQuery.fetchResults();
            List<StarDTO> stars = queryResults.getResults().stream().map(i -> {
            	
            	// 获取明星记录数据
            	StarDTO starDTO = i.get(qBean);
            	
            	// 获取子查询统计数据
                starDTO.setVideoCount(i.get(1, Long.class).intValue());
                
                return starDTO;
            }).collect(Collectors.toList());

		
		* Qbean 可以有多个
			QOrder qOrder = QOrder.order;
			QUser qUser = QUser.user;
			
			// 封装订单信息
			QBean<OrderDTO> orderQBean = Projections.bean(OrderDTO.class, qOrder.id, qOrder.amount, qOrder.orderNumber,
											qOrder.createdDate, qOrder.paymentDate, qOrder.snapshot, qOrder.state);
			
			// 封装用户信息
			QBean<User> userQBean = Projections.bean(User.class, qUser.account, qUser.id, qUser.avatar, qUser.name);
			
			JPAQuery<Tuple> jpaQuery = jpaQueryFactory.select(orderQBean, userQBean)
				.from(qOrder)
				.innerJoin(qUser).on(qOrder.userId.eq(qUser.id))
				.orderBy(qOrder.createdDate.desc());
			
			QueryResults<Tuple> queryResults = jpaQuery.fetchResults();
			List<OrderDTO> results = queryResults.getResults().stream().map(i -> {
				// 从结果集Tuple中根据Qbean获取到数据
				OrderDTO orderDTO = i.get(orderQBean);
				orderDTO.setUser(i.get(userQBean));
				return orderDTO;
			}).collect(Collectors.toList());

		
	
	# 封装结果为数组
		List<Integer[]> result = queryFactory.select(Projections.array(Integer[].class, QUser.user.version, QUser.user.id))
				.from(QUser.user)
				.fetch();
		
		* 多个相同类型的列, 封装为数组
	




---------------------------
对结果集进行group
---------------------------
	# 在内存中进行分组，也就是关联检索结果集封装
		jpaQueryFactory.select().from().innerJoin().on().transform();
		jpaQueryFactory.select().from().innerJoin().on().fetchAll()transform();
	
	
	# 结果集的聚合
		import static com.querydsl.core.group.GroupBy.*;
		Map<Integer, List<Comment>> results = query.from(post, comment)
			.where(comment.post.id.eq(post.id))
			.transform(groupBy(post.id).as(list(comment)));
		
		Map<Integer, Group> results = query.from(post, comment)
			.where(comment.post.id.eq(post.id))
			.transform(groupBy(post.id).as(post.name, set(comment.id)));


	# Group By 所有静态方法
		<K> GroupByBuilder<K> groupBy(Expression<K> key)
		GroupByBuilder<List<?>> groupBy(Expression<?>... keys)
			* group 核心方法，用于指定要分组的字段

		<E extends Comparable<? super E>> AbstractGroupExpression<E, E> min(Expression<E> expression)
		<E extends Number> AbstractGroupExpression<E, E> sum(Expression<E> expression)
		<E extends Number> AbstractGroupExpression<E, E> avg(Expression<E> expression)
		<E extends Comparable<? super E>> AbstractGroupExpression<E, E> max(Expression<E> expression)
		<E> AbstractGroupExpression<E, List<E>> list(Expression<E> expression)
		<E, F> AbstractGroupExpression<E, List<F>> list(GroupExpression<E, F> groupExpression)
		<E> AbstractGroupExpression<E, Set<E>> set(Expression<E> expression)
		<E, F> GroupExpression<E, Set<F>> set(GroupExpression<E, F> groupExpression)
		<E extends Comparable<? super E>> AbstractGroupExpression<E, SortedSet<E>> sortedSet(Expression<E> expression)
		<E, F extends Comparable<? super F>> GroupExpression<E, SortedSet<F>> sortedSet(GroupExpression<E, F> groupExpression)
		<E> AbstractGroupExpression<E, SortedSet<E>> sortedSet(Expression<E> expression, Comparator<? super E> comparator)
		<E, F> GroupExpression<E, SortedSet<F>> sortedSet(GroupExpression<E, F> groupExpression, Comparator<? super F> comparator)
		<K, V> AbstractGroupExpression<Pair<K, V>,Map<K, V>> map(Expression<K> key, Expression<V> value)
		<K, V, T> AbstractGroupExpression<Pair<K, V>, Map<T, V>> map(GroupExpression<K, T> key, Expression<V> value)
		<K, V, U> AbstractGroupExpression<Pair<K, V>, Map<K, U>> map(Expression<K> key,  GroupExpression<V, U> value)
		<K, V, T, U> AbstractGroupExpression<Pair<K, V>, Map<T, U>> map(GroupExpression<K, T> key, GroupExpression<V, U> value)
		<K extends Comparable<? super K>, V> AbstractGroupExpression<Pair<K, V>, SortedMap<K, V>> sortedMap(Expression<K> key, Expression<V> value)
		<K, V, T extends Comparable<? super T>> AbstractGroupExpression<Pair<K, V>, SortedMap<T, V>> sortedMap(GroupExpression<K, T> key, Expression<V> value)
		<K extends Comparable<? super K>, V, U> AbstractGroupExpression<Pair<K, V>, SortedMap<K, U>> sortedMap(Expression<K> key, GroupExpression<V, U> value)
		<K, V, T extends Comparable<? super T>, U> AbstractGroupExpression<Pair<K, V>, SortedMap<T, U>> sortedMap(GroupExpression<K, T> key, GroupExpression<V, U> value)
		<K, V> AbstractGroupExpression<Pair<K, V>, SortedMap<K, V>> sortedMap(Expression<K> key, Expression<V> value, Comparator<? super K> comparator)
		<K, V, T> AbstractGroupExpression<Pair<K, V>, SortedMap<T, V>> sortedMap(GroupExpression<K, T> key, Expression<V> value, Comparator<? super T> comparator)
		<K, V, U> AbstractGroupExpression<Pair<K, V>, SortedMap<K, U>> sortedMap(Expression<K> key, GroupExpression<V, U> value, Comparator<? super K> comparator)
		<K, V, T, U> AbstractGroupExpression<Pair<K, V>, SortedMap<T, U>> sortedMap(GroupExpression<K, T> key, GroupExpression<V, U> value, Comparator<? super T> comparator)
	
	# GroupByBuilder 实例方法
		ResultTransformer<Map<K, Group>> as(Expression<?>... expressions)
		ResultTransformer<CloseableIterator<Group>> iterate(Expression<?>... expressions)
		ResultTransformer<List<Group>> list(Expression<?>... expressions)
		<V> ResultTransformer<Map<K, V>> as(Expression<V> expression)
		<V> ResultTransformer<CloseableIterator<V>> iterate(Expression<V> expression)
		<V> ResultTransformer<List<V>> list(Expression<V> expression)
		<V> Expression<V> getLookup(Expression<V> expression)
		<V> ResultTransformer<Map<K, V>> as(FactoryExpression<V> expression)
		<V> ResultTransformer<CloseableIterator<V>> iterate(FactoryExpression<V> expression)
		<V> ResultTransformer<List<V>> list(FactoryExpression<V> expression)

