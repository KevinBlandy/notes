---------------------------------
继承
---------------------------------
	# 注意
		* Proto3 起，不支持扩展（声明自定义选项除外）。

	# 使用 extend  关键字来扩展一个消息

		// 原始消息
		message Member {

		  // 声明可以被扩展的字段编号
		  extensions 100 to 199;

		  int64 id = 1;
		  string nickName = 2;
		}

		// 通过 Extend 关键字对其进行扩展
		extend Member {
		  string avatar = 100;
		  string timeZone = 101;
		}
		
		// Go
		// 创建原始对象
		v := &proto.Member{}

		// 设置继承消息
		pb.SetExtension(v, proto.E_Avatar, "default")
		pb.SetExtension(v, proto.E_TimeZone, "UTC+8")

		// 读取继承消息
		pb.GetExtension(v, proto.E_Avatar)
	

		* 扩展消息的字段编号不能重复，且必须要在原对象上通过 extensions 声明。
		* 编号也可以用 max 关键字来表示最大：extensions 1000 to max;
	
	# 校验声明

		* 支持对扩展进行严格的限制

			// 原始消息
			message Member {

			  // 声明可以被扩展的字段编号
			  extensions 100 to 199 [
				// 扩展声明 1
				declaration = {
				  number: 100,
				  full_name: ".avatar",
				  type: "string",
				  repeated: false
				},
				// 扩展声明 2
				declaration = {
				  number: 101,
				  full_name: ".timeZone",
				  type: "string",
				  repeated: false
				},
				// 需要对扩展进行声明校验
				verification = DECLARATION
			  ];
			  int64 id = 1;
			  string nickName = 2;
			}

			// 通过 Extend 关键字对其进行扩展
			extend Member {
			  string avatar = 100;
			  string timeZone = 101;
			}
	
	# 嵌套扩展（不建议）
		
		* 可以在消息中，扩展另一个消息
	
