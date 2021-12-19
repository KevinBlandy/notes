package base62

import (
	"errors"
	"math"
	"regexp"
	"strings"
)

// 62�����ַ�����������
var chars = []rune("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz")

// ����
const scale = 62

// MaxVal 62�������ֵ
const MaxVal = 56800235583

// �6λ�ַ�
const pattern = "^[a-zA-Z0-9]{1,6}$"

// Ԥ�����쳣��Ϣ
var (
	BadNumber = errors.New("bad number")
	BadString = errors.New("bad string")
)

// Encode ����Ϊ�ַ���
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

// Decode ����
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

// IndexOf ��������
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

// Reverse ��ת�ַ���
func reverse(s string) string {
	runes := []rune(s)
	for i, j := 0, len(runes)-1; i < j; i, j = i+1, j-1 {
		runes[i], runes[j] = runes[j], runes[i]
	}
	return string(runes)
}
