-----------------
模块常量
-----------------

-----------------
结构体
-----------------
	# Buffer
		type Builder struct {
			addr *Builder // of receiver, to detect copies by value
			buf  []byte
		}

		* 用于在内存中创建一个字节的缓冲区，用于IO
		* 最终可以把结果写入/读取到别的地儿，类似于Java中的 StringBuilder

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

		* 用于在内存中构建一个字符串缓冲区，只读
		* 类似于：bytes.NewBufferString 但是更高效，因为只读
		* 用各种方式从责这个缓冲区中读取出数据
		
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
模块方法
-----------------
	func Compare(a, b string) int
		* 比较2个字符串的大小，返回int
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
		* 字符串的切割

	func Fields(s string) []string
		* 基于空白分隔符将一行文本分割成一个字符串切片，空白分隔符是由unicode.IsSpace()定义的

	func FieldsFunc(s string, f func(rune) bool) []string
	func Join(elems []string, sep string) string 
		* 把elements，以sep进行拼接后返回

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
		* 替换字符串，参数n表示要替换的个数，如果设置为-1表示替换所有

	func ReplaceAll(s, old, new string) string
		* 替换字符串，替换所有，本质上是调用：return Replace(s, old, new, -1)

	func EqualFold(s, t string) bool
		* 不区分大小写比较字符串是否相等

	func Index(s, substr string) int
	func Cut(s, sep string) (before, after string, found bool)
	func CutPrefix(s, prefix string) (after string, found bool)
		* 类似于 TrimPrefix，但是返回是否找到前缀
	func CutSuffix(s, suffix string) (before string, found bool)
		* 同上
	func Clone(s string) string



-----------------
Demo
-----------------
	# Builder的简单用法
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
	