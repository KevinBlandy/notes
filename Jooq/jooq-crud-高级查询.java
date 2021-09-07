-----------------------
ͳ�Ʋ�ѯ
-----------------------
	# count
		Record1<Integer> result =dslContext.select(DSL.count()).from(admin).fetchOne(); // select count(*) from `jooq`.`admin`
		System.out.println(result);

		Record1<Integer> result =dslContext.select(DSL.count(admin.ID)).from(admin).fetchOne();  // select count(`jooq`.`admin`.`id`) from `jooq`.`admin`
        System.out.println(result);
	

	# exists ��ѯ
		 Boolean exists =  dslContext
                    .select(DSL.field(DSL.exists(DSL.selectOne().from(adminTable)
                            .where(adminTable.ID.eq(UInteger.valueOf(1)).and(adminTable.ACCOUNT.eq("123456"))))).as("exists"))
                    .fetchOneInto(Boolean.class);
			
			// select (exists (select 1 as `one` from `jooq`.`admin` where (`jooq`.`admin`.`id` = 1 and `jooq`.`admin`.`account` = '123456'))) as `exists`

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

-----------------------
��ҳ
-----------------------
	

-----------------------
����
-----------------------
	# ���Ķ���
		SortField<T>
	
