------------------------
1,Bootstrap-栅格系统介绍	|
------------------------
	* Bootstrap的个创新，它提供了一套响应式(移动设备优先)的流式的栅格系统
	* Bootstrap把一个容器或者一个网页，平均分成了 12 列
	* Bootstrap的栅格系统，由一个行和多个列组成
	* 通过行列的形式，来创建网页布局，把具体的数据放入列当中
	* 栅格系统必须放在: container 和 container-fluid 中
	container	
		* 宽度是:1170px
	container-fluid
		* 宽度是 100%

------------------------
2,Bootstrap-栅格系统参数	|
------------------------
	row
		表示一个行
	col-xs-*	手机
	col-sm-*	平板
	col-md-*	中等屏幕
	col-lg-*	大屏幕
		* 代表占几列
		* 每列之间的间距大约是30像素
		* 如果div超过了12,那么超出的div会跳转到下一行
	* 结构
	<div class="container">
		<div class="row">
			<div class="col-md-6">
				占了６列
			<idv>
			<div class="col-md-6">
				占了６列
			<idv>
		</div>
	</div>
		
------------------------
3,Bootstrap-列偏移		|
------------------------
	* 也就是指，一个栏向右偏移多少各列
	* col-md-offset-*
	* col-md-offset-4		//指定的栏目向右编译２个列,中间就会空出来
	* <div class="col-md-9 info col-md-offset-3"></div>
	
------------------------
4,Bootstrap-列嵌套		|
------------------------
	* 嵌套列的思路,在某个栏中再嵌套一个完整的栅格系统
	<div class="container">
		<div class="row">
			<div class="col-md-3">
				左边占三列
			<idv>
			<div class="col-md-9">
				右边占９列
				<div class="row">
					左边占６列
					<div class="col-md-6"></div>
					右边占６列
					<div class="col-md-6"></div>
				</div>
			<idv>
		</div>
	</div>
	
