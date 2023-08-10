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
			* CompilePOSIX 和 Compile 的不同点在于 POSIX 必须使用 POSIX 语法，它使用最左最长方式搜索
			* 而 Compile 是采用的则只采用最左方式搜索 
			
			* 例如 [a-z]{2,4} 这样一个正则表达式，应用于 "aa09aaa88aaaa" 这个文本串时，CompilePOSIX 返回了 aaaa，而 Compile 的返回的是 aa


		func MustCompile(str string) *Regexp
		func MustCompilePOSIX(str string) *Regexp
			* 同上，如果正则编译不过，则会异常

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
		func (re *Regexp) MarshalText() ([]byte, error)

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
			* 把src中符合this规则的字符串。都以func计算结果进行替换

		func (re *Regexp) Split(s string, n int) []string
		func (re *Regexp) String() string
		func (re *Regexp) SubexpIndex(name string) int
		func (re *Regexp) SubexpNames() []string
		func (re *Regexp) UnmarshalText(text []byte) error
	
-------------
方法
-------------
	func Match(pattern string, b []byte) (matched bool, err error)
	func MatchReader(pattern string, r io.RuneReader) (matched bool, err error)
	func MatchString(pattern string, s string) (matched bool, err error)
		* 判断指定的字节数组/Reader/字符串 是否符合表达式

	func QuoteMeta(s string) string