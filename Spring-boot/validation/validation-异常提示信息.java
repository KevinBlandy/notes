------------------------
�쳣��ʾ��Ϣ
------------------------
	# MessageInterpolator �Զ����ʽ����Ϣ
		String interpolate(String messageTemplate, Context context);
		String interpolate(String messageTemplate, Context context,  Locale locale);
		interface Context {
			ConstraintDescriptor<?> getConstraintDescriptor();
			Object getValidatedValue();
			<T> T unwrap(Class<T> type);
		}
	

------------------------------------------------
����Ĭ�ϵ���Ϣ
------------------------------------------------

	# �� classpath �´������ʻ��ļ�

		ValidationMessages
		ValidationMessages_zh.properties
	
	# ���ļ��ж���У��ע��� message ��Ϣ
		jakarta.validation.constraints.NotNull.message=������ NULL
		jakarta.validation.constraints.NotBlank.message=�����ǿ�

		* @NotBlank ��Ĭ��ֵ���ǣ�
			String message() default "{jakarta.validation.constraints.NotBlank.message}";

	
	# ע��
		* ��ע��� message ������ʹ�� {xxx.message} ������Ϣ
		* �����Զ���ע��

			@NotBlank
			private String title;
		
		* ������ properties �ļ���ʹ�� xxx.message ������Ϣ


------------------------------------------------
���ʻ�
------------------------------------------------

	# ���ʻ���Ҫ������
		1. �Զ����쳣��Ϣ��ʾ
		2. �Զ����ֶ�����
	
	# �����Զ���У��ע�⣬��ע�������� ���ֶ����ơ� ����
		@Retention(RUNTIME)
		@Target(FIELD)
		@Constraint(validatedBy = { PhoneNumber.PhoneNumberValidator.class }) // ָ����֤��ʵ����
		public @interface PhoneNumber {
			
			// �����쳣ʱ����ʾ��Ϣ
			String message() default "{cn.springdoc.demo.validate.PhoneNumber.message}";
			
			// �Զ����ֶ�����
			String field() default "{cn.springdoc.demo.validate.PhoneNumber.field}";

			// group
			Class<?>[] groups() default {};

			// payload
			Class<? extends Payload>[] payload() default {};
			
			// ʵ�������ڲ�����ʽ
			static final class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, Object> {
				
				@Override
				public boolean isValid(Object value, ConstraintValidatorContext context) {
					return value != null && value.toString().matches("!1[3-9]\\d{9}^");
				}
			}
		}
	
		* ���� message �� field �ֶΣ����ʽ��ʹ�� {} ȡֵ���ʽ
	
		# �� classpath �´������ʻ��ļ�

			ValidationMessages
			ValidationMessages_zh.properties
		
			* �ڶ�Ӧ���ļ��ж��岻���Ե�����ֵ
				# �ֶ�����
				cn.springdoc.demo.validate.PhoneNumber.field=�ֻ���
				# ��ʾ��Ϣ
				cn.springdoc.demo.validate.PhoneNumber.message={cn.springdoc.demo.validate.PhoneNumber.field}: �ֻ��Ų���Ŷ
			
			* �������ļ��У�����ʹ�� {cn.springdoc.demo.validate.PhoneNumber.field} Ƕ�׶�ȡ
	
		# ��ʹ����Ҳ����ʹ�� {}
			@PhoneNumber(message = "{cn.springdoc.demo.validate.PhoneNumber.field} ���԰�")
			private String phone;
		
