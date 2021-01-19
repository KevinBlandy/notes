
from http.server import BaseHTTPRequestHandler
from http.server import HTTPServer
from socketserver import ThreadingMixIn
import json
import time

import Config
import Logger
import UdpClient
import CommonUtils

logger = Logger.getLogger()

# 预定义响应
responseBody = {
    'success':{'success':True,'message':'ok'},
    'methodNotAllowd':{'success':False,'message':'请求方式不支持'},
    'notFound':{'success':False,'message':'请求路径未找到'},
    'systemError':{'success':False,'message':'系统异常'},
}

# content type
contentType = ('Content-type', 'application/json;charset=UTF-8')

# 处理器方法映射
controllers = {
    '/open':'open',
    '/setting':'doorSetting',
    '/card':'card',
    '/delay':'delay',
    '/setPass':'setPass',
    '/restart':'restart',
    '/setServerInfo':'setServerInfo',
    # ------------- 梯控api -------------
    '/setCards':'setCards',
    '/elevatorControll':'elevatorControll',
    '/timeAsync':'timeAsync',
    '/setElevatorKey':'setElevatorKey',
    '/setElevatorConfig':'setElevatorConfig',
    '/setElevatorPass':'setElevatorPass'
}


class HttpHandler(BaseHTTPRequestHandler):

    def do_GET(self):
        self.send_response(200, message=None)
        self.send_header(*contentType)
        self.end_headers()
        if self.path not in controllers.keys():
            self.wfile.write(bytes(json.dumps(responseBody['notFound'], ensure_ascii=False), 'UTF_8'))
        else:
            self.wfile.write(bytes(json.dumps(responseBody['methodNotAllowd'], ensure_ascii=False), 'UTF_8'))

    def do_POST(self):
        self.send_response(200, message=None)
        self.send_header(*contentType)
        self.end_headers()

        try:
            if self.path not in controllers.keys():
                self.wfile.write(bytes(json.dumps(responseBody['notFound'], ensure_ascii=False), 'UTF_8'))
                return

            request_obj = json.loads(self.rfile.read(int(self.headers["Content-length"])).decode(encoding='utf_8', errors='strict'))

            # 获取处理方法名称
            handlerMethod = controllers[self.path]

            # 获取处理方法并且执行
            result = getattr(self,handlerMethod)(request_obj)

            if result:
                self.wfile.write(bytes(json.dumps(result),'UTF_8'))

        except KeyError as exception:
            self.wfile.write(bytes(json.dumps({'success':False,'message':'JSON请求体格式异常,属性 %s 未找到'%(exception)}), 'UTF_8'))
        except Exception as exception:
            logger.error(exception)
            self.wfile.write(bytes(json.dumps(responseBody['systemError']), 'UTF_8'))


    def open(self,requestBody):
        '''
        执行开门
        :param requestBody:
        :return:
        '''
        logger.debug('执行开门:%s'%(requestBody))
        UdpClient.sendUdpData(bytes.fromhex(requestBody['message']), (requestBody['ip'], int(requestBody['port'])))
        return responseBody['success']

    def doorSetting(self,requestBody):
        '''
        执行udp门禁远程设置
        :param requestBody:
        :return:
        '''
        logger.debug('执行远程设置:%s' % (requestBody))

        ip = requestBody['ip']                  # 目标IP
        port = int(requestBody['port'])         # 目标端口
        operation = int(requestBody['type'])    # 操作类型
        header = requestBody['header'].zfill(2) # 机号
        param = requestBody['param'].zfill(2)   # 设置参数
        do = requestBody['do'].zfill(2)         # 继电器动作(operation)

        message = None

        if operation == 1:  # 设备初始化             03{0}0A
            template = '03{0}0A'
            message = template.format(header)
        elif operation == 2:  # 设置机号            05FF71{0}{1}
            template = '05FF71{0}{1}'
            message = template.format(param, CommonUtils.getHexComplement(param))
        elif operation == 3:  # 设置开门延迟        05{0}0E{1}{2}
            template = '05{0}0E{1}{2}'
            message = template.format(header, param, do)
        elif operation == 4:  # 设置扇区读写指令    09{0}11{1}
            template = '09{0}11{1}'
            message = template.format(header, param)
        elif operation == 5:  # 设置扇区区号        05{0}05{1}{2}
            template = ' 05{0}05{1}{2}'
            message = template.format(header, param, CommonUtils.getHexComplement(param))

        if not message:
            return

        message = message.upper()

        logger.debug("指令渲染结果:%s" % (message))

        message = CommonUtils.xorOperation(bytes.fromhex(message))

        logger.debug("指令运算结果:%s" % (CommonUtils.bytesToHex(message)))

        UdpClient.sendUdpData(message,(ip, port))
        return responseBody['success']

    def card(self,requestBody):
        '''
        卡片同步执行
        :param requestBody:
        :return:
        '''
        logger.debug('执行卡片同步:%s'%(requestBody))
        ip = requestBody['ip']
        port = int(requestBody['port'])
        serial = requestBody['serial']
        cards = requestBody['cards']
        if cards:
            for card in cards:
                cardNum = card['num']
                operation = card['operation']
                message = serial
                if operation == 'create':
                    message += '02'
                elif operation == 'delete':
                    message += '03'
                else:
                    continue
                cardNum = cardNum.zfill(10)
                message += cardNum
                logger.debug('同步卡片信息(%s):%s'%('添加' if operation == 'create' else '删除',message))
                message = CommonUtils.xorOperation(bytes.fromhex(message))
                # 发送udp包到设备
                UdpClient.sendUdpData(message,(ip,port))
        return responseBody['success']
    
    def delay(self,requestBody):
        '''
        设置开门延迟
        :param requestBody:
        :return:
        '''
        logger.debug('设置开门延迟:%s'%(requestBody))
        ip = requestBody['ip']
        port = int(requestBody['port'])
        serial = requestBody['serial']
        delay = requestBody['delay'].zfill(2)
        message = serial + '09' + delay
        message = CommonUtils.xorOperation(bytes.fromhex(message))
        logger.debug('延迟设置指令:%s'%(CommonUtils.bytesToHex(message)))
        UdpClient.sendUdpData(message,(ip,port))
        return responseBody['success']

    def setPass(self,requestBody):
        '''
        设置固定密码
        :param requestBody:
        :return:
        '''
        logger.debug('设置固定密码:%s'%(requestBody))

        ip = requestBody['ip']
        port = int(requestBody['port'])
        serial = requestBody['serial']
        pass_ = requestBody['pass']

        message = serial + '0B' + pass_
        message = CommonUtils.xorOperation(bytes.fromhex(message))

        logger.debug('固定密码设置指令:%s' % (CommonUtils.bytesToHex(message)))
        UdpClient.sendUdpData(message, (ip, port))
        return responseBody['success']

    def restart(self,requestBody):
        '''
        门禁重启
        :param requestBody:
        :return:
        '''
        logger.debug('重启门禁:%s'%(requestBody))
        for door in requestBody:
            serial = door['serial']
            ip = door['ip']
            port = int(door['port'])

            reverseSerial = list(serial)
            reverseSerial.reverse()

            message = CommonUtils.xorOperation(bytes.fromhex(serial + '0C' + ''.join(reverseSerial)))

            logger.debug('重启指令:%s'%(CommonUtils.bytesToHex(message)))
            UdpClient.sendUdpData(message,(ip,port))
        return responseBody['success']

    def setServerInfo(self,requestBody):
        '''
        设置设备的目标服务器ip和端口
        :param requestBody:
        :return:
        '''
        logger.debug('设置服务器信息:%s'%(requestBody))

        ip = requestBody['ip']
        port = int(requestBody['port'])
        serverIp = requestBody['serverInfo']['ip']
        # 端口按照协议来说,最大长度为4,也就是说端口号只能是 0 - 9999
        serverPort = requestBody['serverInfo']['port']
        # 去除'.',ip每一段必须是3位长度,不足补0
        serverIp = ''.join(list(map(lambda x: x.strip().zfill(3), serverIp.split('.'))))
        message = requestBody['mac'] + '0D' + serverIp + serverPort.zfill(4)
        message = CommonUtils.xorOperation(bytes.fromhex(message))
        logger.debug('执行服务器信息设置:%s'%(CommonUtils.bytesToHex(message)))
        UdpClient.sendUdpData(message, (ip, port))


    ###################    梯控接口     ########################

    '''
    '''
    def setCards(self,requestBody):
        '''
        电梯下发/删除卡号
        :param requestBody:
        :return:
        '''
        serial = requestBody['serial']
        ip = requestBody['ip']
        port = int(requestBody['port'])
        cards = requestBody['cards']
        if cards:
            for card in cards:
                num = card['num'].zfill(10)
                operation = card['operation']
                floors = card.get('floors',[])

                message = serial

                if operation == "create":
                    # 下发卡
                    message += '12' + num + CommonUtils.getFloorHex(floors)
                else:
                    # 删除卡
                    message += '13' + num
                message = CommonUtils.xorOperation(bytes.fromhex(message))
                logger.debug('梯控卡号下发/删除:%s'%(CommonUtils.bytesToHex(message)))
                UdpClient.sendUdpData(message,(ip,port))

    def timeAsync(self,requestBody):
        '''
        主动时间同步
        :param requestBody:
        :return:
        '''
        serial = requestBody['serial']
        ip = requestBody['ip']
        port = int(requestBody['port'])

        message = serial + '14' + time.strftime("%y%m%d%H%M%S")
        message = CommonUtils.xorOperation(bytes.fromhex(message))
        logger.debug('梯控时间同步:%s'%(CommonUtils.bytesToHex(message)))
        UdpClient.sendUdpData(message, (ip, port))

    def elevatorControll(self,requestBody):
        '''
        电梯控制
        :return:
        '''
        serial = requestBody['serial']
        ip = requestBody['ip']
        port = int(requestBody['port'])
        floors = requestBody['floors']

        message = serial + '10' + CommonUtils.getFloorHex(floors)
        message = CommonUtils.xorOperation(bytes.fromhex(message))

        logger.debug('执行电梯控制:%s'%(CommonUtils.bytesToHex(message)))
        UdpClient.sendUdpData(message,(ip,port))

    def setElevatorKey(self,requestBody):
        '''
        下发密钥            密钥在心跳数据里面已经包含,该接口不做实现
        :param requestBody:
        :return:
        '''
        serial = requestBody['serial']
        ip = requestBody['ip']
        port = int(requestBody['port'])
        key = requestBody['key']
        message = serial + '16' + key
        message = CommonUtils.xorOperation(bytes.fromhex(message))
        logger.debug('设置梯控密钥:%s'%(CommonUtils.bytesToHex(message)))
        UdpClient.sendUdpData(message,(ip,port))


    def setElevatorCardFormat(self,requestBody):
        '''
        下发卡号输出格式        在心跳里面包含有,该接口不做实现
        :param requestBody:
        :return:
        '''
        serial = requestBody['serial']
        ip = requestBody['ip']
        port = int(requestBody['port'])
        cardFormat = requestBody['cardFormat']
        message = serial + '18' + cardFormat
        message = CommonUtils.xorOperation(bytes.fromhex(message))
        logger.debug('设置梯控卡号输出格式:%s' % (CommonUtils.bytesToHex(message)))
        UdpClient.sendUdpData(message, (ip, port))

    def setElevatorConfig(self,requestBody):
        '''
        设置电梯参数格式
        :param requestBody:
        :return:
        '''
        serial = requestBody['serial']
        ip = requestBody['ip']
        port = int(requestBody['port'])

        delay = requestBody['delay'] .zfill(2)       # 开门延迟
        switch = requestBody['switch'].zfill(2)      # 行程开关号

        message = serial + '19' + delay + switch
        message = CommonUtils.xorOperation(bytes.fromhex(message))
        logger.debug('设置电梯参数格式:%s'%(CommonUtils.bytesToHex(message)))
        UdpClient.sendUdpData(message, (ip, port))

    def setElevatorPass(self,requestBody):
        '''
        设置电梯固定密码
        :param requestBody:
        :return:
        '''
        serial = requestBody['serial']
        ip = requestBody['ip']
        port = int(requestBody['port'])
        _pass = requestBody['pass']

        message = serial + '1B' + _pass
        message = CommonUtils.xorOperation(bytes.fromhex(message))
        logger.debug('设置电梯固定密码:%s'%(CommonUtils.bytesToHex(message)))
        UdpClient.sendUdpData(message,(ip,port))

class ThreadingHttpServer(ThreadingMixIn, HTTPServer):
    pass


server = ThreadingHttpServer(('0.0.0.0', Config.httpServerPort), HttpHandler)

def start():
    '''
    启动服务器
    :return:
    '''
    server.serve_forever()