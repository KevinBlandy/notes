---------------
time
---------------
	# 瓜皮一般的格式化
		* 并不是采用通用的 "yyyy-MM-dd HH:mm:ss"
		* 而是采用Go诞生的日期作为格式化字符串
			2006 1 2 3 4 5 => 2006年1月2日15点04分05秒
		
		* 如果需要格式化为12小时制，需要指定PM


		

---------------
常量
---------------
	# UTC时区
		var UTC *Location = &utcLoc
	
	# 本地时区
		var Local *Location = &localLoc
	
	# 时间格式化的预定义格式
		ANSIC       = "Mon Jan _2 15:04:05 2006"
		UnixDate    = "Mon Jan _2 15:04:05 MST 2006"
		RubyDate    = "Mon Jan 02 15:04:05 -0700 2006"
		RFC822      = "02 Jan 06 15:04 MST"
		RFC822Z     = "02 Jan 06 15:04 -0700" // RFC822 with numeric zone
		RFC850      = "Monday, 02-Jan-06 15:04:05 MST"
		RFC1123     = "Mon, 02 Jan 2006 15:04:05 MST"
		RFC1123Z    = "Mon, 02 Jan 2006 15:04:05 -0700" // RFC1123 with numeric zone
		RFC3339     = "2006-01-02T15:04:05Z07:00"
		RFC3339Nano = "2006-01-02T15:04:05.999999999Z07:00"
		Kitchen     = "3:04PM"
		// Handy time stamps.
		Stamp      = "Jan _2 15:04:05"
		StampMilli = "Jan _2 15:04:05.000"
		StampMicro = "Jan _2 15:04:05.000000"
		StampNano  = "Jan _2 15:04:05.000000000"

