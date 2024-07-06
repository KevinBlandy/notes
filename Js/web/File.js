--------------------
File
--------------------
	# extends Blob 
		* 提供有关文件的信息，并允许网页中的 JavaScript 访问其内容。

	# 构造函数
		new File(fileBits, fileName, options)
			
			fileBits
				* 一个可迭代对象，例如一个具有 ArrayBuffer、TypedArray、DataView、Blob、字符串或任何此类元素的组合的数组，将被放入 File 内。
				* 注意，这里的字符串被编码为 UTF-8，与通常的 JavaScript UTF-16 字符串不同。
			
			fileName
				* 表示文件名或文件路径的字符串。
			
			options
				* 可选，包含文件可选属性的选项对象。可用选项如下：

				type 
					* 可选，表示将放入文件的内容的 MIME 类型的字符串。默认值为 ""。

				endings
					* 可选，如果数据是文本，如何解释内容中的换行符（\n）。
					* 默认值 transparent 将换行符复制到 blob 中而不更改它们。要将换行符转换为主机系统的本机约定，指定值为 native。

				lastModified
					* 可选，一个数字，表示 Unix 时间纪元与文件上次修改时间之间的毫秒数。
					* 默认值为调用 Date.now() 返回的值。

--------------------
this
--------------------
	lastModified
	lastModifiedDate
		* 最后修改日期的字符串

	name
		* 文件名称
	webkitRelativePath


--------------------
static
--------------------
