-------------------------
Event-事件				 |
-------------------------
	# 鼠标事件
		onclick	
			* 当用户点击某个对象时调用的事件句柄。	
		oncontextmenu	
			* 在用户点击鼠标右键打开上下文菜单时触发	 
		ondblclick	
			* 当用户双击某个对象时调用的事件句柄。	
		onmousedown	
			* 鼠标按钮被按下。	
		onmouseenter	
			*当鼠标指针移动到元素上时触发。	
		onmouseleave	
			*　当鼠标指针移出元素时触发	
		onmousemove	
			* 鼠标被移动。	
		onmouseover	
			* 鼠标移到某元素之上。	
		onmouseout	
			* 鼠标从某元素移开。	
		onmouseup	
			* 鼠标按键被松开。	
	
	# 键盘事件
		onkeydown	
			* 某个键盘按键被按下。	
		onkeypress	
			* 某个键盘按键被按下并松开。	
		onkeyup	
			* 某个键盘按键被松开。

	# 表单事件
		onblur	
			* 元素失去焦点时触发	
		onchange	
			* 该事件在表单元素的内容改变时触发( <input>, <keygen>, <select>, 和 <textarea>)	
		onfocus	
			* 元素获取焦点时触发	
		onfocusin	
			* 元素即将获取焦点时触发	
		onfocusout	
			* 元素即将失去焦点时触发	
		oninput	
			* 元素获取用户输入时触发	
		onreset	
			* 表单重置时触发	
		onsearch	
			* 用户向搜索域输入文本时触发 ( <input="search">)	 
		onselect	
			* 用户选取文本时触发 ( <input> 和 <textarea>)	
		onsubmit	
			* 表单提交时触发	
	
	# 框架/对象（Frame/Object）事件
		onabort	
			* 图像的加载被中断。 ( <object>)	
		onbeforeunload	
			* 该事件在即将离开页面（刷新或关闭）时触发	
		onerror	
			* 在加载文档或图像时发生错误。 ( <object>,<body>和 <frameset>)	 
		onhashchange	
			* 该事件在当前 URL 的锚部分发生修改时触发。	 
		onload	
			* 一张页面或一幅图像完成加载。	
		onpageshow	
			* 该事件在用户访问页面时触发	 
		onpagehide	
			* 该事件在用户离开当前网页跳转到另外一个页面时触发	 
		onresize	
			* 窗口或框架被重新调整大小。	
		onscroll	
			* 当文档被滚动时发生的事件。	
		onunload	
			* 用户退出页面。 (<body> 和 <frameset>)

-------------------------
Event-绑定与取消		 |
-------------------------
	# 绑定一级事件
		<button onclick="test()"/>
	
	# 取消绑定一级事件
		document.getElementByTagName("button")[0].onclick = null;

	# 绑定二级事件
		* 可以为一种事件绑定N个执行方法,会依次执行
		* 可以为N种事件绑定N个执行方法

		Node.事件 = function(event){

		};

		Node.addEventListener('事件',function(event){

		},useCapture);

		* 此处的事件的名称需要删除'on'前缀
		* useCapture 默认值为 false, 即冒泡传递，当值为 true 时, 事件使用捕获传递。
		* 在 冒泡 中,内部元素的事件会先被触发，然后再触发外部元素。
		* 在 捕获 中,外部元素的事件会先被触发，然后才会触发内部元素的事件


	# 取消绑定二级事件
		Node.removeEventListener('事件',fun);
		

------------------------
Event-属性				|
------------------------
	# 组合按键相关
		altKey	
			* 返回当事件被触发时，"ALT" 是否被按下。
		shiftKey	
			* 返回当事件被触发时，"SHIFT" 键是否被按下。
		ctrlKey	
			* 返回当事件被触发时，"CTRL" 键是否被按下。
		metaKey	
			* 返回当事件被触发时，"meta" 键是否被按下。
	
	
	# 键盘事件相关
		keyCode	
			* 对于 keypress 事件，该属性声明了被敲击的键生成的 Unicode 字符码。
			* 对于 keydown 和 keyup 事件，它指定了被敲击的键的虚拟键盘码。虚拟键盘码可能和使用的键盘的布局相关。
		key	
			* 在按下按键时返回按键的标识符。
			* 就是键的名称
	
	# 鼠标事件相关
		clientX	
		clientY	
			* 鼠标相对于整个页面的坐标
			* 仅仅是可以看到的页面,无视滚动条的距离.览器窗口大小修改的时候,该值也会修改

		screenX	
		screenY	
			* 鼠标相对于屏幕的坐标。
		
		pageX
		pageY
			* 鼠标相对于整个页面的坐标
			* 会计算滚动条的高度
		
		offsetX
		offsetY	
			* 点击的地方,在元素的内部坐标
			* 说白了,相对于整个元素体(事件源),你戳的哪儿?

	# 节点相关
		relatedTarget	
			* 返回与事件的目标节点相关的节点。
		button	
			* 返回当事件被触发时，哪个鼠标按钮被点击。
		target	
			* 返回触发此事件的元素（事件的目标节点）。
		currentTarget	
			* 返回其事件监听器触发该事件的元素。
		

	# 其他
		cancelBubble	
			* 如果事件句柄想阻止事件传播到包容对象，必须把该属性设为 true。
		fromElement	
			* 对于 mouseover 和 mouseout 事件，fromElement 引用移出鼠标的元素。
		

		returnValue	
			*　如果设置了该属性，它的值比事件句柄的返回值优先级高。把这个属性设置为 fasle，可以取消发生事件的源元素的默认动作。

		srcElement	
			* 对于生成事件的 Window 对象、Document 对象或 Element 对象的引用。

		toElement	
			* 对于 mouseover 和 mouseout 事件，该属性引用移入鼠标的元素。
		x
		y	
			* 事件发生的位置的 x 坐标和 y 坐标，它们相对于用CSS动态定位的最内层包容元素。
	
		bubbles	
			* 返回布尔值，指示事件是否是起泡事件类型。
		cancelable	
			* 返回布尔值，指示事件是否可拥可取消的默认动作。
		
		eventPhase	
			* 返回事件传播的当前阶段。
		
		timeStamp	
			* 返回事件生成的日期和时间。
		type	
			* 返回当前 Event 对象表示的事件的名称。

------------------------
Event-方法				|
------------------------
		initEvent()			
			* 初始化新创建的 Event 对象的属性。

		preventDefault()	
			* 通知浏览器不要执行与事件关联的默认动作。
			* 例如:form里面的submit input,点击就会执行提交

		stopPropagation()	
			* 阻止事件流的产生
			* 仅仅执行当前的事件,不执行父级元素的事件
			* 冒泡,捕捉形的事件流都可以阻止
			* 一般只考虑冒泡(先执行小的,再执行大的)
		
