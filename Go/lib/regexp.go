-------------
regexp
-------------
-------------
变量
-------------
-------------
type
-------------
	# type Regexp struct 

		* 正则表达式的结构体

		func Compile(expr string) (*Regexp, error)
		func CompilePOSIX(expr string) (*Regexp, error)
		func MustCompile(str string) *Regexp
		func MustCompilePOSIX(str string) *Regexp

		func (re *Regexp) Copy() *Regexp
		func (re *Regexp) Expand(dst []byte, template []byte, src []byte, match []int) []byte
		func (re *Regexp) ExpandString(dst []byte, template string, src string, match []int) []byte
		func (re *Regexp) Find(b []byte) []byte
		func (re *Regexp) FindAll(b []byte, n int) [][]byte
		func (re *Regexp) FindAllIndex(b []byte, n int) [][]int
		func (re *Regexp) FindAllString(s string, n int) []string
		func (re *Regexp) FindAllStringIndex(s string, n int) [][]int
		func (re *Regexp) FindAllStringSubmatch(s string, n int) [][]string
		func (re *Regexp) FindAllStringSubmatchIndex(s string, n int) [][]int
		func (re *Regexp) FindAllSubmatch(b []byte, n int) [][][]byte
		func (re *Regexp) FindAllSubmatchIndex(b []byte, n int) [][]int
		func (re *Regexp) FindIndex(b []byte) (loc []int)
		func (re *Regexp) FindReaderIndex(r io.RuneReader) (loc []int)
		func (re *Regexp) FindReaderSubmatchIndex(r io.RuneReader) []int
		func (re *Regexp) FindString(s string) string
		func (re *Regexp) FindStringIndex(s string) (loc []int)
		func (re *Regexp) FindStringSubmatch(s string) []string
		func (re *Regexp) FindStringSubmatchIndex(s string) []int
		func (re *Regexp) FindSubmatch(b []byte) [][]byte
		func (re *Regexp) FindSubmatchIndex(b []byte) []int
		func (re *Regexp) LiteralPrefix() (prefix string, complete bool)
		func (re *Regexp) Longest()

		func (re *Regexp) Match(b []byte) bool
		func (re *Regexp) MatchReader(r io.RuneReader) bool
		func (re *Regexp) MatchString(s string) bool
			* 是否可以匹配指定的字节数组/Reader/字符串

		func (re *Regexp) NumSubexp() int
		func (re *Regexp) ReplaceAll(src, repl []byte) []byte
		func (re *Regexp) ReplaceAllFunc(src []byte, repl func([]byte) []byte) []byte
		func (re *Regexp) ReplaceAllLiteral(src, repl []byte) []byte
		func (re *Regexp) ReplaceAllLiteralString(src, repl string) string
		func (re *Regexp) ReplaceAllString(src, repl string) string
		func (re *Regexp) ReplaceAllStringFunc(src string, repl func(string) string) string
		func (re *Regexp) Split(s string, n int) []string
		func (re *Regexp) String() string
		func (re *Regexp) SubexpIndex(name string) int
		func (re *Regexp) SubexpNames() []string
	
-------------
方法
-------------
	func Match(pattern string, b []byte) (matched bool, err error)
	func MatchReader(pattern string, r io.RuneReader) (matched bool, err error)
	func MatchString(pattern string, s string) (matched bool, err error)
		* 判断指定的字节数组/Reader/字符串 是否符合表达式

	func QuoteMeta(s string) string