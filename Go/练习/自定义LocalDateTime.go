import (
	"database/sql/driver"
	"fmt"
	"time"
)

var ZeroTime time.Time

type LocalDateTime time.Time

var format = "2006-01-02 15:04:05"
var formatStr = fmt.Sprintf(`"%s"`, format)

// MarshalJSON 序列化为json
func (l LocalDateTime) MarshalJSON () ([]byte, error){
	return []byte(time.Time(l).Format(formatStr)), nil
}
// UnmarshalJSON 反序列化为对象
func (l *LocalDateTime) UnmarshalJSON (bytes []byte) error {
	val, err := time.Parse(formatStr, string(bytes))
	if err != nil {
		return err
	}
	*l = LocalDateTime(val)
	return nil
}

// Value Go数据转换为数据库数据
func (l LocalDateTime) Value() (driver.Value, error) {
	return time.Time(l), nil
}

// Scan 数据库数据转换为Go数据
func (l *LocalDateTime) Scan(src interface{}) error {
	switch t := src.(type) {
		case time.Time: {
			*l = LocalDateTime(t)
			return nil
		}
	}
	return fmt.Errorf("无法解析 %v 为LocalDateTime", src)
}