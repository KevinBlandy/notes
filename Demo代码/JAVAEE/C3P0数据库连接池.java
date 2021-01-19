import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;


/**
 * C3P0连接池
 * */
public class Demo
{
	public static void main(String[] args) throws SQLException, PropertyVetoException
	{
		method1();
	}
	public static void method1() throws SQLException, PropertyVetoException
	{
		/**
		 * 创建连接池对象,配置四大参数
		 * 配置池参数
		 * 得到连接对象
		 * */
		ComboPooledDataSource ds = new ComboPooledDataSource();
		ds.setJdbcUrl("jdbc:mysql://localhost:3306/demo");//配置4大参数
		ds.setUser("root");
		ds.setPassword("root");
		ds.setDriverClass("com.mysql.jdbc.Driver");
		//配置池参数
		ds.setAcquireIncrement(5);//配置连接数的增量
		ds.setInitialPoolSize(20);//池的初始大小
		ds.setMinPoolSize(2);//最小连接数
		ds.setMaxPoolSize(50);//最大连接数
		Connection conn = ds.getConnection();
		System.out.println(conn);
		conn.close();
	}
}