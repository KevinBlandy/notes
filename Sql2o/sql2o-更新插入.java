-----------------------
insert/update		   |
-----------------------
	# 插入记录
		try(Connection connection = sql2o.open()){
			Connection executeUpdate = connection.createQuery("INSERT INTO `user`(`id`,`name`,`create_date`) VALUES(NULL, :name, :createDate);")
					.addParameter("name", "KevinBlandy")
					.addParameter("createDate", LocalDateTime.now())
					.executeUpdate();

			// 返回收到的影响行数
			int result = executeUpdate.getResult();
			// 返回自增长的key
			Object key = executeUpdate.getKey();
			Integer integerKey = executeUpdate.getKey(Integer.class);

			Object[] keys = executeUpdate.getKeys();
			List<Long> longKeys = executeUpdate.getKeys(Long.class);

			System.out.println(result);
			System.out.println(key);
		}

		* 通过 addParameter(String name, final String value) 添加占位符参数
		* addParameter() 有 N 多重载方法,对应不同的数据类型

		* 使用 executeUpdate() 执行插入/修改
		
		* 通过 getResult(); 获取到受影响的函数
		* 通过 getKey(); 获取到自增长字段的值,也可以使用 getKey(Class<?> clzz); 来转换为需要的数据类型

		* 如果存在多个自增长字段,那么可以使用:Object[] getKeys() / List<Long> getKeys(Long.class); 来获取

	
	# 修改记录
		try(Connection connection = sql2o.open()){
			Connection executeUpdate = connection.createQuery("UPDATE `user` SET `name` = :name WHERE `id` = :id")
					.addParameter("id",13)
					.addParameter("name", "KevinBlandy1")
					.executeUpdate();

			// 返回收到的影响行数
			int result = executeUpdate.getResult();
			System.out.println(result);
		}

		* 使用 executeUpdate() 执行插入/修改
		* 通过 getResult(); 获取到受影响的函数
	
	# 绑定对象完成占位符的映射关系
		try(Connection connection = sql2o.open()){
			
			// 创建对象
			User user = new User();
			user.setId(null);
			user.setName("SpringBoot中文社区");
			user.setCreateDate(LocalDateTime.now());

			Connection executeUpdate = connection.createQuery("INSERT INTO `user`(`id`, `name`, `create_date`) VALUES(:id, :name, :createDate)")
					// 映射对象的属性到SQL中的占位符
					.bind(user)
					.executeUpdate();

			// 返回收到的影响行数
			int result = executeUpdate.getResult();

			// 自增长的id
			Integer id = executeUpdate.getKey(Integer.class);

			System.out.println("result = " + result + ", id = " + id);
		}

		* :prop 是对象的属性
	

-----------------------
批量处理			   |
-----------------------
	# 批量插入
		try(Connection connection = sql2o.open()){

			Query query = connection.createQuery("INSERT  INTO `user`(`id`, `name`, `create_Date`) VALUES(:id, :name, :createDate);");

			for(int x = 0 ;x < 100 ; x++){

				// 执行数据绑定
				query.addParameter("id",x);
				query.addParameter("name","name_" + x);
				query.addParameter("createDate",LocalDateTime.now());

				// 添加到批处理
				query.addToBatch();
			}

			// 执行批量处理
			Connection executeBatch = query.executeBatch();

			// 获取到批量处理的结果
			int[] result = executeBatch.getBatchResult();

			System.out.println(Arrays.toString(result));
		}

		* addToBatch() 添加到批处理
		* executeBatch() 执行批处理
		* getBatchResult() 获取到批处理结果
	
		
		* 优点：
			SQL语句预编译了
			对于同一种类型的SQL语句,不用编写很多条

		* 缺点：
			不能发送不同类型的SQL语句