-------------------------
语法
-------------------------
	# 开启严格模式
		* 在脚本放第一行声明指令
			"use strict";
		
		* 也可以在函数第一行声明
			function foo (){
				"use strict";
			}
		
		* 所有现代浏览器都支持严格模式
	
	# 所有关键字
		break         do             in               typeof
		case          else          instanceof     var
		catch         export        new              void
		class         extends      return          while
		const         finally      super           with
		continue     for           switch          yield
		debugger     function     this
		default      if             throw
		delete        import        try

-------------------------
变量
-------------------------
	# var 变量声明
		* var 声明的范围是函数作用域
		* 如果省略 var 标识符，会创建全局变量
			// "use strict"; // 在严格模式下，声明变量不使用关键字会语法错误 index.js:5 Uncaught ReferenceError: bar is not defined
			function foo (){
				// 函数内部声明变量，不使用 var 关键字
				bar = "Hello"
			}
			// 运行函数，会创建 bar 全局变量
			foo()
			// 输出变量
			console.log(bar)
		
		* var 声明的变量，会自动提升到函数作用域顶部（把所有变量声明都拉到函数作用域的顶部）
			* 先访问变量，再声明变量
			    function foo() {
				  console.log(age);		// 先访问变量
				  var age = 26;			// 后声明、赋值变量
				}
				// 调用函数，运行正常
				foo();   // undefined
			
			* ECMAScript 运行时把它看成等价于如下代码
				function foo() {
				  var age;				// 变量声明，提升到顶部
				  console.log(age);
				  age = 26;				// 赋值变量
				}
				foo();   // undefined
		
		* 同一作用域下，可以多次重复声明相同变量名称的变量
			    function foo() {
				  var age = 16;
				  var age = 26;
				  var age = 36;
				  console.log(age);
				}
				foo();   // 36
		
		* var 在全局作用域下声明的变量会成为浏览器 window 对象的属性
			    var name = 'Matt';
				console.log(window.name); // 'Matt'

	
	# let 变量声明（推荐）
		* let 声明的范围是块作用域，块作用域是函数作用域的子集，因此适用于 var 的作用域限制同样也适用于let。
		* let 不存在变量提升，所以，必须先声明，后访问。
		* 任何时候下，只要是先使用 let 变量，后声明一定会异常。这叫做变量的 “暂时性死区”

		* let 在同一作用域下，不允许重复声明相同名称的变量
			let name;
			let name;
			// ncaught SyntaxError: Identifier 'name' has already been declared (at index.js:5:5)
		
	
	# const 常量声明
		* 常量，声明后不能修改。
	
	# 在严格模式下，不能定义名为 eval 和 arguments 的变量，否则会导致语法错误。
	
	# 一次性声明多个变量，使用逗号分隔
		var foo = "123", bar = true;
	
