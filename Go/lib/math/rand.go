----------------------
rand
----------------------

----------------------
变量
----------------------

----------------------
rand
----------------------
	# type Rand struct {
		}
		func New(src Source) *Rand
			* 初始化随机数生成器
				r := rand.New(rand.NewSource(time.Now().UnixNano()))

		func (r *Rand) ExpFloat64() float64
		func (r *Rand) Float32() float32
		func (r *Rand) Float64() float64
		func (r *Rand) Int() int
		func (r *Rand) Int31() int32
		func (r *Rand) Int31n(n int32) int32
		func (r *Rand) Int63() int64
		func (r *Rand) Int63n(n int64) int64
		func (r *Rand) Intn(n int) int
		func (r *Rand) NormFloat64() float64
		func (r *Rand) Perm(n int) []int
		func (r *Rand) Read(p []byte) (n int, err error)
		func (r *Rand) Seed(seed int64)
		func (r *Rand) Shuffle(n int, swap func(i, j int))
		func (r *Rand) Uint32() uint32
		func (r *Rand) Uint64() uint64
	
	# type Source interface {
			Int63() int64
			Seed(seed int64)
		}
		func NewSource(seed int64) Source
	
	# type Source64 interface {
			Source
			Uint64() uint64
		}
	
	# type Zipf struct {
		}
		func NewZipf(r *Rand, s float64, v float64, imax uint64) *Zipf
		func (z *Zipf) Uint64() uint64

----------------------
方法
----------------------
	func ExpFloat64() float64
	func Float32() float32
	func Float64() float64
	func Int() int
	func Int31() int32
	func Int31n(n int32) int32
	func Int63() int64
	func Int63n(n int64) int64
	func Intn(n int) int
	func NormFloat64() float64
	func Perm(n int) []int
	func Read(p []byte) (n int, err error)
	func Seed(seed int64)
		* 设定随机数种子
			rand.Seed(time.Now().UnixNano())

	func Shuffle(n int, swap func(i, j int))
	func Uint32() uint32
	func Uint64() uint64



----------------------
Demo
----------------------
	# 生成 0 - 9 随机数
		var random = rand.New(rand.NewSource(time.Now().UnixNano()))
		fmt.Println(random.Intn(10))
	
	# 系统启动的时候，一般就设置全局的随机种子
		rand.Seed(time.Now().UTC().UnixNano())

		* 这个方法已经废弃了，不建议设置全局