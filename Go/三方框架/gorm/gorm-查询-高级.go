-----------------------
智能选择字段
-----------------------
	# 通过结构体定义投影查询
		type SimpleUser struct {
			NickName string
			Email string
		}
		var result = &SimpleUser{}
		db.Model(&model.User{}).Find(result, "id = ?", 1) //  SELECT `user`.`nick_name`,`user`.`email` FROM `user` WHERE id = 1
	
-----------------------
锁
-----------------------
	# 排他锁
		db.Model(&model.User{}).Clauses(clause.Locking{
			Strength: "UPDATE",
		}).Find(result)		// FOR UPDATE
	
	# 读锁
		db.Model(&model.User{}).Clauses(clause.Locking{
			Strength: "SHARE",
			Table: clause.Table{Name: clause.CurrentTable},
		}).Find(result, "id = ?", 1)		// LOCK IN SHARE MODE

-----------------------
子查询
-----------------------
	# 条件中的子查询
		var result []*model.User
		subQuery := db.Model(&model.User{}).Select("AVG(id)")
		db.Model(&model.User{}).Where("id >= (?)", subQuery).Find(&result)
		//  SELECT * FROM `user` WHERE id >= (SELECT AVG(id) FROM `user`)

		* where参数填充，判断的条件对象，可以是另一个子查询
	
	# 临时表子查询
		var result []*model.User
		db.Table("(?) AS `u`", db.Model(&model.User{}))
				.Select("`u`.`id`", "`u`.`nick_name`")
				.Where("`u`.`id` >= ?", 1).Find(&result)
		// SELECT `u`.`id`,`u.`nick_name` FROM (SELECT * FROM `user`) AS `u` WHERE `u`.`id` >= 1
		
	
		* Table的参数填充，可以是另一个子查询

	# 列子查询
		var result []*model.User
		db.Table("`user` AS `u`")
			.Select("`u`.`id`", "`u`.`nick_name`", "(SELECT `allow_follow` FROM `user_config` WHERE `user_id` = `u`.`id`) AS `allow_follow`")
			.Find(&result)
			// SELECT `u`.`id`,`u`.`nick_name`,(SELECT `allow_follow` FROM `user_config` WHERE `user_id` = `u`.`id`) AS `allow_follow` FROM `user` AS `u`

		* 可以直接在Select，用字符串写另一个结果列的SQL检索语句

-----------------------
条件分组
-----------------------
	# Where 条件可以包含另一个Where
		var result []*model.User
		db.Model(&model.User{}).
			Where(
				db.Where("id > ?", 1).Or("nick_name = ?", "Vin"),
			).
			Where(
				db.Where("version <> ?", "5"),
			).
			Or(
				db.Where("create_at != NOW()").Or("update_at > NOW()"),
			).
		Find(&result)
		// SELECT * FROM `user` WHERE (id > 1 OR nick_name = 'Vin') AND version <> '5' OR (create_at != NOW() OR update_at > NOW())

		* 本质上，嵌套一个Where，就相当于添加了一个分组 () 条件
	

-----------------------
命名参数
-----------------------
	# 支持 sql.NamedArg 和 map[string]interface{}{} 形式的命名参数
		db.Model(&model.User{}).Find(&result, "id > @id", sql.NamedArg{Name: "id", Value: 0})
		// SELECT * FROM `user` WHERE id > 0

		db.Model(&model.User{}).Find(&result, "id > @id OR nick_name = @name", map[string]interface{} {
			"id": 1,
			"name": "Vin",
		})
		// SELECT * FROM `user` WHERE id > 1 OR nick_name = 'Vin'
	
	# 支持使用结构体的字段名称作为命名参数
		var user = &model.User{}
		db.Model(&model.User{}).Where("id = @Id AND version = @V", &struct{
			Id int		// 字段名称Id
			V int		// 字段名称V
		}{1, 2}).First(user)

		// SELECT * FROM `user` WHERE id = 1 AND version = 2 ORDER BY `user`.`id` LIMIT 1
	

