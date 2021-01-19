--------------------
接口				|
--------------------
	# JS中没有接口这个概念,但是可以模拟高级语言的接口
	# JS中的接口,是一个很重要的概念
	# 建立接口的方式
		1,注释描述形接口
		2,属性检测形接口
		3,鸭式辩型接口
	# 接口利弊
		对于小程序来说,使用接口就会显得很不明智,因为这东西会让程序更加的复杂
	

--------------------
注释描述形接口		|
--------------------
	* 其实只是使用注解来约束,并没有任何强制性的约束
		/**		定义接口
		interface Composite {
			function add (o);
			function remove(o);
			function update(o);
		}
		*/

		/**		定义实现
		 * CompositeImpl implements Composite
		 */
		function CompositeImpl(){

		}

		/**
		 * 在原型对象上实现接口方法
		 */
		CompositeImpl.prototype.add = function(o){
			
		}
		CompositeImpl.prototype.remove = function(o){
			
		}
		CompositeImpl.prototype.update = function(o){
			
		}

		var c1 = new CompositeImpl();
		var c2 = new CompositeImpl();

		out(c1.add == c2.add);

	
	

--------------------
属性检测形接口		|
--------------------
	* 其实跟上面是一个德行,只是说多了一步,检测
	
--------------------
鸭式辩型接口		|
--------------------
	# 终极,最完美的方式
	# 完全的面向对象,代码也实现统一,而且也解耦
	
		1,创建接口规范类
		2,创建接口规范类的实现
			* 传递进去接口的名称(以字符串表示)
			* 接口里面的抽象方法(以字符串表示)
		3,创建接口的实现类
			* 实现那些方法
		4,使用校验
	# 代码
	
		/**
		 * 接口类,需要两个参数
		 * 1,接口名称
		 * 2,是一个数组,就是方法名称的集合(String)
		 */
		function Interface (name,methods){
			//判断接口的参数个数
			if(arguments.length != 2){
				throw new Error("异常,参数不正确");
			}
			this.name = name;
			this.methods = [];
			for(var x = 0;x < methods.length;x++){
				if(methods[x] && typeof(methods[x]) === "string"){
					//确定方法名称是字符串
					this.methods.push(methods[x]);
				}else{
					throw new Error(methods[x] + " - 不是String类型")
				}
			}
		}

		/**
		 * 实例化接口对象
		 */
		var CompositeInterface = new Interface("CompositeInterface",["add","remove"]);
		var FormItemInterface = new Interface("FormItemInterface",["select"]);

		/**
		 * 具体实现类
		 * CompositeImpl implements CompositeInterface,FormItemInterface
		 */
		function CompositeImpl(){
			
		}

		/**
		 * 接口方法实现
		 */
		CompositeImpl.prototype.add = function(o){console.log("add")}
		CompositeImpl.prototype.remove = function(o){console.log("remove")}
		CompositeImpl.prototype.select = function(o){console.log("select")}



		/**
		 * 检验实现方法,属于 Interface 的静态方法
		 */
		Interface.ensureImplements = function(instance,interfaces){
			if(!(instance && interfaces)){
				throw new Error("参数异常");
			}
			for(var x = 0;x < interfaces.length;x++ ){
				//接口
				var inter = interfaces[x];
				//判断参数是否是接口类类型
				if(!(inter.constructor === Interface)){
					throw new Error("接口并非 Interface 类型");
				}
				//遍历当前接口中的抽象方法名称
				for(var j = 0 ; j < inter.methods.length ; j++ ){
					var method = inter.methods[j];
					var prop = instance[method];
					if(!prop || typeof(prop) !== 'function'){
						//属性不存在,或者属性非函数,那么判定抽象方法未实现
						throw new Error("方法["+method+"]未实现");
					}
				}
			}
		}

		/**
		 * 创建对象
		 */
		var c = new CompositeImpl();

		/**
		 * 执行检验
		 */
		Interface.ensureImplements(c,[CompositeInterface,FormItemInterface]);

		