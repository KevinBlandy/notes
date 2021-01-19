------------------------
字符串模板				|
------------------------
	# 传统的 JavaScript 语言输出模板通常是这样写的
		$('#result').append(
			'There are <b>' + basket.count + '</b> ' +
			'items in your basket, ' +
			'<em>' + basket.onSale +
			'</em> are on sale!'
		);
	
	# 上面这种写法相当繁琐不方便,ES6 引入了模板字符串解决这个问题
		$('#result').append(
			`
			  There are <b>${basket.count}</b> items
			   in your basket, <em>${basket.onSale}</em>
			  are on sale!
			`
		);

	# 模板字符串
		* 模板字符串(template string)是增强版的字符串,用反引号(`)标识
		* '它可以当作普通字符串使用,也可以用来定义多行字符串,或者在字符串中嵌入变量'

			// 普通字符串
			`In JavaScript '\n' is a line-feed.`

			// 多行字符串
			`In JavaScript this is
			 not legal.`

			console.log(`string text line 1
			string text line 2`);

			// 字符串中嵌入变量
			let name = "Bob", time = "today";
			`Hello ${name}, how are you ${time}?`

		* 如果在模板字符串中需要使用反引号，则前面要用反斜杠转义。
			let greeting = `\`Yo\` World!`;
		
		* 如果使用模板字符串表示多行字符串,所有的空格和缩进都会被保留在输出之中
			let str = `
				前面的空格换行都被保留了
			`;
			console.log(str);

			* 如果你不想要这个换行,空格可以使用trim方法消除它
				console.log(str.trim());
			
		* 模板字符串中嵌入变量,需要将变量名写在${}之中
			function authorize(user, action) {
				  if (!user.hasPrivilege(action)) {
					throw new Error(
					  // 传统写法为
					  // 'User '
					  // + user.name
					  // + ' is not authorized to do '
					  // + action
					  // + '.'
					  `User ${user.name} is not authorized to do ${action}.`);
				  }
			}

		* ${}内部可以放入任意的 JavaScript 表达式,可以进行运算,以及引用对象属性
			let x = 1;
			let y = 2;
		
			`${x} + ${y} = ${x + y}`
			// "1 + 2 = 3"
		
			`${x} + ${y * 2} = ${x + y * 2}`
			// "1 + 4 = 5"
		
			let obj = {x: 1, y: 2};
			`${obj.x + obj.y}`
			// "3"
		
		* 模板字符串之中还能调用函数。
			function fn() {
			  return "Hello World";
			}

			`foo ${fn()} bar`
			// foo Hello World bar
		
		* 如果大括号中的值不是字符串,将按照一般的规则转为字符串比如,大括号中是一个对象,将默认调用对象的toString方法

		* 如果模板字符串中的变量没有声明,将报错

			// 变量place没有声明,报错
			let msg = `Hello, ${place}`;
			
		* 由于模板字符串的大括号内部,就是执行 JavaScript 代码,因此如果大括号内部是一个字符串将会原样输出
			`Hello ${'World'}`
			// "Hello World"
		
		* 模板字符串甚至还能嵌套。
			const tmpl = addrs => 
			`
			  <table>
			  ${addrs.map(addr => 
			  `
				<tr><td>${addr.first}</td></tr>
				<tr><td>${addr.last}</td></tr>
			  `
			  ).join('')}
			  </table>
			`;

			* 就是在  `你好啊 ${getNameById(`747692844`)}`
		
		* 如果需要引用模板字符串本身,在需要时执行,可以像下面这样写
			// 写法一
			let str = 'return ' + '`Hello ${name}!`';
			let func = new Function('name', str);
			func('Jack') // "Hello Jack!"

			// 写法二
			let str = '(name) => `Hello ${name}!`';
			let func = eval.call(null, str);
			func('Jack') // "Hello Jack!"

------------------------
模板编译				|
------------------------
	# 一个通过模板字符串,生成正式模板的实例

	1,定义模板
		let template = `
			<ul>
				<%for (let i of users){%>
					<li><%=i.name%>,<%=i.age%></li>
				<%}%>
			</ul>
		`;

		* 上面代码在模板字符串之中,放置了一个常规模板
		* 该模板使用<%...%>放置 JavaScript 代码,使用<%= ... %>输出 JavaScript 表达式
	
	2,编译模版方法
		function compile(template){
			const evalExpr = /<%=(.+?)%>/g;
			const expr = /<%([\s\S]+?)%>/g;

			template = template
			.replace(evalExpr, '`); \n  echo( $1 ); \n  echo(`')
			.replace(expr, '`); \n $1 \n  echo(`');

			template = 'echo(`' + template + '`);';

			let script =
			`	(function parse(data){
				let output = "";
				
				function echo(html){
				  output += html;
				}
				
				${ template }
				
				return output;
				})
			`;

			return script;
		}
	
	3,执行编译和渲染
		
		//获取编译后的模版
		let parse = eval(compile(template));
		
		//定义填充数据
		var users = [{name:'Kevin',age:23},{name:'Litch',age:25}];
		
		//执行模版渲染,返回渲染结果
		console.log(parse(users));


------------------------
模板标签				|
------------------------
	# 模板字符串,可以紧跟在一个函数名后面,该函数将被调用来处理这个模板字符串,这被称为"标签模板"功能(tagged template)
		alert`Hello`
	
	# 标签模板其实不是模板,而是函数调用的一种特殊形式,"标签"指的就是函数,紧跟在后面的模板字符串就是它的参数

	# 但是,如果模板字符里面有变量,就不是简单的调用了,而是会将模板字符串先处理成多个参数,再调用函数
		let a = 5;
		let b = 10;

		tag`Hello ${ a + b } world ${ a * b }`;

		// 等同于   ===== 根据 ${} 把字符串切分开来,把每段儿都放在一个数组里传递给第一个参数,然后把${}里面的值,挨个写入后面的参数
		//
		tag(['Hello ', ' world ', ''], 15, 50);

		* 函数tag依次会接收到多个参数
			function tag(stringArr, value1, value2){
			  // ...
			}
			// 等同于
			function tag(stringArr, ...values){
			  // ...
			}
			* tag函数的第一个参数是一个数组,该数组的成员是模板字符串中那些没有变量替换的部分
			* 也就是说,变量替换只发生在数组的第一个成员与第二个成员之间,第二个成员与第三个成员之间,以此类推
			* 也就是说，tag函数实际上以下面的形式调用(第一个数组参数最后一定有一个空字符串(''))
				tag(['Hello ', ' world ', ''], 15, 50)
	
		* 我们可以按照需要编写tag函数的代码
			let a = 5;
			let b = 10;

			function tag(s, v1, v2) {
			  console.log(s[0]);	// "Hello "
			  console.log(s[1]);	// " world "
			  console.log(s[2]);	// ""
			  console.log(v1);		// 15
			  console.log(v2);		// 50
			  return "OK";
			}
			tag`Hello ${ a + b } world ${ a * b}`;
		
		* 更复杂的例子
			function passthru(literals) {
				let result = '';
				let i = 0;
				while (i < literals.length) {
					result += literals[i++];
					if (i < arguments.length) {		//通过arguments来获取传递进来的模版参数
						result += arguments[i];
					}
				}
				return result;
			}
		
		* rest 参数的函数调用
			function passthru(literals, ...values) {
				let output = "";
				let index;
				for (index = 0; index < values.length; index++) {		//遍历模版参数
					output += literals[index] + values[index];
				}
				output += literals[index]
				return output;
			}

		* "标签模板"的一个重要应用,就是过滤 HTML 字符串,防止用户输入恶意内容
			function SaferHTML(templateData) {
				let s = templateData[0];
				for (let i = 1; i < arguments.length; i++) {
					let arg = String(arguments[i]);
					// Escape special characters in the substitution.
					s += arg.replace(/&/g, "&amp;")
					.replace(/</g, "&lt;")
					.replace(/>/g, "&gt;");

					// Don't escape special characters in the template.
					s += templateData[i];
				}
				return s;
			}

			let message = SaferHTML`<p>${sender} has sent you a message.</p>`;

			* ender变量往往是用户提供的,经过 SaferHTML 函数处理,里面的特殊字符都会被转义

		* 标签模板的另一个应用,就是多语言转换(国际化处理)
			i18n`Welcome to ${siteName}, you are visitor number ${visitorNumber}!`
			// "欢迎访问xxx，您是第xxxx位访问者！"
		
		* 模板处理函数的第一个参数(模板字符串数组),还有一个raw属性
			console.log`123`
			// ["123", raw: Array[1]]

			* console.log接受的参数,实际上是一个数组,该数组有一个raw属性
			
			* tag函数的第一个参数,有一个raw属性,也指向一个数组,该数组的成员与第一个参数完全一致
				tag`First line\nSecond line`
				function tag(strings) {
					console.log(strings.raw[0]);
					// strings.raw[0] 为		"First line\\nSecond line"
					// 打印输出				"First line\nSecond line"
				}
				* 比如 strings 数组是		["First line\nSecond line"]
				* 那么 strings.raw 数组就是	["First line\\nSecond line"]
				* 两者唯一的区别,就是字符串里面的斜杠都被转义了,\
				* 比如,strings.raw 数组会将\n视为\\和n两个字符,而不是换行符
				  这是为了方便取得转义之前的原始模板而设计的
		
		* String.raw() 方法
			* 就是把 `` 转换为正规的字符串,然后把``里面看不见的那些换行啊之类的符号,都添加字符串的转义符号
			* ES6 还为原生的 String 对象,提供了一个raw方法
			* String.raw方法,往往用来充当模板字符串的处理函数,返回一个斜杠都被转义(即斜杠前面再加一个斜杠)的字符串对应于替换变量后的模板字符串
				String.raw`Hi\n${2+3}!`;
				// 返回 "Hi\\n5!"

				String.raw`Hi\u000A!`;
				// 返回 "Hi\\u000A!"

			* 如果原字符串的斜杠已经转义,那么String.raw会进行再次转义
				String.raw`Hi\\n`
				// 返回 "Hi\\\\n"
			
			* String.raw方法可以作为处理模板字符串的基本方法,它会将所有变量替换,而且对斜杠进行转义,'方便下一步作为字符串来使用'
			* String.raw方法也可以作为正常的函数使用,这时,它的第一个参数,应该是一个具有raw属性的对象,'且raw属性的值应该是一个数组'

				String.raw({ raw: 'test' }, 0, 1, 2);
				// 't0e1s2t'

				// 等同于
				String.raw({ raw: ['t','e','s','t'] }, 0, 1, 2);
			
			* 作为函数,String.raw的代码实现基本如下
				String.raw = function (strings, ...values) {
					let output = '';
					let index;

					for (index = 0; index < values.length; index++) {
					  output += strings.raw[index] + values[index];
					}
					
					output += strings.raw[index]
					return output;
				}

------------------------
字符串模版的限制		|
------------------------
	* 标签模板里面,可以内嵌其他语言,但是,模板字符串默认会将字符串转义,导致无法嵌入其他语言
	* 为了解决这个问题,ES2018 放松了对标签模板里面的字符串转义的限制
	* 如果遇到不合法的字符串转义,就返回undefined,而不是报错,并且从raw属性上面可以得到原始字符串
		function tag(strs) {
		  strs[0] === undefined							//是不合法的字符串转义
		  strs.raw[0] === "\\unicode and \\u{55}";		//可以获取其原始字符串
		}
		tag`\unicode and \u{55}`
		
		* 上面代码中,模板字符串原本是应该报错的,但是由于放松了对字符串转义的限制,所以不报错了
		* JavaScript 引擎将第一个字符设置为undefined,但是raw属性依然可以得到原始字符串,因此tag函数还是可以对原字符串进行处理

		* 注意,这种对字符串转义的放松,只在标签模板解析字符串时生效,不是标签模板的场合,依然会报错
			let bad = `bad escape sequence: \unicode`; // 报错