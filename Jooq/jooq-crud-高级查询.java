-----------------------
统计查询
-----------------------
	# count
		Record1<Integer> result =dslContext.select(DSL.count()).from(admin).fetchOne(); // select count(*) from `jooq`.`admin`
		System.out.println(result);

		Record1<Integer> result =dslContext.select(DSL.count(admin.ID)).from(admin).fetchOne();  // select count(`jooq`.`admin`.`id`) from `jooq`.`admin`
        System.out.println(result);
	
		int count = dslContext.selectCount().from(ADMIN).fetchOneInto(Integer.class);  //  select count(*) from `jooq`.`admin`
		System.out.println(count);

		dslContext.select(count(one())).from(ADMIN).fetchOne(); // SELECT count(1) FROM `springcloud.io`.`admin`

	# exists 查询
		 Boolean exists =  dslContext
                    .select(DSL.field(DSL.exists(DSL.selectOne().from(adminTable)
                            .where(adminTable.ID.eq(UInteger.valueOf(1)).and(adminTable.ACCOUNT.eq("123456"))))).as("exists"))
                    .fetchOneInto(Boolean.class);
			
			// select (exists (select 1 as `one` from `jooq`.`admin` where (`jooq`.`admin`.`id` = 1 and `jooq`.`admin`.`account` = '123456'))) as `exists`
		
		boolean exists = dslContext.fetchExists(ADMIN, ADMIN.ACCOUNT.eq("123456"));
		//  select 1 as `one` from dual where exists (select 1 as `one` from `springcloud.io`.`admin` where `springcloud.io`.`admin`.`account` = '123456')
		System.out.println(exists); 

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

	# 常用的一些表达式
		* 无条件，可以代替 where 1 = 1
			public static Condition noCondition()
	
		* where 的参数可以为null，表示没有条件
			List<Admin> admin = dslContext.select(field("*")).from(ADMIN).where((Condition) null).offset(1).limit(10).fetchInto(Admin.class); 
	
	
	# Record 可以作为条件，默认关系是 and 
		AdminRecord adminRecord = new AdminRecord();
		
		adminRecord.setId(UInteger.valueOf(1));
		adminRecord.setAccount("123456");
		
		// SELECT `springcloud.io`.`admin`.`balance` FROM `springcloud.io`.`admin` WHERE (`springcloud.io`.`admin`.`id` = 1 AND `springcloud.io`.`admin`.`account` = '123456')
		this.dslContext.select(ADMIN.BALANCE).from(ADMIN)
			.where(condition(adminRecord))
			.fetch()
			;
	
	# Map也可以作为条件

-----------------------
分页
-----------------------
	# 俩方法
		offset()
		limit()
		

		List<Admin> admin = dslContext.select(field("*")).from(ADMIN).limit(1, 10).fetchInto(Admin.class); //  select * from `jooq`.`admin` limit 10 offset 1
		List<Admin> admin = dslContext.select(field("*")).from(ADMIN).offset(1).limit(10).fetchInto(Admin.class); //  select * from `jooq`.`admin` limit 10 offset 1
	

-----------------------
排序
-----------------------
	# 核心对象
		SortField<T>
	
	# NULL 值处理
		ADMIN.CREATE_AT.desc().nullsLast()
		ADMIN.CREATE_AT.desc().nullsFirst()
	
	# 校对方式的指定
		create.selectFrom(BOOK)
		  .orderBy(BOOK.TITLE.collate("utf8_bin"))
		  .fetch();

-----------------------
类型转换
-----------------------
	# 结果类型转换
		dsl.select(count(MYTABLE.ID).cast(SQLDataType.DOUBLE).divide(7.0))
		   .from(MYTABLE)
		   .fetch();
	
	# 使用 convert 接口转换
		enum Language { de, en, fr, it, pt; }
		Converter<String, Language> converter = new EnumConverter<>(String.class, Language.class);

		Result<Record2<Integer, Language>> result =
				create.select(LANGUAGE.ID, LANGUAGE.CD.convert(converter))
			  .from(LANGUAGE)
			  .fetch();
	
		Result<Record2<Integer, Language>> result =
			create.select(LANGUAGE.ID, LANGUAGE.CD.convert(Language.class, Language::valueOf, Language::name))
				  .from(LANGUAGE)
				  .fetch();

-----------------------
读写锁
-----------------------
	# 主要是 SelectForUpdateStep 接口提供了核心的加锁方法
		forUpdate();			// for update
		forNoKeyUpdate();
		forShare();				// for share (for share 是MYSQL8的)
		forKeyShare();

		https://github.com/jOOQ/jOOQ/issues/12410

	# for update
		List<Admin> admins = dslContext.selectOne().from(ADMIN).where(ADMIN.ID.eq(UInteger.valueOf(1))).forUpdate().fetchInto(Admin.class);
		// select 1 as `one` from `springcloud.io`.`admin` where `springcloud.io`.`admin`.`id` = 1 for update

	# lock in share mode
		boolean exists = dslContext.select(
					field(
						exists(
							selectOne().from(ADMIN).where(ADMIN.ID.eq(UInteger.valueOf(1))).forShare()
						)
					).as("exists"))
				.fetchOneInto(boolean.class);
		
		// select (exists (select 1 as `one` from `springcloud.io`.`admin` where `springcloud.io`.`admin`.`id` = 1 for share)) as `exists` from dual
	
		System.out.println(exists);


-----------------------
常量
-----------------------
	# 常量查询
		dslContext.select(val("Kevin").as("name"), inline("boy").as("gender")).fetch();
		// select 'Kevin' as `name`, 'boy' as `gender` from dual


-----------------------
case/when
-----------------------
	# 结果集中的case
		Field<String> foo = case_(ADMIN.ID)
			.when(UInteger.valueOf(1), "壹")
			.when(UInteger.valueOf(2), "贰")
			.else_("不知道")
		.as("tmp");
		
		Result<Record> result = dslContext.select(ADMIN.asterisk(), foo).from(ADMIN).orderBy(ADMIN.CREATE_AT.desc().nullsLast()).fetch();

	# 排序中的case
		create.select()
		  .from(BOOK)
		  .orderBy(case_(BOOK.TITLE)
				   .when("1984", 0)
				   .when("Animal Farm", 1)
				   .else_(2).asc())
		  .fetch();
	
		
		* 排序还支持map，可以省略多个 when
			create.select()
			  .from(BOOK)
			  .orderBy(BOOK.TITLE.sort(new HashMap<String, Integer>() {{
				  put("1984", 1);
				  put("Animal Farm", 13);
				  put("The jOOQ book", 10);
			  }}))
			  .fetch();
	

	# 结果集中的when
		create.select(
		  // Searched case
		  when(AUTHOR.FIRST_NAME.eq("Paulo"), "brazilian")
		  .when(AUTHOR.FIRST_NAME.eq("George"), "english")
		  .otherwise("unknown");

		  // Simple case
		  choose(AUTHOR.FIRST_NAME)
		  .when("Paulo", "brazilian")
		  .when("George", "english")
		  .otherwise("unknown"))
		.from(AUTHOR)
		.fetch();


-----------------------
复用PreparedStatement
-----------------------
	// 创建一个查询，该查询被配置为保持其底层PreparedStatement的开放。
	try (ResultQuery<Record> query = create.selectOne().keepStatement(true)) {
		Result<Record> result1 = query.fetch(); // 延迟创建一个新的 PreparedStatement
		Result<Record> result2 = query.fetch(); // 复用上一步上传的  PreparedStatement
	}