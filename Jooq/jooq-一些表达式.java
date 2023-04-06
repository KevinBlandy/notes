-----------------------------
һЩ���ʽ
-----------------------------
	# �����ֻ������м�4λ
		Field<String> phone = concat(substring(ADMIN.ACCOUNT, 1, 3), inline("****"), substring(ADMIN.ACCOUNT, 8)).as("phone");
		Field<String> phone = overlay(ADMIN.ACCOUNT, "****", 4).as("phone");
	
	# MYSQL�� FIND_IN_SET
		field("FIND_IN_SET({0},  {1})", "1", "1,2,3,4,5,").cast(Boolean.class)
		// CAST(FIND_IN_SET('1',  '1,2,3,4,5,') AS unsigned)
	
	# ���ڸ�ʽ��
		field("date_format({0}, {1})", field("NOW()"), "%d/%m/%Y").cast(String.class).as("NOW")
		// CAST(date_format(NOW(), '%d/%m/%Y') AS char) AS `NOW`
		
	
	# IN ��ѯ������ IN ������������
		List<Long> ids = List.of(108561785421955072L, 108561954930556928L, 108563064705646592L);

		var table = Tables.SYS_MENU;

		List<SysMenu> ret = this.dslContext.select()
				.from(table)
				.where(table.ID.in(ids))  // IN ����
				.orderBy(table.ID.sortAsc(ids)) // ��������� IN �Ľ����������
				.fetch()
				.into(SysMenu.class)
				;
		ret.stream().forEach(System.out::println);


	# �����ݿ��������ȡN����¼
		var ret = this.dslContext.select()
			.from(Tables.SYS_MENU)
			.orderBy(rand())
			.limit(5)
			.fetch()
			;
		ret.stream().forEach(System.out::println);
	
