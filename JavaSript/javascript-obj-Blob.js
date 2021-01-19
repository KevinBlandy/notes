------------------
Blob
------------------
	# Blob 对象表示一个不可变, 原始数据的类文件对象
		* Blob 表示的不一定是JavaScript原生格式的数据
		* File 接口基于Blob，继承了 blob 的功能并将其扩展使其支持用户系统上的文件。

	# 文档
		https://developer.mozilla.org/zh-CN/docs/Web/API/Blob
	
	# 构造函数
		Blob(blobParts[, options])

			blobParts
				* 数组类型, 数组中的每一项连接起来构成Blob对象的数据
				* 数组中的每项元素可以是ArrayBuffer(二进制数据缓冲区), ArrayBufferView,Blob,DOMString。或其他类似对象的混合体。
			
			options： 可选项，字典格式类型，可以指定如下两个属性：
				* type，默认值为""，它代表了将会被放入到blob中的数组内容的MIME类型。
				* endings， 默认值为"transparent"，用于指定包含行结束符\n的字符串如何被写入。 它是以下两个值中的一个： "native"，表示行结束符会被更改为适合宿主操作系统文件系统的换行符； "transparent"，表示会保持blob中保存的结束符不变。

	
	# 属性（都是只读的）
		size
			* Blob 对象中所包含数据的大小（字节）

		type
			* 一个字符串，表明该 Blob 对象所包含数据的 MIME 类型。如果类型未知，则该值为空字符串。
	
	# 实例方法
		slice([start[, end[, contentType]]])
			* 返回一个新的 Blob 对象, 包含了源 Blob 对象中指定范围内的数据

			start 可选
				* 这个参数代表 Blob 里的下标, 表示第一个会被会被拷贝进新的 Blob 的字节的起始位置
				* 如果传入的是一个负数，那么这个偏移量将会从数据的末尾从后到前开始计算。举例来说, -10 将会是  Blob 的倒数第十个字节。
				* 它的默认值是0, 如果传入的start的长度大于源 Blob 的长度, 那么返回的将会是一个长度为0并且不包含任何数据的一个 Blob 对象
				
			end 可选
				* 这个参数代表的是 Blob 的一个下标, 这个下标-1的对应的字节将会是被拷贝进新的Blob 的最后一个字节
				* 如果你传入了一个负数, 那么这个偏移量将会从数据的末尾从后到前开始计算。举例来说， -10 将会是 Blob 的倒数第十个字节。
				* 它的默认值就是它的原始长度(size).

			contentType 可选
				* 给新的 Blob 赋予一个新的文档类型。这将会把它的 type 属性设为被传入的值。它的默认值是一个空的字符串。
	

		stream()
			* 返回一个能读取blob内容的 ReadableStream
		
		text()
			* 返回一个promise且包含blob所有内容的UTF-8格式的 USVString
		
		arrayBuffer()
			* 返回一个promise且包含blob所有内容的二进制格式的 ArrayBuffer 
		
