-----------------------------
≤È—Ø
-----------------------------
	# ≤È—Ø Record
		Result<AdminRecord> result = dslContext.selectFrom(Tables.ADMIN).where(Tables.ADMIN.ID.eq(UInteger.valueOf(1))).fetch();
		for (AdminRecord adminRecord : result) {
			Admin admin = adminRecord.into(Admin.class);
		}
	

	# exist ≤È—Ø
		 boolean exists = dslContext.fetchExists(
                            dslContext.selectOne().from(adminRole)
                                .where(adminRole.ADMIN_ID.eq(UInteger.valueOf(1))));
		

		* sql
			SELECT
				EXISTS ( SELECT 1 AS `one` FROM `jooq`.`admin_role` WHERE `jooq`.`admin_role`.`admin_id` = 1 )