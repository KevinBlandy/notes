-----------------------
Update
-----------------------
	# DSLContext 执行修改
		int retVal = dslContext.update(Tables.ADMIN)
			.set(Tables.ADMIN.NICKAME, "NEW NAME")
			.set(Tables.ADMIN.PASSWORD, Tables.ADMIN.PASSWORD.concat("123"))
			.where(Tables.ADMIN.ID.eq(UInteger.valueOf(1)))
			.execute()
			;
		System.out.println(retVal);

		* sql
			update `jooq`.`admin` set `jooq`.`admin`.`nickame` = 'NEW NAME', `jooq`.`admin`.`password` = concat(`jooq`.`admin`.`password`, '123') where `jooq`.`admin`.`id` = 1
	
	# Record 更新

		AdminRecord adminRecord = dslContext.newRecord(Tables.ADMIN);
		adminRecord.setId(UInteger.valueOf(1));
		adminRecord.setNickame("hhh");
		int ret = adminRecord.update();

		System.out.println(ret);

		* sql
			update `jooq`.`admin` set `jooq`.`admin`.`id` = 1, `jooq`.`admin`.`nickame` = 'hhh' where `jooq`.`admin`.`id` = 1
		* 根据主键值进行更新，如果主键值为null，那么条件也就是null

		* 只有经过set的字段，才会被更新处理
		* 可以在更新的时候指定需要更新的字段
			

	

	# 批量更新
		List<AdminRecord> adminRecords = new ArrayList<>();
		for (int i = 0; i < 5; i ++){
			AdminRecord adminRecord = new AdminRecord();
			adminRecord.setId(UInteger.valueOf(i + 1)) ;
			adminRecord.setAccount("a1111" + i);
			adminRecord.setPassword("123456");
			adminRecord.setCreateAt(LocalDateTime.now());
			adminRecord.setEnabled(UByte.valueOf(1));
			adminRecords.add(adminRecord);
		}
		int[] retVal = dslContext.batchUpdate(adminRecords).execute();
		System.out.println(Arrays.toString(retVal));

		* sql
			update `jooq`.`admin` set `jooq`.`admin`.`id` = ?, `jooq`.`admin`.`account` = ?, `jooq`.`admin`.`create_at` = ?, `jooq`.`admin`.`enabled` = ?, `jooq`.`admin`.`password` = ? where `jooq`.`admin`.`id` = ?
		* 根据主键值进行更新，如果主键值为null，那么条件也就是null