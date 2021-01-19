

	1,MyBatis如何设置:主键自增长.或者说,怎么设置主键生成策略
	2,关于起别名方式,会出现SQL异常.
		* 执行update语句,必须加上别名,不然异常
		* 执行insert语句,不能加上别名,不然异常
	3,${}该表达式,跟#{},实质区别
	4,extends=""属性,ResultMap,ParameterMap中的用法
	5,一个ResultMap中引用另一个<select>语句,cloun="" select=""..用法
	6,核心配置文件中的类型转换器 typeHandlers
	7,如何批量加载普通的sql映射文件.
	8,整合EHCache,执行查询抛出异常
		* java.net.UnknownHostException: www.terracotta.org
	9,整合Spring的问题
		1,怎么控制事务传播属性?
		2,怎么控制事务隔离级别?
		3,mapper代理和普通整合方式,事务是在Service层么?怎么确定?
	