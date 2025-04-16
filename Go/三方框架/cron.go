-------------
cron
-------------
	# 地址
		https://github.com/robfig/cron
		https://pkg.go.dev/github.com/robfig/cron/v3

		import "github.com/robfig/cron/v3"
	
-------------
lib
-------------
	# type Chain struct {
		}
		
		* job 的wrapper队列，可以为job定义一系列行为

		func NewChain(c ...JobWrapper) Chain
		func (c Chain) Then(j Job) Job
	
	# type ConstantDelaySchedule struct {
			Delay time.Duration
		}
		func Every(duration time.Duration) ConstantDelaySchedule
		func (schedule ConstantDelaySchedule) Next(t time.Time) time.Time
	
	# type Cron struct {
		}
		func New(opts ...Option) *Cron
			* 创建cron调度器

		func (c *Cron) AddFunc(spec string, cmd func()) (EntryID, error)
			* 添加一个job

		func (c *Cron) AddJob(spec string, cmd Job) (EntryID, error)
			* 添加一个job

		func (c *Cron) Entries() []Entry
			* 获取所有任务

		func (c *Cron) Entry(id EntryID) Entry
			* 根据id获取任务

		func (c *Cron) Location() *time.Location
			* 获取时区
			
		func (c *Cron) Remove(id EntryID)
			* 移除一个任务

		func (c *Cron) Run()
			* 开始调度，会阻塞调用线程

		func (c *Cron) Schedule(schedule Schedule, cmd Job) EntryID
		func (c *Cron) Start()
			* 开始调度，不会阻塞调用线程
			* 本质上：go c.run()

		func (c *Cron) Stop() context.Context

	# type Entry struct {
			ID EntryID
				* 任务ID，

			Schedule Schedule
				* 此job应在其上运行的时间表。

			Next time.Time
				* 下一次执行时间，如果cron未启动，则为0
				* 启动或此条目的时间表不满足时，则为零。

			Prev time.Time
				* Prev 是此job的最后一次运行，如果从未运行过，则为0

			WrappedJob Job
				* WrappedJob是当 Schedule 被激活时要运行的东西。

			Job Job
				* 提交给cron的东西。
		}

		* 本质上就是job

		func (e Entry) Valid() bool
			* 如果这不是 0 entry，Valid返回true。
	

	# type EntryID int
		* job ID


	# type FuncJob func()
		func (f FuncJob) Run()
	
	# type Job interface {
			Run()
		}
	
	# type JobWrapper func(Job) Job
		
		* JobWrapper用一些行为来装饰给定的job。
		
		func DelayIfStillRunning(logger Logger) JobWrapper
			* DelayIfStillRunning将 job 序列化，将后续的运行推迟到前一个作业完成。
			* 在延迟超过一分钟后运行的作业，其延迟被记录在Info。

			* 当前任务执行时间超过了间隔时间，当前任务执行完毕后，立即执行下次任务。
			* 这种策略会把未执行的任务都累计起来，在 Stop 的时候会阻塞调度器，直到所有任务都执行完毕
			
		func Recover(logger Logger) JobWrapper
			* Recover 被 warpper 的 job 中的 panic，并用提供的记 Logger 记录它们。

		func SkipIfStillRunning(logger Logger) JobWrapper
			* SkipIfStillRunning 如果先前的调用仍在运行，则跳过 job 的调用。它将跳过的信息记录在给定的 Logger 中。
			
			* 当前任务执行时间超过了间隔时间，当前任务执行完毕后，等待间隔时间后，执行下次任务。
	
	# type Logger interface {
			Info(msg string, keysAndValues ...interface{})
			Error(err error, msg string, keysAndValues ...interface{})
		}

		* 日志记录器

		var DefaultLogger Logger = PrintfLogger(log.New(os.Stdout, "cron: ", log.LstdFlags))
		var DiscardLogger Logger = PrintfLogger(log.New(ioutil.Discard, "", 0))

		func PrintfLogger(l interface{ ... }) Logger
		func VerbosePrintfLogger(l interface{ ... }) Logger
	
	# type Option func(*Cron)
		* 配置选项

		func WithChain(wrappers ...JobWrapper) Option
			* 指定一系列的 wrapper，会应用到所有加入到corn的Job
		func WithLocation(loc *time.Location) Option
		func WithLogger(logger Logger) Option
		func WithParser(p ScheduleParser) Option
			* WithParser 覆盖了用于解释 job schedule 的解析器。

		func WithSeconds() Option
			* WithSeconds重写了用于解释 job schedule 的解析器，以包括一个作为第一个字段的秒字。

	# type ParseOption int
		* 解析配置

		const (
				Second         ParseOption = 1 << iota // Seconds field, default 0
				SecondOptional                         // Optional seconds field, default 0
				Minute                                 // Minutes field, default 0
				Hour                                   // Hours field, default 0
				Dom                                    // Day of month field, default *
				Month                                  // Month field, default *
				Dow                                    // Day of week field, default *
				DowOptional                            // Optional day of week field, default *
				Descriptor                             // Allow descriptors such as @monthly, @weekly, etc.
		)
	

	# type Parser struct {
		}
		func NewParser(options ParseOption) Parser
		func (p Parser) Parse(spec string) (Schedule, error)

		* 根据解析配置，新建解析器
	
	# type Schedule interface {
			Next(time.Time) time.Time
		}
	
		* Schedule 描述了一个 job 的工作周期。

		func ParseStandard(standardSpec string) (Schedule, error)
	
	# type ScheduleParser interface {
			Parse(spec string) (Schedule, error)
		}

		* ScheduleParser是 schedule 规范解析器的一个接口，它返回一个 Schedule

	
	# type SpecSchedule struct {
			Second, Minute, Hour, Dom, Month, Dow uint64
			Location *time.Location
		}
		
		* SpecSchedule基于传统的crontab规范，指定了一个工作周期（到第二个颗粒度）。它最初被计算出来，并以 bit sets 的形式存储。

		func (s *SpecSchedule) Next(t time.Time) time.Time

			* 下一次返回这个时间表被激活的下一次时间，大于给定的时间。如果找不到满足该时间表的时间，则返回零时间。


-------------
调度
-------------
	package main

	import (
		_ "github.com/go-sql-driver/mysql"
		"github.com/robfig/cron/v3"
		"log"
		"os"
		"time"
	)

	func init() {
		log.Default().SetOutput(os.Stdout)
		log.Default().SetFlags(log.LstdFlags | log.Lshortfile)
	}

	func main() {

		// 初始化 & 启动任务
		scheduler := Initialization()

		// 手动异步开始调度
		go scheduler.Run()

		// 自动异步开始调度
		// scheduler.Start()

		time.Sleep(time.Second * 5)

		// 停止调度器
		ctx := scheduler.Stop()

		// 等待定时器中的任务停止
		<-ctx.Done()

	}

	func Initialization() *cron.Cron {

		scheduler := cron.New(cron.WithSeconds(), // 解析秒
			cron.WithChain(cron.DelayIfStillRunning(cron.DiscardLogger))) // 如果执行超时，不会重复执行，当前任务执行完毕后，立即执行下一个任务，超时日志输出到Discard

		// 指定cron表达式，和执行方法
		scheduler.AddFunc("0/1 * * * * ? ", func() {
			log.Println("任务执行")
			time.Sleep(time.Second * 2)
			log.Println("任务执行完毕")
		})

		return scheduler
	}
