-----------------------------
ɾ��
-----------------------------
	# DSLContextɾ��
		int ret = dslContext.delete(Tables.ADMIN)
			.where(Tables.ADMIN.ID.ge(UInteger.valueOf(1)))
			.execute()
			;
		System.out.println(ret);

		* sql
			 delete from `jooq`.`admin` where `jooq`.`admin`.`id` >= 1
	

	# Recordɾ��
		AdminRecord adminRecord = dslContext.newRecord(Tables.ADMIN);

		adminRecord.setId(UInteger.valueOf(15));
		adminRecord.setNickame("foo"); // û������ֶ�

		int ret = adminRecord.delete();
		System.out.println(ret);
		
		* sql
			 delete from `jooq`.`admin` where `jooq`.`admin`.`id` = 15
		* ��������ɾ���������ֶ�����û������
		* ���IDû���õĻ�����ô�������� id IS NULL
	
	# ����ɾ��
		List<AdminRecord> adminRecords = new ArrayList<>();
		for (int i = 0; i < 5; i ++){
			AdminRecord adminRecord = new AdminRecord();
			adminRecord.setId(UInteger.valueOf(i + 1)) ;
		}
		int[] retVal = dslContext.batchDelete(adminRecords).execute();
		System.out.println(Arrays.toString(retVal));
	
