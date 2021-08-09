----------------------------------
Throwables
----------------------------------
	# �����쳣�Ĺ�����
		public static <X extends Throwable> void propagateIfInstanceOf(@Nullable Throwable throwable, Class<X> declaredType) throws X
		public static void propagateIfPossible(@Nullable Throwable throwable) 
		public static <X extends Throwable> void propagateIfPossible(@Nullable Throwable throwable, Class<X> declaredType) throws X
		public static <X1 extends Throwable, X2 extends Throwable> void propagateIfPossible(@Nullable Throwable throwable, Class<X1> declaredType1, Class<X2> declaredType2) throws X1, X2
		public static RuntimeException propagate(Throwable throwable) 
		public static Throwable getRootCause(Throwable throwable)
		public static List<Throwable> getCausalChain(Throwable throwable)

		public static String getStackTraceAsString(Throwable throwable)
			* ���ַ�����ʽ�����쳣�Ķ�ջ��Ϣ
