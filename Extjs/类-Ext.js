-----------------
Ext				 |
-----------------
	# Ext是一个全局的单例对象


-----------------
方法(静态)		 |
-----------------
	application
	get
		* 参数是一个dom节点的id值
		* 获取到一个被Ext包装过后的 dom对象
		* ext dom对象的操作
			* 绑定事件
				on('事件',function(){
			
				});
			* 获取原始的dom对象
				.dom;
	
	getCmp
		* 参数是一个组件的id值
		* 其实就是 Ext.ComponentManager.get();的缩写

	define
		* 创建一个自定义类
		* 单独开笔记讲解
	
	widget
		* 创建对象,是使用别名的形式创建.涉及到 xtype 属性

	createWidget
		* 跟上面一样的,上面是简写
		
	apply
		* 三个参数,原对象,增强对象,配置信息
		* 该方法,主要就是为对象拓展属性和方法
			 //原始对象
			var src = {name:"vin",age:23};
			//增强对象
			var config = {name:"hah",sex:"男"};
			Ext.apply(src,config);
			println(src.name + ":" + src.sex)//hah:男
		* 如果增前对象的属性,原对象已经存在.那么原对象的会被覆盖

	applyIf
		* 同上,但是这个方法仅仅只会复制原对象没有的方法

	typeOf
		* 和原生JS的  typeof 其实差不多
	
	iterate
		* 迭代器,一看就懂
			Ext.iterate(arr,function(item){
				console.log(item);
			});
	
	override
		* 覆写指定类的指定方法
		* 一看就懂
			Ext.override(user,{
				say:function(){
					alert("我是覆盖了的say方法");
				}
			});
	

	-----Ext-more.js (对Ext方法的扩充)


-----------------	
属性			 |
-----------------
	ComponentManager
		* 静态属性,只要是Ext中添加了id标识的组件,都会被它所管理
	
