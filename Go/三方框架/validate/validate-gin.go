-------------------
gin
-------------------
	# 整合gin
		// 实现gin的校验器 binding.StructValidator
		type customValidator struct {}
		func (v *customValidator) ValidateStruct(ptr interface{}) error {
			var result = validate.Struct(ptr)
			if result.Validate() {
				return nil
			}
			return result.Errors
		}
		func (v *customValidator) Engine() interface{} {
			return nil
		}

		// 设置gin的默认校验器
		binding.Validator = &customValidator{}

	
	# 使用
		var userRequest = &UserRequest{}
		if err := ctx.ShouldBindJSON(userRequest); err != nil {
			// 转换为校验异常
			ctx.JSON(http.StatusBadRequest, gin.H{
				"success": false,
				"message": err.(validate.Errors).One(),  // 转换为 validate.Errors， 随机响应一条异常信息
			})
			return
		}
		ctx.JSON(http.StatusOK, gin.H{"success": true, "data": userRequest})
