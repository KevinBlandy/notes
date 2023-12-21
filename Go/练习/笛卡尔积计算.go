package main

import "fmt"

func main() {
	ret := Attrs(
		[]string{"红", "黄", "蓝"},
		[]string{"钢", "铁", "铝", "金"},
		[]string{"大", "中", "小"})

	for _, v := range ret {
		fmt.Println(v)
	}
}

// Attrs 计算笛卡尔积
func Attrs(attrs ...[]string) [][]string {

	ret := make([][]string, 0)

	line := make([]string, 0)

	var read func(int)

	read = func(depth int) {

		// 一轮遍历到底了
		if len(attrs) == depth {
			ret = append(ret, append([]string{}, line...))
			return
		}
		for _, v := range attrs[depth] {
			line = append(line, v)
			read(depth + 1)
			// 移除最后一个元素
			line = line[:len(line)-1]
		}
	}

	read(0)

	return ret
}
