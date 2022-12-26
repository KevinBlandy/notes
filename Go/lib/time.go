---------------
time
---------------
	# ��Ƥһ��ĸ�ʽ��
		* �����ǲ���ͨ�õ� "yyyy-MM-dd HH:mm:ss"
		* ���ǲ���Go������������Ϊ��ʽ���ַ���
			2006 1 2 3 4 5 => 2006��1��2��15��04��05��
		
		* �����Ҫ��ʽ��Ϊ12Сʱ�ƣ���Ҫָ��PM


		

---------------
����
---------------
	# UTCʱ��
		var UTC *Location = &utcLoc
	
	# ����ʱ��
		var Local *Location = &localLoc
	
	# ʱ���ʽ����Ԥ�����ʽ
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
		* �·�ö��
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
		* ��ö��
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
		* Ԥ�����ʱ�䵥λ
			const (
				Nanosecond  Duration = 1					// ����
				Microsecond          = 1000 * Nanosecond	// ΢��
				Millisecond          = 1000 * Microsecond	// ����
				Second               = 1000 * Millisecond	// ��
				Minute               = 60 * Second			// ��
				Hour                 = 60 * Minute			// ʱ
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
			* �������������/΢��/����/��/��/ʱ

		func (d Duration) Truncate(m Duration) Duration 
		func (d Duration) Round(m Duration) Duration
		func (d Duration) Abs() Duration
			* ���ؾ���ֵ


	# type Time struct 
		
		func (t Time) String() string 
		func (t Time) Format(layout string) string 
			* ��ʽ��ʱ��
				2006-01-02 15:04:05
				2006-01-02 15:04:05.0000000
			
			* ϵͳԤ���˺ܶ��ʽ����ʽ
				time.Now().Format(time.RFC3339)

		func (t Time) AppendFormat(b []byte, layout string) []byte 
			* ��ʽ������������ַ�ǰ׺
				text = t.AppendFormat([]byte("Time: "), time.Kitchen) fmt.Println(string(text))

		func (t Time) After(u Time) bool
		func (t Time) Before(u Time) bool 
		func (t Time) Equal(u Time) bool 
			* �Ƿ���ָ����ʱ��֮ǰ/֮��
		
		func (t Time) IsZero() bool
			* �Ƿ�����ʱ��
					t := time.Time{}
					log.Println(t)			//  0001-01-01 00:00:00 +0000 UTC
					log.Println(t.IsZero())  // true

		func (t Time) Date() (year int, month Month, day int) 
		func (t Time) Year() int 
		func (t Time) Month() Month 
		func (t Time) Day() int
		func (t Time) Weekday() Weekday 
			* ��ȡ��������

		func (t Time) ISOWeek() (year, week int)
		func (t Time) Clock() (hour, min, sec int) 
			* ����ʱ����

		func (t Time) Hour() int 
		func (t Time) Minute() int 
		func (t Time) Second() int 
			* ��ȡ��ǰ��
		
		func (t Time) YearDay() int 
			* ������ݻ�ȡ��

		func (t Time) UTC() Time 
			* ת��ΪUTCʱ�䣬����ϵͳʱ��

		func (t Time) Local() Time
			* ת��Ϊ����ʱ����ʱ��

		func (t Time) In(loc *Location) Time
			* ��ָ����ʱ����ת��Ϊʱ��

		func (t Time) Location() *Location 
			* ��ȡ���ʱ���ʱ��

		func (t Time) Zone() (name string, offset int)
			* ��ȡ���ʱ���ʱ�����������ֺ�ƫ����

		func (t Time) Unix() int64
			* ����unixʱ��������� - 1652946208
		
		func (t Time) Nanosecond() int 
			* ���ص�ǰ���Ժ�������� - 205556100

		func (t Time) UnixNano() int64
			* ����Unixʱ��������� - 1652946208205556100

		func (t Time) MarshalBinary() ([]byte, error)
		func (t *Time) UnmarshalBinary(data []byte) error 
		func (t Time) GobEncode() ([]byte, error)
		func (t *Time) GobDecode(data []byte) error 

		func (t Time) MarshalJSON() ([]byte, error) 
		func (t *Time) UnmarshalJSON(data []byte) error 
			* ���л�/�����л�Ϊjson

		func (t Time) MarshalText() ([]byte, error) 
		func (t *Time) UnmarshalText(data []byte) error 
		func (t Time) Truncate(d Duration) Time
			* �ѵ�ǰʱ���������뵽d�ı���
			
		func (t Time) Round(d Duration) Time
			* �ؽ� t �������뵽 d ����������Ľ��������ʱ�𣩡�
			* ���ֵ��������Ϊ���������롣

		func (t Time) Sub(u Time) Duration 
			* ��ǰʱ���ȥָ�����¼�������ʱ������

		func (t Time) Add(d Duration) Time 
			* ���ָ����ʱ������

		func (t Time) AddDate(years int, months int, days int) Time 
			* ���ʱ�䣬������
		
		func (t Time) GoString() string
			* ��ʹ��fmt ���е�%#v ��ʽָ������ӡʱ��������һ�������õ�ʱ��ֵ��
		
		func (t Time) IsDST() bool
			* ��ǰʱ���Ƿ�������ʱ
		
		func (t Time) UnixMilli() int64
			* ����ʱ������루�����Լ�д�ˣ�
		
		func (t Time) UnixMicro() int64
			* ����ʱ���΢��
		
		func (t Time) ZoneBounds() (start, end Time)
			* ZoneBounds������ʱ��t��Ч��ʱ���ı߽磬ʱ����start��ʼ����һ��ʱ����end��ʼ��
			* ����������ʱ��Ŀ�ʼ��ʼ��start��������Ϊһ�����ʱ�䡣
			* �������һֱ������ȥ����ô����������ʱ�����ʽ���ء����ص�ʱ���λ�ý���t��ͬ��


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
		
		* ��ʾ������ָ��ʱ��󣬾ͻ���C����һ������

		func NewTimer(d Duration) *Timer 

		func (t *Timer) Stop() bool
		func (t *Timer) Reset(d Duration) bool 

	# type Ticker struct{
			C <-chan Time
		}
		
		* ��ʱ����ָ����ָ��ʱ���ڣ���һֱ�ظ�����C��������

		func NewTicker(d Duration) *Ticker

		func (t *Ticker) Stop()
		func (t *Ticker) Reset(d Duration) 

	
	# type Location struct
		* ʱ����Ϣ

		func (l *Location) String() string 


