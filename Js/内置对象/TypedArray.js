----------------------------
TypedArray
----------------------------
	# 这是一堆 “类型化缓冲区” 的的统称，描述了底层二进制数据缓冲区的类数组视图。
	
	# 'TypedArray' 并不是一个具体的类，而是 N 个静态类型数据的 Array 的统称，他们的方法，构造器基本一样。
		* 也就是说，下文所有的 TypedArray 需要替换为下面的任何一个构造函数。

	# 类型如下
		类型				值范围										字节大小	描述					Web IDL 类型	等价的 C 类型
		Int8Array			-128 到 127									1			8 位有符号整型（补码）	byte	int8_t
		Uint8Array			0 到 255									1			8 位无符号整型	octet	uint8_t
		Uint8ClampedArray	0 到 255									1			8 位无符号整型（一定在 0 到 255 之间）	octet	uint8_t
			* 允许任何方向溢出。超出最大值255的值会被向下舍入为255，而小于最小值0的值会被向上舍入为0。
		Int16Array			-32768 到 32767								2			16 位有符号整型（补码）	short	int16_t
		Uint16Array			0 到 65535									2			16 位无符号整型	unsigned short	uint16_t
		Int32Array			-2147483648 到 2147483647					4			32 位有符号整型（补码）	long	int32_t
		Uint32Array			0 到 4294967295								4			32 位无符号整型	unsigned long	uint32_t
		Float32Array		-3.4E38 到 3.4E38 并且 1.2E-38 是最小的正数	4			32 位 IEEE 浮点数（7 位有效数字，例如 1.234567）	unrestricted float	float
		Float64Array		-1.8E308 到 1.8E308 并且 5E-324 是最小的正数8			64 位 IEEE 浮点数（16 位有效数字，例如 1.23456789012345）	unrestricted double	double
		BigInt64Array		-263 到 263 - 1								8			64 位有符号整型（补码）	bigint	int64_t (signed long long)
		BigUint64Array		0 到 264 - 1								8			64 位无符号整型	bigint	uint64_t (unsigned long long)
	
	# 构造函数
		new TypedArray()
		new TypedArray(length)
		new TypedArray(typedArray)
		new TypedArray(object)

		new TypedArray(buffer)
		new TypedArray(buffer, byteOffset)
		new TypedArray(buffer, byteOffset, length)

			typedArray
				* 当使用 TypedArray 子类的实例调用时，typedArray 会被拷贝到一个新的类型数组中。
				* 对于非 bigint TypeedArray 构造函数，typedArray 参数仅可以是非 bigint 类型（例如 Int32Array）。
				* 同样，对于 bigint TypedArray 构造函数（BigInt64Array 或 BigUint64Array），typedArray 参数仅可以是 bigint 类型。
				* typedArray 中的每个值在拷贝到新数组之前都转换为构造函数的相应类型。新的类型化数组的长度与 typedArray 参数的长度相同。
			
			object
				* 当使用的不是 TypedArray 实例的对象调用时，将以与 TypedArray.from() 方法相同的方式创建一个新的类型化数组。
			
			length
				* 可选，当使用非对象调用时，该参数将被视为指定类型化数组长度的数字。
				* 在内存中创建一个内部数组缓冲区，大小长度乘以 `BYTES_PER_ELEMENT` 字节，用 0 填充。
				* 省略所有参数，等同于使用 0 作为参数。
			
			buffer、byteOffset（可选）、length（可选）
				* 当使用 ArrayBuffer 或 SharedArrayBuffer 实例以及可选的 byteOffset 和 length 参数调用时，将创建一个新的指定缓冲区的类型化数组视图。
				* byteOffset 和 length 参数指定类型化数组视图将暴露的内存范围。
				* 如果忽略这两个参数，则是整个视图的所有 buffer；如果仅忽略 length，则是从 byteOffset 开始的 buffer 剩余部分的视图。
	
	# 类似于数组
		* 可以使用索引 [] 访问元素
		* 支持 for-of 迭代
			let arr = new Int32Array(4);
			for (const val of arr){
				console.log(val)
			}
		
	
	# 初始化 Demo
		* 使用数组初始化
			let arr = new Int32Array([0xFF, 1, 2, 3]);
			console.log(arr.length)             // 4
			console.log(arr.buffer.byteLength)  // 16
			console.log(arr);                   // 255, 1, 2, 3
			console.log(arr[arr.length - 1])    // 3
	
		* 指定长度，初始化为0 
			let arr = new Int32Array(4);
			console.log(arr.length)             // 4
			console.log(arr.buffer.byteLength)  // 16
			console.log(arr);                   // 0, 0, 0, 0
			console.log(arr[arr.length - 1])    // 0

	# 读写 Demo

