------------------------
链式调用				|
------------------------
	# 不用解释太多,很简单的东西,每个函数返回 this 
		function Dog (){
			this.say = function(){
				console.log("dog is run");
				return this;
			};
			this.eat = function(){
				console.log("dog is ear");
				return this;
			};
		}
		var dog = new Dog();
		dog.say().eat();
	

------------------------
模拟Jquery				|
------------------------
	