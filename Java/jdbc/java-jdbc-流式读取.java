package app.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ApplicationMainTest {

	public static void main(String[] args) throws Exception {
		

		// 注意，流式查询，连接对象不能公用，也就是说在迭代期间，不能把连接用于其他查询
		try (Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true", "root", "root")) {

			// 设置查询语句，和 ResultSet 设置
			// ResultSet.TYPE_FORWARD_ONLY：只允许结果集的游标向下移动。
			// ResultSet.CONCUR_READ_ONLY：游标只读
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM sku ORDER BY id", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			// 一定要把 FetchSize 设置为 nteger.MIN_VALUE， 在执行 ResultSet.next() 方法时, 会通过数据库连接一条一条的返回, 这样也不会大量占用客户端的内存.
			// MySQL 驱动实现做了特殊的判断
			statement.setFetchSize(Integer.MIN_VALUE);
			
			
			// 开始流式查询，迭代每一条记录
			try(ResultSet resultSet = statement.executeQuery()){
				while (resultSet.next()) {
					Long id = resultSet.getLong("id");
					System.out.println(id);
				}
			}
		}
	}
}