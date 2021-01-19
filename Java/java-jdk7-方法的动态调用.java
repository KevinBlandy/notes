------------------------
方法动态调用			|
------------------------

	import java.lang.invoke.MethodHandle;
	import java.lang.invoke.MethodHandles;
	import java.lang.invoke.MethodHandles.Lookup;
	import java.lang.invoke.MethodType;

	public class Main {

		public void println(Object value) {
			System.out.println("value=" + value);
		}
		
		public static MethodHandle getMethodHandle(Object receiver) throws NoSuchMethodException, IllegalAccessException {
			Lookup lookup = MethodHandles.lookup();
			//方法的返回类型和参数类型
			MethodType methodType = MethodType.methodType(void.class,Object.class);
			//获取指定对象的方法名称，以及方法类型，绑定到该对象
			return lookup.findVirtual(receiver.getClass(), "println", methodType).bindTo(receiver);
		}
		
		public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, Throwable {
			getMethodHandle(System.out).invoke("我是System.out");	//我是System.out
			getMethodHandle(new Main()).invoke("我是Main");		//value=我是Main
		}
	}