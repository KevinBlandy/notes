----------------------------
js-原型						|
----------------------------
	# prototype
		* 每一个 function(类) 都有一个 prototype 属性,该属性指向一个对象
		* 该对象的所有属性和方法,都可以被该 function 的所有对象 共享
	
		function User(name,age,gender){
			this.name = name;
			this.age = age;
			this.gender = gender;
		}
		//获取指定类的原型对象
		var prototype = User.prototype;
		//原型对象的构造器,就是类的构造器
		console.log(prototype.constructor);
		//instanceof 运算类,为 false
		console.log(prototype instanceof User);
		//可以先创建对象
		var user1 = new User("K",23,"男");
		var user2 = new User("K",23,"男");
		//在指定类的原型对象上添加方法
		prototype.say = function(){
			alert(this.name + "," + this.age + "," + this.gender);
		}
		//调用实例的原型对象方法,调用之前,该方法必须赋值给原型对象
		user1.say();
		//两个对象中,原型对象的方法.都是同一个方法,== 运算结果为 true
		console.log(user1.say == user2.say);
	
	# 通过实例对象来获取原型对象
		* 使用Object的静态方法:	getPrototypeOf(); 
		var user = new User();
		//获取 User的原型对象
		var prototype = Object.getPrototypeOf(user);
	
	# 原型对象的动态性
		* 简单原型
			* '无所谓先后创建对象,只要保证操作属性的时候,该属性在之前已经赋值给原型对象'
			function User(){
			}
			//1,先创建对象
			var user = new User();		
			//2,原型对象赋值方法
			User.prototype.say = function(){
				alert("我是say");
			}
			//3,调用原型方法
			user.say();		
		
		* 覆写原型
			* '必须要保证对象的创建,是在原型被覆写后'
			function User(){
			}
			//1,原型对象覆写
			User.prototype = {
				say : function(){
					alert("我是say");
				}
			}
			//2,创建对象
			var user = new User();
			//3,调用原型覆写对象的方法
			user.say();


	# 覆盖原型对象
		* 原型对象可以被覆盖掉
		* User.prototype = {name:'123456'}	
		* 这种方法会改变原型对象的构造函数(function Object() { [native code] })
			//手动的方式来处理原型构造函数变化的问题
			//但是这个 constructor 会被 in 操作所枚举出来
			User.prototype = {
				constructor:User			
			}
		* Object的静态方法:Object.defineProperty(); 
		* 可以为原型对象重新定义构造器,并且可以通过配置来使构造器不会被枚举
			Object.defineProperty(User.prototype,'constructor',{
				enumerable:false,		//不可被枚举
				value:User
			}); 

	# 当原型对象属性名与实例对象属性名称冲突
		* 实例属性,会覆盖原型对象中的同名属性(覆写)
		* 实在是要获取原型对象的属性值,可以执行 delete 语句删除调用对象的属性,再调用属性
		* 不可以直接通过 对象.属性 来设置/修改从原型对象继承的属性
			function User(){}
			User.prototype.x = 5;
			var user1 = new User();
			var user2 = new User();
			user1.x = 9;		//重新设置user1.x ,并不会修改原型中的x,会在当前对象新增/覆盖属性
			out(user1.x);		//9
			out(user2.x)		5

	# 判断实例的属性值是否是原型对象的属性值
		* 使用Object的实例方法:	hasOwnProperty();仅仅会在当前实例中检索属性,并不会检索原型对象中的属性
		* in 操作符,不仅仅会检查实例对象,还会去检查原型对是否具备指定属性值
		* 以上两个操作组合起来,可以判断属性是否是原型属性

	# 判断是否实例,是否存在于原型对象中 
		* isPrototypeOf(var obj);
		* 如果obj不是对象,或者 this 对象不出现在 obj 的原型链中,则该方法返回false
		* demo
			function User(name,age,gender){
				this.name = name;
				this.age = age;
				this.gender = gender;
			}
			//获取原型对象
			var prototype = User.prototype;
			//创建对象
			var user = new User("Kevin",23,"男");
			//判断对象中,是否继承了原型对象
			console.log(prototype.isPrototypeOf(user));				//true
			

			//新建一个额外的原型对象
			var outherPrototype = {
				test:'213'
			}
			//覆盖原先的原型对象
			User.prototype = outherPrototype;
			var user1 = new User("Litch",23,"男");
			console.log(outherPrototype.isPrototypeOf(user1));		//true
	
	# 原型继承
		* 当原型对象,是另一个类型的实例
			//父类
			function Sup(name){
				this.name = name;
			}

			//子类
			function Sub(age){
				this.age = age;
			}

			//父类原型覆写
			Sup.prototype = {
				constructor:Sup,
				sayName : function(){
					console.log(this.name);
				}
			}
			//子类原型对象,是父类的实例
			Sub.prototype = new Sup("KevinBlandy");
			//创建子类对象
			var sub = new Sub(23);
			//调用父类方法
			sub.sayName();		//KevinBlandy

		* 子类的原型对象,等于父类的实例,那么子类就有了父类的所有方法和属性,包括父类原型的方法和属性

