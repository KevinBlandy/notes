---------------------
yaml
---------------------
	# 源码
		https://github.com/go-yaml/yaml

		gopkg.in/yaml.v2
		gopkg.in/yaml.v3
	
	# 注解的支持
		* 属性绑定
			type StructA struct {
				A string `yaml:"a"`		
			}
		
		* 忽略零值:omitempty
			* 把对象解析为yaml的时候，如果是零值则忽略
		
		* 结构体嵌入:inline
			type StructA struct {
				A string `yaml:"a"`
			}

			type StructB struct {
				StructA `yaml:",inline"`			// 嵌入了 StructA，在这里等于声明了: A string `yaml:"a"`
				B       string `yaml:"b"`
			}

			* inline 只能用在Map或者struct结构体上
		
		* 解析数组:flow


---------------------
var
---------------------

---------------------
type
---------------------
	# type Decoder struct {
		}
		func NewDecoder(r io.Reader) *Decoder
		func (dec *Decoder) Decode(v interface{}) (err error)
		func (dec *Decoder) KnownFields(enable bool)
	
	# type Encoder struct {
		}
		func NewEncoder(w io.Writer) *Encoder
		func (e *Encoder) Close() (err error)
		func (e *Encoder) Encode(v interface{}) (err error)
		func (e *Encoder) SetIndent(spaces int)
	
	# type IsZeroer interface {
			IsZero() bool
		}
	# type Kind uint32
		const (
			DocumentNode Kind = 1 << iota
			SequenceNode
			MappingNode
			ScalarNode
			AliasNode
		)
	
	# type Marshaler interface {
			MarshalYAML() (interface{}, error)
		}
	
	# type Node struct {
			Kind Kind
			Style Style
			Tag string
			Value string
			Anchor string
			Alias *Node
			Content []*Node
			HeadComment string
			LineComment string
			FootComment string
			Line   int
			Column int
		}
		func (n *Node) Decode(v interface{}) (err error)
		func (n *Node) Encode(v interface{}) (err error)
		func (n *Node) IsZero() bool
		func (n *Node) LongTag() string
		func (n *Node) SetString(s string)
		func (n *Node) ShortTag() string
	
	# type Style uint32
		const (
			TaggedStyle Style = 1 << iota
			DoubleQuotedStyle
			SingleQuotedStyle
			LiteralStyle
			FoldedStyle
			FlowStyle
		)
	
	# type TypeError struct {
			Errors []string
		}
		func (e *TypeError) Error() string
	
	# type Unmarshaler interface {
			UnmarshalYAML(value *Node) error
		}

---------------------
func
---------------------
	func Marshal(in interface{}) (out []byte, err error)
	func Unmarshal(in []byte, out interface{}) (err error)
		* 序列化，反序列化
	



---------------------
demo
---------------------
	# 解析配置文件文件通过的Map结构
		package main
		import (
			"gopkg.in/yaml.v2"
			"log"
			"os"
			"path/filepath"
		)

		func main() {
			folder, _ := os.Getwd()
			file, err := os.Open(filepath.Join(folder, "config", "application.yaml"))
			if err != nil {
				log.Fatalf("配置文件读取异常:%s\n", err.Error())
			}

			// 存储配置信息，使用 Map<Object, Object>
			config := make(map[interface{}] interface{})
			err = yaml.NewDecoder(file).Decode(config)
			file.Close()

			if err != nil {
				log.Fatalf("配置文件解析异常:%s\n", err.Error())
			}
			log.Println(config)
		}
	
	# 通过匿名对象定义配置结构
		* 配置
			server:
			  port: 80
			  host: "0.0.0.0"
			  read-time-out: "5s"
			  compression:
				enabled: true
				mime-types:
				  - application/json
				  - application/xml
				  - application/javascript
				  - text/html
				  - text/xml
				  - text/plain
				  - text/css
				  - text/javascript
				min-response-size: 2048

			users:
			  - {account: "admin", password: "123456"}
			  - {account: "manager", password: "654321"}
			  - {account: "guest", password: "11111"}

			static-locations:
			  - "/static"
			  - "/resource"
		* 对象
			type Config struct {
				// 服务器配置
				Server struct {
					Port int `yaml:"port"`
					Host string `yaml:"host"`
					ReadTimeOut time.Duration `yaml:"read-time-out"`			// 默认单位是纳秒：5000000000
					Compression struct{
						Enabled bool `yaml:"enabled"`
						MinResponseSize int `yaml:"min-response-size"`
						MineTypes []string `yaml:"mime-types"`
					} `yaml:"compression"`
				} `yaml:"server"`

				// 用户账户配置
				Users []*struct{
					Account string `yaml:"account"`
					Password string `yaml:"password"`
				} `yaml:"users"`

				// 静态资源访问路径配置
				StaticLocations []string `yaml:"static-locations"`
			}
		
		* 解析
			data, err := os.ReadFile("./app.yml")
			if err != nil {
				log.Println(err)
			}
			var config *Config
			err = yaml.Unmarshal(data, &config)
			if err != nil {
				log.Println(err)
			}

			jsonStr, _ := json.MarshalIndent(config, "", " ")
			log.Println(string(jsonStr))
		
		* 输出的json
			{
			 "Server": {
			  "Port": 80,
			  "Host": "0.0.0.0",
			  "ReadTimeOut": 5000000000,
			  "Compression": {
			   "Enabled": true,
			   "MinResponseSize": 2048,
			   "MineTypes": [
				"application/json",
				"application/xml",
				"application/javascript",
				"text/html",
				"text/xml",
				"text/plain",
				"text/css",
				"text/javascript"
			   ]
			  }
			 },
			 "Users": [
			  {
			   "Account": "admin",
			   "Password": "123456"
			  },
			  {
			   "Account": "manager",
			   "Password": "654321"
			  },
			  {
			   "Account": "guest",
			   "Password": "11111"
			  }
			 ],
			 "StaticLocations": [
			  "/static",
			  "/resource"
			 ]
			}

