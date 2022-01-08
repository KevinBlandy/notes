package dataunit

// 数据大小

import (
	"errors"
	"fmt"
	"regexp"
	"strconv"
)

const pattern = "^([+\\-]?\\d+)([a-zA-Z]{0,2})$"

const (
	BytesPerKb = 1024
	BytesPerMb = BytesPerKb * 1024
	BytesPerGb = BytesPerMb * 1024
	BytesPerTb = BytesPerGb * 1024
)

// DataUnit 数据单位
type DataUnit struct {
	suffix string
	size   *DataSize
}

var (
	BYTES     = &DataUnit{suffix: "B", size: OfBytes(1)}
	KILOBYTES = &DataUnit{suffix: "KB", size: OfKilobytes(1)}
	MEGABYTES = &DataUnit{suffix: "MB", size: OfMegabytes(1)}
	GIGABYTES = &DataUnit{suffix: "GB", size: OfGigabytes(1)}
	TERABYTES = &DataUnit{suffix: "TB", size: OfTerabytes(1)}
)

// FromSuffix 根据Suffix获取到 DataUnit
func FromSuffix(suffix string) *DataUnit {
	switch suffix {
	case BYTES.suffix:
		return BYTES
	case KILOBYTES.suffix:
		return KILOBYTES
	case MEGABYTES.suffix:
		return MEGABYTES
	case GIGABYTES.suffix:
		return GIGABYTES
	case TERABYTES.suffix:
		return TERABYTES
	}
	return nil
}

// DataSize 数据大小
type DataSize struct {
	bytes int64
}

func (d DataSize) MarshalJSON() ([]byte, error) {
	return []byte(fmt.Sprintf("%d", d.bytes)), nil
}

func (d *DataSize) UnmarshalJSON(data []byte) error {
	ret, err := strconv.ParseInt(string(data), 10, 64)
	if err == nil {
		d.bytes = ret
	}
	return err
}

func (d DataSize) String() string {
	return fmt.Sprintf("%d", d.bytes)
}

// IsNegative 是否是负值
func (d DataSize) IsNegative() bool {
	return d.bytes < 0
}

// ToByte 转换为字节
func (d DataSize) ToByte() int64 {
	return d.bytes
}

// ToKilobytes 转换为Kb
func (d DataSize) ToKilobytes() int64 {
	return d.bytes / BytesPerKb
}

// ToMegabytes 转换为Mb
func (d DataSize) ToMegabytes() int64 {
	return d.bytes / BytesPerMb
}

// ToGigabytes 转换为GB
func (d DataSize) ToGigabytes() int64 {
	return d.bytes / BytesPerGb
}

// ToTerabytes 转换为TB
func (d DataSize) ToTerabytes() int64 {
	return d.bytes / BytesPerTb
}

// OfBytes Byte
func OfBytes(bytes int64) *DataSize {
	return &DataSize{bytes: bytes}
}

// OfKilobytes Kb
func OfKilobytes(kilobytes int64) *DataSize {
	return &DataSize{bytes: kilobytes * BytesPerKb}
}

// OfMegabytes Mb
func OfMegabytes(megabytes int64) *DataSize {
	return &DataSize{bytes: megabytes * BytesPerMb}
}

// OfGigabytes GB
func OfGigabytes(gigabytes int64) *DataSize {
	return &DataSize{bytes: gigabytes * BytesPerGb}
}

// OfTerabytes TB
func OfTerabytes(terabytes int64) *DataSize {
	return &DataSize{bytes: terabytes * BytesPerTb}
}

// Of 解析
func Of(amount int64, unit *DataUnit) *DataSize {
	return &DataSize{bytes: amount * unit.size.ToByte()}
}

// Parse 解析字符串为数据单位
func Parse(text string) (*DataSize, error) {
	pattern := regexp.MustCompile(pattern)
	if !pattern.MatchString(text) {
		return nil, errors.New("does not match data size pattern")
	}
	result := pattern.FindAllStringSubmatch(text, -1)
	size, err := strconv.ParseInt(result[0][1], 10, 64)
	if err != nil {
		return nil, err
	}
	var suffix = result[0][2]
	unit := FromSuffix(suffix)
	if unit == nil {
		return nil, fmt.Errorf("unknown data unit suffix '%s'", suffix)
	}
	return Of(size, unit), nil
}
