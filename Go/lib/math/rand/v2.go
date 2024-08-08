----------------
rand
----------------
	# Go 1.22 的新包，是因为这是标准库第一次为某个包建立v2版本包

---------------
var
---------------

---------------
type
---------------

	# type ChaCha8 struct {
		// contains filtered or unexported fields
		}

		func NewChaCha8(seed [32]byte) *ChaCha8
		func (c *ChaCha8) MarshalBinary() ([]byte, error)
		func (c *ChaCha8) Seed(seed [32]byte)
		func (c *ChaCha8) Uint64() uint64
		func (c *ChaCha8) UnmarshalBinary(data []byte) error
	
	# type PCG struct {
		}

		func NewPCG(seed1, seed2 uint64) *PCG
		func (p *PCG) MarshalBinary() ([]byte, error)
		func (p *PCG) Seed(seed1, seed2 uint64)
		func (p *PCG) Uint64() uint64
		func (p *PCG) UnmarshalBinary(data []byte) error
	
	# type Rand struct {
			// contains filtered or unexported fields
		}

		func New(src Source) *Rand
		func (r *Rand) ExpFloat64() float64
		func (r *Rand) Float32() float32
		func (r *Rand) Float64() float64
		func (r *Rand) Int() int
		func (r *Rand) Int32() int32
		func (r *Rand) Int32N(n int32) int32
		func (r *Rand) Int64() int64
		func (r *Rand) Int64N(n int64) int64
		func (r *Rand) IntN(n int) int
		func (r *Rand) NormFloat64() float64
		func (r *Rand) Perm(n int) []int
		func (r *Rand) Shuffle(n int, swap func(i, j int))
		func (r *Rand) Uint32() uint32
		func (r *Rand) Uint32N(n uint32) uint32
		func (r *Rand) Uint64() uint64
		func (r *Rand) Uint64N(n uint64) uint64
		func (r *Rand) UintN(n uint) uint
	
	# type Source interface {
			Uint64() uint64
		}

	# type Zipf struct {
			// contains filtered or unexported fields
		}
		func NewZipf(r *Rand, s float64, v float64, imax uint64) *Zipf
		func (z *Zipf) Uint64() uint64





---------------
func
---------------
	func ExpFloat64() float64
	func Float32() float32
	func Float64() float64
	func Int() int
	func Int32() int32
	func Int32N(n int32) int32
	func Int64() int64
	func Int64N(n int64) int64
	func IntN(n int) int
		* 随机 0 - n，不包含 n

	func N[Int intType](n Int) Int
		* 返回半开放区间 [0,n) 内的伪随机数
		* 适用于任何整数类型。例如，从 0 到 5 分钟的随机持续时间为： rand.N(5 * time.Minute)。

	func NormFloat64() float64
	func Perm(n int) []int
	func Shuffle(n int, swap func(i, j int))
	func Uint32() uint32
	func Uint32N(n uint32) uint32
	func Uint64() uint64
	func Uint64N(n uint64) uint64
	func UintN(n uint) uint

