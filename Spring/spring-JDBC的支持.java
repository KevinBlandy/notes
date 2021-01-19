————————————————————————
一,Spring 对JDBC的支持	|
————————————————————————
	> JdbcTemplate
	> org.springframework.jdbc.core.JdbcTemplate
	> 该类是spring对jdbc的支持类,通过这类可以完成一对数据的基础操作
	> JdbcTemplate类被设计为线程安全的,所以可以再IOC容器中声明它的单个实例,并将这个实例注入到所有的DAO实例当中
	> 这里配合的是c3p0数据库连接池(这东西依赖连接池,虽然它自带了一个连接池.但是还是可以用更专业的c3p0)
		* org.springframework.jdbc.datasource.DriverManagerDataSource
	> 跟dbutils灰常的相似
	> 需要导入俩jar包,一个jdbc的,一个事务的
	1,在src目录下准备db.properties文件,里面配置c3p0数据库连接池的各种参数
	2,在xml中进行配置(c3p0实例bean,JdbcTemplate实例bean)
一,applicationContext.xml配置
<bean....>
	<!-- 导入资源文件 -->
	<context:property-placeholder location="classpath:db.properties"/>
	<!-- 配置c3p0数据源 -->
	<bean id="datasource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<!-- 数据库四大参数 -->
		<property name="jdbcUrl" value="${jdbc.jdbcUrl}"/>
		<property name="user" value="${jdbc.user}"/>
		<property name="password" value="${jdbc.password}"/>
		<property name="driverClass" value="${jdbc.driverClass}"/>
		<!-- 池参数 -->
		<property name="acquireIncrement" value="${jdbc.acquireIncrement}"/>
		<property name="initialPoolSize" value="${jdbc.initialPoolSize}"/>
		<property name="minPoolSize" value="${jdbc.minPoolSize}"/>
		<property name="maxPoolSize" value="${jdbc.maxPoolSize}"/>
	</bean>
	<!-- 配置Spring的JDBCTemplate -->
		<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
			<property name="dataSource" ref="datasource"/>
		</bean>
</beans>

二,测试代码演示
package com.kevin.spring.main;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.kevin.spring.jdbc.User;
/**
 * 主方法
 * */
public class Main 
{
	private static ApplicationContext ctx;
	private static JdbcTemplate jdbcTemplate;
	public static void main(String[] args) throws Exception
	{
		/***
		 * 这东西吧,功能并不强大!跟commons-dbutil差不多.看演示吧
		 * */
		 update();
		BatchUpdate();
		queryForObject();
		queryForObjects();
		queryForSingle();
	}
	/**
	 * 单个更新/插入
	 * */
	public static void update()
	{
		String sql ="UPDATE users SET userName=? WHERE passWord=?";
		jdbcTemplate.update(sql,new Object[]{"Litch","1234"});//可变参数
	}
	/**
	 * 批量更新/插入
	 * */
	public static void BatchUpdate()
	{
		/**
		 * 每个 Object[]都代表一条数据的记录
		 * */
		String sql = "INSERT INTO users(userName,passWord,age,gender) VALUES(?,?,?,?)";
		List<Object[]> list = new ArrayList<Object[]>();
		list.add(new Object[]{"one","123",15,"boy"});
		list.add(new Object[]{"two","456",16,"none"});
		list.add(new Object[]{"three","789",19,"girl"});
		jdbcTemplate.batchUpdate(sql,list);
		
	}
	/**
	 * 查询出数据,封装对象
	 * */
	public static void queryForObject()
	{
		/***
		 * 如果根据条件查询出来的数据,不止一条!那么就会抛出异常
		 * 要保证数据库字段名称,跟javaBean的属性名称一样!(别名,可以解决这个问题)
		 * 不支持级联属性(毕竟人家spring是个IOC框架,不是ORM框架.这个只是一个小工具)
		 * */
		String sql = "SELECT * FROM users WHERE userName=?";
		//查询结果生成的对象的类类型
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		User user = jdbcTemplate.queryForObject(sql,rowMapper,new Object[]{"one"});
		System.out.println(user);
	}
	/***
	 * 查询一组对象
	 * */
	public static void queryForObjects()
	{
		String sql = "SELECT * FROM users WHERE userName=?";
		//查询结果生成的对象的类类型
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		List<User> users = jdbcTemplate.query(sql,rowMapper,new Object[]{"Litch"});
		System.out.println(users.size());
	}
	/**
	 * 返回单行单列的数据.例如:count(),max(),min()....这些查询
	 * */
	public static void queryForSingle()
	{
		//查询总记录数
		String sql = "SELECT COUNT(*) FROM users";
		Long l = jdbcTemplate.queryForObject(sql,Long.class);
		System.out.println(l);
	}
	static
	{
		//静态代码块,读取配置文件,创建
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		jdbcTemplate = ctx.getBean("jdbcTemplate",JdbcTemplate.class);
	}
}

