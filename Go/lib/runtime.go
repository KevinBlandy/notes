-----------------
模块常量
-----------------
	const Compiler = "gc"
	const GOARCH string = sys.GOARCH
	const GOOS string = sys.GOOS

	var MemProfileRate int = 512 * 1024


-----------------
结构体
-----------------

-----------------
自定义类型
-----------------
	# type MemStats struct {
			Alloc uint64
			TotalAlloc uint64
			Sys uint64
			Lookups uint64
			Mallocs uint64
			Frees uint64
			HeapAlloc uint64
			HeapSys uint64
			HeapIdle uint64
			HeapInuse uint64
			HeapReleased uint64
			HeapObjects uint64
			StackInuse uint64
			StackSys uint64
			MSpanInuse uint64
			MSpanSys uint64
			MCacheInuse uint64
			MCacheSys uint64
			BuckHashSys uint64
			GCSys uint64
			OtherSys uint64
			NextGC uint64
			LastGC uint64
			PauseTotalNs uint64
			PauseNs [256]uint64
			PauseEnd [256]uint64
			NumGC uint32
			NumForcedGC uint32
			GCCPUFraction float64
			EnableGC bool
			DebugGC bool
			BySize [61]struct {
				Size uint32
				Mallocs uint64
				Frees uint64
			}
		}
	
	
	# type Error interface {
			error
			RuntimeError()
		}

		* 运行时异常

	# type Func struct {
		}
		func FuncForPC(pc uintptr) *Func
			* 根据pc返回fanc信息

		func (f *Func) Entry() uintptr
		func (f *Func) FileLine(pc uintptr) (file string, line int)
		func (f *Func) Name() string
			* 返回函数的名称
	
	# type Frames struct {
		}
		* 程序调用栈信息

		func CallersFrames(callers []uintptr) *Frames
		func (ci *Frames) Next() (frame Frame, more bool)

-----------------
方法
-----------------
	func BlockProfile(p []BlockProfileRecord) (n int, ok bool)
	func Breakpoint()
	func CPUProfile() []byte
	func Caller(skip int) (pc uintptr, file string, line int, ok bool)
		* 返回指定的调用栈的指针，文件名称，行号，是否获取成功

	func Callers(skip int, pc []uintptr) int
		* 返回调用栈的程序计数器, 放到一个uintptr中

	func GC()
	func GOMAXPROCS(n int) int
		* 设置可以同时执行的最大CPU数，并返回以前的设置。如果n <1，则不会更改当前设置。
		* 可以使用NumCPU查询本地计算机上的逻辑CPU数量。
		* 实际线程数要比你设置的这个数要大，有时候甚至远远大于设置的数值

	func GOROOT() string
	func Goexit()
	func GoroutineProfile(p []StackRecord) (n int, ok bool)
	func Gosched()
	func KeepAlive(x interface{})

	func UnlockOSThread()
	func LockOSThread()
		* 会把当前goroutine绑定在当前的系统线程上，这个goroutine总是在这个线程中执行，而且也不会有其它goroutine在这个线程中执行。
		* 只有这个goroutine调用了相同次数的 UnlockOSThread 函数之后，才会进行解绑。
		* 如果goroutine在退出的时候没有unlock这个线程，那么这个线程会被终止。

	func MemProfile(p []MemProfileRecord, inuseZero bool) (n int, ok bool)
	func MutexProfile(p []BlockProfileRecord) (n int, ok bool)
	func NumCPU() int
		* 获取CPU内核的数量

	func NumCgoCall() int64
	func NumGoroutine() int
	func ReadMemStats(m *MemStats)
	func ReadTrace() []byte
	func SetBlockProfileRate(rate int)
	func SetCPUProfileRate(hz int)
	func SetCgoTraceback(version int, traceback, context, symbolizer unsafe.Pointer)
	func SetFinalizer(obj interface{}, finalizer interface{})
	func SetMutexProfileFraction(rate int) int
	func Stack(buf []byte, all bool) int
		* 写入调用栈信息到buf
	
	func StartTrace() error
	func StopTrace()
	func ThreadCreateProfile(p []StackRecord) (n int, ok bool)
	
	func Version() string
		* 返回Go的版本


-----------------
Demo
-----------------
	# 获取调用方法的名称
		func printMyName() string {
			pc, _, _, _ := runtime.Caller(1)
			return runtime.FuncForPC(pc).Name()
		}