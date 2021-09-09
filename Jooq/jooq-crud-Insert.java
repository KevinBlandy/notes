-------------------------
Insert
-------------------------
	# 一次性插入指定列的数据
		 int retVal = dslContext.insertInto(Tables.ADMIN, Tables.ADMIN.ACCOUNT, Tables.ADMIN.CREATE_AT, Tables.ADMIN.ENABLED, Tables.ADMIN.NICKAME, Tables.ADMIN.PASSWORD)
				.values("foo", LocalDateTime.now(), UByte.valueOf(1), "foo", "123456")
				.values("bar", LocalDateTime.now(), UByte.valueOf(1), "bar", "123456")
				.execute()
				;
	
		* sql
			insert into `jooq`.`admin` (`account`, `create_at`, `enabled`, `nickame`, `password`) values (?, ?, ?, ?, ?), (?, ?, ?, ?, ?)

		* 必须要保证 values的值类型和insertinto中的字段相同，并且列数量是一样的
	
	# 分批指定插入列的数据
		 int ret =  dslContext.insertInto(Tables.ADMIN)
                            .set(Tables.ADMIN.ACCOUNT, "123456")
                            .set(Tables.ADMIN.PASSWORD, "123456")
                            .set(Tables.ADMIN.ENABLED, UByte.valueOf(1))
                            .set(Tables.ADMIN.CREATE_AT, LocalDateTime.now())
                        .newRecord()
                            .set(Tables.ADMIN.ACCOUNT, "654321")
                            .set(Tables.ADMIN.PASSWORD, "654321")
                            .set(Tables.ADMIN.ENABLED, UByte.valueOf(1))
                            .set(Tables.ADMIN.CREATE_AT, LocalDateTime.now())
                            .set(Tables.ADMIN.NICKAME, "昵称哟") // 多了一个昵称字段
                        .execute()
                        ;
			
		* sql
			insert into `jooq`.`admin` (`account`, `password`, `enabled`, `create_at`, `nickame`) values (?, ?, ?, ?, ?), (?, ?, ?, ?, ?)
			insert into `jooq`.`admin` (`account`, `password`, `enabled`, `create_at`, `nickame`) values ('123456', '123456', 1, {ts '2021-09-07 11:14:44.0086243'}, null), ('654321', '654321', 1, {ts '2021-09-07 11:14:44.0086243'}, '昵称哟')

		* 如果每一批插入记录的列数不一样，则以最多的为准，其他少字段的列，为默认值
	

			
	# 通过 Record 插入单行数据
		AdminRecord adminRecord = dslContext.newRecord(Tables.ADMIN);

		adminRecord.setAccount("a1111");
		adminRecord.setPassword("123456");
		adminRecord.setCreateAt(LocalDateTime.now());
		adminRecord.setEnabled(UByte.valueOf(1));

		int retVal = adminRecord.insert();

		System.out.println(retVal);
		
		* sql
			insert into `jooq`.`admin` (`account`, `create_at`, `enabled`, `password`) values (?, ?, ?, ?)
		
		* 必须要通过 DSLContext的newRecord来执行，如果是自己直接创建的则不行： new AdminRecord();
		* 如果有自增ID，则会反写入到 adminRecord 对象中
			adminRecord.getId();
		
		* 插入的字段必须显式的进行set操作，才会在最终的SQL语句中体现出来
		* 也可以指定只插入部分数据
			 adminRecord.insert(Tables.ADMIN.ACCOUNT, Tables.ADMIN.ENABLED, Tables.ADMIN.PASSWORD);
	 

	# 通过 Record 批量插入数据

		List<AdminRecord> adminRecords = new ArrayList<>();

		for (int i = 0; i < 5; i ++){
			AdminRecord adminRecord = new AdminRecord();
			adminRecord.setAccount("a1111" + i);
			adminRecord.setPassword("123456");
			adminRecord.setCreateAt(LocalDateTime.now());
			adminRecord.setEnabled(UByte.valueOf(1));
			adminRecords.add(adminRecord);
		}

		int[] results = dslContext.batchInsert(adminRecords).execute();
		System.out.println(Arrays.toString(results));  // [1, 1, 1, 1, 1]
		
		* sql
			insert into `jooq`.`admin` (`account`, `create_at`, `enabled`, `password`) values (?, ?, ?, ?)
		* 这是for插入了5次？
		* 这种方式不会把自增ID反写入到 Record 对象中
	
	# 更新或者插入
		AuthorRecord author = create.fetchOne(AUTHOR, AUTHOR.ID.eq(1));
		if (author == null) {
			author = create.newRecord(AUTHOR);
			author.setId(1);
			author.setFirstName("Dan");
			author.setLastName("Brown");
		}
		// 如果记录已经存在则执行修改，如果不存在则执行插入
		// 如果记录已经存在，读取出来后，没有进行过修改，那么不会执行更新操作。
		author.store();
	
	# 默认值，defaultValue 可以设置指定列的默认值
		create.insertInto(
				AUTHOR, AUTHOR.ID, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME, ...)
			  .values(
				defaultValue(AUTHOR.ID), 
				defaultValue(AUTHOR.FIRST_NAME), 
				defaultValue(AUTHOR.LAST_NAME), ...)
			  .execute();
	
	# INSERT .. SELECT
		create.insertInto(AUTHOR_ARCHIVE)
			  .select(selectFrom(AUTHOR).where(AUTHOR.DECEASED.isTrue()))
			  .execute();

