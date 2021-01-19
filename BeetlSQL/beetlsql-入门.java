----------------------------
Beetlsql-入门				|
----------------------------


----------------------------
Beetlsql-HelloWorld			|
----------------------------
	//连接源
	ConnectionSource connectionSource = ConnectionSourceHelper.getSimple(DRIVER,URL,USERNAME,PASSWORD);
	//数据库类型
	DBStyle dbStyle = new MySqlStyle();
	//SQL资源加载器
	SQLLoader sqlLoader = new ClasspathLoader("/sql");
	//命名匹配策略
	UnderlinedNameConversion underlinedNameConversion = new UnderlinedNameConversion();
	//SQLManager
	SQLManager sqlManager = new SQLManager(dbStyle,sqlLoader,connectionSource,underlinedNameConversion,new Interceptor[]{new DebugInterceptor()});

	//创建记录
	User user = new User();
	user.setAge(23);
	user.setName("KevinBlandy");
	sqlManager.insert(user);

	//根据主键检索
	int id = 1;
	user = sqlManager.unique(User.class,id);
	System.out.println(user);

	//根据非空参数更新
	User updateUser = new User();
	updateUser.setId(1);
	updateUser.setAge(22);
	sqlManager.updateTemplateById(updateUser);

	//根据非空参数检索
	User queryUser = new User();
	queryUser.setName("KevinBlandy");
	List<User> users = sqlManager.template(queryUser);

	//使用md/sql文件进行检索
	User query = new User();
	query.setName("KevinBlandy");
	List<User> userList = sqlManager.select("user.select",User.class,query);


