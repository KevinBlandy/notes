---------------
io os包
---------------
	# 属性
		* 文件类型/属性枚举
			type FileMode uint32
			const (
				// The single letters are the abbreviations
				// used by the String method's formatting.
				ModeDir        FileMode = 1 << (32 - 1 - iota) // d: 是一个目录
				ModeAppend                                     // a: append-only
				ModeExclusive                                  // l: exclusive use
				ModeTemporary                                  // T: temporary file; Plan 9 only
				ModeSymlink                                    // L: symbolic link
				ModeDevice                                     // D: 是一个设备文件
				ModeNamedPipe                                  // p: named pipe (FIFO)
				ModeSocket                                     // S: Unix domain socket
				ModeSetuid                                     // u: setuid
				ModeSetgid                                     // g: setgid
				ModeCharDevice                                 // c: Unix character device, when ModeDevice is set
				ModeSticky                                     // t: sticky
				ModeIrregular                                  // ?: non-regular file; nothing else is known about this file

				// Mask for the type bits. For regular files, none will be set.
				ModeType = ModeDir | ModeSymlink | ModeNamedPipe | ModeSocket | ModeDevice | ModeCharDevice | ModeIrregular

				ModePerm FileMode = 0777 // Linux下的最高的权限了：chmod 777
			)
		
		* 跨平台的分隔符，定义在 os包
			const (
				PathSeparator     = '\\' // OS-specific path separator
				PathListSeparator = ';'  // OS-specific path list separator
			)
		
		* 文件结束符异常
			var EOF = errors.New("EOF")
		
		* 打开文件的IO方式
				const (
					O_RDONLY int = syscall.O_RDONLY // 只读
					O_WRONLY int = syscall.O_WRONLY // 只写
					O_RDWR   int = syscall.O_RDWR   // 读写
					O_APPEND int = syscall.O_APPEND // 追加模式
					O_CREATE int = syscall.O_CREAT  // 文件不存在，就创建新的
					O_EXCL   int = syscall.O_EXCL   // 和 O_CREATE配合使用，如果文件存在则抛出异常
					O_SYNC   int = syscall.O_SYNC   // 打开异步IO
					O_TRUNC  int = syscall.O_TRUNC  // 打开就会清空文件
				)
		
		* 系统标准的输入/输出/错误流
			Stdin  = NewFile(uintptr(syscall.Stdin), "/dev/stdin")
			Stdout = NewFile(uintptr(syscall.Stdout), "/dev/stdout")
			Stderr = NewFile(uintptr(syscall.Stderr), "/dev/stderr")
		
	# 常用方法
		* 打开文件
			func OpenFile(name string, flag int, perm FileMode) (*File, error)
				* 打开文件
					name 文件名称
					flag IO方式，多个IO方式可以用 | 运算表示
					perm 如果文件不存在的情况下，并且使用的是 O_CREATE，那么就需要通过这个参数指定创建文件的权限信息(Linux用的一般)，它可以使用8进制表示
				
					file, err := os.OpenFile("D:\\test.txt", os.O_EXCL | os.O_CREATE | os.O_RDWR, os.ModePerm) // 文件如果存在，抛出异常，不存在就创建，打开读写模式

					

			func Open(name string) (*File, error)
				* 以只读方式打开文件
				* 本质上就是返回: OpenFile(name, O_RDONLY, 0)
					
			
		* 创建文件
			func Create(name string) (*File, error) 
		
		* 获取指定描述符文件
			func NewFile(fd uintptr, name string) *File 
		
		* 获取指定文件的信息
			func Stat(name string) (FileInfo, error) 
		
		
		* 重命名文件
			func Rename(oldpath, newpath string) error
		
		* 创建文件夹
			func Mkdir(name string, perm FileMode) error
				* 创建文件夹，如果父级文件夹不存在，抛出异常
			func MkdirAll(path string, perm FileMode) error
				* 创建多级文件，如果不存在都会创建
		
		
		* 删除文件
			func Remove(name string) error
			func RemoveAll(path string) error
		
		* 获取管道流
			func Pipe() (r *File, w *File, err error)
		
		* 截断文件
			func Truncate(name string, size int64) error
		
		* 切换工作目录
			func Chdir(dir string) error
		
		* 修改文件的用户/用户组/权限信息
			func Chown(name string, uid, gid int)
			func Lchown(name string, uid, gid int) error
			func Chmod(name string, mode FileMode) error
		
		* 修改文件的时间属性
			func Chtimes(name string, atime time.Time, mtime time.Time) error
	
	# 系统目录相关
		* 获取临时目录
			func TempDir() string 
		
		* 获取数据缓存目录
			func UserCacheDir() (string, error)
		
		* 获取配置目录
			func UserConfigDir() (string, error)
		
		* 获取用户的home目录
			func UserHomeDir() (string, error)
		
	# 链接相关
		* 创建硬连接
			func Link(oldname, newname string) error
			
		* 返回命名的符号链接的目标
			func Readlink(name string) (string, error)
			
		
		func Symlink(oldname, newname string) error
		

	# 文件 File 的常用方法
		func (f *File) Name() string 
		func (f *File) Close() error
			* 释放资源
		
		func (f *File) Read(b []byte) (n int, err error)
			* 读取字节数据到文件，返回读取到的字节数和异常信息
			* 如果读取到末尾，返回 (0, io.EOF)

		func (f *File) ReadAt(b []byte, off int64) (n int, err error)
			

		func (f *File) ReadFrom(r io.Reader) (n int64, err error)
		func (f *File) Write(b []byte) (n int, err error)
		func (f *File) WriteAt(b []byte, off int64) (n int, err error)
		func (f *File) Seek(offset int64, whence int) (ret int64, err error)
			* 移动指针
				offset 偏移
				whence 位置
						os.SEEK_SET int = 0 // 文件开头
						os.SEEK_CUR int = 1 // 当前位置
						os.SEEK_END int = 2 // 文件结束

		func (f *File) WriteString(s string) (n int, err error)
		func (f *File) Chmod(mode FileMode) error
		func (f *File) SetDeadline(t time.Time) error
		func (f *File) SetReadDeadline(t time.Time) error
		func (f *File) SetWriteDeadline(t time.Time) error
		func (f *File) Stat() (FileInfo, error)
			* 返回文件信息，是一个接口
				type FileInfo interface {
					Name() string       // base name of the file
					Size() int64        // length in bytes for regular files; system-dependent for others
					Mode() FileMode     // file mode bits
					ModTime() time.Time // modification time
					IsDir() bool        // abbreviation for Mode().IsDir()
					Sys() interface{}   // underlying data source (can return nil)
				}

		func (f *File) Chown(uid, gid int) error
			* 修改所有者/组

		func (f *File) Truncate(size int64) error
		func (f *File) Sync() error
		func (f *File) SyscallConn() (syscall.RawConn, error)
		func (f *File) Chdir()
		func (f *File) Fd() uintptr
		func (f *File) Readdir(n int) ([]FileInfo, error)
		func (f *File) Readdirnames(n int) (names []string, err error)
		


