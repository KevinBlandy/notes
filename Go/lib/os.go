---------------
os
---------------
---------------
����
---------------
	* ����������������һ��������Զ�ǵ�ǰִ���ļ��ľ���·��
		var Args []string
	
		[C:\Users\KevinBlandy\AppData\Local\Temp\___go_build_main_go.exe]

	* ��׼������/���/�쳣��
		var (
			Stdin  = NewFile(uintptr(syscall.Stdin), "/dev/stdin")
			Stdout = NewFile(uintptr(syscall.Stdout), "/dev/stdout")
			Stderr = NewFile(uintptr(syscall.Stderr), "/dev/stderr")
		)

	
	* �ļ����ܷ������쳣
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

	
	* �ļ��Ĵ򿪷�ʽ
		const (
			// Exactly one of O_RDONLY, O_WRONLY, or O_RDWR must be specified.
			O_RDONLY int = syscall.O_RDONLY // ֻ��
			O_WRONLY int = syscall.O_WRONLY // ֻд
			O_RDWR   int = syscall.O_RDWR   //��д
			// The remaining values may be or'ed in to control behavior.
			O_APPEND int = syscall.O_APPEND //���ļ������
			O_CREATE int = syscall.O_CREAT  // ����ļ����������ȴ���
			O_EXCL   int = syscall.O_EXCL   // ��O_CREATEһ��ʹ�ã��ļ����벻�ܴ���
			O_SYNC   int = syscall.O_SYNC   // ��ͬ��I/O�ķ�ʽ��
			O_TRUNC  int = syscall.O_TRUNC  // �ļ���ʱ�ü��ļ������ȱ���Ϊ0�����Բ��䣩
		)
	
	* �ļ��ƶ������λ��
		const (
			SEEK_SET int = 0 // seek relative to the origin of the file
			SEEK_CUR int = 1 // seek relative to the current offset
			SEEK_END int = 2 // seek relative to the end
		)
	
	* �ļ���·���ָ���
		const (
			PathSeparator     = '/' // OS-specific path separator
			PathListSeparator = ':' // OS-specific path list separator
		)
	
	* �ڶ�
		const DevNull = "/dev/null"
	
	var ErrProcessDone = errors.New("os: process already finished")


---------------
type
---------------
	# type DirEntry = fs.DirEntry
		
		* Ŀ¼�µ��ļ���Ϣ

		func ReadDir(name string) ([]DirEntry, error)
			* ��ȡһ��Ŀ¼�µ������ļ�/�ļ�����Ϣ
			* ���name����Ŀ¼���򷵻��쳣��Ϣ
				readdir ...: The system cannot find the path specified.
	
	# type File struct 
		func Create(name string) (*File, error)
		func NewFile(fd uintptr, name string) *File
			* �����µ��ļ���������ָ������

		func Open(name string) (*File, error)
			* ���ļ���ֻ��

		func OpenFile(name string, flag int, perm FileMode) (*File, error)
			* ���ļ�ָ�������Լ�����ʱ��Ȩ����Ϣ

		func (f *File) Chdir() error
		func (f *File) Chmod(mode FileMode) error
		func (f *File) Chown(uid, gid int) error
		func (f *File) Close() error
		func (f *File) Fd() uintptr
		func (f *File) Name() string
			* �����ļ��ľ���·��

		func (f *File) Read(b []byte) (n int, err error)
		func (f *File) ReadAt(b []byte, off int64) (n int, err error)
		func (f *File) ReadFrom(r io.Reader) (n int64, err error)
		func (f *File) Readdir(n int) ([]FileInfo, error)
			* ����Ŀ¼�µ��ļ���Ϣ�б�nָ����෵���ļ�����
			* ���n <= 0��Readdir����Ŀ¼�е�����FileInfo

		func (f *File) Readdirnames(n int) (names []string, err error)
			* ����Ŀ¼�µ��ļ����ƣ�nָ����෵���ļ�����
			* ���n <= 0��Readdir����Ŀ¼�е�����FileInfo

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
			Name() string       // �����ļ����ƣ�����·��
			Size() int64        // length in bytes for regular files; system-dependent for others
			Mode() FileMode     // �ļ���Ȩ����Ϣ
			ModTime() time.Time // modification time
			IsDir() bool        // abbreviation for Mode().IsDir()
			Sys() interface{}   // underlying data source (can return nil)
		}

		* �ļ�����Ϣ�ṹ��

		func Lstat(name string) (FileInfo, error)
			* ����һ���ļ�����Ϣ�����ǵ��ļ���һ��������ʱ�������������ӵ���Ϣ�����������õ��ļ�����Ϣ��
			* Symlink��Windows�в�������

		func Stat(name string) (FileInfo, error)
	
	# type FileMode uint32
		* �ļ�������
		* Ԥ����
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
			* �Ƿ���Ŀ¼
		func (m FileMode) IsRegular() bool
			* �Ƿ��ǳ����ļ�
		func (m FileMode) Perm() FileMode
		func (m FileMode) String() string
			* ��Ȩ����Ϣ���ַ�����
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

		* ���̵������Ϣ
	
	# type Process struct {
			Pid int
				* ���е�id
		}
		
		* ������Ϣ

		func FindProcess(pid int) (*Process, error)
			* ����ID����Ϣ��ȡ������Ϣ

		func StartProcess(name string, argv []string, attr *ProcAttr) (*Process, error)
			* ���ݲ�������Ϣ������һ���µĽ���

		func (p *Process) Kill() error
		func (p *Process) Release() error
		func (p *Process) Signal(sig Signal) error
			* �����̷����źţ�����Windows��֧�֣����쳣
				not supported by windows
			* ��������Ѿ����������� ErrProcessDone 

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

		* ����״̬
	
	# type Signal interface {
			String() string
			Signal() // to distinguish from other Stringers
		}

		* �����ź�
		* Ԥ����(syscall���ж���)
			var (
				Interrupt Signal = syscall.SIGINT
					* �ж��ź�
				Kill      Signal = syscall.SIGKILL
					* Kill�ź�
			)
		
		
	# type SyscallError struct {
			Syscall string
			Err     error
		}
		func (e *SyscallError) Error() string
		func (e *SyscallError) Timeout() bool
		func (e *SyscallError) Unwrap() error
	