————————————————————————
二,JdbcDaoSupport(了解)	|
————————————————————————
	> 这个东西是spring jdbc框架提供,用来简化DAO实现类.该类声明了jdbcTemplate属性,它可以从IOC容器中注入,或者自动从数据源创建
	> 是一个类,让dao层去继承,有个 final 方法不让覆写
	> 不怎么建议用这个东西,推荐直接使用JdbcTemplate作为DAO层的成员变量
		@Repository
		public class UserDao extends JdbcSupport
		{
			@Autowired		//自动装配注解,自动的装配一个DataSource尽量
			public void setDate(DataSource datasource)
			{
				setDateSource(datasource);//调用父类方法,传递数据库连接池
			}
		}
	> 直接就把连接池对象,当作propery 属性注给 jdbcTemplate的实现类, 它就可以根据连接池对象创建模板



————————————————————————
三,JDBC模板中用具名参数 |
————————————————————————
	> NamedParameterJdbcTemplate
	> org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
	* 该类没有无参构造器,必须传递:DataSource 或者一个我也不知道是个啥的参数!
	> 在静待你的JDBC用法中,SQL参数是用占位符"?"问号,表示!并且受到位置的限制
	> 定位参数的问题在于,一旦参数的顺序发生变化!就必须改变参数绑定
	> 在springJDBC框架中,绑定SQL参数的另一种选择就是,使用具名参数(named parameter)
	> 具名参数:SQL按名称(以冒号开头),而不是按位置进行指定,具名参数更易于维护,也提升了可读性!具名参数由框架类在运行时用占位符取代
	> 具名参数,只在NamedParameterJdbcTemplate类中得到支持
	> 这东西的好处就是,参数的位置可以随意更换.不用管!但是要多写一个Map<String,Object>类!而且SQL语句变得更加的复杂
	> 代码演示
package com.kevin.spring.main;
import java.util.Map;
import java.util.HashMap;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
/**
 * 主方法
 * */
public class Main 
{
	private static NamedParameterJdbcTemplate named;
	private static ApplicationContext ctx;
	public static void main(String[] args) throws Exception
	{
		//传统SQL语句写法
		String sql = "INSERT INTO users VALUES(?,?,?,?)";
		//NamedParameterJdbcTemplate的SQL语句写法
		sql = "INSERT INTO users VALUES(:userName,:passWord,:age,:gender)";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userName","Rocco");
		map.put("passWord","a12551255");
		map.put("gender","GIRL");
		map.put("age", 26);	//可以无视参数顺序
		named.update(sql, map);

		/*************还有一种方法******************/
		/**
		* 这个方法的话传递的就不是一个Map,而直接是一个对象!要求对象的属性跟数据库字段对应(有get/set)!有点儿Hibernate的感觉
		* 会使用到一个接口以及实现类,细看
		**/
		User user = new User("name","password",21,"boy");
		SqlParameterSource papramSource = new BeanPropertySqlParameterSource(user);
		named.update(sql,papramSource);
	}
	static
	{
		//静态代码块,读取配置文件,创建
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		named = ctx.getBean("named",NamedParameterJdbcTemplate.class);
	}
}

————————————————————————
四,值得注意的问题		 |
————————————————————————
	1,Spring 不仅对JDBC提供了支持,对很多持久层框架都提供了对应的支持

		Hibernate		-->		HibernateTemplate
		Ibatis			-->		SqlMapClientTemplate
		JPA				-->		JpaTempate
	

	2,Spring 本身也有一个连接池(DataSource)的实现
		* DriverManagerDataSource
		* 具备参数
			1,driverClassName		//JDBC驱动
			2,url					//URL
			3,username				//用户名
			4,password				//密码
	* 可以在核心配置文件中加载这个类,配置属性参数.

	