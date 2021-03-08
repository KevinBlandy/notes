---------------------
更新
---------------------
	# Save 会保存所有的字段，即使字段是零值
		var user = &model.User{}
		db.First(&user)
		user.NickName = "Foo"
		db.Save(user)

		// SELECT * FROM `user` ORDER BY `user`.`id` LIMIT 1
		// UPDATE `user` SET `nick_name`='Foo',`email`='10000@qq.com',`create_at`='2021-02-23 17:12:52',`update_at`='2021-02-23 17:12:52',`version`=0,`introduction`='1号用户' WHERE `id` = 1
	
	# Update 更新单个列
		db.Model(&model.User{}).Where("version > 0").Update("version", 0)
		// UPDATE `user` SET `version`=0 WHERE version > 0
	
	# Updates 更新多列
		var user = &model.User{
			NickName: "New Name",
			Version: 15,
		}
		db.Model(&model.User{}).Where("id = ?", user.Id).Updates(user)
		// UPDATE `user` SET `nick_name`='New Name',`version`=15 WHERE id = 0

		var user = &model.User{
			Id: 1,
			NickName: "New Name",
			Version: 15,
		}
		db.Model(&user).Updates(&user)
		// UPDATE `user` SET `nick_name`='New Name',`version`=15 WHERE `id` = 1

		* 该方法支持 struct 和 map[string]interface{} 参数。
		* 当使用 struct 更新时，默认情况下，GORM 只会更新非零值的字段

	# 更新选定字段
		db.Model(&model.User{}).Select("nick_name", "version").Where("id = ?", 1).Updates(map[string] interface{}{
			"NickName": "Bar",
			"Version": 155,
		})
		UPDATE `user` SET `nick_name`='New Name',`version`=15 WHERE `id` = 1

		* 先select，指定要更新的字段，orm就会从map/struct中取找这些字段值来进行更新
	
	# 全局更新
		* 更新的时候，如果没有给struct指定ID值，或者没有任何条件判断，那么会导致全表更新
		* 默认不允许全局更新，会抛出异常：ErrMissingWhereClause 

		* 如果需要全局更新那么可以必须：加一些条件，或者使用原生 SQL，或者启用 AllowGlobalUpdate 模式
	
	# 更新列表中使用SQL表达式：func Expr(expr string, args ...interface{}) clause.Expr 
		db.Model(&model.User{}).
			Where("id = ?", 1).
			Where("version = ?", 1).
			Updates(map[string]interface{}{
				"Version": gorm.Expr("Version + ?", 1),
		})
		// UPDATE `user` SET `version`=version + 1 WHERE id = 1 AND version = 1
	
	# 根据子查询进行更新
		subQuery := db.Model(&model.Role{}).Select("name").Where("id = ?", 1)
		db.Model(&model.User{}).Where("id = ?", 1).UpdateColumn("name", subQuery)
		// UPDATE `user` SET `name`=(SELECT `name` FROM `role` WHERE id = 1) WHERE id = 1


		* Update 的值，可以是另一个子查询
	
	# 跳过Hook
		* 还有俩方法，UpdateColumn、UpdateColumns 跟 Update/Updates 一样
		* 唯一不同的是这俩方法会跳过 Hook
	

	# 检查字段是否有变更

	# 在更新时修改值
