-----------------
ģ�鳣��
-----------------

-----------------
�ṹ��
-----------------
	# Buffer
		type Builder struct {
			addr *Builder // of receiver, to detect copies by value
			buf  []byte
		}

		* �������ڴ��д���һ���ֽڵĻ�����������IO
		* ���տ��԰ѽ��д��/��ȡ����ĵض���������Java�е� StringBuilder

		func (b *Builder) String() string 
		func (b *Builder) Len() int { return len(b.buf) 
		func (b *Builder) Cap() int { return cap(b.buf) 
		func (b *Builder) Reset()
		func (b *Builder) Grow(n int)
		func (b *Builder) Write(p []byte) (int, error)
		func (b *Builder) WriteByte(c byte) error
		func (b *Builder) WriteRune(r rune) (int, error)
		func (b *Builder) WriteString(s string) (int, error) 

	
	# Reader
		type Reader struct {
			s        string
			i        int64 // current reading index
			prevRune int   // index of previous rune; or < 0
		}

		* �������ڴ��й���һ���ַ�����������ֻ��
		* �����ڣ�bytes.NewBufferString ���Ǹ���Ч����Ϊֻ��
		* �ø��ַ�ʽ��������������ж�ȡ������
		
		func NewReader(s string) *Reader 

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
		func (r *Reader) Reset(s string) 

	# Replacer
		type Replacer struct {
			once   sync.Once // guards buildOnce method
			r      replacer
			oldnew []string
		}
		
		func NewReplacer(oldnew ...string) *Replacer
		
		func (r *Replacer) Replace(s string) string
		func (r *Replacer) WriteString(w io.Writer, s string)


-----------------
ģ�鷽��
-----------------
	func Compare(a, b string) int
		* �Ƚ�2���ַ����Ĵ�С������int
			a > b = 1
			a < b = -1
			a = b = 0
	
	func Count(s, substr string) int
	func Contains(s, substr string) bool 
	func ContainsAny(s, chars string) bool 
	func ContainsRune(s string, r rune) bool 
	func LastIndex(s, substr string) int 
	func IndexByte(s string, c byte) int 
	func IndexRune(s string, r rune) int 
	func IndexAny(s, chars string) int
	func LastIndexAny(s, chars string) int 
	func LastIndexByte(s string, c byte) int 

	func SplitN(s, sep string, n int) []string 
	func SplitAfterN(s, sep string, n int) []string
	func Split(s, sep string) []string
	func SplitAfter(s, sep string) []string
		* �ַ������и�

	func Fields(s string) []string
		* ���ڿհ׷ָ�����һ���ı��ָ��һ���ַ�����Ƭ���հ׷ָ�������unicode.IsSpace()�����

	func FieldsFunc(s string, f func(rune) bool) []string
	func Join(elems []string, sep string) string 
		* ��elements����sep����ƴ�Ӻ󷵻�

	func HasPrefix(s, prefix string) bool
	func HasSuffix(s, suffix string) bool 
	func Map(mapping func(rune) rune, s string) string
	func Repeat(s string, count int) string 
	func ToUpper(s string) string
	func ToLower(s string) string
	func ToTitle(s string) string 
	func ToUpperSpecial(c unicode.SpecialCase, s string) string
	func ToLowerSpecial(c unicode.SpecialCase, s string) string 
	func ToTitleSpecial(c unicode.SpecialCase, s string) string
	func ToValidUTF8(s, replacement string) string 
	func Title(s string) string {
	func TrimLeftFunc(s string, f func(rune) bool) string
	func TrimRightFunc(s string, f func(rune) bool) string
	func TrimFunc(s string, f func(rune) bool) string
	func IndexFunc(s string, f func(rune) bool) int
	func LastIndexFunc(s string, f func(rune) bool) int
	func Trim(s, cutset string) string
	func TrimLeft(s, cutset string) string
	func TrimRight(s, cutset string) string 
	func TrimSpace(s string) string 
	func TrimPrefix(s, prefix string) string
	func TrimSuffix(s, suffix string) string
	func Replace(s, old, new string, n int) string 
		* �滻�ַ���������n��ʾҪ�滻�ĸ������������Ϊ-1��ʾ�滻����

	func ReplaceAll(s, old, new string) string
		* �滻�ַ������滻���У��������ǵ��ã�return Replace(s, old, new, -1)

	func EqualFold(s, t string) bool
		* �����ִ�Сд�Ƚ��ַ����Ƿ����

	func Index(s, substr string) int
	func Cut(s, sep string) (before, after string, found bool)
	func CutPrefix(s, prefix string) (after string, found bool)
		* ������ TrimPrefix�����Ƿ����Ƿ��ҵ�ǰ׺
	func CutSuffix(s, suffix string) (before string, found bool)
		* ͬ��
	func Clone(s string) string



-----------------
Demo
-----------------
	# Builder�ļ��÷�
		import (
			"fmt"
			"strings"
		)

		func main(){
			builder := strings.Builder{}
			builder.WriteString("Hello")
			builder.WriteRune(',')
			builder.WriteString("World")
			fmt.Println(builder.String()) // Hello,World
		}
	