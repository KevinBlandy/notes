-----------------
反射
-----------------
	# 反射主要与 interface 接口有关，只有interface类型才有反射一说。
		* 每个interface变量都有一个对应pair，pair中记录了实际变量的值和类型
			(value, type)

			tty, err := os.OpenFile("/dev/tty", os.O_RDWR, 0)
			var r io.Reader	
			r = tty
			// r的pair = (tty, *os.File)
		
		* 这个pair在接口变量的连续赋值过程中是不变的，将接口变量r赋给另一个接口变量w:
			var w io.Writer
			w = r.(io.Writer)
			// w的pair = (tty, *os.File)
		

		* 反射就是用来检测存储在接口变量内部(值value；类型concrete type) pair对的一种机制。
	
	# reflect.TypeOf() 获取 pair 中的type，这个是一个interface
		func TypeOf(i interface{}) Type


	# reflect.ValueOf() 获取 pair 中的value，这是一个struct
		func ValueOf(i interface{}) Value 

		* 如果参数是nil，返回空 Value
	
	# 比较常用的一些对象
		* 描述方法的
			type Method struct {
				Name    string
				PkgPath string
				Type  Type
				Func  Value
				Index int 
			}
		
		* 描述方法tag的
			type StructTag string {}
			func (tag StructTag) Get(key string) string 
			func (tag StructTag) Lookup(key string) (value string, ok bool)
		
		* 描述属性字段的
			type StructField struct {
				PkgPath string
				Type      Type		
				Tag       StructTag		
				Offset    uintptr 
				Index     []int			
				Anonymous bool		
			}
		

	# Go的反射比较慢
		* 涉及到内存分配以及后续的GC
		* reflect实现里面有大量的枚举，也就是for循环，比如类型之类的。