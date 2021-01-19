----------------------------
define						|
----------------------------
	# 对于Ext4.X来说,采用了新定义类的 define方法,而不是延续旧版本的extend方法
	# Ext.define(classname,properties,callback);
		classname	:类名
		properties	:配置参数
		callback	:回调函数,当该类创建OK后执行

	# 如果自定义类,未继承任何类(extend),那么会默认继承 : Ext.Base 这个类


----------------------------
properties 参数详解			|
----------------------------
	# 该参数实际上是来自于 Ext.Class

	config
		* 定义类的属性,会自动的把类中的每个属性加上get/set方法
			config:{
				name:"KevinBlandy",
				age:23,
				jobe:"java"
			}

	extend
		* 继承,以字符串的形式指定类,那么就可以把父类的属性包括构造函数都会继承过来

	alias
		* 别名

	alternateClassName
		* 备用名,其实跟 alias 差不多一个德行

	requires
		* 定义需要的类组件,是一个数组
		* 当前类被初始化之前加载

	uses
		* 定义需要的类组件,是一个数组
		* 是在当前类被初始化之后加载

	constructor
		* 定义类的构造器
			constructor:function (config) {
				var me = this;
				//初始化
				me.initConfig(config);
			}
		* 目的是为了要初始化信息,一般定义类都需要要有
		* config应该是一个对象,里面的属性应该是要设置给对象的属性,属性名保持一致
		* new 创建对象,通过形参传递初始值
			var vin = new Kevin({name:"Kevin"});
		* Ext.create 创建对象,通过配置参数来传递初始值
			var vin = Ext.create("Kevin",{name:"Kevin"});

	mixins
		* 混入,配置项.是一个可以多继承的配置
		* key自定义,value指定类.
		* 底层代码,Ext.ClassManager
			mixins:{
				foo:"指定类"
			}
		* 继承到指定类s的属性,方法.其实就是多继承

	statics
		* 定义静态的属性或者是方法,属性'不能被子类继承'
			statics:{
				id:"123"
			}	
		* '千万注意,实例对象是无法使用静态属性或方法的'

	inheritableStatics
		* 定义静态的属性或者是方法属性'可以被子类继承'

	singleton
		* 如果该值为 true,那么当前类就会被当作单例对象
		* Ext.create();得到的会是单例对象


