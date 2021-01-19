import pymysql

db_host = '127.0.0.1'        
db_port = 3306
db_user = 'root'
db_pass = 'root'
db_database = 'information_schema'
db_charset = 'utf8mb4'
db_cursorclass = pymysql.cursors.DictCursor

# 创建连接对象
conn = pymysql.connect(host=db_host,port=db_port,user=db_user,passwd=db_pass,db=db_database,charset=db_charset,cursorclass=db_cursorclass)

# 通过连接对象,创建游标
cursor = conn.cursor()

