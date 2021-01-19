package myjava;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public final class JdbcUtils//不允许继承
{
	private static String url = "jdbc:mysql://localhost:3306/demo";//连接地址
	private static String user = "root";//用户名
	private static String password = "root";//密码
	private JdbcUtils(){};//禁止创建对象不允许继承
	static//静态代码块，保证了驱动只注册一次
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(ClassNotFoundException e)
		{
			throw new ExceptionInInitializerError(e);
		}
	}
	public static Connection getConnection() throws SQLException//获取建立了连接的对象
	{
		return DriverManager.getConnection(url,user,password);
	}
	public static void free(ResultSet rs,Statement st,Connection conn)//关闭资源
	{
		try
		{
			if(rs != null)
			{
				rs.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(st != null)
				{
					st.close();
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(conn!=null)
				{
					try 
					{
						conn.close();
					} catch (SQLException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}	
}
