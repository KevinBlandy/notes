---------------
os
---------------
---------------
变量
---------------
	* 程序启动参数，第一个参数永远是当前执行文件的绝对路径
		var Args []string
	
		[C:\Users\KevinBlandy\AppData\Local\Temp\___go_build_main_go.exe]

	* 标准的输入/输出/异常流
		var (
			Stdin  = NewFile(uintptr(syscall.Stdin), "/dev/stdin")
			Stdout = NewFile(uintptr(syscall.Stdout), "/dev/stdout")
			Stderr = NewFile(uintptr(syscall.Stderr), "/dev/stderr")
		)

	
	* 文件可能发生的异常
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

	
	* 文件的打开方式
		const (
			// Exactly one of O_RDONLY, O_WRONLY, or O_RDWR must be specified.
			O_RDONLY int = syscall.O_RDONLY // 只读
			O_WRONLY int = syscall.O_WRONLY // 只写
			O_RDWR   int = syscall.O_RDWR   //读写
			// The remaining values may be or'ed in to control behavior.
			O_APPEND int = syscall.O_APPEND //往文件中添加
			O_CREATE int = syscall.O_CREAT  // 如果文件不存在则先创建
			O_EXCL   int = syscall.O_EXCL   // 和O_CREATE一起使用，文件必须不能存在
			O_SYNC   int = syscall.O_SYNC   // 以同步I/O的方式打开
			O_TRUNC  int = syscall.O_TRUNC  // 文件打开时裁剪文件
		)
	
	* 文件移动的相对位置
		const (
			SEEK_SET int = 0 // seek relative to the origin of the file
			SEEK_CUR int = 1 // seek relative to the current offset
			SEEK_END int = 2 // seek relative to the end
		)
	
	* 文件和路径分隔符
		const (
			PathSeparator     = '/' // OS-specific path separator
			PathListSeparator = ':' // OS-specific path list separator
		)
	
	* 黑洞
		const DevNull = "/dev/null"
	
	var ErrProcessDone = errors.New("os: process already finished")


---------------
type
---------------
	# type DirEntry = fs.DirEntry
		
		* 目录下的文件信息

		func ReadDir(name string) ([]DirEntry, error)
			* 读取一个目录下的所有文件/文件夹信息
			* 如果name不是目录，则返回异常信息
				readdir ...: The system cannot find the path specified.
	
	# type File struct 
		func Create(name string) (*File, error)
		func NewFile(fd uintptr, name string) *File

		func Open(name string) (*File, error)
			* 打开文件，只读

		func OpenFile(name string, flag int, perm FileMode) (*File, error)
			* 打开文件指定属性以及创建时的权限信息

		func (f *File) Chdir() error
		func (f *File) Chmod(mode FileMode) error
		func (f *File) Chown(uid, gid int) error
		func (f *File) Close() error
		func (f *File) Fd() uintptr
		func (f *File) Name() string
			* 返回文件的绝对路径

		func (f *File) Read(b []byte) (n int, err error)
		func (f *File) ReadAt(b []byte, off int64) (n int, err error)
		func (f *File) ReadFrom(r io.Reader) (n int64, err error)
		func (f *File) Readdir(n int) ([]FileInfo, error)
			* 返回目录下的文件信息列表，n指定最多返回文件个数
			* 如果n <= 0，Readdir返回目录中的所有FileInfo

		func (f *File) Readdirnames(n int) (names []string, err error)
			* 返回目录下的文件名称，n指定最多返回文件个数
			* 如果n <= 0，Readdir返回目录中的所有FileInfo

		func (f *File) Seek(offset int64, whence int) (ret int64, err error)
		func (f *File) SetDeadline(t time.Time) error
		func (f *File) SetReadDeadline(t time.Time) error
		func (f *File) SetWriteDeadline(t time.Time) error
		func (f *File) Stat() (FileInfo, error)
		func (f *File) Sync() error
		func (f *File) SyscallConn() (syscall.RawConn, error)
		func (f *File) Truncate(size int64) error
		func (f *File) Write(b []byte) (n int, err error)
		func (f *File) WriteAt(b []byte, off int64) (n int, err error)
		func (f *File) WriteString(s string) (n int, err error)
	
	# type FileInfo interface {
			Name() string       // 返回文件名称，不带路径
			Size() int64        // length in bytes for regular files; system-dependent for others
			Mode() FileMode     // 文件的权限信息
			ModTime() time.Time // modification time
			IsDir() bool        // abbreviation for Mode().IsDir()
			Sys() interface{}   // underlying data source (can return nil)
		}

		* 文件的信息结构体

		func Lstat(name string) (FileInfo, error)
			* 返回一个文件的信息，但是当文件是一个软链接时，它返回软链接的信息，而不是引用的文件的信息。
			* Symlink在Windows中不工作。

		func Stat(name string) (FileInfo, error)
	
	# type FileMode uint32
		* 文件的类型
		* 预定义
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
			* 是否是目录
		func (m FileMode) IsRegular() bool
			* 是否是常规文件
		func (m FileMode) Perm() FileMode
		func (m FileMode) String() string
			* 返权限信息的字符描述
				-rw-rw-rw-
	
	# type LinkError struct {
			Op  string
			Old string
			New string
			Err error
		}
		
		func (e *LinkError) Error() string
		func (e *LinkError) Unwrap() error
	
	# type PathError struct {
			Op   string
			Path string
			Err  error
		}
		
		func (e *PathError) Error() string
		func (e *PathError) Timeout() bool
		func (e *PathError) Unwrap() error
		
	
	# type ProcAttr struct {
			Dir string
			Env []string
			Files []*File
			Sys *syscall.SysProcAttr
		}

		* 进程的相关信息
	
	# type Process struct {
			Pid int
				* 进行的id
		}
		
		* 进程信息

		func FindProcess(pid int) (*Process, error)
			* 根据ID等信息获取进程信息

		func StartProcess(name string, argv []string, attr *ProcAttr) (*Process, error)
			* 根据参数等信息，创建一个新的进程

		func (p *Process) Kill() error
		func (p *Process) Release() error
		func (p *Process) Signal(sig Signal) error
			* 给进程发送信号
			* 如果进程已经结束，返回 ErrProcessDone 

		func (p *Process) Wait() (*ProcessState, error)
	
	# type ProcessState struct
		func (p *ProcessState) ExitCode() int
		func (p *ProcessState) Exited() bool
		func (p *ProcessState) Pid() int
		func (p *ProcessState) String() string
		func (p *ProcessState) Success() bool
		func (p *ProcessState) Sys() interface{}
		func (p *ProcessState) SysUsage() interface{}
		func (p *ProcessState) SystemTime() time.Duration
		func (p *ProcessState) UserTime() time.Duration

		* 进程状态
	
	# type Signal interface {
			String() string
			Signal() // to distinguish from other Stringers
		}

		* 输入信号
		* 预定义(syscall中有定义)
			var (
				Interrupt Signal = syscall.SIGINT
					* 中断信号
				Kill      Signal = syscall.SIGKILL
					* Kill信号
			)
		
		
	# type SyscallError struct {
			Syscall string
			Err     error
		}
		func (e *SyscallError) Error() string
		func (e *SyscallError) Timeout() bool
		func (e *SyscallError) Unwrap() error
	

