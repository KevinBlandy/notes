------------------------
Bootstrap-布局容器样式	|
------------------------
	* 用于容器,一般一个大的div直接包裹整个网页
	.container
		* 固定宽度1170px,响应式的
	.container-fluid
		* 自由宽度(100%),响应式

------------------------
Bootstrap-标题样式		|
------------------------
	h1/.h2 .. .h6
		* 有<h1>标题字体的大小,但是其实是一个行内元素,不会换行
		* <span class="h1">h1大小的文字</span>

	help-block
		* 标在p标签上啥的,让文字以:帮助信息的形式出现
	
	page-header
		* 添加在<h1>标签之类上的,标题下方有分割线
	
	<small>小标题</small>
		* 一般添加到<hx>标签之后,会小一点,以副标题的形式出现

------------------------
Bootstrap-文本样式		|
------------------------

	# 行内文本样式
		* 还是使用传统标签
		<strong>	//粗体
		<i>			//斜体
		<em>		//html5新标记
		<del>		//删除线,html5新标记

	# 文本排版样式
		text-left
			* 左对齐<h1 class="text-left"></h1>
		text-center
			* 居中
		text-right
			* 右对齐
		text-justify
			* 两端对齐

	# 颜色颜色
		text-muted
		text-primary
		text-success
		text-info
		text-warning
		text-danger

------------------------
Bootstrap-列表样式		|
------------------------
	list-unstyled
		* 无符号,标记在ul/ol上,就没有前面恶心的小黑点了,而且会去掉左边的间距

	list-inline
		* 行内块,列表不独占一行,而是横向铺开

------------------------
Bootstrap-表格样式		|
------------------------

	table
		* 少量padding和水平方向分割线,就是没一行都有分割线

	table-striped
		* 有条纹的背景颜色(隔行变色)

	table-bordered
		* 带边框的表格

	table-hover
		* 鼠标悬停变色,移开恢复

	table-condensed
		* 紧凑的表格,单元格内补会减半

	table-responsive
		* 响应式表格,把table元素包裹在这个属性的元素内,就可以创建响应式表格
		* 要注意的是,这个东西是加在'包裹表格'的元素上,当窗口过小,就会出现滚动条
	
	table-striped 
		* <tbody>会有条纹

	# 行,或单元格背景色(下面这些仅仅是改变颜色)
		active		
		success
		info
		warning
		danger
		* 它们只能给<tr>,<td>,<th>添加,除此之外一个都不能

------------------------
Bootstrap-表单样式		|
------------------------
		form-group
			* 表单组样式,可以吧<label>和表单元素包在里面,可以获得更好的排列
		form-control
			* 表单元素样式,经常用于<input>,<textarea>,<select>元素
		form-inline
			* 内联表单样式,用于<form>元素,使表单中的元素一行排列
		radio-inline
			* 使一组单选框,在同一行
		checkbox-inline
			* 使一组复选框在同一行
		sr-only
			* 可以用于隐藏元素,加在哪个元素是哪个,哪个元素就会被隐藏
			* 可以加在任何元素

------------------------
Bootstrap-摁钮样式		|
------------------------
	# 可以作为摁扭使用的元素:<a>,<input>,<button>
	# 摁钮大小
		btn
			* 变粗变大,圆角,的摁扭
		active
			* 激活状态的摁扭
		disabled
			* 禁用状态的摁扭
		btn-lg
			* 大摁扭
		btn-sm
			* 小摁扭
		btn-xs
			* 超小的摁扭
		btn-block
			* 把摁扭拉伸到撑满整个父元素
	# 摁钮颜色
		btn-default
		btn-primary
		btn-success
		btn-info
		btn-warning
		btn-danger		
		btn-link			//链接形式展现,其实还是一个摁扭

------------------------
Bootstrap-图片样式		|
------------------------
	img-responsive
		* 给图片添加此样式，可以实现响应式的图片
	center-block
		* 图片居中样式,而不能使用text-center
	img-rounded
		* 圆角图片
	img-circle
		* 圆形图片
	img-thumbnail
		* 边框圆角

------------------------
Bootstrap-辅助类		|
------------------------
	# 背景颜色系列
		bg-primary
		bg-success
		bg-info
		bg-warning
		bg-danger

	# ICON符号系列
		三角符号
			<span class="caret"></span>
			
		显示一个关闭的x摁扭
			<button type="button" class="close">
				<span aria-hidden="true">x</span>
				<span class="sr-only">关闭</span>
			</button>

	# 浮动系列
		pull-left
			* 左浮动
		pull-right
			* 右浮动
		center-block
			* 设置元素为display:block,并且居中
		show
			* 强制元素显示
		hidden
			* 强制元素隐藏
		clearfix
			* 清除浮动
			* 注意,是添加到父级元素

	# 让内容块，网页居中
			center-block
			* <div ckass="center-block"></div>

	
	# 字体图标
		* 这个其实就是在一些摁扭上，有文字，而且有图标
		<button type="button" class="btn btn-primary btn-lg" style="text-shadow: black 5px 3px 3px;">
			<span class="glyphicon glyphicon-user"></span> 摁钮上的文字
		</button>
		* User前面会有个图标，而且还有阴影
		* 图标有很多,样式也有很多,没必要记使用到的时候去参考
			http://www.runoob.com/bootstrap/bootstrap-glyphicons.html

		
		
		
		
	
	 
	
