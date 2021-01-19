------------------------
layui-页面元素			|
------------------------



------------------------
layui-CSS内置公共类		|
------------------------
	# CSS命名规范
		* class命名前缀:layui,连接符：-,如:class="layui-form"
		* 命名格式一般分为两种
			1,layui-模块名-状态或类型
			2,layui-状态或类型,因为有些类并非是某个模块所特有,他们通常会是一些公共类
				* (定义按钮的原始风格):class="layui-btn layui-btn-primary"
				* (定义内联块状元素)	:class="layui-inline"

	# 常用公共类
		layui-icon   
			* 用于图标
		layui-box   
			* 用于排除一些UI框架(如Bootstrap)强制将全部元素设为box-sizing:border-box所引发的尺寸偏差
		layui-clear   
			* 用于消除浮动(一般不怎么常用,因为layui几乎没用到浮动)
		layui-inline   
			* 用于将标签设为内联块状元素
		layui-elip    
			* 用于单行文本溢出省略
		layui-unselect   
			* 用于屏蔽选中
		layui-disabled    
			* 用于设置元素不可点击状态
		layui-circle    
			* 用于设置元素为圆形
		layui-show   
			* 用于显示块状元素
		layui-hide   
			* 用于隐藏元素

		layui-main    
			* 用于设置一个宽度为 1140px的水平居中块

		layui-bg-red   
			* 用于设置元素赤色背景
		layui-bg-orange   
			* 用于设置元素橙色背景
		layui-bg-green   
			* 用于设置元素墨绿色背景(主色调)
		layui-bg-cyan   
			* 用于设置元素青色背景
		layui-bg-blue   
			* 用于设置元素蓝色背景
		layui-bg-black   
			* 用于设置元素经典黑色背景
		layui-bg-gray    
			* 用于设置元素经典灰色背景
		
	'其它的类一般都是某个元素或模块所特有,因此不作为此处所定义的公共类'


------------------------
layui-常用公共属性		|
------------------------
	# 很多时候,元素的基本交互行为,都是由模块自动开启
	# 但不同的区域可能需要触发不同的动作,这就需要你设定我们所支持的自定义属性来作为区分
		lay-submit
		lay-filter
	
	# 属性
		lay-skin=" "	
			* 定义相同元素的不同风格，如checkbox的开关风格

		lay-filter=" "	
			* 事件过滤器,你可能会在很多地方看到他
			* 他一般是用于监听特定的自定义事件,可以把它看作是一个ID选择器

		lay-submit	
			* 定义一个触发表单提交的button,不用填写值
	
	# 其它的自定义属性基本都在各自模块的文档中有所介绍。