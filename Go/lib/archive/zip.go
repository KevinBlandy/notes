-----------------
zip
-----------------

-----------------
var
-----------------
	const (
		Store   uint16 = 0 // no compression
			* 不压缩
		Deflate uint16 = 8 // DEFLATE compressed
			* 压缩
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
				* 名称，如果是 "/" 结尾，则是一个目录

			Comment string
			NonUTF8 bool
			CreatorVersion uint16
			ReaderVersion  uint16
			Flags          uint16
			Method uint16
				* 压缩方式，对于目录项来说无效
				* 一般设置: zip.Deflate

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
		
		* 一个Zip压缩项

		func FileInfoHeader(fi fs.FileInfo) (*FileHeader, error)
			* 根据文件的 FileInfo 创建

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
			* 写入一个*FileHeader文件项
		func (w *Writer) AddFS(fsys fs.FS) error
			* 写入一个 FS，AddFS
			* 它从文件系统的根目录开始遍历目录树，使用 deflate 将每个文件添加到压缩包，同时保持目录结构不变。

		func (w *Writer) Flush() error
		func (w *Writer) RegisterCompressor(method uint16, comp Compressor)
		func (w *Writer) SetComment(comment string) error
		func (w *Writer) SetOffset(n int64)
		func (w *Writer) CreateRaw(fh *FileHeader) (io.Writer, error)
			* 与CreateHeader不同的是，传递给Writer的字节没有被压缩。
		
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
	
	# 压缩整个目录
		package main

		import (
			"archive/zip"
			"fmt"
			"os"
		)

		func main() {
			// 预创建 zip 文件
			zipFile, err := os.Create("D:\\apache-maven.zip")
			if err != nil {
				fmt.Printf("创建文件异常 %s\n", err.Error())
				return
			}
			defer func() {
				_ = zipFile.Close()
			}()

			writer := zip.NewWriter(zipFile)

			defer func() {
				_ = writer.Close()
			}()

			// 压缩指定的目录
			if err := writer.AddFS(os.DirFS("D:\\apache-maven-3.9.6")); err != nil {
				fmt.Printf("zip 文件压缩异常 %s\n", err.Error())
				return
			}
		}
