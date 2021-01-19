------------------------
协程					|
------------------------
	* 协程是一种用户级的轻量级线程,又叫:微线程
	* 协程是用户自己来编写调度逻辑的,对CPU来说,协程其实是单线程
	* CPU不用去考虑怎么调度,切换上下文,就省去了CPU的切换开销,所以协程在一定程度上又好于多线程
	* 优点
		无需线程上下文切换的开销
		不需要原子操作锁同步的开销
		方便切换控制流,简化编程模型
		高并发+高扩展性+低成本,一个CPU上W个协程不是问题
	* 缺点
		无法利用多核资源,协程本质上还是一个单线程
		进行阻塞操作(例如:IO)会阻塞整个程序
	

------------------------
协程-gevent				|
------------------------
	* 它是一个第三方的库,简化了协程的开发
	* 
		import gevent
		from gevent import monkey

		# 该函数的执行,不是必须的,
		# 它存在的意义是:使协程更大程度的发现IO阻塞,说白了.可以提高一丢丢的性能(有点像补丁一样的存在)
		monkey.patch_all()

		def fun1(arg):
			print('1')
			gevent.sleep(1)
			print('2')

		def fun2(arg):
			print('a')
			gevent.sleep(1)
			print('b')
		
		# 该函数,会执行[]中的所有方法,如果有方法遇到了 io 阻塞,会自动的进行切换操作
		# gevent.spawn() 第一个参数就是执行函数,后面的参数,执行该函数时需要传递的参数
		gevent.joinall([
			gevent.spawn(fun1,'arg'),
			gevent.spawn(fun2,'arg'),
		])
		'''
			1
			a
			2
			b
		'''
	* demo
		import gevent
		from gevent import monkey

		import time
		from urllib import request

		monkey.patch_all()

		def do_get(url):
			with request.urlopen(url) as response:
				print(response.read())

		start = time.time()
		gevent.joinall([
			gevent.spawn(do_get,'http://www.qq.com/'),
			gevent.spawn(do_get,'http://www.sina.com.cn/'),
			gevent.spawn(do_get,'http://www.sina.com.cn/'),
			gevent.spawn(do_get,'http://www.163.com/'),
		])
		print(time.time() - start)    # 0.2665996551513672

------------------------
协程-gevent-greenlet	|
------------------------
	* greenlet 是 gevent 下的轻量级协程切换模块
	* 其实就是通过 switch() 方法来完成线程上任务的切换
	* 但是这个遇到io要去手动的切换上下文
	* demo
		from greenlet import greenlet
		def fun1():
			print('1')
			g2.switch()
			print('2')
			g2.switch()
			
		def fun2():
			print('a')
			g1.switch()
			print('b')

		g1 = greenlet(fun1)
		g2 = greenlet(fun2)

		g1.switch()

		'''
			1
			a
			2
			b
		'''

------------------------
协程-gevent的tcp服务器	|
------------------------
import gevent

from gevent import socket,monkey
monkey.patch_all()

def handle_request(conn):
    while True:
        data = conn.recv(1024)
        if not data:
            conn.close()
            break
        print("recv:", data)
        conn.send(data)

def server(port):
    s = socket.socket()
    s.bind(('0.0.0.0', port))
    s.listen(5)
    while True:
        cli, addr = s.accept()
        gevent.spawn(handle_request, cli)

if __name__ == '__main__':
    server(7788)