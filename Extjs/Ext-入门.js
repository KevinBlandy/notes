----------------------------
1,Ext-入门					|
----------------------------
	# 基于4.1版本
	# 简介
		* 基于JS
		* 有点像Java Swing的MVC架构
		* 支持组件化,模块化设计
		* 提供本地数据源支持
		* 完善与服务端的交互机制

	# 目录结构
	# Web Site 和 Web AppliCation 的区别
		Web Site:以展示为主,例如:个人博客,门户网站
		Web AppliCation:应用,以业务逻辑为主CMS系统

	# 官网
	
	# 学习网站
		http://www.jeeboot.com/
		http://www.cnblogs.com/DonaHero/p/5983151.html
		http://blog.csdn.net/tianxiaode/article/details/51453630
		http://www.cnblogs.com/mlzs/

----------------------------
2,Ext-基本概念				|
----------------------------
	组件(Compoment)
		# 直接以图形化形式显示界面的类
		# 其中还可以分为容器与元件组件
			* 容器组件:能够包含其他容器组件或元组件的类,是进行单元化组件开发的基础
			* 元组件  :能够图形化表示一个片面功能的组件(日历,树,列表)
	类	
		# 提供功能的非图形化的类,它们为图形类提供了有力的支持
		# 按功能分为
			数据支持类(data)
			拖放支持类(dd)
			布局支持类(layout)
			本地状态存储支持类(state-操作Cookie)
			实用工具类(util)
		# 密封类:不能被扩展的类
		# 原型类:扩展了Javascript标准类库中的类
		# 创建类
			Ext.define("类名",{
				
			},function(){
				console.log("类创建完成");
			});
	方法
		# 作为类功能的提现,能够产生改变对象本身产生变化的直接因素
		# 方法按访问形式可分为,private 和 public(其实完全靠用户自觉,没有强制要求)

	事件
		# 由类定义,并且可以在类对象自身状态发生改变的时候,触发
		# 只有被订阅的事件才会有效
		# 如果不需要此事件,应该进行退订,增强程序的执行效率

	配置选项
		# 用于初始化一个Extjs类对象的手段
		# 注意,配置选项不一定就是属性,总算是属性,也有可能出现属性返回的类型与你当初指定的配置选项类型不一致的情况
	

	属性
		# 能够在程序运行期间,能够被访问,用以了解当前类型对象的状态
		# 实际开发中,EXT的属性设置,比较差劲,需要通过了解源代码.才能了解各种使用属性的用处
	

	命名空间
		# 把编写好的Extjs类进行有效组织的手段
		# 这是EXT被称之为优秀AJAX框架的特征之一
		# 其实就是这么回事
			Ext.namespace("com.kevin");
			package com.kevin;
	
----------------------------
3,基本导入					|
----------------------------
		
	1,导入整个文件夹
		ext-6.2.0\build\classic\theme-crisp
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/ext/theme-crisp/resources/theme-crisp-all.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/ext/theme-crisp/resources/theme-crisp-all-debug.css" />
	2,导入JS文件
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/ext/ext-bootstrap.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/ext/locale-zh_CN.js"></script>





----------------------------
4,API文档释疑				|
----------------------------
	xtype
		* 组件别名
	Hierarchy
		* 层次结构
	Inherited mixins
		* 混入的类
	Requires
		* 该组件需要使用的类
	Configs
		* 组件的配置信息
	Properties
		* 主键的属性
	methods
		* 组件的方法
	events
		* 组件的事件

----------------------------
5,知识点					|
----------------------------
	# 创建组件对象两种方式
		var win = Ext.create('Ext.window.Window',{
			title:""
		});

		var win = new Ext.window.Window({
			title:""
		});
	
	# 组件中添加组件
		items:[
			{
				xtype:'button',
				text:'被添加的摁钮组件'
			},
			 new Ext.button.Button({
                text:"我也是摁钮",
                handler:function (e) {
                    alert("haha");
                }
            },
			 Ext.create('Ext.button.Button',{
                text:"摁钮三辣",
                handler:function (e) {
                    alert("哈哈");
                }
            }),
		]

	# 获取父级组件

		* up();方法几乎是所有的组件都有的方法
		* 参数可以是一个组件的 xtype 或者是 选择器
			tbar:[
				text:"工具栏-摁钮1",
				handler:function(e){
					//获取到父级的window组件对象的标题
					var title = e.up('window').title;
				}
			]
		* 其实也可以直接 Ext.getCmp("主键ID"); 获取到指定的组件
		* 还有一种方式,就是 ownerCt,该属性几乎是所有组件都具备的,返回的就是直接父级组件对象
			tbar:[
				text:"工具栏-摁钮1",
				handler:function(e){
					//获取直属父级组件-tbar
					var tbar = e.ownerCt;
				}
			]
		

		

	
----------------------------
4,通过Sencha构建Ext项目		|
----------------------------
	1,安装Sencha cmd工具,需要JDK
	2,下载ext.js,解压到目录
	3,执行命令
		sencha -sdk d:\ext-6.2.0 generate app myapp e:\myapp