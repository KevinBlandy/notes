package main

import (
	"fmt"
	"time"
)

func Task(ch chan struct{}, name string){
	fmt.Println(fmt.Sprintf("任务 %s - %d 执行", name , 1))
	time.Sleep(time.Second)
	fmt.Println(fmt.Sprintf("任务 %s - %d 执行", name , 2))
	time.Sleep(time.Second)
	fmt.Println(fmt.Sprintf("任务 %s - %d 执行", name , 3))
	ch <- struct{}{}
}

func main(){
	ch := make(chan struct{}, 3)
	go Task(ch, "A")
	go Task(ch, "B")
	go Task(ch, "C")

	<- ch
	<- ch
	<- ch
}