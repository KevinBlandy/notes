--------------------------------
aio								|
--------------------------------
	* 学习资料
		http://www.jianshu.com/p/b5e347b3a17c

	* 事件驱动形IO
	* 3.5版本以前
			1,在生成器上标识注解  @asyncio.coroutine
				然后在coroutine内部用yield from调用另一个 coroutine 实现异步操作
			2,创建 event_loop 对象(asyncio.get_event_loop())
			3,执行 event_loop 对象的 run_until_complete()函数,把生成器实例作为参数传递进去
				* 该函数参数也可以是  asyncio.wait(tast_arr) 函数返回值
				* 表示一次性执行多个生成器
				* demo : 
					tasks = [hello(), hello()]
					loop.run_until_complete(asyncio.wait(tasks))
			4,关闭 event_loop 对象 (close())
			* demo
				import asyncio
				@asyncio.coroutine
				def test(name):
					print('%s 开始执行'%(name))
					yield from asyncio.sleep(1)
					print('%s 执行完毕' % (name))

				loop = asyncio.get_event_loop()
				loop.run_until_complete(asyncio.wait([test('Kevin'),test('Litch'),test('Rocco')]))
				loop.close()
				
				Kevin 开始执行
				Litch 开始执行
				Rocco 开始执行
				Kevin 执行完毕
				Litch 执行完毕
				Rocco 执行完毕

	* 3.5 版本以后
		* 用asyncio提供的 @asyncio.coroutine 可以把一个generator标记为coroutine类型,然后在coroutine内部用yield from调用另一个 coroutine 实现异步操作
		* 为了简化并更好地标识异步IO，从Python 3.5开始引入了新的语法async和await,可以让coroutine的代码更简洁易读
		* 请注意,async和await是针对coroutine的新语法,要使用新的语法,只需要做两步简单的替换
		* 步骤
			1,生成器不使用 @asyncio.coroutine 注解标识,而使用 async 关键字标识
			2,不使用 yield from 语句,而使用 await 关键字
		* demo
			import asyncio
			async def test(name):
				print('%s 开始执行'%(name))
				await asyncio.sleep(1)
				print('%s 执行完毕' % (name))

			loop = asyncio.get_event_loop()
			loop.run_until_complete(asyncio.wait([test('Kevin'),test('Litch'),test('Rocco')]))
			loop.close()
			
			Kevin 开始执行
			Litch 开始执行
			Rocco 开始执行
			Kevin 执行完毕
			Litch 执行完毕
			Rocco 执行完毕

	* event_loop api
		create_task()
		run_until_complete()
		run_forever()
		create_server()
		close()
		stop()
			

--------------------------------
aio-入门						|
--------------------------------
	import asyncio

	@asyncio.coroutine
	def hello():
		print("Hello world!")
		# 异步调用asyncio.sleep(1):
		r = yield from asyncio.sleep(1)
		print("Hello again!")

	# 获取EventLoop:
	loop = asyncio.get_event_loop()
	# 执行coroutine
	loop.run_until_complete(hello())
	loop.close()
	'''
		Hello world!
		Hello again!
	'''

--------------------------------
aio-进阶						|
--------------------------------
	import threading
	import asyncio

	@asyncio.coroutine
	def hello():
		print('Hello world! (%s)' % threading.currentThread())
		yield from asyncio.sleep(1)
		print('Hello again! (%s)' % threading.currentThread())

	loop = asyncio.get_event_loop()
	tasks = [hello(), hello()]
	loop.run_until_complete(asyncio.wait(tasks))
	loop.close()
	'''
		Hello world! (<_MainThread(MainThread, started 6956)>)
		Hello world! (<_MainThread(MainThread, started 6956)>)
		Hello again! (<_MainThread(MainThread, started 6956)>)
		Hello again! (<_MainThread(MainThread, started 6956)>)
	'''

--------------------------------
aio-结果回调					|
--------------------------------
import asyncio

async def demo(var):
    asyncio.sleep(1)
    return var * var

# 创建 loop
loop = asyncio.get_event_loop()

# 创建 task
task = asyncio.ensure_future(demo(5))
# task添加监听,在回调函数中调用形参的 result() 获取结果信息
# task和回调里的x对象,实际上是同一个对象
task.add_done_callback(lambda x : print('result:%s'%(x.result())))

# 执行任务
loop.run_until_complete(asyncio.wait([task]))

loop.close()

--------------------------------
aio-server						|
--------------------------------
import asyncio

class SimpleProtocol(asyncio.Protocol):

    # 新的连接
    def connection_made(self, transport):
        self.transport = transport

    # 收到新的数据
    def data_received(self, data):
        # 把收到的数据响应给客户端
        self.transport.write(data)

    # 连接断开
    def connection_lost(self, exception):
        #server.close()
        pass

# 创建 event_loop
loop = asyncio.get_event_loop()

# 创建服务器实例
server = loop.run_until_complete(loop.create_server(SimpleProtocol, 'localhost', 1024))

# 运行
loop.run_until_complete(server.wait_closed())

--------------------------------
aio-实战						|
--------------------------------

# 旧版实现
import asyncio

@asyncio.coroutine
def wget(host):
    print('wget %s...' % host)
    connect = asyncio.open_connection(host, 80)
    reader, writer = yield from connect
    header = 'GET / HTTP/1.0\r\nHost: %s\r\n\r\n' % host
    writer.write(header.encode('utf-8'))
    yield from writer.drain()
    while True:
        line = yield from reader.readline()
        if line == b'\r\n':
            break
        print('%s header > %s' % (host, line.decode('utf-8').rstrip()))
    # Ignore the body, close the socket
    writer.close()

loop = asyncio.get_event_loop()
tasks = [wget(host) for host in ['www.sina.com.cn', 'www.sohu.com', 'www.163.com']]
loop.run_until_complete(asyncio.wait(tasks))
loop.close()


# 新版实现
import asyncio

async def demo(target):
    reader, writer = await asyncio.open_connection(*target)
    header = 'GET / HTTP/1.0\r\nHost: %s\r\n\r\n'%(target[1])
    writer.write(bytes(header,'UTF_8'))
    await writer.drain()
    while True:
        line = await reader.readline()
        if line == b'\r\n':
            break
        print('%s header > %s' %(target[1], line.decode('utf-8').rstrip()))
    writer.close()

loop = asyncio.get_event_loop()
loop.run_until_complete(asyncio.wait([demo(('www.qq.com',80)),demo(('www.javaweb.io',80))]))
loop.close()

--------------------------------
aio-asyncio						|
--------------------------------
	wait()
	gather()
		* 跟 wait() 一样,不过它会返回一个[],里面的每个元素是每个协程执行完毕后的结果
	ensure_future()
	get_event_loop()
	as_completed()
	open_connection(ip, port)
		* 打开一个异步IO连接
		* 返回 (reader,writer) 元组
    
    run()
	


	

	
