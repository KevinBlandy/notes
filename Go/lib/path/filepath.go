-------------------------
filepath
-------------------------

-------------------------
变量
-------------------------
	const (
		Separator     = os.PathSeparator
		ListSeparator = os.PathListSeparator
	)

	var ErrBadPattern = errors.New("syntax error in pattern")
	
	* 遍历目录的时候，可以返回这个异常，则跳出当前目录
		var SkipDir = errors.New("skip this directory")


-------------------------
type
-------------------------
	# type WalkFunc func(path string, info os.FileInfo, err error) error

-------------------------
方法
-------------------------
	func Abs(path string) (string, error)
		* 返回 path 代表的绝对路径，如果 path 不是绝对路径，会加入当前工作目录以使之成为绝对路径。


	func Base(path string) string
		* 返回路径的最后一个元素。在提取元素前会去掉末尾的斜杠。
		* 如果路径是 ""，会返回 "."；如果路径是只有一个斜杆构成的，会返回 "/"。
			"C:\go-project"			-> go-project
			"C:\go-project\"		-> go-project
			"C:\go-project\a.jpg"	-> a.jpg
			""						-> .

	func Clean(path string) string
	func Dir(path string) string
		* 返回路径中除去最后一个路径元素的部分，即该路径最后一个元素所在的目录


	func EvalSymlinks(path string) (string, error)
	func Ext(path string) string
		* 获取后缀名称
	
	func FromSlash(path string) string
	func Glob(pattern string) (matches []string, err error)
	func HasPrefix(p, prefix string) bool
	func IsAbs(path string) bool
		* 返回路径是否是一个绝对路径
	
	func Join(elem ...string) string
		* 拼接路径

	func Match(pattern, name string) (matched bool, err error)
	func Rel(basepath, targpath string) (string, error)
	func Split(path string) (dir, file string)
		* 把path分隔为目录和文件名两部分
		* 如果没有目录，则 dir 是空字符串

	func SplitList(path string) []string
	func ToSlash(path string) string
	func VolumeName(path string) string

	func Walk(root string, walkFn WalkFunc) error
		* 遍历目录树，如果walkFn返回 SkipDir ，则跳出当前目录，继续遍历
		* 大目录Walk的效率会很低，不会关联快捷方式
