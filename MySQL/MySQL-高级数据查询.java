------------------------
MYSQL高级数据查询		|
------------------------
	# SELECT 的完整语句
	select [select 选项] [字段列表*] from 数据源 [where子语句] [groupby子语句] [having子句] [order by 子句] [limit 子句];

------------------------
MYSQL-SELECT选项		|
------------------------
	# SELECT选项:对查询出来的结果集的处理方式(两种)
		1,ALL (默认)
			* 默认,保留所有数据
		2,distinct
			* 去除重复的数据(两条数据所有字段都相同,就去除多余的,仅仅剩下一条)

------------------------
MYSQL-字段别名			|
------------------------
	# 不想讲,简单
		1,select id `主键`...
		2,select id AS `主键`...

------------------------
MYSQL-数据源			|
------------------------
	# 其实仔细看,SELECT完整语句后面的FROM跟的是数据源,而不是表
	# 所谓是数据源,是数据的来源.关系型数据库的来源都是数据表.
	# '只要保证数据类似二维表,都可以作为数据源'
	# 数据源可以分为多种
		1,单表数据源	
			* 很显然,就是一张表
		2,多表数据源
			* 多表联合查询
		3,查询语句
			* 子查询... ...
-------------------------
MYSQL-列运算			 |
-------------------------
	select 列名操作符数值 form 表名;[对指定列名进行指定的数据操作]
		--无法转换成整数的字段数据(字符串),都会被当成0来进行计算
		--'任何运算操作null,结果都是null'
	select ifnull(列名,替代值) from 表名;[把表格中指定字段如果是null值,就当作指定替换值显示]
	select name,sal+ifnull('有null值的字段',0) from emp;
		--计算工资加奖金,如果奖金为null,则换成0。 
	select sal*0.5,* from emp;
		--查询出emp表中所有的字段,并且把sal*0.5！
		--如果对不能进行运算的字段(varchar)进行操作。那么结果都是0
		--任何东西跟null相运算。结果就是null
	select concat(name,job) from 表名;[把两个字段的字符串拼接后查询出来]
		--MySQL中不能用+号来连接字符串。
	select 字段名1 as 别名1,字段名2 al 别名2 form 表名;[给指定字段名起别名]
		--as 其实是可以省略的

------------------------
MYSQL-WHERE子句			|
------------------------
	# 筛选数据,where 子句返回的结果:1,0(true,false)... ...其实非0就是true(1=1 值为true)
	# 判断表达式
		=			//等于
		>			//大于
		>=			//大于等于 
		<			//小于
		<=			//小余等于
		!=/<>		//不等于 
		is null		//为空
		not null	//不为空
		like		//模糊查询..像
		between and //在...之间
			* between 90 and 100... ...
			* 本身是闭区间
			* 左边的值必须小于等于右边的值
			* >= && <= ...懂了吗?
		in()		//在集合之中
			* 这个可以是多个字段表达
			* select...from ...where (age,gender) in (select age,gender from xxx where...);
			* 就是说,这个可以打组合
			
		not in()	//不在集合之中
		and			//并且
		or			//或者

	# where的原理
		* where是唯一一个,直接从磁盘获取数据的时候就开始判断的条件.
		  从磁盘取出一条记录,开始进行where判断,成功就保存到内存,否则放弃
	
------------------------
MYSQL-GROUP BY 子句		|
------------------------
	# 分组.根据某个字段进行分组.
	# 根据某种个字段进行分组
	# 分组的意义是为了统计数据,按照分组的字段进行梳理统计
	# 基本语法
		select 分组字段,N多聚合函数 from [表名] group by [分组字段];
		* 不需要where也是可以的.
	# 提供了N多用于统计的函数(纵向查询)
		count();
			* 统计分组后的记录数,每一组有多少记录
			* 里面可以使用两种参数
				1,*		:代表统计记录
				2,字段	:代表字段,如果是null,则不参与统计
		max(字段);
			* 统计分组后中,最大值
		min(字段);
			* 最小值
		avg(字段);
			* 最小值
		sum(字段);
			* 和
	# 多字段分组
		* 对分组后的结果,再次按照其他字段进行分组
		select 分组字段1,分组字段2 ,N多聚合函数 from [表名] group by [分组字段1],[分组字段2]
		select gander,max(age),avg(age),min(age) from stu group by province;
		select province,max(age),avg(age),min(age) from stu group by province;
	# 一般用到了group by,就会用到这些函数.且分组的那个字段要出现在select后
	# 分组会自动排序,而且是根据分组字段
		group by [字段] [ASC/DESC]
		* 对分组的结果合并之后.再进行排序
	# 对分组结果中的某个字段进行字符串连接(保留该组所有的某个字段)
		group_concat([字段]);
	# 回溯统计
		with rollup
		* 任何分组后的结构,都会有一个小组.最后都需要向上级分组进行汇报统计
		* 都要回报数据.
	-------催希凡版

	# 一般执行了分组查询后,还要查询'组信息',操作组信息.就是聚合函数.说白了就是操作一个组的方法.而不是操作某条记录的函数
		* 你们的职业是啥啊?
		* 你们一共有多少人啊?
		* 你们里面谁薪资最高啊?
		--	以上,都是组信息
	# 分组查询'不能去查询个人信息',是要去查询一个组的信息
	# 分组查询
		select job as `职位`,count(1) `人数` from emp group by job asc;
		* select后面仅仅能有作为分组的字段.其他的全部必须是聚合函数 
		* 你非要放,语法上没错.但是它不符合逻辑
	
