-----------------------
ͳ�Ʋ�ѯ
-----------------------
	# count
		Record1<Integer> result =dslContext.select(DSL.count()).from(admin).fetchOne(); // select count(*) from `jooq`.`admin`
		System.out.println(result);

		Record1<Integer> result =dslContext.select(DSL.count(admin.ID)).from(admin).fetchOne();  // select count(`jooq`.`admin`.`id`) from `jooq`.`admin`
        System.out.println(result);
	
		int count = dslContext.selectCount().from(ADMIN).fetchOneInto(Integer.class);  //  select count(*) from `jooq`.`admin`
		System.out.println(count);

		dslContext.select(count(one())).from(ADMIN).fetchOne(); // SELECT count(1) FROM `springcloud.io`.`admin`

	# exists ��ѯ
		 Boolean exists =  dslContext
                    .select(DSL.field(DSL.exists(DSL.selectOne().from(adminTable)
                            .where(adminTable.ID.eq(UInteger.valueOf(1)).and(adminTable.ACCOUNT.eq("123456"))))).as("exists"))
                    .fetchOneInto(Boolean.class);
			
			// select (exists (select 1 as `one` from `jooq`.`admin` where (`jooq`.`admin`.`id` = 1 and `jooq`.`admin`.`account` = '123456'))) as `exists`
		
		boolean exists = dslContext.fetchExists(ADMIN, ADMIN.ACCOUNT.eq("123456"));
		//  select 1 as `one` from dual where exists (select 1 as `one` from `springcloud.io`.`admin` where `springcloud.io`.`admin`.`account` = '123456')
		System.out.println(exists); 

-----------------------
�Ӳ�ѯ
-----------------------
	# �Ӳ�ѯ
		* ������е��Ӳ�ѯ��Ҫ��װΪ Filed ����
			DSL.field() 
			// Field<Integer> count = DSL.field( DSL.selectCount().from(adminRole).where(adminRole.ADMIN_ID.eq(admin.ID))).as("count");
			// @NotNull Field<Integer> count = DSL.field( DSL.select(DSL.count(adminRole.ROLE_ID)).from(adminRole).where(adminRole.ADMIN_ID.eq(admin.ID))).as("count");

	
	# ������Ӳ�ѯ
		 // ����Ա
		Admin admin = Tables.ADMIN;
		// �����ɫ
		AdminRole adminRole = Tables.ADMIN_ROLE;
		// ��ɫ
		Role role = Tables.ROLE;

		// roleName�Ӳ�ѯ��ʹ�� DSL.field ����
		Field<String> roleName = DSL.field(DSL.select(role.NAME).from(role)
				.innerJoin(adminRole).on(adminRole.ROLE_ID.eq(role.ID))
				.where(adminRole.ADMIN_ID.eq(admin.ID))).as("roleName");

		dslContext.select(Tables.ADMIN.fields()).select(roleName)
			.from(admin)
				.where(DSL.exists(dslContext.selectOne().from(adminRole)
						.where(adminRole.ADMIN_ID.eq(admin.ID))))
			.fetch()
			;
	
	# ����� exists ��ѯ
		    // ����Ա
            Admin admin = Tables.ADMIN;
            // �����ɫ
            AdminRole adminRole = Tables.ADMIN_ROLE;
			// exists �Ӳ�ѯ
            Field<Boolean> exists = DSL.field(DSL.exists(DSL.selectOne().from(adminRole).where(adminRole.ADMIN_ID.eq(admin.ID)))).as("exists");

            dslContext.select(admin.fields())
                    .select(exists).
                    from(admin)
                    .fetch();

	# �����Ӳ�ѯ
		Condition condition = adminTable.PASSWORD.eq("123456");
		condition = condition.and(adminTable.ID.in( // �Ӳ�ѯ
				dslContext.select(subTable.ID).from(subTable).where(subTable.ID.eq(UInteger.valueOf(58))))
		);

	# ����exists�Ӳ�ѯ
		// ����Ա
		Admin admin = Tables.ADMIN;
		// �����ɫ
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

	# ���Ӳ�ѯ
	   * ��ʱ�������ֶ�����
	   	    // ��ʱ��
            Table<Record2<UInteger, UByte>> table = dslContext.select(Tables.ADMIN.ID.as("bid"),
                    Tables.ADMIN.ENABLED).from(Tables.ADMIN)
					.asTable("temp"); // asTable�� ����Ϊ��ʱ��
            // ����ʱ���ѯ������ͨ����������
            dslContext.selectFrom(table).where(table.field("bid", Integer.class).eq(1)).fetch();
		
		* ��ʱ����û���ֶ�����
			// ��ʱ��
            @NotNull Table<AdminRecord> table = dslContext.selectFrom(Tables.ADMIN).asTable("temp");
            // ����ʱ���ѯ��ͨ���ֶ��±����
            dslContext.selectFrom(table).where(table.field(0, Integer.class).eq(1)).fetch();
			// ͨ���ֶ����Ʒ���
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
��������
-----------------------
	# �������Ķ���
		Condition

	# ���õ�һЩ���ʽ
		* �����������Դ��� where 1 = 1
			public static Condition noCondition()
	
		* where �Ĳ�������Ϊnull����ʾû������
			List<Admin> admin = dslContext.select(field("*")).from(ADMIN).where((Condition) null).offset(1).limit(10).fetchInto(Admin.class); 
	
	
	# Record ������Ϊ������Ĭ�Ϲ�ϵ�� and 
		AdminRecord adminRecord = new AdminRecord();
		
		adminRecord.setId(UInteger.valueOf(1));
		adminRecord.setAccount("123456");
		
		// SELECT `springcloud.io`.`admin`.`balance` FROM `springcloud.io`.`admin` WHERE (`springcloud.io`.`admin`.`id` = 1 AND `springcloud.io`.`admin`.`account` = '123456')
		this.dslContext.select(ADMIN.BALANCE).from(ADMIN)
			.where(condition(adminRecord))
			.fetch()
			;
	
	# MapҲ������Ϊ����

