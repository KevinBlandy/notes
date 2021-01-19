--------------------
基本的检索	
--------------------
	# 根据单个条件检索
		User user = queryFactory.selectFrom(QUser.user).where(QUser.user.id.eq(1)).fetchFirst();
		json(user);
	
	# 根据多个条件检索
		BooleanExpression expression1 = QUser.user.name.eq("KevinBlandy");
		BooleanExpression expression2 = QUser.user.version.lt(1);

		BooleanExpression expression3 = QUser.user.id.eq(1);
		
		queryFactory.selectFrom(QUser.user).where(expression1.or(expression2), expression3).fetchFirst();

		// 生成的条件SQL
		where (user0_.name=? or user0_.version<? ) and user0_.id=?

		* 默认 where 中的多个参数关系都为 and
	
	# 检索部分字段
		// 单个字段
		List<String> names = queryFactory.select(QUser.user.name)
				.from(QUser.user)
				.fetch();
	
		* 字段类型是什么, 结果集就是什么

		
		// 多个字段封装为 Tuple
		List<Tuple> tuples = queryFactory.select(QUser.user.name, QUser.user.id)
				.from(QUser.user)
				.fetch();
		
		// 多个字段封装为实体
		List<User> users = queryFactory.select(Projections.bean(QUser.user, QUser.user.name, QUser.user.id))
				.from(QUser.user)
				.fetch();
	
	# 一张表参与多次检索
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
		
		* 需要创建多个检索对象, 一个检索对象表示一张表

	
	# 判断数据是否存在
		Integer r = this.jpaQueryFactory.select(Expressions.ONE)
			.from(QUser.user)
			.where(QUser.user.id.eq(1)).fetchFirst();
		
		* 如果r为null, 则数据不存在
	

	# 类似于SQL的检索
		QVideo qVideo = QVideo.video;
		this.jpaQueryFactory.selectFrom(qVideo)  // 指定要检索的表
			.select(qVideo.id, qVideo.title)	// 指定要检索的列
			.fetch();					

--------------------
分页
--------------------
	# 分页检索
		QueryResults<User> results = queryFactory.selectFrom(QUser.user)
						.offset(1)
						.limit(2)
						.fetchResults();
		
		offset(1)	设置limit
		limit(10)	设置每页显示的数量

		* offset 不是页码, offset = (页码 - 1) * limit
		* 只要是使用了 fetchResults(), 就会进行总数量的统计查询
	
	# 结果集 QueryResults, 包含了属性
		private final long limit, offset, total;
		private final List<T> results;
	
	
	# 检索count统计
		Long count = queryFactory.selectFrom(QUser.user)
				.offset(2)
				.limit(1)
				.fetchCount();
	
	# 仅仅分页, 不统计检索
		List<User> results = queryFactory.selectFrom(QUser.user)
				.offset(2)
				.limit(1)
				.fetch();

--------------------
排序
--------------------
	# 通过 OrderSpecifier 排序
		List<User> results = queryFactory.selectFrom(QUser.user)
					.orderBy(new OrderSpecifier(Order.DESC, QUser.user.name),
							new OrderSpecifier(Order.DESC, QUser.user.id, OrderSpecifier.NullHandling.NullsFirst))
					.fetch();
	
	# OrderSpecifier 构造函数
		OrderSpecifier(Order order, Expression<T> target, NullHandling nullhandling)
		OrderSpecifier(Order order, Expression<T> target)
			order 
				* 排序方式, 枚举
					ASC
					DESC
			
			target
				* 排序字段, 对象的属性
			
			nullhandling
				* null值的处理, 枚举
					Default			默认
					NullsFirst		null记录排在前面
					NullsLast		null记录排在后面
		
	# 通过对象属性排序
		List<User> results = queryFactory.selectFrom(QUser.user)
			.orderBy(QUser.user.name.asc(),
					QUser.user.id.desc())
			.fetch();
		
		* 不能对 null 值进行处理
	

--------------------
加锁
--------------------
	# 加锁
		queryFactory.selectFrom(QUser.user).where()
			.setLockMode(LockModeType.PESSIMISTIC_WRITE)
			.fetchResults();
		

		LockModeType 枚举
			READ
			WRITE
			OPTIMISTIC
			OPTIMISTIC_FORCE_INCREMENT
			PESSIMISTIC_READ
			PESSIMISTIC_WRITE
			PESSIMISTIC_FORCE_INCREMENT
			NONE

--------------------
本地JPA查询
--------------------
	Query query = queryFactory.select(QUser.user).createQuery();

--------------------
常量
--------------------
	//TODO

	SELECT 'CONST MNAME' AS `name` ...

----------------------------------------
CASE 表达式, IF 语句, IFNULL 语句
----------------------------------------
	QVideo qVideo = QVideo.video;
	
	// nullif 语句，如果参数和值一样，就返回null，
	// NULLIF(Expression1,Expression2):给定两个参数Expression1和Expression2，如果两个参数相等，则返回NULL；否则就返回第一个参数。
	SimpleExpression<Integer> c2 = qVideo.playCount.nullif(22);
	
	// is null 语句，如果结果是NULL，返回1, 如果结果不是NULL，返回 0
	// 还有一个 isNotNull(); 刚好相反
	BooleanExpression c4 = qVideo.poster.isNull();
	
	// CASE 语句

	// 如果 playCount 是-1，则返回0，否则返回 999
	NumberExpression<Integer> c1 = qVideo.playCount.when(-1).then(0).otherwise(999);
	// 如果 title 是 字符串“null”，“none”，返回 0，否则返回 1
	BooleanExpression c3 = qVideo.title.lower().when("null").then(false).when("none").then(false).otherwise(true);
	// 如果 coin 是1，返回为null,否则返回coin值
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