————————————————————————————
一,Hibernat检索总结			|
————————————————————————————
Hibernate 检索方式
	
	1,对象导航方式
		* Customer c = ...;
		* C.getOrders();
	2,根据OID检索
		* get();
		* load();
	3,HQL
		sessin.createQuery(HQL);
		* 简单查询
			from Customer 
		* 排序查询
			from Customer order by 属性
		* 条件查询
			* 位置绑定
				from Customer where name=?
			* 参数名称绑定
				from Customer where name=:name
		* 分页查询
			query.setFirstResult();
			query.setMaxResults();
		* 聚合函数
			select count(*) from Customer
		* 多态查询
			查询指定类的子类
		* 别名查询
		* 构造方法
			根据构造方法,只创建有指定方法的对象
		* 投影查询
			只查询对象的部分属性
		* 多表查询
			* 交叉连接
			* 内连接
			* 迫切内连接
			* 左外连接		
				把一条记录封装成一个数组!多条记录是把多条数组封装成一个集合
			* 迫切左外连接
				把一条记录封装成一个对象,多个对象形成系一个集合
		* 命名查询
			在映射文件中配置HQL语句
	4,QBC
		session.createCriteria(Class clazz);
		* 简单查询
		* 排序的查询
		* 带有条件的查询
		* 分页查询
		* 离线条件查询
	5,SQL查询
————————————————————————————
二,Hibernat抓取策略			|
————————————————————————————
	1, 立即检索
	2, 延迟检索
		* class 标签的lazy属性
		* javaBean final 修饰
		* 在调用方法的时候,使用Hibernate.ini...初始化代理对象
	> 类级别延迟
		class 标签上的 lazy
	> 关联级别延迟
		<Set> 
			fetch
				* select 默认值
				* join   使用迫切左外连接
				* subselect 使用子查询查询相关的对象
			lazy
				* true 默认值
				* false 不采用延迟
				* extra 极其懒惰
		<many-to-many>
			fetch
				* select 默认值,多条SQL查询关联对象 
				* join 使用迫切左外连接
			lazy
				* proxy :是否采用延迟,根据对方的class标签的lazy属性!
				* false	:不采用延迟
				* no-proxy
		
		batch-size: 批量抓取	

————————————————————————————
三,Hibernat事务与并发		|
————————————————————————————
	事务管理
		* 特性:1,2,3,4
		* 隔离级别 1,2,4,8
		* 丢失更新解决
			> 悲观锁:session.get(Class clazz,"10028",LockMode.UPCRADE )
			> 乐观锁:在javabean中添加 Integer 字段,映射文件对应 <version>标签
	事务并发管理
		* 设置事务隔离级别
		* 使用悲观锁和乐观锁解决丢失更新问题
		* 提供了本地线程绑定Session
	