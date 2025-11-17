----------------------
常量
----------------------

----------------------
struct
----------------------
	# type Decoder struct {
		}
		func NewDecoder(r io.Reader) *Decoder
		func (dec *Decoder) Buffered() io.Reader
		func (dec *Decoder) Decode(v interface{}) error
			* 尝试解码一个json，如果读取到了最后，返回 io.EOF

		func (dec *Decoder) DisallowUnknownFields()
		func (dec *Decoder) InputOffset() int64
		func (dec *Decoder) More() bool
			* 在解析数组JSON的时候，报告是否还有下一个元素

		func (dec *Decoder) Token() (Token, error)
			* 读取下一个分隔符，在流结束的时候返回 io.EOF
			* 分隔符为：
				[/]/{/}，用于标记数组和对象的开始和结束
				string
				float64
				<nil>

		func (dec *Decoder) UseNumber()
			* 将一个数字作为Number而不是float64解码到 interface{}。
			* 使用Number代替json中的float64
				kv := map[string]any{} 
				d := json.NewDecoder(...)
				d.UseNumber()
				d.Decode(&kv)
				

	# type Delim rune
		func (d Delim) String() string
	
	# type Encoder struct {
		}
		func NewEncoder(w io.Writer) *Encoder
		func (enc *Encoder) Encode(v interface{}) error
		func (enc *Encoder) SetEscapeHTML(on bool)
		func (enc *Encoder) SetIndent(prefix, indent string)

		* 自定义 JSON 编码器
			// 输出到 stdout
			encoder := json.NewEncoder(os.Stdout)
			// 前缀 & 缩进
			encoder.SetIndent("", "  ")
			// 不编码 HTML
			encoder.SetEscapeHTML(false)

			// 编码 & 输出
			encoder.Encode(map[string]interface{}{
				"lang": "GO语言",
				"tag":  "<br>",
			})
	
	# type InvalidUTF8Error struct {
		}
		func (e *InvalidUTF8Error) Error() string
	
	# type InvalidUnmarshalError struct {
			Type reflect.Type
		}
		func (e *InvalidUnmarshalError) Error() string
	
	# type Marshaler interface {
			MarshalJSON() ([]byte, error)
		}
	
	# type MarshalerError struct {
			Type reflect.Type
			Err  error
		}
		func (e *MarshalerError) Error() string
		func (e *MarshalerError) Unwrap() error
	
	# type Number string
		
		* JSON类型的数字，可以转换为Go中的各个类型
			
		func (n Number) Float64() (float64, error)
		func (n Number) Int64() (int64, error)
		func (n Number) String() string
	
	# type RawMessage []byte
		func (m RawMessage) MarshalJSON() ([]byte, error)
		func (m *RawMessage) UnmarshalJSON(data []byte) error

		* json原始字节，它作为结构体的一个字段，序列化的时候，会把它展开为独立的json字段
			h := json.RawMessage(`{"precomputed": true}`)
			c := struct {
				Header *json.RawMessage `json:"header"`
				Body   string           `json:"body"`
			}{Header: &h, Body: "Hello Gophers!"}
			b, err := json.MarshalIndent(&c, "", "\t")
			if err != nil {
				fmt.Println("error:", err)
			}
			os.Stdout.Write(b)
			// {
			// 	"header": {
			// 		"precomputed": true
			// 	},
			// 	"body": "Hello Gophers!"
			// }
		
		* 说白了，知道是个JSON但是不知道JSON的类型，结构，就可以用这个
	
	# type SyntaxError struct {
			Offset int64 // error occurred after reading Offset bytes
		}
		func (e *SyntaxError) Error() string
	
	# type Token interface{}
	
	# type UnmarshalFieldError struct {
			Key   string
			Type  reflect.Type
			Field reflect.StructField
		}
		func (e *UnmarshalFieldError) Error() string
	
	# type UnmarshalTypeError struct {
			Value  string       // description of JSON value - "bool", "array", "number -5"
			Type   reflect.Type // type of Go value it could not be assigned to
			Offset int64        // error occurred after reading Offset bytes
			Struct string       // name of the struct type containing the field
			Field  string       // the full path from root node to the field, include embedded struct
		}
		func (e *UnmarshalTypeError) Error() string

	# type Unmarshaler interface {
			UnmarshalJSON([]byte) error
		}
	
	# type UnsupportedTypeError struct {
			Type reflect.Type
		}
		func (e *UnsupportedTypeError) Error() string
	
	# type UnsupportedValueError struct {
			Value reflect.Value
			Str   string
		}
		func (e *UnsupportedValueError) Error() string
		
