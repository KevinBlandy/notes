----------------------
Element
----------------------
	# Element extends Node/EventTarget

		https://developer.mozilla.org/zh-CN/docs/Web/API/Element
	
	# 表示 XML 或 HTML 元素，对外暴露出访问元素标签名、子节点和属性的能力。



		* 是最通用的基类，Document 中的所有元素对象（即表示元素的对象）都继承自它。它只具有各种元素共有的方法和属性。更具体的类则继承自 Element。


----------------------
this
----------------------
	tagName
		* 返回 element 元素的标签名。
		* 对于元素节点来说，tagName 属性的值和nodeName属性的值是相同的。
	
	children
		* 返回 一个 Node 的子elements ，是一个动态更新的 HTMLCollection。

	classList
		* 返回元素上的的 class 类名集合，是一个 DOMTokenList。
		* 可以使用 add()、remove()、replace() 和 toggle() 方法修改其关联的 DOMTokenList。
	
	attributes
		* 返回一个注册到节点的所有属性节点的实时集合（NamedNodeMap 对象）。
	
	animate(keyframes, options)
		* 创建一个新的 Animation ，将它应用于元素，然后运行动画。
		* 返回一个新建的 Animation 对象实例

	getAttribute(attributeName);
		* 返回元素上一个指定的属性值。如果指定的属性不存在，则返回 null 或 ""
		* 能取得不是HTML语言正式属性的自定义属性的值
	
	getAttributeNames()
		* 返回一个Array，该数组包含指定元素（Element）的所有属性名称，如果该元素不包含任何属性，则返回一个空数组。


	getAttributeNode(attrName)

	hasAttributes()
		* 节点是不是有一个或者多个属性

	setAttribute(name, value)
		* 设置指定元素上的某个属性值。如果属性已经存在，则更新该值；否则，使用指定的名称和值添加一个新的属性。
		
		name
			* 属性的名称的字符串
		
		value
			* 属性的值的字符串。任何指定的非字符串值都会自动转换为字符串。
	
	requestFullscreen(options)
		* 发出异步请求使元素进入全屏模式。

			options
				* 可选，FullscreenOptions 对象
				* 提供切换到全屏模式的控制选项。目前，唯一的选项是 'navigationUI'，这控制了是否在元素处于全屏模式时显示导航条 UI。
				* 默认值是 'auto'，表明这将由浏览器来决定是否显示导航条。
		
		* Ok 后返回一个 resolved 的 Promise，值为 undefined。
		* 这个方法需要用于触发，而不能直接通过顺序执行代码来自动全屏。
	

----------------------
static
----------------------