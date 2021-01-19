------------------------
js工具库				|
------------------------
	* MDUI 内置了一个轻量的 JavaScript 工具库,它拥有和 jQuery 类似的 api 和 jQuery 风格的链式调用,但体积仅为 jQuery 的八分之一
	* 你可以通过 mdui.JQ 来调用该库,但最好把 mdui.JQ 存储到一个简短的变量中以方便调用,为了避免和其他库冲突,推荐使用 $$
		var $$ = mdui.JQ;
		var md = mdui.JQ;


------------------------
js工具库-核心			|
------------------------
	md()
		# 该方法有多种用法：
		* 可以传入一个 CSS 选择器作为参数,返回包含匹配元素的 JQ 对象,该方法是通过 querySelectorAll 实现的
			md('.mdui-table')

		* 可以传入 DOM 元素,DOM 元素数组,NodeList,JQ 对象,返回包含指定元素的 JQ 对象
			md(document)

		* 可以传入 HTML 字符串,返回包含根据 HTML 创建的 DOM 的 JQ 对象
			md('<div id="wrapper"><span id="inner"></span></div>')

		* 可以传入一个函数，在 DOM 加载完毕后会调用该函数
			md(function () { 
				console.log('DOM Loaded') 
			})
	

------------------------
js工具库-插件编写		|
------------------------
	md.extend()
		# 如果只传入一个对象,该对象中的属性将合并到 JQ 对象中,相当于在 JQ 的命名空间下添加新的功能
			md({
				func:function(){
					console.log('哈哈')
				}
			})
			md.func();
		
		# 如果传入了两个或更多个对象,所有对象的属性都添加到第一个对象,并返回合并后的对象
			* 其实就是把多个对象的属性都集合成一个对象,然后返回
			var object = _$.extend(
				{ key1: '1' },
				{ key2: '2' },
				{ key3: '3' }
			);
			object.key1;
			object.key2;
			object.key3;
		
	md.fn.extend()
		# 在 JQ 的原型链上扩展方法。
			md.fn.extend({
				customFunc: function () {}
			})

			// 然后就可以这样使用扩展的方法了
			md(document).customFunc()


------------------------
js工具库-URL			|
------------------------
	md.param();
		# 将数组或对象序列化。
			* md.param({width:1680, height:1050})
			// width=1680&height=1050

			* md.param({foo: {one: 1,two: 2 } })
			// foo[one]=1&foo[two]=2

			* md.param({ids:[1,2,3] })
			// ids[]=1&ids[]=2&ids[]=3
		
------------------------
js工具库-数组对象操作	|
------------------------
	md.each()
		# 遍历数组/对象
			md.each(['a', 'b', 'c'], function (i, value) {
				console.log(i + ':' + value);
			})
			// 结果：
			// 0:a
			// 1:b
			// 2:c
			md.each({'name': 'mdui', 'lang': 'zh'}, function (i, value) {
				console.log(i + ':' + value);
			})

			// 结果
			// name:mdui
			// lang:zh

	md.merge()	
		# 合并两个数组,合并的结果会替换第一个数组的内容,并返回合并的结果
			var first = ['a', 'b', 'c'];
			var second = ['c', 'd', 'e'];

			var result = md.merge(first, second);

			console.log(first);		// ['a', 'b', 'c', 'c', 'd', 'e']
			console.log(result);	// ['a', 'b', 'c', 'c', 'd', 'e']
	
	md.unique()	
		# 删除数组中的重复元素。
			* 可以是 DOM 元素数组,字符串数组,数字数组
			* 返回去重后的数组
			var result = $$.unique([1,2,12,3,2,1,2,1,1,1,1]);
			console.log(result); // [1, 2, 12, 3]
	
	md.map()	
		# 遍历数组或对象,通过函数返回一个新的数组或对象
			* null 和 undefined 将被过滤掉
			var result = md.map(['a', 'b', 'c'], function (value, key) {
				return i + value;
			});
			console.log(result); // ['0a', '1b', '2c']

			var result = md.map([1, 2, 3], function (value, key) {
				return [value, value + 1];
			});
			console.log(result); // [1, 2, 2, 3, 3, 4]
	
	md.contains()	
		# 判断父节点是否包含子节点,返回布尔值
			md.contains(document, document.body); // true
			md.contains(document.body, document); // false

------------------------
js工具库-数据类型判断	|
------------------------
	.is()	
		# 根据 CSS 选择器、DOM 元素,或 JQ 对象来检测匹配的元素集合
		# 至少有一个元素匹配则返回 true 否则返回 false
			md('.box').is('.box');			// true
			md('.box').is('.boxss');		// false
			md('.box').is(md('.box')[0]);	// true
	
------------------------
js工具库-对象访问		|
------------------------
	.length	
		# 返回 JQ 对象中元素的数量。
			md('body').length;	// 1
	
	.each()	
		# 遍历一个 JQ 对象,为每一个匹配元素执行一个函数.如果函数返回 false 则结束遍历
			* 函数的第一个参数为元素的索引号,第二个参数为当前元素,函数的 this 关键字指向当前元素
			md('img').each(function(i, element) {
				this.src = 'test' + i + '.jpg';
			});

	.map()	
		# 遍历一个 JQ 对象,为对象的每个元素都调用一个函数,生成一个包含函数返回值的新的 JQ 对象
		# null 和 undefined 会被过滤掉
			* 函数的第一个参数为元素的索引号,第二个参数为当前元素,函数的 this 关键字指向当前元素
			var result = md('input.checked').map(function (i, element) {
				return md(this).val();
			});
			// result 为匹配元素的值组成的 JQ 对象
	
	.eq()	
		# 返回 JQ 对象中指定索引号的元素的 JQ 对象。
			md('div').eq(0); // 返回第一个元素的 JQ 对象
			md('div').eq(-1); // 返回最后一个元素的 JQ 对象
			md('div').eq(-2); // 返回倒数第二个元素的 JQ 对象
				


