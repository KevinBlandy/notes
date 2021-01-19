----------------------------
Jooq
----------------------------
	# JOOQ
		* 全称Java Object Oriented Querying, 即面向Java对象查询
		* 它是Data Geekery公司研发的DA方案(Data Access Layer), 主要解决两个问题

	# 官方地址
		https://www.jooq.org/
		https://www.jooq.org/learn/
		https://github.com/jOOQ/jOOQ
		https://www.jooq.org/doc/current/manual-single-page/

	# 参考学习
		https://amao12580.github.io/post/2016/04/JOOQ-from-entry-to-improve/

	# Maven 依赖
		<dependency>
			<groupId>org.jooq</groupId>
			<artifactId>jooq</artifactId>
		</dependency>
		<dependency>
			<groupId>org.jooq</groupId>
			<artifactId>jooq-meta</artifactId>
		</dependency>
		<dependency>
			<groupId>org.jooq</groupId>
			<artifactId>jooq-codegen</artifactId>
		</dependency>
	
	
	# 入门
		// 初始化Connection
		try(Connection connection = DriverManager.getConnection("jdbc....", "root", "root")){
			// 初始化dslContext，指定jdbc链接和数据库方言
			try(DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL)){
				// 执行请求，获取到执行结果
				Result<Record> result = dslContext.select().from(Tables.AUTHOR).fetch();
				System.out.println(result);
			}
		} 

----------------------------
Jooq 自动生成
----------------------------
	# 通过命令生成
		* windows
			java -classpath jooq-3.13.2.jar;^
			jooq-meta-3.13.2.jar;^
			jooq-codegen-3.13.2.jar;^
			reactive-streams-1.0.2.jar;^
			mysql-connector-java-5.1.18-bin.jar;. ^
			org.jooq.codegen.GenerationTool jooq.xml
		
		* Linux/Mac
			java -classpath jooq-3.13.2.jar:\
			jooq-meta-3.13.2.jar:\
			jooq-codegen-3.13.2.jar:\
			reactive-streams-1.0.2.jar:\
			mysql-connector-java-5.1.18-bin.jar:. \
			org.jooq.codegen.GenerationTool jooq.xml

	# 通过代码生成
		import org.jooq.codegen.GenerationTool;
		import org.springframework.core.io.ClassPathResource;

		public class JooqGeneration {
			public static void main(String[] args) throws Exception {
				// 从classpath读取到文件资源
				ClassPathResource classPathResource = new ClassPathResource("/jooq/jooq.xml");
				// 获取到文件资源的绝对路径
				String path = classPathResource.getFile().getAbsolutePath();
				// 调用 GenerationTool 完成生成
				GenerationTool.main(new String[] {path});
			}
		}
	
	# maven自动生成插件（建议）
		jooq-plugin.xml
