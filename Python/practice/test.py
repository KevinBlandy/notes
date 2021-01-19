import threading
import urllib

from http.server import BaseHTTPRequestHandler
from http.server import HTTPServer
from socketserver import ThreadingMixIn
 

class Http_Handler(BaseHTTPRequestHandler):
    def do_GET(self):
        self.send_response(200, message=None)
        self.send_header('Content-type', 'text/html')
        self.end_headers()
        self.wfile.write('Hello Python'.encode(encoding='utf_8', errors='strict'))
   
    def do_POST(self):
        # 响应设置
        self.send_response(200, message=None)
        self.send_header('Content-type', 'application/json;charset=UTF-8')
        self.end_headers()
        
        # 请求路径判断
        if not self.path == '/open':
            self.wfile.write(bytes(json.dumps(RESPONSE_BODY['not_found'],ensure_ascii=False),'UTF_8'))
            return
        
        # 获取POST的JSON请求体
        request_size = int(self.headers["Content-length"])
        request_body = self.rfile.read(request_size).decode(encoding='utf_8', errors='strict')
        request_obj = json.loads(request_body)
        
        # 响应客户端
        self.wfile.write(bytes(json.dumps(RESPONSE_BODY['success'],ensure_ascii=False),'UTF_8'))
class ThreadingHttpServer(ThreadingMixIn, HTTPServer):
    pass
     
server = ThreadingHttpServer(('localhost', 8080), Http_Handler)
server.serve_forever()
server.server_close()