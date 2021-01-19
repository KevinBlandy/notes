
---------------------
pymysql				 |
---------------------
	* 操作步骤
		1,通过 pymysql 获取 connection 连接对象
		2,通过 connection 连接对象获取 cursor 游标对象
		3,通过 cursor 执行 CRUD 操作
		4,通过 connection 提交事务

---------------------
pymysql				 |
---------------------
	connect()
		* 获取mysql的连接
		* 命名参数
			host
				* 连接地址
			port
				* 连接端口
			user
				* 连接用户名
			password
				* 连接密码
			db
				* 数据库名称
			charset
				* 数据库的字符集
			cursorclass
				* 该值决定了执行 SELECT 后返回的 tuple 中是 tuple(默认) 还是 dict(key是DB字段名称)
				* 可选值
					pymysql.cursors.DictCursor	
						* 结果集会被映射为  dict
		
		* 使用dict封装参数
			db_config = {
				'host': '127.0.0.1',
				'port': 3306,
				'user': 'root',
				'password': 'root',
				'db': 'user',
				'charset': 'utf8mb4',
				'cursorclass': pymysql.cursors.DictCursor
			}

---------------------
Connection			 |
---------------------
	cursor(cursor)
		* 获取执行SQL的游标
		* 参数决定了执行 SELECT 后返回的 tuple 中是 tuple(默认) 还是 dict(key是DB字段名称),该参数具备默认值
		* 可选参数
			pymysql.cursors.DictCursor	
						* 结果集会被映射为  dict
	autocommit()
		* 设置事务提交方式是否自动提交,默认手动提交
		* 参数为 bool 值

	begin()
		* 开始事务
	commit()
		* 提交事务
	rollback()
		* 回滚事务
	close()
		* 关闭连接

---------------------
cursor				 |
---------------------
	execute(sql,param)
		* 执行SQL语句
		* parma 是一个序列,填充sql中的 %s 占位符
		* 返回结果是受到影响的行数
	
	executemany(sql,params)
		* 执行SQL语句
		* params 是一个嵌套序列,会循环的填充占位符 %s 并且执行 sql
		* 返回结果是受到影响的行数
		* 返回 tuple

	tuple cursor.fetchall()
		* 获取所有的数据
		* 游标会移动到最后
		* 返回 tuple
	
	tuple fetchmany(num)
		* 获取指定行数的数据,游标后移num个单位
		* 如果num超出数据行数,则会被无视
		* 返回 tuple

	tuple fetchone()
		* 获取第一行数据,游标向前移动一个单位
		* 当游标后没有数据的时候,返回 None
	
	None scroll(num,mode)
		* 移动游标,num,代表移动的单位
		* 关键字参数
			mode
				* 该值决定了游标的移动模式
				* relative	当对于当前位置进行移动
				* absolute	当对于绝对位置进行移动
		* demo
			cursor.scroll(-1,mode='relative')
				* 在当前位置,指针向前移动一位
			cursor.scroll(0,mode='absolute')
				* 指针移到开头
	
	close()
		* 关闭游标对象

	rowcount
		* 该属性值为,受到影响的行数

	lastrowid
		* 该属性值为执行 INSERT 后最后一条记录的'自增长id值'


				
---------------------
demo				 |
---------------------

import pymysql
import time

db_host = '59.110.167.11'        
db_port = 1124
db_user = 'root'
db_pass = 'KevinBlandy_mysql'
db_database = 'demo'
db_charset = 'utf8mb4'
db_cursorclass = pymysql.cursors.DictCursor

'''
config = {
    'host':'127.0.0.1',
    'port' : 3306,
    'user' : 'root',
    'passwd' : 'root',
    'database' : 'springcloud',
    'charset' : 'utf8mb4',
    'cursorclass' : pymysql.cursors.DictCursor
}
'''

# 创建连接对象
conn = pymysql.connect(host=db_host,port=db_port,user=db_user,passwd=db_pass,db=db_database,charset=db_charset,cursorclass=db_cursorclass)

# 通过连接对象,创建游标
cursor = conn.cursor()

# 通过游标执行 INSERT 操作,返回受影响的行数
for i in range(100):
    affected_rows = cursor.execute('INSERT INTO `demo`(`id`,`name`,`age`,`phone`,`create_date`) VALUES(%s,%s,%s,%s,%s)',(i,'KevinBlandy',23,'18523570974',time.localtime()))

# 通过游标执行 SELECT 操作,返回检索到的数据行数
cursor.execute('SELECT * FROM `demo`')

#通过游标获取执行后的结果
for row in cursor.fetchall():
    print(row)

# 通过游标执行 UPDATE 操作,返回受影响的行数
cursor.execute('UPDATE `demo` SET `name` = %s WHERE `id` = %s',('余文朋',0))

# 通过游标执行 DELETE 操作,返回受影响的行数
cursor.execute('DELETE FROM `demo` WHERE 1=1')
    
# 通过连接对象,提交事务
conn.commit()
# 释放连接
conn.close()