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