---------------
type
---------------
	# type Month int
		* 月份枚举
			const (
				January Month = 1 + iota
				February
				March
				April
				May
				June
				July
				August
				September
				October
				November
				December
			)
		
		func (m Month) String() string
	
	# type Weekday int
		* 周枚举
			const (
				Sunday Weekday = iota
				Monday
				Tuesday
				Wednesday
				Thursday
				Friday
				Saturday
			)
		
		func (d Weekday) String() string 
	
	# type Duration int64
		* 预定义的时间单位
			const (
				Nanosecond  Duration = 1					// 纳秒
				Microsecond          = 1000 * Nanosecond	// 微秒
				Millisecond          = 1000 * Microsecond	// 毫秒
				Second               = 1000 * Millisecond	// 秒
				Minute               = 60 * Second			// 分
				Hour                 = 60 * Minute			// 时
			)
		
		func Since(t Time) Duration 
		func Until(t Time) Duration
		
		func (d Duration) String() string 

		func (d Duration) Nanoseconds() int64
		func (d Duration) Microseconds() int64
		func (d Duration) Milliseconds() int64 
		func (d Duration) Seconds() float64
		func (d Duration) Minutes() float64
		func (d Duration) Hours() float64
			* 返回区间的纳秒/微秒/毫秒/秒/分/时

		func (d Duration) Truncate(m Duration) Duration 
		func (d Duration) Round(m Duration) Duration
		func (d Duration) Abs() Duration
			* 返回绝对值


	# type Time struct 
		
		func (t Time) String() string 
		func (t Time) Format(layout string) string 
			* 格式化时间
				2006-01-02 15:04:05
				2006-01-02 15:04:05.0000000
			
			* 系统预定了很多格式化方式
				time.Now().Format(time.RFC3339)

		func (t Time) AppendFormat(b []byte, layout string) []byte 
			* 格式化，并且添加字符前缀
				text = t.AppendFormat([]byte("Time: "), time.Kitchen) fmt.Println(string(text))

		func (t Time) After(u Time) bool
		func (t Time) Before(u Time) bool 
		func (t Time) Equal(u Time) bool 
			* 是否在指定的时间之前/之后
		
		func (t Time) IsZero() bool
			* 是否是零时间
					t := time.Time{}
					log.Println(t)			//  0001-01-01 00:00:00 +0000 UTC
					log.Println(t.IsZero())  // true

		func (t Time) Date() (year int, month Month, day int) 
		func (t Time) Year() int 
		func (t Time) Month() Month 
		func (t Time) Day() int
		func (t Time) Weekday() Weekday 
			* 获取年月日周

		func (t Time) ISOWeek() (year, week int)
		func (t Time) Clock() (hour, min, sec int) 
			* 返回时分秒

		func (t Time) Hour() int 
		func (t Time) Minute() int 
		func (t Time) Second() int 
			* 获取当前秒
		
		func (t Time) YearDay() int 
			* 按照年份获取日

		func (t Time) UTC() Time 
			* 转换为UTC时间，按照系统时区

		func (t Time) Local() Time
			* 转换为本地时区的时间

		func (t Time) In(loc *Location) Time
			* 以指定的时区，转换为时间

		func (t Time) Location() *Location 
			* 获取这个时间的时区

		func (t Time) Zone() (name string, offset int)
			* 获取这个时间的时区，返回名字和偏移量

		func (t Time) Unix() int64
			* 返回unix时间戳的秒数 - 1652946208
		
		func (t Time) Nanosecond() int 
			* 返回当前秒以后的纳秒数 - 205556100

		func (t Time) UnixNano() int64
			* 返回Unix时间戳的纳秒 - 1652946208205556100

		func (t Time) MarshalBinary() ([]byte, error)
		func (t *Time) UnmarshalBinary(data []byte) error 
		func (t Time) GobEncode() ([]byte, error)
		func (t *Time) GobDecode(data []byte) error 

		func (t Time) MarshalJSON() ([]byte, error) 
		func (t *Time) UnmarshalJSON(data []byte) error 
			* 序列化/反序列化为json

		func (t Time) MarshalText() ([]byte, error) 
		func (t *Time) UnmarshalText(data []byte) error 
		func (t Time) Truncate(d Duration) Time
			* 把当前时间四舍五入到d的倍数
			
		func (t Time) Round(d Duration) Time
			* 回将 t 四舍五入到 d 的最近倍数的结果（自零时起）。
			* 半程值的舍入行为是向上舍入。

		func (t Time) Sub(u Time) Duration 
			* 当前时间减去指定的事件，返回时间区间

		func (t Time) Add(d Duration) Time 
			* 添加指定的时间区间

		func (t Time) AddDate(years int, months int, days int) Time 
			* 添加时间，年月日
		
		func (t Time) GoString() string
			* 当使用fmt 包中的%#v 格式指定符打印时，将返回一个更有用的时间值。
		
		func (t Time) IsDST() bool
			* 当前时间是否是夏令时
		
		func (t Time) UnixMilli() int64
			* 返回时间戳毫秒（不用自己写了）
		
		func (t Time) UnixMicro() int64
			* 返回时间戳微秒
		
		func (t Time) ZoneBounds() (start, end Time)
			* ZoneBounds返回在时间t生效的时区的边界，时区从start开始，下一个时区从end开始。
			* 如果该区域从时间的开始开始，start将被返回为一个零的时间。
			* 如果该区一直持续下去，那么结束将以零时间的形式返回。返回的时间的位置将与t相同。


	# type ParseError struct{
			Layout     string
			Value      string
			LayoutElem string
			ValueElem  string
			Message    string
		}
		
		func (e *ParseError) Error() string 

	# type Timer struct { 
			C <-chan Time
		}
		
		* 演示器，在指定时间后，就会网C发送一次数据

		func NewTimer(d Duration) *Timer 

		func (t *Timer) Stop() bool
		func (t *Timer) Reset(d Duration) bool 

	# type Ticker struct{
			C <-chan Time
		}
		
		* 定时器，指定在指定时间内，会一直重复的往C发送数据

		func NewTicker(d Duration) *Ticker

		func (t *Ticker) Stop()
		func (t *Ticker) Reset(d Duration) 

	
	# type Location struct
		* 时区信息

		func (l *Location) String() string 


