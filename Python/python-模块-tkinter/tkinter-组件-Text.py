---------------------------
Text						|
---------------------------
	* 用于处理和显示多行文本信息,也可以作为简单的文本信息
	* 构造函数
		def __init__(self, master=None, cnf={}, **kw)

---------------------------
Text-属性					|
---------------------------
	width
		* 宽度,该值是表示宽可以容纳的字符数量
	height
		* 高度,该值表示高度可以容纳的字符数量
	undo
		* bool 值,是否支持撤销操作,默认 False

	maxundo
		* 最大可以撤销的次数

	autoseparators
		* bool 值,默认为 True
		* Tkinter 会在认为每一次完整的操作结束后,自动的插入一个'分隔符'
		* 这个分隔符不可见
		* 可以理解为'操作分隔符',很多东西(例如:undo)需要还原操作,撤销操作
		* 该属性是用默认的方式来定义'操作分隔符',可以设置为 False,后手动的调用 edit_separator() 来自定义的插入一个操作分隔符

---------------------------
Text-方法					|
---------------------------
	insert(index,content,tags)
		* 插入文本数据信息,index 是一个索引值
		* index 表示插入的位置,可以使用预定义变量 INSERT/END 来表示,光标所在/最后的位置
		* index 也可以使用 mark 来表示要插入的位置
		* tags,应该是一个tupe,表示设置的tag标签
	
	delete(b,e)
		* 根据索引,删除指定的内容
		* b,表示开始的地方(包含),e表示结束的地方(不包含)
	
	get(b,e)
		* 根据索引获取内容
		* b,表示开始的地方(包含),e表示结束的地方(不包含)
	
	window_create(index,window)
		* 插入一个 window 组件,该window组件的 master 应该是当前Text的实例对象
		* index ,使用预定义变量 INSERT/END 来表示,光标所在/最后的位置
		* window 是关键字属性,该属性值应该是 window 组件的实例对象
		* demo
			text = Text(root)
			button = Button(text,text='摁钮')				# 创建摁钮实例
			text.window_create(INSERT,window=button)		# 设置摁钮实例到Text
			text.pack()	
	
	image_create(index,image)
		* 同样的,插入一个图片组件(PhotoImage实例)到Text组件中
		text = Text(root)
		photo = PhotoImage(file='D:\\趣图\\QQ图片20161226105344.gif')	# 创建image组件
		text.image_create(INSERT,image=photo)							# 设置该组件到文本中
	
	index(exp)
		* 该方法会把索引表达式转换为正儿八经的索引位置值
		* demo
			text = Text(root)
			text.insert(INSERT,'123456789')
			text.index("1.end")	# 1.9
	mark_set(name,index)
		* mark一个位置,index应该是一个索引表达式,指定行/列
	mark_unset(name)
		* 删除指定的mark
	mark_gravity(markName,direction)
		* 设置指定mark的重力,枚举值:LEFT/RIGHT
		* 控制新增的数据是在mark的左边或者右边,以及新增数据的排序方式
	
	tag_add(tag_name,b,e)
		* 添加一个标签,tag_name指定了标签的名称
		* b 表示开始的索引位置,e表示结束的索引位置(留头不留尾)
	tag_config(tag_name.**config)
		* 设置指定标签文本的属性信息
	tag_lower(tag_name)
		* 在多个tag修饰同一个文本信息的时候
		* 降低指定名称tag的优先级
	tag_bind(name,event,func)
		* 为指定的tag绑定事件
		* 回调函数中第一个参数会是 event 对象
	
	str search()
		* search(self, pattern, index, stopindex=None,forwards=None, backwards=None, exact=None, regexp=None, nocase=None, count=None, elide=None)
		* 检索指定的文本,返回该文第一次出现的索引(行.列),没有找到返回空字符串
		* 参数
			pattern		表达式
			index		开始检索的地方
		* 关键字参数
			stopindex	停止检索的索引值
	
	edit_undo()
		* 撤销上一步的操作,必须在 Text 实例的 undo 属性值为 True 的时候才有用
		* 如果没有可以撤销的操作了,那么会抛出异常
		* 内部会有一个撤销栈

	edit_redo()
		* 恢复操作
	
	edit_separator()
		* 手动的插入一个操作分隔符

