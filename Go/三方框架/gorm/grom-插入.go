--------------------
创建
--------------------
	# 通过 Create 存储记录，参数是指针
		role := model.Role{
			Name: "超级管理员",
		}
		result := gormDB.Create(&role)
		log.Printf("Error=%v\n", result.Error)		// Error=<nil>
		log.Printf("RowsAffected=%d\n", result.RowsAffected)	// RowsAffected=1
		log.Println(role.Id)	// 1

		* 从返回的result中获取异常/受影响行数信息
		* 从参数对象中获取到自增ID
	
	# 指定字段
		* 仅仅插入指定的字段，忽略其他字段
			result := gormDB.Select("Email", "Version").Create(user)
		
		* 插入没有指定的字段（忽略Omit指定的字段）
			result := gormDB.Omit("nick_name", "introduction").Create(user)
	
	# 批量插入
		* Create形参，使用切片
			var roles = []*model.Role {{
					Name: "role1",
				}, {
					Name: "role2",
				}, {
					Name: "role3",
				}, {
					Name: "role4",
				},
			}
			result := gormDB.Create(roles)
			log.Println(result)
			for _, r := range roles {
				log.Println(r.Id)
			}
	
		* 使用 CreateInBatches 创建时，还可以指定批量大小
			result := gormDB.CreateInBatches(roles, 10)
	

	# 使用Map作为数据，进行插入，可以是单个map[string] interface{}也可以是批量[]map[string] interface{}
		* 通过Model进行绑定数据表后执行，注意Map中的Key是对象的字段名称
			result := gormDB.Model(&model.UserConfig{}).Create([]map[string] interface{} {
				map[string] interface{} {
					"UserId": 7,
					"AllowFollow": false,
				},
				map[string] interface{} {
					"UserId": 6,
					"AllowFollow": true,
				},
			})
			log.Println(result) // nil, 0
		
		* 通过Table绑定数据表后执行，注意Map中的Key是表字段名称
			result := gormDB.Table("user_config").Create([]map[string] interface{} {
				map[string] interface{} {
					"user_id": 99,
					"allow_follow": true,
				},
				map[string] interface{} {
					"user_id": 88,
					"allow_follow": true,
				},
			})

		* 根据 map 创建记录时，association 不会被调用，且主键也不会自动填充
	
	# 默认值的问题
		* 零值，也是值，会被当做数据处理
				role := &model.Role{
					// Name: ""
				}
				result := gormDB.Create(role)	// 插入成功，数据库的name字段是空字符串: ""
				log.Println(result)
				log.Println(role.Id) 
		
		* 需要插入null，可以用指针类型来定义结构体的字段
			type Role struct {
				Id		uint	`gorm:"type:int(11) unsigned not null auto_increment comment 'ID';"`
				Name	*string	`gorm:"type:varchar(20) not null comment '角色名称'"`
			}

			//再次执行空对象插入 &model.Role{}，就会抛出异常： Column 'name' cannot be null
		
		* 需要插入null，可以使用sql包下的 NullXXX 类型
			type Role struct {
				Id		uint	`gorm:"type:int(11) unsigned not null auto_increment comment 'ID';"`
				Name	sql.NullString	`gorm:"type:varchar(20) not null comment '角色名称'"`
			}

			role := &model.Role{
				Name: sql.NullString{
					String: "",
					Valid:  false,		// 值是true，表示非null，值是false，表示null
				},
			}
			result := gormDB.Create(role)	// 执行异常，Column 'name' cannot be null
		
		* 使用default标签指定零值的默认值
			type Role struct {
				Id		uint	`gorm:"type:int(11) unsigned not null auto_increment comment 'ID';"`
				Name	string	`gorm:"default:默认角色; type:varchar(20) not null comment '角色名称'"`
			}
			// 执行空对象插入 &model.Role{}，默认的Name 就是"默认角色"

	

	# Upsert 及冲突
		* 在冲突时，什么都不做
			user := &model.User{
				Email:   "1000@qq.com",	// email 是唯一约束，并且已经存在这条记录了
				Version: 0,
			}
			result := gormDB.Clauses(clause.OnConflict{DoNothing: true}).Create(user)
			log.Println(result)	// nil, 受影响行数是0

