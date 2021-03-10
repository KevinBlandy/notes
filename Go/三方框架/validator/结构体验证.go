------------------
结构体验证
------------------
	# 如果属性是map/切片，需要关联校验里面的数据，那么使用 dive 标签
		type User struct {
			Id int `json:"id"`
			Hobby [] *struct {
				Name string `json:"name" validate:"required,min=1,max=50"`	// 字符串，1-50个字符
				Proficiency int `json:"proficiency" validate:"required,min=0,max=100"` // 熟练度，0 - 100
			} `json:"hobby" validate:"required,max=5,dive"` // 最多10个选项，并且使用 dive ，关联校验这个Hobby里面的数据
		}
	
	# 如果属性是结构体，那么会自动关联校验

	