-----------------------
FirstOrInit
-----------------------
	# 如果根据条件没查询到，就返回自定义的结果
		param := &model.User{NickName: "Foo"}
		db.Model(&model.User{}).FirstOrInit(param, &model.User{
			NickName: "Foo不存在",
		})

		// SELECT * FROM `user` WHERE `user`.`nick_name` = 'Foo不存在' ORDER BY `user`.`id` LIMIT 1

		// param的值已经变成了自定义的结果值
		log.Println(param) // &{0 Foo不存在  <nil> <nil> 0 }

		* 仅支持 sturct 和 map 条件

-----------------------
FirstOrCreate
-----------------------


-----------------------
优化器、索引提示
-----------------------

-----------------------
迭代
-----------------------
	# 通过 Rows 获取到 sql/Rows 进行迭代，通过 ScanRows 进行封装
		rows, err := db.Model(&model.User{}).Rows()
		if err != nil {
			log.Fatal(err)
		}
		for rows.Next() {
			// var user = &model.User{}
			var result = map[string] interface{}{}
			db.ScanRows(rows, &result)
			log.Println(result)
		}
		
		* 可以用结构体或者是 map[string] interface{}{} 来封装数据
	
-----------------------
FindInBatches
-----------------------
	# 用于批量查询并处理记录
		result := db.Model(&model.User{}).FindInBatches(&results, 2, 	// 指定结果集容器，和一次最大检索数量
					func(tx *gorm.DB, batch int) error {

			// 遍历这批数据
			for _, result := range results {
				log.Println(result)
			}

			// 这一批数据有多少
			log.Println(tx.RowsAffected)

			// 这是处理的第几批数据了
			log.Println(batch)

			// 如果返回错误会终止后续批量操作
			return nil
		})
		log.Println(result.Error) 		// 批量操作返回的异常
		log.Println(result.RowsAffected) // 整个批量操作影响的记录数


		* 这个批量检索，是通过条件，ID大于来完成的: `user`.`id` > 1 ORDER BY `user`.`id` LIMIT 2 
	

-----------------------
Pluck
-----------------------
	# 单列结果集封装
		var ages []int64
		db.Model(&users).Pluck("age", &ages)

		var names []string
		db.Model(&User{}).Pluck("name", &names)

		db.Table("deleted_users").Pluck("name", &names)

		// Distinct Pluck
		db.Model(&User{}).Distinct().Pluck("Name", &names)
		// SELECT DISTINCT `name` FROM `users`

-----------------------
Scopes
-----------------------
	# 指定常用的查询，可以在调用方法时引用这些查询
		func AmountGreaterThan1000(db *gorm.DB) *gorm.DB {
		  return db.Where("amount > ?", 1000)
		}

		func PaidWithCreditCard(db *gorm.DB) *gorm.DB {
		  return db.Where("pay_mode_sign = ?", "C")
		}

		func PaidWithCod(db *gorm.DB) *gorm.DB {
		  return db.Where("pay_mode_sign = ?", "C")
		}

		func OrderStatus(status []string) func (db *gorm.DB) *gorm.DB {
		  return func (db *gorm.DB) *gorm.DB {
			return db.Where("status IN (?)", status)
		  }
		}

		db.Scopes(AmountGreaterThan1000, PaidWithCreditCard).Find(&orders)
		// 查找所有金额大于 1000 的信用卡订单

		db.Scopes(AmountGreaterThan1000, PaidWithCod).Find(&orders)
		// 查找所有金额大于 1000 的 COD 订单

		db.Scopes(AmountGreaterThan1000, OrderStatus([]string{"paid", "shipped"})).Find(&orders)
		// 查找所有金额大于1000 的已付款或已发货订单
		
		* Scopes 接收一个方法，返回值要么是 *gorm.DB，要么是 func (db *gorm.DB) *gorm.DB
			func (db *gorm.DB) *gorm.DB
			func (any) func (db *gorm.DB) *gorm.DB

----------------
Count
----------------
	# 获取总记录数量
		var count int64
		db.Model(&model.User{}).Where("id > ?", 0).Count(&count)
		log.Println(count)
		// SELECT count(1) FROM `user` WHERE id > 0

		* Count 形参，接收的是 int64 的指针
	
