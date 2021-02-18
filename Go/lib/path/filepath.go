-------------------------
filepath
-------------------------

-------------------------
变量
-------------------------
	const (
		Separator     = os.PathSeparator			// 路径分隔符（分隔路径元素）
		ListSeparator = os.PathListSeparator		// 路径列表分隔符（分隔多个路径）
	)
		
		* 跨平台的路径分隔符和路径集合分隔符

	var ErrBadPattern = errors.New("syntax error in pattern")
	
	var SkipDir = errors.New("skip this directory")
		* 遍历目录的时候，可以返回这个异常，则跳出当前目录


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
		* 可以用于把网络路径改成本地的文件路径
			filepath.Clean("/dd/dd.jpg") // \dd\dd.jpg
		* 空字符串，返回 "."
		* 清理路径中的多余字符，比如 /// 或 ../ 或 ./

	func Dir(path string) string
		* 返回路径中除去最后一个路径元素的部分，即该路径最后一个元素所在的目录


	func EvalSymlinks(path string) (string, error)
		* 返回链接（快捷方式）所指向的实际文件

	func Ext(path string) string
		* 获取后缀名称
	
	func FromSlash(path string) string
		* 将 path 中的 '/' 转换为系统相关的路径分隔符
				
	func Glob(pattern string) (matches []string, err error)
		* 列出与指定的模式 pattern 完全匹配的文件或目录（匹配原则同上）

	func HasPrefix(p, prefix string) bool
	func IsAbs(path string) bool
		* 返回路径是否是一个绝对路径
	
	func Join(elem ...string) string
		* 拼接路径，如果路径中已经包含了一些多余的分隔符，会被去除
		* 忽略空元素，清理多余字符。

	func Match(pattern, name string) (matched bool, err error)
		* name 是否和指定的模式 pattern 完全匹配
	
	func Rel(basepath, targpath string) (string, error)
		* 获取 targpath 相对于 basepath 的路径。
		* 要求 targpath 和 basepath 必须“都是相对路径”或“都是绝对路径”。

	func Split(path string) (dir, file string)
		* 把path分隔为目录和文件名两部分
		* 如果没有目录，则 dir 是空字符串

	func SplitList(path string) []string	
		* 路径序列 path 分割为多条独立的路径

	func ToSlash(path string) string
		* 将 path 中平台相关的路径分隔符转换为 '/'

	func VolumeName(path string) string
		* 返回路径字符串中的卷名
			indows 中的 `C:\Windows` 会返回 "C:"
			Linux 中的 `//host/share/name` 会返回 `//host/share`

	func Walk(root string, walkFn WalkFunc) error
		* 遍历目录树，如果walkFn返回 SkipDir ，则跳出当前目录，继续遍历
		* 大目录Walk的效率会很低，不会关联快捷方式
	
	func WalkDir(root string, fn fs.WalkDirFunc) error
		* 类似于 Walk，但通常效率更高。
		* 传递给的函数WalkDir接收 fs.DirEntry 而不是 fs.FileInfo
	

