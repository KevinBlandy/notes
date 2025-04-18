--------------------------------
smtp
--------------------------------

--------------------------------
var
--------------------------------

--------------------------------
type
--------------------------------
	# type Auth interface {
			Start(server *ServerInfo) (proto string, toServer []byte, err error)
			Next(fromServer []byte, more bool) (toServer []byte, err error)
		}
		func CRAMMD5Auth(username, secret string) Auth
		func PlainAuth(identity, username, password, host string) Auth

	# type Client struct {
			Text *textproto.Conn
		}
		func Dial(addr string) (*Client, error)
		func NewClient(conn net.Conn, host string) (*Client, error)
		func (c *Client) Auth(a Auth) error
		func (c *Client) Close() error
		func (c *Client) Data() (io.WriteCloser, error)
		func (c *Client) Extension(ext string) (bool, string)
		func (c *Client) Hello(localName string) error
		func (c *Client) Mail(from string) error
		func (c *Client) Noop() error
		func (c *Client) Quit() error
		func (c *Client) Rcpt(to string) error
		func (c *Client) Reset() error
		func (c *Client) StartTLS(config *tls.Config) error
		func (c *Client) TLSConnectionState() (state tls.ConnectionState, ok bool)
		func (c *Client) Verify(addr string) error
	
	# type ServerInfo struct {
			Name string   // SMTP server name
			TLS  bool     // using TLS, with valid certificate for Name
			Auth []string // advertised authentication mechanisms
		}


--------------------------------
func
--------------------------------
	func SendMail(addr string, a Auth, from string, to []string, msg []byte) error
		* �����ʼ�
		addr	Ŀ���ַ
		a		��֤��Ϣ
		form	������
		to		�ռ���
		msg		�ʼ�����
	

--------------------------------
Demo
--------------------------------
	# ��������
		package main

		import (
			"fmt"
			"log"
			"net/smtp"
		)

		func main() {
			// ���ӵ�Զ�� smtp ������
			c, err := smtp.Dial("mail.example.com:25")
			if err != nil {
				log.Fatal(err)
			}

			// ���÷�����
			if err := c.Mail("sender@example.org"); err != nil {
				log.Fatal(err)
			}
			// �����ռ���
			if err := c.Rcpt("recipient@example.net"); err != nil {
				log.Fatal(err)
			}

			// ��ȡ�ʼ� Body Writer
			wc, err := c.Data()
			if err != nil {
				log.Fatal(err)
			}

			// д���ʼ�����
			_, err = fmt.Fprintf(wc, "This is the email body")
			if err != nil {
				log.Fatal(err)
			}
			err = wc.Close()
			if err != nil {
				log.Fatal(err)
			}

			// ���� QUIT ����ر����ӡ�
			err = c.Quit()
			if err != nil {
				log.Fatal(err)
			}
		}
