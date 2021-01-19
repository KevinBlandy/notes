-------------------------------
JS-类							|
-------------------------------
	# 类的本质就是一个函数
	# 类名称(方法名称大写)
		function Persion (){
		
		}

		function Persion(name,age){
			this.name = name;
			this.age = age;
		}
	
	# 设置类的静态属性
		Persion.type = "test";
	
	# 创建对象
		var p = new Persion();
		var p = new Persion("KevinBlandy",23);

