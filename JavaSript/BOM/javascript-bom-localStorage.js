----------------------
window.localStorage		|
----------------------
	# localStorage 对象存储的数据没有时间限制。第二天、第二周或下一年之后，数据依然可用。
	# sessionStorage 方法针对一个 session 进行数据存储。当用户关闭浏览器窗口后，数据会被删除。
	# 仅仅可以存储基本数据类型,不能直接存储对象
	# 如果需要存储对象,需要转换为JSON字符串
	# 同名的参数可以被覆盖
	# 可以使用 for in 进行迭代
		for(var name : ins localStorage){
			var value = localStorage[name];
		}
	# 不允许跨域访问,同源共享数据


----------------------
window.localStorage	属性|
----------------------
	localStorage.属性名称
		* 其实就是可以对指定的属性进行存取

	localStorage.['属性名称']
		* 也支持数组的方式来进行属性的设置和读取
	
	length
		* 长度,有多少个数据

----------------------
window.localStorage	方法|
----------------------
	localStorage.setItem(key,value);
		* 保存数据

	localStorage.getItem(key);
		* 读取数据

	localStorage.removeItem(key);
		* 删除单个数据

	localStorage.clear();
		* 删除所有数据

	localStorage.key(index);
		* 得到某个索引的key

----------------------
window.localStorage	事件|
----------------------
	onstorage
		* 该事件应该注册到window对象上
		* 事件对象的特殊属性
			key				//key
			newValue		//新值
			oldValue		//如果是新插入,该值为null
			storageArea		//是 localStorage 还是 sessionStorage
			url				//当前URL

		* 这个事件属于广播事件,会通知当前浏览器中打开的所有相同的页面
		* 当同源页面的某个页面修改了localStorage,其余的同源页面只要注册了storage事件，就会触发
		* 在同一个网页修改本地存储，又在同一个网页监听，这样是没有效果的。
		* Chrome 下必须由其他页面触发。IE,Firefox 可以本页面触发
		* demo	
			window.addEventListener('storage',function(event){
				
			});
		
