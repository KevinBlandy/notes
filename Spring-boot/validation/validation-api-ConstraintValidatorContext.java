---------------------------------------------------
ConstraintValidatorContext
---------------------------------------------------
	# 接口方法
		void disableDefaultConstraintViolation();
		String getDefaultConstraintMessageTemplate();
		ClockProvider getClockProvider();
		ConstraintViolationBuilder buildConstraintViolationWithTemplate(String messageTemplate);
		<T> T unwrap(Class<T> type);

	
	# 内部Builder接口
		ConstraintViolationBuilder
			NodeBuilderDefinedContext addNode(String name);
			NodeBuilderCustomizableContext addPropertyNode(String name);
			LeafNodeBuilderCustomizableContext addBeanNode();
			ContainerElementNodeBuilderCustomizableContext addContainerElementNode(String name, Class<?> containerType, Integer typeArgumentIndex);
			NodeBuilderDefinedContext addParameterNode(int index);
			ConstraintValidatorContext addConstraintViolation();
		
		LeafNodeBuilderDefinedContext
			ConstraintValidatorContext addConstraintViolation();
		
		LeafNodeBuilderCustomizableContext
			LeafNodeContextBuilder inIterable();
			LeafNodeBuilderCustomizableContext inContainer(Class<?> containerClass, Integer typeArgumentIndex);
			ConstraintValidatorContext addConstraintViolation();
		
		LeafNodeContextBuilder
			LeafNodeBuilderDefinedContext atKey(Object key);
			LeafNodeBuilderDefinedContext atIndex(Integer index);
			ConstraintValidatorContext addConstraintViolation();
		
		NodeBuilderDefinedContext
			NodeBuilderCustomizableContext addNode(String name);
			NodeBuilderCustomizableContext addPropertyNode(String name);
			LeafNodeBuilderCustomizableContext addBeanNode();
			ContainerElementNodeBuilderCustomizableContext addContainerElementNode(String name, Class<?> containerType, Integer typeArgumentIndex);
			ConstraintValidatorContext addConstraintViolation();
		
		NodeBuilderCustomizableContext
			NodeContextBuilder inIterable();
			NodeBuilderCustomizableContext inContainer(Class<?> containerClass, Integer typeArgumentIndex);
			NodeBuilderCustomizableContext addNode(String name);
			NodeBuilderCustomizableContext addPropertyNode(String name);
			LeafNodeBuilderCustomizableContext addBeanNode();
			ContainerElementNodeBuilderCustomizableContext addContainerElementNode(String name, Class<?> containerType, Integer typeArgumentIndex);
			ConstraintValidatorContext addConstraintViolation();
		
		NodeContextBuilder
			NodeBuilderDefinedContext atKey(Object key);
			NodeBuilderDefinedContext atIndex(Integer index);
			NodeBuilderCustomizableContext addNode(String name);
			NodeBuilderCustomizableContext addPropertyNode(String name);
			LeafNodeBuilderCustomizableContext addBeanNode();
			ContainerElementNodeBuilderCustomizableContext addContainerElementNode(String name, Class<?> containerType, Integer typeArgumentIndex);
			ConstraintValidatorContext addConstraintViolation();
		
		ContainerElementNodeBuilderDefinedContext
			NodeBuilderCustomizableContext addPropertyNode(String name);
			LeafNodeBuilderCustomizableContext addBeanNode();
			ContainerElementNodeBuilderCustomizableContext addContainerElementNode(String name, Class<?> containerType, Integer typeArgumentIndex);
			ConstraintValidatorContext addConstraintViolation();
		
		ContainerElementNodeBuilderCustomizableContext
			ContainerElementNodeContextBuilder inIterable();
			NodeBuilderCustomizableContext addPropertyNode(String name);
			LeafNodeBuilderCustomizableContext addBeanNode();
			ContainerElementNodeBuilderCustomizableContext addContainerElementNode(String name, Class<?> containerType, Integer typeArgumentIndex);
			ConstraintValidatorContext addConstraintViolation();
		
		ContainerElementNodeContextBuilder
			ContainerElementNodeBuilderDefinedContext atKey(Object key);
			ContainerElementNodeBuilderDefinedContext atIndex(Integer index);
			NodeBuilderCustomizableContext addPropertyNode(String name);
			LeafNodeBuilderCustomizableContext addBeanNode();
			ContainerElementNodeBuilderCustomizableContext addContainerElementNode(String name, Class<?> containerType, Integer typeArgumentIndex);
			ConstraintValidatorContext addConstraintViolation();