----------------------------
this
----------------------------

	TypedArray.prototype.buffer
	TypedArray.prototype.byteLength
	TypedArray.prototype.byteOffset
		* 和 DataView 一样

	TypedArray.prototype.length
		* 当前类型数组的长度，注意不是字节长度。而是这种数据类型在数组中的长度
			let buffer = new ArrayBuffer(Int32Array.BYTES_PER_ELEMENT * 4);
			console.log(`buffer length ${buffer.byteLength}`);      // buffer length 16
			let intArr = new Int32Array(buffer);
			console.log(`intArr length ${intArr.length}`);          // intArr length 4

	TypedArray.prototype[@@iterator]()
	TypedArray.prototype.at()
	TypedArray.prototype.copyWithin()
	TypedArray.prototype.entries()
	TypedArray.prototype.every()
	TypedArray.prototype.fill()
	TypedArray.prototype.filter()
	TypedArray.prototype.find()
	TypedArray.prototype.findIndex()
	TypedArray.prototype.findLast()
	TypedArray.prototype.findLastIndex()
	TypedArray.prototype.forEach()
	TypedArray.prototype.includes()
	TypedArray.prototype.indexOf()
	TypedArray.prototype.join()
	TypedArray.prototype.keys()
	TypedArray.prototype.lastIndexOf()
	TypedArray.prototype.map()
	TypedArray.prototype.reduce()
	TypedArray.prototype.reduceRight()
	TypedArray.prototype.reverse()
	TypedArray.prototype.set(array, targetOffset)
		* 从提供的数组或定型数组中把值复制到当前定型数组中指定的索引位置
			array
				* 拷贝数据的源数组，溢出会抛出错误
			
			offset
				* 可选，从什么地方开始写入
				* 如果忽略该参数，则默认为 0
			
		* demo
			
			let arr = new Int32Array(4);

			arr.set([1024 * 1, 1024 * 2], 0)
			arr.set([1024 * 4], 2)
			arr.set([1024 * 8], 3)

			console.log(Array.from(arr));       // [1024, 2048, 4096, 8192]
			console.log(arr.buffer.byteLength); // 16
			console.log(arr.length);            // 4

	TypedArray.prototype.slice()
	TypedArray.prototype.some()
	TypedArray.prototype.sort()
	TypedArray.prototype.subarray(begin, end)
		* 返回一个新的、基于相同 ArrayBuffer、元素类型也相同的类型化数组（新的 TypedArray 对象）。开始的索引将会被包括，而结束的索引将不会被包括。

			begin 
				* 可选，元素开始的索引，开始索引的元素将会被包括。若该值没有传入，将会返回一个拥有全部元素的数组。
			end
				* 可选，元素结束的索引，结束索引的元素将不会被包括。若该值没有传入，从 begin 所指定的那一个元素到数组末尾的所有元素都将会被包含进新数组中。
		
		* demo
			let arr = new Int32Array([1024 << 0, 1024 << 1, 1024 << 2, 1024 << 3]); 
			console.log(Array.from(arr)); // [1024, 2048, 4096, 8192]

			let newArr = arr.subarray(1, 3);

			console.log(Array.from(newArr));  // [2048, 4096]
			console.log(newArr.length);                  // 2
			console.log(newArr.buffer === arr.buffer);  // true

	TypedArray.prototype.toLocaleString()
	TypedArray.prototype.toReversed()
	TypedArray.prototype.toSorted()
	TypedArray.prototype.toString()
	TypedArray.prototype.values()
	TypedArray.prototype.with()

----------------------------
static
----------------------------

	TypedArray[@@species]
		* 返回类型化数组的构造器。

	TypedArray.BYTES_PER_ELEMENT
		* 代表了强类型数组中每个元素所占用的字节数。
	
	TypedArray.from(arrayLike, mapFn, thisArg)
		* 一个类数组或者可迭代对象中创建一个新类型数组。这个方法和 Array.from() 类似。

			arrayLike
				* 想要转换为类型数组的类数组或者可迭代对象。
			mapFn
				* 可选参数。如果指定了该参数，则最后生成的类型数组会经过该函数的加工处理后再返回。
				* mapFn(x)
			thisArg
				* 可选参数。执行 mapFn 函数时 this 的值。

	TypedArray.of(element0, element1, /* ... ,*/ elementN)
		* 建一个具有可变数量参数的新类型数组。此方法几乎与 Array.of() 相同。

			elementN
				* 创建类型数组的元素。
			

			let intArr = Int32Array.of(1, 2, 3);
			intArr.forEach(i => console.log(i)) // 1 2 3
			
		*  和 TypedArray.of() 之间的一些细微区别：
			* 如果传递给 TypedArray.of 的这个值不是构造函数，TypedArray.of 将抛出一个TypeError ，其中 Array.of 默认创建一个新的 Array。
			* TypedArray.of 使用 [[Put]] 其中 Array.of 使用 [[DefineProperty]]。因此，当使用Proxy 对象时，它调用 handler.set 创建新的元素，而不是 handler.defineProperty。
		

