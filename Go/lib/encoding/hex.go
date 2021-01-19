-----------------------
hex
-----------------------	
	# 16进制的工具类

-----------------------
var
-----------------------
	# 错误的长度
		var ErrLength = errors.New("encoding/hex: odd length hex string")
	

-----------------------
type
-----------------------
	# type InvalidByteError byte
		func (e InvalidByteError) Error() string

-----------------------
func
-----------------------
	func Decode(dst, src []byte) (int, error)
	func DecodeString(s string) ([]byte, error)
		* 把字符串解码为字节数组

	func DecodedLen(x int) int
	func Dump(data []byte) string
	func Dumper(w io.Writer) io.WriteCloser
	func Encode(dst, src []byte) int
	func EncodeToString(src []byte) string
		* 把字节数组编码为16进制字符串

	func EncodedLen(n int) int
	func NewDecoder(r io.Reader) io.Reader
	func NewEncoder(w io.Writer) io.Writer