--------------------
Array-常用实例方法	|
--------------------
	concat(arr1,arr2...);
		* 连接一个或者多个数组,返回新的数组

	fill(value,start,end);
		* 使用指定值,填充数组
		* start 默认是1,end默认 length

	slice(start,end);
		* 获取指定角标到指定角标的元素,返回新的数组
	
	splice(index,many,item...);
		* 对当前数组进行删除/添加.从inde处开始删除,删除many个数据,如有有一个或者多个 item 存在,就会进行填充

	every(Function fun);
		* 判断元素是否都符合条件

	some(Function fun);
		* 是否有一个或者多个元素符合条件

	filter(Function fun);
		* 对元素进行过滤,返回新的数组

	find(Function fun);
		* 返回符合条件的第一个元素

	findIndex(Function fun);
		* 返回符合条件的第一个元素的角标

	forEach(Function fun);
		* 遍历

	indexOf(value);
		* 获取指定值的第一个角标,正序检索

	lastIndexOf();
		* 获取指定值的第一个角标,逆序检索

	join(spe);
		* 以指定的字符串把数组的所有元素组合为新的字符串

	map(Function fun);
		* 挨个处理元素,后返回,组成新的数组

	pop();
		* 删除最后一个元素,并返回

	shift();
		* 删除第一个元素,并且返回

	push();
		* 向末尾添加一个或者多个元素,返回新的数组长度

	unshift();
		* 在开头添加一个或者多个元素,返回新的数组长度

	reverse();
		* 反转数组

	sort(Function fun);
		* 对当前数组进行排序
		


