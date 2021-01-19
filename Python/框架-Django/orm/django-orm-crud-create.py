
--------------------------------
create-基本的保存一个对象		|
--------------------------------
	1,使用 objects 进行创建
		Model.objects.create()
			* 参数为实例属性的键值对
			* Model.objects.create(name='KevinBlandy',age=23)
	
	2,使用实例方法进行创建
		model = Model('KevinBlandy',23)
		model.save()
			* 创建model实例对象,直接调用实例的save()方法
			

