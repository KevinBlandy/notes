
----------------------------
对象-Object					|
----------------------------
	# 对象,是一种引用数据类型,跟JAVA的Object有点类似
	# 所有的对象都具备一些公共的属性与方法
	# 属性
		constructor
			* 返回当前对象的构造函数
			* 就是创建了当前这个对象的函数
		
	# 对于属性的操作
		* 实例方法
			hasOwnProperty();
			isPrototypeOf();
			propertyIsEnumerable();
			属性 in 对象
				* 判断指定属性是否存在于对象
				* '包含原型继承过来的属性'

		* Object静态方法
			create();
				* 创建对象
			keys(obj);
				* 获取指定对象的自有属性,包括不可枚举的属性,返回数组
			getOwnPropertyNames(obj);
				* 获取指定对象的自有属性,不包括不可枚举属性,返回数组
			defineProperty(); 
				* 重新设置指定对象的指定属性的属性
			defineProperties();
				* 同上,一次性设置多个属性的属性
			getOwnPropertyDescriptor(obj,prop);
				* 获取指定对象的属性的属性
			
----------------------------
对象-创建					|
----------------------------
	var obj = new Object();
		* 如果对象没有构造函数,是可以省略小括号的
		* new Object
	var obj = {};
	
	var obj = Object.create(Object.prototype);

----------------------------
对象-属性					|
----------------------------
	# 定义属性
		obj.name = "KevinBlandy";
		obj["name"] = "KevinBlandy";
			* 动态的添加属性

	
	# 访问属性
		var name = obj.name;
		var name = obj["name"];
	
	# 删除属性
		* 删除成功会返回boolean
		* 仅仅可以删除自己的属性,不能删除从原型对象上继承的属性
		delete obj.age;
		delete obj["age"];
	
	# 遍历属性
		* 仅仅只能遍历可枚举的属性
		for(var x in obj) {
			//x				:表示对象的属性名,是字符串
			//对象[属性]	:获取指定的属性值
			console.log(x);
			console.log(obj[x]);
		}
	
----------------------------
对象-方法					|
----------------------------
	# 定义方法
		obj.say = function(){

		}
		* 当然,肯定也可以定义参数
	
	# 调用方法
		obj.say();
	
	# 删除方法
		* 删除成功会返回boolean
		delete obj.say;
		delete obj["say"];


----------------------------
对象-私有属性				|
----------------------------
	# 定义对象的私有成员,仅仅只需要在'函数内部声明变量'(局部变量),就好
	function User(){
		var sex = "男人";
		var doSome = function(){
			console.log("私有方法");
		};
		this.getSex = function(){		//定义访问私有属性的方法
			return sex;
		};
	};
	var user = new User();
	var sex = user.getSex();
	
----------------------------
对象-getter/setter			|
----------------------------
	# 对象的属性分为两种
		1,数据属性,就是以上定义的属性,是最普通的属性
		2,存取器属性
	# getter/setter 定义(存取器属性)
	# 定义形式
		get 属性名称(){
			//code 在获取值的时候调用,记住千万不能使用 this.当前属性名称
		},
		set 属性名称(value){
			//code 在设置属性的时候调用,记住千万不能使用 this.当前属性名称 ,
		}

	# 当仅仅定义了get的时候,该属性就只能读取(实例.属性),如果进行赋值操作,则没有效果(实例.属性 = 值)
		* 在严格模式中还会报错
	
	# 存取器属性是可以被继承的

	# 定义有存取器属性的对象
		var user = {
				get name(){
					out("获取name");		//在 实例.name 的时候执行
					return name;
				},
				set name(value){
					out("设置name:"+value)	//在 实例.name = xx 的时候执行
					name = value;
				}
		}
		user.name = "KevinBlandy";		//设置新的属性
		println(user.name);


	# 在已有对象上添加/修改存取器属性
		* 注意:get后面后冒号
		var user = {x:5};
		Object.defineProperty(user,"x",{
			get:function(){
				return (this.y) ? "默认值": this.y;
			},
			set:function(value){
				this.y = value;
			}
		}); 

----------------------------
对象-属性的属性				|
----------------------------
	# 获取属性的属性
		Object.getOwnPropertyDescriptor(obj,prop);		
			* 获取指定对象,指定属性的...属性
			* 就是参数1这个对象的参数2的属性的一些属性(只读...等等)
			* 不能检测从原型继承的属性
			* Object.getOwnPropertyDescriptor(user,"name")

	# 属性的属性
		configurable	属性能否被删除或者重新定义
		enumerable		遍历对象的时候属性是否可见	
		value			属性值，'当设置后不能设置get和set'	
		writable		属性能否改变,设置该值后,执行 :实例.对象 不会有反映
		get				当获取属性的时候触发,记住千万不能使用 this.当前属性名称	
		set				当设置属性的时候触发,记住千万不能使用 this.当前属性名称

		

	# 设置对象的属性的属性
		1,创建的时候设置
			Object.create();
				* 直接创建出一个对象
				* 第一个参数就是设置给该对象的原型对象
				* 第二个参数,用于对对象属性进行更详细的描述
				var user = Object.create(Object.prototype,{
					x:{
						get : function(){
							return 15;
						}
					}
				});
		
		2,对已经创建的对象的属性进行设置
			Object.defineProperty();		//单个属性配置
				* 给指定的对象设置属性
				* 三个参数
					1,要设置属性的对象
					2,设置什么属性(以字符串表示属性的名称)
					3,Options配置项({})
					Object.defineProperty(user,"x",{
						get:function(){
							return (this.y) ? "默认值": this.y;
						},
						set:function(value){
							this.y = value;
						}
					}); 
		
			Object.defineProperties();		//批量的属性配置
				* 跟上面一样,只有两个参数
				* 对对象进行批量的属性添加,覆盖
				* 第一个参数还是对象,第二个参数,里面是一堆对象,key就是属性名,value又是一个对象,就是属性的配置
					Object.defineProperties(user,{
						x:{
							get:function(){
								return (this.y) ? "默认值": this.y;
							},
							set:function(value){
								this.y = value;
							}
						},
						y:{
							value:2
						}
					});
	

----------------------------
对象-return	对对象的影响	|
----------------------------
	# return 后面的参数无法执行
	# 对创建的对象没有影响
	# Demo
		function User(name,age){
			this.name = name;
			this.age = age;
			return 123;
			this.sex = "男";		//该属性会
		}
		var user = new User("Kevin",23);





