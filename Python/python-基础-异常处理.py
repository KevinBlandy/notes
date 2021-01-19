------------------------
python-异常处理			|
------------------------
	* 跟JAVA差不多
	* 异常的处理有优先级,在继承最底层的类应该在上面.
	* 处理语法
		# 每个异常单独 catch
			try:
				pass
			except LookupError as error:
				pass
			except KeyError as error:
				pass
			finally:
				pass
		
		# 多个异常统一 catch
			try:
				pass
			except (LookupError,KeyError) as error:
				pass
			finally:
				pass
		
		# 带 else 的catch
			try:
				pass
			except (LookupError,KeyError) as error:
				pass
			else:
				print('一切正常,当程序正常,没有发生异常的时候执行')
			finally:
				pass
	
	* 抛出异常
		raise BaseException("哦豁,异常了")


		
------------------------
python-异常类体系		|
------------------------
	Exception

------------------------
python-自定义异常		|
------------------------
	* 自定义类,继承 :Exception 类
	* 使用 raise 抛出异常(其实也是创建异常实例)
	* demo
		class ServiceException(Exception):
			def __init__(self,info):
				self.info = info
			
		#	def __str__(self):
		#		return self.info
		try:
			raise ServiceException('自定义异常信息')
		except ServiceException as serviceException:
			print(serviceException) # 自定义异常信息

	