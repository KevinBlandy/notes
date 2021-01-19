------------------
Java代码生成
------------------
	# 编译器为每个.proto文件输入创建一个单一的.java文件
		* .proto文件中定义的类, 以内部类的形式存在
		* 这个文件包含一个单一的outer class定义,包含一些内嵌类和静态字段


	# 生成的类
		* 实现 Message 接口, 并且被标识为 final
			public static final class Person extends com.google.protobuf.GeneratedMessageV3 implements PersonOrBuilder

		* 还会为类生成一个builder对象, builder没有任何静态方法, 仅仅用来初始化类属性
		* builer对象还会根据字段的类型不同, 提供不同的操作方法

	
	# 字段
		* 下划线会转换为驼峰
			foo_bar_baz  -> fooBarBaz

		* 如果名字有附加前缀(如 "get"), 第一个字母大写，否则小写
		
		* 编译器为每个字段生成一个包含它的字段编号的整型常量
		* 常量名是: 转换为大写并加上 _FIELD_NUMBER 字段名字
				optional int32 foo_bar = 5 -> public static final int FOO_BAR_FIELD_NUMBER = 5;
		
		
		* 重复字段生成为 List
	
	# Map字段
		* 生成为 Map , map字段默认是不能修改的
		* 可以通过Builder获取到可更改的map, 但是这个map是新的,每次调用都会生成新的map
			getMutableXXX()
		
	# Any 子弹
		* Any字段变成了 Any 对象
		* Any 类有序列化的静态api, 以及反序列化的实例api
			public static <T extends com.google.protobuf.Message> Any pack(T message)
			public static <T extends com.google.protobuf.Message> Any pack(T message, String typeUrlPrefix)
				* 打包数据

			public <T extends com.google.protobuf.Message> T unpack(java.lang.Class<T> clazz)
				* 解包数据
			public <T extends Message> boolean is(class<T> clazz);
				* 判断消息是否是指定的 Message 类实现
		
		
	# Oneof

	
		
-----------------------
Outer类的静态方法
-----------------------
	Descriptor getDescriptor()
	void registerAllExtensions(ExtensionRegistry registry)
	void registerAllExtensions(ExtensionRegistryLite registry)


-----------------------
生成类的静态方法
-----------------------
	static T getDefaultInstance()
		* 返回单例

	Descriptor getDescriptor()
		* 获取描述

	Parser<T> parser()
		* 返回解析器

	T parseDelimitedFrom(InputStream input);
	T parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry)

	T parseFrom(byte[] data)
	T parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)

	T parseFrom(ByteString input)
	T parseFrom(ByteString input, ExtensionRegistryLite extensionRegistry)

	T parseFrom(CodedInputStream input)
	T parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry)

	T parseFrom(InputStream input)
	T parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry)

	T parseFrom(ByteBuffer data)
	T parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry)
		* 解析数据为对象

	Builder newBuilder(T prototype)
		* 创建一个新的builder
		* 所有字段初始化为和在prototype中一样的值, 浅克隆类似

	Builder newBuilder()
		* 创建一个新的builder

-----------------------
实例方法方法
-----------------------
	void writeTo(CodedOutputStream output)
		* 把当前类序列化到指定的OutputStream
	
	XXXOrBuilder getXXXOrBuilder();
		* 如果字段的builder已经存在则返回字段的builder
		* 如果不存在则返回字段
	
	Builder toBuilder()
		* 转换当前对象为builder
	
	
	boolean hasOneof(OneofDescriptor oneof)
		