---------------
方法
---------------
	func Chdir(dir string) error
	func Chmod(name string, mode FileMode) error
	func Chown(name string, uid, gid int) error
		* 修改权限/所有者信息

	func Chtimes(name string, atime time.Time, mtime time.Time) error
		* 改变时间信息

	func Clearenv()
	func Environ() []string
		* 获取环境变量，每一个字符元素都是: name=value 这种格式

	func Executable() (string, error)
		* 返回可执行文件的路径

	func Exit(code int)
	func Expand(s string, mapping func(string) string) string
	func ExpandEnv(s string) string
	func Getegid() int
	func Getenv(key string) string
		* 获取指定的环境变量
	
	func Geteuid() int
	func Getgid() int
	func Getgroups() ([]int, error)
		* 获取当前用户所属组的ID

	func Getpagesize() int
	func Getpid() int
	func Getppid() int
	func Getuid() int
		* 获取当前用户的UID

	func Getwd() (dir string, err error)
	func Hostname() (name string, err error)

	func IsExist(err error) bool
	func IsNotExist(err error) bool
	func IsPathSeparator(c uint8) bool
	func IsPermission(err error) bool
	func IsTimeout(err error) bool
		* 对异常的判断 

	func Lchown(name string, uid, gid int) error
	func Link(oldname, newname string) error
		* 创建一个硬链接

	func LookupEnv(key string) (string, bool)
	func Mkdir(name string, perm FileMode) error
	func MkdirAll(path string, perm FileMode) error
		* 创建目录，All可以创建多级目录

	func NewSyscallError(syscall string, err error) error

	func Pipe() (r *File, w *File, err error)
		* 返回一个管道

	func Readlink(name string) (string, error)

	func Remove(name string) error
	func RemoveAll(path string) error
		* 删除文件

	func Rename(oldpath, newpath string) error
		* 重命名，类似于mv操作，和move操作是一样的

	func SameFile(fi1, fi2 FileInfo) bool
	func Setenv(key, value string) error
	func Symlink(oldname, newname string) error

	func TempDir() string
	func Truncate(name string, size int64) error
		* 裁剪一个文件到n个字节，多了就删，少了就填充，反正一定要是size个字节
		* 如果文件大小小于n，则会保留原始数据，剩下的填充null
		* 如果设置0，就会清空


	func ReadFile(name string) ([]byte, error)
		* 读取文件
	
	func MkdirTemp(dir, pattern string) (string, error)
	func CreateTemp(dir, pattern string) (*File, error)
		* 创建临时目录/文件，需要自己删除
		* 如果 dir 是空字符串，那么会默认的在系统的临时目录去创建
		* os包下
	
	func WriteFile(name string, data []byte, perm FileMode) error
		* 快速的写入数据到文件中

	func Unsetenv(key string) error
	func UserCacheDir() (string, error)
	func UserConfigDir() (string, error)
	func UserHomeDir() (string, error)

------------------------
Demo
------------------------
	# 递归统计指定目录下所有文件的大小
		func TreeSize(name string) (int64, error) {
			var total int64 = 0
			dirEntry, err := os.ReadDir(name)
			if err != nil {
				return 0, err
			}
			for _, entry := range dirEntry {
				if entry.IsDir() {
					retVal, err := TreeSize(filepath.Join(name, entry.Name()))
					if err != nil {
						return 0, err
					}
					total += retVal
				} else {
					fileInfo, err := entry.Info()
					if err != nil {
						return 0, err
					}
					total += fileInfo.Size()
				}
			}
			return total, nil
		}