-----------------------
hex
-----------------------	
	# 16���ƵĹ�����

-----------------------
var
-----------------------
	# ����ĳ���
		var ErrLength = errors.New("encoding/hex: odd length hex string")
	

-----------------------
type
-----------------------
	# type InvalidByteError byte
		func (e InvalidByteError) Error() string

-----------------------
func
-----------------------
	func AppendDecode(dst, src []byte) ([]byte, error)
	func AppendEncode(dst, src []byte) []byte
	func Decode(dst, src []byte) (int, error)
	func DecodeString(s string) ([]byte, error)
		* ���ַ�������Ϊ�ֽ�����

	func DecodedLen(x int) int
	func Dump(data []byte) string
	func Dumper(w io.Writer) io.WriteCloser
	func Encode(dst, src []byte) int
	func EncodeToString(src []byte) string
		* ���ֽ��������Ϊ16�����ַ���

	func EncodedLen(n int) int
	func NewDecoder(r io.Reader) io.Reader
	func NewEncoder(w io.Writer) io.Writer