-------------------------
计算
-------------------------
	# 自增/减
		++i;  // 先自增，再计算
		i--;  // 先计算，再自增
		
		* 操作符可以作用于任何值，不限于整数
			* 对于字符串，如果是有效的数值形式，则转换为数值再应用改变。变量类型从字符串变成数值。
			* 对于字符串，如果不是有效的数值形式，则将变量的值设置为 NaN。变量类型从字符串变成数值
			* 对于布尔值，如果是 false，则转换为0再应用改变。变量类型从布尔值变成数值。
			* 对于布尔值，如果是 true，则转换为1再应用改变。变量类型从布尔值变成数值。
			* 对于浮点值，加1或减1。
			* 如果是对象，则调用其 valueOf() 方法取得可以操作的值。对得到的值应用上述规则。
			* 如果是NaN，则调用 toString() 并再次应用其他规则。变量类型从对象变成数值。
		
	# 一元运算
		* 将一元加应用到非数值，则会执行与使用 Number() 转型函数一样的类型转换
			* 布尔值false和true转换为0和1
			* 字符串根据特殊规则进行解析
			* 对象会调用它们的 valueOf() 和/或 toString() 方法以得到可以转换的值。
	

	# 位移
		* 和 Java 一样，包含无符号右移。
	
	
	# 布尔逻辑
		* 除了常规的 boolean 逻辑外，js 还有一些骚操作。

		!
			* 这个操作符始终返回布尔值，无论应用到的是什么数据类型。逻辑非操作符首先将操作数转换为布尔值，然后再对其取反。
		
		&&
			* 果有操作数不是布尔值，则逻辑与并不一定会返回布尔值，而是遵循如下规则。
			* 如果第一个操作数是对象，则返回第二个操作数。
			* 如果第二个操作数是对象，则只有第一个操作数求值为 true 才会返回该对象。
			* 如果两个操作数都是对象，则返回第二个操作数。
			* 如果有一个操作数是 null，则返回null。
			* 如果有一个操作数是 NaN，则返回NaN。
			* 如果有一个操作数是undefined，则返回undefined。

		||
			* 如果有一个操作数不是布尔值，那么逻辑或操作符也不一定返回布尔值。它遵循如下规则。
			
			* 如果第一个操作数是对象，则返回第一个操作数。
			* 如果第一个操作数求值为false，则返回第二个操作数。
			* 如果两个操作数都是对象，则返回第一个操作数。
			* 如果两个操作数都是null，则返回null。
			* 如果两个操作数都是NaN，则返回NaN。
			* 如果两个操作数都是undefined，则返回undefined。
	# 乘
	
	# 除
	
	# 关系比较： >、<
		* 如果有任一操作数是数值，则将另一个操作数转换为数值，执行数值比较。
		* 如果有任一操作数是布尔值，则将其转换为数值再执行比较。
	
	# == 比较
		* 这个操作符都会先进行类型转换（通常称为强制类型转换）再确定操作数是否相等。

			* 如果任一操作数是布尔值，则将其转换为数值再比较是否相等。false转换为0,true转换为1。
			* 如果一个操作数是字符串，另一个操作数是数值，则尝试将字符串转换为数值，再比较是否相等。
			* 如果一个操作数是对象，另一个操作数不是，则调用对象的valueOf()方法取得其原始值，再根据前面的规则进行比较。
		
		* 比较时，这个操作符会遵循如下规则。
	
			* null和undefined相等。
			* null和undefined不能转换为其他类型的值再进行比较。
			* 如果有任一操作数是NaN，则相等操作符返回false，不相等操作符返回true。记住：即使两个操作数都是NaN，相等操作符也返回false，因为按照规则，NaN不等于NaN。
			* 如果两个操作数都是对象，则比较它们是不是同一个对象。如果两个操作数都指向同一个对象，则相等操作符返回true。否则，两者不相等。
		
	# === 比较
		* 全等操作符由3个等于号（===）表示，只有两个操作数在不转换的前提下相等才返回 true。
		* 也就是说，如果类型不同返回 false
	
	# 指数运算符 **
		2 ** 2 // 4
		2 ** 3 // 8

		* 特点是右结合，而不是常见的左结合。多个指数运算符连用时，是从最右边开始计算的。

			2 ** 3 ** 2 // 相当于 2 ** (3 ** 2)
			// 512
		
		* 以与等号结合，形成一个新的赋值运算符（**=）。
			let a = 1.5;	
			a **= 2;	// 等同于 a = a * a;

			let b = 4;
			b **= 3;	// 等同于 b = b * b * b;
	
	# 链判断运算符 ?.
		* ?. 判断左侧的对象是否为 null 或 undefined。如果是的，就不再往下运算，而是返回 undefined。

			const firstName = message?.body?.user?.firstName || 'default';
			const fooValue = myForm.querySelector('input[name=foo]')?.value
		
		* 判断对象方法是否存在，如果存在就立即执行，不会存在的话不会执行，返回 undefined
			let ret = {}.foo?.();  // 存在 foo 方法，就立即执行
			console.log(ret);  // undefined
		
		* 如果值存在，且不是函数，调用会异常
			let  obj = {a:1}
			obj.a?.()  // TypeError: obj.a is not a function
		
		* ?. 有三种写法。
				obj?.prop // 对象属性是否存在
				obj?.[expr] // 同上
				func?.(...args) // 函数或对象方法是否存在
			
		* 右侧不得为十进制数值
			* foo?.3:0 被解析成 foo ? .3 : 0
			* 也就是说，那个小数点会归属于后面的十进制数字，形成一个小数。

	# Null 判断运算符 ??
		* 行为类似于 ||，只有运算符左侧的值为 null 或 undefined 时，才会返回右侧的值。

			const headerText = response.settings.headerText ?? 'Hello, world!';
			const animationDuration = response.settings.animationDuration ?? 300;
			const showSplashScreen = response.settings.showSplashScreen ?? true;
		
		* 其目的，就是跟链判断运算符 ?. 配合使用，为 null 或 undefined 的值设置默认值。
			let  obj = {a:1}

			// let val = obj.bar.foo ?? 'defualt';  // TypeError: Cannot read properties of undefined (reading 'foo')
			let val = obj?.bar?.foo ?? 'defaultVal';
			console.log(val);  // defaultVal
		
		* 和 || 的不同
			* 使用 ?? 时，只有当左边的值为 null 或 undefined 时才返回右边的值。
			* 使用 || 时，左边的值会转换为布尔值判断，为 true 返回左边的值，false 返回右边的值。

		
		* 与 && 和 || 的优先级问题，如果多个逻辑运算符一起使用，必须用括号表明优先级，否则会报错。
			(lhs && middle) ?? rhs;
			lhs && (middle ?? rhs);

			(lhs ?? middle) && rhs;
			lhs ?? (middle && rhs);

			(lhs || middle) ?? rhs;
			lhs || (middle ?? rhs);

			(lhs ?? middle) || rhs;
			lhs ?? (middle || rhs);
		
	# 逻辑赋值运算符 ||= &&= ??=
		* ES2021 引入了三个新的逻辑赋值运算符（logical assignment operators），将逻辑运算符与赋值运算符进行结合。
		* 相当于先进行逻辑运算，然后根据运算结果，再视情况进行赋值运算。
			// 或赋值运算符
			x ||= y
			// 等同于
			x || (x = y)

			// 与赋值运算符
			x &&= y
			// 等同于
			x && (x = y)

			// Null 赋值运算符
			x ??= y
			// 等同于
			x ?? (x = y)
		
		* 用途是，为变量或属性设置默认值。
			// 老的写法
			user.id = user.id || 1;

			// 新的写法
			user.id ||= 1;
	

	# '#!' 命令
		* 类似于 Shell 和 Python 脚本的第一行
			#!/bin/sh
			#!/usr/bin/env python
		
		* ES2023 为 JavaScript 脚本引入了#!命令，写在脚本文件或者模块文件的第一行。

			// 写在脚本文件第一行
			#!/usr/bin/env node
			'use strict';
			console.log(1);

			// 写在模块文件第一行
			#!/usr/bin/env node
			export {};
			console.log(1);
		
		* 有了这一行以后，Unix 命令行就可以直接执行脚本。
			# 以前执行脚本的方式
			$ node hello.js

			# hashbang 的方式
			$ ./hello.js
		
		* 对于 JavaScript 引擎来说，会把#!理解成注释，忽略掉这一行。

