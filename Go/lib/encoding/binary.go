----------------------------
binary
----------------------------

----------------------------
变量
----------------------------
	const (
		MaxVarintLen16 = 3
		MaxVarintLen32 = 5
		MaxVarintLen64 = 10
	)

	* 网络字节序
		var BigEndian bigEndian
		var LittleEndian littleEndian

----------------------------
type
----------------------------
	# type AppendByteOrder interface {
		AppendUint16([]byte, uint16) []byte
		AppendUint32([]byte, uint32) []byte
		AppendUint64([]byte, uint64) []byte
		String() string
	}

	# type ByteOrder interface {
			Uint16([]byte) uint16
			Uint32([]byte) uint32
			Uint64([]byte) uint64
			PutUint16([]byte, uint16)
			PutUint32([]byte, uint32)
			PutUint64([]byte, uint64)
			String() string
		}

		* 字节序接口

----------------------------
方法
----------------------------
	func AppendUvarint(buf []byte, x uint64) []byte
	func AppendVarint(buf []byte, x int64) []byte
	func PutUvarint(buf []byte, x uint64) int
	func PutVarint(buf []byte, x int64) int
	func Read(r io.Reader, order ByteOrder, data interface{}) error
		* 从指定的Reader按照指定的字节序，读取数据到data中

	func ReadUvarint(r io.ByteReader) (uint64, error)
	func ReadVarint(r io.ByteReader) (int64, error)
		* 从 ByteReader中读取数值
		* 如果目标Reader没有实现ByteReader接口，可以用 bufio包装
			bufio.NewReader(reader)

	func Size(v interface{}) int
	func Uvarint(buf []byte) (uint64, int)
		* 将在10 bytes 之后停止读取，以避免计算的浪费。如果需要超过10 bytes ，返回的字节数是-11 。

	func Varint(buf []byte) (int64, int)
		* 从buf中解码出一个int64，并返回该值和读取的字节数n（>0）
		* 如果发生错误，该值为0，且字节数n<=0，其含义如下
			* n == 0: buf 太小了。
			* n < 0: 值大于64位(溢出)

	func Write(w io.Writer, order ByteOrder, data interface{}) error 
		* 把data以指定的字节序列写入到 w
		* data可以是boo/[]bool/数值/[]数值
	


----------------------------
demo
----------------------------
	# 数值和二进制数组的转换
		buffer := make([]byte, 4, 4)
		// 把一个数值，转换为字节数组，放进buffer
		binary.BigEndian.PutUint32(buffer, 0xFFFFFFFF)	// 0xFFFFFFFF = 4294967295
		log.Println(buffer)		// [255 255 255 255]

		// 从buffer中读取一个数值
		val := binary.BigEndian.Uint32(buffer)
		log.Println(val)		// 4294967295