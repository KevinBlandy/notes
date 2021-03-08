-----------------
基本的查询
-----------------
	# First/Take/Last单个对象查询
		*  从数据库中检索单个对象，会添加了 LIMIT 1 条件，且没有找到记录时，它会返回 ErrRecordNotFound 错误
			var user = &model.User{}
			result := gormDB.First(user)
			if errors.Is(result.Error, gorm.ErrRecordNotFound) {
				log.Println("没有找到")
			} else {
				log.Println(user)
			}

		* First 根据ID排序，取第一条	ORDER BY ID LIMIT 1
		* Last 根据ID排序，取最后一条	LIMIT 1
		* Take 没有排序，取第一条		ORDER BY ID DESC LIMIT 1

		* First, Last方法将按主键排序查找第一/最后一条记录，只有在用struct查询或提供model时才有效（因为要通过反射读取列信息）
			db.First(&user)
			db.Model(&User{}).First(&result)

		* 如果当前model没有定义主键，将按第一个字段排序

		* 使用Model后，封装的结果，也可以用：map[string]interface{}{}
			rvar user = map[string]interface{}{}
			result := gormDB.Model(&model.User{}).First(&user) // key是表列名称

		* Take，可以使用table的方式。（因为Take不需要任何列信息）
			result := map[string]interface{}{}
			db.Table("users").Take(&result)
			
	
	# 根据主键ID检索
		* First，不指定检索列，默认根据ID检索，如果ID是数值，那么参数可以是字符串/数值
			db.First(&user, 10)
			db.First(&user, "10")
		
		* Find，不指定检索列，根据多个ID检索，也就是IN
			var users = make([]*model.User, 3, 3)	// 如果切片不够长，会自动扩容
			result := gormDB.Find(&users, []interface{} {1, 2, "4"}) 
			log.Println(result)
			for _, user := range users {
				log.Println(user)
			}
					
		* 如果ID是字符串的话，那么需要注意SQL注入的问题，可以通过指定ID列，预编译来操作
			db.First(&user, "id = ?", "1b74413f-f3b8-409f-ac47-e8c062e3472a")
			// SELECT * FROM users WHERE id = "1b74413f-f3b8-409f-ac47-e8c062e3472a";

		
	# 检索所有数据
		* Find 不写条件
			result := db.Find(&users)
			// SELECT * FROM users;
		
-----------------
条件查询
-----------------
	# 字符串条件，使用字符串构造，条件中的列，是表列名称
		* = 
			var users = make([]*model.User, 1, 1)
			result := gormDB.Where("nick_name = ? AND email = ?", "vin", "10000@qq.com").Find(&users)
		
		* !=
			Where("name <> ?", "jinzhu")

		* IN 
			Where("name IN ?", []string{"jinzhu", "jinzhu 2"})
		
		* LIKE
			Where("name LIKE ?", "%jin%")
		
		* BETWEEN
			Where("created_at BETWEEN ? AND ?", lastWeek, today)


		* 使用 Find 检索多条记录，封装为切片
		* 使用 First 检索第单条记录，封装为单个对象

		* 需要封装为 []map[string] interface{}，那么可以使用 Table 绑定表
			var users = make([]map[string] interface{}, 1, 1)
			result := gormDB.Table("user").Where("nick_name = ? AND email = ?", "vin", "10000@qq.com").Find(&users)
			log.Println(result)

	# 使用对象/Map作为条件
		* 使用struct作为条件
			param := &model.User{
				NickName: "vin",
				Email: "10000@qq.com",
			}
			result := gormDB.Where(&param).First(param)

			* struct作为条件，只会用非0值作为条件，默认的0, '', false 不会参与条件运算
				db.Where(&User{Name: "jinzhu", Age: 0}).Find(&users) // Age 是白写
		
		* 仅仅使用结构体中的某些字段作为条件
			param := &model.User{
				NickName: "name",
				Email: "abs@qq.com",
			}
			result := gormDB.Where(param, "NickName").First(param) // 仅仅使用param中的"NickName"作为条件检索
			
		
		* 使用Map作为条件
			param := &model.User{}
 			result := gormDB.Where(map[string] interface{} {"nick_name": "vin"}).First(param)
	
	# 内联条件
		* 使用字符串条件
			var users []*model.User
			result := gormDB.Find(&users, "id > ?", 0)	// WHERE `id` > 0
		
		* 使用结构体
			var users []*model.User
			result := gormDB.Find(&users, &model.User{NickName: "vin", Email: "100100@qq.com"}) // WHERE `nick_name` ='vin' AND `email` = '100100@qq.com'
		
		* 使用Map
			var users []*model.User
			result := gormDB.Find(&users, map[string]interface{} {"nick_name": "vin"})	// WHERE `nick_name` ='vin' 
	
	# Not 条件
		result := gormDB.Where("nick_name = ?", "vin").Not("id = ?", 2).Find(&users) // WHERE nick_name = 'vin' AND NOT id = 2
	
	# Or 条件
		Where("nick_name = ?", "vin").Or("id = ?", 2).Find(&users) // Where("nick_name = ?", "vin").Or("id = ?", 2).Find(&users)

	# 组合
		var users []*model.User
		result := gormDB.Where("nick_name = ?", "vin").
				Or(map[string]interface{}{"id": 1, "email": "10000@qq.com"}).
				Not(&model.User{Version: 1, Introduction: "55"}).
				Find(&users)
				//  WHERE nick_name = 'vin' OR (`email` = '10000@qq.com' AND `id` = 1) AND (`user`.`version` <> 1 AND `user`.`introduction` <> '55')
		
	
