------------------
基于 groutine
------------------


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


------------------
基于 semaphore
------------------
import (
	"fmt"
	"golang.org/x/sync/semaphore"
	"time"
)

func test() error {

	// semaphore
	var sem = semaphore.NewWeighted(5)

	// 迭代任务
	for i := range 10000 {
		if sem.TryAcquire(1) {
			// 获取到令牌，则异步执行
			go func() {
				// 释放令牌
				defer sem.Release(1)
				handle("worker", i)
			}()
		} else {
			// 未获取到令牌，当前主线程执行
			handle("boss", i)
		}
	}

	return nil
}

// 业务处理
func handle(m string, x int) {
	fmt.Printf("%s handle %d\n", m, x)
	time.Sleep(time.Second) // 模拟耗时
}
