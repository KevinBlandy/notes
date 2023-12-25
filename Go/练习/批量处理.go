package main

import (
	"context"
	"errors"
	"fmt"
	"time"
)

func main() {

	queue := make(chan int, 100)

	exit := make(chan struct{})

	ctx, cancel := context.WithTimeout(context.Background(), time.Second*10)
	defer cancel()

	go func() {
		err := BatchTask(ctx, 5, queue, func(batch []int) {
			fmt.Printf("Batch Hanler：Size = %v Value = %v\n ", len(batch), batch)
		})

		if err != nil {
			fmt.Println(err.Error())
		}

		exit <- struct{}{}
	}()

	go func() {
		for i := 0; i <= 1000; i++ {
			queue <- i
		}
		close(queue)
	}()

	<-ctx.Done()

}

// BatchTask 批量任务处理
// ctx 取消操作
// size 批次大小
// queue 数据队列
// fn 处理函数
func BatchTask[T any](ctx context.Context, size int, queue chan T, fn func([]T)) error {
	if size < 1 {
		return errors.New("bad size")
	}
	batch := make([]T, 0, size) // 0 长度的队列

	for {
		select {
		case <-ctx.Done():
			if len(batch) > 0 { // 任务取消，还有剩余任务需要一次性处理完
				fn(batch)
			}
			return ctx.Err()
		case item, ok := <-queue:
			if !ok {
				if len(batch) > 0 { // 队列关闭，如果还有剩余元素，需要一次性处理完
					fn(batch)
				}
				return nil
			}
			batch = append(batch, item) // 收到任务，添加到批量切片
			if len(batch) >= size {
				fn(batch)
				batch = make([]T, 0, size) // 攒满一批后，批量执行，并且重置容器
			}
		default: // 数据生产过慢，避免空轮询导致CPU 100%
			if len(batch) > 0 {
				fn(batch) // 没有等到数据，但是容器中给还有剩余内容
				batch = make([]T, 0, size)
			} else {
				select {
				case item, ok := <-queue: // 没有等到数据，容器中也没内容。阻塞，直到有新的数据。（避免CPU耗费）
					if !ok {
						return nil // 队列关闭
					}
					// 等到新的 1 一个数据，添加到 batch 容器
					// 如果生产速度慢，则还会进入到 default 代码块，此时 len(batch) == 1，所以会被 if 分支处理掉
					batch = append(batch, item)
				case <-ctx.Done(): // 收到了退出通知
					if len(batch) > 0 {
						fn(batch)
					}
					return ctx.Err()
				}
			}
		}
	}
}
