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





