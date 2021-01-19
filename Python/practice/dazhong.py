import queue
from concurrent.futures import ThreadPoolExecutor
import threading
from urllib import request
from bs4 import BeautifulSoup

opener = request.build_opener()

url = 'https://www.dianping.com/search/keyword/9/0_%E8%8A%B1%E5%BA%97'

# 工作队列
work_queue = queue.Queue(maxsize=100)

# 总页码
total = None

# 线程池
exector = ThreadPoolExecutor()

def worker(item):
    opener = request.build_opener()
    with opener.open(item['href']) as rep:
        html = BeautifulSoup(rep.read(),'html.parser')
        print(html)
        address = html.find_all('span',attr={'class':'item','itemprop':'street-address'})
        print(address)

def start():
    while True:
        item = work_queue.get()
        exector.submit(worker,item)
    
threading.Thread(target = start).start()

with opener.open(url) as rep:
    html = BeautifulSoup(rep.read(),'html.parser')
    page_div = html.find('div',class_='page')
    # 获取总页码
    total = int(page_div.find_all('a')[-2].string)

for i in range(1,total + 1):
    with opener.open(url + '/p' + str(i)) as rep:
        div = html.find_all('div',class_='tit')
        for i in div:
            href = i.find('a').get('href')
            name = i.find_all('h4')[0].string
            work_queue.put({'href':href,'name':name})





