-------------------------------
EasyUI-draggable				|
-------------------------------
	# 用来创建一个可拖拽的组键
	# 属性名称
		easyui-draggable
	

-------------------------------
EasyUI-draggable属性			|
-------------------------------
	proxy
		* 在拖动的时候使用的代理元素，当使用'clone'的时候，将使用该元素的一个复制元素来作为替代元素。如果指定了一个函数, 它将返回一个jquery对象。 
		* demo
			//下面的例子显示了如何创建一个简单的代理对象。
			$('.dragitem').draggable({
				proxy: function(source){
					var p = $('<div style="border:1px solid #ccc;width:80px"></div>');
					p.html($(source).html()).appendTo('body');
					return p;
				}
			});


	revert
		* 该值如果为 true,则鼠标拖动,松开后.元素会返回起始位置
		* 其实就是让你拽着玩,你一放开.就会滑回原来的位置
	
	cursor
		* 拖动的时候,指定一个CSS,其实就是说.拖动的时候.样式会有变化
		* 这个css必须先存在
	
	deltaX
		* 被拖动的元素对应于当前光标位置x。
		* 参数是一个 Number
	
	deltaY
		* 被拖动的元素对应于当前光标位置y。
		* 参数是一个 Number
	
	handle
		* 开始拖动的句柄
		* 参数是一个选择器.也就是说必须要拖拽选择器指定的组键.才可以拖动元素
	
	disabled
		* true/false 如果为 true 则禁止该元素被拖动
	
	edge
		* 可以在其中拖动的容器宽度
		* 参数是一个 Number
	
	axis
		* 定义元素移动的轴向，可用值有：'v'或'h'，当没有设置或设置为null时可同时在水平和垂直方向上拖动。
	
	delay
		* 延迟,定义在多少毫秒后才进行移动.
		* 参数是一个 Number
	
-------------------------------
EasyUI-draggable事件			|
-------------------------------
	'事件的所有参数是:e'
	onBeforeDrag
		* 在拖动之前触发,返回  false,可以阻止事件
	
	onStartDrag
		* 在目标参数开始被拖动的时候触发
	
	onDrag
		* 在拖动过程中触发.当不能再拖动时返回false。
	
	onStopDrag
		* 在拖动停止时触发。


-------------------------------
EasyUI-draggable方法			|
-------------------------------
	options
		* 返回属性对象
	
	proxy
		* 如果代理属性被设置则返回该拖动代理元素。
	
	enable
		* 允许拖动
	
	disable
		* 禁止拖动
	
