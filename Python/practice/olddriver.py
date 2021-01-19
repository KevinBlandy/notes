'''
DROP TABLE IF EXISTS `7mav001`;
CREATE TABLE `7mav001`  (
  `id` int(11) NOT NULL COMMENT ' pk',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `href` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '链接地址',
  `src` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '播放地址',
  `is_down` tinyint(1) NULL DEFAULT NULL COMMENT '是否已经下载',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

'''
from urllib import request,parse
from bs4 import BeautifulSoup
import queue
import pymysql
from concurrent.futures import ThreadPoolExecutor
import base64

# url
base_url = base64.b64decode(b'aHR0cDovL3d3dy43bWF2MDAxLmNvbQ==').decode('UTF_8')

# 主页
index = base_url + '/recent/'

# 当前页
current_page = 1

# 总页数
total_page = None

# 数据库配置
db_config = {
    'host':'127.0.0.1',
    'port':3306,
    'user':'root',
    'passwd':'root',
    'db':'7mav001',
    'charset':'utf8mb4',
    'cursorclass':pymysql.cursors.DictCursor
}

class Vedio(object):
    def __init__(self,id,title,href,src):
        self.id = int(id)
        self.title = title
        self.href = href
        self.src = src
        
    def __str__(self, *args, **kwargs):
        return "id=%s,title=%s,href=%s,src=%s"%(self.id,self.title,self.href,self.src)
    
req = request.Request(index)

with request.urlopen(req) as rep:
    soup = BeautifulSoup(rep.read(),'html.parser')
    pagination = soup.find('ul', class_='pagination').find_all('li')[-2];
    # 得到总页码
    total_page = int(pagination.string)

def create(video):
    connection = pymysql.connect(**db_config)
    connection.begin()
    cursor = connection.cursor()
    cursor.execute('SELECT `id` FROM `7mav001` WHERE `id` = %s;',(video.id,))
    if not cursor.fetchone():
        print(video)
        cursor.execute('INSERT INTO `7mav001`(`id`,`title`,`href`,`src`) VALUES(%s,%s,%s,%s);',(video.id,video.title,video.href,video.src))
    connection.commit()
    connection.close()
    
def worker (page):
    req = request.Request(index + page)
    with request.urlopen(req) as rep:
        soup = BeautifulSoup(rep.read(),'html.parser')
        videos = soup.find('ul', class_ = 'videos').find_all('li')
        # 遍历视频列表
        for video in videos:
            # 视频id
            id = video.attrs['id'].split('-')[1]
            link = video.find('a',class_ = 'thumbnail')
            # 视频标题
            title = link.attrs['title']
            # 视频页面地址
            href = base_url + '/' + id + '/' + parse.quote(title)
            
            with request.urlopen(href) as rep:
                soup = BeautifulSoup(rep.read(),'html.parser')
                video = soup.find('video')
                source = video.find('source')
                # 视频播放地址
                src = source.attrs['src']
                # 持久化
                create(Vedio(id,title,href,src))

def start():
    global current_page
    while current_page <= total_page:
        page = ''
        if current_page > 1:
            page = str(current_page)
        worker(page)
        #页码前移
        current_page += 1
        break

if __name__ == '__main__':
    start()
