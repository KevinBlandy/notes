------------------------
导航					|
------------------------
	# 导航一般指页面引导性频道集合,多以菜单的形式呈现,可应用于头部和侧边
	# 是整个网页画龙点晴般的存在
	# 面包屑结构简单,支持自定义分隔符,千万不要忘了加载 element 模块
	# 虽然大部分行为都是在加载完该模块后自动完成的,但一些交互操作,如呼出二级菜单等,需借助element模块才能使用


------------------------
水平导航				|
------------------------
	<ul class="layui-nav" lay-filter="">
		<li class="layui-nav-item layui-this"><a href="">最新活动</a></li>
		<li class="layui-nav-item"><a href="">产品</a></li>
		<li class="layui-nav-item"><a href="">大数据</a></li>
		<li class="layui-nav-item"><a href="">解决方案</a>
			<dl class="layui-nav-child">
				<!-- 二级菜单 -->
				<dd>
					<a href="">移动模块</a>
				</dd>
				<dd>
					<a href="">后台模版</a>
				</dd>
				<dd>
					<a href="">电商平台</a>
				</dd>
			</dl>
		</li>
		<li class="layui-nav-item"><a href="">社区</a></li>
	</ul>

	<script>
		//注意：导航 依赖 element 模块，否则无法进行功能性操作
		layui.use('element', function() {
			var element = layui.element();
			//…
		});
	</script>

	layui-this : 指向当前分类



------------------------
垂直与侧边导航			|
------------------------
	<ul class="layui-nav layui-nav-tree" lay-filter="test">
		<!-- 侧边导航: <ul class="layui-nav layui-nav-tree layui-nav-side"> -->
		<li class="layui-nav-item layui-nav-itemed"><a
			href="javascript:;">默认展开</a>
			<dl class="layui-nav-child">
				<dd>
					<a href="javascript:;">选项1</a>
				</dd>
				<dd>
					<a href="javascript:;">选项2</a>
				</dd>
				<dd>
					<a href="">跳转</a>
				</dd>
			</dl>
		</li>
		<li class="layui-nav-item"><a href="javascript:;">解决方案</a>
			<dl class="layui-nav-child">
				<dd>
					<a href="">移动模块</a>
				</dd>
				<dd>
					<a href="">后台模版</a>
				</dd>
				<dd>
					<a href="">电商平台</a>
				</dd>
			</dl>
		</li>
		<li class="layui-nav-item"><a href="">产品</a></li>
		<li class="layui-nav-item"><a href="">大数据</a></li>
	</ul>

	<script>
		//注意：导航 依赖 element 模块，否则无法进行功能性操作
		layui.use('element', function() {
			var element = layui.element();
			//…
		});
	</script>
	
	* 水平,垂直,侧边三个导航的HTML结构是完全一样的不同的是：

	垂直导航需要追加class:layui-nav-tree 
	侧边导航需要追加class:layui-nav-tree layui-nav-side(侧边导航在我看来就是,垂直全部铺满)


------------------------
面包屑导航				|
------------------------
	<span class="layui-breadcrumb">
		<a href="">首页</a>
		<a href="">国际新闻</a>
		<a href="">亚太地区</a>
		<a><cite>正文</cite></a>
	</span>
	* 可以通过设置属性 lay-separator 来自定义分隔符
		lay-separator="-"
	
	* <cite><cite> ,标签可以作为当前页的标识




