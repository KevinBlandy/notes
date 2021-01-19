---------------------------
MyBatis-通用Mapper			|
---------------------------
	# 只写接口,不写配置文件.实现CRUD
	# maven仓库地址
		<dependency>
			<groupId>com.github.abel533</groupId>
			<artifactId>mapper</artifactId>
			<version>${abel533.version}</version>
		</dependency>


---------------------------
MyBatis-集成Mapper			|
---------------------------
	# 一共有三种方式来集成Mapper
	1,JAVA编码方式

	2,Spring 集成('有BUG存在')

	3,拦截器
		* 其实就是mybatis全局配置中的一个插件
			<plugins>
				<!--
					指定拦截器类
				-->
				<plugin interceptor="com.github.abel533.mapperhelper.MapperInterceptor">
					<!--
						主键自增回写方法,默认值MYSQL
					-->
					<property name="IDENTITY" value="MYSQL" />
					<property name="mappers" value="com.github.abel533.mapper.Mapper" />
				</plugin>
			</plugins>

---------------------------
MyBatis-mapper接口,实现		|
---------------------------
	# mapper接口实现:com.github.abel533.mapper.Mapper<T>	接口
	# 该泛型就是:'当前Mapper要操作的实体对象'
	# 一旦实现此接口,就有下面的一些方式
			//根据实体类不为null的字段进行查询,条件全部使用=号and条件
		List<T> select(T record);
			//根据实体类不为null的字段查询总数,条件全部使用=号and条件
		int selectCount(T record);
			//根据主键进行查询,必须保证结果唯一
			//单个字段做主键时,可以直接写主键的值
			//联合主键时,key可以是实体类,也可以是Map
		T selectByPrimaryKey(Object key);
			//插入一条数据
			//支持Oracle序列,UUID,类似Mysql的INDENTITY自动增长(自动回写)
			//优先使用传入的参数值,参数值空时,才会使用序列、UUID,自动增长
		int insert(T record);
			//插入一条数据,只插入不为null的字段,不会影响有默认值的字段
			//支持Oracle序列,UUID,类似Mysql的INDENTITY自动增长(自动回写)
			//优先使用传入的参数值,参数值空时,才会使用序列、UUID,自动增长
		int insertSelective(T record);
			//根据实体类中字段不为null的条件进行删除,条件全部使用=号and条件
		int delete(T key);
			//通过主键进行删除,这里最多只会删除一条数据
			//单个字段做主键时,可以直接写主键的值
			//联合主键时,key可以是实体类,也可以是Map
		int deleteByPrimaryKey(Object key);
			//根据主键进行更新,这里最多只会更新一条数据
			//参数为实体类
		int updateByPrimaryKey(T record);
			//根据主键进行更新
			//只会更新不是null的数据
		int updateByPrimaryKeySelective(T record);
			//根据Exmaple条件查询总数
		int selectCountByExample(Object example);
			//根据Exmaple条件删除
		int deleteByExample(Object example);
			//根据Exmaple条件查询
		List<T> selectByExample(Object example);
			//根据Exmaple条件更新非空(null)字段
		int updateByExampleSelective(@Param("record") T record, @Param("example") Object example);
			//根据Exmaple条件更新全部字段
		int updateByExample(@Param("record") T record, @Param("example") Object example);
	
	# 对于'要操作的实体类',是有一定条件的
		1,类和表名绑定
			@Table(name="tb_user")
		2,属性和表列名绑定
			@Column(name="id")
		3,忽略某个字段,被这个注解标识的属性不会被当作数据库字段来使用
			@Transient
		4,绑定主键字段,可以有多个来形成联合主键
			@id
		4,指定主键的增长形式,标识在ID上
			UUID:
				@GeneratedValue(generator = "UUID")
			自增长:
				@GeneratedValue(strategy = GenerationType.IDENTITY)
				
---------------------------
MyBatis-在全局配置中扫描	|
---------------------------
	<package name="com.kevin.mybatis.mapper"/>
	# 不多说,你懂

---------------------------
MyBatis-实现操作			|
---------------------------
	# 直接获取到session.getMapper(Mapper接口.class);
	# 然后就是使用那些来自于父类的方法