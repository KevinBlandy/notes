import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import com.kevin.utils.JDBCUtils;
public class UtilsDemo 
{
	public static void main(String[] args) throws SQLException 
	{
		update();
		find();
	}
	public static void update() throws SQLException
	{
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());//创建QueryRunner对象传递连接池对象
		String sql = "insert into users values(?,?,?,?)";//创建SQL模板(增,删，改)
		Object[] params ={"Litch","1234",22,"Boy"};//创建参数
		int u = qr.update(sql,params);//传递SQL模板和参数,执行查询语句.返回被影响的操作行
	}
	public static void find() throws SQLException
	{
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());//创建QueryRunner对象传递连接池对象
		String sql = "select * from users where userName=?";//创建SQL模板
		Object[] params = {"Kevin"};//创建参数
		User user = qr.query(sql,new BeanHandler(User.class),params);//传递SQL模板和参数,执行查询语句返回对象/或者集合根据第二个参数决定
	}
}
