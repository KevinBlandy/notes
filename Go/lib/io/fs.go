----------------------
fs
----------------------
	# 定义了文件系统的基本接口。文件系统可以由主机操作系统提供，也可以由其他软件包提供。

----------------------
var
----------------------
	var (
		ErrInvalid    = errInvalid()    // "invalid argument"
		ErrPermission = errPermission() // "permission denied"
		ErrExist      = errExist()      // "file already exists"
		ErrNotExist   = errNotExist()   // "file does not exist"
		ErrClosed     = errClosed()     // "file already closed"
	)
	
	var SkipDir = errors.New("skip this directory")
	var SkipAll = errors.New("skip everything and stop the walk")

----------------------
type
----------------------
	# type DirEntry interface {
			Name() string			// 名称，不是全路径
			IsDir() bool
			Type() FileMode
			Info() (FileInfo, error)
		}
		
		* 目录下面的项

		func FileInfoToDirEntry(info FileInfo) DirEntry
			* 将一个FileInfo 转换为DirEntry

	# type FS interface {
			Open(name string) (File, error)
		}
		
		* 文件系统接口

		func Sub(fsys FS, dir string) (FS, error)
	
	
	# type File interface {
			Stat() (FileInfo, error)
			Read([]byte) (int, error)
			Close() error
		}
		
		* 文件接口
			
	# type FileInfo interface {
			Name() string       // 返回文件名称，不带路径
			Size() int64        // length in bytes for regular files; system-dependent for others
			Mode() FileMode     // 文件的权限信息
			ModTime() time.Time // modification time
			IsDir() bool        // abbreviation for Mode().IsDir()
			Sys() interface{}   // underlying data source (can return nil)
		}
		
		* 文件系统接口

		func Stat(fsys FS, name string) (FileInfo, error)
	
	# type FileMode uint32
		const (
			// The single letters are the abbreviations
			// used by the String method's formatting.
			ModeDir        FileMode = 1 << (32 - 1 - iota) // d: is a directory
			ModeAppend                                     // a: append-only
			ModeExclusive                                  // l: exclusive use
			ModeTemporary                                  // T: temporary file; Plan 9 only
			ModeSymlink                                    // L: symbolic link
			ModeDevice                                     // D: device file
			ModeNamedPipe                                  // p: named pipe (FIFO)
			ModeSocket                                     // S: Unix domain socket
			ModeSetuid                                     // u: setuid
			ModeSetgid                                     // g: setgid
			ModeCharDevice                                 // c: Unix character device, when ModeDevice is set
			ModeSticky                                     // t: sticky
			ModeIrregular                                  // ?: non-regular file; nothing else is known about this file

			// Mask for the type bits. For regular files, none will be set.
			ModeType = ModeDir | ModeSymlink | ModeNamedPipe | ModeSocket | ModeDevice | ModeCharDevice | ModeIrregular

			ModePerm FileMode = 0777 // Unix permission bits
		)
	
		func (m FileMode) IsDir() bool
		func (m FileMode) IsRegular() bool
		func (m FileMode) Perm() FileMode
		func (m FileMode) String() string
		func (m FileMode) Type() FileMode
	
	# type GlobFS interface {
			FS
			Glob(pattern string) ([]string, error)
		}

		* 支持根据pattern检索的FS
	
	# type PathError struct {
			Op   string
			Path string
			Err  error
		}
		func (e *PathError) Error() string
		func (e *PathError) Timeout() bool
		func (e *PathError) Unwrap() error
	
	# type ReadDirFS interface {
			FS
			ReadDir(name string) ([]DirEntry, error)
		}
	
	# type ReadDirFile interface {
			File
			ReadDir(n int) ([]DirEntry, error)
		}
	# type ReadFileFS interface {
			FS
			ReadFile(name string) ([]byte, error)
		}
	
	# type ReadLinkFS interface {
		FS
		ReadLink(name string) (string, error)
		Lstat(name string) (FileInfo, error)
	}

	# type StatFS interface {
			FS
			Stat(name string) (FileInfo, error)
		}
	# type SubFS interface {
			FS
			Sub(dir string) (FS, error)
		}
	# type WalkDirFunc func(path string, d DirEntry, err error) error

----------------------
func
----------------------
	func FormatDirEntry(dir DirEntry) string
	func FormatFileInfo(info FileInfo) string
	func Glob(fsys FS, pattern string) (matches []string, err error)
	func ReadFile(fsys FS, name string) ([]byte, error)
	func ValidPath(name string) bool
	func WalkDir(fsys FS, root string, fn WalkDirFunc) error
		* 递归遍历 fsys 文件系统中的文件
		* root 指定开始遍历的根文件/目录。可以使用 "." 表示从根路径开始遍历
		* fn 遍历到的文件、目录回调
