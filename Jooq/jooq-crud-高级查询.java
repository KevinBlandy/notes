-----------------------
统计查询
-----------------------
	# count
		Record1<Integer> result =dslContext.select(DSL.count()).from(admin).fetchOne(); // select count(*) from `jooq`.`admin`
		System.out.println(result);

		Record1<Integer> result =dslContext.select(DSL.count(admin.ID)).from(admin).fetchOne();  // select count(`jooq`.`admin`.`id`) from `jooq`.`admin`
        System.out.println(result);
	

	# exists 查询
		 Boolean exists =  dslContext
                    .select(DSL.field(DSL.exists(DSL.selectOne().from(adminTable)
                            .where(adminTable.ID.eq(UInteger.valueOf(1)).and(adminTable.ACCOUNT.eq("123456"))))).as("exists"))
                    .fetchOneInto(Boolean.class);
			
			// select (exists (select 1 as `one` from `jooq`.`admin` where (`jooq`.`admin`.`id` = 1 and `jooq`.`admin`.`account` = '123456'))) as `exists`

-----------------------
子查询
-----------------------
	# 子查询
		* 结果集中的子查询，要封装为 Filed 对象
			DSL.field() 
			// Field<Integer> count = DSL.field( DSL.selectCount().from(adminRole).where(adminRole.ADMIN_ID.eq(admin.ID))).as("count");
			// @NotNull Field<Integer> count = DSL.field( DSL.select(DSL.count(adminRole.ROLE_ID)).from(adminRole).where(adminRole.ADMIN_ID.eq(admin.ID))).as("count");

	
	# 结果集子查询
		 // 管理员
		Admin admin = Tables.ADMIN;
		// 管理角色
		AdminRole adminRole = Tables.ADMIN_ROLE;
		// 角色
		Role role = Tables.ROLE;

		// roleName子查询。使用 DSL.field 构建
		Field<String> roleName = DSL.field(DSL.select(role.NAME).from(role)
				.innerJoin(adminRole).on(adminRole.ROLE_ID.eq(role.ID))
				.where(adminRole.ADMIN_ID.eq(admin.ID))).as("roleName");

		dslContext.select(Tables.ADMIN.fields()).select(roleName)
			.from(admin)
				.where(DSL.exists(dslContext.selectOne().from(adminRole)
						.where(adminRole.ADMIN_ID.eq(admin.ID))))
			.fetch()
			;
	
	# 结果子 exists 查询
		    // 管理员
            Admin admin = Tables.ADMIN;
            // 管理角色
            AdminRole adminRole = Tables.ADMIN_ROLE;
			// exists 子查询
            Field<Boolean> exists = DSL.field(DSL.exists(DSL.selectOne().from(adminRole).where(adminRole.ADMIN_ID.eq(admin.ID)))).as("exists");

            dslContext.select(admin.fields())
                    .select(exists).
                    from(admin)
                    .fetch();

	# 条件子查询
		Condition condition = adminTable.PASSWORD.eq("123456");
		condition = condition.and(adminTable.ID.in( // 子查询
				dslContext.select(subTable.ID).from(subTable).where(subTable.ID.eq(UInteger.valueOf(58))))
		);

	# 条件exists子查询
		// 管理员
		Admin admin = Tables.ADMIN;
		// 管理角色
		AdminRole adminRole = Tables.ADMIN_ROLE;

		dslContext.select()
			.from(admin)
				.where(DSL.exists(DSL.selectOne().from(adminRole)
						.where(adminRole.ADMIN_ID.eq(admin.ID))))
			.fetch()
			;
		
		* sql
			SELECT
				`jooq`.`admin`.`id`,
				`jooq`.`admin`.`account`,
				`jooq`.`admin`.`create_at`,
				`jooq`.`admin`.`enabled`,
				`jooq`.`admin`.`nickame`,
				`jooq`.`admin`.`password`,
				`jooq`.`admin`.`update_at` 
			FROM
				`jooq`.`admin` 
			WHERE
				EXISTS ( SELECT 1 AS `one` FROM `jooq`.`admin_role` WHERE `jooq`.`admin_role`.`admin_id` = `jooq`.`admin`.`id` )

	# 表子查询
	   * 临时表中有字段名称
	   	    // 临时表
            Table<Record2<UInteger, UByte>> table = dslContext.select(Tables.ADMIN.ID.as("bid"),
                    Tables.ADMIN.ENABLED).from(Tables.ADMIN)
					.asTable("temp"); // asTable， 定义为临时表
            // 从临时表查询，可以通过别名访问
            dslContext.selectFrom(table).where(table.field("bid", Integer.class).eq(1)).fetch();
		
		* 临时表中没有字段名称
			// 临时表
            @NotNull Table<AdminRecord> table = dslContext.selectFrom(Tables.ADMIN).asTable("temp");
            // 从临时表查询，通过字段下标访问
            dslContext.selectFrom(table).where(table.field(0, Integer.class).eq(1)).fetch();
			// 通过字段名称访问
			dslContext.selectFrom(table).where(table.field("enabled", Integer.class).eq(1)).fetch();
			
			* sql
				SELECT
					`temp`.`id`,
					`temp`.`account`,
					`temp`.`create_at`,
					`temp`.`enabled`,
					`temp`.`nickame`,
					`temp`.`password`,
					`temp`.`update_at` 
				FROM
					(
				SELECT
					`jooq`.`admin`.`id`,
					`jooq`.`admin`.`account`,
					`jooq`.`admin`.`create_at`,
					`jooq`.`admin`.`enabled`,
					`jooq`.`admin`.`nickame`,
					`jooq`.`admin`.`password`,
					`jooq`.`admin`.`update_at` 
				FROM
					`jooq`.`admin` 
					) AS `temp` 
				WHERE
					`temp`.`id` = 1

-----------------------
条件分组
-----------------------
	# 条件核心对象
		Condition

-----------------------
分页
-----------------------
	

-----------------------
排序
-----------------------
	# 核心对象
		SortField<T>
	
