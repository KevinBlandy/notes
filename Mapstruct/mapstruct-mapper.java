----------------------
Mapper
----------------------
	# 映射器注解
		@Mapper
			Class<?>[] uses() default { };
			Class<?>[] imports() default { };
			ReportingPolicy unmappedSourcePolicy() default ReportingPolicy.IGNORE;
				* 原属性不存在时的策略
			ReportingPolicy unmappedTargetPolicy() default ReportingPolicy.WARN;
				* 目标属性不存在时的策略
			ReportingPolicy typeConversionPolicy() default ReportingPolicy.IGNORE;
				* 当涉及到类型转换时的策略，默认是 IGNORE 忽略

			String componentModel() default "default";
			String implementationName() default "<CLASS_NAME>Impl";
			String implementationPackage() default "<PACKAGE_NAME>";
			Class<?> config() default void.class;
			CollectionMappingStrategy collectionMappingStrategy() default CollectionMappingStrategy.ACCESSOR_ONLY;
			NullValueMappingStrategy nullValueMappingStrategy() default NullValueMappingStrategy.RETURN_NULL;
			NullValuePropertyMappingStrategy nullValuePropertyMappingStrategy() default NullValuePropertyMappingStrategy.SET_TO_NULL;
			MappingInheritanceStrategy mappingInheritanceStrategy() default MappingInheritanceStrategy.EXPLICIT;
			NullValueCheckStrategy nullValueCheckStrategy() default ON_IMPLICIT_CONVERSION;
			InjectionStrategy injectionStrategy() default InjectionStrategy.FIELD;
			boolean disableSubMappingMethodsGeneration() default false;
			Builder builder() default @Builder;
			Class<? extends Annotation> mappingControl() default MappingControl.class;
			Class<? extends Exception> unexpectedValueMappingException() default IllegalArgumentException.class;
		
	
	# 显示映射配置
		* 默认会遵循javaBean规范，进行映射，这是隐式的
		* target和source属性名称不同，使用 @Mapping 指定
			@Mapper
			public interface CarMapper {

				@Mapping(source = "make", target = "manufacturer")
				@Mapping(source = "numberOfSeats", target = "seatCount")
				CarDto carToCarDto(Car car);

				@Mapping(source = "name", target = "fullName")
				PersonDto personToPersonto(Person person);
			}
		
		* @Mapping
			String target();
			String source() default "";
			String dateFormat() default "";
				* 日期/String 转换时的pattern

			String numberFormat() default "";
				* 源是数值/BigDecimal，目标是字符串时，指定的格式化方式

			String constant() default "";
			String expression() default "";
			String defaultExpression() default "";
			boolean ignore() default false;
			Class<? extends Annotation>[] qualifiedBy() default { };
			String[] qualifiedByName() default { };
			Class<?> resultType() default void.class;
			String[] dependsOn() default { };
			String defaultValue() default "";
			NullValueCheckStrategy nullValueCheckStrategy() default ON_IMPLICIT_CONVERSION;
			NullValuePropertyMappingStrategy nullValuePropertyMappingStrategy() default NullValuePropertyMappingStrategy.SET_TO_NULL;
			Class<? extends Annotation> mappingControl() default MappingControl.class;
		
		* @BeanMapping
			Class<?> resultType() default void.class;
			Class<? extends Annotation>[] qualifiedBy() default {};
			String[] qualifiedByName() default { };
			NullValueMappingStrategy nullValueMappingStrategy() default NullValueMappingStrategy.RETURN_NULL;
			NullValuePropertyMappingStrategy nullValuePropertyMappingStrategy() default NullValuePropertyMappingStrategy.SET_TO_NULL;
			NullValueCheckStrategy nullValueCheckStrategy() default ON_IMPLICIT_CONVERSION;
			boolean ignoreByDefault() default false;
				* 默认为 false,  意思是按照JavaBean的规范来映射字段
				* 如果设置为false 这意味着所有映射必须通过的方式来指定 @Mapping
			
			String[] ignoreUnmappedSourceProperties() default {};
			Builder builder() default @Builder;
			Class<? extends Annotation> mappingControl() default MappingControl.class;

				
		* 支持 Fluent setter。也就是返回与被修改类型相同类型的 setter
			public Builder seatCount(int seatCount) {
				this.seatCount = seatCount;
				return this;
			}
	
	# 映射组合
	
	# 自定义映射方法
		* jdk8以前，需要把 mapper 定义为抽象类，然后手动实现方法
		* jdk8以后可以用接口的默认方法
			@Mapper
			public interface CarMapper {

				@Mapping(...)
				...
				CarDto carToCarDto(Car car);

				default PersonDto personToPersonDto(Person person) {
					// 手写的映射逻辑
				}
			}
	
	# 多个源参数
		* 把多个源对象，映射到一个目标对线
			@Mapper
			public interface AddressMapper {
				@Mapping(source = "person.description", target = "description")
				@Mapping(source = "address.houseNo", target = "houseNumber")
				DeliveryAddressDto personAndAddressToDeliveryAddressDto(Person person, Address address);
			}

		* 如果多个源对象有同层同名属性和目标对线冲突，必须使用@Mapping注释指定从中检索属性的源参数，冲突不解决就会异常
		* 可以直接引用源参数
			@Mapper
			public interface AddressMapper {
				@Mapping(source = "person.description", target = "description")
				@Mapping(source = "hn", target = "houseNumber")
				DeliveryAddressDto personAndAddressToDeliveryAddressDto(Person person, Integer hn);
			}
		
	# 嵌套的 bean 属性映射
		* 源对象是嵌套的
			 @Mapper
			 public interface CustomerMapper {

				 @Mapping( target = "name", source = "record.name" ) // customerDto.record.name 属性映射给 Customer.name
				 @Mapping( target = ".", source = "record" ) // customerDto.record 所有属性映射给 Customer
				 @Mapping( target = ".", source = "account" )// customerDto.account 所有属性映射给 Customer
				 Customer customerDtoToCustomer(CustomerDto customerDto);
			 }
	
	# 更新现有 bean 实例
		* 不需要创建新的目标对象，而是从已有的对象进行修改，使用 @MappingTarget 注解标识目标对象
			@Mapper
			public interface CarMapper {
				void updateCarFromDto(CarDto carDto, @MappingTarget Car car);
			}
		
		* 返回值也可以设置为 target 类型，这将返回参数。
	

	# 直接字段访问的映射
		* 在没有getter/setter的情况下，映射遵循一些条件
			1. 如果字段是 public, public final 的，则作为源可读
			2, 如果字段是 public 的，则作为目可写
			3, 如果字段是 static 的，则忽视
			4, 只有可读，可写能够进行匹配的时候才会执行值的复制
	
	# 使用构建器(Builder)

	# 使用构造函数

----------------------
Mapper 检索
----------------------
	# Mappers 工厂（无依赖注入）
		CarMapper mapper = Mappers.getMapper(CarMapper.class );

		* 单例
	

	# 依赖注入
		@Mapper(componentModel = "cdi")  // ci模式
		public interface CarMapper {
			CarDto carToCarDto(Car car);
		}

		@Inject
		private CarMapper mapper;

	
	# 生成的实现类路径
		target/generated-sources/annotations