---------------
����
---------------
	func Chdir(dir string) error
	func Chmod(name string, mode FileMode) error
	func Chown(name string, uid, gid int) error
		* �޸�Ȩ��/��������Ϣ

	func Chtimes(name string, atime time.Time, mtime time.Time) error
		* �ı�ʱ����Ϣ

	func Clearenv()
	func Environ() []string
		* ��ȡ����������ÿһ���ַ�Ԫ�ض���: name=value ���ָ�ʽ

	func Executable() (string, error)
		* ���ؿ�ִ���ļ���·��

	func Exit(code int)
	func Expand(s string, mapping func(string) string) string
	func ExpandEnv(s string) string
	func Getegid() int
	func Getenv(key string) string
		* ��ȡָ���Ļ�������
	
	func Geteuid() int
	func Getgid() int
	func Getgroups() ([]int, error)
		* ��ȡ��ǰ�û��������ID

	func Getpagesize() int
	func Getpid() int
		* ��ȡ��ǰ����ID
		* ��ȡ����PIDд�뵽�ļ�
			var pid = os.Getpid()
			if config.App.PidFile != "" {
				// д��PID�ļ�
				if err := os.WriteFile(config.App.PidFile, []byte(fmt.Sprintf("%d", pid)), 0x775); err != nil {
					log.Printf("д��PID�ļ��쳣��%s\n", err.Error()) // �����쳣
				} else {
					defer func() {
						if err := os.Remove(config.App.PidFile); err != nil {
							log.Printf("ɾ��PID�ļ��쳣��%s\n", err.Error())
						}
					}()
				}
			}

	func Getppid() int
	func Getuid() int
		* ��ȡ��ǰ�û���UID

	func Getwd() (dir string, err error)
	func Hostname() (name string, err error)

	func IsExist(err error) bool
	func IsNotExist(err error) bool
	func IsPathSeparator(c uint8) bool
	func IsPermission(err error) bool
	func IsTimeout(err error) bool
		* ���쳣���ж� 

	func Lchown(name string, uid, gid int) error
	func Link(oldname, newname string) error
		* ����һ��Ӳ����

	func LookupEnv(key string) (string, bool)
	func Mkdir(name string, perm FileMode) error
	func MkdirAll(path string, perm FileMode) error
		* ����Ŀ¼��All���Դ����༶Ŀ¼

	func NewSyscallError(syscall string, err error) error

	func Pipe() (r *File, w *File, err error)
		* ����һ���ܵ�

	func Readlink(name string) (string, error)

	func Remove(name string) error
	func RemoveAll(path string) error
		* ɾ���ļ�

	func Rename(oldpath, newpath string) error
		* ��������������mv��������move������һ����

	func SameFile(fi1, fi2 FileInfo) bool
	func Setenv(key, value string) error
	func Symlink(oldname, newname string) error

	func TempDir() string
	func Truncate(name string, size int64) error
		* �ü�һ���ļ���n���ֽڣ����˾�ɾ�����˾���䣬����һ��Ҫ��size���ֽ�
		* ����ļ���СС��n����ᱣ��ԭʼ���ݣ�ʣ�µ����null
		* �������0���ͻ����


	func ReadFile(name string) ([]byte, error)
		* ��ȡ�ļ�
	
	func MkdirTemp(dir, pattern string) (string, error)
	func CreateTemp(dir, pattern string) (*File, error)
		* ������ʱĿ¼/�ļ�����Ҫ�Լ�ɾ��
		* ��� dir �ǿ��ַ�������ô��Ĭ�ϵ���ϵͳ����ʱĿ¼ȥ����
		* os����
	
	func WriteFile(name string, data []byte, perm FileMode) error
		* ���ٵ�д�����ݵ��ļ���

	func Unsetenv(key string) error
	func UserCacheDir() (string, error)
	func UserConfigDir() (string, error)
	func UserHomeDir() (string, error)

------------------------
Demo
------------------------
	# �ݹ�ͳ��ָ��Ŀ¼�������ļ��Ĵ�С
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