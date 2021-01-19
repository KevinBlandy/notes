package jdbc;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mysql.jdbc.Connection;

public class CRUD 
{
	public static void main(String[] args)
	{
		
	}
	static void creater()
	{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try
		{
			conn = (Connection) Util.getConnection();
			st = conn.createStatement();
			String sql = "insert into user(name,birthday,money)value('name','1993-12-5,'9527'')";
			int i = st.executeUpdate(sql);
		}
		catch(Exception e){e.printStackTrace();}
		finally
		{
			Util.free(rs, st, conn);
		}
	}
	static void read()
	{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try
		{
			conn = (Connection) Util.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("select id,name,birthday,money from user");
			while(rs.next())
			{
				System.out.println(rs.getObject("id")+"/t"+rs.getObject("name")+"/t"+rs.getObject("birthday")+"/t"+rs.getObject("money"));
			}
		}
		catch(Exception e){e.printStackTrace();}
		finally
		{
			Util.free(rs, st, conn);
		}
	}
	static void update()
	{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try
		{
			conn = (Connection) Util.getConnection();
			st = conn.createStatement();
			String sql = "update user set money=money+10";
			int i = st.executeUpdate(sql);
		}
		catch(Exception e){e.printStackTrace();}
		finally
		{
			Util.free(rs, st, conn);
		}
	}
	static void delete()
	{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try
		{
			conn = (Connection) Util.getConnection();
			st = conn.createStatement();
			String sql="delete from user where id>4";
			int i = st.executeUpdate(sql);
			while(rs.next())
			{
				System.out.println(rs.getObject("id")+"/t"+rs.getObject("name")+"/t"+rs.getObject("birthday")+"/t"+rs.getObject("money"));
			}
		}
		catch(Exception e){e.printStackTrace();}
		finally
		{
			Util.free(rs, st, conn);
		}
	}
}
