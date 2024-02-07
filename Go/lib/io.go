--------------------------
io
--------------------------
	# 大都是IO接口的定义

--------------------------
变量
--------------------------
	* /dev/null
		var Discard Writer = discard{}
	
	* 移动的相对位置
		const (
			SeekStart   = 0 // seek relative to the origin of the file
			SeekCurrent = 1 // seek relative to the current offset
			SeekEnd     = 2 // seek relative to the end
		)
	
	* 结束符
		var EOF = errors.New("EOF")
	
	* 异常信息
		var ErrClosedPipe = errors.New("io: read/write on closed pipe")
		var ErrNoProgress = errors.New("multiple Read calls return no data or error")
		var ErrShortBuffer = errors.New("short buffer")
		var ErrShortWrite = errors.New("short write")
		var ErrUnexpectedEOF = errors.New("unexpected EOF")
	

--------------------------
type
--------------------------
	# type ByteReader interface {
			ReadByte() (byte, error)
		}
	# type ByteScanner interface {
			ByteReader
			UnreadByte() error
		}
	
	# type ByteWriter interface {
			WriteByte(c byte) error
		}
	# type Closer interface {
			Close() error
		}
	# type LimitedReader struct {
			R Reader // underlying reader
			N int64  // max bytes remaining
		}
		
		func (l *LimitedReader) Read(p []byte) (n int, err error)
	
	# type OffsetWriter struct {
		}
		func NewOffsetWriter(w WriterAt, off int64) *OffsetWriter
		func (o *OffsetWriter) Seek(offset int64, whence int) (int64, error)
		func (o *OffsetWriter) Write(p []byte) (n int, err error)
		func (o *OffsetWriter) WriteAt(p []byte, off int64) (n int, err error)

	# type PipeReader struct
		func (r *PipeReader) Close() error
		func (r *PipeReader) CloseWithError(err error) error
		func (r *PipeReader) Read(data []byte) (n int, err error)
	
	# type PipeWriter struct
		func (w *PipeWriter) Close() error
		func (w *PipeWriter) CloseWithError(err error) error
		func (w *PipeWriter) Write(data []byte) (n int, err error)
	
	# type ReadCloser interface {
			Reader
			Closer
		}
		func NopCloser(r Reader) ReadCloser

		* 把Reader包装成一个ReadClose，对Close()的进行空实现
	
	# type ReadSeekCloser interface {
			Reader
			Seeker
			Closer
		}
	
	# type ReadSeeker interface {
			Reader
			Seeker
		}
	# type ReadWriteCloser interface {
			Reader
			Writer
			Closer
		}
	# type ReadWriteSeeker interface {
			Reader
			Writer
			Seeker
		}
	
	# type ReadWriter interface {
			Reader
			Writer
		}
	# type Reader interface {
			Read(p []byte) (n int, err error)
		}
	
		func LimitReader(r Reader, n int64) Reader
		func MultiReader(readers ...Reader) Reader
			* 连接多个Reader，所有Reader都读取完毕才会返回 EOF
			* 如果任何一个Reader，返回一个非nil、非EOF的错误，Read将返回该错误

		func TeeReader(r Reader, w Writer) Reader
			* 返回一个reader，读这个reader，就是相当于从 r 读取数据
			* 并且读取出来的数据，还会被自动写入到w
	
	# type ReaderAt interface {
			ReadAt(p []byte, off int64) (n int, err error)
		}

	# type ReaderFrom interface {
			ReadFrom(r Reader) (n int64, err error)
		}
		
		* 底层可能使用零拷贝技术

	# type RuneReader interface {
			ReadRune() (r rune, size int, err error)
		}
	# type RuneScanner interface {
			RuneReader
			UnreadRune() error
		}
	# type SectionReader struct

		func NewSectionReader(r ReaderAt, off int64, n int64) *SectionReader
		
		* 区间读写
		
		func (s *SectionReader) Outer() (r ReaderAt, off int64, n int64)
		func (s *SectionReader) Read(p []byte) (n int, err error)
		func (s *SectionReader) ReadAt(p []byte, off int64) (n int, err error)
		func (s *SectionReader) Seek(offset int64, whence int) (int64, error)
		func (s *SectionReader) Size() int64
	
	# type Seeker interface {
			Seek(offset int64, whence int) (int64, error)
		}
	
	# type StringWriter interface {
			WriteString(s string) (n int, err error)
		}
	# type WriteCloser interface {
			Writer
			Closer
		}
	# type WriteSeeker interface {
			Writer
			Seeker
		}
	# type Writer interface {
			Write(p []byte) (n int, err error)
		}
		
		func MultiWriter(writers ...Writer) Writer
			* 把多个writer绑定到一个writer返回
			* 往返回writer写入数据的时候，会复制到所有的Writer中，类似于 Unix 的 tee(1) 命令
			* 如果任何一个Writer写入异常，就会立即停止并且返回

	
	# type WriterAt interface {
			WriteAt(p []byte, off int64) (n int, err error)
		}
	
	# type WriterTo interface {
			WriteTo(w Writer) (n int64, err error)
		}

		* 底层可能使用零拷贝技术
	

--------------------------
方法
--------------------------
	func CopyBuffer(dst Writer, src Reader, buf []byte) (written int64, err error)
		* 与copy方法一样，但是这里是同坐自己提供了的缓冲区进行复制的
		* 如果buf是nil的话，系统会自己创建一个，反之如果buf的len是0，则会抛出异常

	func CopyN(dst Writer, src Reader, n int64) (written int64, err error)
		* 复制，限制复制的大小

	func Pipe() (*PipeReader, *PipeWriter)
		* 返回pipe连接通道，管道流

	func ReadAtLeast(r Reader, buf []byte, min int) (n int, err error)
		* 从r中最少读取N个字节到buf
		* 在不能得到最小的字节的时候会返回错误，但会把已读的文件保留

	func ReadFull(r Reader, buf []byte) (n int, err error)
		* 从r读取数据存储到buf
		* 在文件r字节数小于buf字节数的时候会返回错误
		* 实现
			return ReadAtLeast(r, buf, len(buf))
	
	func ReadAll(r Reader) ([]byte, error)
		* 读取所有数据

	func WriteString(w Writer, s string) (n int, err error)
		* 把字符串写入writer，内部做了判断优化，如果实现了 StringWriter则会转换后再执行，避免内存copy
	