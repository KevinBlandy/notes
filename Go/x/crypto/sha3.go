------------------------
sha3
------------------------
	# sha3 实现了 FIPS-202 标准定义的 SHA-3 固定输出长度哈希函数及 SHAKE 可变输出长度哈希函数。

		* 该包所有类型均实现了 encoding.BinaryMarshaler、encoding.BinaryAppender 和 encoding.BinaryUnmarshaler 接口，用于序列化与反序列化哈希内部状态。
		* 两类哈希函数均采用"海绵"结构和 Keccak 置换算法，详细规范参见 http://keccak.noekeon.org/
	


------------------------
var
------------------------

------------------------
type
------------------------

	# type ShakeHash interface {
			hash.Hash

			// Read reads more output from the hash; reading affects the hash's
			// state. (ShakeHash.Read is thus very different from Hash.Sum)
			// It never returns an error, but subsequent calls to Write or Sum
			// will panic.
			io.Reader

			// Clone returns a copy of the ShakeHash in its current state.
			Clone() ShakeHash
		}
		func NewCShake128(N, S []byte) ShakeHash
		func NewCShake256(N, S []byte) ShakeHash
		func NewShake128() ShakeHash
		func NewShake256() ShakeHash

------------------------
func
------------------------
	func New224() hash.Hash
	func New256() hash.Hash
	func New384() hash.Hash
	func New512() hash.Hash
	func NewLegacyKeccak256() hash.Hash
	func NewLegacyKeccak512() hash.Hash
	func ShakeSum128(hash, data []byte)
	func ShakeSum256(hash, data []byte)
	func Sum224(data []byte) (digest [28]byte)
	func Sum256(data []byte) (digest [32]byte)
	func Sum384(data []byte) (digest [48]byte)
	func Sum512(data []byte) (digest [64]byte)

------------------------
Demo
------------------------
	# 用于以太坊中的 以太坊的 Keccak-256 计算
		package main

		import (
			"fmt"
			"golang.org/x/crypto/sha3"
		)

		func main() {
			hasher := sha3.NewLegacyKeccak256()
			// 计算方法签名
			hasher.Write([]byte("transfer(address,uint256)"))
			sign := hasher.Sum(nil)
			// a9059cbb2ab09eb219583f4a59a5d0623ade346d962bcd4e46b11da047c9049b
			fmt.Printf("%x\n", sign)
			// 取前 4 字节 0xa9059cbb，即为方法的 ID
		}
	

