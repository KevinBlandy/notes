----------------------------
var
----------------------------

----------------------------
type
----------------------------
	# type CustomTypeFunc func(field reflect.Value) interface{}
		* 自定义的校验方法

	# type FieldError interface {
			Tag() string
				* 所有注解
			ActualTag() string
				* 验证失败的注解
			Namespace() string
			StructNamespace() string
			Field() string
			StructField() string
				* 字段在结构体中的名字

			Value() interface{}
				* 字段值
			
			Param() string
			Kind() reflect.Kind
			Type() reflect.Type
				* 返回字段的Type

			Translate(ut ut.Translator) string
			Error() string
		}
		
		* 校验失败的异常结果集

	# type FieldLevel interface {
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
		
		* 校验中的字段信息

	# type FilterFunc func(ns []byte) bool

	# type Func func(fl FieldLevel) bool
	# type FuncCtx func(ctx context.Context, fl FieldLevel) bool
		* 自定义Tag验证的验证方法

	# type InvalidValidationError struct {
			Type reflect.Type
		}
		func (e *InvalidValidationError) Error() string
	# type RegisterTranslationsFunc func(ut ut.Translator) error

	# type StructLevel interface {
			Validator() *Validate
			Top() reflect.Value
			Parent() reflect.Value
			Current() reflect.Value
			ExtractType(field reflect.Value) (value reflect.Value, kind reflect.Kind, nullable bool)

			ReportError(field interface{}, fieldName, structFieldName string, tag, param string)
				* 报告异常?
			
			ReportValidationErrors(relativeNamespace, relativeActualNamespace string, errs ValidationErrors)
		}
		
		* 校验中的结构体信息
			
	# type StructLevelFunc func(sl StructLevel)
	# type StructLevelFuncCtx func(ctx context.Context, sl StructLevel)
		* 结构体校验方法

	# type TagNameFunc func(field reflect.StructField) string
	# type TranslationFunc func(ut ut.Translator, fe FieldError) string
	# type Validate struct {
		}
		func New() *Validate
		func (v *Validate) RegisterAlias(alias, tags string)
		func (v *Validate) RegisterCustomTypeFunc(fn CustomTypeFunc, types ...interface{})
			* 注册自定义的校验函数，通过types指定类型

		func (v *Validate) RegisterStructValidation(fn StructLevelFunc, types ...interface{})
		func (v *Validate) RegisterStructValidationCtx(fn StructLevelFuncCtx, types ...interface{})
			* 注册自定义校验结构体的函数
			* fn指定参数，types指定结构体（创建一个实例）

		func (v *Validate) RegisterTagNameFunc(fn TagNameFunc)
			* 对于结构体中的字段，校验失败的时候，会在异常信息中添加字段名称
			* 可以通过这个方法，来修改字段名称
			* 例如：使用为结构的JSON表示而指定的名称，而不是普通的Go字段名。
				validate.RegisterTagNameFunc(func(fld reflect.StructField) string {
					name := strings.SplitN(fld.Tag.Get("json"), ",", 2)[0]
					if name == "-" {
						return ""
					}
					return name
				})

		func (v *Validate) RegisterTranslation(tag string, trans ut.Translator, registerFn RegisterTranslationsFunc, ...) (err error)

		func (v *Validate) RegisterValidation(tag string, fn Func, callValidationEvenIfNull ...bool) error
		func (v *Validate) RegisterValidationCtx(tag string, fn FuncCtx, callValidationEvenIfNull ...bool) error
			* 注册自定义校验tag
			* tag指定参数，fn指定校验方法，callValidationEvenIfNull 是否在null值的时候也调用验证

		func (v *Validate) SetTagName(name string)

		func (v *Validate) Struct(s interface{}) error
		func (v *Validate) StructCtx(ctx context.Context, s interface{}) (err error)
			* 校验结构体

		func (v *Validate) StructExcept(s interface{}, fields ...string) error
		func (v *Validate) StructExceptCtx(ctx context.Context, s interface{}, fields ...string) (err error)
		func (v *Validate) StructFiltered(s interface{}, fn FilterFunc) error
		func (v *Validate) StructFilteredCtx(ctx context.Context, s interface{}, fn FilterFunc) (err error)
		func (v *Validate) StructPartial(s interface{}, fields ...string) error
		func (v *Validate) StructPartialCtx(ctx context.Context, s interface{}, fields ...string) (err error)

		func (v *Validate) Var(field interface{}, tag string) error
		func (v *Validate) VarCtx(ctx context.Context, field interface{}, tag string) (err error)
			* 校验指定的变量，是否符合tag

		func (v *Validate) VarWithValue(field interface{}, other interface{}, tag string) error
		func (v *Validate) VarWithValueCtx(ctx context.Context, field interface{}, other interface{}, tag string) (err error)
			* 校验2个变量的关系，是否符合tag 
				s1 := "abcd"
				s2 := "abcd"
				validate.VarWithValue(s1, s2, "eqcsfield") // returns true
			

	# type ValidationErrors []FieldError
		
		* 校验失败后的异常结果集
		* 一般验证失败后，都会转换为这个异常
		
		func (ve ValidationErrors) Error() string
		func (ve ValidationErrors) Translate(ut ut.Translator) ValidationErrorsTranslations

	# type ValidationErrorsTranslations map[string]string
		
----------------------------
func
----------------------------