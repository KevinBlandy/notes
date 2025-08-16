
--------------------------------
multipart
--------------------------------
--------------------------------
����
--------------------------------
	# ����������쳣
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

		* �ಿ����������ļ�����
		* ���������ڴ��У�Ҳ�����Ǵ����ϣ�����Ǵ����ϣ���ײ�����: *os.File

	# type FileHeader struct {
			Filename string
			Header   textproto.MIMEHeader
			Size     int64
		}
		func (fh *FileHeader) Open() (File, error)
			* ����File��������ȡ����
	
	# type Form struct {
			Value map[string][]string
			File  map[string][]*FileHeader
		}
		func (f *Form) RemoveAll() error
			* ɾ�����й�������ʱ�ļ�
	
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
			* �����������β������ io.EOF �쳣

		func (r *Reader) NextRawPart() (*Part, error)
			* �� NextPart һ����
			* ֻ����û���ر�ķ�ʽ������ "Content-Transfer-Encoding: quoted-printable" (�ɴ�ӡ�ַ����ñ���)

		func (r *Reader) ReadForm(maxMemory int64) (*Form, error)
	
	# type Writer struct {
		}
		func NewWriter(w io.Writer) *Writer
		func (w *Writer) Boundary() string

		func (w *Writer) Close() error
			* �����Ҫ����close������������ķֽ���

		func (w *Writer) CreateFormField(fieldname string) (io.Writer, error)
			* д����ͨ����
		func (w *Writer) CreateFormFile(fieldname, filename string) (io.Writer, error)
			* д���ļ�����
		func (w *Writer) CreatePart(header textproto.MIMEHeader) (io.Writer, error)
			* д���Զ���part�������ͨ��part�Զ���header
		func (w *Writer) WriteField(fieldname, value string) error
			* ���ٵ�д����ͨ����
				
		func (w *Writer) FormDataContentType() string
			* ����contentType 
				multipart/form-data; boundary=b647104eac41c51a2701657dee93231333b7f5ad44bf521e43880773a6ee
		func (w *Writer) SetBoundary(boundary string) error
			* ���÷ֽ磬�����Ҫ���ã�ֻ������д��֮ǰ�ͱ��뱻����
			* Ĭ�ϲ���Ҫ���ã���Ĭ�ϵ�
		
		
--------------------------------
����
--------------------------------

	func FileContentDisposition(fieldname, filename string) string
		* ����ָ���ֶ������ļ�����Ӧ�� Content-Disposition ��ͷֵ��
		* Դ��
			return fmt.Sprintf(`form-data; name="%s"; filename="%s"`, escapeQuotes(fieldname), escapeQuotes(filename))
		
--------------------------------
demo
--------------------------------
	# ʹ�� Writer ����ಿ������
		// �ܵ��������bodyС�Ļ�������ֱ����buffer��
		r, w := io.Pipe()
		defer r.Close()

		// ���� multipart��ָ��writer
		formWriter := multipart.NewWriter(w)

		go func() {

			defer w.Close()

			var writer io.Writer

			// ���ٹ�����ͨ���key/value�����ַ���
			formWriter.WriteField("lang", "PHP��������õ�����")

			// ������ͨ�ı��ͨ��Writerд������
			writer, _ = formWriter.CreateFormField("lang")
			writer.Write([]byte("Java����������õ�����"))

			// �����ļ����ָ�������ƣ��Լ��ļ����ƣ�ͨ��Writerд�����ݣ�Ĭ�ϵ�ContentType �� application/octet-stream
			writer, _ = formWriter.CreateFormFile("file", "app.json")
			jsonVal, _ := json.Marshal(map[string] string {"name": "KevinBlandy"})
			writer.Write(jsonVal)

			// �Զ���part�����������Զ����header
			header := textproto.MIMEHeader{}
			header.Set("Content-Disposition", `form-data; name="file"; filename="app1.json"`)		// �Զ����ֶ����ƣ��ļ����ƣ����Ǳ����
			header.Set("Content-Type", `application/octet-stream`)									// ָ��ContentType�����Ǳ����
			writer, _ = formWriter.CreatePart(header)
			writer.Write([]byte("foo"))

			// ���д�룬��Ҫ����close����
			formWriter.Close()
		}()

		// ����http�ͻ���
		client := http.Client{}
		// ����request����ָ��body reader
		req, _ := http.NewRequest(http.MethodPost, "http://127.0.0.1/upload", r)
		req.Header.Set("Content-Type", formWriter.FormDataContentType()) // ��Ҫ��ȷ������ContentType

		// ִ�������ȡ��Ӧ
		resp, _ := client.Do(req)
		defer resp.Body.Close()

		// ��ȡ��Ӧ
		data, _ := ioutil.ReadAll(resp.Body)
		fmt.Println(string(data))