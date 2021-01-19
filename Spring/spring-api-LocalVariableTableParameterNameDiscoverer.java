--------------------------------------------
LocalVariableTableParameterNameDiscoverer	|
--------------------------------------------
	# 一个工具类,可以通过反射,获取到方法的参数名称

	import java.lang.reflect.Method;

	import org.assertj.core.util.Arrays;
	import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

	public class ParameterNameTest {
		
		public static void main(String[] args) throws NoSuchMethodException, SecurityException {
			
			LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
			
			//目标方法对象
			Method method = ParameterNameTest.class.getMethod("main", String[].class);
			
			String[] names = localVariableTableParameterNameDiscoverer.getParameterNames(method);
			
			//localVariableTableParameterNameDiscoverer.getParameterNames(Constructor<?> constructor);		还可以获取构造器的参数名称
			
			System.out.println(Arrays.asList(names));		//[args]
		}
	}

	# jdk8,已经具备了可以直接获取到参数对象的API,但是需要在编译的时候添加参数,让编译器保留参数名称属性
