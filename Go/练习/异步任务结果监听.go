package main

import (
	"fmt"
	"time"
)

func main() {

	AsyncTask[string](func() (string, error) {
		fmt.Println("任务开始执行")
		time.Sleep(time.Second * 2)
		fmt.Println("任务执行完毕")
		return "OK", nil
	}, func(s string) {
		fmt.Printf("任务执行结果：%s\n", s)
	}, func(err error) {
		fmt.Printf("任务执行异常：%s\n", err.Error())
	})

	time.Sleep(time.Minute)
}

// AsyncTask 异步任务执行，task：任务、onSuccess：成功监听、onError：异常监听
func AsyncTask[T any](task func() (T, error), onSuccess func(T), onError func(error)) {
	listener := make(chan T)
	go func() {
		defer close(listener)
		ret, err := task()
		if err != nil {
			onError(err)
			return
		}
		listener <- ret
	}()
	go func() {
		val, ok := <-listener
		if ok {
			onSuccess(val)
		}
	}()
}