--------------------
Array				|
--------------------
	# 数组
		* 不会有越界,未定义的下标为 undefined
		* 可以存储任意数据类型,没有泛型约束
		* 也可以任意的进行修改/覆盖/删除

	# 创建
		var arr = new Array();				//空集合
		var arr = new Array(5);				//初始长度为5的集
		var arr = new Array("1","2","3");	//在初始化的时候,就添加数据
		var arr = ["1","2","4"];			//隐士创建
		var arr = [1,,,,,3];				//一共有6个元素,中间四个是undefind
	
	# 属性
		constructor	
			* 返回构造函数
		length	
			* 设置或返回数组元素的个数。
		prototype	
			* 返回原型对象
	# 访问数据
		var arr = ["1","2","4"];	//隐式创建
		arr["0"];					//隐式的转换为0
		arr[0];
	
	# 删除元素
		var arr = [1,2,3]
		delete arr[0];
			* 删除了第一个元素,但是长度仍然是3,第一个元素就是undefind
		arr.length = 0;
			* 直接把数组长度变为0,也会删除数组中的所有数据
	
	# 静态方法
		isArray();
			* 判断指定的对象是否是数组类型
		
		from()
			* 把指定的数据转换为[],数据可以是类数组对象,也可以是 Ierator 子类
		
		of()
			* 相当于是构造方法,可以把一个或者多个参数,组成一个数组返回
			
		
	# 实例方法
		concat()	
			* 连接一个或更多的数组,/返回新的数组
			* 如果没有传递参数的话,相当于是浅复制了该数组,返回当前数组的所有成员组成的新数组
			* demo
				var a1 = [1,2,3];
				var a2 = ["4","5","6"];
				var a3 = [7,8,9];
				var a4 = a1.concat(a2,a3);		//a4 = 1,2,3,4,5,6,7,8,9

		copyWithin()
			* 从数组的指定位置拷贝元素到数组的另一个指定位置中。
			* 参数
				target	
					> 必需。复制到指定目标索引位置。
				start	
					> 必需。元素复制的起始位置。
				end	
					> 可选。停止复制的索引位置 (默认为 array.length)
			* demo
				var fruits = ["Banana", "Orange", "Apple", "Mango"];
				fruits.copyWithin(2, 0);	//Banana,Orange,Banana,Orange

		every()
			* 检测数值元素的每个元素是否都符合条件。返回 boolean 值
				> 全部都符合,返回 true,任意一个不符合就返回 false
			* 参数
				function(currentValue, index,arr){}
					> 必须(参数)函数,该函数应该返回 true/false
					> 参数
						currentValue
							> 当前数组中的值
						index
							> 当前值的下标
						arr
							> 当前数组
				thisValue
					> 可选参数,对象作为该执行回调时使用，传递给函数，用作 "this" 的值。如果省略了 thisValue ，"this" 的值为 "undefined"
			* Demo
				var ages = [32, 33, 16, 40];
				//判断当前数组中的数据,是否所有数据都大于 18 
				ages.every(function(currentValue, index,arr){
					if(currentValue > 18){
						return true;
					}
					return false;
				});
			
		fill()	
			* 使用一个固定值来填充数组。
			* 参数
				value	
					> 必需。填充的值。
				start
					> 可选。开始填充位置。
				end	
					>可选。停止填充位置 (默认为 array.length)
			* Demo
				var arr [1,2,3] ;
				arr.fill("8");		//arr = ["8","8","8"];

		filter()
			* 检测数值元素,其实就是过滤并返回符合条件所有元素的数组.
			* 参数
				function(currentValue, index,arr){}
					> 必须(参数)函数,该函数应该返回 true/false
					> 参数
						currentValue
							> 当前数组中的值
						index
							> 当前值的下标
						arr
							> 当前数组
				thisValue
					> 可选参数,对象作为该执行回调时使用，传递给函数，用作 "this" 的值。如果省略了 thisValue ，"this" 的值为 "undefined"
				
			* Demo
				var arr = [1,2,3,4,5];
				//仅仅留下数组中大于 2 的值
				var result = arr.filter(function(cv,index,arr){
					if(cv > 2){
						return true;
					}
					return false;
				});		
		find()	
			* 返回符合条件的第一个元素。
			* 当找到了第一个符合的元素,就返回,如果一个都没,返回 undefined
			* 如果数据为空,那么该方法不会执行
			* 参数
				function(currentValue, index,arr){}
					> 必须(参数)函数,该函数应该返回 true/false
					> 参数
						currentValue
							> 当前数组中的值
						index
							> 当前值的下标
						arr
							> 当前数组
				thisValue
					> 可选参数,对象作为该执行回调时使用，传递给函数，用作 "this" 的值。如果省略了 thisValue ，"this" 的值为 "undefined"
			* Demo
				var arr = [1,2,3];
				//获取数组中第一个大于 1的数
				var result = arr.find(function(cv,index,arr){
					if(cv > 1){
						return true;
					}
					return false;
				});

		findIndex()
			* 返回符合条件的第一个元素的索引。
			* 同上,不过该方法返回的是 元素的索引,而不是元素本身

		forEach()
			* 数组每个元素都执行一次回调函数。
			* 遍历
			* 参数
				function(currentValue, index,arr){}
					> 必须(参数)函数,该函数应该返回 true/false
					> 参数
						currentValue
							> 当前数组中的值
						index
							> 当前值的下标
						arr
							> 当前数组
				thisValue
					> 可选参数,对象作为该执行回调时使用，传递给函数，用作 "this" 的值。如果省略了 thisValue ，"this" 的值为 "undefined"
			* Demo
				var arr = [1,2,3,4,5,6,7];
				arr.forEach(function(value,index,arr){
					println(value);
				});

		indexOf()
			* 正序搜索数组中的第一个元素，并返回它所在的位置。
			* 使用全 === 去匹配的,如果没找到,返回 -1
			* 参数
				item	
					> 必须。查找的元素。
				start	
					> 从哪个角标开始正序检索,默认值为0
			* Demo
				var arr = [1,2,3,4,5,"6",6,7];
				var index = arr.indexOf(6));		//6
		
		lastIndexOf()
			* 逆序序搜索数组中的第一个元素，并返回它所在的位置
			* 参数
				item	
					> 必须。查找的元素。
				start	
					> 从哪个角标开始逆序检索,默认值为 length - 1 
			* Demo
				var arr = [1,2,3,4,5,"6",6,7];
				var index = arr.lastIndexOf(6));		//6

		join()
			* 把数组中的所有元素转换为一个字符串,
			* 参数
				separator	
					> 可选。指定要使用的分隔符。如果省略该参数，则使用逗号作为分隔符。
			* Demo
				var arr = [1,2,3];
				var result = arr.join('-');		//1-2-3
		
		map()
			* 通过指定函数处理数组的每个元素，并返回处理后的新数组。
			* 参数
				function(currentValue, index,arr){}
					> 必须(参数)函数,该函数应该返回 true/false
					> 参数
						currentValue
							> 当前数组中的值
						index
							> 当前值的下标
						arr
							> 当前数组
				thisValue
					> 可选参数,对象作为该执行回调时使用，传递给函数，用作 "this" 的值。如果省略了 thisValue ，"this" 的值为 "undefined"
			* Demo
				var arr = [1,2,3];
				var result = arr.map(function(va,index,arr){
					//把数组中的元素,每个元素都加上1
					return va + 1;
				});

		pop()
			* 删除数组的最后一个元素并返回删除的元素。

		shift()
			* 删除并返回数组的第一个元素。

		push()
			* 向数组的末尾添加一个或更多元素，并返回新的长度。

		reduce()
			* 将数组元素计算为一个值（从左到右）。
			* 参数
				function(currentValue, index,arr){}
					> 必须(参数)函数,该函数应该返回 true/false
					> 参数
						total
							> 必须,初始值,或者计算结束后的返回值。
						currentValue
							> 当前数组中的值
						index
							> 当前值的下标
						arr
							> 当前数组
				thisValue
					> 可选参数,对象作为该执行回调时使用，传递给函数，用作 "this" 的值。如果省略了 thisValue ，"this" 的值为 "undefined"
			* Demo
				

		reduceRight()
			* 将数组元素计算为一个值（从右到左）。
			* 同上,只是方向不同

		reverse()
			* 反转数组的元素顺序,不返回新的数组,直接就把当前数组反转
		
		slice()
			* 选取数组的的一部分,并返回一个新数组
			* 包含头,不包含尾
			* 参数(从哪个角标开始?到哪个角标结束?)
				start	
					- 必需,规定从何处开始选取
					- 如果是负数,那么它规定从数组尾部开始算起的位置,也就是说,-1 指最后一个元素,-2 指倒数第二个元素,以此类推
				end	
					> 可选,规定从何处结束选取,该参数是数组片断结束处的数组下标,如果没有指定该参数,那么切分的数组包含从 start 到数组结束的所有元素
					- 如果这个参数是负数,那么它规定的是从数组尾部开始算起的元素
			* Demo	
				var arr = [1,2,3];
				var result = arr.slice(0,2);		//12

		some()	
			* 检测数组元素中是否有一个或者元素符合指定条件。返回 boolean
			* 参数
				function(currentValue, index,arr){}
					> 必须(参数)函数,该函数应该返回 true/false
					> 参数
						currentValue
							> 当前数组中的值
						index
							> 当前值的下标
						arr
							> 当前数组
				thisValue
					> 可选参数,对象作为该执行回调时使用，传递给函数，用作 "this" 的值。如果省略了 thisValue ，"this" 的值为 "undefined"
			
			* Demo
				var arr = [1,2,3,4];
				arr.some(function(val){
					if(val == 4){
						return true;
					}
					return false;
				});

		sort()	
			* 对数组的元素进行排序。会修改原数组的排序
			* 参数
				sortfunction(item1,item2){}
					> 必须的参数
						item1
							> 元素1
						item2
							> 元素2
			* Demo
				var arr = [5,2,9,4]; 
				var result = arr.sort(function(v1,v2){
					return v1 > v2;
				});

		splice()	
			* 在当前数组中添加或删除元素, 不会返回新得数组, 修改是基于this数组进行得, length 属性会被修改
			* 参数
				index	
					> 必需。规定从何处添加/删除元素。该参数是开始插入和（或）删除的数组元素的下标，必须是数字。
				howmany	
					> 必需。规定应该删除多少元素。必须是数字，但可以是 "0"。如果未规定此参数，则删除从 index 开始到原数组结尾的所有元素。
				item1, ..., itemX	
					> 可选。要添加到数组的新元素
			* index 决定了从哪里开始删除元素,howmany 决定了要删除多少个元素,如果有一个或者多个 item ,就会被添加到删除的空缺处,没有就无视
			* 如果 item 个数超出删除的长度,则,原来数组的其他元素后移
			* Demo
				var fruits = ["Banana", "Orange", "Apple", "Mango"];
				fruits.splice(2,1,"Lemon","Kiwi");	//Banana,Orange,Lemon,Kiwi,Mango

		toString()
			* 把数组转换为字符串，并返回结果。
			* 使用,号分隔

		unshift()
			* 向数组的开头添加一个或更多元素，并返回新的长度。
			* Demo
				var fruits = ["Banana", "Orange", "Apple", "Mango"];
				fruits.unshift("Lemon","Pineapple");//	Lemon,Pineapple,Banana,Orange,Apple,Mango

		valueOf()
			* 返回数组对象的原始值。
			* valueOf() 是数组对象的默认方法。
			* Demo
				var fruits = ["Banana", "Orange", "Apple", "Mango"];
				var v = fruits.valueOf();	//fruits.valueOf()与 fruits返回值一样,v输出结果为：Banana,Orange,Apple,Mango
		

		entries()
		keys()
		values()
			* 这仨方法返回的都是迭代器,一看就懂

		includes()
			* 返回一个布尔值,表示某个数组是否包含给定的值
			* 的第二个参数表示搜索的起始位置,默认为0
		
--------------------
Array-注意的地方	|
--------------------
	# 修改Array的length属性,会影响到数组中的元素
		var x = [1,2,3,4,6];
		x.length = 3;		//1,2,3    

--------------------
Array-可遍历多维数组|
--------------------	
Array.prototype.each = function(fun){
		try{
			//预定义初始化变量
			this.i || (this.i = 0);
			//确定当前数组有元素,并且传递的参数是一个函数
			if(this.length > 0 && fun.constructor == Function){
				while(this.length > this.i){
					//获取每一个元素
					var temp = this[this.i];
					if(temp && temp.constructor == Array){
						//如果当前元素还是一个数组,递归调用
						temp.each(fun);
					}else{
						//如果不是,则执行传递进来的函数
						var obj = true;
						fun.call(temp,temp);
					}
					//释放变量内存
					this.i ++;
				}
				this.i = null;
			}
		}catch(e){
			//TODO
		}
		return this;
	}
	var arr = [5,6,7,8,9,[10,11,12,[55,[66]]]];
	arr.each(function(val){
		console.log(val);
	});
	

