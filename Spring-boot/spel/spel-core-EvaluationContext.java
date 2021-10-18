---------------------------
EvaluationContext
---------------------------
	# 执行的上下文环境接口
		* 用于设置根对象、自定义变量、自定义函数、类型转换器
		* 默认实现: StandardEvaluationContext

		public interface EvaluationContext {
			TypedValue getRootObject();
			List<PropertyAccessor> getPropertyAccessors();
			List<ConstructorResolver> getConstructorResolvers();
			List<MethodResolver> getMethodResolvers();
			@Nullable
			BeanResolver getBeanResolver();
			TypeLocator getTypeLocator();
			TypeConverter getTypeConverter();
			TypeComparator getTypeComparator();
			OperatorOverloader getOperatorOverloader();
			void setVariable(String name, @Nullable Object value);
			@Nullable
			Object lookupVariable(String name);
		}

---------------------------
StandardEvaluationContext
---------------------------
	# 默认实现
		public StandardEvaluationContext()
		public StandardEvaluationContext(@Nullable Object rootObject)
			* 设置root对象
			* 访问root对象属性，不需要使用 '#' 标识
		
		
		public void setRootObject(@Nullable Object rootObject, TypeDescriptor typeDescriptor)
		public void setRootObject(@Nullable Object rootObject)
		public TypedValue getRootObject()
		public void setPropertyAccessors(List<PropertyAccessor> propertyAccessors)
		public List<PropertyAccessor> getPropertyAccessors() 
		public void addPropertyAccessor(PropertyAccessor accessor) 
		public boolean removePropertyAccessor(PropertyAccessor accessor)
		public void setConstructorResolvers(List<ConstructorResolver> constructorResolvers)
		public List<ConstructorResolver> getConstructorResolvers()
		public void addConstructorResolver(ConstructorResolver resolver)
		public boolean removeConstructorResolver(ConstructorResolver resolver)
		public void setMethodResolvers(List<MethodResolver> methodResolvers) 
		public List<MethodResolver> getMethodResolvers()
		public void addMethodResolver(MethodResolver resolver)
		public boolean removeMethodResolver(MethodResolver methodResolver) 

		public void setBeanResolver(BeanResolver beanResolver)
		public BeanResolver getBeanResolver() 
			* 设置 Spring 的 Bean 解析器，可以通过SpEL 访问到 IOC 中的 Bean

		public void setTypeLocator(TypeLocator typeLocator) 
		public TypeLocator getTypeLocator()
		public void setTypeConverter(TypeConverter typeConverter) 
		public TypeConverter getTypeConverter()
		public void setTypeComparator(TypeComparator typeComparator)
		public TypeComparator getTypeComparator()
		public void setOperatorOverloader(OperatorOverloader operatorOverloader)
		public OperatorOverloader getOperatorOverloader()
		public void setVariable(@Nullable String name, @Nullable Object value)
		public void setVariables(Map<String, Object> variables)
		public void registerFunction(String name, Method method)
		public Object lookupVariable(String name)
		public void registerMethodFilter(Class<?> type, MethodFilter filter) throws IllegalStateException 
		private List<PropertyAccessor> initPropertyAccessors()
		private List<ConstructorResolver> initConstructorResolvers()
		private List<MethodResolver> initMethodResolvers()
		private static <T> void addBeforeDefault(List<T> resolvers, T resolver)




