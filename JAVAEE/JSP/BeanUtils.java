import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
/**
 * 强悍的Beanutils演示
 * */
public class Demo
{
	private static String className = "User";
	public static void main(String[] args) throws Exception
	{
		test();
		demo();
		useMap();
	}
	public static void test()throws Exception
	{
		/**
		 * 传统反射方法的演示
		 * */
		Class clazz = Class.forName(className);
		Object bean = clazz.newInstance();//通过反射创建对象
		Method setUserName = clazz.getMethod("setUserName",String.class);//获取setUserName方法
		Method setPassWord = clazz.getMethod("setPassWord",String.class);//获取setPassWord方法
		Method setAge = clazz.getMethod("setAge",int.class);//获取setAge方法
		Method setGender = clazz.getMethod("setGender",String.class);//获取setAge方法
		/**赋值操作**/
		setUserName.invoke(bean, "Litch");
		setPassWord.invoke(bean, "a12551255");
		setAge.invoke(bean, 22);
		setGender.invoke(bean, "男");
		System.out.println(bean);
		/**读取操作**/
		Method getUserName = clazz.getMethod("getUserName");
		Method getPassWord = clazz.getMethod("getPassWord");
		Method getAge = clazz.getMethod("getAge");
		Method getGender = clazz.getMethod("getGender");
		String userName = (String) getUserName.invoke(bean);
		String passWord = (String) getPassWord.invoke(bean);
		int age = (Integer) getAge.invoke(bean);
		String gender = (String) getGender.invoke(bean);
		System.out.println(userName+":"+passWord+":"+age+":"+gender);
	}
	public static void demo() throws Exception
	{
		/**
		 * beanutils的演示
		 * */
		Class clazz = Class.forName(className);
		Object bean = clazz.newInstance();
		/**赋值操作**/
		BeanUtils.setProperty(bean, "userName", "Kevin");//给userName属性赋值
		BeanUtils.setProperty(bean, "passWord", "123456");//给passWord属性赋值
		BeanUtils.setProperty(bean, "age", 22);//给age属性赋值,就算22是一个字符串,也能正确的赋值进去
		BeanUtils.setProperty(bean, "gender", "男");//给gender属性赋值
		BeanUtils.setProperty(bean, "没有的字段", "瞎赋");//给根本就不存在的属性赋值,也是不会报错的
		System.out.println(bean);//调用toString测试对象是否赋值成功
		/**读取操作**/
		String userName = BeanUtils.getProperty(bean, "userName");
		String passWord = BeanUtils.getProperty(bean, "passWord");
		String age = BeanUtils.getProperty(bean, "age");	//int类型的字段返回的是String
		String gender = BeanUtils.getProperty(bean, "gender");
		System.out.println(userName+":"+passWord+":"+age+":"+gender);
	}
	public static void useMap()throws Exception
	{
		/**
		 * Map封装
		 * 把map中的数据直接封装到javaBean中,通过beanutils
		 * 要保证Map键跟javaBean的属性名称一模一样！
		 * */
		Class clazz = Class.forName(className);
		Object bean = clazz.newInstance();
		Map<String,String> map = new HashMap<String,String>();
		map.put("userName", "Rocco");
		map.put("passWord", "a12551255");
		map.put("age", "23");
		map.put("gender", "男");
		BeanUtils.populate(bean, map);//直接把Map中的数据直接封装到对象中
		System.out.println(bean);
	}
}