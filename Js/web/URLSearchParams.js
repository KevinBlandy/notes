----------------------
URLSearchParams
----------------------
	# 参数参数封装
	
	# 构造函数
		new URLSearchParams(options)

			options 可选，可以是以下之一。
				* 一个字符串，会按 application/x-www-form-urlencoded 的格式进行解析。开头的 '?' 字符会被忽略。
				* 一系列基于字面量的字符串键值对，或者任何对象（例如 FormData 对象），能提供一系列字符串对的迭代器对象。
				* 注意，File 将被序列化为 [object File]，而不是它们的文件名（就像 application/x-www-form-urlencoded 格式中的那样）。
				* 一个由字符串键和字符串值组成的键值对对象。请注意，不支持嵌套。
			
			
			new URLSearchParams("foo=&bar=baz");
			 
			new URLSearchParams([
			  ["foo", "1"],
			  ["bar", "2"],
			]);

			new URLSearchParams({ foo: "1", bar: "2" });

----------------------
this
----------------------
	size
		* 参数查询参数的数量
	
	append()
	delete()
	entries()
	forEach()
	get()
	getAll()
	has()
	keys()
	set()
	sort()
	toString()
		* 回适用于 URL 中的查询字符串（不带问好，如果为空则返回空字符串 ''）。

	values()

----------------------
static
----------------------