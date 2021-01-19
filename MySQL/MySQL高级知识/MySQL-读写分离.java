--------------------------------
MySQL - 读写分离				|
-------------------------------
	# 背景
		一般的应用对数据库而言,都是"读多写少",也就是说对数据库读取的压力较大,有一个思路就是采取集群的方式
		其中一个是主要库,负责写入数据,我们称之为:写库
		其他的都是从库,负责读取数据,我们称之为:读库

	# 要求
		1,读库与写库的数据必须一致
		2,写数据别写到主库
		3.读数据必须读到从库
	
	# 解决读写分离的方案的有两种
		1,应用层解决	
			* 通过程序来控制数据库的读写
			
		2,中间件解决
	

	# 原理
		1,master 把数据改变记录到二进制日志:binary.log中,也就是配置文件:log-bin 指定的文件(这些记录叫做二进制日志事件),binary log events
		2,slave 把 master 的 binary log events 拷贝到它的中继日志(relay log)
		3,slave 重做中继日志中的事件,将改变,反映它自己的数据(数据重演)
	
	# 需要注意的地方
		1,主DB的版本和从DB的版本一致
		2,主DB和从BD的数据库数据一直
			* 可以把主的备份在从上执行,也可以直接复制数据目录
		3,主DB开启二进制日志,主DB和从DB的server_id都必须唯一
	

--------------------------------
MySQL - 配置步骤				|
-------------------------------
	* 开始执行主从复制的时候,要保证两个数据库中的数据是一摸一样的
	* 如果出现错误,不要慌.去查看错误日志
	
	# 主库配置
		1,修改配置文件:my.conf
			log_bin=mysql_bin  
				* 开启二进制文件
			server-id = 5
				* 指定服务器ID(随便,但是要唯一,一般取IP后一段)
			binlog-do-db = db
				* 这个属性,是需要手动添加.配置文件中不存在
				* 指定哪些数据库进行同步,不写默认所有
		
		2,重启主库
			
		3,执行SQL语句查询状态
			show master status
				* 需要记录下 File 值,从库执行命令的时候会使用到
				* 需要记录下 Position 值,需要在从库中设置同步的起始值
				+------------------+----------+--------------+------------------+-------------------+
				| File             | Position | Binlog_Do_DB | Binlog_Ignore_DB | Executed_Gtid_Set |
				+------------------+----------+--------------+------------------+-------------------+
				| mysql_bin.000001 |      120 |              |                  |                   |
				+------------------+----------+--------------+------------------+-------------------+

		4,在主库创建同步用户
			* 授权用户 slave01 使用 123456 密码登录 mysql
				grant replication slave on *.* to 'slave01'@'127.0.0.1' identified by '123456';
				flush privileges
			* 可以把IP值替换为'%',表示可以在任意IP连接同步数据
			


	# 从库配置
		1,修改my.conf
			server-id = 指定serve ID,只要不重复,就OK.
			* 从库只有这一个配置文件的操作.其他的都在SQL语句中操作完成
		
		2,执行以下SQL
			change master to
				master_host='120.76.182.243',
				master_user='slave01',
				master_password='a12551255',
				master_port=1124,
				master_log_file='mysql_bin.000007',			
				master_log_pos=1642315;							

		3,启动slave 同步
			start slave;		//开启同步
			stop slave;			//停止同步

		4,查看同步状态
			show slave status\G;
				* 如果下列俩值,都是YES,则设置成功

				Slave_IO_Running:Yes
				Slave_SQL_Running:Yes



-------------------------------
MySQL - 中间件的解决方案		|
-------------------------------
	# 对于开发者是透明的,中间件代理.会自动的去选择处理

	# 优点
		1,程序不要任何的改动,对于开发者是透明的
		2,动态添加数据源不需要重启程序

	# 缺点
		1,程序依赖于中间件,会导致切换数据库变得困难.
		2,由中间件做了中转代理,性能有所下降
		3,其实目前为止.还没有一个比较成熟的中间件产品

	# 相关的中间件产品
		 mysql-proxy
		 Amoeba for MySQL
		
	# 这里不讲...因为要学中间件.哈哈哈哈哈


