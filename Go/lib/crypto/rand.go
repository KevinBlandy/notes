---------------------
rand
---------------------

---------------------
var
---------------------
	var Reader io.Reader
		* 全局的、共享的、在密码学上的安全的随机数发生器。
			在 Linux 和 FreeBSD 上，Reader 使用 getrandom(2) 如果可用，否则使用 /dev/urandom。
			在 OpenBSD 上，Reader 使用 getentropy(2) 。
			在其他类似Unix的系统中，Reader使用/dev/urandom读取。
			在Windows系统中，Reader使用RtlGenRandom API。
			在Wasm系统上，Reader使用Web Crypto API。

---------------------
type
---------------------


---------------------
func
---------------------
	func Int(rand io.Reader, max *big.Int) (n *big.Int, err error)
	func Prime(rand io.Reader, bits int) (p *big.Int, err error)
	func Read(b []byte) (n int, err error)
		* 本质上是
			return io.ReadFull(Reader, b)
	
	func Text() string
		* 使用标准 RFC 4648 base32 字母表返回一个加密随机字符串，供需要秘密字符串、令牌、密码或其他文本时使用。
		* 结果至少包含 128 比特的随机性，足以防止暴力猜测攻击，并使碰撞的可能性微乎其微。未来版本可能会根据需要返回更长的文本，以保持这些特性。
		* 例如：3BVGQSPSODDDH7JZMKFWR5N5XE


---------------------
demo
---------------------
	# 生成安全的随机密钥

		package main

		import (
			"crypto/rand"
			"encoding/base64"
			"fmt"
		)

		func main() {
			// 32 个字节的密钥
			key := make([]byte, 32)
			for range 5 {
				// 生成
				if _, err := rand.Read(key); err != nil {
					panic(err)
				}
				fmt.Println(base64.StdEncoding.EncodeToString(key), len(key))
			}
		}
