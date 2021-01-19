----------------------------
base64
----------------------------
----------------------------
变量
----------------------------
	const (
		StdPadding rune = '=' // Standard padding character
		NoPadding  rune = -1  // No padding
	)

	# 预定义的编解码器
		var RawStdEncoding = StdEncoding.WithPadding(NoPadding)
		Var RawURLEncoding = URLEncoding.WithPadding(NoPadding)
		var StdEncoding = NewEncoding(encodeStd)
		var URLEncoding = NewEncoding(encodeURL)


----------------------------
type
----------------------------
	# type Encoding struct {
		}
		
		func NewEncoding(encoder string) *Encoding

		func (enc *Encoding) Decode(dst, src []byte) (n int, err error)
		func (enc *Encoding) DecodeString(s string) ([]byte, error)
		func (enc *Encoding) DecodedLen(n int) int
		func (enc *Encoding) Encode(dst, src []byte)
		func (enc *Encoding) EncodeToString(src []byte) string
		func (enc *Encoding) EncodedLen(n int) int
		func (enc Encoding) Strict() *Encoding
		func (enc Encoding) WithPadding(padding rune) *Encoding


----------------------------
方法
----------------------------
	func NewDecoder(enc *Encoding, r io.Reader) io.Reader
	func NewEncoder(enc *Encoding, w io.Writer) io.WriteCloser 
