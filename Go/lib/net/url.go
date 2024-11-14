--------------------
url
--------------------

--------------------
变量
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
				* 协议

			Opaque      string    // encoded opaque data
			User        *Userinfo // username and password information
				* 用户信息，用户名密码

			Host        string    // host or host:port
			Path        string    // 相对路径，（相对路径可以省略前面的斜线）
			RawPath     string    // 编码的路径提示（见 EscapedPath 方法）。
			OmitHost	bool	  // do not emit empty host (authority)
			ForceQuery  bool      // append a query ('?') even if RawQuery is empty
			RawQuery    string    // encoded query values, without '?'
			Fragment    string    // fragment for references, without '#'
			RawFragment string    // encoded fragment hint (see EscapedFragment method)
		}
		
		* URL对象

		func Parse(rawurl string) (*URL, error)
			* 把原始 URL 解析为 URL 结构。
			* URL 可以是相对的（不含主机的路径），也可以是绝对的（以 scheme 开头）。
			* 尝试解析不含 scheme 的主机名和路径是无效的，但由于解析歧义，不一定会返回错误。

		func ParseRequestURI(rawurl string) (*URL, error)
			* 将原始 url 解析为 URL 结构。它假定 url 是在 HTTP 请求中收到的，因此 url 只被解释为绝对 URI 或绝对路径。
			* 假定 url 字符串没有 #fragment 后缀。（浏览器在向网络服务器发送 URL 前会去掉 #fragment）。

		func (u *URL) EscapedFragment() string
		func (u *URL) EscapedPath() string
		func (u *URL) Hostname() string
		func (u *URL) IsAbs() bool
		func (u *URL) MarshalBinary() (text []byte, err error)
		func (u *URL) Parse(ref string) (*URL, error)
		func (u *URL) Port() string
		func (u *URL) Query() Values
			* 返回检索参数

		func (u *URL) Redacted() string
			* 把URL中的密码信息替换为: XXXX 

		func (u *URL) RequestURI() string
		func (u *URL) ResolveReference(ref *URL) *URL
		func (u *URL) String() string
		func (u *URL) UnmarshalBinary(text []byte) error
		func (u *URL) JoinPath(elem ...string) *URL 
			* 添加路径

	# type Userinfo struct {
		}
		
		func User(username string) *Userinfo
		func UserPassword(username, password string) *Userinfo
			* 通过用户名和密码构建用户信息
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
			* 解析请求字符串，为Values

		func (v Values) Add(key, value string)
		func (v Values) Del(key string)
		func (v Values) Encode() string
		func (v Values) Get(key string) string
			* 获取与给定 KEY 相关的第一个值。如果没有与 KEY 相关的值，Get 返回空字符串。
			* 要访问多个值，可直接使用 map。
				var column []string = query["column"]
			
		func (v Values) Set(key, value string)
		func (v Values) Has(key string) bool

--------------------
fanc
--------------------
	func PathEscape(s string) string
	func PathUnescape(s string) (string, error)
	func QueryEscape(s string) string
		* URL编码
			c.Writer.Header().Set("Content-Disposition", `attachment; filename*=UTF-8''`+url.QueryEscape(filename))
		
	func QueryUnescape(s string) (string, error) 
		* 检索参数URI解码

	func JoinPath(base string, elem ...string) (result string, err error)
		* 路径join

--------------------
Demo
--------------------
	# 构建表单
		import (
			"fmt"
			"net/url"
		)

		func main(){
			var form url.Values = make(map[string] []string)
			form.Add("name", "Go语言")
			form.Add("name", "Java语言")
			fmt.Print(form.Encode())  // name=Go%E8%AF%AD%E8%A8%80&name=Java%E8%AF%AD%E8%A8%80
		}
	
	# 构建完整的请求
		import (
			"fmt"
			"net/url"
		)

		func main(){
			// 构建请求地址
			target, err := url.Parse("https://springboot.io")
			if err != nil {
				println(err)
			}

			// 获取地址中的请求参数，进行增删
			query := target.Query()
			query.Add("name", "SpringBoot中文社区")
			query.Add("address", "https://springboot.io")

			// 把请求参数编码后，重新设置给目标地址
			target.RawQuery = query.Encode()

			fmt.Println(target)

			// https://springboot.io?address=https%3A%2F%2Fspringboot.io&name=SpringBoot%E4%B8%AD%E6%96%87%E7%A4%BE%E5%8C%BA
		}
