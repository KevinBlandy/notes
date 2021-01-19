------------------------
Entry					|
------------------------
	* 输入框组键
	* 构造函数
		 def __init__(self, master=None, cnf={}, **kw)
	
	* 构造密码框
		

------------------------
Entry-属性				|
------------------------
	show
		* 对外展示的字符,可以使用它来构建一个密码输入框
		* entry = Entry(root,**{'show':'*'})
	textvariable
		* 使用tkvar与该输入框的输入相关联
		* 如果tkvar为IntVar,那么输入中文,在使用tkvar的get()的时候会抛出异常
	validate
		* 内容校验,该选项可以设置的值(字符串)有
			'focus'
				* 组件获得或者时区焦点的时候验证
			'focusin'
				* 组件获得焦点的时候验证
			'focusout'
				* 组件失去焦点的时候验证
			'key'
				* 输入框被编辑的时候验证
				* 如果 validate 的值为 'key',那么只有在 validatecommand 返回 True 的时候,才会把数据放入 textvariable 绑定的tkvar
				* 也就是说,在validate 的值为 'key'的时候,在验证函数中,不要使用 tkvar 的 get() 来获取输入的值
			'all'
				* 当上面出现任何一种情况的时候验证
			'none'
				* 关闭验证功能(默认值)
	validatecommand
		* 指定进行内容校验的函数,该函数需要通过返回 True/False 来决定内容是否合法
		* 验证方法中可以通过绑定 tkvar 来获取输入的变量值
	
	invalidcommand
		* 当 validatecommand 验证非法(返回 False)的时候,会调用该数的函数
	state
		* 设置输入框的状态,枚举值
			'readonly'		不能编辑,但支持复制,insert(0 / delete() 会被忽略
			'normal'		默认
			'disabled'		不能编辑,不支持复制,insert(0 / delete() 会被忽略(该值在constants中有预定义 - DISABLED)
		



------------------------
Entry-方法				|
------------------------
	str	get()
		* 返回输入框中的输入的数据
	
	delete(begin,len)
		* 删除输入框中的数据,从begin角标开始删除,删除len个数据
		* len 也可以直接使用 tk预定义的 END 属性,表示执行清空输入框直到末尾
	
	insert(index,data)
		* 在指定的下标插入字符串
		* index及其以后的数据向后移动
	

	
	
------------------------
Entry-验证				|
------------------------
	* tkinter为Entry的验证提供了一些额外的选项,来方便在验证的过程中获取更多有用的信息
		'%d'
			* 操作代码:0(删除操作),1(插入操作),2(失去焦点或textvariable变量值发生改变的时候)
		'%i'
			* 当用户尝试插入或者删除的时候,该选项表示插入或删除的位置(索引)
			* 如果是由:获得,失去焦点,textvariable值被修改而调用的验证函数,那么该值为 -1
		'%P'
			* 当输入框的值允许改变的时候,该值有效
			* 该值为输入框的最新文本内容
		'%s'
			* 该值为调用验证前输入框的文本内容
		'%S'
			* 当插入或删除操作触发验证函数的时候,该值有效
			* 该选择表示文本被插入和删除的内容
		'%v'
			* 该组件当前 validate 选项的值
		'%V'
			* 调用验证函数的原因
			* 该值是'focusin','focusout','key','forced'(textvariable值被修改)
		'%W'
			* 该组件的名称
		
	* 在验证函数中使用这些名称
		# 1,定义校验函数,预定义形参为自己需要的验证数据
		def validate_func(operation,index):
			print(operation,index)
			return True
		
		# 1,通过 root 组件(Tk实例对象)的register()函数,对验证函数进行包装
		# 也可以直接使用 @root.register 来对该函数进行装饰操作
		validate_func = root.register(validate_func)
		# 3,在实例化Entry组件时,指定 validatecommand 属性,该属性值应该是一个 tuple
		# 第一个参数就是包装后的校验函数,后面的参数就是你想要的属性,可以有多个,必须与校验函数的形参个数相对应
		validatecommand=(validate_func,'%d','%i')
	
	* 常用的验证函数
		# 必须是数字或者为空
		def only_num(data):
			return data.strip() == '' or data.isdigit()