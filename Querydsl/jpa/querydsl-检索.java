--------------------
�����ļ���	
--------------------
	# ���ݵ�����������
		User user = queryFactory.selectFrom(QUser.user).where(QUser.user.id.eq(1)).fetchFirst();
		json(user);
	
	# ���ݶ����������
		BooleanExpression expression1 = QUser.user.name.eq("KevinBlandy");
		BooleanExpression expression2 = QUser.user.version.lt(1);

		BooleanExpression expression3 = QUser.user.id.eq(1);
		
		queryFactory.selectFrom(QUser.user).where(expression1.or(expression2), expression3).fetchFirst();

		// ���ɵ�����SQL
		where (user0_.name=? or user0_.version<? ) and user0_.id=?

		* Ĭ�� where �еĶ��������ϵ��Ϊ and
	
	# ���������ֶ�
		// �����ֶ�
		List<String> names = queryFactory.select(QUser.user.name)
				.from(QUser.user)
				.fetch();
	
		* �ֶ�������ʲô, ���������ʲô

		
		// ����ֶη�װΪ Tuple
		List<Tuple> tuples = queryFactory.select(QUser.user.name, QUser.user.id)
				.from(QUser.user)
				.fetch();
		
		// ����ֶη�װΪʵ��
		List<User> users = queryFactory.select(Projections.bean(QUser.user, QUser.user.name, QUser.user.id))
				.from(QUser.user)
				.fetch();
	
	# һ�ű�����μ���
		QUser qUser = QUser.user;
		QUser user1 = new QUser("user1");
		
		this.jpaQueryFactory.select(user1)
			.from(qUser)
			.innerJoin(user1).on(user1.id.eq(qUser.id))
			.fetch();
		
		// SQL
		SELECT
			user1_.id AS id1_2_,
			user1_.gender AS gender2_2_,
			user1_.NAME AS name3_2_,
			user1_.version AS version4_2_ 
		FROM
			USER user0_
				INNER JOIN USER user1_ ON (
				user1_.id = user0_.id 
			)
		
		* ��Ҫ���������������, һ�����������ʾһ�ű�

	
	# �ж������Ƿ����
		Integer r = this.jpaQueryFactory.select(Expressions.ONE)
			.from(QUser.user)
			.where(QUser.user.id.eq(1)).fetchFirst();
		
		* ���rΪnull, �����ݲ�����
	

	# ������SQL�ļ���
		QVideo qVideo = QVideo.video;
		this.jpaQueryFactory.selectFrom(qVideo)  // ָ��Ҫ�����ı�
			.select(qVideo.id, qVideo.title)	// ָ��Ҫ��������
			.fetch();					

--------------------
��ҳ
--------------------
	# ��ҳ����
		QueryResults<User> results = queryFactory.selectFrom(QUser.user)
						.offset(1)
						.limit(2)
						.fetchResults();
		
		offset(1)	����limit
		limit(10)	����ÿҳ��ʾ������

		* offset ����ҳ��, offset = (ҳ�� - 1) * limit
		* ֻҪ��ʹ���� fetchResults(), �ͻ������������ͳ�Ʋ�ѯ
	
	# ����� QueryResults, ����������
		private final long limit, offset, total;
		private final List<T> results;
	
	
	# ����countͳ��
		Long count = queryFactory.selectFrom(QUser.user)
				.offset(2)
				.limit(1)
				.fetchCount();
	
	# ������ҳ, ��ͳ�Ƽ���
		List<User> results = queryFactory.selectFrom(QUser.user)
				.offset(2)
				.limit(1)
				.fetch();

--------------------
����
--------------------
	# ͨ�� OrderSpecifier ����
		List<User> results = queryFactory.selectFrom(QUser.user)
					.orderBy(new OrderSpecifier(Order.DESC, QUser.user.name),
							new OrderSpecifier(Order.DESC, QUser.user.id, OrderSpecifier.NullHandling.NullsFirst))
					.fetch();
	
	# OrderSpecifier ���캯��
		OrderSpecifier(Order order, Expression<T> target, NullHandling nullhandling)
		OrderSpecifier(Order order, Expression<T> target)
			order 
				* ����ʽ, ö��
					ASC
					DESC
			
			target
				* �����ֶ�, ���������
			
			nullhandling
				* nullֵ�Ĵ���, ö��
					Default			Ĭ��
					NullsFirst		null��¼����ǰ��
					NullsLast		null��¼���ں���
		
	# ͨ��������������
		List<User> results = queryFactory.selectFrom(QUser.user)
			.orderBy(QUser.user.name.asc(),
					QUser.user.id.desc())
			.fetch();
		
		* ���ܶ� null ֵ���д���
	

