----------------------------
正则						|
----------------------------
	1,RegExp 构造函数
	2,字符串的正则方法
	3,u 修饰符
	5,y 修饰符
	6,sticky 属性
	7,flags 属性
	8,s 修饰符：dotAll 模式
	9,后行断言
	10,Unicode 属性类
	11,具名组匹配
	12,String.prototype.matchAll


----------------------------
RegExp 构造函数				|
----------------------------
	# ES5 中,RegExp构造函数的参数有两种情况。
		* 第一种情况是参数是字符串,这时第二个参数表示正则表达式的修饰符(flag)
			var regex = new RegExp('xyz', 'i');
			//等价于
			var regex = /xyz/i;
	
	# 第二种情况是,参数是一个正则表示式,这时会返回一个原有正则表达式的拷贝
		var regex = new RegExp(/xyz/i);
		// 等价于
		var regex = /xyz/i;
	
	# ES5 不允许此时使用第二个参数添加修饰符,否则会报错
		var regex = new RegExp(/xyz/, 'i');
		// Uncaught TypeError: Cannot supply flags when constructing one RegExp from another
	
	# ES6 改变了这种行为,如果RegExp构造函数第一个参数是一个正则对象,那么可以使用第二个参数指定修饰符
	  而且,返回的正则表达式会忽略原有的正则表达式的修饰符,只使用新指定的修饰符
		new RegExp(/abc/ig, 'i').flags
		// "i"

		* 原有正则对象的修饰符是ig,它会被第二个参数i覆盖

----------------------------
字符串的正则方法			|
----------------------------
	# 字符串对象共有 4 个方法,可以使用正则表达式
		match()
		replace()
		search()
		split()

	# ES6 将这 4 个方法,在语言内部全部调用RegExp的实例方法,从而做到所有与正则相关的方法,全都定义在RegExp对象上

		String.prototype.match		调用 RegExp.prototype[Symbol.match]
		String.prototype.replace	调用 RegExp.prototype[Symbol.replace]
		String.prototype.search		调用 RegExp.prototype[Symbol.search]
		String.prototype.split		调用 RegExp.prototype[Symbol.split]

----------------------------
u修饰符						|
----------------------------
	# ES6 对正则表达式添加了u修饰符,含义为"Unicode 模式",用来正确处理大于\uFFFF的 Unicode 字符
	# 也就是说,会正确处理四个字节的 UTF-16 编码
		/^\uD83D/u.test('\uD83D\uDC2A')		// false
		/^\uD83D/.test('\uD83D\uDC2A')		// true
	

----------------------------
具名组匹配					|
----------------------------
	# 正则表达式里面有三组圆括号,使用exec方法,就可以将这三组匹配结果提取出来
		const RE_DATE = new RegExp('^(\\d{4})-(\\d{2})-(\\d{2})$')

		const matcher = RE_DATE.exec('1999-12-31');

		//["1999-12-31", "1999", "12", "31", index: 0, input: "1999-12-31"]
	
	# ES2018 引入了具名组匹配(Named Capture Groups),允许为每一个组匹配指定一个名字.既便于阅读代码,又便于引用

		const RE_DATE = new RegExp('^(?<year>\\d{4})-(?<month>\\d{2})-(?<day>\\d{2})$')
        const matcher = RE_DATE.exec('1999-12-31');

        console.log(matcher);               //["1999-12-31", "1999", "12", "31", index: 0, input: "1999-12-31", groups: {…}]
        console.log(matcher.groups.year);   //1999
        console.log(matcher.groups.month);  //12
        console.log(matcher.groups.day);    //31

		* 如果具名组没有匹配,那么对应的groups对象属性会是undefined
	
----------------------------
解构赋值和替换				|
----------------------------
	# 有了具名组匹配以后,可以使用解构赋值直接从匹配结果上为变量赋值
		const RE_DATE = new RegExp('^(?<year>\\d{4})-(?<month>\\d{2})-(?<day>\\d{2})$')
		const matcher = RE_DATE.exec('1999-12-31');
		let {groups:{year:y,month:m,day:d}} = matcher
		console.log(y)      //1999
		console.log(m)      //12
		console.log(d)      //31
	
	# 字符串替换时,使用$<组名>引用具名
		let re = /(?<year>\d{4})-(?<month>\d{2})-(?<day>\d{2})/u;

		'2015-01-02'.replace(re, '$<day>/$<month>/$<year>')

		* replace方法的第二个参数是一个字符串,而不是正则表达式
		* replace方法的第二个参数也可以是函数,该函数的参数序列如下
			'2015-01-02'.replace(re, (
			   matched, // 整个匹配结果 2015-01-02
			   capture1, // 第一个组匹配 2015
			   capture2, // 第二个组匹配 01
			   capture3, // 第三个组匹配 02
			   position, // 匹配开始的位置 0
			   S, // 原字符串 2015-01-02
			   groups // 具名组构成的一个对象 {year, month, day}
			 ) => {
				 let {day, month, year} = args[args.length - 1];
				 return `${day}/${month}/${year}`;
			});
			
			* 具名组匹配在原来的基础上,新增了最后一个函数参数:具名组构成的一个对象,函数内部可以直接对这个对象进行解构赋值


----------------------------
String.prototype.matchAll	|
----------------------------
	# 如果一个正则表达式在字符串里面有多个匹配,现在一般使用g修饰符或y修饰符,在循环里面逐一取出
		var regex = /t(e)(st(\d?))/g;
        var string = 'test1test2test3';

        var matches = [];
        var match;
        while (match = regex.exec(string)) {
            matches.push(match);
        }

        matches
        // [
        //   ["test1", "e", "st1", "1", index: 0, input: "test1test2test3"],
        //   ["test2", "e", "st2", "2", index: 5, input: "test1test2test3"],
        //   ["test3", "e", "st3", "3", index: 10, input: "test1test2test3"]
        // ]
	
	# 目前有一个提案,增加了String.prototype.matchAll方法
	# 可以一次性取出所有匹配,不过,它返回的是一个遍历器(Iterator),而不是数组

		const string = 'test1test2test3';

        // g 修饰符加不加都可以
        const regex = /t(e)(st(\d?))/g;

        for (const match of string.matchAll(regex)) {
            console.log(match);
        }
        // ["test1", "e", "st1", "1", index: 0, input: "test1test2test3"]
        // ["test2", "e", "st2", "2", index: 5, input: "test1test2test3"]
        // ["test3", "e", "st3", "3", index: 10, input: "test1test2test3"]

		* 迭代器比较省资源
		* 遍历器转为数组是非常简单的,使用...运算符和Array.from方法就可以了。

		// 转为数组方法一
		[...string.matchAll(regex)]

		// 转为数组方法二
		Array.from(string.matchAll(regex));