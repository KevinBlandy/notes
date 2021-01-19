import socket
#client = socket.socket(type=socket.SOCK_DGRAM)
#client.setsockopt(socket.SOL_SOCKET,socket.SO_REUSEADDR,1)
#client.bind(('0.0.0.0',3025))
#client.sendto(bytes.fromhex('515148475485 1A 4879 FF'),('127.0.0.1',3025))


# client = socket.socket()
# client.connect(('127.0.0.1',1024))
# client.send(bytes("有点意思",'UTF-8'))
# data = client.recv(1024)
# print(data)

# testStr = '''
#     ndiqndqw单位签订大旗网$1的の委屈@#^$#^4451
# '''
# s = re.sub(r'[\W\u4e00-\u9fa5]',lambda s:"",testStr)
# print(s)
ip = '8.8.8.8'
ip = ''.join(list(map(lambda x:x.strip().zfill(3),ip.split('.'))))
print(ip)