-----------------
投影查询
-----------------
	# 指定列
		db.Select("UPPER(`nick_name`)", "email").Find(&users) //  SELECT UPPER(`nick_name`),`email` FROM `user`
	
	# 通过切片指定列
		db.Select([]string{"`nick_name`", "age"}).Find(&users)	//  SELECT `nick_name`,age FROM `user`
	
	# 绑定Table后，Select
		var users []*model.User
		db.Table("user").Select([]string{"UPPER(`nick_name`)", "email"}).Find(&users) //  SELECT UPPER(`nick_name`),`email` FROM `user`

-----------------
排序
-----------------
	# Order进行排序
		db.Order("`nick_name DESC`, `email` ASC").Order("`id`").Find(&users)  // ORDER BY `nick_name DESC`, `email` ASC,`id`
	
	# Clauses进行排序
		// TODO

-----------------
分页
-----------------
	# 使用 Offset/Limit 进行分页
		var users []*model.User
		db.Offset(1).Limit(2).Find(&users) //  LIMIT 2 OFFSET 1
	
		* Offset/Limit的参数值如果是-1，则会忽略这个方法
	

-----------------
聚合
-----------------
	# Group & Having 进行聚合检索

	# 返回结果为 (sql.Rows, err) / (sql.Row)
		var users []*model.User
		db.Table("`user`").Group("version").Having("`version` < ?", 100).Select("SUM(`id`)", "COUNT(`id`)").Rows()
		// SELECT SUM(`id`),COUNT(`id`) FROM `user` GROUP BY `version` HAVING `version` < 100
	
	# 封装结果为结构体
		type Result struct {
			Sum uint
			Count uint
		}
		var result = &Result{}
		db.Model(&model.User{}).Select("SUM(`id`) AS `sum`", "COUNT(`id`) AS `count`", `version`).Group("version").Having("`version` < ?", 100).Find(&result)
	
-----------------
去重
-----------------
	# Distinct
		db.Distinct("name", "age").Order("name, age desc").Find(&results)

	
-----------------
链接
-----------------
	# Joins
		var r = map[string]interface{}{}
		result := db.Model(&model.User{}).Select("`user`.`nick_name`", "`user_config`.`allow_follow`").
				Joins("INNER JOIN `user_config` ON `user_config`.`user_id` = `user`.`id`").
				Where("`user`.`id` = ?", 1).
				Scan(&r)
		log.Println(result)
	

	
-----------------
Scan
-----------------
	# 类似于Find，用于封装数据
		type Result struct {
		  Name string
		  Age  int
		}

		var result Result
		db.Table("users").Select("name", "age").Where("name = ?", "Antonio").Scan(&result)

		// Raw SQL
		db.Raw("SELECT name, age FROM users WHERE name = ?", "Antonio").Scan(&result)

