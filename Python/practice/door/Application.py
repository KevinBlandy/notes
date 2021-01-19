'''
    @author :KevinBlandy
    @email  :747692844@qq.com
    @date   :2017年12月18日 18:07:13
'''
import os
import sys

sys.path.append(os.path.dirname(os.path.abspath(__file__)))

import threading

import Logger
import UdpServer
import SimpleHttpServer
#import HttpServer
#import Config

logger = Logger.getLogger()

if __name__ == '__main__':

    '''
        子线程启动udp服务器
    '''
    udpServer = threading.Thread(target=UdpServer.start)
    udpServer.setDaemon(True)
    udpServer.start()

    logger.debug('http服务启动.... ...');

    #HttpServer.application.run(port=Config.httpServerPort,debug=False,host='0.0.0.0')
    SimpleHttpServer.start()