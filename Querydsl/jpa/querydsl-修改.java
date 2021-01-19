----------------------
插入
----------------------
	# 插入
		

----------------------
更新
----------------------
	# 更新
		JPAUpdateClause jpaUpdateClause = queryFactory
			.update(QUser.user)
			.set(QUser.user.name, "new Value")
			.where(QUser.user.id.eq(15));
		long result = jpaUpdateClause.execute();
		
----------------------
删除
----------------------
	# 删除
		JPADeleteClause jpaDeleteClause = queryFactory
					.delete(QUser.user)
					.where(QUser.user.id.in(5, 6, 9, 8, 10));  // 批量删除
		long result = jpaDeleteClause.execute();
		

	

