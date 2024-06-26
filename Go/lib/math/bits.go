------------------
bits
------------------
	# λ������İ�

------------------
var
------------------
	const UintSize = uintSize

------------------
type
------------------
------------------
func
------------------
	func Add(x, y, carry uint) (sum, carryOut uint)
	func Add32(x, y, carry uint32) (sum, carryOut uint32)
	func Add64(x, y, carry uint64) (sum, carryOut uint64)
	func Div(hi, lo, y uint) (quo, rem uint)
	func Div32(hi, lo, y uint32) (quo, rem uint32)
	func Div64(hi, lo, y uint64) (quo, rem uint64)
	func LeadingZeros(x uint) int
	func LeadingZeros16(x uint16) int
	func LeadingZeros32(x uint32) int
	func LeadingZeros64(x uint64) int
	func LeadingZeros8(x uint8) int
	func Len(x uint) int
	func Len16(x uint16) (n int)
	func Len32(x uint32) (n int)
	func Len64(x uint64) (n int)
	func Len8(x uint8) int
	func Mul(x, y uint) (hi, lo uint)
	func Mul32(x, y uint32) (hi, lo uint32)
	func Mul64(x, y uint64) (hi, lo uint64)
	func OnesCount(x uint) int
	func OnesCount16(x uint16) int
	func OnesCount32(x uint32) int
	func OnesCount64(x uint64) int
	func OnesCount8(x uint8) int
	func Rem(hi, lo, y uint) uint
	func Rem32(hi, lo, y uint32) uint32
	func Rem64(hi, lo, y uint64) uint64
	func Reverse(x uint) uint
	func Reverse16(x uint16) uint16
	func Reverse32(x uint32) uint32
	func Reverse64(x uint64) uint64
	func Reverse8(x uint8) uint8
	func ReverseBytes(x uint) uint
	func ReverseBytes16(x uint16) uint16
	func ReverseBytes32(x uint32) uint32
	func ReverseBytes64(x uint64) uint64
	func RotateLeft(x uint, k int) uint
	func RotateLeft16(x uint16, k int) uint16
	func RotateLeft32(x uint32, k int) uint32
	func RotateLeft64(x uint64, k int) uint64
	func RotateLeft8(x uint8, k int) uint8
	func Sub(x, y, borrow uint) (diff, borrowOut uint)
	func Sub32(x, y, borrow uint32) (diff, borrowOut uint32)
	func Sub64(x, y, borrow uint64) (diff, borrowOut uint64)
	func TrailingZeros(x uint) int
	func TrailingZeros16(x uint16) int
	func TrailingZeros32(x uint32) int
	func TrailingZeros64(x uint64) int
	func TrailingZeros8(x uint8) int