package main

import (
	"fmt"
	"time"
)

func main() {

	// 可并发执行的协程数量
	ch := make(chan struct{}, 3)

	for i := 0; i >= 0; i++ {

		// 获取 Token
		ch <- struct{}{}

		// 启动协程
		go Run(func() {
			time.Sleep(time.Millisecond * 500)
			fmt.Printf("执行：%d\n", i)
		}, ch)
	}
}

func Run(task func(), ch chan struct{}) {
	defer func() {
		// 执行完毕后返还 Token
		_ = <-ch
	}()
	task()
}