--------------------
����
--------------------
	# ����
		queryFactory.selectFrom(QUser.user).where()
			.setLockMode(LockModeType.PESSIMISTIC_WRITE)
			.fetchResults();
		

		LockModeType ö��
			READ
			WRITE
			OPTIMISTIC
			OPTIMISTIC_FORCE_INCREMENT
			PESSIMISTIC_READ
			PESSIMISTIC_WRITE
			PESSIMISTIC_FORCE_INCREMENT
			NONE

--------------------
����JPA��ѯ
--------------------
	Query query = queryFactory.select(QUser.user).createQuery();

--------------------
����
--------------------
	// TODO ������

	StringExpression stringExpression = Expressions.asString("Ĭ��");

	query.select(stringExpression.as("name")); // SELECT `Ĭ��` AS `name`


----------------------------------------
CASE ���ʽ, IF ���, IFNULL ���
----------------------------------------
	# CaseBuilder
		QCustomer customer = QCustomer.customer;
			Expression<String> cases = new CaseBuilder()
			.when(customer.annualSpending.gt(10000)).then("Premier")
			.when(customer.annualSpending.gt(5000)).then("Gold")
			.when(customer.annualSpending.gt(2000)).then("Silver")
			.otherwise("Bronze");
		// The cases expression can now be used in a projection or condition
	
	# Expressions.nullExpression()
		query.select(
							qAccUnitStock.quantity.when(
								Expressions.nullExpression()).then(0L).otherwise(qAccUnitStock.quantity) 
					)
					.from(qAccUnitStock)
					.where(qAccUnitStock.booksId.eq(item.getBooksId()).and(qAccUnitStock.unitAgencyId.eq(ret.getUnitAgencyId()))).fetchOne()
		

		* ���ֵΪnull��������Ϊ�Զ���ֵ

	# Demo
		QVideo qVideo = QVideo.video;
		
		// nullif ��䣬���������ֵһ�����ͷ���null��
		// NULLIF(Expression1,Expression2):������������Expression1��Expression2���������������ȣ��򷵻�NULL������ͷ��ص�һ��������
		SimpleExpression<Integer> c2 = qVideo.playCount.nullif(22);
		
		// is null ��䣬��������NULL������1, ����������NULL������ 0
		// ����һ�� isNotNull(); �պ��෴
		BooleanExpression c4 = qVideo.poster.isNull();
		
		// CASE ���

		// ��� playCount ��-1���򷵻�0�����򷵻� 999
		NumberExpression<Integer> c1 = qVideo.playCount.when(-1).then(0).otherwise(999);
		// ��� title �� �ַ�����null������none�������� 0�����򷵻� 1
		BooleanExpression c3 = qVideo.title.lower().when("null").then(false).when("none").then(false).otherwise(true);
		// ��� coin ��1������Ϊnull,���򷵻�coinֵ
		Expression<Object> c5 = qVideo.coin.when(0).thenNull().otherwise(qVideo.coin);
		
		this.jpaQueryFactory
			.select(c1, c2, c3, c4, c5)
			.from(qVideo)
			.fetch();
		
		// SQL
		SELECT
			CASE 
				WHEN video0_.play_count =? THEN ? 
				ELSE 999 
			END AS col_0_0_,
			nullif( video0_.play_count, ? ) AS col_1_0_,
			CASE
				WHEN lower( video0_.title ) =? THEN ? 
				WHEN lower( video0_.title ) =? THEN? 
				ELSE 1 
			END AS col_2_0_,
				video0_.poster IS NULL AS col_3_0_,
			CASE
				WHEN video0_.coin =? THEN NULL 
				ELSE 'video.coin' 
			END AS col_4_0_ 
		FROM
			video video0_