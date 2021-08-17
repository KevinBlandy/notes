-----------------
zip
-----------------

-----------------
var
-----------------
	const (
		Store   uint16 = 0 // no compression
		Deflate uint16 = 8 // DEFLATE compressed
	)
	var (
		ErrFormat    = errors.New("zip: not a valid zip file")
		ErrAlgorithm = errors.New("zip: unsupported compression algorithm")
		ErrChecksum  = errors.New("zip: checksum error")
	)

-----------------
type
-----------------
	# type Compressor func(w io.Writer) (io.WriteCloser, error)
	# type Decompressor func(r io.Reader) io.ReadCloser
	# type File struct {
			FileHeader
		}
		func (f *File) DataOffset() (offset int64, err error)
		func (f *File) Open() (io.ReadCloser, error)
		func (f *File) OpenRaw() (io.Reader, error)
	
	# type FileHeader struct {
			Name string
			Comment string
			NonUTF8 bool
			CreatorVersion uint16
			ReaderVersion  uint16
			Flags          uint16
			Method uint16
			Modified     time.Time
			ModifiedTime uint16 // Deprecated: Legacy MS-DOS date; use Modified instead.
			ModifiedDate uint16 // Deprecated: Legacy MS-DOS time; use Modified instead.
			CRC32              uint32
			CompressedSize     uint32 // Deprecated: Use CompressedSize64 instead.
			UncompressedSize   uint32 // Deprecated: Use UncompressedSize64 instead.
			CompressedSize64   uint64
			UncompressedSize64 uint64
			Extra              []byte
			ExternalAttrs      uint32 // Meaning depends on CreatorVersion
		}
		func FileInfoHeader(fi fs.FileInfo) (*FileHeader, error)
		func (h *FileHeader) FileInfo() fs.FileInfo
		func (h *FileHeader) ModTime() time.Time
		func (h *FileHeader) Mode() (mode fs.FileMode)
		func (h *FileHeader) SetModTime(t time.Time)
		func (h *FileHeader) SetMode(mode fs.FileMode)

	# type ReadCloser struct {
			Reader
		}
		func OpenReader(name string) (*ReadCloser, error)
		func (rc *ReadCloser) Close() error
	
	# type Reader struct {
			File    []*File
			Comment string
		}
		func NewReader(r io.ReaderAt, size int64) (*Reader, error)
		func (r *Reader) Open(name string) (fs.File, error)
		func (z *Reader) RegisterDecompressor(method uint16, dcomp Decompressor)
	
	# type Writer struct {
			// contains filtered or unexported fields
		}
		func NewWriter(w io.Writer) *Writer
		func (w *Writer) Close() error
		func (w *Writer) Create(name string) (io.Writer, error)
		func (w *Writer) CreateHeader(fh *FileHeader) (io.Writer, error)
		func (w *Writer) Flush() error
		func (w *Writer) RegisterCompressor(method uint16, comp Compressor)
		func (w *Writer) SetComment(comment string) error
		func (w *Writer) SetOffset(n int64)
		func (w *Writer) CreateRaw(fh *FileHeader) (io.Writer, error)
		func (w *Writer) Copy(f *File) error


	
-----------------
func
-----------------
	func RegisterCompressor(method uint16, comp Compressor)
	func RegisterDecompressor(method uint16, dcomp Decompressor)


-----------------
demo
-----------------
	# 解压缩
		import (
			"archive/zip"
			"log"
		)

		func main(){
			reader, err := zip.OpenReader("D:\\eclipse-jee-2020-09-R-win32-x86_64.zip")
			if err != nil {
				log.Fatal(err)
			}
			defer reader.Close()

			// 遍历每一个压缩项
			for _, file := range reader.File {
				log.Println(file.Name)	// 压缩文件名称，全路径
				closeReader, err := file.Open()
				if err != nil {
					log.Println(err)
				}
				// TODO 读取处理 reader 中的数据
				closeReader.Close()
			}
		}
	
	# 压缩文件
		import (
			"archive/zip"
			"io"
			"log"
		)

		func main(){

			// 创建Writer
			writer := zip.NewWriter(io.Discard)

			// 写入一个或者多个数据
			w, err := writer.Create("index.html")
			if err != nil {
				log.Fatal(err)
			}
			w.Write([]byte("im html content"))

			// 关闭Writer
			err = writer.Close()
			if err != nil {
				log.Fatal(err)
			}
		}
