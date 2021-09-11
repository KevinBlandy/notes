---------------------------
ͨ�õĽ����
---------------------------
	# Tuple ����ֵ��Ϊͨ�õĽ����
	
	# ͬʱ������������¼
		List<Tuple> tuples = queryFactory.select(QUser.user, QAddress.address)
			.innerJoin(QAddress.address).on(QUser.user.id.eq(QAddress.address.user.id))
			.fetch();
		
		* ��ʱ, Tuple ת��Ϊ�����������[User, Address]
	
	# Ҳ����������������Ϊ QTuple
		List<Tuple> result = query.select(new QTuple(employee.firstName, employee.lastName)).from(employee).fetch();
		for (Tuple row : result) {
			 System.out.println("firstName " + row.get(employee.firstName));
			 System.out.println("lastName " + row.get(employee.lastName));
		}}

---------------------------
���ӽ��ӳ��Ϊ�Զ������
---------------------------
	# ͨ�����캯����װΪ�Զ���Ķ���

		List<UserModel> userModels = queryFactory.select(Projections.constructor(UserModel.class, QUser.user.id, QUser.user.name))
					.from(QUser.user)
					.fetch();
			
		
		* ����ֻ�����ָ�����ֶ�, �������Ҫ�ж�Ӧ�Ĺ��캯��
	


		<T> ConstructorExpression<T> constructor(Class<? extends T> type, Expression<?>... exprs)
		<T> ConstructorExpression<T> constructor(Class<? extends T> type, Class<?>[] paramTypes, Expression<?>... exprs)
		<T> ConstructorExpression<T> constructor(Class<? extends T> type, Class<?>[] paramTypes, ImmutableList<Expression<?>> exprs)

		type
			* �Զ������
		paramTypes
			* ���캯��������
		exprs
			* ʵ�������
		
	# ͨ�����Է�װΪ�Զ������
		List<UserModel> list = queryFactory.select(Projections.bean(UserModel.class, QUser.user.name))
					.from(QUser.user)
					.fetch()
		

		* ����ֻ�����ָ�����ֶ�, �������Ҫ�пյĹ��캯��
		* ���Զ������������Ʒ�װ����, ����������Ʋ�����, ����ʹ�ñ���
			QUser.user.name.as("name")
		

		* ��������������, ʹ�� Projections.bean ��װ�����ֶ�, ����Ҫ��������Ҫ��������(����), ����ֱ��дһ��Q��ѯ����, ����û����ѯ������

			// ���Ǳ�
            QStar qStar = QStar.star;
            
            // ���ǹ�������Ƶ��
            QVideoStar qVideoStar = QVideoStar.videoStar;
            
            // �Ѳ����������ݷ�װΪ StartDTO ����
            QBean<StarDTO> qBean = Projections.bean(StarDTO.class, qStar.id, qStar.name, qStar);
			// QBean<StarDTO> qBean = Projections.bean(StarDTO.class, qStar);	����
            
            JPAQuery<Tuple> jpaQuery = jpaQueryFactory.select(qBean,
                        JPAExpressions.select(qVideoStar.starId.count()).from(qVideoStar).where(qVideoStar.starId.eq(qStar.id)))
                    .from(qStar);

            QueryResults<Tuple> queryResults = jpaQuery.fetchResults();
            List<StarDTO> stars = queryResults.getResults().stream().map(i -> {
            	
            	// ��ȡ���Ǽ�¼����
            	StarDTO starDTO = i.get(qBean);
            	
            	// ��ȡ�Ӳ�ѯͳ������
                starDTO.setVideoCount(i.get(1, Long.class).intValue());
                
                return starDTO;
            }).collect(Collectors.toList());

		
		* Qbean �����ж��
			QOrder qOrder = QOrder.order;
			QUser qUser = QUser.user;
			
			// ��װ������Ϣ
			QBean<OrderDTO> orderQBean = Projections.bean(OrderDTO.class, qOrder.id, qOrder.amount, qOrder.orderNumber,
											qOrder.createdDate, qOrder.paymentDate, qOrder.snapshot, qOrder.state);
			
			// ��װ�û���Ϣ
			QBean<User> userQBean = Projections.bean(User.class, qUser.account, qUser.id, qUser.avatar, qUser.name);
			
			JPAQuery<Tuple> jpaQuery = jpaQueryFactory.select(orderQBean, userQBean)
				.from(qOrder)
				.innerJoin(qUser).on(qOrder.userId.eq(qUser.id))
				.orderBy(qOrder.createdDate.desc());
			
			QueryResults<Tuple> queryResults = jpaQuery.fetchResults();
			List<OrderDTO> results = queryResults.getResults().stream().map(i -> {
				// �ӽ����Tuple�и���Qbean��ȡ������
				OrderDTO orderDTO = i.get(orderQBean);
				orderDTO.setUser(i.get(userQBean));
				return orderDTO;
			}).collect(Collectors.toList());

		
	
	# ��װ���Ϊ����
		List<Integer[]> result = queryFactory.select(Projections.array(Integer[].class, QUser.user.version, QUser.user.id))
				.from(QUser.user)
				.fetch();
		
		* �����ͬ���͵���, ��װΪ����
	




---------------------------
�Խ��������group
---------------------------
	# �ٷ�����
		https://github.com/querydsl/querydsl/blob/master/querydsl-collections/src/test/java/com/querydsl/collections/GroupByTest.java
	
	# ���ڴ��н��з��飬Ҳ���ǹ��������������װ
		jpaQueryFactory.select().from().innerJoin().on().transform();
		jpaQueryFactory.select().from().innerJoin().on().fetchAll()transform();
	
	
	# ������ľۺ�Demo
		import static com.querydsl.core.group.GroupBy.*;
		Map<Integer, List<Comment>> results = query.from(post, comment)
			.where(comment.post.id.eq(post.id))
			.transform(groupBy(post.id).as(list(comment)));
		
		Map<Integer, Group> results = query.from(post, comment)
			.where(comment.post.id.eq(post.id))
			.transform(groupBy(post.id).as(post.name, set(comment.id)));


	# Group By ���о�̬����
		<K> GroupByBuilder<K> groupBy(Expression<K> key)
		GroupByBuilder<List<?>> groupBy(Expression<?>... keys)
			* group ���ķ���������ָ��Ҫ������ֶ�

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
	
	# GroupByBuilder ʵ������
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

	
	# ����Ľ�����ӿ�	Group
		Object[] toArray();
		<T, R> R getGroup(GroupExpression<T, R> coldef);
		<T> T getOne(Expression<T> expr);
		<T> Set<T> getSet(Expression<T> expr);
		<T> SortedSet<T> getSortedSet(Expression<T> expr);
		<T> List<T> getList(Expression<T> expr);
		<K, V> Map<K, V> getMap(Expression<K> key, Expression<V> value);
		<K, V> SortedMap<K, V> getSortedMap(Expression<K> key, Expression<V> value);
	
