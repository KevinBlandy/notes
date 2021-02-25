-------------------------
异常处理
-------------------------
	# GORM 提供的是链式 API。如果遇到任何错误，GORM 会设置 *gorm.DB 的 Error 字段
	
	# 需要自己在最后检查
		if err := db.Where("name = ?", "jinzhu").First(&user).Error; err != nil {
			// 处理错误...
		}

		if result := db.Where("name = ?", "jinzhu").First(&user); result.Error != nil {
		  // 处理错误...
		}

	
	# 内置的错误列表
		// ErrRecordNotFound record not found error
		ErrRecordNotFound = errors.New("record not found")
		// ErrInvalidTransaction invalid transaction when you are trying to `Commit` or `Rollback`
		ErrInvalidTransaction = errors.New("no valid transaction")
		// ErrNotImplemented not implemented
		ErrNotImplemented = errors.New("not implemented")
		// ErrMissingWhereClause missing where clause
		ErrMissingWhereClause = errors.New("WHERE conditions required")
		// ErrUnsupportedRelation unsupported relations
		ErrUnsupportedRelation = errors.New("unsupported relations")
		// ErrPrimaryKeyRequired primary keys required
		ErrPrimaryKeyRequired = errors.New("primary key required")
		// ErrModelValueRequired model value required
		ErrModelValueRequired = errors.New("model value required")
		// ErrInvalidData unsupported data
		ErrInvalidData = errors.New("unsupported data")
		// ErrUnsupportedDriver unsupported driver
		ErrUnsupportedDriver = errors.New("unsupported driver")
		// ErrRegistered registered
		ErrRegistered = errors.New("registered")
		// ErrInvalidField invalid field
		ErrInvalidField = errors.New("invalid field")
		// ErrEmptySlice empty slice found
		ErrEmptySlice = errors.New("empty slice found")
		// ErrDryRunModeUnsupported dry run mode unsupported
		ErrDryRunModeUnsupported = errors.New("dry run mode unsupported")
	

	# 通过 erros.Is 来判断异常类型
		// 检查错误是否为 RecordNotFound
		err := db.First(&user, 100).Error
		errors.Is(err, gorm.ErrRecordNotFound)
