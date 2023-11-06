--------------------------
�Զ���Constraint
--------------------------
	# ���õ�constraint������ʵ��ʹ����˵�Ǹ���������

	# ����һ��constraint��Ҫ�������֣�һ����constraintע�⣬һ����ִ��У���߼�����


	# ʵ�ֽӿ� ConstraintValidator<A extends Annotation, T> �����֤�߼�
		* ���� A ���Լ������У��ע��
		* T ��У��Ŀ�����������
			import javax.validation.ConstraintValidator;
			import javax.validation.ConstraintValidatorContext;
			public class TestConstraintValidator implements ConstraintValidator<TestConstraint, String> {

				@Override
				public void initialize(TestConstraint constraintAnnotation) {
					// ��ʼ���������Ի�ȡ��ע��
					// ֻ���ڳ�ʼ����ִ��һ��
				}

				@Override
				public boolean isValid(String value, ConstraintValidatorContext context) {
					context.disableDefaultConstraintViolation();
					context.buildConstraintViolationWithTemplate("����������¸�дע���еĴ�����ʾ��Ϣ").addConstraintViolation();
					return "Test".equals(value);
				}
			}
		

		* ConstraintValidator ������ʱֻ�ᴴ��һ��

		

	# �Զ���ע��
		* ����������ʱ @Retention(RUNTIME)
		* ��ע���ϱ�ʶ @Constraint ע��, ����ָ�� ConstraintValidator �ӿڵ�ʵ����
			import static java.lang.annotation.ElementType.FIELD;
			import static java.lang.annotation.RetentionPolicy.RUNTIME;
			import java.lang.annotation.Retention;
			import java.lang.annotation.Target;
			import javax.validation.Constraint;
			import javax.validation.Payload;

			@Retention(RUNTIME)
			@Target(FIELD)
			@Constraint(validatedBy = { TestConstraintValidator.class }) // ָ����֤��ʵ����
			public @interface TestConstraint {
				
				// �����쳣ʱ����ʾ��Ϣ
				String message() default "�ֶ�ֵ����Test";

				// group
				Class<?>[] groups() default {};

				// payload
				Class<? extends Payload>[] payload() default {};
			}
		
		* @Constraint ��Ψһ���� validatedBy ��һ������
			Class<? extends ConstraintValidator<?, ?>>[] validatedBy();
		* ������ע����ҪУ�������������, ��ô��ʵ�ֶ�� ConstraintValidator �ӿ�, ����ͨ�� validatedBy ��ʶ
		
		* message(), groups(), payload() �Ǳ�Ҫ������

--------------------------
��� Constraint
--------------------------
	# �������е�constraintע��Ĺ��ܣ�ʵ�ּ̳�У�����	

	# ���
		import static java.lang.annotation.ElementType.FIELD;
		import static java.lang.annotation.RetentionPolicy.RUNTIME;

		import java.lang.annotation.Retention;
		import java.lang.annotation.Target;

		import javax.validation.Constraint;
		import javax.validation.OverridesAttribute;
		import javax.validation.Payload;
		import javax.validation.constraints.Max;
		import javax.validation.constraints.Min;

		@Retention(RUNTIME)
		@Target(FIELD)
		@Constraint(validatedBy = { })

		/**
		 * һ�����߶����֤ע������
		 */
		@Min(0)
		@Max(value = 100)
		public @interface Range {

			/**
			 * ����ͨ��  @OverridesAttribute ע�⸲д��������ע���е�ָ������
			 */
			@OverridesAttribute(constraint = Min.class, name = "value") long min() default 1;
			@OverridesAttribute(constraint = Max.class, name = "value") long max() default 5;
			
			@OverridesAttribute(constraint = Min.class, name = "message") String minMessage() default "��Сֻ����1";
			@OverridesAttribute(constraint = Max.class, name = "message") String maxMessage() default "���ֻ����5";
			
			String message() default "����ֵ������ 1 - 5 ֮��";

			Class<?>[] groups() default { };

			Class<? extends Payload>[] payload() default { };
		}

		* �Զ���ע��, ��ע���ϱ�ʶN����֤ע��

		* message(), groups(), payload() �Ǳ�Ҫ������
		* @Constraint(validatedBy = { }) ע���Ǳ���ģ�����û��ָ�� validatedBy

		* ������ע����, ͨ�� @OverridesAttribute ����дָ��ע���ָ������
			@OverridesAttribute
				Class<? extends Annotation> constraint();
					* ָ��ע��
				String name() default "";
					* ָ��ע������
				int constraintIndex() default -1;
					* ������ظ���ʶ�Ķ����ͬע��
					* ͨ��������, ָ��ע����±�
		* ����ͨ�������� @OverridesAttribute ע�⣬����дһ������ע���еĶ������
		

		* @ReportAsSingleViolation ���ص�һ��֤��Ϣ
			* ʹ�������Լ��ע�⣬����Ϊÿ��ע�ⵥ������: message �쳣��Ϣ
			* Ĭ������£��᷵������У��ʧ��ע��� message �쳣��Ϣ���� @NotNull ����£�����Ϊ null ֻ�᷵�� @NotNull ��Ψһһ�� ��				

			* ʹ�����ע��󣬻����κ���֤ʧ�ܺ󷵻ص�ǰע���е� message ��Ϣ
			* �������ע���б�ʶ�� @NotNull ��������null������£�Ҳ���Ƿ��ص�ǰע��� message ��Ϣ

	
		* @ConstraintComposition ����ʾ���ע���еĹ�ϵ
			* ���ע���еĹ�ϵ, Ĭ���� AND, ��˼�����е�ע�⣬����������֤�ɹ�

			CompositionType value() default AND;
				* ö��
					OR				// or��ϵ�����ע���У��κ�һ��ͨ������Ϊ��ͨ��
					AND				// 
					ALL_FALSE		// ����ע��ȫ����ûͨ��������Ϊ���յĽ����ͨ��
			

		