------------------------
MYSQL-函数				|
------------------------

------------------------
MYSQL-系统自带函数		|
------------------------	
	# MYSQL中所有的函数都是有返回值的,那么就是说都是由 select 调用的
	# MYSQL字符串的操作单位,基本都是操作字符串为单位的.

	ifnull(列名,替代值);
		* selct ifnull(phone,'未知');,如果phone字段为空,那么设置为未知
	/** 统计相关 **/
	rand()
		* 返回一个0-1之间的随机数
	floor()
		* 向下取整,就是直接干掉小数点.留下整数
	ceil()
		* 向上取整
	count();
		* 返回某个字段的所有有效行数,不包含null
		* 里面可以使用两种参数
			1,*		:代表统计记录
			2,字段	:代表字段,如果是null,则不参与统计
		* 里面放入数值.其实跟*差不多!意义完全相同
	max(字段);
		* 统计分组后中,最大值
	min(字段);
		* 最小值
	avg(字段);
		* 最小值
	sum(字段);
		* 某个字段值的和
		* null,一律当作0来进行计算
		* 如果varchar是数字的字符串,那么会参与运算.如果是非数字的字符串.那么当作0来处理
		
	/** 时间相关 **/
	curdate();
		* 获取当前日期,格式:2016-06-12
	now();
		* 获取当前时间,格式:2016-06-12 22:53:30
	unix_timestamp()
		* 返回unix时间戳

	/** 字符串相关 **/
	concat('1','2');
		* 字符串拼接
		* select concat(name,job) from 表名;[把两个字段的字符串拼接后查询出来]
		* select ..from ..where name like concat('%',concat('kevin','%'));	//mybatis模糊查询
	substring(str,x,y);
		* 字符串截取.从指定字符串的指定下标开始,取多少长度
		* '注意:'该函数的下标是从1开始.('因为0代表 false,在这个里面是不能用的')
	char_length(str);
		* 获取的指定字符串的长度,'字符长度'
	length(str1,str2);
		* 获取的指定字符串才长度,'字节长度'
		* 字符集会影响到结果.
	instr(str);
		* 判断字符串(str2)是否在某个字符串(str1)中存在
		* 如果存在,则返回其存在的位置(下标从1开始)
		* 如果不存在,则返回0
	lpad(str,len,in);
		* '左填充',把字符串按照指定的填充方式,填充到指定的长度
		* 就是,字符串太短了.你要延伸到几位,延伸出来的用啥代替
	insert(str,start,len,newStr);
		* 替换,找到'目标位置''指定长度'的字符串,替换为'目标字符串'
	strcmp(stra,strb);
		* 比较俩字符串大小..根据字典排序咯.A > B
		* 如果a > b 返回 1,反之返回 -1 相等返回 0
	
	find_in_set(str,list);
		* 如果 str 出现在 list 中,返回 true
			SELECT find_in_set(123,'123,456,788') as result; // 1

	//加密相关
	md5("被加密的字符");
		* MD5加密函数,不可逆.平时用的
	password("被加密的字符");
		* 专门供MYSQL用的
	sha1("被加密的字符");
		* 这个也是一个加密函数，可以用于项目
	

	//IP 转换
	INET_ATON()
		* 把IP字符串转换为int
	
	INET_NTOA()
		* 把INT转换为字符串ip
	
	* ip转换函数的意义在于可以方便快速的检索
		SELECT * FROM `table` WHERE `ip` BETWEEN INET_ATON('192.168.0.1') AND INET_NTOA('192.168.0.255');


------------------------
MYSQL-自定义函数		|
------------------------
	# 函数的要素
		1,函数名
		2,参数列表
		3,返回值
		4,函数体(作用域)
	# 系统函数跟自定义函数调用是一毛一样的
	# 函数是属于指定数据库的,只有在函数所在的数据库下才能使用该函数
	# 需要开启一个配置:log_bin_trust_function_creators = 1
	# 语法
		create function [函数名] ([形参列表]) returns [要返回的数据类型] 
		begin
			//函数体
			return [返回值]		//一定要跟上面定义的数据类型一样
		end
	# 如果返回的数据类型是VARCHAR,则还可以添加字符集
		 create function [函数名] ([形参列表]) returns [要返回的数据类型] CHARSET utf8
