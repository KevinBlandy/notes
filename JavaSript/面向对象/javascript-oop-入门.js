------------------------
JS面向对象入门			|
------------------------
	# JS是一门基于对象的语言
	# 但是JS可以模拟出面向对象的语言的特性

------------------------
JS面向对象				|
------------------------
	# 面相对象的语言,都有一个类的概念,可以通过类来创建N多个具有相同属性和方法的对象
	# 但是JS中没有类
	# 我们可以通过一些设计模式,来模拟面向对象
		1,工厂模式
			function createUser(name,age,sex){
				var user = new Object();
				user.name = name;
				user.age = age;
				user.gender = sex;
				user.say = function (){
					alert(this.name + "," + this.age + "," + this.gender);
				}
				return user;
			}
			//可以通过调用工厂方法,传递不同的数据来创建不同的对象

		2,构造函数模式
			//自定义,如果定义的是类,非函数的话.类名称首字母为大写
			function User(name,age,gender){
				this.name = name;
				this.age = age;
				this.gender = gender;
				this.say = function(){
					alert(this.name + "," + this.age + "," + this.gender);
				}
			}
			* 创建方式一,直接 new
				var user1 = new User("K",23,"男");
				var user2 = new User("v",22,"女");
			
			* 创建方式二
				* 在另一个对象的作用域中去调用类方法
				var o = new Object();
				User.call(o,"V",23,"男");
				console.log(o);

		3,动态原型模式
		4,寄生构造模式
		5,稳妥构模式
	
	# 使用 instanceof 关键字判断对象是否属于'类的实例'
		user instanceof  User
		* 任何对象都是 Object,所以任何对象 instanceof Object 都是 true
	

	