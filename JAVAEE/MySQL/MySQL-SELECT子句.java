
——————————————————
order by		  |
——————————————————
	* order by 是用于排序的子句
	* 它是对检索出来的结果集进行排序,所以位置是在 WHERE 关键字之后
	* ORDER BY 字段 DESC/ASC
		* ASC  由小大大排序,默认值.可以省略不写
		* DESC 由大到小排序
	* 示例
		SELECT * FROM user ORDER BY age;	
			//根据age,从小到大排序结果集
		SELECT * FROM user ORDER BY age desc;
			//根据age,从大到小排序结果集
		SELECT * FROM user ORDER BY age DESC,money DESC
			//根据age,从大到小排序结果集,如果age相同.那么就相同的字段就按照money进行从大到小排序
			//以此类推,后面可以添加N个字段+排序方式,当前面的字段相同.那么就会使用到后面的排序
	* 如果是GROUP BY,则应该使用对分组字段进行排序的GROUP BY

——————————————————
limit			  |
——————————————————
	* limit 是用于限制查询结果数量的子句
	* limit是对检索结果集进行限制,所以位置是在 WHERE 关键字之后
	* limit start ,count
		* start 是指从第几条记录开始获				最小值为0,没有最大值,超过总记录数会空集合
		* count 是指一个获取多少条记录(包括strat)	最小值为0,没有最大值,超过记录数会空集合
	* 如果LIMIT 后仅有一个参数N,那么默认为:从第一条开始,获取N条数据
	* 示例
		SELECT * FROM user LIMIT 0,1;
			//从第一条记录开始,只获取第一条记录
		SELECT * FROM user LIMIT 1000,1;
			//从第1000条记录开始,只获取一条记录,但是数据量不足1000条.返回Empty set
	
——————————————————
distinct		  |
——————————————————
	* distinct 是用于去除重复记录的子句
	* 示例
		SELECT DISTINCT age FROM user;
			//查询出user表中的所有年龄,并且去除多余的相同年龄数据
		SELECT DISTINCT * FROM user;
			//查询出user表中的所有字段,并且去除所有字段都相同的记录
	* DISTINCT 还有一个相对的子句:ALL  意思就是不去除重复,默认值.极少写

——————————————————
union			  |
——————————————————
	* union查询,也叫做联合查询.并非SELECT子句
	* 把多条SELECT语句的结果集进行合并
	* 要求,多个SELECT结果集的字段数量一致,字段类型可以不一致
	* 如果UNION结果集,有重复的记录,那么默认会被去除
	* 使用UNION ALL可以显示重复记录
	* 列名称,是由第一条SELECT语句来决定的
	* 示例
		(SELECT u.id,u.name FROM user as u) UNION (SELECT o.count, o.date FROM _order as o);
			//从user表中获取id,name字段,从_order表里面获取count,date字段.把结果集合并到一个表,子语句的括号不是必须的
	* 当获得数据条件,出现逻辑冲突.或者很难在一个逻辑内表示,就可以拆分成多个逻辑,分别实现.最后把结果合并到一起

