-------------------------
BigInt
-------------------------
	# BigInt extends Object/Function
		https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/BigInt

	# 这是 ECMAScript 的第八种数据类型。BigInt 只用来表示整数，没有位数的限制，任何位数的整数都可以精确表示。
		* BigInt 与普通整数是两种值，它们之间并不相等，也不能与普通数值进行混合运算。
			console.log(8 === 8n); // false
		
		* 可以使用负号（-），但是不能使用正号（+），因为会与 asm.js 冲突。
		* 几乎所有的数值运算符都可以用在 BigInt，但是有两个例外。
			* 不带符号的右移位运算符>>>
			* 一元的求正运算符 +
					

	# 在数值后面加上 n ，就表示为一个 BigInt
		const val = 9999999999999999999999999999999999999999999999999n;
		console.log(val); // 9999999999999999999999999999999999999999999999999n
		console.log(0xFFFFFFFFFFFFFFFFFFFFFFFFFFFFn);  // 5192296858534827628530496329220095n
		
	
	# 构造函数
		BigInt(value)
			* 构造函数必须有参数，而且参数必须可以正常转为数值。
			
			value
				* 被创建的对象的数值。可以是字符串或整数。
		
-------------------------
this
-------------------------

	BigInt.prototype.toLocaleString()
	BigInt.prototype.toString()
	BigInt.prototype.valueOf()

-------------------------
static
-------------------------
	BigInt.asUintN(width, BigInt)
		* 给定的 BigInt 转为 0 到 2width - 1 之间对应的值。

	BigInt.asIntN(width, BigInt)
		* 给定的 BigInt 转为 -2width - 1 到 2width - 1 - 1 之间对应的值。

	BigInt.parseInt(string[, radix])
		* 近似于 Number.parseInt()，将一个字符串转换成指定进制的 BigInt。
