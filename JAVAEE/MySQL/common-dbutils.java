common-dbutils.jar包的使用

创建此类对象 --- QueryRunner
QueryRunner qr = new QueryRunner(数据库连接池对象);
方法
-----------增删改
   update(String sql,Object... params);//返回int类型
	 -- 可以执行增删改语句,
	 例:
	QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());//创建QueryRunner对象传递连接池对象
	String sql = "insert into users values(?,?,?,?)";//创建SQL模板(增,删，改)
	Object[] params ={"Litch","1234",22,"Boy"};//创建参数
	int u = qr.update(sql,params);//传递SQL模板和参数,执行查询语句.返回被影响的操作行
   update(Connection con,String sql,Object... params)
	 -- 需要调用者提供Connection,说明这个方法不用管理Cconnection
	    * (支持事务)事务操作的时候需要保证是同一个连接,这个方法就能保证
-----------查询
   <T>query(String sql,ResultSetHandler rsh,Object... params);
	 -- 可以执行查询
	 -- 它会先得到ResultSet,然后调用rsh的handle()把rs转换成需要的类型
	  > ResultSetHandler接口的实现类
单个对象  * BeanHandler(Class);(单行)//需要一个Class类型的参数,用来把结果集转换成指定类型的javaBean对象.
对象集合  * BeanListHandler(Class);(多行)//需要一个Class类型的参数,用来把结果集转换成List类型的对象,一堆javaBean. -- List<User> 
			List<User> list = qr.query(sql,new BeanListHandler<User>(User.class));
Map	  * MapHandler(Class);(单行)//把一行结果集转换成一个Map对象 --Map<String, Object>
		-- name age gender
		   kevin 21 男	
		 Map[name - kevin] [age - 21] [gender - 男]
	    //其实就是两个有关联的表,查询出来一行记录.把这个记录中的所有数据都变成map集合,我们可以分别进行对象的封装
List<Map> * MapListHandler();(多行)把每行记录都转换成一个Map,多行就是多个Map 即List<Map>!一个Map对应一行记录
		List<Map<String, Object>> orderItemList = qr.query(sql, new MapListHandler(),order.getOid());
		多表查询懂？
	   //多行记录,封装多个对象,多个对象之间还有关联.
Object	  * ScalarHandler();//单行单列,通常用于:Select count(*) from tb_name;语句,结果集是单行单列,返回一个Object
		qr.query(sql, new ScalarHandler());


   <T>query(Connection conn,String sql,ResultSetHandler rsh,Object... params)
	  --  -- 需要调用者提供Connection,说明这个方法不用管理Cconnection
	    *(支持事务)事务操作的时候需要保证是同一个连接,这个方法就能保证
	 例:
	QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());//创建QueryRunner对象传递连接池对象
	String sql = "select * from users where userName=?";//创建SQL模板
	Object[] params = {"Kevin"};//创建参数,如果是查询所有 select * from tb_name;那么不用给参数,执行的时候也不用给
	User user = qr.query(sql,new BeanHandler<User>(User.class),params);//传递SQL模板和参数,执行查询语句返回对象/或者集合根据第二个参数决定;

批处理执行操作

batch(String sql,Object[][] params);

其中params是多个一维数组,
每个一维数组,都会与sql语句在一起执行一次,多个一维数组,就执行多次

public void addOrderItemList(List<OrderItem> orderItemList)
{
	try
	{
		String sql = "insert into orderitem values(?,?,?,?,?)";
		/**
		 * 把orderItemList 转换成二维数组
		 * 把一个orderItem对象,转换成一个一维数组
		 * */
		Object[][] params = new Object[orderItemList.size()][];
		//循环遍历,orderItemList使用每个对象为params中赋值
		for(int x = 0;x < orderItemList.size();x++)
		{
			OrderItem item = orderItemList.get(x);
			params[x] = new Object[]{item.getIid(),item.getCount(),item.getSubtoltal(),item.getOrder().getOid(),item.getBook().getBid()};
		}
		qr.batch(sql, params);
	}
}

-----------
当一个结果集中,包含了两张表,而我们需要用 MapHandler(); 来进行处理
让它生成多个对象
1,把结果集封装到map(一个map就是一条记录)
2,使用map生成第一个
3,使用map生存第二个对象
4,把两个对象实体建立关系(丢进去)

当结果集有N多个,我们就需要MapListHandler();来进行处理
让它生成多个对象,并且由我们自己进行封装
1,把结果集封装到List<Map<String,Object>>,一个map就是一条记录
2,循环遍历,把每个map的对象都创建出来
3,把对象进行关联,丢进去
4,把最后已经进行了关联的对象装进List容器

BeanListHandler<T>(T.class)			//对象集合

MapListHandler()					//List<Map<String,Object>>

BeanHandler<T>(T.class)				//单个属性

ScalarHandler<T>()					//单个结果集

