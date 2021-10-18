------------------------
Expression
------------------------
	# 表示表达式对象
		* 默认实现: SpelExpression
	
	public interface Expression {
		String getExpressionString();
		@Nullable
		Object getValue() throws EvaluationException;
		@Nullable
		<T> T getValue(@Nullable Class<T> desiredResultType) throws EvaluationException;
		@Nullable
		Object getValue(@Nullable Object rootObject) throws EvaluationException;
		@Nullable
		<T> T getValue(@Nullable Object rootObject, @Nullable Class<T> desiredResultType) throws EvaluationException;
		@Nullable
		Object getValue(EvaluationContext context) throws EvaluationException;
		@Nullable
		Object getValue(EvaluationContext context, @Nullable Object rootObject) throws EvaluationException;
		@Nullable
		<T> T getValue(EvaluationContext context, @Nullable Class<T> desiredResultType) throws EvaluationException;
		@Nullable
		<T> T getValue(EvaluationContext context, @Nullable Object rootObject, @Nullable Class<T> desiredResultType)
				throws EvaluationException;
		@Nullable
		Class<?> getValueType() throws EvaluationException;
		@Nullable
		Class<?> getValueType(@Nullable Object rootObject) throws EvaluationException;
		@Nullable
		Class<?> getValueType(EvaluationContext context) throws EvaluationException;
		@Nullable
		Class<?> getValueType(EvaluationContext context, @Nullable Object rootObject) throws EvaluationException;
		@Nullable
		TypeDescriptor getValueTypeDescriptor() throws EvaluationException;
		@Nullable
		TypeDescriptor getValueTypeDescriptor(@Nullable Object rootObject) throws EvaluationException;
		@Nullable
		TypeDescriptor getValueTypeDescriptor(EvaluationContext context) throws EvaluationException;
		@Nullable
		TypeDescriptor getValueTypeDescriptor(EvaluationContext context, @Nullable Object rootObject) throws EvaluationException;
		boolean isWritable(@Nullable Object rootObject) throws EvaluationException;
		boolean isWritable(EvaluationContext context) throws EvaluationException;
		boolean isWritable(EvaluationContext context, @Nullable Object rootObject) throws EvaluationException;
		void setValue(@Nullable Object rootObject, @Nullable Object value) throws EvaluationException;
		void setValue(EvaluationContext context, @Nullable Object value) throws EvaluationException;
		void setValue(EvaluationContext context, @Nullable Object rootObject, @Nullable Object value) throws EvaluationException;
	}

	