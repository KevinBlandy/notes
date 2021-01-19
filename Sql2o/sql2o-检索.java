-----------------------
select				   |
-----------------------
	# 基本的检索
		try(Connection connection = sql2o.open()){
			User user = connection.createQuery("SELECT * FROM `user`").executeAndFetchFirst(User.class);
		}

		* executeAndFetchFirst(Class<?> clazz) ,封装为单行结果集
		* 如果存在多行数据,那么只取第一行
	
	# 带参数的检索
		try(Connection connection = sql2o.open()){
			List<User> users = connection.createQuery("SELECT * FROM `user` WHERE `create_date` >= :start")
				.addParameter("start",LocalDateTime.of(2019,4,12,7,21,53))
				.executeAndFetch(User.class);

			System.out.println(JSON.toJSONString(users));
		}

		* executeAndFetch(Class<?> clazz),封装为多行多列结果集
		* 在SQL里面使用 ':name' 占位
		* 通过 addParameter() api设置填充参数
	
	# 绑定对象属性来检索
		try(Connection connection = sql2o.open()){

			User user = new User();
			user.setId(15);

			user = connection.createQuery("SELECT * FROM `user` WHERE `id` = :id")
					.addColumnMapping("create_date","createDate")
					.bind(user)
					.executeAndFetchFirst(User.class);

			System.out.println(user);
		}

		* 使用 bind(final Object pojo) 方法来帮到一个对象
		* :prop 就是对象的属性
	
	# 统计检索
		try(Connection connection = sql2o.open()){
			List<Integer> count = connection.createQuery("SELECT COUNT(1) FROM `user`")
						.executeScalarList(Integer.class);
			System.out.println(JSON.toJSONString(count));
		}

		* 通过 executeScalarList(Class<?> clazz),来封装统计的结果集合

		try(Connection connection = sql2o.open()){
			Long count = connection.createQuery("SELECT COUNT(1) FROM `user`")
						.executeScalar(Long.class);
			System.out.println(JSON.toJSONString(count));
		}

		* 通过 executeScalar(Class<?> clazz),来封装统计的单个结果
	
	# 使用Map封装多行多列结果集
		Table table = connection.createQuery("SELECT * FROM `user`")
					.executeAndFetchTable();

		// 获取到结果集List,List每个Map元素都是一行记录,每个Map元素的内容都是多列记录
		// Map的key是列名称,value是 sql 类型的数据对象
		List<Map<String,Object>> map = table.asList();

		// 每一行
		List<Row> rows = table.rows();

		// 每一列
		List<Column> columns = table.columns();

		// 检索的表名称
		String name = table.getName();		

		* Row 对象表示一行记录
			//　把一个行记录,转换为Map
			Map<String,Object> rowMap = row.asMap();

			//　根据列名读取数据为BigDecimal
			BigDecimal salary = row.getBigDecimal("salary");

			// 根据下标读取数据为Integer,从0开始
			Integer id = row.getInteger(1);

			// 根据列名读取数据为指定的数据对象类型
			// 会使用转换器:Converter<T>
			String string = row.getObject("",String.class);

			// 读取数据为Object类型
			Object result = row.getObject(1);

			* 还有很多: getXxxx(int index)/getXxxx(String name) 的重载方法,用于转换为不同的数据类型
		
		* Column 表示一列记录
			// 列所在的index,从0开始
			Integer index = column.getIndex();
			// 列名
			String name = column.getName();

			// 列数据类型,JDBCType 枚举实例的name()
			String type = column.getType();
	
	# 迭代检索
		* 对于数据记录量很大,一次性的全部读取到内存可能会造成不必要的麻烦
		* 可以采取这种迭代检索的方法,分批次的检索数据,分批次的处理

		try(Connection connection = sql2o.open()){

			// 一次性最多处理多条数据
			final int MAXSIZE = 1;

			// 结果集容器
			List<User> users = new ArrayList<>(MAXSIZE);

			ResultSetIterable<User> resultSetIterable = connection.createQuery("SELECT * FROM `user`")
					.executeAndFetchLazy(User.class);

			Iterator<User> iterator = resultSetIterable.iterator();
			
			while (iterator.hasNext()){

				User user = iterator.next();

				if(users.size() == MAXSIZE){
					//已经到达最大数据量,执行业务处理
					users.stream().forEach(System.out::println);

					// 处理完毕后,清空容器
					users.clear();
				}
				// 添加元素到容器
				users.add(user);
			}
		}

		* 使用 executeAndFetchLazy(Class<?> clazz) 获取结果集迭代器
	
	# 列名与对象属性映射
		* 如果结果集中,存在对象中不存在的属性名,那么会抛出异常
			org.sql2o.Sql2oException: Could not map xxxx to any property.

		try(Connection connection = sql2o.open()){
			User user = connection.createQuery("SELECT * FROM `user`")
					.addColumnMapping("create_date","createDate")
					.executeAndFetchFirst(User.class);
				System.out.println(user);
		}
		
		* 使用 addColumnMapping(String columnName, String propertyName) 来设置列名称与对象的属性名映射关系
		* 当然,也可以自己在SQL里面使用`AS` 别名的方式来处理属性映射
	
		* 可以通过 Sql2o 对象设置全局的映射关系,而不用每次检索都设置
			Map<String, String> colMaps = new HashMap<String,String>();
			colMaps.put("DUE_DATE", "dueDate");
			colMaps.put("DESC", "description");
			colMaps.put("E_MAIL", "email");
			colMaps.put("SHORT_DESC", "shortDescription");

			sql2o.setDefaultColumnMappings(colMaps);
	
