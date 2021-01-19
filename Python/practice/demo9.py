from urllib import request,parse

req = request.Request('http://www.96096kp.com/ecsite/control/updateSelfInformation')

req.add_header('Host', 'www.96096kp.com')
req.add_header('User-Agent','Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:62.0) Gecko/20100101 Firefox/62.0')
req.add_header('Accept','application/json, text/javascript, */*; q=0.01')
req.add_header('Accept-Language','zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2')
#req.add_header('Accept-Encoding','gzip, deflate')
req.add_header('Referer','http://www.96096kp.com/ecsite/control/userData')
req.add_header('Content-Type','application/x-www-form-urlencoded; charset=UTF-8')
req.add_header('X-Requested-With','XMLHttpRequest')
req.add_header('Cookie','ykx_wap_token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjI4MjY0NSwic3ViIjoic3ViamVjdCIsIm5iZiI6MTUzODI5OTMxMSwiaXNzIjoiaXNzdWVyIiwidHlwZSI6IjEiLCJleHAiOjE1Mzg4MjQ5MTEsImlhdCI6MTUzODI5OTMxMSwianRpIjoiaWQifQ.pd792otyO3y5xWERb7Mxj0DfHYXuy1BzdiseDDxFPdQ; JSESSIONID=02A18788492DE66BBFEDE9594BE6B483.jvm1; UM_distinctid=1660e54e30c343-0be86433864ac1-143c7340-1fa400-1660e54e30d6f3; CNZZDATA1258728964=2030848198-1537836918-null%7C1538299291; Hm_lvt_7299c6dbd5750ffb2b56c44b9f4ad2ab=1537838737,1538299315; MEIQIA_VISIT_ID=1AgCDjL9caBO0Wkq89iE0pUkBU7; MEIQIA_EXTRA_TRACK_ID=1APQRzCyKrqP7syyIayPzzJidXE; ykx=20111181; Hm_lpvt_7299c6dbd5750ffb2b56c44b9f4ad2ab=1538299347')
req.add_header('Connection','keep-alive')

with request.urlopen(req, 'userName=KevinBlandy&telphone=18523570974&idNumber=500222199312096610&partyId=2100299'.encode(encoding='utf_8', errors='strict')) as rep:
    print(rep.read().decode('UTF_8'))