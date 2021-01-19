----------------------------
yml	配置文件				|
----------------------------
	# springboot默认使用 properties和yml类型的配置文件
		application.yml
		application.properties
	
	# yml(YAML Ain t,Markup Language)
		* 标记语言,比JSON,xml,properties 更适合做配置文件
	

----------------------------
yml	语法详解				|
----------------------------
	# 使用缩进表示层级关系
	# 缩进不允许使用Tab,只允许空格
	# 缩进空格数目不重要,但是相同层级的元素必须左侧对齐(python一样)
	# 大小写敏感

	# yml支持三种数据结构
		1,对象
			* 键值对的集合
				frends:
				  name: Litch
				  age: 23

			* 行业写法也是允许的
				frends: {name:Kevin,age23}

		2,数组
			* 一组按次序排序的值,使用:短横线 空格 值
				skill:
				 - Java
				 - Python
				 - Javascript
			
			* 支持行内写法
				skill: [Java,Python,Javascript]

		3,字面量
			* 单个的,不可再分的值
				Kevin：
					name: Kevin
					age: 23
			
			* 双引号不会转义字符串里面的特殊字符
				name: "Kevin\n"		//\n会换行

			* 单引号,会转义特殊字符
				name: 'Kevin\n'		//\n 会被转义输出
			
	# 数据结构可以相互的交错
		class Dog{
			String name;
			Integer age;
		}

		@ConfigurationProperties(prefix = "Person")
		class Person{
			String lastName;
			Integer age;
			Boolean boss;
			Date birth;
			Map<String,Object> maps;
			List<Object> lists;
			Dog dog;
		}

		
		Person:
		  lastName: Kevin
		  last-name: Kevin

		  age: 23
		  boss: false
		  birth: 2017/12/12

		  maps:
			key1: value1
			key2: value2
		  maps: {key1: value1,key2: values}

		  lists:
			- item1
			- item2
		  lists: [item1,item2]

		  dog:
			name: Litch
			age: 23

	# 同样可以使用 @Value 注解
		@Value("${Person.lastName}")

