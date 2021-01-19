---------------------
WindowGroup			 |
---------------------
	# Ext.WindowGroup
	# 又名:Ext.ZIndexManager
	# 只要是能浮动的组件,都可以交给它进行统一,批量的管理
	# 创建
		var windowGroup = Ext.create('Ext.WindowGroup');


---------------------
方法				 |
---------------------
	register
		* 注册一个window组件到管理器
		* 参数就是一个组件对象
	
	hideAll
		* 隐藏所有被管理的组件
	
	each
		* 循环遍历管理的组件
		* demo
			windowGrou.each(function(item){
				item.show();
			});
	
	show
		* 显示所管理的所有组件
	
	get
		* 通过id,获取到注册的组件
	
	getAcitve
		* 获取管理组件中,已经激活的主键
	
	bringToFront
		* 参数是管理组件中的任意组件的的ID值
		* 会把指定的组件,显示在最前端

	sendToBack
		* 把指定ID值的组件,放到最后面,跟上面相反
	
	unregister
		* 传递ID值,移除指定的组件
	
	create
		* 
	