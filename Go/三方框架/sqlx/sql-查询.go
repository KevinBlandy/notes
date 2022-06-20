--------------------------
查询
--------------------------
	# 对象的绑定
		* 只会绑定到导出字段
		* 默认的列和对象字段匹配规则是: 字段名称小写后与列名称匹配
		* 可以通过 db.MapperFunc 来修改这个规则
		* 这个影响到StructScan,SliceScan, MapScan 方法
		* 也可以通过 '`db' 注解来指定列名称
			type Place struct {
				Country       string
				City          sql.NullString
				TelephoneCode int `db:"telcode"`
			}
			 
			rows, err := db.Queryx("SELECT * FROM place")
			for rows.Next() {
				var p Place
				err = rows.StructScan(&p)
			}
	
	
	# IN 查询
		type User struct {
			Id   int
			Name string
		}

		// 构造带有 IN 的查询。默认使用 ? 作为占位符
		query, args, err := sqlx.In("SELECT `id`, `name` FROM `user` WHERE `create_at` < ? and `name` in (?) and `id` IN (?) and `enabled` = ? ", time.Now(), []string{"JPA", "MYBATIS"}, []int{15, 16}, true)
		if err != nil {
			log.Fatalf(err.Error())
		}
		log.Println(query, args, err) 

		// 重新绑定为当前数据库支持的占位类型（有些数据库不支持?）
		query = db.Rebind(query)

		// 执行检索，获取到返回值
		var ret []User
		err = db.Select(&ret, query, args...)
		if err != nil {
			log.Fatalln(err.Error())
		}
		log.Println(ret)
	
	# Named 查询
		* 解析 name 查询为 ? 查询
			var params = map[string]interface{}{
				"createAt": time.Now(),
				"names":    []string{"JPA", "MYBATIS"},
				"ids":      []int{15, 16},
				"enabled":  true,
			}

			query, args, err := sqlx.Named("SELECT `id`, `name` FROM `user` WHERE `create_at` < :createAt and `name` in (:names) and `id` IN (:ids) and `enabled` = :enabled ", params)
			log.Println(query, args, err)
			// SELECT `id`, `name` FROM `user` WHERE `create_at` < ? and `name` in (?) and `id` IN (?) and `enabled` = ?  [2022-06-20 12:45:52.0977691 +0800 CST m=+0.008774201 [JPA MYBATIS] [15 16] true] <nil>
		
		* 也可以把参数换成结构体
			var params = struct {
				CreateAt time.Time `db:"createAt"`
				Names    []string  `db:"names"`
				Ids      []int     `db:"ids"`
				Enabled  bool      `db:"enabled"`
			}{
				CreateAt: time.Now(),
				Names:    []string{"JPA", "MYBATIS"},
				Ids:      []int{15, 16},
				Enabled:  true,
			}
		
