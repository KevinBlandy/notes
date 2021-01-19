from urllib import request
import json

target_ip = "59.110.167.11"
target_url = "http://ip.taobao.com/service/getIpInfo.php?ip=%s"%(target_ip)

with request.urlopen(target_url) as response:
    result = json.loads(response.read().decode(encoding="UTF-8"))
    print(result)
    
{
    'code': 0, 
    'data': {
        'country': '中国', 
        'country_id': 'CN', 
        'area': '华东', 
        'area_id': '300000', 
        'region': '山东省', 
        'region_id': '370000', 
        'city': '青岛市',
        'city_id': '370200', 
        'county': '', 
        'county_id': '-1', 
        'isp': '阿里云', 
        'isp_id': '1000323', 
        'ip': '115.28.2.168'
    }
}