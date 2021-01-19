---------------------
ObjectUtils			 |
---------------------
	# 关于对象的一些工具方法

		boolean isCheckedException(Throwable ex)
		boolean isCompatibleWithThrowsClause(Throwable ex, @Nullable Class<?>... declaredExceptions) 
		boolean isArray(@Nullable Object obj
		boolean isEmpty(@Nullable Object[] array)
		boolean isEmpty(@Nullable Object obj)
		Object unwrapOptional(@Nullable Object obj)
		boolean containsElement(@Nullable Object[] array, Object element)
		boolean containsConstant(Enum<?>[] enumValues, String constant)
		boolean containsConstant(Enum<?>[] enumValues, String constant, boolean caseSensitive)
		<E extends Enum<?>> E caseInsensitiveValueOf(E[] enumValues, String constant)
		<A, O extends A> A[] addObjectToArray(@Nullable A[] array, @Nullable O obj)
		Object[] toObjectArray(@Nullable Object source)

		boolean nullSafeEquals(@Nullable Object o1, @Nullable Object o2)
		boolean arrayEquals(Object o1, Object o2)
		int nullSafeHashCode(@Nullable Object obj)
		int nullSafeHashCode(@Nullable Object[] array)
		int nullSafeHashCode(@Nullable boolean[] array)
		int nullSafeHashCode(@Nullable byte[] array)
		int nullSafeHashCode(@Nullable char[] array)
		int nullSafeHashCode(@Nullable double[] array)
		int nullSafeHashCode(@Nullable float[] array) 
		int nullSafeHashCode(@Nullable int[] array)
		int nullSafeHashCode(@Nullable long[] array)
		int nullSafeHashCode(@Nullable short[] array)
		String identityToString(@Nullable Object obj)
		String getIdentityHexString(Object obj)
		String getDisplayString(@Nullable Object obj)
		String nullSafeClassName(@Nullable Object obj)
		String nullSafeToString(@Nullable Object obj)
		String nullSafeToString(@Nullable Object[] array)
		String nullSafeToString(@Nullable boolean[] array)
		String nullSafeToString(@Nullable byte[] array) 
		String nullSafeToString(@Nullable char[] array)
		String nullSafeToString(@Nullable double[] array)
		String nullSafeToString(@Nullable float[] array)
		String nullSafeToString(@Nullable int[] array)
		String nullSafeToString(@Nullable long[] array)
		String nullSafeToString(@Nullable short[] array)


