---------------------------------------
flag
---------------------------------------

---------------------------------------
变量
---------------------------------------
	var CommandLine = NewFlagSet(os.Args[0], ExitOnError)
	var ErrHelp = errors.New("flag: help requested")
	var Usage = func() {
		fmt.Fprintf(CommandLine.Output(), "Usage of %s:\n", os.Args[0])
		PrintDefaults()
	}

---------------------------------------
type
---------------------------------------
	# type Flag struct {
			Name     string // name as it appears on command line
			Usage    string // help message
			Value    Value  // value as set
			DefValue string // default value (as text); for usage message
		}
		func Lookup(name string) *Flag
	
	# type ErrorHandling int
			const (
			ContinueOnError ErrorHandling = iota // 返回异常异常信息
			ExitOnError                          // Call os.Exit(2) or for -h/-help Exit(0).
			PanicOnError                         // Call panic with a descriptive error.
		)

		* 异常处理枚举
		

	# type FlagSet struct {
			Usage func()
		}
		
		func NewFlagSet(name string, errorHandling ErrorHandling) *FlagSet

		func (f *FlagSet) Arg(i int) string
		func (f *FlagSet) Args() []string
		func (f *FlagSet) Bool(name string, value bool, usage string) *bool
		func (f *FlagSet) BoolVar(p *bool, name string, value bool, usage string)
		func (f *FlagSet) Duration(name string, value time.Duration, usage string) *time.Duration
		func (f *FlagSet) DurationVar(p *time.Duration, name string, value time.Duration, usage string)
		func (f *FlagSet) ErrorHandling() ErrorHandling
		func (f *FlagSet) Float64(name string, value float64, usage string) *float64
		func (f *FlagSet) Float64Var(p *float64, name string, value float64, usage string)
		func (f *FlagSet) Init(name string, errorHandling ErrorHandling)
		func (f *FlagSet) Int(name string, value int, usage string) *int
		func (f *FlagSet) Int64(name string, value int64, usage string) *int64
		func (f *FlagSet) Int64Var(p *int64, name string, value int64, usage string)
		func (f *FlagSet) IntVar(p *int, name string, value int, usage string)
		func (f *FlagSet) Lookup(name string) *Flag
		func (f *FlagSet) NArg() int
		func (f *FlagSet) NFlag() int
		func (f *FlagSet) Name() string
		func (f *FlagSet) Output() io.Writer
		func (f *FlagSet) Parse(arguments []string) error
		func (f *FlagSet) Parsed() bool
		func (f *FlagSet) PrintDefaults()
		func (f *FlagSet) Set(name, value string) error
		func (f *FlagSet) SetOutput(output io.Writer)
		func (f *FlagSet) String(name string, value string, usage string) *string
		func (f *FlagSet) StringVar(p *string, name string, value string, usage string)
		func (f *FlagSet) Uint(name string, value uint, usage string) *uint
		func (f *FlagSet) Uint64(name string, value uint64, usage string) *uint64
		func (f *FlagSet) Uint64Var(p *uint64, name string, value uint64, usage string)
		func (f *FlagSet) UintVar(p *uint, name string, value uint, usage string)
		func (f *FlagSet) Var(value Value, name string, usage string)
		func (f *FlagSet) Visit(fn func(*Flag))
		func (f *FlagSet) VisitAll(fn func(*Flag))
	
	# type Getter interface {
			Value
			Get() interface{}
		}
	
	# type Value interface {
			String() string
			Set(string) error
		}

---------------------------------------
方法
---------------------------------------
	func Arg(i int) string
	func Args() []string
	func Bool(name string, value bool, usage string) *bool
		* 参数名称为name，默认值为value，usage是提示信息
		* 返回的这个参数值的引用

	func BoolVar(p *bool, name string, value bool, usage string)
	func Duration(name string, value time.Duration, usage string) *time.Duration
	func DurationVar(p *time.Duration, name string, value time.Duration, usage string)
	func Float64(name string, value float64, usage string) *float64
	func Float64Var(p *float64, name string, value float64, usage string)
	func Int(name string, value int, usage string) *int
	func Int64(name string, value int64, usage string) *int64
	func Int64Var(p *int64, name string, value int64, usage string)
	func IntVar(p *int, name string, value int, usage string)

	func NArg() int
	func NFlag() int
	func Parse()
		* 通常，在定义了命令行选项后，需要调用flag.Parse()。

	func Parsed() bool
	func PrintDefaults()
	func Set(name, value string) error
	func String(name string, value string, usage string) *string
	func StringVar(p *string, name string, value string, usage string)
	func Uint(name string, value uint, usage string) *uint
	func Uint64(name string, value uint64, usage string) *uint64
	func Uint64Var(p *uint64, name string, value uint64, usage string)
	func UintVar(p *uint, name string, value uint, usage string)
	func UnquoteUsage(flag *Flag) (name string, usage string)

	func Var(value Value, name string, usage string)
		* 把name参数，设置给value接口
		
	func Visit(fn func(*Flag))
	func VisitAll(fn func(*Flag))

---------------------------------------
Demo
---------------------------------------
	# 解析程序命令参数
		import (
			"flag"
			"fmt"
		)
		func main(){
			var epoll = flag.Bool("epoll", false, "表示要使用Epoll")
			var port = flag.Int("port", 80, "使用的端口")
			var host = flag.String("host", "0.0.0.0", "绑定网卡")
			flag.Parse()
			fmt.Printf("epoll=%v, port=%v, host=%v\n", *epoll, *port, *host)
		}
		// main.exe -epoll=true -port=8080 -host=192.168.0.152
		// epoll=true, port=8080, host=192.168.0.152
	

