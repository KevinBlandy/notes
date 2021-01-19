------------------
md5
------------------

------------------
var
------------------
	const BlockSize = 64
	const Size = 16

------------------
type
------------------


------------------
func
------------------
	func New() hash.Hash
		* 创建一个新的Hash
	
	func Sum(data []byte) [Size]byte
		* 对指定的内容，进行hash计算返回结果