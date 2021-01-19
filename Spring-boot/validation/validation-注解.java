--------------------
注解
--------------------
	@Constraint
		* 指定校验器的实现
		Class<? extends ConstraintValidator<?, ?>>[] validatedBy();
	
	@Valid
		* 嵌套校验，在需要校验的属性（单个对象或者对象集合）上添加该注解

	@SupportedValidationTarget
		ValidationTarget[] value();
			* 枚举
				ANNOTATED_ELEMENT
				PARAMETERS

--------------------
约束注解
--------------------
	# 通用的属性
		String message() default "{javax.validation.constraints....message}";
			* 数据校验失败的时候提示信息

		Class<?>[] groups() default { };
			* 指定Group

		Class<? extends Payload>[] payload() default { };
			* 指定payload
			* 在验证的过程中，可以通过API获取到注解上定义的payload
			* 子接口
				Unwrapping.Unwrap
				Unwrapping.Skip

	@AssertTrue
	@AssertFalse
		* 可以为null,如果不为null的话必须为 true/false
		* 可以标识在 某些 方法上, 方法通过一系列的校验逻辑 返回 true/false

	@DecimalMax
	@DecimalMin
		* 不能超过最大, 最小值
		* 可以用在: BigDecimal, BigInteger, CharSequence, byte, short, int, long

		String value();
			* 限制值
		boolean inclusive() default true;
			* 是否包含指定的值
	
	@Max
	@Min
		* 最大不得小于此最小值
		* 可以用在: BigDecimal, BigInteger, byte, short, int, long

		long value();
			* 指定值

	@Digits
		* 设置必须是数字且数字整数的位数和小数的位数必须在指定范围内

		int integer();
			* 最大整数

		int fraction();
			* 最大小数位
	@Email
		* 必须是邮件

		String regexp() default ".*";
			* 默认的邮件验证表达式
		Pattern.Flag[] flags() default { };
			* 指定正则的选项

	@Future
	@Past
	@FutureOrPresent
	@PastOrPresent
		* 日期必须在当前日期的 将来/过去/未来或者现在/过去或者现在
		* 支持几乎现在java的所有日期相关的对象
	
	@Negative
	@NegativeOrZero
	@Positive
	@PositiveOrZero
		* 数据必须是: 负数/负数或0/正数/正数或0
		* 支持数据类型: BigDecimal, BigInteger, byte, short, int, long, float
	
	

	@NotNull
		* 不能为null

	@Null
		* 必须为null

	@Pattern
		* 必须满足指定的正则表达式

		String regexp();
			* 指定正则
		Flag[] flags() default { };
			* 指定表达式的flag

	@Size
		* 集合、数组、map等的size()值必须在指定范围内

		int min() default 0;
			* 最小长度
		int max() default Integer.MAX_VALUE;
			* 最大长度

	@NotBlank
		* 字符串不能为null,字符串trin()后也不能等于""

	@NotEmpty
		* 不能为null，集合、数组、map等size()不能为0；字符串trin()后可以等于 ""