----------------------
方法
----------------------
	func Compact(dst *bytes.Buffer, src []byte) error
		* 压缩src(json)到dst

	func HTMLEscape(dst *bytes.Buffer, src []byte)
		* 把src（json）进行HTML编码后，输出到dst
			var out bytes.Buffer
			json.HTMLEscape(&out, []byte(`{"Name":"<b>HTML content</b>"}`))
			out.WriteTo(os.Stdout)

	func Indent(dst *bytes.Buffer, src []byte, prefix, indent string) error
		* 对src(json)，进行格式化（缩进）处理后，输出到dst
			prefix	指定前缀
			indent	指定格式化字符

	func Marshal(v interface{}) ([]byte, error)
	func MarshalIndent(v interface{}, prefix, indent string) ([]byte, error)
	func Unmarshal(data []byte, v interface{}) error
	func Valid(data []byte) bool
		* 判断是否是合法的json

----------------------
demo
----------------------
	# 解析遍历json文件中的数组
		import (
			"encoding/json"
			"log"
			"strings"
		)

		func main(){
			const val = `	[
				{"Name": "Ed", "Text": "Knock knock."},
				{"Name": "Sam", "Text": "Who's there?"},
				{"Name": "Ed", "Text": "Go fmt."},
				{"Name": "Sam", "Text": "Go fmt who?"},
				{"Name": "Ed", "Text": "Go fmt yourself!"}
			]
		`
			dec := json.NewDecoder(strings.NewReader(val))

			// 读取开始的分隔符
			token, err := dec.Token()
			if err != nil {
				log.Fatal(err)
			}
			log.Printf("start token=%s\n", token)		// start token=[

			type User struct {
				Name, Text string
			}

			// 遍历数组的每一项
			for dec.More() {
				var user = User{}
				err := dec.Decode(&user)
				if err != nil {
					log.Fatal(err)
				}
				log.Println(user)
			}

			// 读取结束的分隔符
			token, err = dec.Token()
			if err != nil {
				log.Fatal(err)
			}
			log.Printf("end token=%s\n", token)		// end token=]
		}
	
	# 解析遍历文件中的每一个json
		import (
			"encoding/json"
			"io"
			"log"
			"strings"
		)

		func main(){
			const val = `	
				{"Name": "Ed", "Text": "Knock knock."}
				{"Name": "Sam", "Text": "Who's there?"}
				{"Name": "Ed", "Text": "Go fmt."}
				{"Name": "Sam", "Text": "Go fmt who?"}
				{"Name": "Ed", "Text": "Go fmt yourself!"}
		`
			dec := json.NewDecoder(strings.NewReader(val))

			for {
				var arr map[string]interface{}
				if err := dec.Decode(&arr); err == io.EOF {
					break		// 读取到了最后
				} else if err != nil {	// 读取异常
					log.Fatal(err)
				}
				log.Println(arr)
			}
			/*
				2021/02/23 10:17:33 map[Name:Ed Text:Knock knock.]
				2021/02/23 10:17:33 map[Name:Sam Text:Who's there?]
				2021/02/23 10:17:33 map[Name:Ed Text:Go fmt.]
				2021/02/23 10:17:33 map[Name:Sam Text:Go fmt who?]
				2021/02/23 10:17:33 map[Name:Ed Text:Go fmt yourself!]
			*/
		}
	
	
	# 自定义 Date 格式化
		* 自定义一个 time.Time 类型，实现json的序列化和反序列化接口
			import (
				"fmt"
				"time"
			)

			type LocalDateTime time.Time

			var Format = "2006-01-02 15:04:05"
			var FormatStr = fmt.Sprintf(`"%s"`, Format)

			// 序列化为json
			func (j *LocalDateTime) MarshalJSON () ([]byte, error){
				return []byte(time.Time(*j).Format(FormatStr)), nil
			}
			// 反序列化为对象
			func (j *LocalDateTime) UnmarshalJSON (bytes []byte) error {
				val, err := time.Parse(FormatStr, string(bytes))
				if err != nil {
					return err
				}
				*j = LocalDateTime(val)
				return nil
			}

			func (j *LocalDateTime) String () string {
				return time.Time(*j).Format(Format)
			}
		
		* 使用
			type Foo struct {
				Id int
				Date *model.LocalDateTime
			}
			now := model.LocalDateTime(time.Now())
			val, _ := json.MarshalIndent(&Foo{Id: 1, Date: &now}, "", " ")
			fmt.Println(string(val))

			var foo = new(Foo)
			_ = json.Unmarshal(val, foo)
			fmt.Println(foo)

			// out
			{
			 "Id": 1,
			 "Date": "2021-03-31 10:44:14"
			}
			&{1 2021-03-31 10:44:14}