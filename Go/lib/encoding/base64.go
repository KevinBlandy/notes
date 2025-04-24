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

		func (enc *Encoding) AppendDecode(dst, src []byte) ([]byte, error)
		func (enc *Encoding) AppendEncode(dst, src []byte) []byte
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
		* 创建新的 Base64 编码器，指定输出的目的地
		* 注意！！最后一定要记得调用返回的 WriteCloser 的 Close 方法，才能把缓冲区的数据刷出

			writer := base64.NewEncoder(base64.StdEncoding, w)
			defer writer.Close()  // 一定要记得关闭
			_, err = writer.Write(content) // 写入数据
