
'''
    UDP服务
'''

import socketserver
import Config
import Logger
import CommonUtils
import HttpClient
import UdpClient
import time

from urllib import request

server = None

logger = Logger.getLogger()

'''
    udp服务处理
'''
class Handler(socketserver.BaseRequestHandler):

    def handle(self):
        try:
            data = self.request[0]

            client = self.client_address

            if len(data) == 6:
                # 旧版本门禁心跳
                self.heartbeat(data,client)
            elif len(data) > 6:

                operation = data[6]

                # 忽略最后位校验字节
                data = data[0:-1]

                ##############      门禁指令开始    ###############

                if operation == 0x01:
                    self.uploadRecord(data,client)
                elif operation == 0x04:
                    # 时间同步(心跳包里有,忽略)
                    pass
                elif operation == 0x05:
                    self.tempPassValidate(data,client)
                elif operation == 0x06:
                    pass
                elif operation == 0x07:
                    self.heartbeatWithStatus(data,client)
                elif operation == 0x08:
                    pass
                elif operation == 0x09:
                    logger.debug('开门延迟设置结果:%s'%(CommonUtils.bytesToHex(data)))
                elif operation == 0x0A:
                    self.uploadPassRecord(data,client)
                elif operation == 0x0B:
                    logger.debug('固定密码设置结果:%s'%(CommonUtils.bytesToHex(data)))
                elif operation == 0x0C:
                    logger.debug('门禁重启结果:%s'%(CommonUtils.bytesToHex(data)))

                ##############      梯控指令开始    ###############

                elif operation == 0x17:
                    self.elevatorHeartBeat(data,client)
                elif operation == 0x10:
                    logger.debug('梯控远程控制结果:%s'%(CommonUtils.bytesToHex(data)))
                elif operation == 0x11:
                    self.elevatorCardUpload(data,client)
                elif operation == 0x12:
                    logger.debug('梯控下发电梯卡号结果:%s'%(CommonUtils.bytesToHex(data)))
                elif operation == 0x13:
                    logger.debug('梯控删除电梯卡号结果:%s'%(CommonUtils.bytesToHex(data)))
                elif operation == 0x14:
                    logger.debug('梯控同步时间设置结果:%s'%(CommonUtils.bytesToHex(data)))
                elif operation == 0x15:
                    self.elevatorTempPassValidate(data,client)
                elif operation == 0x16:
                    logger.debug('梯控密钥下发结果:%s'%(CommonUtils.bytesToHex(data)))
                elif operation == 0x18:
                    logger.debug('梯控读卡器卡号格式下发结果:%s'%(CommonUtils.bytesToHex(data)))
                elif operation == 0x19:
                    logger.debug('梯控参数设置结果:%s'%(CommonUtils.bytesToHex(data)))
                elif operation == 0x1A:
                    self.uploadElevatorPass(data,client);
                elif operation == 0x1B:
                    logger.debug('梯控固定密码设置结果:%s'%(CommonUtils.bytesToHex(data)))
                elif operation == 0x0D:
                    logger.debug('设置服务器信息结果:%s'%(CommonUtils.bytesToHex(data)))
                else:
                    logger.error('未识别指令:data=%s,client=%s'%(CommonUtils.bytesToHex(data),client))
            else:
                logger.error('非法数据:data=%s,client=%s'%(CommonUtils.bytesToHex(data),client))
        except Exception as exception:
            logger.error('异常:%s'%(exception))

    def heartbeat(self,data,client):
        '''
        普通心跳
        :param data:
        :param client:
        :return:
        '''
        requestBody = {
            'sourceIp':client[0],                   # 门禁ip
            'sourcePort':client[1],                 # 门禁端口
            'mac':CommonUtils.bytesToHex(data),     # 序列号
            'targetIp':Config.udpServerIp,          # 目标服务器ip(当前服务器ip)
            'targetPort':Config.udpServerPort,      # 目标服务器端口
            'date':CommonUtils.now(),               # 心跳时间
        }
        logger.debug('心跳注册:%s'%(requestBody))
        HttpClient.doPost(Config.doorHeartbeat,requestBody)

    def uploadRecord(self,data,client):
        '''
        刷卡记录上传
        :param data:
        :param client:
        :return:
        '''
        requestBody = {
            'serial':CommonUtils.bytesToHex(data[0:6]),
            'carNum':CommonUtils.bytesToHex(data[7:12]),
            'dateTime':CommonUtils.bytesToHex(data[12:])
        }

        logger.debug('上传刷卡记录:%s'%(requestBody))

        response = HttpClient.doGet(Config.doorRecord + '?type=1&mac=' + requestBody['serial'] + '&cardNum=' + requestBody['carNum'])

        logger.debug('刷卡记录上传结果:%s'%(response))

        data = requestBody['serial'] + '01' + requestBody['carNum']

        if response['success']:
            data += '4F4B'
        else:
            data += '4445'

        data = CommonUtils.xorOperation(bytes.fromhex(data))

        logger.debug('响应UDP门禁:hex=%s'%(CommonUtils.bytesToHex(data)))

        UdpClient.sendUdpData(data,client)


    def tempPassValidate(self,data,client):
        '''
        临时密码校验
        :param data:
        :param client:
        :return:
        '''
        requestBody = {
            'serial':CommonUtils.bytesToHex(data[0:6]),
            'pass':CommonUtils.bytesToHex(data[7:9]),
        }

        logger.debug('临时密码校验:%s',requestBody)


        #response = HttpClient.doGet(Config.doorTempPassValidate + '?mac=' + requestBody['serial'] + '&code=' + requestBody['pass'])
        with request.urlopen(Config.doorTempPassValidate + '?mac=' + requestBody['serial'] + '&code=' + requestBody['pass']) as response:
            '''
            响应非标准JSON
            '''
            response = str(response.read(),'UTF_8')

            logger.debug('临时密码校验结果:%s'%(response))

            data = requestBody['serial'] + '05'

            if response:
                data += '4F4B'
                # 服务端有响应任何数据,则校验成功,进行密码记录开门上传
                response = HttpClient.doGet(Config.doorRecord + '?type=2&mac=' + requestBody['serial'] + '&cardNum=' + requestBody['pass'])
                logger.debug('密码开门记录上传结果:%s' % (response))
            else:
                data += '4445'


            data = CommonUtils.xorOperation(bytes.fromhex(data))

            logger.debug('临时密码校验响应:hex=%s'%(CommonUtils.bytesToHex(data)))

            UdpClient.sendUdpData(data,client)

    def heartbeatWithStatus(self,data,client):
        '''
        带有状态的心跳包
        :param data:
        :param client:
        :return:
        '''
        requestBody = {
            'sourceIp':client[0],
            'sourcePort':client[1],
            'mac':CommonUtils.bytesToHex(data[0:6]),
            'targetIp':'' if not Config.udpServerIp else Config.udpServerIp,
            'targetPort':Config.udpServerPort,
            'status':CommonUtils.bytesToHex(data[7:8]),
            'date':CommonUtils.now(),
        }

        logger.debug('状态心跳:%s'%(requestBody))

        response = HttpClient.doPost(Config.doorHeartbeat,requestBody)

        logger.debug('状态响应:%s'%(response))

        if not response['success']:
            logger.error('服务器异常:%s'%(response['message']))
            return

        if 'data' not in response or not response['data']:
            logger.error('门禁没有密钥和卡片输出格式:serital=%s'%(requestBody['mac']))
            return
        
        if 'key' not in response['data']:
            logger.error('门禁没有密钥信息:serital=%s'%(requestBody['mac']))
            return
                
        key = response['data']['key']
        
        if 'cardOutFormat' not in response['data']:
             cardFormat = '04'
        else:
            cardFormat = response['data']['cardOutFormat'].zfill(2)
        
        responseHex = requestBody['mac'] + '07' + key + time.strftime("%y%m%d%H%M%S") + cardFormat
        
        data = CommonUtils.xorOperation(bytes.fromhex(responseHex))

        logger.debug('状态响应包:hex=%s'%(CommonUtils.bytesToHex(data)))
        
        UdpClient.sendUdpData(data,client)

    def uploadPassRecord(self,data,client):
        '''
        密码开门记录上传
        :param data:
        :param client:
        :return:
        '''
        requestBody = {
            'serial': CommonUtils.bytesToHex(data[0:6]),
            'pass': CommonUtils.bytesToHex(data[7:9]),
        }

        logger.debug('上传密码开门记录:%s'%(requestBody))

        response = HttpClient.doGet(Config.doorRecord + '?type=2&mac=' + requestBody['serial'] + '&cardNum=' + requestBody['pass'])

        logger.debug('密码开门记录上传结果:%s' % (response))

        data = requestBody['serial'] + '0A4F4B'

        data = CommonUtils.xorOperation(bytes.fromhex(data))

        logger.debug('响应UDP门禁:hex=%s' % (CommonUtils.bytesToHex(data)))

        UdpClient.sendUdpData(data, client)


    #####################   梯控api   ########################

    def elevatorHeartBeat(self,data,client):
        '''
        梯控心跳
        :return:
        '''
        requestBody = {
            'sourceIp': client[0],
            'sourcePort': client[1],
            'mac': CommonUtils.bytesToHex(data[0:6]),
            'targetIp': '' if not Config.udpServerIp else Config.udpServerIp,
            'targetPort': Config.udpServerPort,
            'date': CommonUtils.now(),
        }
        logger.debug("梯控心跳:%s"%(requestBody))
        response = HttpClient.doPost(Config.elevatorHeartbeat,requestBody)
        logger.debug('梯控心跳结果:%s'%(response))
        if response['success']:
            cardFormat = response['data']['cardFormat']
            key = response['data']['key']
            if not key:
                logger.error('没有密钥')
                return
            if not cardFormat:
                cardFormat = '04'

            message = requestBody['mac'] + '17' + key + time.strftime("%y%m%d%H%M%S") + cardFormat;
            message = CommonUtils.xorOperation(bytes.fromhex(message))
            logger.debug('梯控心跳响应:%s'%(CommonUtils.bytesToHex(message)))
            UdpClient.sendUdpData(message,client)

    def elevatorCardUpload(self,data,client):
        '''
        梯控刷卡记录上传
        :param data:
        :param client:
        :return:
        '''
        requestBody = {
            'mac': CommonUtils.bytesToHex(data[0:6]),
            'carNum': CommonUtils.bytesToHex(data[7:12]),
            'dateTime': CommonUtils.bytesToHex(data[12:])
        }
        logger.debug('梯控卡号上传:%s'%(requestBody))
        # 提交记录到云平台,返回该卡可以使用的楼层
        response = HttpClient.doPost(Config.elevatorRecord, requestBody)
        logger.debug('梯控卡号上传结果:%s'%(response))

        message = None

        if response['success']:
            message = requestBody['mac'] + '11' + requestBody['carNum'] + CommonUtils.getFloorHex(response['data'])
        else:
            message = requestBody['mac'] + '11' + requestBody['carNum'] + '4445'

        message = CommonUtils.xorOperation(bytes.fromhex(message))

        logger.debug('梯控卡号上传响应:%s' % (CommonUtils.bytesToHex(message)))

        UdpClient.sendUdpData(message, client)


    def elevatorTempPassValidate(self,data,client):
        '''
        梯控临时密码校验
        :param data:
        :param celint:
        :return:
        '''
        requestBody = {
            'serial':CommonUtils.bytesToHex(data[0:6]),
            'pass':CommonUtils.bytesToHex(data[7:9]),
        }
        logger.debug('梯控临时密码校验:%s'%(requestBody))
        response = HttpClient.doPost(Config.elevatorTempPassValidate,requestBody)
        logger.debug('梯控临时密码校验结果:%s'%(response))
        if response['success']:
            floors = response['data']
            message = None
            if floors:
                # 存在一个或者多个楼层权限
                message = requestBody['serial'] + '15' + CommonUtils.getFloorHex(floors) + '4F4B'
            else:
                # 校验失败,不存在楼层权限
                message = requestBody['serial'] + '15' + '4445'
            message = CommonUtils.xorOperation(bytes.fromhex(message))
            logger.debug('梯控临时密码响应:%s'%(CommonUtils.bytesToHex(message)))
            UdpClient.sendUdpData(message,client)

    def uploadElevatorPass(self,data,client):
        '''
        梯控密码记录上传
        :param data:
        :param client:
        :return:
        '''
        requestBody = {
            'mac': CommonUtils.bytesToHex(data[0:6]),
            'pass': CommonUtils.bytesToHex(data[7:12]),
        }
        logger.debug('梯控密码记录上传:%s' % (requestBody))
        response = HttpClient.doPost(Config.elevatorPassRecord,requestBody)
        logger.debug('梯控密码记录上传结果:%s'%(response))
        if response['success']:
            message = requestBody['mac'] + '1A4F4B'
            message = CommonUtils.xorOperation(bytes.fromhex(message))
            logger.debug('梯控密码记录上传响应:%s'%(CommonUtils.bytesToHex(message)))
            UdpClient.sendUdpData(message,client)

def start():
    '''
    启动udp服务
    :return:
    '''
    global server
    socketserver.UDPServer.allow_reuse_address = True
    server = socketserver.ThreadingUDPServer(('0.0.0.0', Config.udpServerPort), Handler)
    logger.debug('udp服务启动... ...')
    server.serve_forever()

def close():
    '''
    关闭udp服务
    :return:
    '''
    pass