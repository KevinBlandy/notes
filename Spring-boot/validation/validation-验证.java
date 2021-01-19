-----------------------
验证
-----------------------
	# 创建验证规则
		* 使用注解在对象的属性上进行标识

	# 验证步骤
		1. 使用 Validation 获取倒 ValidatorFactory
		2. 通过 ValidatorFactory 获取到 Validator
		3. 使用 Validator 对对象, 属性进行校验, 返回校验的结果
	
	
	# 支持验证的地方
		* 属性, 方法, 构造函数, 泛型, 返回值, 方法

		* 注意注意，如果是对方法，进行验证。那么方法名称需要满足JavaBean的规范:名称以 getter/is 开头
			@AssertTrue(message = "手机和邮箱必须填写其中一项")
			public boolean isEmailAndPhoneValid () {
				if (this.email == null && this.phone == null) {
					return false;
				}
				return true;
			}
	
	# 验证
		// SPI加载启动类
		GenericBootstrap genericBootstrap = Validation.byDefaultProvider();
		
		// 获取配置
		Configuration<?> configuration = genericBootstrap.configure();
		
		// 从配置创建工厂类
		ValidatorFactory  validatorFactory = configuration.buildValidatorFactory();
		
		Validator validation = validatorFactory.getValidator();
		
		Set<ConstraintViolation<Object>> constraintViolations = validation.validate(user);
		
		for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
			// 异常消息
			String message = constraintViolation.getMessage();
			System.out.println(message);
		}

-----------------------
Object Graph验证
-----------------------
	# Object Graph是指对象的拓扑结构，比如对象的引用关系
		* Bean Validation支持Object Graph验证
	
	# 默认如果A对象引用B对象是不会对B对象进行校验的。需要在B对象的字段或者getter标识 @Valid 注解才行
		* 可以标识在集合上，会对集合用的元素挨个执行校验
	

