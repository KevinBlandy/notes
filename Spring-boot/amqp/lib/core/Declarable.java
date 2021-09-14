--------------------------
Declarable
--------------------------
	# 接口
		public interface Declarable

		boolean shouldDeclare();

		Collection<?> getDeclaringAdmins();

		boolean isIgnoreDeclarationExceptions();

		void setAdminsThatShouldDeclare(Object... adminArgs);

		default void addArgument(String name, Object value);

		default @Nullable Object removeArgument(String name);
