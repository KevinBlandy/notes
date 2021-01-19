关于JAVA的JDBC
	IDBC是由一些接口构成的API(Java Data Base Connectivity,java数据库连接);
	连接数据库的步骤
	java,sql   javax,sql   //俩包
①注册驱动(只做一次)
②建立连接(Connection)
③创建执行SQL语句(Statement)
④执行语句
⑤处理执行结果(ResultSet)
⑥释放资源

DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	|--驱动注册.注册多个驱动的时候。中间用:隔开。
System.setProperty("jdbc.drivers","com.mysql.jdbc.Driver");
	|--驱动注册。
Class.forName("con.mysql.jdbc.Driver");
	|--驱动注册。
建立连接——
	Connection conn = DriverManager.getConnection(url,user,password);
MySql——url格式
	JDBC:子协议:子名称//主机名:端口/数据库名?属性名=属性值&
	本机连接:"jdbc:mysql:///jdbc";
User,password
	属性名=属性值
其他参数
	useUnicode=true
----------------------------------------------------------------------------------------------------------------
Connection  -- JAVA连接数据的对象接口。每一个连接都是有一个 Connection
它是一个接口无法创建对象。需要靠 DriverManager 类来获取 Connection 的对象。