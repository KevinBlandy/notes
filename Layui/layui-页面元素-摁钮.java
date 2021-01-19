-----------------------
摁钮					|
-----------------------
	# 向任意HTML元素设定class="layui-btn",建立一个基础按钮
	# 通过追加格式为 layui-btn-{type} 的class来定义其它按钮风格
	# 内置的按钮class可以进行任意组合,从而形成更多种按钮风格
	# 简单的摁钮
		<button class="layui-btn">一个标准的按钮</button>
		<a class="layui-btn" href="http://www.layui.com">一个可跳转的按钮</a>
	

-----------------------
主题摁钮				|
-----------------------
	默认	class="layui-btn"
	原始	class="layui-btn layui-btn-primary"
	百搭	class="layui-btn layui-btn-normal"
	暖色	class="layui-btn layui-btn-warm"
	警告	class="layui-btn layui-btn-danger"
	禁用	class="layui-btn layui-btn-disabled"
	
-----------------------
尺寸摁钮				|
-----------------------
	默认	class="layui-btn"
	大型	class="layui-btn layui-btn-big"
	小型	class="layui-btn layui-btn-small"
	迷你	class="layui-btn layui-btn-mini"


-----------------------
圆角摁钮				|
-----------------------
	默认	class="layui-btn layui-btn-radius"
	原始	class="layui-btn layui-btn-radius layui-btn-primary"
	百搭	class="layui-btn layui-btn-radius layui-btn-normal"
	暖色	class="layui-btn layui-btn-radius layui-btn-warm"
	警告	class="layui-btn layui-btn-radius layui-btn-danger"
	禁用	class="layui-btn layui-btn-radius layui-btn-disabled"

-----------------------
图标摁钮				|
-----------------------
	# 结构
		<button class="layui-btn">
			<i class="layui-icon">&#xe608;</i> 添加					//带字
		</button>

		<button class="layui-btn layui-btn-small layui-btn-primary">//不带字
			<i class="layui-icon">&#x1002;</i>
		</button>

		* 就是button中添加了 <i> 图标
	
-----------------------
组合摁钮				|
-----------------------
	# 将多个按钮放入一个class="layui-btn-group"元素中,即可形成按钮组
	# 按钮本身仍然可以随意搭配
	# Demo
		<div class="layui-btn-group">
			<button class="layui-btn">增加</button>
			<button class="layui-btn">编辑</button>
			<button class="layui-btn">删除</button>
		</div>

		<div class="layui-btn-group">
			<button class="layui-btn layui-btn-small">
				<i class="layui-icon">&#xe654;</i>
			</button>
			<button class="layui-btn layui-btn-small">
				<i class="layui-icon">&#xe642;</i>
			</button>
			<button class="layui-btn layui-btn-small">
				<i class="layui-icon">&#xe640;</i>
			</button>
			<button class="layui-btn layui-btn-small">
				<i class="layui-icon">&#xe602;</i>
			</button>
		</div>

		<div class="layui-btn-group">
			<button class="layui-btn layui-btn-primary layui-btn-small">
				<i class="layui-icon">&#xe654;</i>
			</button>
			<button class="layui-btn layui-btn-primary layui-btn-small">
				<i class="layui-icon">&#xe642;</i>
			</button>
			<button class="layui-btn layui-btn-primary layui-btn-small">
				<i class="layui-icon">&#xe640;</i>
			</button>
		</div>

-----------------------
组合摁钮				|
-----------------------
	大型百搭	class="layui-btn layui-btn-big layui-btn-normal"
	正常暖色	class="layui-btn layui-btn-warm"
	小型警告	class="layui-btn layui-btn-small layui-btn-danger"
	迷你禁用	class="layui-btn layui-btn-mini layui-btn-disabled"

	大型百搭圆角	class="layui-btn layui-btn-big layui-btn-radius layui-btn-normal"
	小型警告圆角	class="layui-btn layui-btn-small layui-btn-radius layui-btn-danger"
	迷你禁用圆角	class="layui-btn layui-btn-mini layui-btn-radius layui-btn-disabled"