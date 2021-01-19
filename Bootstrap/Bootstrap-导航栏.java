————————————————————————
3,JavaScript-导航		|
————————————————————————	
	* 导航效果
	.nav
		* 是标签页的基类
	.nav-tabs
		* 是标签页类样式
	.nav-pills
		* 标签页样式(胶囊)
	.active
		* 是标签页的状态类(当前样式)
	.nav-stacked
		* 胶囊式标签页堆放排序(垂直排序)
		
	样式一
	<ul class="nav nav-tabs">
		<li class="active"><a>菜单一</a></li>
		<li><a>菜单二</a></li>
		<li><a>菜单一</a></li>
		<li><a>菜单二</a></li>
	</ul>
	* 要加上a标签,才有效果,而且只是一个样式,需要我们自己添加js代码来实现页面的切换
	样式二
	<ul class="nav nav-pills">
		<li class="active"><a>菜单一</a></li>
		<li><a>菜单二</a></li>
		<li><a>菜单一</a></li>
		<li><a>菜单二</a></li>
	</ul>
	
	面包屑导航(路径导航)
	<ul class="breadcrumb">
		<li>菜单一</li>
		<li>菜单二</li>
		<li>菜单三</li>
		<li>菜单四</li>
		<li>菜单五</li>
	</ul>



————————————————————————
3,JavaScript-导航条		|
————————————————————————
	.navbar
		* 导航栏的基类,用于<nav>元素
	.navbar-default
		* 导航栏默认样式,用于<nav>元素
	.navbar-inverse
		* 导航栏反色，用于<nav>元素
	.container
		* 是<nav>的子元素,导航栏内容都放入其中
	.navbar-header
		* 导航栏头部样式
	.nav-brand
		* 设置品牌图标样式
	.collapse
		* 是折叠导航栏的样式基类
	.navbar-collapse
		* 是折叠导航栏样式
	.nav
		* 是导航栏的链接基类
	.navbar-nav
		* 是导航栏的链接样式
	.navbar-form
		* 导航栏菜单，可以使表单元素排在同一行,一般顶部的搜索框就是这么整
	.navbar-left
	.navbar-right
		* 组件排列，导航链接，表单，摁扭或文本对其
	.navbar-btn
		* 对于不在<form>中的button元素,实现垂直对其
	.navbar-text
		* 对于导航栏的普通文本有了行距和颜色，常用于<p>元素
	.navbar-fixed-top
		* 导航栏固定在顶部,用于<nav>元素
		* 需要为body设置padding-top:70px;
	.navbar-fixed-botton
		* 导航栏固定在底部,用于<nav>元素
		* 需要为body设置padding-botton:70px;
	.navbar-static-top
		* 导航栏固定在底部,用于<nav元素>,固定定死在顶部
		* 会随着滚动条移动而消失
	<nav class="navbar navbar-default">
			<!--导航栏头部信息 -->
			<div class="container">
				<!--网页ＬＯＧＯ或者名称 -->
				<div class="navbar-header">
					<!--网页名称 -->
					<a class="navbar-brand">Ans</a>
					<!--欢迎语 -->
					<p class="navbar-text">这里是黯势</p>
				</div>
				<!--导航条主链接 -->
				<div class="collapse navbar-collapse">
					<ul class="nav navbar-nav">
						<li class="active"><a>入侵</a></li>
						<li><a>渗透</a></li>
						<li><a>攻击</a></li>
						<li><a>破解</a></li>
					<ul>
					<!--搜索框 -->
					<form class="navbar-form navbar-left">
						<div class="form-group">
							<input type="text" class="form-control" placeholder="搜索"/>
						</div>
						<button　type="button" class="btn btn-success">Serch</button>
					</form>
				</div>
			</div>
		</nav>
		* 有问题

