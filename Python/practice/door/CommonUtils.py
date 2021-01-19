import time

def bytesToHex(bins):
    '''
    把字节数组转换为16进制字符串,不带0x前缀
    :param bins:
    :return:
    '''
    return ''.join([hex(i).replace('0x','').zfill(2).upper() for i in bins])

def hexToBytes(hexStr):
    '''
    16进制字符串,转换为字节数组
    字符串不能包含 0x 前缀,会自动忽略空格
    :param hexStr:
    :return:
    '''
    return bytes.fromhex(hexStr)


def xorOperation(data):
    '''
    最byte数组进行异或运算,返回新byte数组,最后一个字节为最终异或运算的结果值
    异或结果值 = data[0] ^ data[1] ^ [2] .... data[-1]
    :param data:
    :return:
    '''
    tempValue = data[0] ^ data[1]
    bins = []
    for i in range(len(data)):
        bins.append(data[i])
        if i <= 1:
            continue
        tempValue = tempValue ^ data[i]
    bins.append(tempValue)
    return bytearray(bins)

def getHexComplement(num):
    '''
    获取指定16进制的反码,不包含0x前缀
    :param hex_num:
    :return:
    '''
    if type(num) == str:
        num = int(num,16)
    return hex(0xFF - num).replace('0x','').zfill(2).upper()

def now():
    '''
    获取当前时间字符串
    :return:
    '''
    return time.strftime("%Y-%m-%d %H:%M:%S")


def getFloorHex(targets):
    '''
    根据楼层获取协议hex编码(8个字节)
    :param targets:
    :return:
    '''
    floors = [['0'], ['0'], ['0'], ['0'], ['0'], ['0'], ['0'], ['0']    # 1 - 8楼
             ,['0'], ['0'], ['0'], ['0'], ['0'], ['0'], ['0'], ['0']
             ,['0'], ['0'], ['0'], ['0'], ['0'], ['0'], ['0'], ['0']
             ,['0'], ['0'], ['0'], ['0'], ['0'], ['0'], ['0'], ['0']
             ,['0'], ['0'], ['0'], ['0'], ['0'], ['0'], ['0'], ['0']
             ,['0'], ['0'], ['0'], ['0'], ['0'], ['0'], ['0'], ['0']
             ,['0'], ['0'], ['0'], ['0'], ['0'], ['0'], ['0'], ['0']
             ,['0'], ['0'], ['0'], ['0'], ['0'], ['0'], ['0'], ['0']]       # 57 - 64楼
    for i in set(targets):
        floor = int(i)
        if floor < 1:
            floor = 1
        elif floor > 64:
            floor = 64
        floors[floor - 1][0] = '1'
    result = ''
    floors.reverse()
    for i in floors:
        result += i[0]
    return hex(int(result, 2)).replace('0x', '').zfill(16)

def parseFloor(hexstr):
    '''
    把网络字节数据转换为楼层,返回[],可以表示多个楼层
    :return:
    '''
    result = []
    arr = list(str(bin(int(hexstr, 16))).replace('0b', '').zfill(64))
    arr.reverse()
    for i,n in enumerate(arr):
        if n == '1':
            result.append(i + 1)
    return result

if __name__ == '__main__':
    result = parseFloor('0000000000000200')
    print(result)

