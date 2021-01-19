-------------------------------
JavaScript-继承					|
-------------------------------
	# 很显然,这是高级语言才有的特性
	# JS实现继承的方法
		* 原型继承(看原型笔记)
		* 简单类继承
		* 混合继承

	

	
	# 原型继承
		* 看原型笔记
		* 可以继承父类的方法属性,以及父类原型的方法属性
		* 这种继承方式,不好在子类实例构造的时候初始化从父类继承的属性


	# 类继承,仅仅继承模版,不继承原型
		* 这种方式,不能继承父类原型中的属性和方法
		* 可以在子类实例构造的时初始化从父类继承的参数

		function Persion(name ,age){
			this.name = name;
			this.age = age;
		}
		function Boy(name,age,sex){
			/*
			 * 执行父类函数,绑定在当前对象
			 * 父类,其实就是个方法,里面的关键点,就是父类里面使用了this
			 * 使用 call 改变其this的指针,执行它,就完成了子类的赋值
			 */
			Persion.call(this,name,age);
			this.sex = sex;
		}


		var boy1 = new Boy("余文朋",23,"男");
		var boy2 = new Boy("肖文浩",24,"男");
	
	# 混合继承
		* 原型继承 + 类继承 = 混合继承
		* 可以继承父类的方法属性,以及父类原型的方法属性
		* 可以在子类实例构造的时初始化从父类继承的参数

		* 缺点:会继承2次父类的模版,继承一次父类原型
			类继承处会继承一次父类模板
			子类原型覆写处,会继承一次父类模板
			子类原型覆写处,会继承一次父类原型

			//父类
			function Persion(name ,age){
				this.name = name;
				this.age = age;
			}
			//父类原型添加函数
			Persion.prototype.sayName = function(){
				alert(this.name);
			}
			//子类
			function Boy(name,age,sex){
				//使用类继承 - 作用就是继承父类的(构造)模版
				Persion.call(this,name,age);
				this.sex = sex;
			}
			/*
				使用原型继承 - 作用就是继承父类的原型对象
			*/
			Boy.prototype = new Persion();
			//Boy.prototype = Persion.prototype;

			var boy = new Boy("余文朋",23,"男");
			boy.sayName();
			console.log(boy);


-------------------------------
JavaScript-模拟Ext底层的继承	|
-------------------------------
	# 解决混合继承的两次继承问题
	//定义父类
	function Persion(name,age){
		this.name = name;
		this.age = age;
	}

	//定义父类原型方法 sayHello
	Persion.prototype = {
		sayHello : function(){
			console.log("Hello");
		}
	}

	//定义子类
	function Boy(name,age,sex){
		//获取类中保存的原型对象.构造函数,执行模版继承
		Boy.superPrototype.constructor.call(this,name,age);
		this.sex = sex;
	}

	//执行原型继承,不继承模版
	extend(Boy,Persion);

	//定义子类原型方法 sayHello
	Boy.prototype.sayHello = function(){
		console.log("Hi");
	}

	//创建子类对象
	var boy = new Boy("余文朋",23,"男");

	out(boy.name);					//父类属性

	out(boy.sex);					//子类属性

	Boy.superPrototype.sayHello();	//父类原型方法 sayHello

	boy.sayHello();					//子类原型方法 sayHello

	/**
	 * 原型继承,不继承模版
	 * @Param	sub 子类
	 * @Param	sup	父类
	 */
	function extend(sub,sup){
		//1,定义空函数用于过度  
		var F = new Function();
		//2,把父类的原型对象,给空函数的原型对象
		F.prototype = sup.prototype;
		//3,子类的原型,等于空函数的实例
		sub.prototype = new F();
		//4,修改子类的原型的构造器
		sub.prototype.constructor = sub;
		//5,保存父类的原型对象到子类静态属性,用于解耦,获取父类的原型对象
		sub.superPrototype = sup.prototype;
		//6,保证父类原型对象的构造器就是父类本身
		if(sup.prototype.constructor != sup){
			sup.prototype.constructor = sup;
		}
	}

	function out(obj) {
		console.log(obj);
	}