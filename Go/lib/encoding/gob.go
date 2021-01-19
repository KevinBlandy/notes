--------------------------
gob
--------------------------

--------------------------
变量
--------------------------

--------------------------
type
--------------------------
	# type CommonType struct {
			Name string
			Id   typeId
		}
	
	# type Decoder struct {
		}
		
		* 解码器，把二进制编码为Go语言

		func NewDecoder(r io.Reader) *Decoder
		func (dec *Decoder) Decode(e interface{}) error
		func (dec *Decoder) DecodeValue(v reflect.Value) error
	
	# type Encoder struct {
		}

		* 编码器，把Go数据编码为二进制

		func NewEncoder(w io.Writer) *Encoder
		func (enc *Encoder) Encode(e interface{}) error
		func (enc *Encoder) EncodeValue(value reflect.Value) error
	
	# type GobDecoder interface {
			GobDecode([]byte) error
		}

	# type GobEncoder interface {
			GobEncode() ([]byte, error)
		}
	
--------------------------
func
--------------------------
	func Register(value interface{})
	func RegisterName(name string, value interface{})

--------------------------
Demo
--------------------------
# 序列化与反序列化
	import (
		"bytes"
		"encoding/gob"
		"fmt"
		"os"
	)

	func main(){

		type User struct {
			Name string
			Age uint
			Skill []string
		}

		// 1Kb大小的存储
		buffer := bytes.NewBuffer(make([]byte, 0, 1024))

		// 创建序列化器
		encoder := gob.NewEncoder(buffer)

		// 序列化
		err := encoder.Encode(&User{"KevinBlandy", 27, []string{"Java", "Python", "Javascript", "Go"}})
		if err != nil {
			fmt.Fprintf(os.Stderr, "序列化异常:%v\n", err)
			os.Exit(1)
		}

		// 接收反序列化的对象
		var user *User

		// 创建反序列化器
		decoder := gob.NewDecoder(buffer)
		// 执行反序列化
		decoder.Decode(&user)
		// 最终的数据
		fmt.Println(user)  // &{KevinBlandy 27 [Java Python Javascript Go]}
	}