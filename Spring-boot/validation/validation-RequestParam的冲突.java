---------------------------------------------------------------------
@RequestParam(required = false) 的冲突
---------------------------------------------------------------------
	# 手机号码验证注解
		import java.lang.annotation.ElementType;
		import java.lang.annotation.Retention;
		import java.lang.annotation.Target;

		import javax.validation.Constraint;
		import javax.validation.Payload;
		import javax.validation.ReportAsSingleViolation;
		import javax.validation.constraints.Pattern;

		import io.streamer.common.constant.RegexPatterns;

		@Retention(RUNTIME)
		@Target(value = { ElementType.FIELD, ElementType.PARAMETER })
		@Constraint(validatedBy = {})
		@ReportAsSingleViolation
		@Pattern(regexp = RegexPatterns.PHONE_NUMBER)
		public @interface Phone {
			String message() default "手机号码不正确，只支持大陆手机号码";
			Class<?>[] groups() default {};
			Class<? extends Payload>[] payload() default {};
		}


	# 参数验证的问题
		@RequestParam(value = "phone", required = false) @Phone String phone
		/api?phone=  //会抛出异常，但是phone参数允许是为空字符串的
	

	
	# 新建验证注解
		* 新建一个注解比较好
			import static java.lang.annotation.RetentionPolicy.RUNTIME;
			import java.lang.annotation.ElementType;
			import java.lang.annotation.Retention;
			import java.lang.annotation.Target;

			import javax.validation.Constraint;
			import javax.validation.Payload;
			import javax.validation.ReportAsSingleViolation;

			import org.hibernate.validator.constraints.CompositionType;
			import org.hibernate.validator.constraints.ConstraintComposition;
			import org.hibernate.validator.constraints.Length;


			@Retention(RUNTIME)
			@Target(value = { ElementType.FIELD, ElementType.PARAMETER })
			@Constraint(validatedBy = {})
			@ReportAsSingleViolation
			@Phone							// 指定手机号码验证
			@Length(max = 0, min = 0)		// 可以为“空”字符串
			@ConstraintComposition(CompositionType.OR)	// or关系
			public @interface PhoneOrEmpty {
				String message() default "手机号码不正确，只支持大陆手机号码";
				Class<?>[] groups() default {};
				Class<? extends Payload>[] payload() default {};
			}