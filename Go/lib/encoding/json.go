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
		func (dec *Decoder) DisallowUnknownFields()
		func (dec *Decoder) InputOffset() int64
		func (dec *Decoder) More() bool
		func (dec *Decoder) Token() (Token, error)
		func (dec *Decoder) UseNumber()

	
	# Encoder
		type Encoder struct {
		}

		func NewEncoder(w io.Writer) *Encoder

		func (enc *Encoder) Encode(v interface{}) error
		func (enc *Encoder) SetEscapeHTML(on bool)
		func (enc *Encoder) SetIndent(prefix, indent string)

	
	# type RawMessage []byte

		func (m RawMessage) MarshalJSON() ([]byte, error)
		func (m *RawMessage) UnmarshalJSON(data []byte) error 

	# type Delim rune
		func (d Delim) String() string
	
	# type Marshaler interface {
			MarshalJSON() ([]byte, error)
		}
	
	# type Number string
		func (n Number) Float64() (float64, error)
		func (n Number) Int64() (int64, error)
		func (n Number) String() string

	# type RawMessage []byte
		func (m RawMessage) MarshalJSON() ([]byte, error)
		func (m *RawMessage) UnmarshalJSON(data []byte) error
	
	# type Token interface{}
		
----------------------
接口
----------------------
	# Marshaler
		type Marshaler interface {
			MarshalJSON() ([]byte, error)
		}
	
	# Unmarshaler
		type Unmarshaler interface {
			UnmarshalJSON([]byte) error
		}

----------------------
方法
----------------------
	func Compact(dst *bytes.Buffer, src []byte) error
	func HTMLEscape(dst *bytes.Buffer, src []byte)
	func Indent(dst *bytes.Buffer, src []byte, prefix, indent string) error
	func Marshal(v interface{}) ([]byte, error)
	func MarshalIndent(v interface{}, prefix, indent string) ([]byte, error)
	func Unmarshal(data []byte, v interface{}) error
	func Valid(data []byte) bool 