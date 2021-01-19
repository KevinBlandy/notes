---------------------------
异步io模块					|
---------------------------
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

---------------------------
模块函数/属性				|
---------------------------
	Protocol
		
	sleep()
		* 异步的线程停止
	
	get_event_loop()

	wait()
	gather()
		* 跟 wait() 一样,不过它会返回一个[],里面的每个元素是每个协程执行完毕后的结果

	ensure_future()
	as_completed()
	open_connection(ip, port)
		* 打开一个异步IO连接
		* 返回 (reader,writer) 元组
	