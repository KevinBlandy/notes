---------------------------------------
flag
---------------------------------------
	# 支持的命令格式
		-flag xxx （使用空格，一个-符号）
		--flag xxx （使用空格，两个-符号）
		-flag=xxx （使用等号，一个-符号）
		--flag=xxx （使用等号，两个-符号）
	
	# Flag解析在第一个非flag参数（单个”-“不是flag参数）之前停止，或者在终止符”–“之后停止。

	# 如果异常处理是不是：ContinueOnError
		* 参数必须要先“定义”，然后才解析，不然会异常
			flag provided but not defined: [参数名称]

---------------------------------------
变量
---------------------------------------
	var CommandLine = NewFlagSet(os.Args[0], ExitOnError)
		* 默认的，就是命令行参数

	var ErrHelp = errors.New("flag: help requested")
		* help信息是必须的
	
	var Usage = func() {
		fmt.Fprintf(CommandLine.Output(), "Usage of %s:\n", os.Args[0])
		PrintDefaults()
	}
		* 默认的说明方法

---------------------------------------
type
---------------------------------------
	# type Flag struct {
			Name     string //参数名称
			Usage    string // 帮助信息
			Value    Value  // 值列表
			DefValue string // 默认值
		}
		func Lookup(name string) *Flag
			* 根据参数，检索指定的值Flag
	
	# type ErrorHandling int
			const (
			ContinueOnError ErrorHandling = iota // 异常的时候继续执行
			ExitOnError                          // 执行系统退出 os.Exit(2) 或者 for -h/-help Exit(0).
			PanicOnError                         // panic
		)

		* 异常处理枚举
		

	# type FlagSet struct {
			Usage func()		// 参数说明输出文档
		}

		* 保存了所有的参数
		
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
			* 解析参数
		
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

		* 参数值的转换接口，可以把参数值转换为 其他类型
		* 它实现了 Value 接口
	
	# type Value interface {
			String() string			// 返回值
			Set(string) error		// 重新设置值
		}

		* 参数值的接口


---------------------------------------
方法
---------------------------------------
	func Arg(i int) string
	func Args() []string
		* 返回未定义的参数列表

	func Bool(name string, value bool, usage string) *bool
	func Duration(name string, value time.Duration, usage string) *time.Duration
	func Float64(name string, value float64, usage string) *float64
	func Int64(name string, value int64, usage string) *int64
	func Int(name string, value int, usage string) *int
		* 参数名称为name，默认值为value，usage是提示信息
		* 返回的这个参数值的指针
		* 时间的解析可以是： 1m/1s/1h/2h45m 等等

	func BoolVar(p *bool, name string, value bool, usage string)
	func DurationVar(p *time.Duration, name string, value time.Duration, usage string)
	func Float64Var(p *float64, name string, value float64, usage string)
	func Int64Var(p *int64, name string, value int64, usage string)
	func IntVar(p *int, name string, value int, usage string)
		* 自己设置值的指针

	func NArg() int
	func NFlag() int

	func Parse()
		* 通常，在定义了命令行选项后，需要调用flag.Parse()。

	func Parsed() bool
		* 返回，是否已经解析了参数

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
	func TextVar(p encoding.TextUnmarshaler, name string, value encoding.TextMarshaler, usage string)

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
	

	# 解析自定义的参数
		import (
			"flag"
			"log"
			"time"
		)
		func main() {
			var flagSet = flag.NewFlagSet("app启动参数", flag.ExitOnError)

			// 预定义要读取的参数
			var name = flagSet.String("name", "", "名字")
			var age = flagSet.Int("age", 0, "年龄")
			var expire = flagSet.Duration("expire", time.Second * 0, "vip过期时间")

			// 解析参数
			var err = flagSet.Parse([]string{"-name=Vin", "-age=28", "-expire=2h45m"})
			// var err = flagSet.Parse([]string{"-name=Vin -age=28 expire=2h45m"})

			if err != nil {
				log.Println(err)
			}

			// 遍历所有预定义参数
			flagSet.VisitAll(func(f *flag.Flag) {
				log.Printf("name=%s, value=%s\n", f.Name, f.Value.String())
			})
			/*
				2021/02/16 10:11:53 name=age, value=28
				2021/02/16 10:11:53 name=expire, value=2h45m0s
				2021/02/16 10:11:53 name=name, value=Vin
			*/

			log.Printf("name=%s, age=%d, expire=%s\n", *name, *age, *expire)		// 2021/02/16 10:11:53 name=Vin, age=28, expire=2h45m0s
		}