-------------------------
原型对象常用开发模式	 |
-------------------------
	# 原型对象,因为被所有的实例共享,所以也存在一些问题
		* 所有对象,都可以修改属性,产生了一些'并发问题'
	
	# 我们一般组合使用构造函数和原型模式
		* 实际开发中用使用最广泛
		* 其实就是'尽量的把只读的属性/方法添加到原型'
		//构造函数
		function User(name,age,frends,job){
			this.name = name;
			this.age = age;
			this.frends = frends;
			this.job = job;
		}
		//原型覆写
		User.prototype = {
			constructor : User,
			sayName : function(){
				console.log("我是:"+this.name);
			}
		};
		var user = new User("Kevin",23,["Litch"],"程序员");
		user.sayName();
	
	# 动态原型模式
		*  把所有代码都封装到一起
		function User(name,age,frends,job){
			this.name = name;
			this.age = age;
			this.frends = frends;
			this.job = job;
			if(typeof this.sayName != 'function'){
				//这里的代码,仅仅会在第一次创建对象的时候执行
				User.prototye.sayName = function(){
					console.log("我是:"+this.name);
				}
			}
		}
		
	# 稳妥构造函数模式
		* durable object(稳妥对象),非常安全的环境中
		* 通俗理解
			* 没有公共属性
			* 不能使用 this 对象
		* 实在在安全的环境中使用,如果程序对安全要求比较高,可以考虑使用这种模式
		
		function User(name,age,job){
			var user = new User();
			user.name = name;
			user.age = age;
			user.job = job;
			//定义私有的变量
			var gender = '男';
			//添加对外的方法
			user.mySex = function(){
				alert(gender);		//不能用this
			};
			return job;
		}
	
		
	

-------------------------
总结					 |
-------------------------
	1,原型对象是类的一个属性,两种获取方式
		* 非实例获取,直接类名.prototype
		* 实例获取,通过Object的静态方法:getPrototypeOf(); 来获取
	2,该属性(原型对象)的(构造函数) constructor 就是类的构造函数,也是该类任何实例的构造函数(===)
	3,该属性(原型对象) instanceof 类,结果 是 false
	4,该对象(原型对象) typeof 运算,结果是 Object
	5,原型对象中定义的方法,属性,在所有的对象中,都是一样的(===)
	6,原型对象可以被覆盖,User.prototype = {name:'123456'}	
		* 但是会导致原型对象的构造函数会变化为: function Object() { [native code] }
		* 可以手动的重新设置构造函数
			User.prototype = {
				constructor : User		//可枚举
			}
		* 使用 Object.defineProperty();来重新配置构造函数,可以使其不能被枚举
			Object.defineProperty(User.prototype,'constructor',{
				enumerable:false,
				value:User
			}); 
			
	7,实例属性,会覆盖原型对象中的同名属性(覆写)
		* 实例对象,不能通过  对象.属性 修改从原型对象继承的值
	8,使用 in 和 hasOwnProperty(); 来判断属性是否是属于原型对象
	9,原型对象的动态性
		* 普通原型-实例的创建无所谓前后
		* 覆写原型-实例的创建必须在原型对象被覆写后
		
	10,原型继承
		* 子类的原型对象,等于父类的实例
		* 那么子类实例就有了父类的所有方法和属性,包括父类原型的方法和属性