-------------------------
语句
-------------------------
	# for in
		* 用于枚举对象中的非 Symbol 属性 key （字符串）
			for (const key in window){
				console.log(key);
			}
	
		* 如果for-in循环要迭代的变量是null或undefined，则不执行循环体
	
	# for of
		* 用于遍历可迭代对象的元素

		for (const item of [1, "2", 3]){
			console.log(item);
		}

		* for-of循环会按照可迭代对象的next()方法产生值的顺序迭代元素。
		* 如果尝试迭代的变量不支持迭代，则for-of语句会抛出错误。

		* 如果使用 var 作为循环变量，则会存在变量泄露到全局的问题。
	
	
	# with
		* with 语句的用途是将代码作用域设置为特定的对象
			    with (obj) {
					
			    };
			
		* 在这个语句内部，每个变量首先会被认为是一个局部变量。如果没有找到该局部变量，则会搜索 with 的对象，看它是否有一个同名的属性。
		* 如果有，则该变量会被求值为 with 对象的属性。

		* 例如：

			// 把 window.location 对象作为作用域
			with(location) {
				// 直接访问 location 中的 search 属性
				let qs = search.substring(1);
				let hostName = hostname;
				let url = href;
			}
		
		* 严格模式不允许使用with语句，否则会抛出错误。
	
	# switch
		* 如果没有 break，则代码会继续匹配下一个条件。
		* default 关键字用于在任何条件都没有满足时指定默认执行的语句（相当于else语句）。
	
		* 条件的值不需要是常量，也可以是变量或表达式。
		    switch ("hello world") {
			  case "hello" + " world":  // 表达式
				console.log("Greeting was found.");
				break;
			  case "goodbye":
				console.log("Closing was found.");
				break;
			  default:
				console.log("Unexpected message was found.");
			}

		* switch 语句在比较每个条件的值时会使用全等操作符 ===。
	
	# try catch 语句

		
		* 允许 catch 语句省略参数。

			try{
				
			}catch {

			}
