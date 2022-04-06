------------------------
Path
------------------------
	# path实现了操作斜线分隔的路径的实用程序。
	# path包应该只用于由正斜线分隔的路径，例如URL中的路径。
		* 这个包不处理带有驱动器字母或反斜线的Windows路径
		* 要处理操作系统的路径，请使用path/filepath包
	
------------------------
var
------------------------
	var ErrBadPattern = errors.New("syntax error in pattern")

------------------------
type
------------------------

------------------------
func
------------------------
	func Base(path string) string
	func Clean(path string) string
	func Dir(path string) string
	func Ext(path string) string
	func IsAbs(path string) bool
	func Join(elem ...string) string
	func Match(pattern, name string) (matched bool, err error)
	func Split(path string) (dir, file string)
