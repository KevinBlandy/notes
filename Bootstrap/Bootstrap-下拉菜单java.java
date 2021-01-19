————————————————————————
1,JavaScript-下拉菜单	|
————————————————————————
	.dropdown
		* 把下拉菜单触发器和下拉菜单包含在其中(下拉菜单父元素)
	.data-toggle
		* 下拉菜单触发器,取值为 "dropdown"
	.dropup
		* 向上弹出的下拉菜单(下拉菜单父元素)
	.divider
		* 为下拉菜单添加分割线，用于将多个链接分组
		<li class="divider"/>
	.disabled
		* 禁用的菜单项
	.dropdown-header
		* 添加标题菜单
		 <li role="presentation" class="dropdown-header">下拉菜单标题</li>
	.btn-large
	.btn-sm 
	.btn-xs
		* 可以下拉菜单摁扭的大小,该样式应该加在button上
	* 三个点
		1,组织一个class="dropdown"的div
		2,在div中加入一个button，添加属性:data-toggle="dropdown"
		3,在ul中添加属性:class="dropdown-menu"
	
	案例	
		<div class="dropdown">					//button,和ul的父容器												
			<button type="button" class="btn-lg btn-success" data-toggle="dropdown">		
			JAVA								//button
			<span class="caret"></span>			//一个倒三角							
			</button>
			<ul class="dropdown-menu">			//ul							
				<li>
					<a href="#">JDBC</a>		//常规选项										
				</li>
				<li class="divider"/>			//选项分割线							
				<li>
					<a href="#">WEB</a>
				</li>
				<li class="divider"/>
				<li>
					<a href="#">JSP</a>
				</li>
				<li class="divider"/>
				<li　class="disabled">			//禁用选项													
					<a href="#">Tomcat</a>
				</li>
			</ul>
		</div>