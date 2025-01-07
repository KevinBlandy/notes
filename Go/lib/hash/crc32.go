------------------------
crc32
------------------------
	#  crc32 实现了 32 位循环冗余校验或 CRC-32 校验和

------------------------
var
------------------------

	const (
		// IEEE is by far and away the most common CRC-32 polynomial.
		// Used by ethernet (IEEE 802.3), v.42, fddi, gzip, zip, png, ...
		IEEE = 0xedb88320

		// Castagnoli's polynomial, used in iSCSI.
		// Has better error detection characteristics than IEEE.
		// https://dx.doi.org/10.1109/26.231911
		Castagnoli = 0x82f63b78

		// Koopman's polynomial.
		// Also has better error detection characteristics than IEEE.
		// https://dx.doi.org/10.1109/DSN.2002.1028931
		Koopman = 0xeb31d82e
	)

	const Size = 4

	var IEEETable = simpleMakeTable(IEEE)

------------------------
type
------------------------
	# type Table [256]uint32

		func MakeTable(poly uint32) *Table

------------------------
func
------------------------

	func Checksum(data []byte, tab *Table) uint32
	func ChecksumIEEE(data []byte) uint32
	func New(tab *Table) hash.Hash32
	func NewIEEE() hash.Hash32
	func Update(crc uint32, tab *Table, p []byte) uint32