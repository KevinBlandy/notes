--------------------------------
ArrayBuffer						|
--------------------------------
	1,ArrayBuffer 对象
	2,TypedArray 视图
	3,复合视图
	4,DataView 视图
	5,二进制数组的应用
	6,SharedArrayBuffer
	7,Atomics 对象

--------------------------------
简介							|
--------------------------------
	# ArrayBuffer对象,TypedArray视图和DataView视图是 JavaScript 操作二进制数据的一个接口
	# 二进制数组由三类对象组成
		1,ArrayBuffer
			* 代表内存之中的一段二进制数据,可以通过"视图"进行操作
			* "视图"部署了数组接口,这意味着,可以用数组的方法操作内存
		
		2,TypedArray
			* 视图,共包括 9 种类型的视图,比如Uint8Array(无符号 8 位整数)数组视图, Int16Array(16 位整数)数组视图, Float32Array(32 位浮点数)数组视图等等
		
		3,DataView
			* 视图,可以自定义复合格式的视图,比如第一个字节是 Uint8(无符号 8 位整数),第二、三个字节是 Int16(16 位整数),第四个字节开始是 Float32(32 位浮点数)等等,此外还可以自定义字节序
		

	# ArrayBuffer对象代表原始的二进制数据
	# TypedArray 视图用来读写简单类型的二进制数据
	# DataView视图用来读写复杂类型的二进制数据

	# TypedArray 视图支持的数据类型一共有 9 种(DataView视图支持除Uint8C以外的其他 8 种)
		数据类型	字节长度	含义					对应的 C 语言类型
		Int8	1		8 位带符号整数					signed char
		Uint8	1		8 位不带符号整数				unsigned char
		Uint8C	1		8 位不带符号整数(自动过滤溢出)	unsigned char
		Int16	2		16 位带符号整数					short
		Uint16	2		16 位不带符号整数				unsigned short
		Int32	4		32 位带符号整数					int
		Uint32	4		32 位不带符号的整数				unsigned int
		Float32	4		32 位浮点数						float
		Float64	8		64 位浮点数						double
	
	# 二进制数组并不是真正的数组,而是类似数组的对象

	# 很多浏览器操作的 API,用到了二进制数组操作二进制数据,下面是其中的几个
		File API
		XMLHttpRequest
		Fetch API
		Canvas
		WebSockets

