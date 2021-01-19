-------------------------------
Expression
-------------------------------
	# 在Query实例中定义了通用类型的表达式
		interface Expression<T> extends Serializable 
	
	# 抽象方法
		@Nullable
		<R,C> R accept(Visitor<R,C> v, @Nullable C context);

		Class<? extends T> getType();
	