---------------
方法
---------------
	func Parse(layout, value string) (Time, error)	
	func ParseInLocation(layout, value string, loc *Location) (Time, error) 
		* 解析指定的时间为本地时间 & 指定时区的时间
		* Parse() 方法会尝试在入参的参数中中分析并读取时区信息，但是如果入参的参数没有指定时区信息的话，那么就会默认使用 UTC 时间

	func ParseDuration(s string) (Duration, error) 
		* 解析Duration，例如："300ms", "-1.5h", "2h45m"
		* 可以使用的单位： "ns", "us" , "ms", "s", "m", "h".

	func Sleep(d Duration)
		* 当前线程暂停一段时间
	
	func Now() Time
		* 获取当前时间

	func Unix(sec int64, nsec int64) Time 
		* 根据unix时间戳创建时间对象（根据本地时区），指定秒和纳秒
			time.Unix(0, 0)  // 1970-01-01 08:00:00 +0800 CST

			now := time.Now()
			time.Unix(now.Unix(), int64(now.Nanosecond())).Equal(now) // true

	func Date(year int, month Month, day, hour, min, sec, nsec int, loc *Location) Time 

	func FixedZone(name string, offset int) *Location 

	func LoadLocationFromTZData(name string, data []byte) (*Location, error)

	func LoadLocation(name string) (*Location, error) 
		* 加载指定的时区
			time.LoadLocation("Asia/Shanghai")
	
	func After(d Duration) <-chan Time
		* 类似于定时器，在指定的时间后，会往一个chan写入当前时间
		* 本质上是 NewTimer 的包装，目的是为了返回chan，防止内存泄漏
			return NewTimer(d).C

	func AfterFunc(d Duration, f func()) *Timer 
		* 在指定时间后，执行 f 方法

	func Tick(d Duration) <-chan Time
		* 定时器
			timer := time.Tick(time.Second)
			for val:= range timer{
				fmt.Println(val) // 1秒钟执行一次
			}
		* 本质上上NewTicker，返回chan，防止内存泄漏
			return NewTicker(d).C
	
	func UnixMilli(msec int64) Time
		* 根据时间戳毫秒，解析为本地时间

	func UnixMicro(usec int64) Time 
		* 根据时间戳微秒，解析为本地时间

---------------
常用操作
---------------
	# 获取Unix时间戳，毫秒 = 纳秒 / 毫秒
		var now = time.Now()
		fmt.Println(now.UnixNano() / int64(time.Millisecond))

	# 获取指定日期当天的开始时间和结束时间
		func DayStartAndEnd(day time.Time) (start, end time.Time){
			dayStart := time.Date(day.Year(), day.Month(), day.Day(), 0, 0, 0, 0, day.Location())
			dayEnd := time.Date(day.Year(), day.Month(), day.Day(), 23, 59, 59, 999999999, day.Location())
			return dayStart, dayEnd
		}

	# 获取指定年份，指定月份的最大天数
		func MaxDaysOfMonth(year int, month time.Month) int {
			switch month {
				case time.January, time.March, time.May, time.July, time.August, time.October, time.December:
					return 31
				case time.February:
					if (year % 4 == 0 && year % 100 !=0) || year % 400 == 0 {
						return 29
					}
					return 28
				default:
					return 30
			}
		}


	# 简单的定时任务执行
		func Schedule (run func(), duration time.Duration) *time.Ticker {
			var ticker = time.NewTicker(duration)
			go func() {
				for range ticker.C {
					func(){
						defer func() {
							if err := recover(); err != nil {
								log.Printf("task err：%s\n", err)
							}
						}()
						run()
					}()
				}
			}()
			return ticker
		}

		func main(){
			fmt.Printf("开始输出，1秒一次\n")

			t := Schedule(func() {
				fmt.Printf("Hello World c=%d\n", runtime.NumGoroutine())
			}, time.Second)

			time.Sleep(time.Second * 5) // 让任务执行5次

			t.Stop()				// 停止任务

			time.Sleep(time.Second)		// 等待停止操作执行完毕
		}
