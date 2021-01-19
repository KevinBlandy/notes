
import socket
import Config

'''
    发送UDP数据包
'''
def sendUdpData(data,_client):
    client = socket.socket(type=socket.SOCK_DGRAM)
    client.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, True)
    # 指令数据包必须从udp监听端口发出
    client.bind(('0.0.0.0', Config.udpServerPort))
    client.sendto(data,_client)