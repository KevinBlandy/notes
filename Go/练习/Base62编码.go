package base62

import (
	"errors"
	"math"
	"regexp"
	"strings"
)

// 62进制字符，升序排序
var chars = []rune("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz")

// 进制
const scale = 62

// MaxVal 62进制最大值
const MaxVal = 56800235583

// 最长6位字符
const pattern = "^[a-zA-Z0-9]{1,6}$"

// 预定义异常信息
var (
	BadNumber = errors.New("bad number")
	BadString = errors.New("bad string")
)

// Encode 编码为字符串
func Encode(val int64) (string, error) {
	if val < 0 || val > MaxVal {
		return "", BadNumber
	}
	var builder = &strings.Builder{}
	for val > (scale - 1) {
		_, err := builder.WriteRune(chars[val%scale])
		if err != nil {
			return "", err
		}
		val = val / scale
	}
	_, err := builder.WriteRune(chars[val])
	if err != nil {
		return "", err
	}
	return reverse(builder.String()), nil
}

// Decode 解码
func Decode(str string) (int64, error) {
	ok, err := regexp.MatchString(pattern, str)
	if err != nil {
		return 0, err
	}
	if !ok {
		return 0, BadString
	}
	var val int64 = 0
	var runes = []rune(str)
	for i := 0; i < len(runes); i++ {
		val += int64(IndexOf(chars, runes[i])) * int64(math.Pow(scale, float64(len(runes)-i-1)))
	}
	return val, nil
}

// IndexOf 二分搜索
func IndexOf(arr []rune, val rune) int {
	var low, high = 0, len(arr) - 1
	for low <= high {
		var mid = int(uint(low+high) >> 1)
		var midVal = arr[mid]
		if midVal > val {
			high = mid - 1
		} else if midVal < val {
			low = mid + 1
		} else {
			return mid
		}
	}
	return -1
}

// Reverse 反转字符串
func reverse(s string) string {
	runes := []rune(s)
	for i, j := 0, len(runes)-1; i < j; i, j = i+1, j-1 {
		runes[i], runes[j] = runes[j], runes[i]
	}
	return string(runes)
}
