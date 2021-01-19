数据库是表的容器。表，必须属于某个数据库。进行表操作的时候都会指定当前的默认数据库。
use db_name;
	--指明当前的数据库。只是设定了默认数据库。不会影响其他操作。
------------------------------增加------------------------------
创建数据表(已经默认了数据库)
	create table tb_name (表结构)[表选项];
	--创建一个表格.应该先要分析被保存的数据。应该有哪些属性，这些属性该如何保存，创建成功后每个表都在数据库的目录中产生一个 frm文件。保存的是表结构。
创建数据表(动态指定数据库)
	create table db_name.tb_name (表结构)[表选项];
	--指该表创建在哪个数据库里面。
------------------------------查询------------------------------
查看该数据库有哪些表
	show tables;
	--查看默认数据库下有哪些数据表
	show tables [like `pattern_%`];
	--类似于模糊查询，查询出来的表格，名字都含有指定标识符。'%'：表示的是任意字数，任意字符，称之为通配符。
查询某个表的创建信息(结构)
	show create table tb_name;
	--查看指定表的创建信息
	show create table tb_name\G
	--通过另一种展示形式来显示表的创建信息
	desc tb_name;
	--同样，也是查询表格的创建信息。这个描述的是表结构
------------------------------修改------------------------------
	修改表名
	rename table oldtb_name to newtb_name,oldtb_name to newtb_name,oldtb_name to newtb_name;
	--修改指定表的名字。(支持同时修改多个表。其中用,号隔开)
	跨数据库修改表名
	rename table old_name to db_name.tb_name;
	--
	修改列定义----------------------alter table
		增加一个列-add
			alter table tb_name add 新列名 数据类型(约束长度);
			--简单的增加。
		修改一个列-modify
			alter table tb_name modify 列名字 列定义(新定义);
			--修改指定表的指定列的定义。
		删除一个列-drop
			alter table tb_name drop 列名字;
			--
		重命名一个列-change--跟modfy相比，多了可以更改名字的操作，
			alter table tb_name change 旧名字 新名字(新属性);
			--重命名指定表格的指定字段。
	修改表选项(结构)
	alter table tb_name 新的表选项;
	--alter table demo character set uff-8;//修改指定表的表属性。这里例子是修改表的ASCII编码。
	
------------------------------删除------------------------------
	删除指定表格
	drop table tb_name;
	--删除指定表格。操作不可逆。删除操作一定要慎之又慎。如果表名不存在，那么会报错。