-----------------------
��ҳ
-----------------------
	# ������
		offset()
		limit()
		

		List<Admin> admin = dslContext.select(field("*")).from(ADMIN).limit(1, 10).fetchInto(Admin.class); //  select * from `jooq`.`admin` limit 10 offset 1
		List<Admin> admin = dslContext.select(field("*")).from(ADMIN).offset(1).limit(10).fetchInto(Admin.class); //  select * from `jooq`.`admin` limit 10 offset 1
	

-----------------------
����
-----------------------
	# ���Ķ���
		SortField<T>
	
	# NULL ֵ����
		ADMIN.CREATE_AT.desc().nullsLast()
		ADMIN.CREATE_AT.desc().nullsFirst()
	
	# У�Է�ʽ��ָ��
		create.selectFrom(BOOK)
		  .orderBy(BOOK.TITLE.collate("utf8_bin"))
		  .fetch();

-----------------------
����ת��
-----------------------
	# �������ת��
		dsl.select(count(MYTABLE.ID).cast(SQLDataType.DOUBLE).divide(7.0))
		   .from(MYTABLE)
		   .fetch();
	
	# ʹ�� convert �ӿ�ת��
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
��д��
-----------------------
	# ��Ҫ�� SelectForUpdateStep �ӿ��ṩ�˺��ĵļ�������
		forUpdate();			// for update
		forNoKeyUpdate();
		forShare();				// for share (for share ��MYSQL8��)
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
����
-----------------------
	# ������ѯ
		dslContext.select(val("Kevin").as("name"), inline("boy").as("gender")).fetch();
		// select 'Kevin' as `name`, 'boy' as `gender` from dual


-----------------------
case/when
-----------------------
	# ������е�case
		Field<String> foo = case_(ADMIN.ID)
			.when(UInteger.valueOf(1), "Ҽ")
			.when(UInteger.valueOf(2), "��")
			.else_("��֪��")
		.as("tmp");
		
		Result<Record> result = dslContext.select(ADMIN.asterisk(), foo).from(ADMIN).orderBy(ADMIN.CREATE_AT.desc().nullsLast()).fetch();

	# �����е�case
		create.select()
		  .from(BOOK)
		  .orderBy(case_(BOOK.TITLE)
				   .when("1984", 0)
				   .when("Animal Farm", 1)
				   .else_(2).asc())
		  .fetch();
	
		
		* ����֧��map������ʡ�Զ�� when
			create.select()
			  .from(BOOK)
			  .orderBy(BOOK.TITLE.sort(new HashMap<String, Integer>() {{
				  put("1984", 1);
				  put("Animal Farm", 13);
				  put("The jOOQ book", 10);
			  }}))
			  .fetch();
	

	# ������е�when
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
����PreparedStatement
-----------------------
	// ����һ����ѯ���ò�ѯ������Ϊ������ײ�PreparedStatement�Ŀ��š�
	try (ResultQuery<Record> query = create.selectOne().keepStatement(true)) {
		Result<Record> result1 = query.fetch(); // �ӳٴ���һ���µ� PreparedStatement
		Result<Record> result2 = query.fetch(); // ������һ���ϴ���  PreparedStatement
	}