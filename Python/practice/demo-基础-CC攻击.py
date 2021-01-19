import threading
import sys
from urllib import request

target_url = "http://www.baidu.com"

def do_request(url):
    while True:
        try:
            with request.urlopen(url) as response:
                if response.getcode() == 200:
                    print('%s,success'%(threading.current_thread().getName()))
                else:
                    print('%s,fail'%(threading.current_thread().getName()))
        except Exception as exception:
            print('%s,error'%(threading.current_thread().getName()))
            
if __name__ == '__main__':
    threading_num = 500
    if(len(sys.argv) > 1):
        threading_num = int(sys.argv[1])
    for i in range(threading_num):
        threading.Thread(target=do_request,args=(target_url,)).start()