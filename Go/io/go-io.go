----------------
变量
----------------
	const (
		// Exactly one of O_RDONLY, O_WRONLY, or O_RDWR must be specified.
		O_RDONLY int = syscall.O_RDONLY // open the file read-only.
		O_WRONLY int = syscall.O_WRONLY // open the file write-only.
		O_RDWR   int = syscall.O_RDWR   // open the file read-write.
		// The remaining values may be or'ed in to control behavior.
		O_APPEND int = syscall.O_APPEND // append data to the file when writing.
		O_CREATE int = syscall.O_CREAT  // create a new file if none exists.
		O_EXCL   int = syscall.O_EXCL   // used with O_CREATE, file must not exist.
		O_SYNC   int = syscall.O_SYNC   // open for synchronous I/O.
		O_TRUNC  int = syscall.O_TRUNC  // truncate regular writable file when opened.
	)

	const (
			SeekStart   = 0 // seek relative to the origin of the file
			SeekCurrent = 1 // seek relative to the current offset
			SeekEnd     = 2 // seek relative to the end
		)

----------------
接口
----------------
	type Reader interface {
		Read(p []byte) (n int, err error)
	}
	type Writer interface {
		Write(p []byte) (n int, err error)
	}
	type Closer interface {
		Close() error
	}
	type Seeker interface {
		Seek(offset int64, whence int) (int64, error)
	}
	type ReadWriter interface {
		Reader
		Writer
	}
	type ReadCloser interface {
		Reader
		Closer
	}
	type WriteCloser interface {
		Writer
		Closer
	}
	type ReadWriteCloser interface {
		Reader
		Writer
		Closer
	}
	type ReadSeeker interface {
		Reader
		Seeker
	}
	type WriteSeeker interface {
		Writer
		Seeker
	}
	type ReadWriteSeeker interface {
		Reader
		Writer
		Seeker
	}
	type ReaderFrom interface {
		ReadFrom(r Reader) (n int64, err error)
	}
	type WriterTo interface {
		WriteTo(w Writer) (n int64, err error)
	}
	type ReaderAt interface {
		ReadAt(p []byte, off int64) (n int, err error)
	}
	type WriterAt interface {
		WriteAt(p []byte, off int64) (n int, err error)
	}
	type ByteReader interface {
		ReadByte() (byte, error)
	}
	type ByteScanner interface {
		ByteReader
		UnreadByte() error
	}
	type ByteWriter interface {
		WriteByte(c byte) error
	}
	type RuneReader interface {
		ReadRune() (r rune, size int, err error)
	}
	type RuneScanner interface {
		RuneReader
		UnreadRune() error
	}
	type StringWriter interface {
		WriteString(s string) (n int, err error)
	}
	type LimitedReader struct {
		R Reader // underlying reader
		N int64  // max bytes remaining
	}
	type SectionReader struct {
		r     ReaderAt
		base  int64
		off   int64
		limit int64
	}

----------------
方法
----------------
	func WriteString(w Writer, s string) (n int, err error)
	func ReadAtLeast(r Reader, buf []byte, min int) (n int, err error)
	func ReadFull(r Reader, buf []byte) (n int, err error)
	func CopyN(dst Writer, src Reader, n int64) (written int64, err error)
	func Copy(dst Writer, src Reader) (written int64, err error)
	func CopyBuffer(dst Writer, src Reader, buf []byte) (written int64, err error)
	func LimitReader(r Reader, n int64) Reader
	
	func NewSectionReader(r ReaderAt, off int64, n int64) *SectionReader
