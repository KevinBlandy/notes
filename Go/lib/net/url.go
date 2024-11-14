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
				* Э��

			Opaque      string    // encoded opaque data
			User        *Userinfo // username and password information
				* �û���Ϣ���û�������

			Host        string    // host or host:port
			Path        string    // ���·���������·������ʡ��ǰ���б�ߣ�
			RawPath     string    // �����·����ʾ���� EscapedPath ��������
			OmitHost	bool	  // do not emit empty host (authority)
			ForceQuery  bool      // append a query ('?') even if RawQuery is empty
			RawQuery    string    // encoded query values, without '?'
			Fragment    string    // fragment for references, without '#'
			RawFragment string    // encoded fragment hint (see EscapedFragment method)
		}
		
		* URL����

		func Parse(rawurl string) (*URL, error)
			* ��ԭʼ URL ����Ϊ URL �ṹ��
			* URL ��������Եģ�����������·������Ҳ�����Ǿ��Եģ��� scheme ��ͷ����
			* ���Խ������� scheme ����������·������Ч�ģ������ڽ������壬��һ���᷵�ش���

		func ParseRequestURI(rawurl string) (*URL, error)
			* ��ԭʼ url ����Ϊ URL �ṹ�����ٶ� url ���� HTTP �������յ��ģ���� url ֻ������Ϊ���� URI �����·����
			* �ٶ� url �ַ���û�� #fragment ��׺�������������������������� URL ǰ��ȥ�� #fragment����

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
			* ��URL�е�������Ϣ�滻Ϊ: XXXX 

		func (u *URL) RequestURI() string
		func (u *URL) ResolveReference(ref *URL) *URL
		func (u *URL) String() string
		func (u *URL) UnmarshalBinary(text []byte) error
		func (u *URL) JoinPath(elem ...string) *URL 
			* ���·��

	# type Userinfo struct {
		}
		
		func User(username string) *Userinfo
		func UserPassword(username, password string) *Userinfo
			* ͨ���û��������빹���û���Ϣ
				u, err := url.Parse("https://gitee.com/kevinblandy/demo.git")
				if err != nil {
					panic(err)
				}
				u.User = url.UserPassword("kevinblandy", "******")
				fmt.Println(u) 
				// https://kevinblandy:%2A%2A%2A%2A%2A%2A@gitee.com/kevinblandy/demo.git

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
			* ��ȡ����� KEY ��صĵ�һ��ֵ�����û���� KEY ��ص�ֵ��Get ���ؿ��ַ�����
			* Ҫ���ʶ��ֵ����ֱ��ʹ�� map��
				var column []string = query["column"]
			
		func (v Values) Set(key, value string)
		func (v Values) Has(key string) bool

--------------------
fanc
--------------------
	func PathEscape(s string) string
	func PathUnescape(s string) (string, error)
	func QueryEscape(s string) string
		* URL����
			c.Writer.Header().Set("Content-Disposition", `attachment; filename*=UTF-8''`+url.QueryEscape(filename))
		
	func QueryUnescape(s string) (string, error) 
		* ��������URI����

	func JoinPath(base string, elem ...string) (result string, err error)
		* ·��join

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
