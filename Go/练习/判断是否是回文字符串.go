
import (
	"fmt"
)

func main() {
	val := test("112212211")
	fmt.Println(val)
}
// 是否是回文字符串
func test(str string) bool {

	// 转换为字符切片
	chars := []rune(str)

	// 获取切片长度
	length := len(chars)

	// 遍历计算
	for i := 0; i < (length / 2); i ++ {
		// 判断前后对应位置的字符是否一样
		if chars[i] != chars[length - 1 - i] {
			return false
		}
	}
	return true
}