-------------------------------
MySQL - 应用层的解决方案		|
-------------------------------
	# 实用程序来控制,在程序中进行判断,如果是增删改,则操作主库.如果是读则操作从库.简单
		* 这种方式,程序里面需要准备N个数据源

	# 优点
		1,多数据源切换方便,由程序自动完成
		2,不需要引入中间件
		3,理论上支持任何数据库

	# 缺点
		1,由程序完成,运维参与不到
		2,不能做到动态增加新的数据源
	
	# 使用Spring来完成读写分离
		* 原理图
			--> Request --> Contoller --> AOP(切换数据源) --> Service ---> Dao

		* 在进入Service之前,使用AOP来做出判断,是使用读库,还是写库.
		* 可以根据方法名称来进行判断:query,find,get,load ... ...


	# 代码
		"DynamicDataSource" 类
			public class DynamicDataSource extends AbstractRoutingDataSource{
				protected Object determineCurrentLookupKey() {
					//使用 DynamicDataSourceHolder保存线程安全,并得到当前线程中的数据源key

					/**
						这个方法的返回值,会去父类中的一个Map<String,DataSource>中寻找.匹配到了key,就返回对应的DataSource
					**/
					return DynamicDataSourceHolder.getDataSourceKey();
				}
			}
			* 这个类本身就是DataSource的子类,所以,在spring的xml配置中的DataSource可以配置为它

		"DynamicDataSourceHolder" 类
			public class DynamicDataSourceHolder {
				/**
				 * 写库对应的数据源key
				 * */
				private static final String MASTER = "master";
				/**
				 * 读库对应的数据源key
				 * */
				private static final String SLAVE = "slave";
				/**
				 * 使用ThreadLocal记录当前的数据源key
				 * */
				private static final ThreadLocal<String> holder = new ThreadLocal<String>();
				/**
				 * 设置数据源key
				 * */
				private static void putDataSourceKey(String key){
					holder.set(key);
				}
				/**
				 * 获取数据源key
				 * */
				public static String getDataSourceKey() {
					return holder.get();
				}
				/**
				 * 标记写库
				 * */
				public static void markMaster(){
					putDataSourceKey(MASTER);
				}
				/**
				 * 标记写库
				 * */
				public static void markSlave(){
					putDataSourceKey(SLAVE);
				}
			}
	
		"DataSourceAspect" 类
		public class DataSourceAspect {
				/**
				 * 切面对象
				 * */
				public void before(JoinPoint point){
					String methodName = point.getSignature().getName();
					if(isSlave(methodName)){
						//确定是读,则标记之
						DynamicDataSourceHolder.markSlave();
					}else{
						//反之标记为写
						DynamicDataSourceHolder.markMaster();
					}
				}
				/**
				 * 判断是否是读库
				 * 根据业务层方法名称来确定
				 * */
				private Boolean isSlave(String methodName){
					//只要是指定名称开头的方法,皆为读
					return StringUtils.startsWithAny(methodName, new String[]{"query","get","find","load"});
				}
			}

	# jdbc.properties

		jdbc.master.driver=com.mysql.jdbc.Driver
		jdbc.master.url=jdbc:mysql://127.0.0.1:3306/db?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true																							*/
		jdbc.master.username=root
		jdbc.master.password=123456
		
		# 从库可以有N个
		jdbc.slave01.driver=com.mysql.jdbc.Driver
		jdbc.slave01.url=jdbc:mysql://127.0.0.1:3307/db?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true						
		jdbc.slave01.username=root
		jdbc.slave01.password=123456
	
	# spring 配置
		<bean id="masterDataSource" class="DataSource">
			<!-- 主库连接池属性略 -->
		</bean>

		<bean id="slaveDataSource01" class="DataSource">
			<!-- 从库连接池属性略 -->
		</bean>
		<!-- 定义自己的dataSource,也就是自定义的"AbstractRoutingDataSource"的子类 -->
		<bean id="dataSource" class="com.kevin.blog.web.dataSource.DynamicDataSource">
			<!-- 设置N个数据源 -->
			<property name="targetDataSources">
				<map key-type="java.lang.String">
					<!-- key 要与 DynamicDataSourceHolder中一样.必须的 -->
					<entry key="master" value-ref="masterDataSource"/>
					<entry key="slave" value-ref="slaveDataSource01">
				</map>				
			</property>
			<!-- 设置默认的数据源,默认为写库 -->
			<property name="defaultTargetDataSource" ref="masterDataSource">
		</bean>

		<!-- 
			切面配置 
			其实就是在原始的xml事务的切面中添加配置
		-->
		<!-- 自定义的切面类,也就是区别读写方法的类 -->
		<bean class="com.kevin.blog.web.datasource.DatasourceAspect" id="dataSourceAspect"/>
		<aop:config>
			<!-- 定义事务切面,对所有的Service方法生效 -->
			<aop:pointcut expression="execution(* com.kevin.blog.service.*.*(..))"  id="service"/>
			<!-- 
				应用事务策略到Service切面
			-->
			<aop:advisor pointcut-ref="service"  advice-ref="txAdvice"/>


			<!--
				把切面应用到自定义的切面处理器上,-9999保证该切面优先级最高执行
			-->
			<aop:aspect ref="dataSourceAspect" order="-9999">
				<aop:before method="before" pointcut-ref="service"/>
			</aop:aspect>
		</aop:config>
	
	# 改进切面(进阶,使用事务策略规则来匹配,而不是方法名)
	
	# 一主多从