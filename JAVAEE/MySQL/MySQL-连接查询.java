连接查询
1,分类
 * 内连接
 * 外连接
      > 左外连接
      > 右外连接
      > 全外连接(MYSQL不支持)
 * 自然连接(属于一种简化方式)

2,内连接
 * 方言:select * from 表1,表2 where 条件;		[笛卡尔积]
 * 标准:select * from 表1 inner join 表2 on 条件
 * 自然:select * from 表1 natural join 表2;
	--这个最叼,他会自动去找寻俩表名字相同的列,进行匹配!自动加了where条件,让主外键=了


3，外连接
 * > 左外
	select * from 表1 left outer join 表2 on 表1.字段=表2.字段;
	外连接,有一主一次。左表为主!主表中所有的记录,不论满足不满足条件都会打印出来
	当不满足条件的时候,右表部分使用null来补列
 * > 右外
	select * from 表1 right outer join 表2 on 表1.字段=表2.字段;
	这个就是以右表为主,左表为次。当右表中的的数据存在,但是没有对应的左表数据，那么就用null来填充结果
 * > 全外(MYSQL不支持,但是我们可以通过其他方式实现)
	这个就是,左右两表都是相同的,两个表的所有内容都必须全部出来,如果没有对应记录的,就用null填充
	select * from 表1 left outer join 表2 on 表1.字段=表2.字段
	union 
	select * from 表1 right outer join 表2 on 表1.字段=表2.字段;
	-懂了没？联合查询 ,把他们的结果集合并一下就出来了

	
————————————————————
内连接				|
————————————————————
	* 数据内部的连接,要求连接的多个数据都必须存在,才能进行连接.
	* 示例
		SELECT * FROM _teacher AS t INNER JOIN _class AS c WHERE c.teacher=t.id;
		//查询出班级与教师的所有关系
	* MySQL连接查询默认的使用内连接,可以省略:INNER JOIN,把ON改成WHERE
	* 需要注意笛卡尔积的问题,不带条件的查询就是笛卡尔积
	* ON	,指的是多表之间的关系,连接条件
		* WHERE	,在ON后,进行数据过滤 ,过滤条件
		SELECT * FROM _user INNER JOIN _order ON user=id WHERE age > 10;
	* USING	,可以替代ON
		* 当从表的外键和主键的主键名称一样,那么就可以使用USING(id)
		SELECT * FROM _user INNER JOIN _order USING(uid);

————————————————————
外连接				|
————————————————————
	* 当连接的数据不存在,就属于外连接.
	* 示例
		SELECT * FROM _user as u LEFT OUTER JOIN _order as o ON o.user = u.id;
		//查询出用户与订单的关系,就算用户表中有记录.都会显示
	
	1,左外连接
		* JION 左边的表为主要查询的表,不论是否有对应记录,都会显示
		SELECT * FROM _user as u LEFT OUTER JOIN _order as o ON o.user = u.id;
	2,右外连接
		* JOIN 右边的表为主要查询的表,不论是否有对应记录,都会显示
		SELECT * FROM _user as u RIGHT OUTER JOIN _order as o ON o.user = u.id;
	3,全外连接
		* 通过 nuion 联合查询来实现
	
	* OUTER 可以省略
	* 外连接不能是用 WHERE?和USIONG?
	渝中石油路万科锦程3号楼 -- 24-13
	

