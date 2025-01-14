

// Timestamp Unix 时间戳，精确到毫米
type Timestamp uint64

func (t Timestamp) LocalTime() time.Time {
	return time.UnixMilli(int64(t))
}
func (t Timestamp) Value() (driver.Value, error) {
	return t.LocalTime(), nil
}
func (t *Timestamp) Scan(src any) error {
	switch src := src.(type) {
	case time.Time:
		*t = Timestamp(uint64(src.UnixMilli()))
	case *time.Time:
		*t = Timestamp(uint64(src.UnixMilli()))
	case int64:
		*t = Timestamp(uint64(src))
	default:
		return fmt.Errorf("unsupported type %T", src)
	}
	return nil
}


// 可以用在 Stuct 结构体上
type User struct {
	Id        uint64    `json:"id,string"`
	Balance   string    `json:"balance"`
	CreatedAt Timestamp `json:"createdAt,string"`  // 序列化为字符串类型的时间戳
}

// 驱动链接，要解析时间，并且设置时区为 Local
parseTime=True&loc=Local
