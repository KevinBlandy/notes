---------------------
ioutil
---------------------

---------------------
变量
---------------------
	* 黑洞Writer
		var Discard io.Writer = devNull(0)

---------------------
type
---------------------


---------------------
方法
---------------------
	func NopCloser(r io.Reader) io.ReadCloser
		* 把Reader包装成ReadClose，会提供一个空实现的Close方法

	func ReadAll(r io.Reader) ([]byte, error)
		* 读取r中的所有数据返回

	func ReadDir(dirname string) ([]os.FileInfo, error)
	func ReadFile(filename string) ([]byte, error)
		* 读取文件中的所有数据返回

	func TempDir(dir, pattern string) (name string, err error)
	func TempFile(dir, pattern string) (f *os.File, err error)
		* 创建临时目录/文件，需要自己删除
		* 如果 dir 是空字符串，那么会默认的在系统的临时目录去创建

	func WriteFile(filename string, data []byte, perm os.FileMode) error
		* 快速的写入数据到文件中