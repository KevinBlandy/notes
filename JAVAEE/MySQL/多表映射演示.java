import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * 多表查询,结果集映射演示
 * 需要BeanUtils,c3p0连接池,dbutils的支持
 * */
public class Demo
{
	private static QueryRunner qr = new QueryRunner(new ComboPooledDataSource());
	public static void main(String[] args) throws Exception
	{
		demo();//单个结果集封装演示
		demo("123456");//多个结果集封装演示
	}
	/**
	 * 单行结果集的封装演示
	 * */
	public static void demo() throws Exception
	{
		String s = "1";//查询指定的主键,也就是只有一条记录
		String sql = "select * from user ,class where user.cid=class.cid and uid=?";
		Object[] params = {s};
		//得到map集合
		Map<String, Object> map = qr.query(sql, new MapHandler(),params);
		//把map中的部分数数据进行封装
		User user = new User();
		//BeanUtils的一件封装
		BeanUtils.populate(user, map);
		Class c = new Class();
		BeanUtils.populate(c, map);
		user.setC(c);
		System.out.println(user);
		System.out.println(user.getC());
	}
	/**
	 * 多行结果集的封装演示
	 * */
	public static void demo(String param)throws Exception
	{
		String sql = "select * from user,class where user.cid=class.cid and user.password=?";
		//执行查询,返回的的是一个map集合
		List<Map<String,Object>> list = qr.query(sql, new MapListHandler(),param);
		List<User> userlist = new ArrayList<User>();//多个结果集,所有准备容器
		for(int x = 0;x < list.size();x++)
		{
			Map<String,Object> map = list.get(x);//把单个map取出
			User u = new User();//创建对象
			Class c = new Class();//创建对象
			BeanUtils.populate(u, map);//封装数据
			BeanUtils.populate(c, map);//封装数据
			u.setC(c);//建立关系,其实也就是把这个c放到了u对象中
			userlist.add(u);//把最终的对象放置到容器,可以返回给service层
		}
		for (int x = 0; x < userlist.size(); x++) 
		{
			User u = userlist.get(x);
			System.out.println(u.toString());
			System.out.println(u.getC().toString());
		}
	}
}