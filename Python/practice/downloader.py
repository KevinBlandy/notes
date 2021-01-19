from urllib import request
import pymysql
import os
import socket

socket.setdefaulttimeout(20)

dir = 'd:' + os.sep + 'video'

if not os.path.exists(dir):
    os.mkdir(dir)

db_config = {
    'host':'localhost',
    'port':3306,
    'user':'root',
    'passwd':'root',
    'db':'7mav001',
    'charset':'utf8mb4',
    'cursorclass':pymysql.cursors.DictCursor
}

connection = pymysql.connect(**db_config)
cursor = connection.cursor()
cursor.execute('SELECT COUNT(*) AS `count` FROM `7mav001`;')
count = cursor.fetchone()['count']

def down_callback(block_number,read_size,total_file_size):
    per=100.0 * block_number * read_size / total_file_size  
    if per > 100:  
        per = 100  
    print('已经下载:%.2f%%' % per)  

for i in range(count):
    cursor.execute('SELECT * FROM `7mav001` LIMIT %s , 1',(i,))
    result = cursor.fetchone()
    # id
    id = result['id']
    # 标题
    title = result['title'] + '.mp4'
    # 视频地址
    src = result['src']
    if not os.path.exists(dir + os.sep + title):
        try:
            request.urlretrieve(src, dir + os.sep + title, down_callback)
        except Exception:
            print("异常%s"%(Exception))
            continue
    
    

