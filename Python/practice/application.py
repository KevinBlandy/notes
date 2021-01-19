from urllib import request,parse
import json
import base64
import time

def instruction():
    req = request.Request('http://127.0.0.1:5236/instruction')
    req.add_header('Content-Type', 'application/json')
    with request.urlopen(req,data = bytes(json.dumps({'serial':'1Y1101','data':{'AcsRes':'1','ActIndex':'1','Time':'1','Event':'10','Note':'出名','Name':'习近平'}}),'UTF_8')) as rep:
        pass

def open():
    req = request.Request('http://192.168.0.101/cdor.cgi?open=0')
    req.add_header('Authorization', 'Basic ' + base64.b64encode(bytes('admin:888888','iso_8859_1')).decode('iso_8859_1'))
    print(req)
    with request.urlopen(req) as rep:
        pass

def card():
    req = request.Request('http://127.0.0.1:5236/card')
    form = parse.urlencode([
        ('Serial','0004A38FC4E5'),
        ('Card','1234567890'),
        ('type','0')
    ])
    with request.urlopen(req,data = bytes(form,'UTF_8')) as rep:
        print(rep)
        
if __name__ == '__main__':
    while True:
        instruction()
        time.sleep(5)
    #open()
    #card();