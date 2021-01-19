'''
    CREATE TABLE `account` (
      `id` int(11) NOT NULL COMMENT 'id',
      `money` int(255) DEFAULT NULL COMMENT '余额',
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
'''
import pymysql
import time
import threading

db_config = {
    'host':'localhost',
    'port':3306,
    'user':'root',
    'passwd':'root',
    'database':'springcloud',
    'charset':'utf8mb4',
    'cursorclass':pymysql.cursors.DictCursor
}
 
 
def add(money,seconds):
     
    connection = pymysql.connect(**db_config)
    connection.autocommit(False)
    # 开启事务
    connection.begin()
    cursor = connection.cursor()
     
    # 执行SELECT
    cursor.execute('SELECT `money` FROM `account` WHERE `id` = %s;',(1,))
     
    balance = cursor.fetchone()['money'];
    print("%s-读取到余额:%s,本次添加金额:%s"%(threading.current_thread().getName(),balance,money))
    # 模拟线程卡住
    time.sleep(seconds)
    # 回写
    cursor.execute('UPDATE `account` SET `money` = %s WHERE `id` = %s;',(balance + money,1))
     
    # 提交事务
    print("%s-提交事务"%(threading.current_thread().getName()))
    connection.commit()
 
 
if __name__ == '__main__':
    threading.Thread(target=add,args=(10,2)).start()
    threading.Thread(target=add,args=(10,1)).start()


