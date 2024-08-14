package main

import (
	"fmt"
)

type Flag byte

const (
	Read Flag = 1 << iota
	Write
	ReadAndWrite
	Connected
)

func Readable(f Flag) bool {
	return (Read & f) != 0
}
func Writeable(f Flag) bool {
	return (Write & f) != 0
}

// TODO 其他标志位判断

func main() {
	fmt.Printf("%d - %04b\n", Read, Read)
	fmt.Printf("%d - %04b\n", Write, Write)
	fmt.Printf("%d - %04b\n", ReadAndWrite, ReadAndWrite)
	fmt.Printf("%d - %04b\n", Connected, Connected)

	// 设置标志，类似于开关多个选项
	f := Connected | Write

	// 判断标志
	fmt.Println(Readable(f))
	fmt.Println(Writeable(f))
}


/*
1 - 0001
2 - 0010
4 - 0100
8 - 1000
false
true
*/