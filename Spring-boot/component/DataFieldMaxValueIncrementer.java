--------------------------------
DataFieldMaxValueIncrementer	|
--------------------------------
	# 用于获取DB自增的主键
	# 不同的DB,有不同的实现，主要有2个子类
		AbstractSequenceMaxAbstractSequence
			* 使用标准的数据库序列产生主键值、

		AbstractColumnMaxValueIncrementer
			* 使用一张模拟序列的表产生主键值
			* 可以通过cacheSize属性指定缓存的主键个数，当内存中主键值用完后，递增器将一次性获取cacheSize个主键，这样可以减少数据库访问的次数，提高应用的性能。

			* mysql实现
				MySQLMaxValueIncrementer

		
	
	# DataFieldMaxValueIncrementer 接口定义了3个获取下一个主键值的方法
	　　int nextIntValue()
			* 获取下一个主键值,主键数据类型为int

	　　long nextLongValue()
			* 获取下一个主键值，主键数据类型为long

	　　String nextStringValue()
			* 获取下一个主键值,主键数据类型为String
	

	# SpringBoot 配置
		import javax.sql.DataSource;

		import org.springframework.beans.factory.annotation.Autowired;
		import org.springframework.context.annotation.Bean;
		import org.springframework.context.annotation.Configuration;
		import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
		import org.springframework.jdbc.support.incrementer.MySQLMaxValueIncrementer;

		@Configuration
		public class MySQLDataFieldMaxValueIncrementer {
			
			@Bean
			public DataFieldMaxValueIncrementer dataFieldMaxValueIncrementer (@Autowired DataSource dataSource) {
				MySQLMaxValueIncrementer mySQLMaxValueIncrementer  = new MySQLMaxValueIncrementer();
				/**
				 * 数据源
				 */
				mySQLMaxValueIncrementer.setDataSource(dataSource);
				/**
				 * 维护主键的表名
				 */
				mySQLMaxValueIncrementer.setIncrementerName("inrement");
				/**
				 * 维护主键表中的列名
				 */
				mySQLMaxValueIncrementer.setColumnName("serial");
				/**
				 * 字符串结果的填充长度，不足长度会在前面填充0
				 */
				mySQLMaxValueIncrementer.setPaddingLength(10);
				/**
				 * 是否每次操作都是使用新的链接，默认为true
				 */
				mySQLMaxValueIncrementer.setUseNewConnection(false);
				/**
				 * 设置缓存主键的个数，当内存中主键值用完后，递增器将一次性获取cacheSize个主键
				 */
				mySQLMaxValueIncrementer.setCacheSize(1);
				
				return mySQLMaxValueIncrementer;
			}
		}
	
	# 使用
		@Autowired
		private DataFieldMaxValueIncrementer dataFieldMaxValueIncrementer;

		@Test
		public void test () {
			int val = this.dataFieldMaxValueIncrementer.nextIntValue();
			System.out.println(val);
		}