---------------------------
layui-栅格系统				|
---------------------------


---------------------------
layui-admin后台布局			|
---------------------------
	<div class="layui-layout layui-layout-admin">
		
		<!-- 顶部开始 -->
		<div class="layui-header">
			
			<!-- LOGO -->
			<div class="layui-logo">LOGO图片</div>
			
			<!-- 顶部左边菜单 -->
			<ul class="layui-nav layui-layout-left">
				<li class="layui-nav-item layui-this"><a href="">主页</a></li>
				<li class="layui-nav-item"><a href="">控制台</a></li>
				<li class="layui-nav-item"><a href="">商品管理</a></li>
				<li class="layui-nav-item"><a href="">用户</a></li>
				<li class="layui-nav-item">
					<a href="javascript:;">其它系统</a>
					<dl class="layui-nav-child">
						<dd><a href="">邮件管理</a></dd>
						<dd><a href="">消息管理</a></dd>
						<dd><a href="">授权管理</a></dd>
					</dl>
				</li>
			</ul>

			<!-- 顶部右边菜单 -->
			<ul class="layui-nav layui-layout-right">
				<li class="layui-nav-item">
					<a>消息<span class="layui-badge">99</span></a>
				</li>
				<li class="layui-nav-item">
					<a href="javascript:;">
						<img src="//qlogo1.store.qq.com/qzone/747692844/747692844/100?1500252668" class="layui-nav-img layui-anim layui-anim-scaleSpring">KevinBlandy
					</a>
					<dl class="layui-nav-child">
						<dd><a href="">切换小区</a></dd>
						<dd><a href="">修改密码</a></dd>
					</dl>
				</li>
				<li class="layui-nav-item"><a href="">安全退出</a></li>
			</ul>

		</div>
		<!-- 顶部结束 -->

		<!-- 右边手风琴菜单开始 -->
		<div class="layui-side layui-bg-black">
			<div class="layui-side-scroll">

				<ul class="layui-nav layui-nav-tree"  lay-filter="test">
					<li class="layui-nav-item layui-nav-itemed">
						<a class="" href="javascript:;">所有商品</a>
						<dl class="layui-nav-child">
							<dd><a href="javascript:;">列表一</a></dd>
							<dd><a href="javascript:;">列表二</a></dd>
							<dd><a href="javascript:;">列表三</a></dd>
							<dd><a href="">超链接</a></dd>
						</dl>
					</li>
					<li class="layui-nav-item">
						<a href="javascript:;">解决方案</a>
						<dl class="layui-nav-child">
							<dd><a href="javascript:;">列表一</a></dd>
							<dd><a href="javascript:;">列表二</a></dd>
							<dd><a href="">超链接</a></dd>
						</dl>
					</li>
					<li class="layui-nav-item"><a href="">云市场</a></li>
					<li class="layui-nav-item"><a href="">发布商品</a></li>
				</ul>
				
			</div>
		</div>
		<!-- 右边手风琴菜单结束 -->
		

		<!-- 主体开始 -->
		<div class="layui-body">
		</div>
		<!-- 主体结束 -->
		
		<!-- 底部开始-->
		<div class="layui-footer">
		</div>
		<!-- 底部开始 -->
	</div>
