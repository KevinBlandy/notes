-------------------------
JobStoreTX				 |
-------------------------
	# 使用jdbc的JobStroe


-------------------------
JobStor配置				 |
-------------------------
org.quartz.jobStore.driverDelegateClass				yes		string	null
	* 指定jdbc的驱动, 可选的配置项
		org.quartz.impl.jdbcjobstore.StdJDBCDelegate(用于完全兼容JDBC的驱动程序)
		org.quartz.impl.jdbcjobstore.MSSQLDelegate(用于Microsoft SQL Server和Sybase)
		org.quartz.impl.jdbcjobstore.PostgreSQLDelegate
		org.quartz.impl.jdbcjobstore.WebLogicDelegate(用于WebLogic驱动程序)
		org.quartz.impl.jdbcjobstore.oracle.OracleDelegate
		org.quartz.impl.jdbcjobstore.oracle.WebLogicOracleDelegate(用于Weblogic中使用的Oracle驱动程序)
		org.quartz.impl.jdbcjobstore.oracle.weblogic.WebLogicOracleDelegate(用于Weblogic中使用的Oracle驱动程序)
		org.quartz.impl.jdbcjobstore.CloudscapeDelegate
		org.quartz.impl.jdbcjobstore.DB2v6Delegate
		org.quartz.impl.jdbcjobstore.DB2v7Delegate
		org.quartz.impl.jdbcjobstore.DB2v8Delegate
		org.quartz.impl.jdbcjobstore.HSQLDBDelegate
		org.quartz.impl.jdbcjobstore.PointbaseDelegate
		org.quartz.impl.jdbcjobstore.SybaseDelegate

org.quartz.jobStore.dataSource						yes		string	null
	* 指定数据源的名称

org.quartz.jobStore.tablePrefix						no		string	"QRTZ_"
	* 数据表的前缀

org.quartz.jobStore.useProperties					no		boolean	false
	* 表示JDBCJobStore JobDataMaps中的所有值都是字符串, 因此可以将其存储为名称-值对, 而不是将更复杂的对象以其序列化形式存储在BLOB列中
	* 这很方便, 因为避免了将非String类序列化为BLOB可能引起的类版本控制问题

org.quartz.jobStore.misfireThreshold				no		int		60000
	* 调度程序在被认为是"误触发"之前将"容忍"触发器经过下一次触发时间的毫秒数

org.quartz.jobStore.isClustered						n		boolean	false
	* 设置为 "true" 以打开群集功能
	* 如果多个Quartz实例使用同一组数据库表, 则必须将此属性设置为: true

org.quartz.jobStore.clusterCheckinInterval			no		long	15000
	* 节点之前的检查间隔

org.quartz.jobStore.maxMisfiresToHandleAtATime		no		int		20
org.quartz.jobStore.dontSetAutoCommitFalse			no		boolean	false
	* 设置事务是否自动提交

org.quartz.jobStore.selectWithLockSQL				no		string	"SELECT * FROM {0}LOCKS WHERE SCHED_NAME = {1} AND LOCK_NAME = ? FOR UPDATE"
	* 使用的加锁语句

org.quartz.jobStore.txIsolationLevelSerializable	no		boolean	false
	* 是否设置事务隔离级别为: 序列化

org.quartz.jobStore.acquireTriggersWithinLock		no		boolean	false (or true - see doc below)
	* 检索一个Trigger的时候, 是否加锁

org.quartz.jobStore.lockHandler.class				no		string	null
org.quartz.jobStore.driverDelegateInitString		no		string	null
	* 配置给数据源的连接参数, 是否 | 分隔
		"settingName=settingValue|otherSettingName=otherSettingValue|..."



-------------------------
数据源配置				 |
-------------------------
org.quartz.jobStore.dataSource = NAME
	* 数据源的配置, 需要依赖于 'org.quartz.jobStore.dataSource' 的配置

org.quartz.dataSource.NAME.driver							yes	String	null
	* jdbc驱动

org.quartz.dataSource.NAME.URL								yes	String	null
	* 链接的url

org.quartz.dataSource.NAME.user								no	String	""
org.quartz.dataSource.NAME.password							no	String	""
	* 用户名和密码

org.quartz.dataSource.NAME.maxConnections					no	int	10
	* 最大链接数据量

org.quartz.dataSource.NAME.validationQuery					no	String	null
	* 测试的检索语句

org.quartz.dataSource.NAME.idleConnectionValidationSeconds	no	int	50
	* 空闲连接测试之间的秒数
	* 仅在设置了验证查询属性(validationQuery)后才启用

org.quartz.dataSource.NAME.validateOnCheckout				no	boolean	false
	* 使用前, 是否检测连接的可用性

org.quartz.dataSource.NAME.discardIdleConnectionsSeconds	no	int	0 (disabled)
	* 连接的空闲释放时间, 默认0, 表示不释放

