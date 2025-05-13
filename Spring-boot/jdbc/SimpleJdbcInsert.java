------------------------
SimpleJdbcInsert
------------
	# 快捷的 INSERT 实现

	# 插入 Bean

		// 创建 SimpleJdbcInsert 指定表名称和自增 ID 列名
		SimpleJdbcInsert insert  = new SimpleJdbcInsert(this.jdbcTemplate)
				.withTableName("t_area")		// 表名称
				.usingGeneratedKeyColumns("id")	// 自增列
				;
		
		// 创建实体对象
		Area area = new Area();
		area.setId(null);
		area.setCode("undefine");
		area.setDepth(9999);
		area.setFirstLetter("U");
		area.setParentId(0L);
		area.setStatus(1);
		area.setTitle("Undefined");
		
		// 创建 BeanPropertySqlParameterSource，用于把实体对象映射到数据列
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource (area);
		
		// 执行插入，返回受影响的行数
		//	int affectedRows = insert.execute(source);
		
		// 执行插入，返回自增 ID
		Number inrementId = insert.executeAndReturnKey(source);
		
		System.out.println(inrementId.longValue());

		* 会自动把驼峰命名的 Bean 属性转换为下划线
	
	# 插入 Map

		// 创建 SimpleJdbcInsert 指定表名称和自增 ID 列名
		SimpleJdbcInsert insert  = new SimpleJdbcInsert(this.jdbcTemplate)
			.withTableName("t_area")		// 表名称
			.usingGeneratedKeyColumns("id")	// 自增列
			;
	
		/*
		 * 通过 Map 指定列名和值 
		 */
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("code", "undefine");
		parameters.put("depth", 9999);
		parameters.put("first_letter", "F");
		parameters.put("parent_id", 0);
		parameters.put("status", 1);
		parameters.put("title", "Undefined");
		
		// 执行插入，返回受影响的行数
		//	int affectedRows = insert.execute(parameters);
		
		// 执行插入，返回自增 ID
		Number inrementId = insert.executeAndReturnKey(parameters);
		
		System.out.println(inrementId.longValue());


		* 注意 key 必须和列名一致的命名风格
	

		