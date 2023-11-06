------------------------
异常提示信息
------------------------
	# MessageInterpolator 自定义格式化消息
		String interpolate(String messageTemplate, Context context);
		String interpolate(String messageTemplate, Context context,  Locale locale);
		interface Context {
			ConstraintDescriptor<?> getConstraintDescriptor();
			Object getValidatedValue();
			<T> T unwrap(Class<T> type);
		}
	

------------------------------------------------
覆盖默认的消息
------------------------------------------------

	# 在 classpath 下创建国际化文件

		ValidationMessages
		ValidationMessages_zh.properties
	
	# 在文件中定义校验注解的 message 消息
		jakarta.validation.constraints.NotNull.message=不能是 NULL
		jakarta.validation.constraints.NotBlank.message=不能是空

		* @NotBlank 的默认值就是：
			String message() default "{jakarta.validation.constraints.NotBlank.message}";

	
	# 注解
		* 在注解的 message 属性中使用 {xxx.message} 定义消息
		* 包括自定义注解

			@NotBlank
			private String title;
		
		* 在所有 properties 文件中使用 xxx.message 定义消息


------------------------------------------------
国际化
------------------------------------------------

	# 国际化的要点在于
		1. 自定义异常消息提示
		2. 自定义字段名称
	
	# 创建自定义校验注解，在注解中新增 “字段名称” 属性
		@Retention(RUNTIME)
		@Target(FIELD)
		@Constraint(validatedBy = { PhoneNumber.PhoneNumberValidator.class }) // 指定验证的实现类
		public @interface PhoneNumber {
			
			// 设置异常时的提示信息
			String message() default "{cn.springdoc.demo.validate.PhoneNumber.message}";
			
			// 自定义字段名称
			String field() default "{cn.springdoc.demo.validate.PhoneNumber.field}";

			// group
			Class<?>[] groups() default {};

			// payload
			Class<? extends Payload>[] payload() default {};
			
			// 实现类以内部类形式
			static final class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, Object> {
				
				@Override
				public boolean isValid(Object value, ConstraintValidatorContext context) {
					return value != null && value.toString().matches("!1[3-9]\\d{9}^");
				}
			}
		}
	
		* 其中 message 和 field 字段，表达式都使用 {} 取值表达式
	
		# 在 classpath 下创建国际化文件

			ValidationMessages
			ValidationMessages_zh.properties
		
			* 在对应的文件中定义不语言的属性值
				# 字段名称
				cn.springdoc.demo.validate.PhoneNumber.field=手机号
				# 提示消息
				cn.springdoc.demo.validate.PhoneNumber.message={cn.springdoc.demo.validate.PhoneNumber.field}: 手机号不对哦
			
			* 在配置文件中，可以使用 {cn.springdoc.demo.validate.PhoneNumber.field} 嵌套读取
	
		# 在使用中也可以使用 {}
			@PhoneNumber(message = "{cn.springdoc.demo.validate.PhoneNumber.field} 不对啊")
			private String phone;
		
