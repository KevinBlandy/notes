------------------------
json
------------------------
	# json的核心库 encoding/json
	
		func Marshal(v interface{}) ([]byte, error)
		func MarshalIndent(v interface{}, prefix, indent string) ([]byte, error)
			* 对指定数据进行json编码，返回字节数组
				prefix	输出中的每一个JSON元素都将从一个新的行开始，以prefix开始
				indent	每一个嵌套项的缩进
		
		func Unmarshal(data []byte, v interface{}) error
			* 对字节数组，进行json解码，封装到参数v
	
	# 序列化与反序列化的接口
		# Marshaler
			type Marshaler interface {
				MarshalJSON() ([]byte, error)
			}

		# Unmarshaler
			type Unmarshaler interface {
				UnmarshalJSON([]byte) error
			}
		
		* 如果存在一些自定义的类型，可以通过自己实现接口来完成

------------------------
json 序列化
------------------------
	# 序列化，大多数的数据类型，都可以转换为json
		* nil，会被转换为null字符串
		* 数据结构中出现指针，那么将会转化指针所指向的值
		* []byte，会被转换为base64编码的字符串
		* 结构体中，只有大写开头的属性字段才会被序列化
		* 序列化Map的时候，Map必须是； map[string]T， T就是可以序列化的类型
		* 不能转换为json的类型
			channel
			complex
	
	# 注解
		* 修改结构体中的json输出字段名称
			type Foo struct {
				Name string `json:"name"`		// 把这个Name字段，序列化成json后，json字段名称叫做：name
			}
		
		* 空值/0值得时候不生成字段
			type Foo struct {
				Name string `json:",omitempty"`		// 如果name字段，为空字符串，不生成Name这个字段
				Account string `json:",omitzero"`		// 如果name字段，为空字符串，不生成Name这个字段
					* 如果字段的值为零时，该字段将被省略。
					* 如果字段类型有 IsZero() bool 方法，将使用该方法确定值是否为零。否则，如果该值是其类型的零值，则该值为零。
					* 当要省略零值时，omitzero 字段标记比 omitempty 更清晰、更不易出错。特别是，与 omitempty 不同的是，omitzero 会省略零值的 time.Time 值。
					* 如果同时指定了 omitempty 和 omitzero，那么如果值为空或零（或两者都是），字段就会被省略。
					* UnmarshalTypeError.Field 包含嵌入式结构体，可提供更详细的错误信息。
			}
		
		
		* 不序列化
			json:"-"
		
		* 要被序列化
			json:"-,"
		
		
		* 把字符串解码/编码为数字
			type TestObject struct {
				Field1 int    `json:",string"`  // 对应的json =>  {"Field1": "100"}
			}
		
		* 总结
			`json:"[filedName],[omitempty]"`
		


------------------------
json 反序列化
------------------------
	# 反序列化
		* 反序列化的时候，数值会被转换为Go中的float64类型

------------------------
json 通用的对象
------------------------
	# 未知的json格式，可以用通用的对象处理
		* 对象
			map[string]interface{}
		
		* 数组
			[]interface{}
		
	# 解析未知json
		val := `["name", "Vin"]`
		var body interface{}
		err := json.Unmarshal([]byte(val), &body)
		if err != nil {
			log.Println(err)
		}
		if obj, ok := body.(map[string]interface{}); ok {
			// json 对象
			log.Println(obj)
		}
		if arr, ok := body.([]interface{}); ok {
			// json数组
			log.Println(arr)
		}


------------------------
json 流式读写
------------------------
	# Decoder
		func NewDecoder(r io.Reader) *Decoder

		func (dec *Decoder) UseNumber() 
		func (dec *Decoder) DisallowUnknownFields() 
		func (dec *Decoder) Decode(v interface{}) error
		func (dec *Decoder) Buffered() io.Reader 
		func (dec *Decoder) Token() (Token, error) 
		func (dec *Decoder) More() bool
		func (dec *Decoder) InputOffset() int64
	
	# Encoder
		func NewEncoder(w io.Writer) *Encoder

		func (enc *Encoder) Encode(v interface{}) error 
		func (enc *Encoder) SetIndent(prefix, indent string)
		func (enc *Encoder) SetEscapeHTML(on bool) 