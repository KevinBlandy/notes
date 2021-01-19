--------------------------
JdbcTemplate			  |
--------------------------

	<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
		<property name="dataSource" ref="datasource"/>
	</bean>


--------------------------
NamedParameterJdbcTemplate|
--------------------------
	> org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
		* 该类没有无参构造器,必须传递:DataSource 或者一个我也不知道是个啥的参数!

	> 在静待你的JDBC用法中,SQL参数是用占位符"?"问号,表示!并且受到位置的限制
	> 定位参数的问题在于,一旦参数的顺序发生变化!就必须改变参数绑定
	> 在springJDBC框架中,绑定SQL参数的另一种选择就是,使用具名参数(named parameter)
	> 具名参数:SQL按名称(以冒号开头),而不是按位置进行指定,具名参数更易于维护,也提升了可读性!具名参数由框架类在运行时用占位符取代
	> 具名参数,只在NamedParameterJdbcTemplate类中得到支持
	> 这东西的好处就是,参数的位置可以随意更换.不用管!但是要多写一个Map<String,Object>类!而且SQL语句变得更加的复杂