---------------------------
自定义安全管理器			|
---------------------------
	# 所有模板的本地调用都需要通过安全管理器校验
	# 默认需要实现 NativeSecurityManager 的 public boolean permit(String resourceId, Class c, Object target, String method) 方法
		* NativeSecurityManager
		* public boolean permit(String resourceId, Class c, Object target, String method)
	# Demo
		public class DefaultNativeSecurityManager implements NativeSecurityManager{

			@Override
			public boolean permit(String resourceId, Class c, Object target, String method){
					if (c.isArray()){
						//允许调用，但实际上会在在其后调用中报错。不归此处管理
						return true;
					}
					String name = c.getSimpleName();
					String pkg = c.getPackage().getName();
					//Runtime,Process,ProcessBuilder 类都不让调用
					if (pkg.startsWith("java.lang")){
							if (name.equals("Runtime") || name.equals("Process") || name.equals("ProcessBuilder")
											|| name.equals("System")){
									return false;
							}
					}
					return true;
			}
		}

