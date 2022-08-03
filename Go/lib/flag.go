---------------------------------------
flag
---------------------------------------
	# ֧�ֵ������ʽ
		-flag xxx ��ʹ�ÿո�һ��-���ţ�
		--flag xxx ��ʹ�ÿո�����-���ţ�
		-flag=xxx ��ʹ�õȺţ�һ��-���ţ�
		--flag=xxx ��ʹ�õȺţ�����-���ţ�
	
	# Flag�����ڵ�һ����flag������������-������flag������֮ǰֹͣ����������ֹ�����C��֮��ֹͣ��

	# ����쳣�����ǲ��ǣ�ContinueOnError
		* ��������Ҫ�ȡ����塱��Ȼ��Ž�������Ȼ���쳣
			flag provided but not defined: [��������]

---------------------------------------
����
---------------------------------------
	var CommandLine = NewFlagSet(os.Args[0], ExitOnError)
		* Ĭ�ϵģ����������в���

	var ErrHelp = errors.New("flag: help requested")
		* help��Ϣ�Ǳ����
	
	var Usage = func() {
		fmt.Fprintf(CommandLine.Output(), "Usage of %s:\n", os.Args[0])
		PrintDefaults()
	}
		* Ĭ�ϵ�˵������

---------------------------------------
type
---------------------------------------
	# type Flag struct {
			Name     string //��������
			Usage    string // ������Ϣ
			Value    Value  // ֵ�б�
			DefValue string // Ĭ��ֵ
		}
		func Lookup(name string) *Flag
			* ���ݲ���������ָ����ֵFlag
	
	# type ErrorHandling int
			const (
			ContinueOnError ErrorHandling = iota // �쳣��ʱ�����ִ��
			ExitOnError                          // ִ��ϵͳ�˳� os.Exit(2) ���� for -h/-help Exit(0).
			PanicOnError                         // panic
		)

		* �쳣����ö��
		

	# type FlagSet struct {
			Usage func()		// ����˵������ĵ�
		}

		* ���������еĲ���
		
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
			* ��������
		
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

		* ����ֵ��ת���ӿڣ����԰Ѳ���ֵת��Ϊ ��������
		* ��ʵ���� Value �ӿ�
	
	# type Value interface {
			String() string			// ����ֵ
			Set(string) error		// ��������ֵ
		}

		* ����ֵ�Ľӿ�


---------------------------------------
����
---------------------------------------
	func Arg(i int) string
	func Args() []string
		* ����δ����Ĳ����б�

	func Bool(name string, value bool, usage string) *bool
	func Duration(name string, value time.Duration, usage string) *time.Duration
	func Float64(name string, value float64, usage string) *float64
	func Int64(name string, value int64, usage string) *int64
	func Int(name string, value int, usage string) *int
		* ��������Ϊname��Ĭ��ֵΪvalue��usage����ʾ��Ϣ
		* ���ص��������ֵ��ָ��
		* ʱ��Ľ��������ǣ� 1m/1s/1h/2h45m �ȵ�

	func BoolVar(p *bool, name string, value bool, usage string)
	func DurationVar(p *time.Duration, name string, value time.Duration, usage string)
	func Float64Var(p *float64, name string, value float64, usage string)
	func Int64Var(p *int64, name string, value int64, usage string)
	func IntVar(p *int, name string, value int, usage string)
		* �Լ�����ֵ��ָ��

	func NArg() int
	func NFlag() int

	func Parse()
		* ͨ�����ڶ�����������ѡ�����Ҫ����flag.Parse()��

	func Parsed() bool
		* ���أ��Ƿ��Ѿ������˲���

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
		* ��name���������ø�value�ӿ�
		
	func Visit(fn func(*Flag))
	func VisitAll(fn func(*Flag))
	func TextVar(p encoding.TextUnmarshaler, name string, value encoding.TextMarshaler, usage string)

---------------------------------------
Demo
---------------------------------------
	# ���������������
		import (
			"flag"
			"fmt"
		)
		func main(){
			var epoll = flag.Bool("epoll", false, "��ʾҪʹ��Epoll")
			var port = flag.Int("port", 80, "ʹ�õĶ˿�")
			var host = flag.String("host", "0.0.0.0", "������")
			flag.Parse()
			fmt.Printf("epoll=%v, port=%v, host=%v\n", *epoll, *port, *host)
		}
		// main.exe -epoll=true -port=8080 -host=192.168.0.152
		// epoll=true, port=8080, host=192.168.0.152
	

	# �����Զ���Ĳ���
		import (
			"flag"
			"log"
			"time"
		)
		func main() {
			var flagSet = flag.NewFlagSet("app��������", flag.ExitOnError)

			// Ԥ����Ҫ��ȡ�Ĳ���
			var name = flagSet.String("name", "", "����")
			var age = flagSet.Int("age", 0, "����")
			var expire = flagSet.Duration("expire", time.Second * 0, "vip����ʱ��")

			// ��������
			var err = flagSet.Parse([]string{"-name=Vin", "-age=28", "-expire=2h45m"})
			// var err = flagSet.Parse([]string{"-name=Vin -age=28 expire=2h45m"})

			if err != nil {
				log.Println(err)
			}

			// ��������Ԥ�������
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