------------------------
MYSQL-函数参数			|
------------------------
	# 参数分为两种:定义时候的参数(形参),使用时候的参数(实参)
	# 形参:
		* 要求必须指定数据类型
		create function demo([形参名字] [字段类型]) returns [返回类型]...
	# 在函数体内部定义变量,如果使用了@,那么在函数外面也可以访问,这就是全局变量

------------------------
MYSQL-作用域			|
------------------------
	# MYSQL中的作用域与JS中的作用域是一毛一样的
	# 全局变量,可以在任何地方使用
	# 局部变量,只能在函数内部使用
	# 全局变量
		* 使用 set 关键字,使用 @ 符号标识
	# 局部变量
		* 使用 declare 关键字声明,没有 @ 符号
		* 所有的局部变量的声明,必须'在函数体开始之前'
		* declare [变量名] [类型] default [默认值];
		* 修改局部变量也要使用到set关键字
		* set [变量名] = [值];
	
------------------------
MYSQL-删除函数			|
------------------------
	# 函数是不能修改的,只能先删除,再新增
	# 系统函数不能被删除
	# drop function [函数名];


------------------------
MYSQL-查看函数			|
------------------------
	# 查看所有函数
		show function status;
		* 后面也可以加个 like 实现模糊匹配
		* 会显示出所有的自定义函数,就算是不在本数据库中.也能看,但是不能用
	# 查看创建语句
		show create function [函数名];
	

------------------------
MYSQL-函数的常用		|
------------------------
	# 获取 0 - 9 的随机数
		select floor(rand() * 10);

	# 拼接字符串,一般用于模糊查询
		concat('%',concat('kevinBlandy','%'));
		* 一般用于模糊查询


------------------------
MYSQL-函数的常用		|
------------------------
CREATE FUNCTION `getChildList`(rootId INT)
    RETURNS varchar(1000)
    BEGIN
      DECLARE sChildList VARCHAR(1000);
      DECLARE sChildTemp VARCHAR(1000);
      SET sChildTemp =cast(rootId as CHAR);
      WHILE sChildTemp is not null DO
        IF (sChildList is not null) THEN
          SET sChildList = concat(sChildList,',',sChildTemp);
    ELSE
      SET sChildList = concat(sChildTemp);
    END IF;
        SELECT group_concat(id) INTO sChildTemp FROM user_role where FIND_IN_SET(parentid,sChildTemp)>0;
      END WHILE;
      RETURN sChildList;
    END;

/*获取子节点*/
/*调用: 1、select getChildList(0) id; 2、select * 5From user_role where FIND_IN_SET(id, getChildList(2));*/
 
 
CREATE FUNCTION `getParentList`(rootId INT)
    RETURNS varchar(1000)
    BEGIN
      DECLARE sParentList varchar(1000);
      DECLARE sParentTemp varchar(1000);
      SET sParentTemp =cast(rootId as CHAR);
      WHILE sParentTemp is not null DO
    IF (sParentList is not null) THEN
         SET sParentList = concat(sParentTemp,',',sParentList);
    ELSE
     SET sParentList = concat(sParentTemp);
    END IF;
        SELECT group_concat(parentid) INTO sParentTemp FROM user_role where FIND_IN_SET(id,sParentTemp)>0;
      END WHILE;
      RETURN sParentList;
    END;

/*获取父节点*/
/*调用: 1、select getParentList(6) id; 2、select * From user_role where FIND_IN_SET(id, getParentList(2));*/

CREATE FUNCTION `getChildList`(rootId INT)
    RETURNS varchar(1000)
    BEGIN
      DECLARE sChildList VARCHAR(1000);
      DECLARE sChildTemp VARCHAR(1000);
      SET sChildTemp =cast(rootId as CHAR);
      WHILE sChildTemp is not null DO
        IF (sChildList is not null) THEN
          SET sChildList = concat(sChildList,',',sChildTemp);
    ELSE
      SET sChildList = concat(sChildTemp);
    END IF;
        SELECT group_concat(reply_id) INTO sChildTemp FROM jw_dynamic_reply where FIND_IN_SET(parent_id,sChildTemp)>0;
      END WHILE;
      RETURN sChildList;
    END;