'''
    配置
'''

from configparser import ConfigParser

import Logger
import sys
import os

logger = Logger.getLogger()

configparser = ConfigParser()

configparser.read(sys.path[-1] + os.sep + 'application.properties',encoding='utf_8')

logger.debug('加载配置信息')

# http服务端口
httpServerPort = int(configparser['global_config']['http.server.port'])

# udp服务ip
if configparser.has_option('global_config','udp.server.ip'):
    udpServerIp = configparser['global_config']['udp.server.ip']
else:
    udpServerIp = ''
# udp服务端口
udpServerPort = int(configparser['global_config']['udp.server.port'])

##########################  门禁接口  ##########################

# 心跳通知地址
doorHeartbeat = configparser['global_config']['door.heartbeat']
# 刷卡记录上传接口
doorRecord = configparser['global_config']['door.record']
# 临时密码校验
doorTempPassValidate = configparser['global_config']['door.tempPassValidate']

##########################  梯控接口  ##########################
# 心跳
elevatorHeartbeat = configparser['global_config']['elevator.heartbeat']
# 刷卡记录上传
elevatorRecord = configparser['global_config']['elevator.record']
# 临时密码校验
elevatorTempPassValidate = configparser['global_config']['elevator.tempPassValidate']
# 密码记录上传
elevatorPassRecord = configparser['global_config']['elevator.passRecord']

logger.debug('      http服务端口:%s'%(httpServerPort))
logger.debug('      udp服务ip:%s'%(udpServerIp))
logger.debug('      udp服务端口:%s'%(udpServerPort))
logger.debug('      门禁-心跳通知接口:%s'%(doorHeartbeat))
logger.debug('      门禁-刷卡记录接口:%s'%(doorRecord))
logger.debug('      门禁-临时密码校验接口:%s'%(doorTempPassValidate))
logger.debug('')
logger.debug('      梯控-心跳通知接口:%s'%(elevatorHeartbeat))
logger.debug('      梯控-刷卡记录接口:%s'%(elevatorRecord))
logger.debug('      梯控-临时密码校验接口:%s'%(elevatorTempPassValidate))
logger.debug('      梯控-密码记录上传接口:%s'%(elevatorPassRecord))




