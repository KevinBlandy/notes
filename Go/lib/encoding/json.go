----------------------
����
----------------------

----------------------
struct
----------------------
	# type Decoder struct {
		}
		func NewDecoder(r io.Reader) *Decoder
		func (dec *Decoder) Buffered() io.Reader
		func (dec *Decoder) Decode(v interface{}) error
			* ���Խ���һ��json�������ȡ������󣬷��� io.EOF

		func (dec *Decoder) DisallowUnknownFields()
		func (dec *Decoder) InputOffset() int64
		func (dec *Decoder) More() bool
			* �ڽ�������JSON��ʱ�򣬱����Ƿ�����һ��Ԫ��

		func (dec *Decoder) Token() (Token, error)
			* ��ȡ��һ���ָ���������������ʱ�򷵻� io.EOF
			* �ָ���Ϊ��
				[/]/{/}�����ڱ������Ͷ���Ŀ�ʼ�ͽ���
				string
				float64
				<nil>

		func (dec *Decoder) UseNumber()
			* ��һ��������ΪNumber������float64���뵽 interface{}��
			* ʹ��Number����json�е�float64
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

		* �Զ��� JSON ������
			// ����� stdout
			encoder := json.NewEncoder(os.Stdout)
			// ǰ׺ & ����
			encoder.SetIndent("", "  ")
			// ������ HTML
			encoder.SetEscapeHTML(false)

			// ���� & ���
			encoder.Encode(map[string]interface{}{
				"lang": "GO����",
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
		
		* JSON���͵����֣�����ת��ΪGo�еĸ�������
			
		func (n Number) Float64() (float64, error)
		func (n Number) Int64() (int64, error)
		func (n Number) String() string
	
	# type RawMessage []byte
		func (m RawMessage) MarshalJSON() ([]byte, error)
		func (m *RawMessage) UnmarshalJSON(data []byte) error

		* jsonԭʼ�ֽڣ�����Ϊ�ṹ���һ���ֶΣ����л���ʱ�򣬻����չ��Ϊ������json�ֶ�
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
		
		* ˵���ˣ�֪���Ǹ�JSON���ǲ�֪��JSON�����ͣ��ṹ���Ϳ��������
	
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
����
----------------------
	func Compact(dst *bytes.Buffer, src []byte) error
		* ѹ��src(json)��dst

	func HTMLEscape(dst *bytes.Buffer, src []byte)
		* ��src��json������HTML����������dst
			var out bytes.Buffer
			json.HTMLEscape(&out, []byte(`{"Name":"<b>HTML content</b>"}`))
			out.WriteTo(os.Stdout)

	func Indent(dst *bytes.Buffer, src []byte, prefix, indent string) error
		* ��src(json)�����и�ʽ��������������������dst
			prefix	ָ��ǰ׺
			indent	ָ����ʽ���ַ�

	func Marshal(v interface{}) ([]byte, error)
	func MarshalIndent(v interface{}, prefix, indent string) ([]byte, error)
	func Unmarshal(data []byte, v interface{}) error
	func Valid(data []byte) bool
		* �ж��Ƿ��ǺϷ���json

----------------------
demo
----------------------
	# ��������json�ļ��е�����
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

			// ��ȡ��ʼ�ķָ���
			token, err := dec.Token()
			if err != nil {
				log.Fatal(err)
			}
			log.Printf("start token=%s\n", token)		// start token=[

			type User struct {
				Name, Text string
			}

			// ���������ÿһ��
			for dec.More() {
				var user = User{}
				err := dec.Decode(&user)
				if err != nil {
					log.Fatal(err)
				}
				log.Println(user)
			}

			// ��ȡ�����ķָ���
			token, err = dec.Token()
			if err != nil {
				log.Fatal(err)
			}
			log.Printf("end token=%s\n", token)		// end token=]
		}
	
	# ���������ļ��е�ÿһ��json
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
					break		// ��ȡ�������
				} else if err != nil {	// ��ȡ�쳣
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
	
	
	# �Զ��� Date ��ʽ��
		* �Զ���һ�� time.Time ���ͣ�ʵ��json�����л��ͷ����л��ӿ�
			import (
				"fmt"
				"time"
			)

			type LocalDateTime time.Time

			var Format = "2006-01-02 15:04:05"
			var FormatStr = fmt.Sprintf(`"%s"`, Format)

			// ���л�Ϊjson
			func (j *LocalDateTime) MarshalJSON () ([]byte, error){
				return []byte(time.Time(*j).Format(FormatStr)), nil
			}
			// �����л�Ϊ����
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
		
		* ʹ��
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