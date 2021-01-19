-----------------------
关联检索
-----------------------
	# 多表关联检索
		queryFactory.selectFrom(QUser.user)		
			.innerJoin(QUser.user.userSeting)		// 一对一
			.leftJoin(QUser.user.roles)				// 多对多
			.fetch();
		
		// SQL
		SELECT
			user0_.id AS id1_2_,
			user0_.NAME AS name2_2_,
			user0_.version AS version3_2_ 
		FROM
			USER user0_
			INNER JOIN user_seting userseting1_ ON user0_.id = userseting1_.id
			LEFT OUTER JOIN user_role roles2_ ON user0_.id = roles2_.user_id
			LEFT OUTER JOIN role role3_ ON roles2_.role_id = role3_.id
		

		* join的时候, 如果有关系, 会自动添加关联关系, 去除笛卡尔积
		* 能自动的判断多对多关系, 自动的去关联中间表去检索


		List<Tuple> tuples = queryFactory.select(QUser.user, QAddress.address)
					.from(QUser.user)
					.innerJoin(QAddress.address).on(QUser.user.id.eq(QAddress.address.user.id))
					.fetch();
		for (Tuple tuple : tuples){
			json(tuple.toArray());
		}
		
		// sql
		SELECT
			user0_.id AS id1_2_0_,
			address1_.id AS id1_0_1_,
			user0_.NAME AS name2_2_0_,
			user0_.version AS version3_2_0_,
			address1_.NAME AS name2_0_1_,
			address1_.user_id AS user_id3_0_1_ 
		FROM
			USER user0_
			INNER JOIN address address1_ ON ( user0_.id = address1_.user_id )


	
	# 多表关联检索, where 条件过滤
		queryFactory.selectFrom(QUser.user)
			.innerJoin(QUser.user.userSeting)
			.leftJoin(QUser.user.addresses)
			.leftJoin(QUser.user.roles)
			.where(QUser.user.id.eq(1), QUser.user.roles.any().name.eq("role"))  // 根据角色名称和用户id过滤
			.fetch();

			
		// SQL
		SELECT
			user0_.id AS id1_2_,
			user0_.NAME AS name2_2_,
			user0_.version AS version3_2_ 
		FROM
			USER user0_
			INNER JOIN user_seting userseting1_ ON user0_.id = userseting1_.id
			LEFT OUTER JOIN address addresses2_ ON user0_.id = addresses2_.user_id
			LEFT OUTER JOIN user_role roles3_ ON user0_.id = roles3_.user_id
			LEFT OUTER JOIN role role4_ ON roles3_.role_id = role4_.id 
		WHERE
			user0_.id =? 
		AND (
			EXISTS (
					SELECT
						1 
					FROM
						user_role roles5_,
						role role6_ 
					WHERE
						user0_.id = roles5_.user_id 
						AND roles5_.role_id = role6_.id 
						AND role6_.NAME =? 
				) 
			)
		
		* 多对多中, 另一方的条件过滤, 是根据子查询过滤
	
	# 多表联合检索, join 条件过滤
		queryFactory.selectFrom(QUser.user)
					.innerJoin(QUser.user.userSeting).on(QUser.user.userSeting.notify.eq(true)).on(QUser.user.userSeting.notify.eq(true))
					.leftJoin(QUser.user.addresses)
					.leftJoin(QUser.user.roles)
					.where(QUser.user.id.eq(1))
					.fetch();
		
		
		//SQL
		from
			user user0_ 
		inner join
			user_seting userseting1_  on user0_.id=userseting1_.id 	and (userseting1_.notify= ? and userseting1_.notify=?) 

		* join的时候, 如果有关系, 除了会自动添加关联关系, 去除笛卡尔积, 还会添加一个子条件过滤
	

	
