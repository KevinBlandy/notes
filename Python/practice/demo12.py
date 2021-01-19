from urllib import request
from bs4 import BeautifulSoup

url = 'http://tool.oschina.net/commons'

with request.urlopen(url) as rep:
    html = rep.read().decode('UTF_8')
    soup = BeautifulSoup(html, 'html.parser')
    trs = soup.select('table tr')[1:]
    for tr in trs:
        tds = tr.find_all('td')
        
        for i,v in enumerate(tds):
            if i % 2 == 1:
                suffix = tds[i - 1].string
                contentType = v.string
                print(suffix,contentType)
