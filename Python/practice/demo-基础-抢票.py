from urllib import request,parse
import demjson
import time

# 需要查询的日期
data_time = '2017-10-01'

while True:
    # 模拟客户端
    client = request.Request("http://www.96096kp.com")
    client.add_header('Accept','*/*')
    client.add_header('Accept-Language','zh-CN,zh;q=0.8')
    client.add_header('Connection','keep-alive')
    client.add_header('Content-Length','268')
    client.add_header('Content-Type','application/x-www-form-urlencoded; charset=UTF-8')
    client.add_header('Host','www.96096kp.com')
    client.add_header('Origin','http://www.96096kp.com')
    client.add_header('Referer','http://www.96096kp.com/TicketMain.aspx')
    client.add_header('User-Agent','Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.79 Safari/537.36')
    client.add_header('X-Requested-With','XMLHttpRequest')
    with request.urlopen(client) as response:
        # 读取俩Cookie值
        request_param = {}
        for k,v in response.getheaders():
            if(k == 'Set-Cookie'):
                attr_list = v.split(';')
                for i in attr_list:
                    attr_str = i.split('=')
                    attr_name = attr_str[0].strip()
                    if(attr_name == 'ASP.NET_SessionId'):           # SESSION
                        request_param['ASP.NET_SessionId'] = attr_str[1]
                    elif (attr_name == 'ykx'):                      # 不知道是啥,但是服务器端要校验的一个值
                        request_param['ykx'] = attr_str[1]

        # 调用数据接口
        req = request.Request('http://www.96096kp.com/UserData/MQCenterSale.aspx')
        req.add_header('Accept', '*/*')
        req.add_header('Accept-Language', 'zh-CN,zh;q=0.8')
        req.add_header('Connection', 'keep-alive')
        req.add_header('Content-Length', '268')
        req.add_header('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8')
        req.add_header('Cookie','ASP.NET_SessionId={0}; UM_distinctid=15ec2341308982-097c49ad9c0854-5d153b16-1fa400-15ec2341309bc3; CNZZDATA1258728964=924638088-1506492017-null%7C1506502815; ykx={1}'.format(request_param['ASP.NET_SessionId'],request_param['ykx']))
        req.add_header('Host', 'www.96096kp.com')
        req.add_header('Origin', 'http://www.96096kp.com')
        req.add_header('Referer', 'http://www.96096kp.com/TicketMain.aspx')
        req.add_header('User-Agent','Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.79 Safari/537.36')
        req.add_header('X-Requested-With', 'XMLHttpRequest')

        # 请求消息体
        request_body = parse.urlencode([
            ('StartStation', '四公里交通换乘枢纽'),  # 出发站
            ('WaitStationCode', ''),
            ('OpStation', '-1'),
            ('OpAddress', '-1'),
            ('SchDate', data_time),
            ('DstNode', '扶欢'),  # 目的站
            ('SeatType', ''),
            ('SchTime', ''),
            ('SchCode', ''),
            ('txtImgCode', ''),
            ('cmd', 'MQCenterGetClass'),
            ('isCheck', 'false')
        ])

        with request.urlopen(req,data = bytes(request_body,'UTF_8')) as response:
            data = demjson.decode(response.read().decode('UTF_8'))
            # 如果班次大于 1 ,则有人退票
            total = data['totalCount']  # 总班次
            if(total > 1):
                # 有人退票
                pass
            for i in range(total):
                info = data['data'][i]
                print('发车时间:%s,剩余票数:%s'%(data_time + ' ' +info['SchTime'],info['SchTicketCount']))
        time.sleep(1)