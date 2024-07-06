----------------------
DataView
----------------------
	# 一个可以从二进制 ArrayBuffer 对象中读写多种数值类型的底层接口，使用它时，不用考虑不同平台的字节序（endianness）问题。
		
		* DataView 完成读、写操作的前提是必须有充足的缓冲区，否则就会抛出 RangeErro。

	
	# 构造函数
		new DataView(buffer, byteOffset, byteLength)

			buffer
				* 现有的 ArrayBuffer 或 SharedArrayBuffer，用作新 DataView 对象的存储空间。
			
			byteOffset
				* 新视图要引用的上述缓冲区中第一个字节的偏移量（以字节为单位）。如果未指定，缓冲视图将从第一个字节开始。
			
			byteLength
				* 可选，字节数组中元素的个数。如果未指定，视图的长度将与缓冲区的长度一致。
	

	# 读写 Demo

		let buf = new ArrayBuffer(16);

		let view = new DataView(buf);
		
		// 在指定位置写入 int32
		view.setInt32(0, 1024 << 0);
		view.setInt32(4, 1024 << 1);
		view.setInt32(8, 1024 << 2);
		view.setInt32(12, 1024 << 3);
		
		// 从指定位置读取 int32
		console.log(view.getInt32(0));  // 1024
		console.log(view.getInt32(4));  // 2048
		console.log(view.getInt32(8));  // 4096
		console.log(view.getInt32(12)); // 8192
				
----------------------
this
----------------------
	DataView.prototype.buffer
		* 造时被 DataView 引用的 ArrayBuffer。
	
	DataView.prototype.byteLength
		* 从 ArrayBuffer 开始的字节长度。

	DataView.prototype.byteOffset
		* 从 ArrayBuffer 开始的字节偏移量。
	
	DataView.prototype.getBigUint64(byteOffset, littleEndian)
		* 从 DataView 的指定的字节偏移量位置读取 8 个字节，并将其解析为一个无符号 64 位整数（unsigned long long）。
			byteOffset
				* 表示从视图的开始位置到要读取数据位置的偏移量，以字节为单位。

			littleEndian
				* 可选，指示 64 位整数是以小端还是大端格式存储。如果值为 false 或 undefined，则以大端格式读取。

	DataView.prototype.getFloat32()
	DataView.prototype.getFloat64()
	DataView.prototype.getInt16()
	DataView.prototype.getInt32()
	DataView.prototype.getInt8()
	DataView.prototype.getUint16()
	DataView.prototype.getUint32()
	DataView.prototype.getUint8()

	DataView.prototype.setBigInt64(byteOffset, value [, littleEndian])
		* 在 byteOffset 位置的指定字节偏移处存储一个带符号的 64 位整数（long long 类型）值。
			byteOffset
				* 字节偏移量，为从视图的起始位置到数据存储位置的字节字节偏移量。
			
			value
				* 一个BigInt类型设置的数值。满足一个带符号的 64 位整数的最大可能数值是 2n ** (64n -1n) - 1n (9223372036854775807n)。
				* 当发生溢出时，将会变成负数 (-9223372036854775808n)。
			
			littleEndian
				* 可选，表示这个 64 位整数是否以little-endian 或者 big-endian格式存储。
				* 如果设置为 false 或者未指定（undefined），将会写入一个 big-endian（大端模式：高位字节排放在内存的低地址端，低位字节排放在内存的高地址端）格式的数值。

	DataView.prototype.setBigUint64()
	DataView.prototype.setFloat32()
	DataView.prototype.setFloat64()
	DataView.prototype.setInt16()
	DataView.prototype.setInt32()
	DataView.prototype.setInt8()
	DataView.prototype.setUint16()
	DataView.prototype.setUint32()
	DataView.prototype.setUint8()

----------------------
static
----------------------
