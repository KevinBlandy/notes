-------------------
gzip
-------------------
	# gzip实现RFC 1952中指定的gzip格式压缩文件的读写


-------------------
var
-------------------
	const (
		NoCompression      = flate.NoCompression
		BestSpeed          = flate.BestSpeed
		BestCompression    = flate.BestCompression
		DefaultCompression = flate.DefaultCompression		// 默认级别
		HuffmanOnly        = flate.HuffmanOnly
	)

	* 压缩等级

	var (
		// ErrChecksum is returned when reading GZIP data that has an invalid checksum.
		ErrChecksum = errors.New("gzip: invalid checksum")
		// ErrHeader is returned when reading GZIP data that has an invalid header.
		ErrHeader = errors.New("gzip: invalid header")
	)

-------------------
type
-------------------
	# type Header struct {
			Comment string    // comment
			Extra   []byte    // "extra data"
			ModTime time.Time // modification time
			Name    string    // file name
			OS      byte      // operating system type
		}
	
	# type Reader struct {
			Header // valid after NewReader or Reader.Reset
		}
		func NewReader(r io.Reader) (*Reader, error)
		func (z *Reader) Close() error
		func (z *Reader) Multistream(ok bool)
		func (z *Reader) Read(p []byte) (n int, err error)
		func (z *Reader) Reset(r io.Reader) error
	
	# type Writer struct {
			Header // written at first call to Write, Flush, or Close
		}
		func NewWriter(w io.Writer) *Writer
		func NewWriterLevel(w io.Writer, level int) (*Writer, error)
		func (z *Writer) Close() error
		func (z *Writer) Flush() error
		func (z *Writer) Reset(w io.Writer)
		func (z *Writer) Write(p []byte) (int, error)


-------------------
func
-------------------
	# gzip压缩与解压缩
		import (
			"bytes"
			"compress/gzip"
			"io"
			"log"
			"os"
		)

		func main(){
			buffer := &bytes.Buffer{}

			// 压缩数据
			writer := gzip.NewWriter(buffer)
			writer.Write([]byte("不见五陵豪杰墓，无花无酒锄作田。"))
			writer.Close()

			// 解压数据
			reader, err := gzip.NewReader(buffer)
			if err != nil {
				log.Fatal(err)
			}
			if _, err := io.Copy(os.Stdout, reader); err != nil {		// 不见五陵豪杰墓，无花无酒锄作田。
				log.Fatal(err)
			}
			reader.Close()
		}
	
