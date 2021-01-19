----------------------
断点续传 相关的header
----------------------
	# Range
		* Range 是一个请求首部，告知服务器返回文件的哪一部分
		* 在一个 Range 首部中，可以一次性请求多个部分，服务器会以 multipart 文件的形式将其返回。
		* 如果服务器返回的是范围响应，需要使用 206 Partial Content 状态码。
		* 假如所请求的范围不合法，那么服务器会返回  416 Range Not Satisfiable 状态码，表示客户端错误。
		* 服务器允许忽略  Range  首部，从而返回整个文件，状态码用 200 。

		* 语法
			Range: <unit>=<range-start>-
			Range: <unit>=<range-start>-<range-end>
			Range: <unit>=<range-start>-<range-end>, <range-start>-<range-end>
			Range: <unit>=<range-start>-<range-end>, <range-start>-<range-end>, <range-start>-<range-end>

			<unit>
				* 范围所采用的单位，通常是字节（bytes）。

			<range-start>
				* 一个整数，表示在特定单位下，范围的起始值。

			<range-end>
				* 一个整数，表示在特定单位下，范围的结束值。这个值是可选的，如果不存在，表示此范围一直延伸到文档结束。
		
		* 示例
			Range: bytes=200-1000, 2000-6576, 19000- 
	
	# If-Range
		* If-Range HTTP 请求头字段用来使得 Range 头字段在一定条件下起作用：
			* 当字段值中的条件得到满足时，Range 头字段才会起作用，同时服务器回复206 部分内容状态码，以及Range 头字段请求的相应部分；
			* 如果字段值中的条件没有得到满足，服务器将会返回 200 OK 状态码，并返回完整的请求资源。

		* 字段值中既可以用 Last-Modified 时间值用作验证，也可以用ETag标记作为验证，但不能将两者同时使用。
		* If-Range 头字段通常用于断点续传的下载过程中，用来自从上次中断后，确保下载的资源没有发生改变。

		* 语法
			If-Range: <day-name>, <day> <month> <year> <hour>:<minute>:<second> GMT
			If-Range: <etag>

			<etag>
				* 一个资源标签（entity tag）代表着所请求的资源。它是由被双引号包围的ACSII 编码的字符串组成的（例如"675af34563dc-tr34"）。当应用弱匹配算法时，E-Tag会有一个 W/ 前缀。
			<day-name>
				* "Mon"，"Tue"，"Wed"，"Thu"，"Fri"，"Sat"或者"Sun"当中的一个（大小写敏感）。
			<day>
				* 两位数字，例如"04"或者"23"。
			<month>
				* "Jan"，"Feb"，"Mar"，"Apr"，"May"，"Jun"，"Jul"，"Aug"，"Sep"，"Oct"，"Nov"，或者"Dec"中的一个（大小写敏感）。
			<year>
				* 四位数字，例如"1990"或者"2016"。
			<hour>
				* 两位数字，例如"09"或者"23"。
			<minute>
				* 两位数字，例如"04"或者"59"。
			<second>
				* 两位数字，例如"04"或者"59"。
			GMT
				* 格林威治标准时间。HTTP 协议的日期总是要使用GMT，而不是当地时间。
		
		* 示例
			If-Range: Wed, 21 Oct 2015 07:28:00 GMT 

	
	# Content-Range
		* 在HTTP协议中，响应首部 Content-Range 显示的是一个数据片段在整个文件中的位置。

		* 语法
			Content-Range: <unit> <range-start>-<range-end>/<size>
			Content-Range: <unit> <range-start>-<range-end>/*
			Content-Range: <unit> */<size>

			<unit>
				* 数据区间所采用的单位。通常是字节（byte）。
			<range-start>
				* 一个整数，表示在给定单位下，区间的起始值。
			<range-end>
				* 一个整数，表示在给定单位下，区间的结束值。
			<size>
				* 整个文件的大小（如果大小未知则用"*"表示）。
		
		* 示例
			Content-Range: bytes 200-1000/67589 
		
	

	
	# Last-Modified
		* The Last-Modified  是一个响应首部，其中包含源头服务器认定的资源做出修改的日期及时间。 
		* 它通常被用作一个验证器来判断接收到的或者存储的资源是否彼此一致。
		* 由于精确度比  ETag 要低，所以这是一个备用机制。
		* 包含有  If-Modified-Since 或 If-Unmodified-Since 首部的条件请求会使用这个字段。

		* 客户端发送 If-Modified-Since 给服务器端，服务端根据这个header获取到最后修改时间，跟本地文件对比
		* 如果本地文件有修改，则返回新的内容
		* 如果本地文件没修改，则返回 304 告诉客户端其本地 cache 的页面是最新的，于是客户端就可以直接从本地加载页面了。
	
	# Etag
		* 主要为了解决 Last-Modified 无法解决的一些问题。
			1. 一些文件也许会周期性的更改，但是内容并不改变（仅改变修改时间），这时候我们并不希望客户端认为这个文件被修改了，而重新 GET。
			2. 某些文件修改非常频繁，例如：在秒以下的时间内进行修改（1s 内修改了 N 次），If-Modified-Since 能检查到的粒度是 s 级的，这种修改无法判断（或者说 UNIX 记录 MTIME 只能精确到秒）。
			3. 某些服务器不能精确的得到文件的最后修改时间。
		
		* 为此，HTTP/1.1 引入了 Etag。Etag 仅仅是一个和文件相关的标记，可以是一个版本标记，
			例如：v1.0.0；或者说 “627-4d648041f6b80” 这么一串看起来很神秘的编码。
		* HTTP/1.1 标准并没有规定 Etag 的内容是什么或者说要怎么实现，唯一规定的是 Etag 需要放在 “” 内。

		* 语法
			ETag: W/"<etag_value>"
			ETag: "<etag_value>"

			W/ （可选）
				* 'W/'(大小写敏感) 表示使用弱验证器。 
				* 弱验证器很容易生成，但不利于比较。 强验证器是比较的理想选择，但很难有效地生成。 相同资源的两个弱Etag值可能语义等同，但不是每个字节都相同。

			"<etag_value>"
				* 实体标签唯一地表示所请求的资源。 它们是位于双引号之间的ASCII字符串（如“675af34563dc-tr34”）。 
				* 没有明确指定生成ETag值的方法。 通常，使用内容的散列，最后修改时间戳的哈希值，或简单地使用版本号。 
				* 例如，MDN使用wiki内容的十六进制数字的哈希值。
			
		
		* 示例
			ETag: "33a64df551425fcc55e4d42a148795d9f25f89d4"
			ETag: W/"0815"



	# If-Match 
		* 请求首部 If-Match 的使用表示这是一个条件请求。
		* 在请求方法为 GET 和 HEAD 的情况下，服务器仅在请求的资源满足此首部列出的 ETag值时才会返回资源。
			* 对于 GET  和 HEAD 方法，搭配  Range首部使用，可以用来保证新请求的范围与之前请求的范围是对同一份资源的请求。
			* 如果  ETag 无法匹配，那么需要返回 416 (Range Not Satisfiable，范围请求无法满足) 响应。

		* 而对于 PUT 或其他非安全方法来说，只有在满足条件的情况下才可以将资源上传。
			* 对于其他方法来说，尤其是 PUT, If-Match 首部可以用来避免更新丢失问题。
			* 它可以用来检测用户想要上传的不会覆盖获取原始资源之后做出的更新。
			* 如果请求的条件不满足，那么需要返回  412 (Precondition Failed，先决条件失败) 响应。


		* 语法
			If-Match: <etag_value>
			If-Match: <etag_value>, <etag_value>, …

			<etag_value>
				* 唯一地表示一份资源的实体标签。
				* 标签是由 ASCII 字符组成的字符串，用双引号括起来（如 "675af34563dc-tr34"）。前面可以加上 W/ 前缀表示应该采用弱比较算法。
			*
				* 星号是一个特殊值，可以指代任意资源。
		
		* 示例
			If-Match: "bfc13a64729c4290ef5b2c2730249c88ca92d82d"
			If-Match: W/"67ab43", "54ed21", "7892dd"
			If-Match: *

----------------------
断点续传 逻辑
----------------------
