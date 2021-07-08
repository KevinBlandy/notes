
package dataunit


// Copy��spring��ܵ� org.springframework.util.unit.DataSize ʵ��

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

// DataUnit ���ݵ�λ
type DataUnit struct {
	suffix string
	size *DataSize
}
var (
	BYTES = &DataUnit{suffix: "B", size:   OfBytes(1)}
	KILOBYTES = &DataUnit{suffix: "KB", size:   OfKilobytes(1)}
	MEGABYTES = &DataUnit{suffix: "MB", size:   OfMegabytes(1)}
	GIGABYTES = &DataUnit{suffix: "GB", size:   OfGigabytes(1)}
	TERABYTES = &DataUnit{suffix: "TB", size:   OfTerabytes(1)}
)

// FromSuffix ����Suffix��ȡ�� DataUnit
func FromSuffix (suffix string) *DataUnit {
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

// DataSize ���ݴ�С
type DataSize struct {
	bytes int64
}

func (d DataSize) String () string {
	return fmt.Sprintf("%dB", d.bytes)
}

// IsNegative �Ƿ��Ǹ�ֵ
func (d DataSize) IsNegative() bool {
	return d.bytes < 0
}
// ToByte ת��Ϊ�ֽ�
func (d DataSize) ToByte() int64 {
	return d.bytes
}
// ToKilobytes ת��ΪKb
func (d DataSize) ToKilobytes() int64 {
	return d.bytes / BytesPerKb
}
// ToMegabytes ת��ΪMb
func (d DataSize) ToMegabytes() int64 {
	return d.bytes / BytesPerMb
}

// ToGigabytes ת��ΪGB
func (d DataSize) ToGigabytes() int64 {
	return d.bytes / BytesPerGb
}
// ToTerabytes ת��ΪTB
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

// Of ����
func Of (amount int64, unit *DataUnit) *DataSize {
	return &DataSize{bytes: amount * unit.size.ToByte()}
}

// Parse �����ַ���Ϊ���ݵ�λ
func Parse (text string) (*DataSize, error) {
	pattern := regexp.MustCompile(pattern)
	if !pattern.MatchString(text) {
		return nil, errors.New("Does not match data size pattern")
	}
	result := pattern.FindAllStringSubmatch(text, -1)
	size, err := strconv.ParseInt(result[0][1], 10, 64)
	if err != nil {
		return nil, err
	}
	var suffix = result[0][2]
	unit := FromSuffix(suffix)
	if unit == nil {
		return nil, fmt.Errorf("Unknown data unit suffix '%s'", suffix)
	}
	return Of(size, unit), nil
}