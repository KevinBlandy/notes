
from urllib import request, parse
import json

url = 'http://service.tiancity.com/GB/Block/QueryCso'

req = request.Request(url)

reqBody = parse.urlencode([
    ('n1', 'undefined'),
    ('n2', 'ByGone丶文子'),
    ('ix', '1'),
    ('sz', '20')
]);

with request.urlopen(req, bytes(reqBody, 'UTF_8')) as rep:
    result = json.loads(rep.read());
    print(result['ReturnObject']['Html']);