---------------------------
Text-标记(Marks)			|
---------------------------
	* 标记是一个可以嵌入到Text文本组件中,不可见的对象
	* 它也是指向文本字符之间的位置
	* 系统预定义级别的mark
		INSERT
			* 当前光标所在的位置
		CURRENT
			* 对应于当前鼠标最近的位置
	* 自定义mark
	* mark的名字是由普通的字符串组成
	* 使用 mark_set(name,index),创建标记,'标记的索引值会随着insert,delete的操作发生修改'
	* 如果在mark标记的位置之前插入或删除文本,那么mark会跟随着一并移动(mark会一直固定于某个字符间隙)
	* 使用 insert(mark,text) 方法,可以在 mark 标记'前'插入新的文字值
		text.insert(INSERT,'123456789')
		text.mark_set("here","1.2")	# 标记名称叫做 here,定位在第1行的第2个字符(2的后面)
		text.insert("here","Hello")	# 从here表示的索引位置(1.2)处插入 "Hello",索引后移
		# 12Hello3456789
		print(text.index("here"))	# 此时,here表示的索引位置是 1.7
	* 总结
		* insert 插入索引的位置,索引向后移动,始终固定在指定字符间隙,而索引值会因为新增的缘故发生改变,相当于把 insert 的数据插入到了索引的左边
		* 多次 insert ,新增的数据会从左向右插入,索引会多次跟随指定的字符间隙移动
		* delete 删除同理,反正索引永远跟随间隙
	
	* 在mark标记后插入文字,'这种方式会mark表示的索引值会一直固定'
	* 使用 insert(mark,text) 方法,可以在 mark 标记'后'插入新的文字值
	* 使用 mark_gravity(markName, direction=None) 方法,可以控制新增文字的方向,默认是 RIGHT
		text.insert(INSERT,'123456789')
		text.mark_set("here","1.2")			# 标记名称叫做 here,定位在第1行的第2个字符(2的后面)
		text.mark_gravity("here",LEFT)		# 指定 here 标记,在被insert执行插入时,新增字符,插入到 LEFT/RIGHT
		text.insert("here","YU")			# 从here表示的索引位置(1.2)向后插入 "Hello",索引不会移动
		# 12YU3456789
		print(text.index("here"))			# 此时,here表示的索引位置是 1.2
		text.insert("here","YU")			# 从here表示的索引位置(1.2)向后插入 "WEN"
		# 12WENYU3456789
		print(text.index("here"))			# 此时,here表示的索引位置是 1.2
	* insert 插入索引的位置,新增文本会插入到索引位置的右边(取决于mark_gravity 的第二个参数),而 mark 表示的索引值不会发生改变
	* 多次 insert ,会把新增的数据从右往左插入
	* delete 同理
	
	* 删除mark
		mark_unset(name)

---------------------------
Text-标签(Tags	)			|
---------------------------
	* Tags通常用于改变Text组件中内容的样式和功能
	* 可以修改文本的字体,颜色,尺寸
	* 还允许把文本,嵌入的组件和图片与键盘的和鼠标等事件关联
	* 除了 user-definded tags(用户自定义tag),还有一个预定义的特殊 Tag:SEL
	* SEL('sel'),用于表示对应选中的内容(如果有的话)
	* 自定义tag,tag名称是普通合法的字符串,支持一次性描述多个tag,任何tag也可以用于描述多个不同的文本内容
		tag_add(tag_name,b,e)
	* 设置标签文本的属性
		tag_config(tag_name,**config)
	* demo	
		text.insert(END,"123456789")
		# 选择了 12 和 5 取标签名称:tag1
		text.tag_add('tag1',1.0,1.2,1.4,1.5)		
		# 对tag1标签表示的字符进行属性修改,设置背景色和文字颜色
		text.tag_config('tag1',background='blue',foreground='red')

	* 也可以先预定义tag属性,然后在插入的时候进行设置
		# 预定义tag属性
		text.tag_config('my_tag1',background='blue',foreground='red')
		# 在插入文本的时候,使用属性,注意,tags属性是一个 tuple,表示可以传递多个标签属性
		text.insert(INSERT,"Hello",("my_tag1",))
		
	* 同一段文本,有多个tag,那么后面的相同的tag样式会覆盖前面的
	* 使用 tag_lower(tag_name),来处理多个tag装饰一段儿文本的时候,tag属性的优先级
		tag_lower('my')

	* 支持事件绑定,text.tag_bind()
		text.insert(END,"Javaweb开发者社区 - ")

		# 创建一个tag,前景色为蓝色,具备下划线
		text.tag_config('url',foreground='blue',underline=True)

		# 在插入URL的时候,指定tag
		text.insert(END,'http://javaweb.io',('url',))

		# 为tag绑定鼠标事件
		text.tag_bind('url','<Enter>',lambda e,t=text:t.config(cursor='arrow'))  # 鼠标指向->变为箭头
		text.tag_bind('url','<Leave>',lambda e,t=text:t.config(cursor='xterm'))  # 鼠标一开->变为插入光标
		# 为tag绑定点击事件
		text.tag_bind('url','<Button-1>',lambda e,:webbrowser.open('http://javaweb.io'))# 通过 webbrowser 打开指定的url链接

		text.pack()
		

