---------------------------
Document
---------------------------
	# Document extends Node/EventTarget

		https://developer.mozilla.org/zh-CN/docs/Web/API/Document

	# 文档对象，也就是 DOM 树。


---------------------------
event
---------------------------

visibilitychange 
	* 页面切换事件，可以通过 hidden 属性来判断是切出还是切入


---------------------------
this
---------------------------
	documentElement
		* 返回 document 的根元素（例如，HTML 文档的 <html> 元素）。
	
	fullscreenElement
		* 返回当前页面中以全屏模式呈现的 Element，如果当前页面未使用全屏模式，则返回 null。
	
	fullscreenEnabled 
		* 只读属性，表明全屏模式是否可用。
		* 如果调用 Element.requestFullscreen() 能进入全屏模式，则为 true。如果全屏模式不可用，则该值为 false。
	
	body 
		* 返回当前文档的 <body> 或 <frameset> 节点，如果不存在此类元素，则为 null。
	
	doctype 
		* 表示与当前文档关联的文档类型声明（DTD）的 DocumentType 对象。
		* 如果当前文档没有关联 DTD，该属性将返回 null。
	
	title 
		* 获取或设置文档的标题。
	
	URL 
		* 返回文档 URL，字符串
			document.URL
			'http://127.0.0.1:5500/baz123123.html?hi=%E5%97%A8%E5%97%A8%E5%97%A8'
	
	domain
		* 返回文档的域名，已废弃。
	
	referrer
		* 当前页面就是从这个 URI 所代表的页面跳转或打开的。
		* 如果用户直接打开了这个页面（不是通过页面跳转，而是通过地址栏或者书签等打开的），则该属性为空字符串。
	
	forms 
		* 返回文档中的所有表单元素（HTMLCollection）。
		
	
	hidden
		* 只读属性，返回一个表示页面是否被视为隐藏的布尔值。
			document.addEventListener("visibilitychange", () => {
			  console.log(document.hidden);
			  // 修改行为……
			});
	
	visibilityState
		* 返回document的可见性，即当前可见元素的上下文环境。
		* 由此可以知道当前文档（页面）是在后台，或是不可见的隐藏的标签页，或者（正在）预渲染。可用的值如下：
	
			visible
			hidden
			prerender
	
		* 当此属性的值改变时，会递交 visibilitychange 事件给Document.


	querySelector(selectors);
		* 返回文档中与指定选择器或选择器组匹配的第一个 Element对象。如果找不到匹配项，则返回null。
		* 用于查询模式的CSS选择符可繁可简，依需求而定。如果选择符有语法错误或碰到不支持的选择符，则 querySelector() 方法会抛出错误。

	querySelectorAll(selectors);
		* 返回与指定的选择器组匹配的文档中的元素列表 (使用深度优先的先序遍历文档的节点)。
		* 返回的对象是 NodeList，注意，静态的快照。

	getElementById(id)
		* 根据 ID 检索元素，ID 必须是全局唯一的，如果不唯一则只返回第一个。
		* 如果没找到则返回 null。

	getElementsByClassName()

	getElementsByName()
		* 根据元素的 name 值检索元素。返回实时的 NodeList 集合。

		name
			* 要查找元素的 name 属性的值。

	getElementsByTagName(name)
		* 根据标签名称来检索元素，返回元素列表（HTMLCollection 对象），是一个实时列表，修改即生效。
		
		name
			* 标签名，如果使用 * ，则表示检索所有。


	getElementsByTagNameNS()

	write(markup)
		* 写入内容到文档中，内容可以 HTML 格式的文本。
		* 在已关闭（已加载）的文档上调用 document.write() 会自动调用 document.open()，这将清空文档。

	writeln(line)
		* 和 write 放一样，它会在文本最后添加一个换行符。
	
	createElement(tagName[, options]);
		* 创建元素
		
		tagName
			* 要创建元素类型的字符串，创建元素时的 nodeName 使用 tagName 的值为初始化，该方法不允许使用限定名称 (如："html:a")
			* 在 HTML 文档上调用 createElement() 方法创建元素之前会将tagName 转化成小写
			* 在 Firefox、Opera 和 Chrome 内核中，createElement(null) 等同于 createElement("null")

		options可选
			* 可选，ElementCreationOptions 对象，是包含一个属性名为 is 的对象，该对象的值是用 customElements.define() 方法定义过的一个自定义元素的标签名。为了向前兼容较老版本的 Custom Elements specification, 有一些浏览器会允许你传一个值为自定义元素的标签名的字符串作为该参数的值。可以参考本页下方的 Web component 示例 Google 的 Extending native HTML elements 文档仔细了解如何使用该参数。
	
	createTextNode(data)
		* 创建文本节点。
		
		data
			* 包含要放入文本节点的数据的字符串。
			* 文本会应用HTML或XML编码。
	
	createNodeIterator(root, whatToShow, filter)
		* 返回一个新的 NodeIterator 对象。

		root
			* 作为遍历根节点的节点。
		whatToShow
			* 数值代码，表示应该访问哪些节点。
		filter
			* NodeFilter对象或函数，表示是否接收或跳过特定节点。
			* NodeFilter 预定义了很过过滤用的常量，还可以组合使用
				let whatToShow = NodeFilter.SHOW_ELEMENT | NodeFilter.SHOW_TEXT;

		entityReferenceExpansion
			* 布尔值，表示是否扩展实体引用。这个参数在HTML文档中没有效果，因为实体引用永远不扩展。
	
	createTreeWalker(root, whatToShow, filter)
		*  返回新创建的 TreeWalker 对象，是 createNodeIterator 高级版。
	
	createRange();
		* 返回一个新的 Range 对象。
	
	
	exitFullscreen();
		* 让当前文档退出全屏模式。
		* 调用这个方法会让文档回退到上一个调用 Element.requestFullscreen() 方法进入全屏模式之前的状态。
		*  如果一个元素 A 在请求进去全屏模式之前，已经存在其他元素处于全屏状态，当这个元素 A 退出全屏模式之后，之前的元素仍然处于全屏状态。浏览器内部维护了一个全屏元素栈用于实现这个目的。

			// 点击切换全屏模式
			document.onclick = function (event) {
			  if (document.fullscreenElement) {
				document.exitFullscreen();
			  } else {
				document.documentElement.requestFullscreen();
			  }
			};
	
---------------------------
static
---------------------------