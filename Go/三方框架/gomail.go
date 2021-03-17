-------------
gomail
-------------
	# 包
		gopkg.in/gomail.v2

-------------
var
-------------

-------------
type
-------------
	# type Dialer struct {
			Host string
			Port int
			Username string
			Password string
			Auth smtp.Auth
			SSL bool
			TLSConfig *tls.Config
			LocalName string
		}
	
		* 邮件服务器的链接

		func NewDialer(host string, port int, username, password string) *Dialer
		func NewPlainDialer(host string, port int, username, password string) *Dialer

		func (d *Dialer) Dial() (SendCloser, error)
			* 创建链接，返回Sender

		func (d *Dialer) DialAndSend(m ...*Message) error
			* 连接，并且发送n个邮件
	
	# type Encoding string

		* 编码器

		const (
			QuotedPrintable Encoding = "quoted-printable"
			Base64 Encoding = "base64"
			Unencoded Encoding = "8bit"
		)

	
	# type FileSetting func(*file)
		
		* 文件设置
		
		func Rename(name string) FileSetting
		func SetCopyFunc(f func(io.Writer) error) FileSetting
		func SetHeader(h map[string][]string) FileSetting
	
	# type Message struct {
		}
		func NewMessage(settings ...MessageSetting) *Message
			* 创建新的消息，通过设置项进行设置

		func (m *Message) AddAlternative(contentType, body string, settings ...PartSetting)
		func (m *Message) AddAlternativeWriter(contentType string, f func(io.Writer) error, settings ...PartSetting)
		func (m *Message) Attach(filename string, settings ...FileSetting)
			* 添加附件，指定文件名称，以及文件（通过FileSetting读取）

		func (m *Message) Embed(filename string, settings ...FileSetting)
		func (m *Message) FormatAddress(address, name string) string
			* 根据address，别名，返回发件人地址信息
				 FormatAddress("no-reply@springboot.io", "SpringBoot中社区")	 
				 // =?UTF-8?q?SpringBoot=E4=B8=AD=E7=A4=BE=E5=8C=BA?= <no-reply@springboot.io>

		func (m *Message) FormatDate(date time.Time) string
		func (m *Message) GetHeader(field string) []string
		func (m *Message) Reset()
		func (m *Message) SetAddressHeader(field, address, name string)
			* 设置地址Header，可以设置别名
			* 本质上上是调用 FormatAddress 进行编码
				m.header[field] = []string{m.FormatAddress(address, name)}
				
		func (m *Message) SetBody(contentType, body string, settings ...PartSetting)
			* 设置邮件内容，指定ContentType

		func (m *Message) SetDateHeader(field string, date time.Time)
		func (m *Message) SetHeader(field string, value ...string)
			* 设置Header
				From 发件人
				To 收件人
				Subject 主题

		func (m *Message) SetHeaders(h map[string][]string)
		func (m *Message) WriteTo(w io.Writer) (int64, error)
	
	# type MessageSetting func(m *Message)
		
		* 消息设置项
		
		func SetCharset(charset string) MessageSetting
			* 设置编码
		func SetEncoding(enc Encoding) MessageSetting
			* 设置编码器
	
	# type PartSetting func(*part)
			
		* Part设置项

		func SetPartEncoding(e Encoding) PartSetting
	
	# type SendCloser interface {
			Sender
			Close() error
		}
	
	# type SendFunc func(from string, to []string, msg io.WriterTo) error
		func (f SendFunc) Send(from string, to []string, msg io.WriterTo) error
	
	# type Sender interface {
			Send(from string, to []string, msg io.WriterTo) error
		}
	

-------------
func
-------------
	func Send(s Sender, msg ...*Message) error



-------------
Demo
-------------
	# 发送邮件
		import (
			"gopkg.in/gomail.v2"
		)

		var authCode = "123456"

		func main(){

			// 创建邮件消息，设置编码
			message := gomail.NewMessage(gomail.SetCharset("utf-8"))

			message.SetAddressHeader("From", "10086@qq.com", "派大星")	// 发件人以及发件人名称
			message.SetHeader("To", "10010@qq.com")					// 收件人，可以有多个
			message.SetHeader("Subject", "Hello!")							// 标题
			message.SetBody("text/html", "Hello World!")					// 邮件内容
			message.Attach("D:\\ruby.db")										// 附件

			// 创建邮箱链接信息，指定主机端口，账户名，密码
			conn := gomail.NewDialer("smtp.163.com", 465, "10086@qq.com", authCode)

			// 创建连接，并且发送邮件
			if err := conn.DialAndSend(message); err != nil {
				panic(err)
			}
		}
	
	# 忽略证书校验
		d := gomail.NewDialer("smtp.example.com", 587, "user", "123456")
		d.TLSConfig = &tls.Config{InsecureSkipVerify: true}