--------------------------------
ArrayBuffer						|
--------------------------------
	# ArrayBuffer对象代表储存二进制数据的一段内存,它不能直接读写
		* 只能通过视图(TypedArray视图和DataView视图)来读写
		* 视图的作用是以指定格式解读二进制数据
	
	# ArrayBuffer也是一个构造函数,可以分配一段可以存放数据的连续内存区域
		const buffer = new ArrayBuffer(32);
		* 生成了一段 32 字节的内存区域,每个字节的值默认都是 0
		* ArrayBuffer构造函数的参数是所需要的内存大小(单位字节)
	
	# 读写ArrayBuffer内容,需要为它指定视图

		* DataView视图的创建,需要提供ArrayBuffer对象实例作为参数

		const buf = new ArrayBuffer(32);
		const dataView = new DataView(buf);
		let data = dataView.getUint8(0)
		console.log(data);		//0
			
			* 32 字节的内存,建立DataView视图,然后以不带符号的 8 位整数格式,从头读取 8 位二进制数据,结果得到 0,
			* 原始内存的ArrayBuffer对象,默认所有位都是 0
	
	# TypedArray 视图,与DataView视图的一个区别是,它不是一个构造函数,而是一组构造函数,代表不同的数据格式
		const x1 = new Int32Array(buffer);
		const x2 = new Uint8Array(buffer);

		* 32 位带符号整数(Int32Array构造函数)和 8 位不带符号整数(Uint8Array构造函数)
		* 由于两个视图对应的是同一段内存,一个视图修改底层内存,会影响到另一个视图
	
		* TypedArray 视图的构造函数,除了接受ArrayBuffer实例作为参数
		* 还可以接受普通数组作为参数,直接分配内存生成底层的ArrayBuffer实例,并同时完成对这段内存的赋值
			const typedArray = new Uint8Array([0,1,2]);

			typedArray.length	// 3
			typedArray[0] = 5;
			typedArray 			// [5, 1, 2]
			
			* TypedArray 视图的Uint8Array构造函数,新建一个不带符号的 8 位整数视图
			* Uint8Array直接使用普通数组作为参数,对底层内存的赋值同时完成
	
	# ArrayBuffer.prototype.byteLength()
		* ArrayBuffer实例的byteLength属性,返回所分配的内存区域的字节长度
			const buffer = new ArrayBuffer(32);
			buffer.byteLength
			// 32
		* 如果要分配的内存区域很大,有可能分配失败(因为没有那么多的连续空余内存)所以有必要检查是否分配成功
			if (buffer.byteLength === [分配的内存大小]) {
				// 成功
			} else {
				// 失败
			}

	# ArrayBuffer.prototype.slice()
		* slice方法,允许将内存区域的一部分,拷贝生成一个新的ArrayBuffer对象
			const buffer = new ArrayBuffer(8);
			const newBuffer = buffer.slice(0, 3);

			* 拷贝buffer对象的前 3 个字节(从 0 开始,到第 3 个字节前面结束),生成一个新的ArrayBuffer对象
			* slice方法其实包含两步,第一步是先分配一段新内存,第二步是将原来那个ArrayBuffer对象拷贝过去

		* slice方法接受两个参数,第一个参数表示拷贝开始的字节序号(含该字节),第二个参数表示拷贝截止的字节序号(不含该字节)
		* 如果省略第二个参数,则默认到原ArrayBuffer对象的结尾
		* slice方法,ArrayBuffer对象不提供任何直接读写内存的方法,只允许在其上方建立视图,然后通过视图读写

	# ArrayBuffer.isView()
		* 静态方法isView,返回一个布尔值,表示参数是否为ArrayBuffer的视图实例
		* 这个方法大致相当于判断参数,是否为 TypedArray 实例或DataView实例
			const buffer = new ArrayBuffer(8);
			ArrayBuffer.isView(buffer) // false

			const v = new Int32Array(buffer);
			ArrayBuffer.isView(v) // true

