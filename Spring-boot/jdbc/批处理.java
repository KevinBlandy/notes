-----------------
JdbcTemplate
-----------------
	
	# 批量插入/修改

		List<Area> batchList = List.of(Area.builder().code(ID.uuid()).depth(1).firstLetter("f").parentId(0L).status(1).title("b1").build(),
						Area.builder().code(ID.uuid()).depth(1).firstLetter("f").parentId(0L).status(1).title("b1").build(),
						Area.builder().code(ID.uuid()).depth(1).firstLetter("f").parentId(0L).status(1).title("b1").build(),
						Area.builder().code(ID.uuid()).depth(1).firstLetter("f").parentId(0L).status(1).title("b1").build()
				);
		

		// 匿名实现 BatchPreparedStatementSetter ，以填充每个语句的参数
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				
				// 批中的元素
				var area = batchList.get(i);
				
				// 填充当前 statement
				ps.setLong(1, area.getParentId());
				ps.setString(2, area.getTitle());
				ps.setString(3, area.getCode());
				ps.setString(4, area.getFirstLetter());
				ps.setInt(5, area.getDepth());
				ps.setInt(6, area.getStatus());
			}

			// 批大小
			@Override
			public int getBatchSize() {
				return batchList.size();
			}
			
		};
		
		// 返回受到影响的行数
		int[] affected = this.jdbcTemplate.batchUpdate("""
				INSERT INTO t_area
					(id, parent_id, title, code, first_letter, `depth`, `status`)
				VALUES
					(NULL, ?, ?, ?, ?, ?, ?);
				""", setter);
		
		System.out.println(affected);


----------------------------------
NamedParameterJdbcTemplate 
----------------------------------
	# 批量更新/删除

		// 批数据
		List<Area> batchList = List.of(
						Area.builder().code(ID.uuid()).depth(1).firstLetter("f").parentId(0L).status(1).title("b1").build(),
						Area.builder().code(ID.uuid()).depth(1).firstLetter("f").parentId(0L).status(1).title("b1").build(),
						Area.builder().code(ID.uuid()).depth(1).firstLetter("f").parentId(0L).status(1).title("b1").build(),
						Area.builder().code(ID.uuid()).depth(1).firstLetter("f").parentId(0L).status(1).title("b1").build()
				);
		
		// 转换批数据为 SqlParameterSource[]
		SqlParameterSource[] source = SqlParameterSourceUtils.createBatch(batchList);
		
		// 执行插入/修改
		int[] affected = this.namedParameterJdbcTemplate.batchUpdate("""
				INSERT INTO t_area
				(id, parent_id, title, code, first_letter, `depth`, `status`)
			VALUES
				(NULL, :parentId, :title, :code, :firstLetter, :depth, :status);
			""", source);
		
		System.out.println(Arrays.toString(affected));

		* 通过 SqlParameterSourceUtils 创建 SqlParameterSource[]，更编解
		* 注意， SQL 中的参数名称要使用命名变量，且名称要和 Bean 属性一致

-----------------
SimpleJdbcInsert
-----------------
	# 批量插入

		// 批数据
		List<Area> batchList = List.of(Area.builder().code(ID.uuid()).depth(1).firstLetter("f").parentId(0L).status(1).title("b1").build(),
						Area.builder().code(ID.uuid()).depth(1).firstLetter("f").parentId(0L).status(1).title("b1").build(),
						Area.builder().code(ID.uuid()).depth(1).firstLetter("f").parentId(0L).status(1).title("b1").build(),
						Area.builder().code(ID.uuid()).depth(1).firstLetter("f").parentId(0L).status(1).title("b1").build()
				);
		
		// 创建 SimpleJdbcInsert 指定表名称和自增 ID 列名
		SimpleJdbcInsert insert  = new SimpleJdbcInsert(this.jdbcTemplate)
			.withTableName("t_area")		// 表名称
			.usingGeneratedKeyColumns("id")	// 自增列
			;
		
		// 把每个数据，使用 BeanPropertySqlParameterSource 封装后，转换为数组
		// 返回受到影响的行数
		int[] affected = insert.executeBatch(batchList.stream().map(BeanPropertySqlParameterSource::new).toArray(BeanPropertySqlParameterSource[]::new));
		
		System.out.println(Arrays.toString(affected));
	
	# 也可以使用 SqlParameterSourceUtils 快速创建

		// 批数据
		List<Area> batchList = List.of(
						Area.builder().code(ID.uuid()).depth(1).firstLetter("f").parentId(0L).status(1).title("b1").build(),
						Area.builder().code(ID.uuid()).depth(1).firstLetter("f").parentId(0L).status(1).title("b1").build(),
						Area.builder().code(ID.uuid()).depth(1).firstLetter("f").parentId(0L).status(1).title("b1").build(),
						Area.builder().code(ID.uuid()).depth(1).firstLetter("f").parentId(0L).status(1).title("b1").build()
				);
		
		// 转换批数据为 SqlParameterSource[]
		SqlParameterSource[] source = SqlParameterSourceUtils.createBatch(batchList);
		
		// 创建 SimpleJdbcInsert 指定表名称和自增 ID 列名
		SimpleJdbcInsert insert  = new SimpleJdbcInsert(this.jdbcTemplate)
				.withTableName("t_area")		// 表名称
				.usingGeneratedKeyColumns("id")	// 自增列
				;
		
		// 批量插入
		int[] affected = insert.executeBatch(source);
		
		System.out.println(Arrays.toString(affected));