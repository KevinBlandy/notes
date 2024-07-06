---------------------------
Math
---------------------------
	# Math
		* 内置对象，它拥有一些数学常数属性和数学函数方法。
		* Math 用于 Number 类型。它不支持 BigInt
	
	# Math 不是一个构造器。Math 的所有属性与方法都是静态的

	
---------------------------
static
---------------------------
	Math.E
	Math.LN10
	Math.LN2
	Math.LOG10E
	Math.LOG2E
	Math.PI
	Math.SQRT1_2
	Math.SQRT2

	Math.abs()
	Math.acos()
	Math.acosh()
	Math.asin()
	Math.asinh()
	Math.atan()
	Math.atan2()
	Math.atanh()
	Math.cbrt(x)
		* 计算一个数的立方根

	Math.ceil()
	Math.clz32(x)
		* 返回一个数字在转换成 32 无符号整形数字的二进制形式后，开头的 0 的个数

	Math.cos()
	Math.cosh()
	Math.exp()
	Math.expm1()
	Math.floor()
	Math.fround()
	Math.hypot()
	Math.imul()
	Math.log()
	Math.log10()
	Math.log1p()
	Math.log2()
	Math.max()
	Math.min()
	Math.pow()
	Math.random()
		* 返回一个在 0（包括）到 1（不包括）之间的伪随机浮点数。

	Math.round()
	Math.sign(x);
		* 判断一个数到底是正数、负数、还是零。对于非数值，会先将其转换为数值。
		* 它会返回五种值
			正数，返回+1
			负数，返回-1
			0，返回0；
			-0，返回-0;
			其他，返回NaN。
	
	Math.sin()
	Math.sinh()
	Math.sqrt()
	Math.tan()
	Math.tanh()
	Math.trunc(value)
		* 去除一个数的小数部分，返回整数部分。

------------------------
Demo
------------------------
	# 得到一个两数之间的随机整数

		function getRandomInt(min, max) {
			const minCeiled = Math.ceil(min);
			const maxFloored = Math.floor(max);
			return Math.floor(Math.random() * (maxFloored - minCeiled) + minCeiled); // 不包含最大值，包含最小值

			// return Math.floor(Math.random() * (maxFloored - minCeiled + 1) + minCeiled); // 包含最小值和最大值
		}
