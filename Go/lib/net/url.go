--------------------
url
--------------------

--------------------
����
--------------------

--------------------
type
--------------------
	# type Error struct {
			Op  string
			URL string
			Err error
		}
		
		func (e *Error) Error() string
		func (e *Error) Temporary() bool
		func (e *Error) Timeout() bool
		func (e *Error) Unwrap() error
	
	# type EscapeError string
		func (e EscapeError) Error() string
	
	# type InvalidHostError string
		func (e InvalidHostError) Error() string
	
	# type URL struct {
			Scheme      string
			Opaque      string    // encoded opaque data
			User        *Userinfo // username and password information
			Host        string    // host or host:port
			Path        string    // ���·���������·������ʡ��ǰ���б�ߣ�
			RawPath     string    // �����·����ʾ���� EscapedPath ��������
			ForceQuery  bool      // append a query ('?') even if RawQuery is empty
			RawQuery    string    // encoded query values, without '?'
			Fragment    string    // fragment for references, without '#'
			RawFragment string    // encoded fragment hint (see EscapedFragment method)
		}
		func Parse(rawurl string) (*URL, error)
		func ParseRequestURI(rawurl string) (*URL, error)

		func (u *URL) EscapedFragment() string
		func (u *URL) EscapedPath() string
		func (u *URL) Hostname() string
		func (u *URL) IsAbs() bool
		func (u *URL) MarshalBinary() (text []byte, err error)
		func (u *URL) Parse(ref string) (*URL, error)
		func (u *URL) Port() string
		func (u *URL) Query() Values
			* ���ؼ�������

		func (u *URL) Redacted() string
		func (u *URL) RequestURI() string
		func (u *URL) ResolveReference(ref *URL) *URL
		func (u *URL) String() string
		func (u *URL) UnmarshalBinary(text []byte) error

	# type Userinfo struct {
		}
		
		func User(username string) *Userinfo
		func UserPassword(username, password string) *Userinfo

		func (u *Userinfo) Password() (string, bool)
		func (u *Userinfo) String() string
		func (u *Userinfo) Username() string
	
	# type Values map[string][]string

		func ParseQuery(query string) (Values, error)
			* ���������ַ�����ΪValues

		func (v Values) Add(key, value string)
		func (v Values) Del(key string)
		func (v Values) Encode() string
		func (v Values) Get(key string) string
		func (v Values) Set(key, value string)
		func (v Values) Has(key string) bool

--------------------
fanc
--------------------
	func PathEscape(s string) string
	func PathUnescape(s string) (string, error)
	func QueryEscape(s string) string
	func QueryUnescape(s string) (string, error) 
		* ��������URI����



--------------------
Demo
--------------------
	# ������
		import (
			"fmt"
			"net/url"
		)

		func main(){
			var form url.Values = make(map[string] []string)
			form.Add("name", "Go����")
			form.Add("name", "Java����")
			fmt.Print(form.Encode())  // name=Go%E8%AF%AD%E8%A8%80&name=Java%E8%AF%AD%E8%A8%80
		}
	
	# ��������������
		import (
			"fmt"
			"net/url"
		)

		func main(){
			// ���������ַ
			target, err := url.Parse("https://springboot.io")
			if err != nil {
				println(err)
			}

			// ��ȡ��ַ�е����������������ɾ
			query := target.Query()
			query.Add("name", "SpringBoot��������")
			query.Add("address", "https://springboot.io")

			// ���������������������ø�Ŀ���ַ
			target.RawQuery = query.Encode()

			fmt.Println(target)

			// https://springboot.io?address=https%3A%2F%2Fspringboot.io&name=SpringBoot%E4%B8%AD%E6%96%87%E7%A4%BE%E5%8C%BA
		}
