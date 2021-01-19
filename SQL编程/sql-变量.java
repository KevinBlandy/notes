---------------------------	
变量						|
---------------------------	
	# 系统变量
		* 是系统定义好的变量,我们只需要去使用即可,大部分的时候,我们根本不用使用到
		* 是控制服务器的表现:autocommit.....等等
		* 查看系统变量:show variables like '变量名';
		* 修改系统变量:set [变量名] = [值];
		* 查看具体变量值还有一种方式
			SELECT @@变量名
			select @@version,@@xxx,@xxx...;
			* 系统变量前面是加俩@符号
			
	# 修改系统变量
		1,会话级别的修改,该次修改仅仅对当前线程/会话有效
			set [变量名] = [值];
			set @@变量名 = [值];
		2,全局级别的修改,对所有的线程/会话都有效,一次修改永久生效
			set global @@变量名 = [值];
			* 这种修改方式对于当前在线的其他用户无效,其他用户得重新登录才有效
		

	# 自定义变量
		* MYSQL中为了区分系统变量,规定用户自定义变量必须使用一个 @符号;
		* set @name = 'KevinBlandy';		//定义变量
		* select @name;						//获取变量
	
		* 在MYSQL中的 '=',会默认的当作比较符号来进行处理,MYSQL为了区分比较和赋值,所以专门定义了一个赋值符号:		:=
		* 颜文字,呵呵:冒号等于
			set @name := 'KevinBlandy';
			select @name;

		* MYSQL允许从数据表中获取数据,赋值给变量
			1,select
				SELECT [变量名] := [值];
					SELECT @name := '44';
				select [变量名] := (子查询);								//该返回值是单行单列
					select @name := (select name from user where id = 1);	
				select [变量名] := [列名] from [表名];
					select @name := name from user ;						//注意了,这个操作是把一列数据都给了该变量,其实这个变量一直在改变,最终值就是最后一个值
				* 得使用:=,如果直接使用等号会变成比较符号	
				
			2,into
				* 这种赋值会比较严格,要求赋值的结果仅仅只能是单行,不能是'多行',也就是仅仅一条记录,MYSQL没有数组这个概念
				set [变量名] := (子查询);														//要求返回单行单列
					set @name := (select name from user where id = 1);			
				select [字段1],[字段2]... from [表名] where [条件] into @变量1,@变量2...;		//可以返回多列,但是只能是一行.而且有多少个字段就必须要有多少个变量
					select name,age from user where id = 1 into @name,@age;
					* 注意的是结果
										
		* 所有自定义的变量都是会话级别,当前连接/用户有效.退了就没了;
		* 这种变量也可以理解为全局变量,如果不加@,那么就是局部变量,在函数中会有该情况
	
---------------------------	
数据类型转换				|
---------------------------	
	# 数据类型
		CHAR[(N)]	字符型 
		DATE		日期型
		DATETIME	日期和时间型
		DECIMAL		float型
		SIGNED		int
		UNSIGNED	int
		TIME		时间型
	
	# 类型转换
		cast()
			SELECT CAST('151515' AS SIGNED)
			SELECT CAST(51515 AS CHAR(10))

		convert()
			SELECT CONVERT(12,CHAR(10))

	
---------------------------	
系统预定义常量				|
---------------------------	
	CURRENT_TIMESTAMP
		* 当前时间戳
	