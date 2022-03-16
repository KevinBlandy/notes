-----------------
模块常量
-----------------
	const MinRead = 512

-----------------
结构体
-----------------
	# Buffer 结构体
		type Buffer struct {
			buf      []byte // contents are the bytes buf[off : len(buf)]
			off      int    // read at &buf[off], write at &buf[len(buf)]
			lastRead readOp // last read operation, so that Unread* can work correctly.
		}

		* 用于在内存中创建一个字节的缓冲区，用于IO，它实现了常规的 Writer/Reader 接口
		* 最终可以把结果写入/读取到别的地儿，类似于Java中的 ByteBuffer

		func NewBuffer(buf []byte) *Buffer { return &Buffer{buf: buf} 
		func NewBufferString(s string) *Buffer 

		func (b *Buffer) Bytes() []byte	
			* 返回其中的字节切片

		func (b *Buffer) String() string
		func (b *Buffer) Len() int
		func (b *Buffer) Cap() int 
		func (b *Buffer) Truncate(n int) 
		func (b *Buffer) Reset() 
		func (b *Buffer) Grow(n int) 
		func (b *Buffer) Write(p []byte) (n int, err error)
		func (b *Buffer) WriteString(s string) (n int, err error) 
		func (b *Buffer) ReadFrom(r io.Reader) (n int64, err error) 
		func (b *Buffer) WriteTo(w io.Writer) (n int64, err error) 
		func (b *Buffer) WriteByte(c byte) error 
		func (b *Buffer) WriteRune(r rune) (n int, err error)
		func (b *Buffer) Read(p []byte) (n int, err error)
		func (b *Buffer) Next(n int) []byte 
		func (b *Buffer) ReadByte() (byte, error) 
		func (b *Buffer) ReadRune() (r rune, size int, err error) 
		func (b *Buffer) UnreadRune() error 
		func (b *Buffer) UnreadByte() error 
		func (b *Buffer) ReadBytes(delim byte) (line []byte, err error) 
		func (b *Buffer) ReadString(delim byte) (line string, err error) 


	# Reader结构体
		type Reader struct {
			s        []byte
			i        int64 // current reading index
			prevRune int   // index of previous rune; or < 0
		}

		* 用于把字节封装到一个缓冲区，只读
		* 可以用各种方法从这个缓冲区中读取数据

		func NewReader(b []byte) *Reader { return &Reader{b, 0, -1}
		
		func (r *Reader) Len() int
		func (r *Reader) Size() int64 
		func (r *Reader) Read(b []byte) (n int, err error)
		func (r *Reader) ReadAt(b []byte, off int64) (n int, err error) 
		func (r *Reader) ReadByte() (byte, error) 
		func (r *Reader) UnreadByte() error 
		func (r *Reader) ReadRune() (ch rune, size int, err error)
		func (r *Reader) UnreadRune() error 
		func (r *Reader) Seek(offset int64, whence int) (int64, error)
		func (r *Reader) WriteTo(w io.Writer) (n int64, err error) 
		func (r *Reader) Reset(b []byte) { *r = Reader{b, 0, -1}


-----------------
模块方法
-----------------
	func Equal(a, b []byte) bool
		* 比较2个字节切片，是否是完全一样的

	func Compare(a, b []byte) int
	func Count(s, sep []byte) int 
	func Contains(b, subslice []byte) bool
	func ContainsAny(b []byte, chars string) bool
	func ContainsRune(b []byte, r rune) bool
	func IndexByte(b []byte, c byte) int
	func LastIndex(s, sep []byte) int
	func LastIndexByte(s []byte, c byte) int
	func IndexRune(s []byte, r rune) int
	func IndexAny(s []byte, chars string) int
	func LastIndexAny(s []byte, chars string) int
	func SplitN(s, sep []byte, n int) [][]byte
	func SplitAfterN(s, sep []byte, n int) [][]byte
	func Split(s, sep []byte) [][]byte
	func SplitAfter(s, sep []byte) [][]byte
	func Fields(s []byte) [][]byte
	func FieldsFunc(s []byte, f func(rune) bool) [][]byte
	func Join(s [][]byte, sep []byte) []byte
	func HasPrefix(s, prefix []byte) bool
	func HasSuffix(s, suffix []byte) bool 
	func Map(mapping func(r rune) rune, s []byte) []byte
	func Repeat(b []byte, count int) []byte 
	func ToUpper(s []byte) []byte 
	func ToLower(s []byte) []byte
	func ToTitle(s []byte) []byte 
	func ToUpperSpecial(c unicode.SpecialCase, s []byte) []byte 
	func ToLowerSpecial(c unicode.SpecialCase, s []byte) []byte 
	func ToTitleSpecial(c unicode.SpecialCase, s []byte) []byte
	func ToValidUTF8(s, replacement []byte) []byte
	func Title(s []byte) []byte 
	func TrimLeftFunc(s []byte, f func(r rune) bool) []byte 
	func TrimRightFunc(s []byte, f func(r rune) bool) []byte 
	func TrimFunc(s []byte, f func(r rune) bool) []byte
	func TrimPrefix(s, prefix []byte) []byte 
	func TrimSuffix(s, suffix []byte) []byte 
	func IndexFunc(s []byte, f func(r rune) bool) int 
	func LastIndexFunc(s []byte, f func(r rune) bool) int 
	func Trim(s []byte, cutset string) []byte 
	func TrimLeft(s []byte, cutset string) []byte
	func TrimRight(s []byte, cutset string) []byte
	func TrimSpace(s []byte) []byte 
	func Runes(s []byte) []rune
	func Replace(s, old, new []byte, n int) []byte
	func ReplaceAll(s, old, new []byte) []byte
	func EqualFold(s, t []byte) bool
	func Index(s, sep []byte) int 
	func Cut(s, sep []byte) (before, after []byte, found bool) 

