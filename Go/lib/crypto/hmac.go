-----------------------
hmac
-----------------------
	# hmac 实现了美国联邦信息处理标准出版物 198 中定义的密钥散列信息验证码（HMAC）。
		* HMAC 是一种使用密钥对信息进行签名的加密散列。
		* 接收方通过使用相同的密钥重新计算散列来验证。

-----------------------
var
-----------------------


-----------------------
type
-----------------------


-----------------------
func
-----------------------
	func Equal(mac1, mac2 []byte) bool
	func New(h func() hash.Hash, key []byte) hash.Hash

-----------------------
Demo
-----------------------
	# 签名验证
		package main

		import (
			"crypto/hmac"
			"crypto/sha256"
			"fmt"
		)

		func main() {
			// Key
			key := []byte("123456")

			// 签名的内容
			content := []byte("123456789")

			// 签名
			sign, err := Sign(content, key)
			if err != nil {
				panic(err)
			}

			// 验证内容和签名是否匹配
			ok, err := Validate(content, sign, key)
			if err != nil {
				panic(err)
			}

			fmt.Println(ok)
		}

		func Sign(message, key []byte) ([]byte, error) {
			mac := hmac.New(sha256.New, key)
			if _, err := mac.Write(message); err != nil {
				return nil, err
			}
			return mac.Sum(nil), nil
		}

		func Validate(message, sign, key []byte) (bool, error) {
			mac := hmac.New(sha256.New, key)
			if _, err := mac.Write(message); err != nil {
				return false, err
			}
			return hmac.Equal(sign, mac.Sum(nil)), nil
		}
