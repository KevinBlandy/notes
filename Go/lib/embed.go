----------------------
embed 
----------------------

----------------------
type 
----------------------
	# type FS struct {
		}

		* 实现了 fs.FS 接口
	
		func (f FS) Open(name string) (fs.File, error)
		func (f FS) ReadDir(name string) ([]fs.DirEntry, error)
			* 文件路径必须要明确写出自己的父级目录，否则会报错
			* 因为嵌入资源是按它存储路径相同的结构存储的，和通配符怎么指定无关。

		func (f FS) ReadFile(name string) ([]byte, error)

		* 实现了 fs.FS 接口，可以用它的遍历方法
			func WalkDir(fsys FS, root string, fn WalkDirFunc) error 

----------------------
func 
----------------------