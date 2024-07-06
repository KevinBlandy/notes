-------------------------------
TextEncoder
-------------------------------
	# TextEncoder 接受码位流作为输入，并提供 UTF-8 字节流作为输出。

		https://developer.mozilla.org/zh-CN/docs/Web/API/TextEncoder
	
	# 构造函数
		new TextEncoder();
			* 返回一个新创建的 TextEncoder 对象，该对象将生成具有 UTF-8 编码的字节流。


-------------------------------
this
-------------------------------
	encoding
	encode(string)
		* 接受一个字符串作为输入，返回一个对参数中给定的文本的编码后的 Uint8Array，编码的方法通过 TextEncoder 对象指定。

	encodeInto(string, uint8Array)
		* 接受一个要编码的字符串和一个目标 Uint8Array，将生成的 UTF-8 编码的文本放入目标数组中，并返回一个指示编码进度的字典对象。
		* 这相比于旧的 encode() 方法性能更高——尤其是当目标缓冲区是 WASM 堆视图时。

			string
				* 一个字符串，包含将要编码的文本。

			uint8Array
				* 一个 Uint8Array 对象实例，用于将生成的 UTF-8 编码的文本放入其中。
		
		* 返回一个对象，包含两个参数。
			read
				* 已经从源字符串中转换为 UTF-8 的，使用 UTF-16 编码的码元数。如果 uint8Array 没有足够的空间，则此值可能小于 string.length。

			written
				* 在目标 Uint8Array 中修改的字节数。写入的字节确保形成完整的 UTF-8 字节序列。

-------------------------------
static
-------------------------------
