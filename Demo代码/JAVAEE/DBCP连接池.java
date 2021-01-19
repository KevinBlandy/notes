import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;


/**
 * DBCP连接池
 * */
public class Demo
{
	public static void main(String[] args) throws SQLException
	{
		method1();
	}
	public static void method1() throws SQLException
	{
		/**
		 * 创建连接池对象,配置四大参数
		 * 配置池参数
		 * 得到连接对象
		 * */
		BasicDataSource dataSource = new BasicDataSource();//创建连接池对象
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");//配置四大参数
		dataSource.setUrl("jdbc:mysql://localhost:3306/demo");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		dataSource.setMaxActive(20);//设置最大活动连接数
		dataSource.setMinIdle(3);//设置最小的空闲连接
		dataSource.setMaxWait(1000);//设置最大等待时间
		Connection conn = dataSource.getConnection();//得到连接池对象
		System.out.println(conn.getClass().getName());
	}
}