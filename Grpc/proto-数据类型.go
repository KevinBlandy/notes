---------------------------------
标量类型
---------------------------------
	# 标量类型
		Protobuf	Java		Go
		-------------------------------
		double		double		float64
		float		float		float32
		int32		int			int32
			* 使用变长编码。对负数的编码效率低，如果字段可能出现负值，请使用 sint32 代替。
		int64		long		int64
			* 使用变长编码。对负数的编码效率低，如果的字段可能有负值，请使用 sint64 代替。
		uint32		int			uint32
			* 使用变长编码。
		uint64		long		uint64
			* 使用变长编码。
		sint32		int			int32
			* 使用变长编码。有符号 int 值。与普通 int32 相比，能更有效地编码负数。
		sint64		long		int64
			* 使用变长编码。有符号 int 值。与普通 int64 相比，能更有效地编码负数。
		fixed32		int			uint32
			* 定长 4 个字节。如果数值经常大于 228，比 uint32 更有处理效率。
		fixed64		long		uint64
			* 定长 8 个字节。如果数值经常大于 256，比 uint64 更有处理效率。
		sfixed32	int			int32
			* 定长 4 个字节
		sfixed64	long		int64
			* 定长 8 个字节
		bool		boolean		bool
		string		String		string
			* 包含 UTF-8 编码或 7 位 ASCII 文本，长度不能超过 2^32（大约 4G）。
		bytes		ByteString	[]byte
			* 字节数据，长度不能超过 2^32（大约 4G）。

		
		* 变长编码：在序列化时会根据值的大小来决定使用多少个字节
		
		* 默认值
			* 对于字符串，默认值为空字符串 ""。
			* 对于字节，默认值是空字节 []。
			* 对于布尔类型，默认值为 false。
			* 对于数字类型，默认值为 0。
			* 对于 message  字段，不设置字段。其确切值取决于语言。
			* 对于枚举，默认值是第一个定义的枚举值，该值必须为 0。 
			* 重复字段的默认值为空（在相应语言中通常为空列表）。
			* map 字段的默认值为空（在相应语言中通常为 empty）。
		

		* 对于 string 和 bytes 来说，只要字节是有效的 UTF-8 就兼容。
	
	# oneof
		* oneof 用于定义多个字段，但只能设置其中的一个
		* oneof  中的所有字段共享内存，且最多只能同时设置一个字段，设置 oneof 中的任何成员都会自动清除所有其他成员。
	
			message Member {
			  int64  id = 1;
			  // identify 字段只能设置 code 或者是 number 字段
			  oneof identify  {
				string code = 2;
				int64 number = 3;
			  }
			}	
			
			// go ------------------------------

			// 创建原始对象
			v := &proto.Member{}

			// 设置某个 OneOf 属性
			code := proto.Member_Code{Code: "1024"}
			number := proto.Member_Number{Number: 2048}
		
			// 设置给方法
			v.Identify = &number
			v.Identify = &code // 覆盖
			
			// 读取的适合根据 switch 判断
			switch v.Identify.(type) {
			case *proto.Member_Number:
				fmt.Println(v.Identify.(*proto.Member_Number).Number)
			case *proto.Member_Code:
				fmt.Println(v.Identify.(*proto.Member_Code).Code)
			default:
				panic("undefined")
			}

		* oneof 中的编号也不能重复
		* oneof 中不能使用 repeated\map 等类型




	# map
		* 定义键值对，指定 key / value 类型

			message Member {
			  int64  id = 1;
			  map<string, string> meta = 2;
			}

			// Go ------
			v := &proto.Member{}
			v.Meta["_ser"] = "ser"
		
		* key 对于基础类型来说，可以是数值或者是字符串类型（浮点数和字节数组都不行）。
		* key 也不能是枚举或另一个 message 
		* value 则任意，除了 map（map 不能嵌套）



