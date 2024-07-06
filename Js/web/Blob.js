-----------------
Blob
-----------------
	# 表示一个不可变、原始数据的类文件对象。它的数据可以按文本或二进制的格式进行读取，也可以转换成 ReadableStream 来用于数据操作。
	
	# 构造函数
		new Blob(blobParts, options)
		
			blobParts
				* 可选，一个可迭代对象，比如 Array，包含 ArrayBuffer、TypedArray、DataView、Blob、字符串或者任意这些元素的混合，这些元素将会被放入 Blob 中。
				* 字符串应该是格式良好的 Unicode，而单独代理项（lone surrogate）会使用和 String.prototype.toWellFormed() 相同的算法进行清理。
			
			options
				* 可选，一个可以指定以下任意属性的对象

				type
					* 将会被存储到 blob 中的数据的 MIME 类型。默认值是空字符（""）。
				endings
					* 可选，如果数据是文本，那么如何解释其中的换行符（\n）。
					* 默认值 transparent 会将换行符复制到 blob 中而不会改变它们。要将换行符转换为主机系统的本地约定，请指定值 native。

-----------------
this
-----------------

	size
	type

	arrayBuffer()
	slice(start, end, contentType)
		* 读取数据的一部分，返回一个新的 Blob 对象。
			start
				* 可选，起始字节，默认值是 0。
				* 如果传入的是一个负数，那么这个偏移量将会从数据的末尾从后到前开始计算。例如，-10 将会是 Blob 的倒数第十个字节。
				* 如果传入的 start 的长度大于源 Blob 的长度，那么返回的将会是一个长度为 0 并且不包含任何数据的一个 Blob 对象。
			
			end
				* 可选，代表 Blob 里的第一个不会被拷贝进新的 Blob 的字节的索引（换句话说，这个索引的字节不会被拷贝）。
				* 默认值就是它的原始长度 size。
				* 如果传入了一个负数，那么这个偏移量将会从数据的末尾从后到前开始计算。举例来说，-10 将会是 Blob 的倒数第十个字节。
			
			contentType
				* 可选，给新的 Blob 赋予一个新的内容类型。这将会把它的 type 属性设为被传入的值。它的默认值是一个空字符串。

	stream()
		* 返回一个 ReadableStream 对象，读取它将返回包含在 Blob 中的数据。

	text()
		* 返回一个 promise，其会兑现一个包含作为文字字符串的 blob 数据的字符串。
		* 数据总是被假定为 UTF-8 格式。

-----------------
static
-----------------
