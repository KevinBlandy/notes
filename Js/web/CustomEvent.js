----------------------------------------
CustomEvent extends Event
----------------------------------------
	# 自定义事件

	# 构造函数
		new CustomEvent(type, options)
		
		options
			* 一个对象，除 Event() 中定义的属性外，该对象还可以具有以下属性：

			detail
				* 可选，与事件相关联的事件相关值。处理器可使用 CustomEvent.detail 属性获取该值。默认为 null。

--------------------
this
--------------------

	detail
		* 返回初始化事件时传递的任何数据（只读）。

	initCustomEvent(type, canBubble, cancelable, detail);
		* 已弃用: 不再推荐使用该特性。

--------------------
static
--------------------