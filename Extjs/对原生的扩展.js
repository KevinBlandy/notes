------------------------
对原生的扩展			|
------------------------
	# Ext对原生的JS对象进行了一些列的扩展
	# 扩展类所在目录
		src/core/src/lang
			Array.js
			Date.js
			Error.js
			Function.js
			Number.js
			Object.js
			String.js

--------------------------
Object						|
--------------------------
	# Ext.Object
	# 方法
		chain
			* 传递一个对象,会得到一个对象.这个新的对象原型就是传进来的这个对象

		each
			* 常用的迭代函数,迭代一个对象,返回false停止迭代
				var person = {
					name: 'Jacky'
					hairColor: 'black'
					loves: ['food', 'sleeping', 'wife']
				};
				Ext.Object.each(person, function(key, value, myself) {
					console.log(key + ":" + value);
					if (key === 'hairColor') {
						return false; // 停止迭代
					}
				});

		fromQueryString
			* 把URL参数解析成一个对象
			  var query = "name=kevin&age=22";
			  va obj =  Ext.Object.fromQueryString(query);//{name:'Kevin',age:'22'}
		
		toQueryString
			* 把一个对象,转换为URL参数
			* 其实就是把一个对象,里面的所有属性.都转换成GET请求中的参数

		getKey
			* 根据指定的值,返回指定的key
				var person = {
					name: 'Jacky',
					loves: 'food'
				};
				alert(Ext.Object.getKey(person, 'food')); // 弹出警告 'loves'

		getKeys
			* 获取指定对象的所有属性名

		getSize
			
		getValues
			* 获取指定对象的所有属性值

		merge
			
		toQueryObjects
			* 

	

------------------------
Number					|
------------------------
	# Ext.Number
	# 方法
		Ext.Number.constrain(number,min,max);
			* 检查 number 是否在 max 和 min 之间,如果在这个之间则返回该值
			* 如果小余则返回 min,如果大于则返回 max
		
		Ext.Number.randomInt(form,to);
			* 随机返回一个数字,该值 大于 form,小余 to
		
		Ext.Number.toFixed(num,length);
			* 对num进行取舍,会保留 length位长度的小数点
			* 会进行四舍五入
		
	
------------------------
String					|
------------------------
	# Ext.String
	# 方法
		Ext.String.capitalize(str);
			* 设置首字母大写
		
		Ext.String.ellipsis(str,num);
			* 返回str前 num 个字符,还会在最后添加三个"..."
		
		Ext.String.trim(str);
			* 去掉字符串两端的空格
		
	
------------------------
Array					|
------------------------
	# Ext.Array
	# 方法
		Ext.Array.clean(arr);
			* 过滤掉arr中的空值
			* 空值的定义见 Ext.isEmpty
		
		Ext.clone
			* 克隆
		
		contains(arr,obj);
			* 判断obj是否在arr中
		

	
		
	
------------------------
Function				|
------------------------

	
------------------------
Date					|
------------------------

	
------------------------
Error					|
------------------------




		



	