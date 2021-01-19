----------------------------
Java 对应的数据类型
----------------------------
	# 数据对应
		.proto Type	Java Type
		double		double
		float		float
		int32		int
		int32		int
		int64		long
		bool		boolean
		string		String
		bytes		ByteString
	
	# 数据类型详情
		double			double
		float			float
		int32			int
			* 使用变长编码, 对于负值的效率很低
			* 如果字段有可能有负值, 请使用 sint64 替代

		uint32			int
			* 变长字段

		uint64			long
			* 变长字段

		sint32			int
			* 使用变长编码, 这些编码在负值时比 int32 高效的多

		sint64			long
			* 使用变长编码, 有符号的整型值编码时比通常的int64高效

		fixed32			int
			* 总是4个字节, 如果数值总是比总是比228大的话, 这个类型会比uint32高效

		fixed64			long
			* 总是8个字节, 如果数值总是比总是比256大的话, 这个类型会比uint64高效

		sfixed32		int
			* 总是4个字节
		sfixed64		long
			* 总8个字节
		bool			boolean
		string			String
			* 一个字符串必须是UTF-8编码或者 7-bit ASCII编码的文本
		bytes			ByteString
			
	# 默认值
		* 当消息被解析时, 如果被编码的消息没有包含特定的简单元素, 被解析的对象对应的字段被设置为默认值. 默认值是和类型有关的:
			对于strings, 默认值是空字符串(注, 是"", 而不是null)
			对于bytes, 默认值是空字节(注, 应该是byte[0], 注意这里也不是null)
			对于boolean, 默认值是false.
			对于数字类型, 默认值是0.
			对于枚举, 默认值是第一个定义的枚举值, 而这个值必须是0.
			对于消息字段, 默认值是null.
			对于重复字段, 默认值是空(通常都是空列表)

		* 注意: 对于简单字段, 当消息被解析后, 是没有办法知道这个字段到底是有设置值然后恰巧和默认值相同(例如一个boolean设置为false)还是这个字段没有没有设置值而取了默认值. 
		* 例如, 不要用一个boolean值然后当设置为false时来切换某些行为, 而你又不希望这个行为默认会发生. 
		* 同样请注意: 如果一个简单消息字段被设置为它的默认值, 这个值不会被序列化.
			
----------------------------
Java 枚举
----------------------------
	# 每个枚举类型必须将其第一个类型映射为0
		* 必须有有一个0值，可以用这个0值作为默认值
		* 这个零值必须为第一个元素，为了兼容proto2语义，枚举类的第一个值总是默认值。
	
	# 如果需要允许多个枚举实例有相同的值，需要在 enum 中添加配置:option allow_alias = true;
		enum EnumAllowingAlias {
			option allow_alias = true;
			UNKNOWN = 0;
			STARTED = 1;
			RUNNING = 1;
		}
	
	# 枚举常量必须在32位整型值的范围内
	# 枚举可以定义在 proto 文件的最外层, 或者定义在 Message 中
		* 可以通过 MessageType.EnumType 导航使用
	