------------------
异常的处理
------------------
	# 对于文件的异常，定义了几个接口
		* PathError
			type PathError struct {
				Op   string
				Path string
				Err  error
			}
			func (e *PathError) Error() string { return e.Op + " " + e.Path + ": " + e.Err.Error() }
			func (e *PathError) Unwrap() error { return e.Err }
			// Timeout reports whether this error represents a timeout.
			func (e *PathError) Timeout() bool {
				t, ok := e.Err.(timeout)
				return ok && t.Timeout()
			}
		* SyscallError
			type SyscallError struct {
				Syscall string
				Err     error
			}
			func (e *SyscallError) Error() string { return e.Syscall + ": " + e.Err.Error() }
			func (e *SyscallError) Unwrap() error { return e.Err }
			// Timeout reports whether this error represents a timeout.
			func (e *SyscallError) Timeout() bool {
				t, ok := e.Err.(timeout)
				return ok && t.Timeout()
			}
	
	# 预定义的异常
		var (
			// ErrInvalid indicates an invalid argument.
			// Methods on File will return this error when the receiver is nil.
			ErrInvalid = errInvalid() // "invalid argument"

			ErrPermission       = errPermission()       // "permission denied"
			ErrExist            = errExist()            // "file already exists"
			ErrNotExist         = errNotExist()         // "file does not exist"
			ErrClosed           = errClosed()           // "file already closed"
			ErrNoDeadline       = errNoDeadline()       // "file type does not support deadline"
			ErrDeadlineExceeded = errDeadlineExceeded() // "i/o timeout"
		)
	
	# 异常的判断方法
		func IsExist(err error) bool 
		func Lstat(name string) (FileInfo, error) 
		func IsNotExist(err error) bool 
			* 判断该异常是不是因为文件不存在导致的
				if _, err := os.Open("D:\\noExists"); os.IsNotExist(err){
					fmt.Println("文件不存在")
				}

		func IsPermission(err error) bool 
		func IsTimeout(err error) bool 
		

		

---------------
demo
---------------
	# 读取文件
		file, err := os.Open("D:\\javaweb_community.sql")
		if err != nil {
			fmt.Println("异常：" + err.Error())
		}
		defer file.Close()
		// 数据缓存容器
		buffer := make([]byte, 1024)
		for  {
			count, err := file.Read(buffer)
			if err == io.EOF || count == 0{
				// 读到了末尾
				break
			}
			// 读取到的字节数据
			data := buffer[:count]
			fmt.Println(string(data))
		}
	

