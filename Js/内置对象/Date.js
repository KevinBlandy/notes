--------------------------
Date
--------------------------
	# Date类型将日期保存为自协调世界时（UTC, Universal Time Coordinated）时间1970年1月1日午夜（零时）至今所经过的毫秒数。
		* 这种存储格式，Date 类型可以精确表示1970年1月1日之前及之后 285616 年的日期。
	
	# 文档
		https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/Date

	# 构造函数
		new Date();
		new Date(value);
			* Unix 时间戳（Unix Time Stamp），整数值，表示自 1970 年 1 月 1 日 00:00:00 UTC（the Unix epoch）以来的毫秒数，忽略了闰秒
			* 注意大多数 Unix 时间戳功能仅精确到最接近的秒。

		new Date(dateString);
			* 表示日期的字符串值。该字符串应该能被 Date.parse() 正确方法识别（即符合 IETF-compliant RFC 2822 timestamps 或 version of ISO8601）。
			* 强烈不推荐使用 Date 构造函数来解析日期字符串 (或使用与其等价的Date.parse)，因为实现差异问题。

		new Date(year, monthIndex [, day [, hours [, minutes [, seconds [, milliseconds]]]]]);
			* 分别指定年月日时分秒毫秒，参数代表的是本地时间
			* 0 到 99 会被映射至 1900 年至 1999 年，其他值代表实际年份。

			* 月份是整数值，从 0（1 月）到 11（12 月）。


--------------------------
实例
--------------------------

	constructor
		* 返回创建了实例的构造函数，默认是 Date 构造函数。

	setYear()
	getYear()
		* 返回/设置相对年份（相对 1900 年，通常是 2 到 3 位数字）
	
	setFullYear()
	getFullYear()
		* 返回/设置完整年份（四位数年份）。
	
	setMonth()
	getMonth()
		* 返回/设置月份（0–11），0 表示一年中的第一月。
	
	setDate()
	getDate() 
		* 返回/设置日， 1 到 31 的整数值。
	
	getDay()
		* 返回一周中的第几天（0-6），0 表示星期天。
	
	setHours()
	getHours()
		* 返回/设置小时（0–23）。
	
	setMinutes()
	getMinutes()
		* 返回/设置分钟（0–59）。
	
	setSeconds()
	getSeconds()
		* 返回/设置秒（0–59）。

	setMilliseconds()
	getMilliseconds()
		* 返回/设置毫秒数（0–999）。
	
	setTime()
	getTime()
		* 返回/设置 1970 年 1 月 1 日 0 时 0 分 0 秒（UTC，即协调世界时） 至当前 date 的毫秒数。
		* 更早的时间会用负数表示。
	
	getTimezoneOffset()
		* 返回协调世界时（UTC）相对于当前时区的时间差值，单位为分钟。
	
	toDateString()
		* 美式英语和人类易读的表述形式返回 Date 对象日期部分的字符串。
	
	toISOString()
		* 把 Date 转换成 ISO 格式表述的字符串并返回。
	
	toJSON()
		* 返回指定 Date 对象调用 toISOString() 方法的返回值。
		* 在 JSON.stringify() 中使用。
	
	toString()
		* 返回字符串，表示该 Date 对象。覆盖了 Object.prototype.toString() 方法。	

	toDateString()
		* 以美式英语和人类易读的表述形式返回 Date 对象日期部分的字符串。
			Fri May 17 2024

	toTimeString()
		* 返回 Date 对象时间部分的字符串，该字符串以美式英语格式化。
			18:28:35 GMT+0800 (中国标准时间)
	
	toUTCString()
		* 使用 UTC 时区，把 Date 对象转换为一个字符串。
	
	valueOf()
		* 返回 Date 对象的原始值，即当前日期的 Unix 时间戳（等于 getTime()）。覆盖了 Object.prototype.valueOf() 方法。
		* 返回类型是 Number
		
			typeof (new Date().valueOf()); // 'number'
		
		* 因此，Date 可以通过比较符比较大小


--------------------------
静态
--------------------------
	Date.prototype
		* 原型对象

	Date.length
		* Date.length 的值是 7。这是该构造函数可接受的参数个数。

	Date.now()
		* 返回自 1970-1-1 00:00:00 UTC（世界标准时间）至今所经过的毫秒数。

	Date.parse(dateString)
		* dateString 是符合 RFC2822 或 ISO 8601 日期格式的字符串。
	
	Date.UTC()
		* 接受的参数同 Date 构造函数接受最多参数时一样，但会视它们为 UTC 时间，而不是本地时间。