------------------------
MYSQL-having 子句		|
------------------------
	# 跟where子句一样的,是进行条件判断的
	# 它是针对'分组函数'来进行判断,一般都是跟'GROUP BY'配合使用
	# where能做的事情,它都能做,但是它能做的,where不一定能做
	# 针对于'分组信息统计的判断'
	# having能够使用字段别名,where不能.where是从磁盘读取数据.而名字只能是字段名

	select....where ...group by ...having count(*) >= 2;

------------------------
MYSQL-Order by 子句		|
------------------------
	# 根据某个字段,进行升序或者降序排序
	# 它依赖于'校对集'
	# order by asc/desc [字段1] asc/desc [字段2] asc/desc...
		1,asc	:升序(默认)
		2,desc	:降序
	# 可以排序多个字段.

------------------------
MYSQL-limit 子句		|
------------------------
	# 限制查询结果的语句
	# '是MYSQL的方言,其他数据库不一定有'
	# 有两种使用方式
		1,只用来限制数据长度
			limit 数据量
		2,用来限制查询结果数据的列数
			limit 0 14
			* 从第0条记录开始,获取14条数据

------------------------
MYSQL-union(联合)查询	|
------------------------
	# 把多次查询,在记录上进行拼接.在字段上不会增加,但是记录增加了
	# 这东西存在的意义:
		1,查询同一张表,但是需求不同.如:查询学生信息,男生身高升序,女生升高降序
	# 联合查询 Order by的使用
		* 在联合查询中,Order by不能直接使用,
		* 需要对查询语句使用(),括号才行.而且还要加上limit才会生效.
		(select...order by..limit 1000) union all(select...order by..limit 1000)
		
	-----------催希凡
	* union查询,也叫做联合查询.并非SELECT子句
	* 把多条SELECT语句的结果集进行合并
	* 要求,多个SELECT结果集的字段数量一致,字段类型可以不一致
	* 如果UNION结果集,'有重复的记录,那么默认会被去除'
	* 使用UNION ALL可以显示重复记录,反之:UNION DISTINCT 会去除重复的记录
	* 列名称,是由第一条SELECT语句来决定的
	* 示例
		(SELECT u.id,u.name FROM user as u)
			UNION
		(SELECT o.count, o.date FROM _order as o);
		//从user表中获取id,name字段,从_order表里面获取count,date字段.把结果集合并到一个表,子语句的括号不是必须的
	* 当获得数据条件,出现逻辑冲突.或者很难在一个逻辑内表示,就可以拆分成多个逻辑,分别实现.最后把结果合并到一起

------------------------
MYSQL-子查询			|
------------------------
	# 子查询:sub query
		* 查询是在某个结果之上进行查询的
	# 分类
		标量子查询	:单行单列
		列子查询	:一列多行
		行子查询	:多列一行
		表子查询	:多列多行

	['标量子查询']
		select name from ag_core_user `a` where a.worker_year = (select max(worker_year) from ag_core_user);
	['列子查询']
		SELECT 
			t.dict_name
		FROM 
			global_dict t
		WHERE	
			t.dict_type 
		IN(
			(select t1.dict_type from global_dict t1 where t1.dict_name='2-3年'),
			(select t2.dict_type from global_dict t2 where t2.dict_name='4001-6000')
		);
	['行子查询']
		select * from my_student where
		(age,height) = (select max(age),max(height) from my_student);
		
		* (age,height),这个是一行,跟后面的结果匹配
	['表子查询']
		
	
	['exists子查询']
		* exists 子查询,就是用来判断某些条件,是否满足(往往是需要跨表)
		* 是在where后,exists返回的结果只有两个:0/1(false/true)
		select exists(select * from tb);	//数据存在,返回1
		select * from my_sutdent where exists(select * from my_class)
	------------- 催希凡
	其实子查询也好理解
	  * 一条SQL语句中出现多个select关键字,那么就是属于子查询

	子查询可以出现的位置
		selet  from 子查询 where 子查询;
		其实就是一些查询语句的结果就是一张表,我们对这个结果表进行了2次查询
		select r.ename,r,empno
		from (select * from emp where deptno=30) as "r"
		
		当子查询出现在where后,那就是做为了条件(单行单列)
		当子查询出现在from后,那就是做为了表的存在(多行多列)

	然而子查询出来的结果集又分为N多种,根据不同的结果集可以采用不同的运算符
		
	单行单列	>,<,=...毕竟子查询出来就一个结果.可以进行判断
	多行单列	in,not in,<all(所有),>any(任意),!=all(都不在) 查询出来的是一列记录,要使用到集合操作符
	单行多列	in	查询出来的是单行多列 （对象样式,但是比较少见）
	多行多列	select c.* from 子查询 as c where...  这个子查询出来其实就是一张表了已经


	单行单列
		SELECT * FROM user WHERE age=(SELECT MAX(age) FROM user);
		//该子查询,获取的是user表中最大的年龄值.然后使用该值作为条件再次进行了查询
	多行单列
		SELECT * FROM _user WHERE age in (SELECT age FROM _user WHERE age < 22);
		//该子查询,获取的是user表小于22的所有年龄,主查询再判断值是否
	多行多列
		SELECT c.name FROM (SELECT * FROM user WHERE age=21) AS c;
		//该子查询,是查询出user表中的所有age等于21的记录,然后又从这个记录中查询出所有的name
		//因为这种'表',是临时生成的,查询结束,随即释放.所以必须起别名
		

	(SELECT * FROM _user WHERE age IN (SELECT age FROM _user WHERE age > 22 )) UNION (SELECT * FROM  _user WHERE age NOT IN (SELECT age FROM _user WHERE age > 22 ));





