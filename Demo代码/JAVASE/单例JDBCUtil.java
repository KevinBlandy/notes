package myjava;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class JdbcUtilsSingle
{
	private String url = "jdbc:mysql://localhost:3306/demo";
	private String user = "root";
	private String password = "root";
	private static JdbcUtilsSingle instance = null;//内部创建对象
	private JdbcUtilsSingle(){}//私有构造函数，禁止创建对象
	public static JdbcUtilsSingle getInstance()//静态获取对象方法
	{
		if(instance == null)
		{
			synchronized(JdbcUtilsSingle.class)
			{
				if(instance == null)
				{
					instance = new JdbcUtilsSingle();
				}
			}
		}
		return instance;
	}
	static//静态模块保证驱动只加载一次。
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
	public Connection getConnection() throws SQLException//获取建立了连接的对象
	{
		return DriverManager.getConnection(url,user,password);
	}
	public void free(ResultSet rs,Statement st,Connection conn)//关闭资源
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

