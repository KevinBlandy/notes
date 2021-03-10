------------------
校验基本数据
------------------
	# 验证变量是否符合tag
		v := validator.New()
		email := "88855.com"
		if err := v.Var(email, "email"); err != nil {
			for _, err := range err.(validator.ValidationErrors) {
				log.Println(err)
			}
		}
	
	# 验证2个变量关系是否符合tag
		// 使用 VarWithValue
	


		