--------------------------------
TypedArray 视图					|
--------------------------------
	# TypedArray 视图一共包括 9 种类型,每一种视图都是一种构造函数
		Int8Array			8	位有符号整数,长度 1 个字节
		Uint8Array			8	位无符号整数,长度 1 个字节
		Uint8ClampedArray	8	位无符号整数,长度 1 个字节,溢出处理不同
		Int16Array			16	位有符号整数,长度 2 个字节
		Uint16Array			16	位无符号整数,长度 2 个字节
		Int32Array			32	位有符号整数,长度 4 个字节
		Uint32Array			32	位无符号整数,长度 4 个字节
		Float32Array		32	位浮点数,长度 4 个字节
		Float64Array		64	位浮点数,长度 8 个字节
	
	# 9 个构造函数生成的数组,统称为 TypedArray 视图
		* 它们很像普通数组,都有length属性,都能用方括号运算符([])获取单个元素
		* 所有数组的方法,在它们上面都能使用
		* 普通数组与 TypedArray 数组的差异主要在以下方面
			TypedArray 数组的所有成员,都是同一种类型
			TypedArray 数组的成员是连续的,不会有空位
			TypedArray 数组成员的默认值为 0
				* 比如,new Array(10)返回一个普通数组,里面没有任何成员,只是 10 个空位
				* new Uint8Array(10)返回一个 TypedArray 数组,里面 10 个成员都是 0
			TypedArray 数组只是一层视图,本身不储存数据,它的数据都储存在底层的ArrayBuffer对象之中
				* 要获取底层对象必须使用buffer属性
	
	# 构造函数
		* TypedArray 数组提供 9 种构造函数,用来生成相应类型的数组实例
		* 构造函数有多种用法
			1,TypedArray(buffer, byteOffset=0, length)
				* 同一个ArrayBuffer对象之上,可以根据不同的数据类型,建立多个视图
					// 创建一个8字节的ArrayBuffer
					const b = new ArrayBuffer(8);
					// 创建一个指向b的Int32视图,开始于字节0,直到缓冲区的末尾
					const v1 = new Int32Array(b);
					// 创建一个指向b的Uint8视图,开始于字节2,直到缓冲区的末尾
					const v2 = new Uint8Array(b, 2);
					// 创建一个指向b的Int16视图,开始于字节2,长度为2
					const v3 = new Int16Array(b, 2, 2);

					* 在一段长度为 8 个字节的内存(b)之上,生成了三个视图:v1、v2和v3
				* 视图的构造函数可以接受三个参数
					第一个参数(必需):视图对应的底层ArrayBuffer对象
					第二个参数(可选):视图开始的字节序号,默认从 0 开始
					第三个参数(可选):视图包含的数据个数,默认直到本段内存区域结束
			
				* byteOffset必须与所要建立的数据类型一致,否则会报错
					const buffer = new ArrayBuffer(8);
					const i16 = new Int16Array(buffer, 1);	//Uncaught RangeError: start offset of Int16Array should be a multiple of 2
					* 带符号的 16 位整数需要两个字节,所以byteOffset参数必须能够被 2 整除

				* 如果想从任意字节开始解读ArrayBuffer对象,必须使用DataView视图,因为 TypedArray 视图只提供 9 种固定的解读格式
			
			2,TypedArray(length)
				* 不通过ArrayBuffer对象,直接分配内存而生成
					const f64a = new Float64Array(8);
					f64a[0] = 10;
					f64a[1] = 20;
					f64a[2] = f64a[0] + f64a[1];
					
					* 生成一个 8 个成员的Float64Array数组(共 64 字节),然后依次对每个成员赋值
					* 这时,视图构造函数的参数就是成员的个数,可以看到,视图数组的赋值操作与普通数组的操作毫无两样
					* 视图数组的赋值操作与普通数组的操作毫无两样

			3,TypedArray(typedArray)
				* 可以接受另一个 TypedArray 实例作为参数
					const typedArray = new Int8Array(new Uint8Array(4));
					* Int8Array构造函数接受一个Uint8Array实例作为参数

				* 生成的新数组,只是复制了参数数组的值,对应的底层内存是不一样的
					* 新数组会开辟一段新的内存储存数据,不会在原数组的内存之上建立视图
						const x = new Int8Array([1, 1]);
						const y = new Int8Array(x);
						x[0] // 1
						y[0] // 1

						x[0] = 2;
						y[0] // 1
						//数组y是以数组x为模板而生成的,当x变动的时候,y并没有变动

				* 果想基于同一段内存,构造不同的视图,可以采用下面的写法
					const x = new Int8Array([1, 1]);
					const y = new Int8Array(x.buffer);		//根据底层的buffer
					x[0] // 1
					y[0] // 1

					x[0] = 2;
					y[0] // 2
			
			4,TypedArray(arrayLikeObject)
				* 构造函数的参数也可以是一个普通数组,然后直接生成 TypedArray 实例
				* 注意,这时 TypedArray 视图会重新开辟内存,不会在原数组的内存上建立视图
					const typedArray = new Uint8Array([1, 2, 3, 4]);
					* 上面代码从一个普通的数组,生成一个 8 位无符号整数的 TypedArray 实例
				
				* TypedArray 数组也可以转换回普通数组
					const normalArray = [...typedArray];
					// or
					const normalArray = Array.from(typedArray);
					// or
					const normalArray = Array.prototype.slice.call(typedArray);
	
	# 普通数组的操作方法和属性,对 TypedArray 数组完全适用
		* 注意,TypedArray 数组没有concat方法
		* 如果想要合并多个 TypedArray 数组,可以用下面这个函数
			function concatenate(resultConstructor, ...arrays) {
				let totalLength = 0;
				for (let arr of arrays) {
					totalLength += arr.length;
				}
				let result = new resultConstructor(totalLength);
				let offset = 0;
				for (let arr of arrays) {
					result.set(arr, offset);
					offset += arr.length;
				}
				return result;
			}

			concatenate(Uint8Array, Uint8Array.of(1, 2), Uint8Array.of(3, 4))
			// Uint8Array [1, 2, 3, 4]
	
	# TypedArray 数组与普通数组一样,部署了 Iterator 接口,所以可以被遍历
		let ui8 = Uint8Array.of(0, 1, 2);
		for (let byte of ui8) {
			console.log(byte);
		}
		// 0
		// 1
		// 2
	
	# 字节序 
		* 看不懂
	
	# BYTES_PER_ELEMENT 属性
		* 每一种视图的构造函数,都有一个BYTES_PER_ELEMENT属性,表示这种数据类型占据的字节数

		Int8Array.BYTES_PER_ELEMENT		// 1
		Uint8Array.BYTES_PER_ELEMENT	// 1
		Int16Array.BYTES_PER_ELEMENT	// 2
		Uint16Array.BYTES_PER_ELEMENT	// 2
		Int32Array.BYTES_PER_ELEMENT	// 4
		Uint32Array.BYTES_PER_ELEMENT	// 4
		Float32Array.BYTES_PER_ELEMENT	// 4
		Float64Array.BYTES_PER_ELEMENT	// 8
		
		* 这个属性在 TypedArray 实例上也能获取,即有TypedArray.prototype.BYTES_PER_ELEMENT
	
	# ArrayBuffer 与字符串的互相转换
		* ArrayBuffer转为字符串,或者字符串转为ArrayBuffer
		* 有一个前提,即字符串的编码方法是确定的,假定字符串采用 UTF-16 编码(JavaScript 的内部编码方式),可以自己编写转换函数
			// ArrayBuffer 转为字符串,参数为 ArrayBuffer 对象
			function ab2str(buf) {
				// 注意,如果是大型二进制数组,为了避免溢出,
				// 必须一个一个字符地转
				if (buf && buf.byteLength < 1024) {
					return String.fromCharCode.apply(null, new Uint16Array(buf));
				}
			
				const bufView = new Uint16Array(buf);
				const len =  bufView.length;
				const bstr = new Array(len);
				for (let i = 0; i < len; i++) {
					bstr[i] = String.fromCharCode.call(null, bufView[i]);
				}
				return bstr.join('');
			}
			
			// 字符串转为 ArrayBuffer 对象,参数为字符串
			function str2ab(str) {
				const buf = new ArrayBuffer(str.length * 2); // 每个字符占用2个字节
				const bufView = new Uint16Array(buf);
				for (let i = 0, strLen = str.length; i < strLen; i++) {
					bufView[i] = str.charCodeAt(i);
				}
				return buf;
			}
	
	# 溢出
		* 看不懂
	
	# TypedArray.prototype.buffer
		* TypedArray 实例的buffer属性,返回整段内存区域对应的ArrayBuffer对象
		* 该属性为只读属性

	        const a = new Float32Array(64);
	        const b = new Uint8Array(a.buffer);
			
			* 上面代码的a视图对象和b视图对象,对应同一个ArrayBuffer对象,即同一段内存
		
	# TypedArray.prototype.byteLength
	# TypedArray.prototype.byteOffset 
		* byteLength属性返回 TypedArray 数组占据的内存长度,单位为字节
		* byteOffset属性返回 TypedArray 数组从底层ArrayBuffer对象的哪个字节开始
		* 这两个属性都是只读属性

		const b = new ArrayBuffer(8);

        const v1 = new Int32Array(b);
        const v2 = new Uint8Array(b, 2);
        const v3 = new Int16Array(b, 2, 2);

        v1.byteLength // 8
        v2.byteLength // 6
        v3.byteLength // 4

        v1.byteOffset // 0
        v2.byteOffset // 2
        v3.byteOffset // 2
	
	# TypedArray.prototype.length
		* length属性表示 TypedArray 数组含有多少个成员
		* 注意将byteLength属性和length属性区分,前者是字节长度,后者是成员长度
			const a = new Int16Array(8);
			a.length		// 8
			a.byteLength	// 16

	# TypedArray.prototype.set()
		* TypedArray 数组的set方法用于复制数组(普通数组或 TypedArray 数组),也就是将一段内容完全复制到另一段内存
			const a = new Uint8Array(8);
			const b = new Uint8Array(8);
			b.set(a);

			* 上面代码复制a数组的内容到b数组,它是整段内存的复制,比一个个拷贝成员的那种复制快得多

		* set方法还可以接受第二个参数,表示从b对象的哪一个成员开始复制a对象
			const a = new Uint16Array(8);
			const b = new Uint16Array(10);
			b.set(a, 2)

			* 上面代码的b数组比a数组多两个成员,所以从b[2]开始复制
		# TypedArray.prototype.subarray()
		* subarray方法是对于 TypedArray 数组的一部分,再建立一个新的视图
			const a = new Uint16Array(8);
			const b = a.subarray(2,3);
			
			a.byteLength // 16
			b.byteLength // 2
			
			* subarray方法的第一个参数是起始的成员序号,第二个参数是结束的成员序号(不含该成员),如果省略则包含剩余的全部成员
			* 上面代码的a.subarray(2,3),意味着 b 只包含a[2]一个成员,字节长度为 2

	# TypedArray.prototype.slice()
		* TypeArray 实例的slice方法,可以返回一个指定位置的新的 TypedArray 实例
			let ui8 = Uint8Array.of(0, 1, 2);
			ui8.slice(-1)
			// Uint8Array [ 2 ]
			
			* ui8是 8 位无符号整数数组视图的一个实例
			* 它的slice方法可以从当前视图之中,返回一个新的视图实例

		* slice方法的参数,表示原数组的具体位置,开始生成新数组
		* 负值表示逆向的位置,即-1 为倒数第一个位置,-2 表示倒数第二个位置,以此类推

	# TypedArray.of()
		* TypedArray 数组的所有构造函数,都有一个静态方法of,用于将参数转为一个 TypedArray 实例
			Float32Array.of(0.151, -8, 3.7)
			//Float32Array [ 0.151, -8, 3.7 ]
		
		* 下面三种方法都会生成同样一个 TypedArray 数组
			// 方法一
			let tarr = new Uint8Array([1,2,3]);
			// 方法二
			let tarr = Uint8Array.of(1,2,3);
			// 方法三
			let tarr = new Uint8Array(3);
			tarr[0] = 1;
			tarr[1] = 2;
			tarr[2] = 3;
	
	# TypedArray.from()
		* 静态方法from接受一个可遍历的数据结构(比如数组)作为参数,返回一个基于这个结构的 TypedArray 实例
			Uint16Array.from([0, 1, 2])
			// Uint16Array [ 0, 1, 2 ]
		
		* 这个方法还可以将一种 TypedArray 实例,转为另一种
			const ui16 = Uint16Array.from(Uint8Array.of(0, 1, 2));
			ui16 instanceof Uint16Array // true
			
		* from方法还可以接受一个函数,作为第二个参数,用来对每个元素进行遍历,功能类似map方法
			Int8Array.of(127, 126, 125).map(x => 2 * x)
			// Int8Array [ -2, -4, -6 ]
		
			Int16Array.from(Int8Array.of(127, 126, 125), x => 2 * x)
			// Int16Array [ 254, 252, 250 ]
			* from方法没有发生溢出,这说明遍历不是针对原来的 8 位整数数组
			* 也就是说,from会将第一个参数指定的 TypedArray 数组,拷贝到另一段内存之中,处理之后再将结果转成指定的数组格式


--------------------------------
复合视图						|
--------------------------------
	