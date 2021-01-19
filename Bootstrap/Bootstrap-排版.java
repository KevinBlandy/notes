------------------------
Bootstrap-标题			|
------------------------
	
	# 普通标题使用标签就好
		* <h1> - <h6>
	
	# 内联子标题
		* 通过<small标题完成>
			<h1>我是标题<small>我是副标题1 h1</small></h1> 
			* 就是在副标题文字上添加一个small标签
			* 就是附标题的颜色会暗点，小一点

		* 其实添加一个small的class属性也是可以的
			<h1>我是标题<span class="small">我是副标题</span></h1> 

	# 引导主体副本
		* 给段落添加强调文本
		<h2>引导主体副本</h2>
		<p class="lead">
			这是一个演示的实例。
		</p>
		* lead 这东西其实就是让p标签内的字体更大，更壮，行高更高

------------------------
Bootstrap-强调			|
------------------------
		* 其实就是文字样式
		<small>在标签内<small>
			* 文字大小为父标签文字大小的８５％
		<strong>在标签内</strong>
			* 粗壮的显示
		<em>在标签内</em>
			* 斜体显示文字
		text-left
			* 左对齐
		text-center
			* 居中
		text-right
			* 右对齐
		text-muted
			* 减弱的效果(感觉就是颜色变淡)		
		* 字体颜色
			text-pirmary
			text-success
			text-info
			text-warning
			text-danger

------------------------
Bootstrap-引用			|
------------------------
		<blockquote>
			<p>
				这是一个默认的引用实例。这是一个默认的引用实例。这是一个默认的引用实例。这是一个默认的引用实例。这是一个默认的引用实例。这是一个默认的引用实例。这是一个默认的引用实例。这是一个默认的引用实例。
			</p>
		</blockquote>

		<blockquote>
			这是一个带有源标题的引用。
			<small>Someone famous in 
				<cite title="Source Title">Source Title</cite>
			</small>
		</blockquote>

		<blockquote class="pull-right">
			这是一个向右对齐的引用。
			<small>Someone famous in 
				<cite title="Source Title">Source Title</cite>
			</small>
		</blockquote>
		
		
------------------------
Bootstrap-列表			|
------------------------		
	list-unstyled
		* 无符号,标记在ul/ol上,就没有前面恶心的小黑点了,而且会去掉左边的间距

	list-inline
		* 行内块,列表不独占一行,而是横向铺开,会自动去除前面小黑点
	
		
------------------------
Bootstrap-缩略图		|
------------------------	
	thumbnail
		* 给a标签.可以实现缩略图样式
	caption
		* 可以实现缩略图标题及其描述
	<div class="row">
		<div class="col-md-6">
			<a class="thumbnail"><img src="image/bygone.png"/></a>
			<div class="caption">
				<h4>菜单一</h4>
			</div>
		</div>
		<div class="col-md-6">
			<a class="thumbnail"><img src="image/bygone.png"/></a>
			<div class="caption">
				<h4>菜单二</h4>
			</div>
		</div>
	</div>
	*　一看就懂,配合栅格系统，添加两个标签，两个属性完		
		
		
		
		
			

	
------------------------
Bootstrap-更多排版样式	|
------------------------
		lead				使段落突出显示	
		small				设定小文本 (设置为父文本的 85% 大小)	
		text-left			设定文本左对齐	
		text-center			设定文本居中对齐	
		text-right			设定文本右对齐	
		text-justify		设定文本对齐,段落中超出屏幕部分文字自动换行	
		text-nowrap			段落中超出屏幕部分不换行	
		text-lowercase		设定文本小写	
		text-uppercase		设定文本大写	
		text-capitalize		设定单词首字母大写	
		initialism			显示在 <abbr> 元素中的文本以小号字体展示，且可以将小写字母转换为大写字母	
		blockquote-reverse	设定引用右对齐	
		list-unstyled		移除默认的列表样式，列表项中左对齐 ( <ul> 和 <ol> 中)。 这个类仅适用于直接子列表项 (如果需要移除嵌套的列表项，你需要在嵌套的列表中使用该样式)	
		list-inline			将所有列表项放置同一行	
		dl-horizontal		该类设置了浮动和偏移，应用于 <dl> 元素和 <dt> 元素中，具体实现可以查看实例	
		pre-scrollable		使 <pre> 元素可滚动 scrollable	