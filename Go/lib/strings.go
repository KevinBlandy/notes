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

	func Clone(s string) string
	func Compare(a, b string) int
	func Contains(s, substr string) bool
	func ContainsAny(s, chars string) bool
	func ContainsFunc(s string, f func(rune) bool) bool
	func ContainsRune(s string, r rune) bool
	func Count(s, substr string) int
	func Cut(s, sep string) (before, after string, found bool)
	func CutPrefix(s, prefix string) (after string, found bool)
	func CutSuffix(s, suffix string) (before string, found bool)
	func EqualFold(s, t string) bool
	func Fields(s string) []string
	func FieldsFunc(s string, f func(rune) bool) []string
	func FieldsFuncSeq(s string, f func(rune) bool) iter.Seq[string]
	func FieldsSeq(s string) iter.Seq[string]
	func HasPrefix(s, prefix string) bool
	func HasSuffix(s, suffix string) bool
	func Index(s, substr string) int
	func IndexAny(s, chars string) int
	func IndexByte(s string, c byte) int
	func IndexFunc(s string, f func(rune) bool) int
	func IndexRune(s string, r rune) int
	func Join(elems []string, sep string) string
	func LastIndex(s, substr string) int
	func LastIndexAny(s, chars string) int
	func LastIndexByte(s string, c byte) int
	func LastIndexFunc(s string, f func(rune) bool) int
	func Lines(s string) iter.Seq[string]
	func Map(mapping func(rune) rune, s string) string
	func Repeat(s string, count int) string
	func Replace(s, old, new string, n int) string
	func ReplaceAll(s, old, new string) string
	func Split(s, sep string) []string
	func SplitAfter(s, sep string) []string
	func SplitAfterN(s, sep string, n int) []string
	func SplitAfterSeq(s, sep string) iter.Seq[string]
	func SplitN(s, sep string, n int) []string
	func SplitSeq(s, sep string) iter.Seq[string]
	func Title(s string) stringdeprecated
	func ToLower(s string) string
	func ToLowerSpecial(c unicode.SpecialCase, s string) string
	func ToTitle(s string) string
	func ToTitleSpecial(c unicode.SpecialCase, s string) string
	func ToUpper(s string) string
	func ToUpperSpecial(c unicode.SpecialCase, s string) string
	func ToValidUTF8(s, replacement string) string
	func Trim(s, cutset string) string
	func TrimFunc(s string, f func(rune) bool) string
	func TrimLeft(s, cutset string) string
	func TrimLeftFunc(s string, f func(rune) bool) string
	func TrimPrefix(s, prefix string) string
	func TrimRight(s, cutset string) string
	func TrimRightFunc(s string, f func(rune) bool) string
	func TrimSpace(s string) string
	func TrimSuffix(s, suffix string) string


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
	