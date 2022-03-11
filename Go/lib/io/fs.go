----------------------
fs
----------------------
	# �������ļ�ϵͳ�Ļ����ӿڡ��ļ�ϵͳ��������������ϵͳ�ṩ��Ҳ����������������ṩ��

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

----------------------
type
----------------------
	# type DirEntry interface {
			Name() string			// ���ƣ�����ȫ·��
			IsDir() bool
			Type() FileMode
			Info() (FileInfo, error)
		}
		
		* Ŀ¼�������

	# type FS interface {
			Open(name string) (File, error)
		}
		
		* �ļ�ϵͳ�ӿ�

		func Sub(fsys FS, dir string) (FS, error)
	
	
	# type File interface {
			Stat() (FileInfo, error)
			Read([]byte) (int, error)
			Close() error
		}
		
		* �ļ��ӿ�
			
	# type FileInfo interface {
			Name() string       // �����ļ����ƣ�����·��
			Size() int64        // length in bytes for regular files; system-dependent for others
			Mode() FileMode     // �ļ���Ȩ����Ϣ
			ModTime() time.Time // modification time
			IsDir() bool        // abbreviation for Mode().IsDir()
			Sys() interface{}   // underlying data source (can return nil)
		}
		
		* �ļ�ϵͳ�ӿ�

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

		* ֧�ָ���pattern������FS
	
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
	func Glob(fsys FS, pattern string) (matches []string, err error)
	func ReadFile(fsys FS, name string) ([]byte, error)
	func ValidPath(name string) bool
	func WalkDir(fsys FS, root string, fn WalkDirFunc) error
	func FileInfoToDirEntry(info FileInfo) DirEntry
		* ��һ��FileInfo ת��ΪDirEntry