-------------------------
自增ID获取
-------------------------
	# context执行insert，获取返回的自增ID
		* 方式1
			Result<AdminRecord> retVal = dslContext.insertInto(Tables.ADMIN, Tables.ADMIN.ACCOUNT, Tables.ADMIN.CREATE_AT, Tables.ADMIN.ENABLED, Tables.ADMIN.NICKAME, Tables.ADMIN.PASSWORD)
				.values("foo", LocalDateTime.now(), UByte.valueOf(1), "foo", "123456")
				.values("bar", LocalDateTime.now(), UByte.valueOf(1), "bar", "123456")
				.returning(Tables.ADMIN.ID)		// 指定自增ID字段
				.fetch() // 如果只插入了1条记录，可以用 fetchOne()
				;

			// 获取到每一条记录的自增ID，反写到 record 中
			retVal.stream().forEach(record -> System.out.println(record.getId()));
		
		* 方式2
			Result<AdminRecord> retVal =  dslContext.insertInto(Tables.ADMIN)
				.set(Tables.ADMIN.ACCOUNT, "123456")
				.set(Tables.ADMIN.PASSWORD, "123456")
				.set(Tables.ADMIN.ENABLED, UByte.valueOf(1))
				.set(Tables.ADMIN.CREATE_AT, LocalDateTime.now())
			.newRecord()
				.set(Tables.ADMIN.ACCOUNT, "654321")
				.set(Tables.ADMIN.PASSWORD, "654321")
				.set(Tables.ADMIN.ENABLED, UByte.valueOf(1))
				.set(Tables.ADMIN.CREATE_AT, LocalDateTime.now())
				.set(Tables.ADMIN.NICKAME, "昵称哟") // 多了一个昵称字段
				.returning(Tables.ADMIN.ID)		// 指定自增ID字段
			.fetch()	// 如果只插入了1条记录，可以用 fetchOne()
			;

			// 获取到每一条记录的自增ID，反写到 record 中
			retVal.stream().forEach(record -> System.out.println(record.getId()));
	
	# 多行自生成数据
		Result<?> result =
			create.insertInto(AUTHOR, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME)
				  .values("Johann Wolfgang", "von Goethe")
				  .values("Friedrich", "Schiller")
				  // 自动生成的字段
				  .returningResult(AUTHOR.ID, AUTHOR.CREATION_DATE)
				  .fetch();
			

-------------------------
唯一冲突处理
-------------------------
	#  ON CONFLICT DO NOTHING 
		 @Nullable AdminRecord retVal = dslContext.insertInto(Tables.ADMIN, Tables.ADMIN.ACCOUNT, Tables.ADMIN.CREATE_AT, Tables.ADMIN.ENABLED, Tables.ADMIN.NICKAME, Tables.ADMIN.PASSWORD)
			.values("foo", LocalDateTime.now(), UByte.valueOf(1), "foo", "123456")
			.onConflictDoNothing()
			.returning(Tables.ADMIN.ID)
			.fetchOne()
			;
		if (retVal != null){
			System.out.println(retVal.getId());
		}

	
	# ON DUPLICATE KEY IGNORE 冲突忽略
		@Nullable AdminRecord retVal = dslContext.insertInto(Tables.ADMIN, Tables.ADMIN.ACCOUNT, Tables.ADMIN.CREATE_AT, Tables.ADMIN.ENABLED, Tables.ADMIN.NICKAME, Tables.ADMIN.PASSWORD)
				.values("foo", LocalDateTime.now(), UByte.valueOf(1), "foo", "123456")
				// .onConflictDoNothing()
				.onDuplicateKeyIgnore()
				.returning(Tables.ADMIN.ID)
				.fetchOne()
				;
		if (retVal != null){
			System.out.println(retVal.getId());
		}
	
	# ON DUPLICATE KEY UPDATE 冲突修改
		dslContext.insertInto(S1_USER)
			.set(S1_USER.ID, 1)
			.set(S1_USER.USERNAME, "duplicateKey-insert")
			.set(S1_USER.ADDRESS, "hello world")
		.onDuplicateKeyUpdate()
			.set(S1_USER.USERNAME, "duplicateKey-update")
			.set(S1_USER.ADDRESS, "update")
		.execute();
		// 生成SQL: insert into `learn-jooq`.`s1_user` (`id`, `username`, `address`) values (1, 'duplicateKey-update', 'hello world') on duplicate key update `learn-jooq`.`s1_user`.`username` = 'duplicateKey-update', `learn-jooq`.`s1_user`.`address` = 'update'