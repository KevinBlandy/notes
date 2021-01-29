---------------------
yaml
---------------------
	# 源码
		https://github.com/go-yaml/yaml

		gopkg.in/yaml.v2
		gopkg.in/yaml.v3

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


---------------------
demo
---------------------
	# 解析配置文件
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
