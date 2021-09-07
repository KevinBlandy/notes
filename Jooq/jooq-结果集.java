--------------------------------------
结果集
--------------------------------------
	# 几个常用的方法
		fetch();
			* 返回 List
		fetchSet();
			* 返回 Set

		fetchArray();
			* 返回数组

		fetchAny();
			* 读取多条或0条记录，如果记录有多条，只读取第一条

		fetchOne();
			* 读取单条或者0记录，如果记录超过一条会报错。
		
		fetchSingle()
			* 必须且只有一条记录，没有或者超出都会异常
		
		fetchMap()
			* 返回 Map
			* 以表字段值为key，返回一个 K:V 的Map对象
		
		fetchGroups()
			* 返回一个分组Map
			* 以表字段值为Key，返回一个 K:List<V> 的Map对象
	
	# 异常
		org.jooq.exception.TooManyRowsException
			* 多条数据时抛出异常
		
		org.jooq.exception.NoDataFoundException
			* 无数据时抛出异常


--------------------------------------
常用的结果集封装
--------------------------------------
	# Result 使用 into() 传递 Pojo的class可以把整列转行为对象
		dslContext.selectFrom(Tables.ADMIN).where(Tables.ADMIN.ID.eq(UInteger.valueOf(1))).fetch().into(Admin.class);
	
	
	# 使用对象的Record 转行为单行结果集 Record 转换为Pojo，对象的 Record 都有


	# 多表联合检索
		* 如果是结果集是把数据平铺的，那么可以直接 into 为对象，检索字段通过 as 和对象匹配即可

			dslContext.select(adminTable.ID, adminRoleTable.ROLE_ID.as("roleId")).from(adminTable)
				.innerJoin(adminRoleTable).on(adminRoleTable.ADMIN_ID.eq(adminTable.ID))
				.fetch(record -> record.into(AdminDTO.class)) // AdminDTO 继承 Admin，新增了一个 private roleId 字段，平铺的，可以直接into
				.stream()
				.forEach(i -> {
					System.out.println("adminId=" + i.getId() + ", roleId=" + i.getRoleId());
				});

		* 如果结果数据不是平铺的，是有层次的，那么 Record 的 into 方法可以指定为 Table，那么返回的就是 Table的 Record 实现
		* 然后再用 TableRecord into 到指定的对象
		* 再手动组装

			dslContext.select(adminTable.ID, adminRoleTable.ROLE_ID.as("roleId")).from(adminTable)
				.innerJoin(adminRoleTable).on(adminRoleTable.ADMIN_ID.eq(adminTable.ID))
				.fetch(record -> {
					// 先把record封装到AdminRecord，再把AdminRecord封装到最终对象
					AdminDTO adminDTO = record.into(adminTable).into(AdminDTO.class);
					// 先把record封装到AdminRoleRecord，再读取需要的数据，封装到最终对象
					adminDTO.setRoleId(record.into(adminRoleTable).getRoleId().intValue());
					return adminDTO;
				})
				.stream()
				.forEach(i -> {
					System.out.println("adminId=" + i.getId() + ", roleId=" + i.getRoleId());
				});
	