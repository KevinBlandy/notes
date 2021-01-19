
# Protobuf
	* 一种高性能的序列化协议

# 参考
	https://colobu.com/2017/03/16/Protobuf3-language-guide
	https://github.com/protocolbuffers/protobuf
	https://blog.csdn.net/u011518120/article/details/54604615
	https://skyao.gitbooks.io/learning-proto3/


# 指定版本号
	syntax
		* 必须定义在第一行，指定版本号
			syntax = "proto3";
		* 如果不指定，默认使用版本：2.x

# 属性的规则
	* 属性的规则, 可以是: 
		单数: 一个定义良好的消息可以有0个或1个此字段(但是不能超过1个), 默认选项不需要声明
		重复: 这个字段可以在定义良好的消息中重复任意次(包括0次).重复值的顺序将被维持原状. 类似于 list 数据类型

	repeated
		* 可以出现0次或者N次, 重复的值的顺序会被保留
		* 由于一些历史原因，默认情况下, 数值类型的可重复(repeated)字段的编码性能没有想象中的好
		* 应该在其后用特殊选项 [packed=true] 来申明以获得更高效的编码
			repeated int32 samples = 4 [packed=true];
		
		* proto3已经默认使用了, 不需要手动声明
		


# 字段编号
	* 每个字段都有一个唯一的编号
	* 这些字段号用于标识消息二进制格式的字段, 在Message使用(序列化/反序列化)的过程中, 不能修改

	* 它的取值范围是: 1 - 536870911
	* 但是不能使用 19000 - 19999, 这段儿protobuf保留使用的编号段儿

	* 范围为1到15的字段编号需要一个字节来编码, 包括字段编号和字段的类型
	* 16到2047之间的字段编号占用两个字节
	* 因此应该为经常出现的消息元素保留数字1到15, 为将来可能添加的频繁出现的元素留出一些空间

# 在一个.proto文件中可以定义多个消息类型
	* 在定义多个相关的消息的时候

# 注释
	* 双斜杠注释

# 保留标识符
	* 就是标识一些保留的字段名称或者字段编号, 警告使用它们的开发者, 尽量不要使用这些命名或者字段编号
		reserved 2, 15, 9 to 11;
		reserved "foo", "bar";
	
	* 可以在同一行同时指定多个, 使用逗号分隔。如果是指定保留的字段编号, 还支持使用 to 关键字来表示一个区间
	* 同一行中不能同时声明字段名称和字段编号

# 导入其他文件的message
	* 直接导入, 默认只会导入指定文件中的定义的message，指定文件中的 import 不会被导入
		import "myproject/other_protos.proto";
	
	* 依赖导入, 可以在当前文件声明 public 导入其他的proto文件, 当前文件被 import 的时候, 会把 public 声明的文件也一起导入
		import public "new.proto";
	
	* 通过在编译器命令行参数中使用 -I/--proto_pathprotocal 编译器会在指定目录搜索要导入的文件
	* 如果没有给出标志，编译器会搜索编译命令被调用的目录。通常只要指定proto_path标志为你的工程根目录就好。并且指定好导入的正确名称就好。


# 消息的嵌套定义
	* 消息可以嵌套的定义多层
		message Outer {                  // Level 0
		  message MiddleAA {  // Level 1
			message Inner {   // Level 2
			  int64 ival = 1;
			  bool  booly = 2;
			}
		  }
		  message MiddleBB {  // Level 1
			message Inner {   // Level 2
			  int32 ival = 1;
			  bool  booly = 2;
			}
		  }
		}

# Any
	* Any 类型允许在没有某些消息类型的.proto 定义时，像使用内嵌的消息类型一样使用它来定义消息类型的字段
	* 一个 Any 类型的消息是一个包含任意字节数的序列化消息，拥有一个 URL 地址作为全局唯一标识符来解决消息的类型。
	* 为了使用 Any 类型的消息，需要import google/protobuf/any.proto
		import "google/protobuf/any.proto";

		message Messae {
			int32 timestamp = 1;
			bool success = 2;
			string message = 3;
			repeated google.protobuf.Any data = 4;
			google.protobuf.Any foo = 5;
		}

	* Any 消息类型的默认 URL 是: type.googleapis.com/packagename.messagename
	* 不同的语言实现都会支持运行库帮助通过类型安全的方式来封包或解包 Any 类型的消息。
	* 在 Java 语言中，Any 类型有专门的访问函数 pack()和unpack()

# OneOf
	* 消息中定义了很多字段，而且最多每次只能有一个字段被设置赋值，那么可以利用 Oneof 特性来实现这种行为并能节省内存。
	* Oneof 字段除了拥有常规字段的特性之外，所有字段共享一片 oneof 内存，而且每次最多只能有一个字段被设置赋值。
	& 设置 oneof组中的任意一个成员的值时，其它成员的值被自动清除。
	* 可以用 case()或 WhickOneof()方法检查 oneof 组中哪个成员的值被设置了
		message SampleMessage {
			oneof test_oneof {
				  string name = 4;
				  SubMessage sub_message = 9;
			  }
		}
	
	* oneof 的字段可以使用任何类型的字段，但是不要使用 repeated 可重复字段。

	
# Map
	* 语法
		map<key_type, value_type> map_field = N;

		key_type 可以任意整数类型或字符串类型(除浮点类型或 bytes 类型意外的任意标准类型 )
		value_Type 可以是任意类型
	
	* map 类型的字段不可重复(不能用 repeated 修饰)
	* 在传输介质上, 下面的代码等效于 map 语法的实现
		message MapFieldEntry {
			key_type key = 1;
			value_type value = 2;
		}
		repeated MapFieldEntry map_field = N;
	* 因此, 即使目前 ProtoBuf 不支持 map 可重复的特性依然可以用上面这种(变通的)方式来处理


# 包
	* 可以在.proto 文件中选择使用 package 说明符 避免 ProtoBuf 消息类型之间的名称冲突(命名空间)
		package foo.bar;
	
	* 在 Java 里面, 除非在 .proto 文件中显示声明了 option java_package ,否则这个包名会被 Java 直接采用

	* 包和名称解析, 最内层的最先被查找, 然后是次内层 ,以此类推
	* 每个包对于其父包来说都是"内部"的以一个'.'(英文句点)开头的包和名称 
		例如(.foo.bar.Baz)表示从最外层开始查找
	
	
		


