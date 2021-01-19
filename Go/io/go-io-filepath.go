-----------------
filepath
-----------------
	# 文件路径相关的api
	# 属性
		const (
			Separator     = os.PathSeparator			// 跨平台的文件分隔符
			ListSeparator = os.PathListSeparator
		)

	# 方法
		func Abs(path string) (string, error)
		func IsAbs(path string) bool
		func HasPrefix(p, prefix string) bool 
		func Match(pattern, name string) (matched bool, err error) 
		func Glob(pattern string) (matches []string, err error)
		func Clean(path string) string
		func ToSlash(path string) string 
		func FromSlash(path string) string
		func SplitList(path string) []string 
		func Split(path string) (dir, file string) 
		func Join(elem ...string) string
			* 多个文件路径，组合成一个路径，会自动添加跨平台的分隔符

		func Ext(path string) string 
		func EvalSymlinks(path string) (string, error)
		func Rel(basepath, targpath string) (string, error) 
		func Base(path string) string 
		func Dir(path string) string
		func VolumeName(path string) string 

		func Walk(root string, walkFn WalkFunc) error
			* 遍历指定的文件夹
			WalkFunc 
				* 是一个函数：type WalkFunc func(path string, info os.FileInfo, err error) error