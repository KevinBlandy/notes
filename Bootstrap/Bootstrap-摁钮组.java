————————————————————————
2,JavaScript-恩纽组		|
————————————————————————
	.btn-group
		* 摁扭组,可以实现把一组按钮，放在同一行
	.btn-group-vertical
		* 垂直排列的按钮组	,把摁扭都放在同一列
	.btn-toolbar
		* 摁扭工具栏目,把多个摁扭阻放在其中
	.btn-group-lg
	.btn-group-sm
	.btn-group-xs
		* 摁扭组大小
	* 几个点
		1,准备的一个div,添加class属性:btn-group
		2,在这个div中添加n个摁扭
	//横向铺开，按钮样式是最大，此处需要通过div来设置按钮大小
	<div class="btn-group btn-group-lg">
		<button type="button" class="btn btn-success">JAVA</button>
		<button type="button" class="btn btn-success">PHP</button>
		<button type="button" class="btn btn-success">C++</button>
		<button type="button" class="btn btn-success">Python</button>
	</div>
————————————————————————
3,JavaScript-恩纽组下拉菜单|
————————————————————————
	* 这个如果领会了前面的两点,那就很好办
	* 几个点
		1,创建一个按钮组div,添加class="btn-group"
		2,在这个里面创建一个div,添加class="btn-group"
		3,在内部的div中添加button,内部div不用添加:dropdown属性
		4,其他属性正常的添加就是了
			<div class="btn-group">
				<!-- java -->
				<div class="btn-group">
					<button type="button" class="btn btn-success" data-toggle="dropdown">
						JAVA <span class="caret"></span>
					</button>
					<ul class="dropdown-menu">
						<li>JDBC</li>
						<li>List</li>
						<li>Set</li>
						<li>Map</li>
					</ul>
				</div>
				<!-- 框架 -->
				<div class="btn-group">
					<button type="button" class="btn btn-success" data-toggle="dropdown">
							框架 <span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<li>Spring</li>
							<li>MyBatis</li>
							<li>Hibernate</li>
							<li>Strtus2</li>
						</ul>
					</div>
				</div>