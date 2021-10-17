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

		https://www.jooq.org/javadoc/latest/org.jooq/module-summary.html
		

	# 参考学习
		https://amao12580.github.io/post/2016/04/JOOQ-from-entry-to-improve/
		https://jooq.diamondfsd.com/learn/section-1-how-to-start.html

		https://www.bilibili.com/video/BV18Q4y1K73V
		https://segmentfault.com/blog/xujian
	
	# 工具
		https://github.com/sevendark/IDEACodeTools
			* 可以把SQL转换为JOOQ的代码语法
		
		https://www.jooq.org/translate/
			* 在线SQL翻译

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
	
		DSLContext context = DSL.using(connection, new DefaultConfiguration().settings()
									   .withRenderKeywordCase(RenderKeywordCase.UPPER) // 关键字大写
									   .withRenderFormatted(true)						// 格式化输出的SQL
									   .withMapJPAAnnotations(false));                  // 不解析JPA注解
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
		
		* 生成命令:
			mvn jooq-codegen:generate
	
	
	# 生成的目录结构
		generated
			|-tables
				|-daos
				|-interfaces
				|-pojos
				|-records
			|-DefaultCatalog
			|-Indexes
			|-Jooq
			|-Keys
			|-Tables
		
		* daos
			* 

		* interfaces
			* 抽象了pojo(数据库字段)的 getter/setter 方法
		
		* pojos 
			* 针对数据表生成的对象

		* records
			* 数据表的每一行记录



----------------------------
核心的一些库
----------------------------
	org.jooq.DSLContext
		* 核心的库，它负责完成CRUD等等操作

	org.jooq.Result
		* 结果集封装，实现了 List 接口
	
	org.jooq.Record
		* 结果集中的每一行纪录，相同预定义了最长22个长度的行: Record22
		* 每一张表，都有对应的 XxxRecord 实现
		* 这个对象可以和POjo互相转换
	
	org.jooq.TableField
		* 每一条记录的，每一个字段
	
