--------------------
自定义tag验证
--------------------
	# 验证方法，通过返回bool值，表示是否验证成功
		type Func func(fl FieldLevel) bool
		type FuncCtx func(ctx context.Context, fl FieldLevel) bool
	
	# 验证参数
		type FieldLevel interface {
			Top() reflect.Value
			Parent() reflect.Value
			Field() reflect.Value
				* 获取当前字段信息

			FieldName() string
				* 获取字段名称

			StructFieldName() string
			Param() string
				* 获取tag对应的参数

			GetTag() string
			ExtractType(field reflect.Value) (value reflect.Value, kind reflect.Kind, nullable bool)
			GetStructFieldOK() (reflect.Value, reflect.Kind, bool)
			GetStructFieldOKAdvanced(val reflect.Value, namespace string) (reflect.Value, reflect.Kind, bool)
			GetStructFieldOK2() (reflect.Value, reflect.Kind, bool, bool)
			GetStructFieldOKAdvanced2(val reflect.Value, namespace string) (reflect.Value, reflect.Kind, bool, bool)
		}

	# 注册方法
		func (v *Validate) RegisterValidation(tag string, fn Func, callValidationEvenIfNull ...bool) error
		func (v *Validate) RegisterValidationCtx(tag string, fn FuncCtx, callValidationEvenIfNull ...bool) error
			* 注册自定义校验tag
			* tag指定参数，fn指定校验方法，callValidationEvenIfNull 是否在null值的时候也调用验证
	
	# Demo
		type User struct {
			Id int `json:"id"`
			Name string `json:"name" validate:"omitempty,nick_name"`  // 如果Name是零值，不校验，如果有值那么必须符合 nick_name 的校验
			Hobby *Hobby `validate:required"`
		}

		v := validator.New()

		var user = &User{...} // 忽略初始化代码

		// 添加tag
		v.RegisterValidation("nick_name", func(fl validator.FieldLevel) bool {
			// 获取到字段的值
			val := fl.Field().Interface()
			// 转换为string，并且判断是否等于字符串 "nick_name"
			if v, ok := val.(string); ok {
				if v == "nick_name" {
					return true
				}
			}
			return false
		})

		if err := v.Struct(user); err != nil {
			if err, ok := err.(validator.ValidationErrors); ok {
				log.Println(err)
			}
		}
		
--------------------
自定义struct验证
--------------------
	# 验证方法
		type StructLevelFunc func(sl StructLevel)
	
	# 验证参数
		type StructLevel interface {
			Validator() *Validate
			Top() reflect.Value
			Parent() reflect.Value
			Current() reflect.Value
				* 获取当前的校验对象

			ExtractType(field reflect.Value) (value reflect.Value, kind reflect.Kind, nullable bool)

			ReportError(field interface{}, fieldName, structFieldName string, tag, param string)
				* 报告异常，指定字段，字段名称，结构体中的字段名，标签，参数值
			
			ReportValidationErrors(relativeNamespace, relativeActualNamespace string, errs ValidationErrors)
		}
	
	# 注册方法
		func (v *Validate) RegisterStructValidation(fn StructLevelFunc, types ...interface{})
		func (v *Validate) RegisterStructValidationCtx(fn StructLevelFuncCtx, types ...interface{}) 
		
		* 这个方法并不是线程安全的，需要注意
	
	# Demo
		v.RegisterStructValidation(func(sl validator.StructLevel) {
			// 获取到校验对象
			if user, ok := sl.Current().Interface().(User); ok {
				if user.Id < 0 {
					// 如果校验失败，则报告异常
					sl.ReportError(user.Id, "Id", "id", "id", "id")
				}
			}
		}, &User{})
	
--------------------
自定义type验证
--------------------
	# 验证方法
		type CustomTypeFunc func(field reflect.Value) interface{}
	

	# 注册方法
		func (v *Validate) RegisterCustomTypeFunc(fn CustomTypeFunc, types ...interface{})
		
		* 这个方法不是线程安全的
	
	# Demo
		v.RegisterCustomTypeFunc(func(field reflect.Value) interface{} {
			// 把字段值，转换为指定的类型
			if valuer, ok := field.Interface().(driver.Valuer); ok {
				val, err := valuer.Value()
				log.Println(val)
				if err != nil {
					// TODO value err
				}
				// 转换后，返回
				return val
			}
			return nil
		}, sql.NullString{})