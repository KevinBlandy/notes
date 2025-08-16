
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
	# type File interface {
			io.Reader
			io.ReaderAt
			io.Seeker
			io.Closer
		}

		* 多部件请求体的文件对象
		* 它可以是内存中，也可以是磁盘上，如果是磁盘上，则底层则是: *os.File

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
			* 跟 NextPart 一样，
			* 只是它没有特别的方式来处理： "Content-Transfer-Encoding: quoted-printable" (可打印字符引用编码)

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

	func FileContentDisposition(fieldname, filename string) string
		* 返回指定字段名和文件名对应的 Content-Disposition 标头值。
		* 源码
			return fmt.Sprintf(`form-data; name="%s"; filename="%s"`, escapeQuotes(fieldname), escapeQuotes(filename))
		
--------------------------------
demo
--------------------------------
	# 使用 Writer 输出多部件表单体
		// 管道流（如果body小的话，可以直接用buffer）
		r, w := io.Pipe()
		defer r.Close()

		// 创建 multipart，指定writer
		formWriter := multipart.NewWriter(w)

		go func() {

			defer w.Close()

			var writer io.Writer

			// 快速构建普通表单项，key/value都是字符串
			formWriter.WriteField("lang", "PHP是宇宙最好的语言")

			// 构建普通的表单项，通过Writer写入数据
			writer, _ = formWriter.CreateFormField("lang")
			writer.Write([]byte("Java是世界上最好的语言"))

			// 构建文件表单项，指定表单名称，以及文件名称，通过Writer写入数据，默认的ContentType 是 application/octet-stream
			writer, _ = formWriter.CreateFormFile("file", "app.json")
			jsonVal, _ := json.Marshal(map[string] string {"name": "KevinBlandy"})
			writer.Write(jsonVal)

			// 自定义part表单项，可以添加自定义的header
			header := textproto.MIMEHeader{}
			header.Set("Content-Disposition", `form-data; name="file"; filename="app1.json"`)		// 自定表单字段名称，文件名称，这是必须的
			header.Set("Content-Type", `application/octet-stream`)									// 指定ContentType，这是必须的
			writer, _ = formWriter.CreatePart(header)
			writer.Write([]byte("foo"))

			// 完成写入，需要调用close方法
			formWriter.Close()
		}()

		// 创建http客户端
		client := http.Client{}
		// 创建request请求，指定body reader
		req, _ := http.NewRequest(http.MethodPost, "http://127.0.0.1/upload", r)
		req.Header.Set("Content-Type", formWriter.FormDataContentType()) // 需要正确的设置ContentType

		// 执行请求获取响应
		resp, _ := client.Do(req)
		defer resp.Body.Close()

		// 获取响应
		data, _ := ioutil.ReadAll(resp.Body)
		fmt.Println(string(data))