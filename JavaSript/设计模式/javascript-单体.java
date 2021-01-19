------------------------
单体					|
------------------------
	# 说白了,对象只有一个的设计模式.很有用
	# 该模式的实现有4种方法
		1.简单单体
		2.闭包单体
		3.惰性单体
		4.分支单体


------------------------
简单单体				|
------------------------
	# 直接创建的对象,就是单体
	var TD = {};
	TD.Singleton = {
		sayName : function(){
			alert("哈哈");
		}
	};
	TD.Singleton.sayName();


------------------------
闭包单体				|
------------------------
	# 很显然,意思就是通过闭包来创建单体
	var TD = {};
	/**
	 * 把块级作用域的执行结果赋值给单体对象
	 */
	TD.Singleton = (function(){
		//私有属性
		var arr1 = '男';
		var arr2 = 18523570974;
		var arr3 = function(){
			return "我是私有函数哟";
		}
		return {
			name : 'KevinBlandy',
			age : 23,
			say : function(){
				alert(this.name);
			},
			sex : arr1,
			phone : arr2,
			test : arr3(),
		};
	})();

------------------------
惰性单体				|
------------------------
	# 其实就是单例设计模式中的,懒汉式
	# 延迟加载
	var Ext = {};
	Ext.Base = (function(){
		//私有变量,控制返回的单体对象
		var uniq ;
		
		//执行初始化,返回实例对象
		function init(){
			var arr1 = 10;
			var arr2 = true;
			var fun1 = function(){
				alert("我是方法1");
			};
			var fun2 = function(){
				alert("我是方法2");
			};
			return {
				p1 : arr1,
				p2 : arr2,
				p3 : fun1,
				p4 : fun2,
			}
		}
		
		return {
			getInstance : function(){
				if(!uniq){
					//创建单体
					uniq = init();
				}
				return uniq;
			}
		};
	})();

	var singeton = Ext.Base.getInstance();

------------------------
分支单体				|
------------------------
	# 就是判断程序的分支(浏览器差异的检测)
	# 其实就是,有N多个单例.然后呢根据逻辑的不同,返回不同的实例
