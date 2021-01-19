
--------------------------------
multipart
--------------------------------
--------------------------------
变量
--------------------------------
	# 请求体过大异常
		var ErrMessageTooLarge = errors.New("multipart: message too large")


--------------------------------
type
--------------------------------
	# type FileHeader struct {
			Filename string
			Header   textproto.MIMEHeader
			Size     int64
		}
		func (fh *FileHeader) Open() (File, error)
			* 返回File，用来读取数据
	
	# type Form struct {
			Value map[string][]string
			File  map[string][]*FileHeader
		}
		func (f *Form) RemoveAll() error
			* 删除所有关联的临时文件
	
	# type Part struct {
			Header textproto.MIMEHeader
		}
		func (p *Part) Close() error
		func (p *Part) FileName() string
		func (p *Part) FormName() string
		func (p *Part) Read(d []byte) (n int, err error)
	
	# type Reader struct {
		}
		func NewReader(r io.Reader, boundary string) *Reader
		func (r *Reader) NextPart() (*Part, error)
			* 如果遍历到结尾，返回 io.EOF 异常

		func (r *Reader) NextRawPart() (*Part, error)
		func (r *Reader) ReadForm(maxMemory int64) (*Form, error)
	
	# type Writer struct {
		}
		func NewWriter(w io.Writer) *Writer
		func (w *Writer) Boundary() string

		func (w *Writer) Close() error
			* 最后需要调用close，它会输出最后的分界线

		func (w *Writer) CreateFormField(fieldname string) (io.Writer, error)
			* 写入普通表单项
		func (w *Writer) CreateFormFile(fieldname, filename string) (io.Writer, error)
			* 写入文件表单项
		func (w *Writer) CreatePart(header textproto.MIMEHeader) (io.Writer, error)
			* 写入自定义part表单项，可以通过part自定义header
		func (w *Writer) WriteField(fieldname, value string) error
			* 快速的写入普通表单项
				
		func (w *Writer) FormDataContentType() string
			* 返回contentType 
				multipart/form-data; boundary=b647104eac41c51a2701657dee93231333b7f5ad44bf521e43880773a6ee
		func (w *Writer) SetBoundary(boundary string) error
			* 设置分界，如果需要调用，只能在在写入之前就必须被调用
			* 默认不需要设置，有默认的
		
		
--------------------------------
方法
--------------------------------