---------------
����
---------------
	func Parse(layout, value string) (Time, error)	
	func ParseInLocation(layout, value string, loc *Location) (Time, error) 
		* ����ָ����ʱ��Ϊ����ʱ�� & ָ��ʱ����ʱ��
		* Parse() �����᳢������εĲ������з�������ȡʱ����Ϣ�����������εĲ���û��ָ��ʱ����Ϣ�Ļ�����ô�ͻ�Ĭ��ʹ�� UTC ʱ��

	func ParseDuration(s string) (Duration, error) 
		* ����Duration�����磺"300ms", "-1.5h", "2h45m"
		* ����ʹ�õĵ�λ�� "ns", "us" , "ms", "s", "m", "h".

	func Sleep(d Duration)
		* ��ǰ�߳���ͣһ��ʱ��
	
	func Now() Time
		* ��ȡ��ǰʱ��

	func Unix(sec int64, nsec int64) Time 
		* ����unixʱ�������ʱ����󣨸��ݱ���ʱ������ָ���������
			time.Unix(0, 0)  // 1970-01-01 08:00:00 +0800 CST

			now := time.Now()
			time.Unix(now.Unix(), int64(now.Nanosecond())).Equal(now) // true

	func Date(year int, month Month, day, hour, min, sec, nsec int, loc *Location) Time 

	func FixedZone(name string, offset int) *Location 

	func LoadLocationFromTZData(name string, data []byte) (*Location, error)

	func LoadLocation(name string) (*Location, error) 
		* ����ָ����ʱ��
			time.LoadLocation("Asia/Shanghai")
	
	func After(d Duration) <-chan Time
		* �����ڶ�ʱ������ָ����ʱ��󣬻���һ��chanд�뵱ǰʱ��
		* �������� NewTimer �İ�װ��Ŀ����Ϊ�˷���chan����ֹ�ڴ�й©
			return NewTimer(d).C

	func AfterFunc(d Duration, f func()) *Timer 
		* ��ָ��ʱ���ִ�� f ����

	func Tick(d Duration) <-chan Time
		* ��ʱ��
			timer := time.Tick(time.Second)
			for val:= range timer{
				fmt.Println(val) // 1����ִ��һ��
			}
		* ��������NewTicker������chan����ֹ�ڴ�й©
			return NewTicker(d).C
	
	func UnixMilli(msec int64) Time
		* ����ʱ������룬����Ϊ����ʱ��

	func UnixMicro(usec int64) Time 
		* ����ʱ���΢�룬����Ϊ����ʱ��

---------------
���ò���
---------------
	# ��ȡUnixʱ��������� = ���� / ����
		var now = time.Now()
		fmt.Println(now.UnixNano() / int64(time.Millisecond))

	# ��ȡָ�����ڵ���Ŀ�ʼʱ��ͽ���ʱ��
		func DayStartAndEnd(day time.Time) (start, end time.Time){
			dayStart := time.Date(day.Year(), day.Month(), day.Day(), 0, 0, 0, 0, day.Location())
			dayEnd := time.Date(day.Year(), day.Month(), day.Day(), 23, 59, 59, 999999999, day.Location())
			return dayStart, dayEnd
		}

	# ��ȡָ����ݣ�ָ���·ݵ��������
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


	# �򵥵Ķ�ʱ����ִ��
		func Schedule (run func(), duration time.Duration) *time.Ticker {
			var ticker = time.NewTicker(duration)
			go func() {
				for range ticker.C {
					func(){
						defer func() {
							if err := recover(); err != nil {
								log.Printf("task err��%s\n", err)
							}
						}()
						run()
					}()
				}
			}()
			return ticker
		}

		func main(){
			fmt.Printf("��ʼ�����1��һ��\n")

			t := Schedule(func() {
				fmt.Printf("Hello World c=%d\n", runtime.NumGoroutine())
			}, time.Second)

			time.Sleep(time.Second * 5) // ������ִ��5��

			t.Stop()				// ֹͣ����

			time.Sleep(time.Second)		// �ȴ�ֹͣ����ִ�����
		}
