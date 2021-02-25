-------------------
删除
-------------------
	# 删除时候要指定主键或者手动通过Where添加条件，不然会全表删除
		* Delete的model，只会根据ID删除
			db.Delete(&model.User{Id: 58, Version: 22})	// Version白给
			// DELETE FROM `user` WHERE `user`.`id` = 58  

		* 自己添加条件
			db.Where("id = ?", 1).Where("version = ?", 5555).Delete(&model.User{})
			// DELETE FROM `user` WHERE id = 1 AND version = 5555

			db.Where(&model.User{Id: 1555, Version: 55}).Delete(&model.User{})
			// DELETE FROM `user` WHERE `user`.`id` = 1555 AND `user`.`version` = 55
	
	# 指定主键删除，只支持整型数值，因为 string 可能导致 SQL 注入
		db.Delete(&User{}, 10)
		// DELETE FROM users WHERE id = 10;

		db.Delete(&User{}, "10")
		// DELETE FROM users WHERE id = 10;

		db.Delete(&users, []int{1,2,3})
		// DELETE FROM users WHERE id IN (1,2,3);

	
	# Delete 也可以快捷条件删除
		db.Delete(&model.User{}, "id = ? AND version > ?", 55, 66)
		// DELETE FROM `user` WHERE id = 55 AND version > 66
	

-------------------
逻辑删除
-------------------
	# gorm提供的一个字段
		type DeletedAt sql.NullTime
	
	# 作为字段类型用在自己的对象上
		* 在执行删除操作的时候，会把 DeletedAt 置为当前时间， 并且不能再通过正常的查询方法找到该记录
		* 在检索的时候，都会添加条件： AND deleted_at IS NULL
	
	# 如果需要强制物理删除，可以使用Unscoped 
		db.Unscoped().Delete(&order)
	
