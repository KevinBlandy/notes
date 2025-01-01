--------------------------
ripemd160
--------------------------
	# ripemd160 HASH 算法
	
--------------------------
var
--------------------------

	const BlockSize = 64

	const Size = 20


--------------------------
type
--------------------------

--------------------------
func
--------------------------

	func New() hash.Hash


--------------------------
Demo
--------------------------
	# ripemd160 Hash 加密

		package main

		import (
			"fmt"
			"golang.org/x/crypto/ripemd160"
		)

		func main() {
			var hash = ripemd160.New()
			hash.Write([]byte("hello world"))
			// 98c615784ccb5fe5936fbc0cbe9dfdb408d92f0f
			fmt.Printf("%x\n", hash.Sum(nil))
		}
