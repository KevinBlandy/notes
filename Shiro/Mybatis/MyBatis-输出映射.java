————————————————————————
1,MyBatis-输出映射		|
————————————————————————
	* MyBatis 有两种方式的输出映射
	1,resultType
		输出POJO类型
		* 使用resultType进行输出映射,只有查询出来的列明和POJO中的属性名一致,那么该列才能映射成功!
		  如果查询出的列名,和属性名全部不一致,那么根本就不会创建这个POJO对象
		* 只要查询出来的列名和POJO的属性名,至少有一个相同.那么就会创建POJO对象

		输出简单类型
		* 其实就是聚合函数那一套,查询数量啊,平均值啊,总和啊
		* 当查询出来的结果集是单行单列的时候,可以使用简单类型来进行输出的映射
		<select id="count" resultType="java.lang.Integer">
			SELECT COUNT(*) FROM user;
		</select>

		['总结']
			* 不管是输出POJO单个对象还是一个列表(包含了POJO的List)
			  那么在mapper.xml中的resultType指定的类型都是一样的,不一样的地方是在mapper接口中方法的返回值不一样
			* 生成的动态代理对象,是根据mapper方法的返回值,来确定是selectOne(),还是selectList()
		
		输出HashMap类型
			* 把输出结果类型改为:java.util.HashMap
			* 结果集字段名称为key,值为value

	2,resultMap
		* MyBatis中可以使用resultMap来完成高级的输出结果映射
		* 也就是所谓的:一对多,一对一... ...等等
		
		* 如果说查询出来的列名和POJO的属性名称不一致,还想映射成为POJO.那么就可以定义一个resultMap来对列名和属性名之间来做一个映射关系
		1,定义resultMap
		2,使用resultMap,来作为statement的输出类型
			<!-- 定义输出类型
			1,type :指的是,该映射最终映射出来的结果对象类型
			2,id:表示的是,查询结果集中的唯一标识的定义
				column:查询出来的列名
				property:POJO的属性名
			* 如果是,多个字段组成的唯一标识.那么就定义多个id
			3,result:表示的是,普通列的定义
				column:查询出来的列名
				property:POJO的属性名
			 -->
			<resultMap type="com.kevin.domian.User" id="user">
				<id column="userid" property="id"/>
				<result column="name" property="username"/>
			</resultMap>
			<!--定义输出映射的map
				resultMap:指定要使用哪个resultMap映射器
					* 如果制定的映射器在其他的的mapper.xml中,那么需要加上命名空间:xxx.xxx
			-->
			<select id="find" parameterType="int" resultMap="user">
				SELECT u.id userid ,u.username name FROM user u WHERE u.id=#{id}
			</select>

————————————————————————————————————
2,resultMap与resultType总结			|
————————————————————————————————————
	resultType
		* 实现简单,只要保证POJO的属性,跟查询结果列中的列名全部对应,就OK,还不会使用到reultMap.
		* 不能实现延迟加载(数据都尼玛一波装对象里面了,延迟加个毛啊)
	resultMap
		* 实现比较麻烦,需要自己定义 resultMap,但是它更加的符合JAVA的面向对象思想.
		* 可以实现延迟加载
	

	
	