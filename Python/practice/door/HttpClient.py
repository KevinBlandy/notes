'''
    Http客户端
'''

from urllib import request

import json

def doPost(url,requestBody):
    client = request.Request(url)
    client.add_header('Content-Type','application/json')
    with request.urlopen(client,data=bytes(json.dumps(requestBody),encoding='UTF_8')) as response:
        data = response.read()
        if data:
            return json.loads(data)
        return ''

def doGet(url):
    with request.urlopen(url) as response:
        responseBody = response.read()
        if responseBody:
            return json.loads(responseBody)
        return ''