---------------------------
Text-索引(Indexs)			|
---------------------------
	* Indexs是用来指向Text组件中文本的位置,该组件索引也是对应实际字符之间的位置
	* Tkinter提供一系列不同的索引类型
		"{line}.{column}"
			* 行/列(行号从1开始,列号则是从0开始)
			* get("1.0","1.3"):从1行第0个字符开始到1行第3个字符结束
			* 也可以直接使用浮点数 get(1.0,1.3)
		"{line}.end"
			* 某行末尾
			* get(1.0,"1.end")
		INSERT
			* 该值表示光标所在的位置
		CURRENT
			* 对应于当前鼠标最近的位置
		END
			* 文本最末尾的下一个位置
		user-defined marks
		user-defined tags("tag.first","tag.last")
		window coordinate("@x.y")
		embedded object name(window,images)
		expressions
			* 用于'修改'任何格式的索引,用字符串的形式实现修改索引的表达式
			"+ count chars"
				* 把索引向前 ->移动 count 个字符,可以越过换行,但是不能越过END
			"- count chars"
				* 把索引向后 <-移动 count 个字符,可以越过换行,但是不能越过1.0
			"+ count lines"
				* 把索引向前移动 count 行,索引会尽量保证在同一列上,如果下一行的文字太少,不够则直接移到末尾
			"- count lines"
				* 把索引向后移动 count 行,索引会尽量保证在同一列上,如果上一行的文字太少,不够则直接移到末尾
			" linestart"
				* 把索引移动到当前行所在的第一个位置,注意,该表达式前面有个空格
			" lineend"
				* 把索引移动到当前行所在的最后一个位置,注意,该表达式前面后有个空格
			"wordstart"
				* 把索引移动到当前索引所在的单词的开头
				* 单词的定义:字母,数字,下划线,任何非空白字符的组合
			"wordend"
				* 把索引移动到当前索引所在的单词的末尾
				* 单词的定义:字母,数字,下划线,任何非空白字符的组合

	

---------------------------
Text-demo					|
---------------------------
	# 全文检索,获取指定内容在文中的所有Index
	text.insert(END,"Javaweb开发者社区")
	def find():
		# 定义开始检索的位置,从第一行的第一列开始检索
		start = "1.0"
		while True:
			# 检索指定内容第一次出现的位置
			pos = text.search("a", start, stopindex=END)
			if not pos: # 没有找到,则退出
				break
			# 打印位置
			print(pos)
			# 修改开始开始检索的位置,往前移动一个字符
			start = pos +"+ 1 chars"
	button = Button(root,text='退出',command=find)

	text.pack()
	button.pack()


	# 在撤销操作中,一次只撤销一个字符
	# autoseparators 定义为Fals,目的是为了让我们自己来插入操作分隔符
	text = Text(root,undo=True,autoseparators=False,maxundo=10)

	# 绑定按键操作,在每一个按键的时候,就插入一个操作隔符
	text.bind('<Key>',lambda e:text.edit_separator())

	text.insert(END,"Javaweb开发者社区")

	# 绑定摁钮,每次点击就返回上一个'操作'
	button = Button(root,text='撤销',command=lambda x=text:x.edit_undo())

	text.pack()
	button.pack()