-----------------
ģ�鳣��
-----------------
	const MinRead = 512

-----------------
�ṹ��
-----------------
	# Buffer �ṹ��
		type Buffer struct {
			buf      []byte // contents are the bytes buf[off : len(buf)]
			off      int    // read at &buf[off], write at &buf[len(buf)]
			lastRead readOp // last read operation, so that Unread* can work correctly.
		}

		* �������ڴ��д���һ���ֽڵĻ�����������IO����ʵ���˳���� Writer/Reader �ӿ�
		* ���տ��԰ѽ��д��/��ȡ����ĵض���������Java�е� ByteBuffer

		func NewBuffer(buf []byte) *Buffer { return &Buffer{buf: buf} 
		func NewBufferString(s string) *Buffer 

		func (b *Buffer) Available() int
			* ���ػ�������δʹ�õ��ֽ�����
				 return cap(b.buf) - len(b.buf)
		
		func (b *Buffer) AvailableBuffer() []byte
			* AvailableBuffer ����һ������Ϊ b.Available() �Ŀջ��������û���������׷�Ӳ����ݸ��������� "Write" ���á�
			* �û�����ֻ���´ζ� b ����д����֮ǰ��Ч��
				 return b.buf[len(b.buf):]

		func (b *Buffer) Bytes() []byte	
			* �������е��ֽ���Ƭ

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


	# Reader�ṹ��
		type Reader struct {
			s        []byte
			i        int64 // current reading index
			prevRune int   // index of previous rune; or < 0
		}

		* ���ڰ��ֽڷ�װ��һ����������ֻ��
		* �����ø��ַ���������������ж�ȡ����

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
ģ�鷽��
-----------------
	func Clone(b []byte) []byte
	func Compare(a, b []byte) int
	func Contains(b, subslice []byte) bool
	func ContainsAny(b []byte, chars string) bool
	func ContainsFunc(b []byte, f func(rune) bool) bool
	func ContainsRune(b []byte, r rune) bool
	func Count(s, sep []byte) int
	func Cut(s, sep []byte) (before, after []byte, found bool)
	func CutPrefix(s, prefix []byte) (after []byte, found bool)
	func CutSuffix(s, suffix []byte) (before []byte, found bool)
	func Equal(a, b []byte) bool
	func EqualFold(s, t []byte) bool
	func Fields(s []byte) [][]byte
	func FieldsFunc(s []byte, f func(rune) bool) [][]byte
	func FieldsFuncSeq(s []byte, f func(rune) bool) iter.Seq[[]byte]
	func FieldsSeq(s []byte) iter.Seq[[]byte]
	func HasPrefix(s, prefix []byte) bool
	func HasSuffix(s, suffix []byte) bool
	func Index(s, sep []byte) int
	func IndexAny(s []byte, chars string) int
	func IndexByte(b []byte, c byte) int
	func IndexFunc(s []byte, f func(r rune) bool) int
	func IndexRune(s []byte, r rune) int
	func Join(s [][]byte, sep []byte) []byte
	func LastIndex(s, sep []byte) int
	func LastIndexAny(s []byte, chars string) int
	func LastIndexByte(s []byte, c byte) int
	func LastIndexFunc(s []byte, f func(r rune) bool) int
	func Lines(s []byte) iter.Seq[[]byte]
	func Map(mapping func(r rune) rune, s []byte) []byte
	func Repeat(b []byte, count int) []byte
	func Replace(s, old, new []byte, n int) []byte
	func ReplaceAll(s, old, new []byte) []byte
	func Runes(s []byte) []rune
	func Split(s, sep []byte) [][]byte
	func SplitAfter(s, sep []byte) [][]byte
	func SplitAfterN(s, sep []byte, n int) [][]byte
	func SplitAfterSeq(s, sep []byte) iter.Seq[[]byte]
	func SplitN(s, sep []byte, n int) [][]byte
	func SplitSeq(s, sep []byte) iter.Seq[[]byte]
	func Title(s []byte) []bytedeprecated
	func ToLower(s []byte) []byte
	func ToLowerSpecial(c unicode.SpecialCase, s []byte) []byte
	func ToTitle(s []byte) []byte
	func ToTitleSpecial(c unicode.SpecialCase, s []byte) []byte
	func ToUpper(s []byte) []byte
	func ToUpperSpecial(c unicode.SpecialCase, s []byte) []byte
	func ToValidUTF8(s, replacement []byte) []byte
	func Trim(s []byte, cutset string) []byte
	func TrimFunc(s []byte, f func(r rune) bool) []byte
	func TrimLeft(s []byte, cutset string) []byte
	func TrimLeftFunc(s []byte, f func(r rune) bool) []byte
	func TrimPrefix(s, prefix []byte) []byte
	func TrimRight(s []byte, cutset string) []byte
	func TrimRightFunc(s []byte, f func(r rune) bool) []byte
	func TrimSpace(s []byte) []byte
	func TrimSuffix(s, suffix []byte) []byte
