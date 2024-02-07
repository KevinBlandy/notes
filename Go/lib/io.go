--------------------------
io
--------------------------
	# ����IO�ӿڵĶ���

--------------------------
����
--------------------------
	* /dev/null
		var Discard Writer = discard{}
	
	* �ƶ������λ��
		const (
			SeekStart   = 0 // seek relative to the origin of the file
			SeekCurrent = 1 // seek relative to the current offset
			SeekEnd     = 2 // seek relative to the end
		)
	
	* ������
		var EOF = errors.New("EOF")
	
	* �쳣��Ϣ
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

		* ��Reader��װ��һ��ReadClose����Close()�Ľ��п�ʵ��
	
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
			* ���Ӷ��Reader������Reader����ȡ��ϲŻ᷵�� EOF
			* ����κ�һ��Reader������һ����nil����EOF�Ĵ���Read�����ظô���

		func TeeReader(r Reader, w Writer) Reader
			* ����һ��reader�������reader�������൱�ڴ� r ��ȡ����
			* ���Ҷ�ȡ���������ݣ����ᱻ�Զ�д�뵽w
	
	# type ReaderAt interface {
			ReadAt(p []byte, off int64) (n int, err error)
		}

	# type ReaderFrom interface {
			ReadFrom(r Reader) (n int64, err error)
		}
		
		* �ײ����ʹ���㿽������

	# type RuneReader interface {
			ReadRune() (r rune, size int, err error)
		}
	# type RuneScanner interface {
			RuneReader
			UnreadRune() error
		}
	# type SectionReader struct

		func NewSectionReader(r ReaderAt, off int64, n int64) *SectionReader
		
		* �����д
		
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
			* �Ѷ��writer�󶨵�һ��writer����
			* ������writerд�����ݵ�ʱ�򣬻Ḵ�Ƶ����е�Writer�У������� Unix �� tee(1) ����
			* ����κ�һ��Writerд���쳣���ͻ�����ֹͣ���ҷ���

	
	# type WriterAt interface {
			WriteAt(p []byte, off int64) (n int, err error)
		}
	
	# type WriterTo interface {
			WriteTo(w Writer) (n int64, err error)
		}

		* �ײ����ʹ���㿽������
	

--------------------------
����
--------------------------
	func CopyBuffer(dst Writer, src Reader, buf []byte) (written int64, err error)
		* ��copy����һ��������������ͬ���Լ��ṩ�˵Ļ��������и��Ƶ�
		* ���buf��nil�Ļ���ϵͳ���Լ�����һ������֮���buf��len��0������׳��쳣

	func CopyN(dst Writer, src Reader, n int64) (written int64, err error)
		* ���ƣ����Ƹ��ƵĴ�С

	func Pipe() (*PipeReader, *PipeWriter)
		* ����pipe����ͨ�����ܵ���

	func ReadAtLeast(r Reader, buf []byte, min int) (n int, err error)
		* ��r�����ٶ�ȡN���ֽڵ�buf
		* �ڲ��ܵõ���С���ֽڵ�ʱ��᷵�ش��󣬵�����Ѷ����ļ�����

	func ReadFull(r Reader, buf []byte) (n int, err error)
		* ��r��ȡ���ݴ洢��buf
		* ���ļ�r�ֽ���С��buf�ֽ�����ʱ��᷵�ش���
		* ʵ��
			return ReadAtLeast(r, buf, len(buf))
	
	func ReadAll(r Reader) ([]byte, error)
		* ��ȡ��������

	func WriteString(w Writer, s string) (n int, err error)
		* ���ַ���д��writer���ڲ������ж��Ż������ʵ���� StringWriter���ת������ִ�У������ڴ�copy
	