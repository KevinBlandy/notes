-----------------------------
删除
-----------------------------
	# DSLContext删除
		int ret = dslContext.delete(Tables.ADMIN)
			.where(Tables.ADMIN.ID.ge(UInteger.valueOf(1)))
			.execute()
			;
		System.out.println(ret);

		* sql
			 delete from `jooq`.`admin` where `jooq`.`admin`.`id` >= 1
	

	# Record删除
		AdminRecord adminRecord = dslContext.newRecord(Tables.ADMIN);

		adminRecord.setId(UInteger.valueOf(15));
		adminRecord.setNickame("foo"); // 没意义的字段

		int ret = adminRecord.delete();
		System.out.println(ret);
		
		* sql
			 delete from `jooq`.`admin` where `jooq`.`admin`.`id` = 15
		* 根据主键删除，其他字段设置没有意义
		* 如果ID没设置的话，那么条件就是 id IS NULL
	
	# 批量删除
		List<AdminRecord> adminRecords = new ArrayList<>();
		for (int i = 0; i < 5; i ++){
			AdminRecord adminRecord = new AdminRecord();
			adminRecord.setId(UInteger.valueOf(i + 1)) ;
		}
		int[] retVal = dslContext.batchDelete(adminRecords).execute();
		System.out.println(Arrays.toString(retVal));
	
