-------------------------
Insert
-------------------------
	# һ���Բ���ָ���е�����
		 int retVal = dslContext.insertInto(Tables.ADMIN, Tables.ADMIN.ACCOUNT, Tables.ADMIN.CREATE_AT, Tables.ADMIN.ENABLED, Tables.ADMIN.NICKAME, Tables.ADMIN.PASSWORD)
				.values("foo", LocalDateTime.now(), UByte.valueOf(1), "foo", "123456")
				.values("bar", LocalDateTime.now(), UByte.valueOf(1), "bar", "123456")
				.execute()
				;
	
		* sql
			insert into `jooq`.`admin` (`account`, `create_at`, `enabled`, `nickame`, `password`) values (?, ?, ?, ?, ?), (?, ?, ?, ?, ?)

		* ����Ҫ��֤ values��ֵ���ͺ�insertinto�е��ֶ���ͬ��������������һ����
	
	# ����ָ�������е�����
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
                            .set(Tables.ADMIN.NICKAME, "�ǳ�Ӵ") // ����һ���ǳ��ֶ�
                        .execute()
                        ;
			
		* sql
			insert into `jooq`.`admin` (`account`, `password`, `enabled`, `create_at`, `nickame`) values (?, ?, ?, ?, ?), (?, ?, ?, ?, ?)
			insert into `jooq`.`admin` (`account`, `password`, `enabled`, `create_at`, `nickame`) values ('123456', '123456', 1, {ts '2021-09-07 11:14:44.0086243'}, null), ('654321', '654321', 1, {ts '2021-09-07 11:14:44.0086243'}, '�ǳ�Ӵ')

		* ���ÿһ�������¼��������һ������������Ϊ׼���������ֶε��У�ΪĬ��ֵ
	

			
	# ͨ�� Record ���뵥������
		AdminRecord adminRecord = dslContext.newRecord(Tables.ADMIN);

		adminRecord.setAccount("a1111");
		adminRecord.setPassword("123456");
		adminRecord.setCreateAt(LocalDateTime.now());
		adminRecord.setEnabled(UByte.valueOf(1));

		int retVal = adminRecord.insert();

		System.out.println(retVal);
		
		* sql
			insert into `jooq`.`admin` (`account`, `create_at`, `enabled`, `password`) values (?, ?, ?, ?)
		
		* ����Ҫͨ�� DSLContext��newRecord��ִ�У�������Լ�ֱ�Ӵ��������У� new AdminRecord();
		* ���������ID����ᷴд�뵽 adminRecord ������
			adminRecord.getId();
		
		* ������ֶα�����ʽ�Ľ���set�������Ż������յ�SQL��������ֳ���
		* Ҳ����ָ��ֻ���벿������
			 adminRecord.insert(Tables.ADMIN.ACCOUNT, Tables.ADMIN.ENABLED, Tables.ADMIN.PASSWORD);
	 

	# ͨ�� Record ������������

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
		* ����for������5�Σ�
		* ���ַ�ʽ���������ID��д�뵽 Record ������
	
	# ���»��߲���
		AuthorRecord author = create.fetchOne(AUTHOR, AUTHOR.ID.eq(1));
		if (author == null) {
			author = create.newRecord(AUTHOR);
			author.setId(1);
			author.setFirstName("Dan");
			author.setLastName("Brown");
		}
		// �����¼�Ѿ�������ִ���޸ģ������������ִ�в���
		// �����¼�Ѿ����ڣ���ȡ������û�н��й��޸ģ���ô����ִ�и��²�����
		author.store();
	
	# Ĭ��ֵ��defaultValue ��������ָ���е�Ĭ��ֵ
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
����ID��ȡ
-------------------------
	# contextִ��insert����ȡ���ص�����ID
		* ��ʽ1
			Result<AdminRecord> retVal = dslContext.insertInto(Tables.ADMIN, Tables.ADMIN.ACCOUNT, Tables.ADMIN.CREATE_AT, Tables.ADMIN.ENABLED, Tables.ADMIN.NICKAME, Tables.ADMIN.PASSWORD)
				.values("foo", LocalDateTime.now(), UByte.valueOf(1), "foo", "123456")
				.values("bar", LocalDateTime.now(), UByte.valueOf(1), "bar", "123456")
				.returning(Tables.ADMIN.ID)		// ָ������ID�ֶ�
				.fetch() // ���ֻ������1����¼�������� fetchOne()
				;

			// ��ȡ��ÿһ����¼������ID����д�� record ��
			retVal.stream().forEach(record -> System.out.println(record.getId()));
		
		* ��ʽ2
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
				.set(Tables.ADMIN.NICKAME, "�ǳ�Ӵ") // ����һ���ǳ��ֶ�
				.returning(Tables.ADMIN.ID)		// ָ������ID�ֶ�
			.fetch()	// ���ֻ������1����¼�������� fetchOne()
			;

			// ��ȡ��ÿһ����¼������ID����д�� record ��
			retVal.stream().forEach(record -> System.out.println(record.getId()));
	
	# ��������������
		Result<?> result =
			create.insertInto(AUTHOR, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME)
				  .values("Johann Wolfgang", "von Goethe")
				  .values("Friedrich", "Schiller")
				  // �Զ����ɵ��ֶ�
				  .returningResult(AUTHOR.ID, AUTHOR.CREATION_DATE)
				  .fetch();
			

-------------------------
Ψһ��ͻ����
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

	
	# ON DUPLICATE KEY IGNORE ��ͻ����
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
	
	# ON DUPLICATE KEY UPDATE ��ͻ�޸�
		dslContext.insertInto(S1_USER)
			.set(S1_USER.ID, 1)
			.set(S1_USER.USERNAME, "duplicateKey-insert")
			.set(S1_USER.ADDRESS, "hello world")
		.onDuplicateKeyUpdate()
			.set(S1_USER.USERNAME, "duplicateKey-update")
			.set(S1_USER.ADDRESS, "update")
		.execute();
		// ����SQL: insert into `learn-jooq`.`s1_user` (`id`, `username`, `address`) values (1, 'duplicateKey-update', 'hello world') on duplicate key update `learn-jooq`.`s1_user`.`username` = 'duplicateKey-update', `learn-jooq`.`s1_user`.`address` = 'update'