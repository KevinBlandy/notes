-------------------
WHERE 条件子查询
-------------------
	# WHERE条件中单行单列检索
		queryFactory.selectFrom(QUser.user)
				.where(QUser.user.id.eq(
					JPAExpressions.select(QUser.user.id).from(QUser.user).where(QUser.user.name.eq("KevinBlandy")))
				)
				.fetch();
	
	# WHERE条件中多列单行
		where(QUser.user.id.in(
				JPAExpressions.select(QUser.user.id).from(QUser.user).where(...))
			)
		
		* 就是用in计算多列结果
		
	# WHERE条件中 exists 检索
		QUser qUser = QUser.user;
		queryFactory.select(qUser)
				.from(qUser)
				.where(JPAExpressions.selectOne().from(QUserRole.userRole)
					.where(QUserRole.userRole.userId.eq(qUser.id).and(QUserRole.userRole.roleId.eq(1)))
					.exists()
				)
				.fetch();
		

-------------------
结果集子查询
-------------------
	# count检索
		QCategory qCategory = QCategory.category;
		QVideoCategory qVideoCategory = QVideoCategory.videoCategory;

		NumberExpression<Long> numberExpression = qVideoCategory.categoryId.count();
		
		QBean<CategoryDTO> categoryQBean = Projections.bean(CategoryDTO.class, 
				qCategory.id, qCategory.title, qCategory.poster, qCategory.describe, qCategory.sorted, qCategory.createdDate);
		
		
		JPAQuery<Tuple> jpaQuery = query.select(categoryQBean, JPAExpressions.select(numberExpression)
					.from(qVideoCategory)
					.where(qVideoCategory.categoryId.eq(qCategory.id)))
				.from(qCategory);
	
	# exists 检索
		QVideo qVideo = QVideo.video;
		QVideoTag qVideoTag = QVideoTag.videoTag;

		// 子查询
		BooleanExpression exists = JPAExpressions.selectOne().from(qVideoTag).where(qVideoTag.videoId.eq(qVideo.id)).fetchAll().exists();

		List<Tuple> tuples = this.jpaQueryFactory.select(qVideo, exists).from(qVideo).fetch();
		for (Tuple tuple : tuples) {
			// 获取子查询结果
			boolean result = tuple.get(exists);
		}

		* 这种 exists 子查询，不能嵌套在 Projections.bean() 中，必须声明在 select 方法中
			this.jpaQueryFactory.select(Projections.bean(VideoDTO.class, qVideo.id, qVideo.title, exists)).from(qVideo).fetch(); // 异常

	
	# 结果集是否为Null检索(算不上子查询)
		QPayMode qPayMode = QPayMode.payMode;
		QPayChannel qPayChannel = QPayChannel.payChannel;
	
		JPAQuery<PayModeDTO> jpaQuery = this.jpaQueryFactory.select(Projections.bean(PayModeDTO.class, qPayMode.id,
				qPayMode.name, qPayMode.enabled, qPayChannel.payModeId.isNotNull().as("selected")))
			.from(qPayMode)
			
			.leftJoin(qPayChannel).on(qPayMode.id.eq(qPayChannel.payModeId).and(qPayChannel.goodsId.eq(goodsId)))
			
			.where(qPayMode.deletedDate.eq(SystemProperties.NOT_DELETED).and(qPayMode.payTypeId.eq(1)))
			.orderBy(new OrderSpecifier<>(Order.DESC, qPayMode.sorted), new OrderSpecifier<>(Order.DESC, qPayMode.createdDate));
		
		List<PayModeDTO> payModes = jpaQuery.fetch();