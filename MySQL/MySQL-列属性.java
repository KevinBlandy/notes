-----------------------
MySQL-列属性			|
-----------------------
	空属性
		# null(默认)
		# not null
			* id varchar(50) not null
	列描述
		# comment
			* 没有实际的含义,专门用来描述字段.会根据创建语句保存
			* id varchar(50) comment '主键'
	默认值
		# default
			* default [值]
			* 执行插入的时候,可以直接使用'default'关键字,来代替字段值
			* gender varchar(3) default '男'
			* insert ...values(default... ...);
	无符号
		# unsigned
			* 用于数值,约束该字段没有符号

	主键约束
		# primary key
			* 两种方式
				1,
					* id primary key
					* 创建表的时候,直接在字段后面添加该约束
					* 这种方式,一次只能设置一个字段为主键.
				2,
					* 在建表的时候,在所有的字段之后
					* primary key(字段1,字段2..)
					* 这种方式,可以一次性的设置多个主键
			* 追加的方式
				alter table [表名] add primary key(字段1,字段2..);
			* 更新主键 & 删除主键
				* '主键,必先先要删除,才能增加'
				* alter table [表名] drop primary key; 
				* alter table [表名] add primary key(字段1,字段2..);
			* 主键不允许为null
			* 主键,本身也是一种索引(主键索引)

	自动增长
		# auto_increment
			
			* 任何一个字段要做自增长,必须前提本身是一个索引(key-一栏有值)
			* 要求字段必须为数值,而且是整数
			* 一张表,只能有一个自增长
			* 自增长,默认第一个值为1,每次自增长1
			* 也可以手动指定值.
			* 查看下一次自增长的值
				show create table [表名];
				* AUTO_INCREMENT= 下次自增长的值
			* 修改下次自增长的值
				* 自增长如果是涉及到字段改变,必须先删除自增长后增加,因为一张表只能有一个自增长
				* 修改当前自增长已经存在的值,只能比当前最大值大.不然不生效
				* alter table [表名] auto_increment = [值];
			* 修改系统中配置的-'自增长设置'
				* show variables like '%auto_increment%';
					+--------------------------+-------+
					| Variable_name            | Value |
					+--------------------------+-------+
					| auto_increment_increment | 1     |
					| auto_increment_offset    | 1     |
					+--------------------------+-------+
					auto_increment_increment:起始值
					auto_increment_offset	:增长值
					* set auto_increment_increment = [值];
					* set auto_increment_offset = [值];
				* 修改是对整个数据库,而不是单张表
				* 会话级别的修改,仅仅对当此连接有效
			* 删除自增长
				* 自增长,是字段的一个属性.可以通过modify.
				* alter table [表名] modify 字段 类型;

	值唯一约束
		# unique key
			* 让一个字段的值,具备唯一值
			* 允许为 null,而且.运行多个 null.null字段不参与唯一性约束
			* 基本与主键差不多
				1,在创建表的时候,在字段之后进行约束
					id varchar(50) unique key
				2,在创建表,所有字段后添加约束
					unique key(字段)
			* 更新唯一约束 & 删除唯一约束
				* 先删除,再更新(唯一约束可以有多个,也不用先删除)
				* alter table [表名] drop index (字段);