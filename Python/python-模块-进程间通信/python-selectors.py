------------------------------
selectors					  |
------------------------------
	# selectors模块是在python3.4版本中引进的，它封装了IO多路复用中的select和epoll，能够更快，更方便的实现多并发效果
	# 该模块允许高层高效的 I/O 多路复用
		https://yiyibooks.cn/xx/python_352/library/selectors.html
		https://docs.python.org/3/library/selectors.html#module-selectors
	# 建立在 选择 select 基础之上,鼓励使用此模块,触发想精确控制系统级别原函数的使用
	# 该模块定义了一个抽象基类以及N多的实现
		BaseSelector
			SelectSelector	基于select.select()的选择器
			PollSelector	基于select.poll()的选择器
			EpollSelector	基于select.epoll()的选择器
			DevpollSelector	基于select.devpoll()的选择器
			KqueueSelector	基于select.kqueue()的选择器
	
	# 跟java的nio没啥区别
	# 模块下的其他类
		class Mapping(Collection)

------------------------------
模块属性和方法				  |
------------------------------
	EVENT_READ
	EVENT_WRITE
		* 读写事件标识
	DefaultSelector
		* 默认选择器类使用在当前平台上可用的最有效的实现,大多数用户的默认选择
		* 默认会用epoll,如果系统中没有epoll(比如windows)则会自动使用select

------------------------------
模块类	SelectorKey			  |
------------------------------
	# 将文件对象关联到其底层文件描述符
	
	fileobj
		* 注册的文件对象(连接)

	fd
		* 文件标识符

	events
		* 返回关注的事件

	data
		* 注册时设置的关联数据

------------------------------
模块类	BaseSelector		  |
------------------------------
	# 基类
	# 方法
		SelectorKey register(fileobj, events, data=None)
			* 注册

		SelectorKey unregister(fileobj)
			*  取消注册

		SelectorKey modify(fileobj, events, data=None)
			* 修改关心的事件或者data

		[(SelectorKey, events)] select(timeout=None)
			* 开始轮询

		None close()
			* 关闭

		SelectorKey get_key(fileobj)
			*  获取指定fileobj的SelectorKey

		Mapping get_map()
		

	
------------------------------
demo						  |
------------------------------
	
import selectors
from selectors import SelectorKey
import socket

# 默认的selector
selector = selectors.DefaultSelector()

server = socket.socket(family=socket.AF_INET, type=socket.SOCK_STREAM)
server.bind(('0.0.0.0', 1024))
server.listen()
server.setblocking(False)  # 设置为非阻塞模式

# 注册读事件
selector.register(server, selectors.EVENT_READ) 


# 处理连接事件
def accept(key,event):
    connection,address = key.fileobj.accept()           # 获取到客户端连接
    connection.setblocking(False)                       # 设置为非阻塞模式
    selector.register(connection, selectors.EVENT_READ) # 注册读事件

# 处理读取事件
def readable(key,event):
    connection = key.fileobj
    data = connection.recv(1024)    # 读取数据
    if data:
        print('收到数据:%s'%(data.decode('UTF_8')))
    else:
        selector.unregister(connection) # 连接关闭,移除注册
        connection.close()

while True:
    events = selector.select()
    for key,event in events:
        print(key,event)
        if key.fileobj == server:
            accept(key,event)       # 新的连接
        else:
            readable(key, event)    # 可读就绪