----------------------
接口					|
----------------------
	* 说起接口,那就肯定有抽象类
	* 感觉Python并没有接口的规范,只有抽象类的规范
	* 而接口的意义,在Python中也许并没有那么的重要
	* 网上接口的定义
		class user_service:			#接口
			def queryById(self,id):
					''''
									函数体是个注释,这个函数就是个抽象的
						:parm id    用户Id
					'''
		class user_service_impl(user_service): #类
			def queryById(self,id):
				print(id)

		obj = user_service_impl()
		obj.queryById(1)