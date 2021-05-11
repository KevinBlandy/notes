-----------------------------
validate
-----------------------------
	# Gookit组织开源的校验框架
		* Github
			https://github.com/gookit/validate
		
		* 文档
			https://github.com/gookit/validate/blob/master/README.zh-CN.md
			https://pkg.go.dev/github.com/gookit/validate
	
	# 是否必须的问题
		* Go的字段本身就有默认值，可以"配合标签"来设置允许为空
			Id int 				`validate:"uint" message:"ID不能小于0"`
			Account string		`validate:"minLen:0|maxLen:20" message:"账户字符长度在6-20位"`

			{
				"Id": null,			// OK
				"Account": null		// OK
			}

		* 如果要求客户端传递数据，就必须使用完整的校验标签
			

	
	# 自定义校验方法
		* 在结构体字段中声明一个方法名称，小写开头
		* 结构体实现这个方法，大写开头，参数就是字段值，返回bool，true表示OK，false表示异常

		type UserRequest struct {
			Desc string			`validate:"descValidate" message:"Desc校验失败，不能以S开头"`
		}
		func (f UserRequest) DescValidate(val string) bool {
			return val[0] != 'S'
		}
	

	
	# 结构体的基本验证
		v := validate.Struct(u)
		if !v.Validate() {
			// Map<String, Map<String, String>> -> {字段名称: {验证标签: 错误信息}}
			for field, v := range v.Errors {
				for tag, message := range v {
					fmt.Printf("Field=%s, tag=%s, err=%s\n", field, tag, message)
				}
			}
		}
	
		