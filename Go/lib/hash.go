-----------------------------
hash
-----------------------------
	# hash

-----------------------------
var
-----------------------------


-----------------------------
type
-----------------------------
	# type Hash interface {
			io.Writer
			Sum(b []byte) []byte
				* 计算出最终的hash，会把结果append到b

			Reset()
				* 重置

			Size() int
				* hash结果的字节长度

			BlockSize() int
				* 返回哈希的底层块大小
		}

		* Hash算法的接口
	
	# type Hash32 interface {
			Hash
			Sum32() uint32
		}
	
	# type Hash64 interface {
			Hash
			Sum64() uint64
		}

-----------------------------
func
-----------------------------

-----------------------------
hash计算
-----------------------------
	# md5
		import (
			"crypto/md5"
			"fmt"
		)

		func main(){
			// 创建hash实例，就是 hash.Hash 接口实现
			md5 := md5.New()
			// 写入数据
			md5.Write([]byte("123"))
			md5.Write([]byte("456"))

			// 计算结果，append到切片中
			buffer := md5.Sum(make([]byte, 0))
			// 转换为16进制字符串
			hex := fmt.Sprintf("%x", buffer)
			fmt.Println(hex) // e10adc3949ba59abbe56e057f20f883e
		}

		* sha256/sha512 都一样，只是库不同
		* md5/sha256/sha512 都提供了模块级别的快捷计算hash方法
			import (
				"crypto/md5"
				"encoding/hex"
				"fmt"
			)
			func main() {
				data := md5.Sum([]byte("123456"))
				fmt.Println(hex.EncodeToString(data[:]))  // e10adc3949ba59abbe56e057f20f883e
			}
	
