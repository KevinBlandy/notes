--------------------------
cgo类型转换
--------------------------
	# Go语言中数值类型和C语言数据类型基本上是相似的
		C语言类型				CGO类型		Go语言类型
		char					C.char		byte
		singed char				C.schar		int8
		unsigned char			C.uchar		uint8
		short					C.short		int16
		unsigned short			C.ushort	uint16
		int						C.int		int32
		unsigned int			C.uint		uint32
		long					C.long		int32
		unsigned long			C.ulong		uint32
		long long int			C.longlong	int64
		unsigned long long int	C.ulonglong	uint64
		float					C.float		float32
		double					C.double	float64
		size_t					C.size_t	uint
	
		
		* C语言中int、short等类型没有明确定义内存大小，但是在CGO中它们的内存大小是确定的。
		* 在CGO中，C语言的int和long类型都是对应4个字节的内存大小，size_t类型可以当作Go语言uint无符号整数类型对待。
	

