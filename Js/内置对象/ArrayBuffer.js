----------------------------
ArrayBuffer
----------------------------
	# 表示通用的原始二进制数据缓冲区。

		* 不能仅通过对 ArrayBuffer 的引用就读取或写入其内容。
		* 要读取或写入 ArrayBuffer，就必须通过视图（DataView）。视图有不同的类型，但引用的都是 ArrayBuffer 中存储的二进制数据。

	
	# 构造函数
		new ArrayBuffer(length, options)

			length
				* 要创建的数组缓冲区的大小（以字节为单位）。
			
			options
				* 可选，配置对象，可以包含以下属性。

				maxByteLength
					* 可选，数组缓冲区可以调整到的最大大小，以字节为单位。
			
			* 不合理的参数会抛出异常。

----------------------------
this
----------------------------

	ArrayBuffer.prototype.byteLength
		* ArrayBuffer 的大小，以字节为单位。
		* 它在构造时确定，并且只有在 ArrayBuffer 可调整大小的情况下才能通过 ArrayBuffer.prototype.resize() 方法进行改变。

	ArrayBuffer.prototype.detached
	ArrayBuffer.prototype.maxByteLength
		* 只读，ArrayBuffer 可以调整到的最大字节长度。它在构造时确定，并且无法更改
	
	ArrayBuffer.prototype.resizable
		* 只读。如果 ArrayBuffer 可调整大小，则返回 true，否则返回 false。

	ArrayBuffer.prototype.resize(newLength)
		* 将 ArrayBuffer 调整为指定的大小，以字节为单位。无返回值。
			newLength
				* 要调整到的新的长度，以字节为单位。
		
		* 如果 ArrayBuffer 已分离或不可调整大小，则抛出 TypeError 错误。
		* 如果 newLength 大于该 ArrayBuffer 的 maxByteLength，则抛出 RangeError 错误。

	ArrayBuffer.prototype.slice(start, end)
		* 法返回一个新的 ArrayBuffer 实例，其包含原 ArrayBuffer 实例中从 begin 开始（包含）到 end 结束（不含）的所有字节的副本。
			start
				* 可选，开始索引。
			
			end
				* 可选，结束索引，结果不包含。

	ArrayBuffer.prototype.transfer(newByteLength)
		* 创建一个内容与该缓冲区相同的新 ArrayBuffer 实例，然后将当前缓冲区分离。
			newByteLength 
				* 可选，新的 ArrayBuffer 的 byteLength。默认为当前 ArrayBuffer 的 byteLength。
				* 如果 newByteLength 小于当前 ArrayBuffer 的 byteLength，“溢出”的字节将被丢弃。
				* 如果 newByteLength 大于当前 ArrayBuffer 的 byteLength，剩下的字节将用零填充。
				* 如果当前的 ArrayBuffer 是可调整大小的，newByteLength 一定不能大于其 maxByteLength。


	ArrayBuffer.prototype.transferToFixedLength(newByteLength)
		* 创建一个不可调整大小的新 ArrayBuffer 对象，该对象与此缓冲区具有相同的字节内容，然后将此缓冲区分离。
			newByteLength
				* 新的 ArrayBuffer 的 byteLength。默认为此 ArrayBuffer 的 byteLength。
				* 如果 newByteLength 小于此 ArrayBuffer 的 byteLength，则 “溢出的” 字节将被丢弃。
				* 如果 newByteLength 大于此 ArrayBuffer 的 maxByteLength，则多余的字节用零填充。


----------------------------
static
----------------------------
	ArrayBuffer[@@species]

	ArrayBuffer.isView(value)
		* 判断传入值是否是 ArrayBuffer 视图之一，例如类型化数组（TypedArray）对象或 DataView。
