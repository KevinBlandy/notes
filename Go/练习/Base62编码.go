package main

import (
	"errors"
	"fmt"
	"math"
	"regexp"
	"strings"
)

// 62进制字符，升序排序
var chars = []rune("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz")

// 进制
const scale = 62

// 62进制最大值
const maxVal = 56800235583

// 最长6位字符
const pattern = "^[a-zA-Z0-9]{1,6}$"

// 预定义异常信息
var (
	BadNumber = errors.New("bad number")
	BadString = errors.New("bad string")
)

func main(){
	test()
}

func test(){
	for i := 100000000; i < 1000000000; i += 10000000 {
		r, err := Base64EncodePad0(i)
		if err != nil {
			fmt.Printf("编码异常:%s\n", err.Error())
			break
		}
		v, err := Base64DecodePad0(r)
		if err != nil {
			fmt.Printf("解码异常:%s\n", err.Error())
			break
		}
		fmt.Printf("%d = %s = %d\n", i, r, v)
		if v != i {
			fmt.Println("解码错误")
			break
		}
	}
}

// 编码为字符串
func Base62Encode (val int) (string, error) {
	if val < 0 || val > maxVal {
		return "", BadNumber
	}
	var builder = &strings.Builder{}
	for val > (scale - 1) {
		_, err := builder.WriteRune(chars[val % scale])
		if err != nil {
			return "", err
		}
		val = val / scale
	}
	_, err := builder.WriteRune(chars[val])
	if err != nil {
		return "", err
	}
	return Reverse(builder.String()), nil
}

// 编码为字符串，如果字符串不足6位，会在前面补“0”
func Base64EncodePad0(val int) (string, error) {
	result, err := Base62Encode(val)
	if err != nil {
		return result, err
	}
	return LeftPad2Len(result, "0", 6), err
}

func Base64Decode(str string) (int, error) {
	ok, err := regexp.MatchString(pattern, str)
	if err != nil {
		return 0, err
	}
	if !ok {
		return 0, BadString
	}
	var val = 0
	var runes = []rune(str)
	for i := 0; i < len(runes); i ++ {
		val += IndexOf(chars, runes[i]) * int(math.Pow(scale, float64(len(runes) - i - 1)))
	}
	return val, nil
}

// 会先清除字符串前面的所有“0”
func Base64DecodePad0(str string) (int, error) {
	var reg, err = regexp.Compile("^0*")
	if err != nil {
		return 0, err
	}
	if str != "" {
		str = reg.ReplaceAllString(str, "")
		if str == "" {
			str = "0"	// 避免 “000000” 情况
		}
	}
	return Base64Decode(str)
}

// 二分搜索
func IndexOf(arr []rune, val rune) int {
	var low, high = 0, len(arr) - 1
	for low <= high {
		var mid = int(uint(low + high) >> 1)
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

// 反转字符串
func Reverse(s string) string {
	runes := []rune(s)
	for i, j := 0, len(runes)-1; i < j; i, j = i+1, j-1 {
		runes[i], runes[j] = runes[j], runes[i]
	}
	return string(runes)
}

// 字符串不足指定长度下，前面填充值
func LeftPad2Len(str string, padStr string, overallLen int) string {
	var padCountInt int
	padCountInt = 1 + ((overallLen - len(padStr)) / len(padStr))
	var retStr = strings.Repeat(padStr, padCountInt) + str
	return retStr[(len(retStr) - overallLen):]
}