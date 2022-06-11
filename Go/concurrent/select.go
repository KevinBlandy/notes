----------------------
select
----------------------
	# select，类似于switch的语法
		var ch chan int = make(chan int)
		select {
			case ch  <- 1 :{
				fmt.Println("写入了数据")
			}
			case val :=  <- ch :{
				fmt.Printf("读取到了：%d\n", val)
			}
			default :{
				fmt.Println("没事件")
			}
		}
		
		* select有比较多的限制，其中最大的一条限制就是每个case语句里必须是一个IO操作（例如：面向channel的操作）
		* 当前其中任意一个 case 中的语句，io到了数据，就会执行代码块
		* 如果 select 语句中有多个 channels 准备好，那么 Go 运行时就会在这些准备好的 channles 中随机选择一个。
		* 如果case的channel是nil，则永远不会执行
		* 退出select可以使用 goto break 语法
		* default 不是必须的


	# 超时机制
		* Go语言没有提供直接的超时处理机制，但我们可以利用select机制
		* 虽然select机制不是专为超时而设计的，却能很方便地解决超时问题
			// 数据通道
			var ch = make(chan int, 10)

			// 超时通道
			var timeout = make(chan bool)
			func(){
				time.Sleep(1e9) // 暂停1s
				timeout <- true	// 写入
			}()

			select {
				case ch <- 10 :{
					// 正常通道写入了数据
					fmt.Println("写入了数据")
				}
				case val := <- ch :{
					// 正常通道读取到了数据
					fmt.Println(val)
				}
				case <- timeout :{
					// 从超时通道读取到了数据
					fmt.Println("超时触发")
				}
			}

		* 因为select的特点是只要其中一个case已经完成，程序就会继续往下执行，而不会考虑其他case的情况
		* 这样使用select机制可以避免永久等待的问题，因为程序会在timeout中获取到一个数据后继续执行，无论对ch的读取是否还处于等待状态，从而达成1秒超时的效果

		* 可以使用 time.After(time.Second * 3) 来代替自定义的超时chan
			select {
				case v := <-in:
					fmt.Println(v)
				case <-time.After(time.Second):
					return // 超时
			}
		
----------------------
控制退出
----------------------
import (
	"fmt"
	"time"
)

func main(){

	// 创建通知用的Chanel
	var channel = make(chan struct{})
	go Worker(channel)

	// 主线程模拟一段时间后发送退出指令
	time.Sleep(5 * time.Second)
	channel <- struct{}{}

	// 等待任务退出完成
	time.Sleep(1 * time.Second)
}

func Worker(end <- chan struct{}){
	for {
		select {
			default: {
				// 一直在循环执行
				fmt.Println("开始执行了")
				time.Sleep(time.Second)
			}
			case <- end: {
				// 收到通知后，退出
				fmt.Print("结束了")
				return
			}
		}
	}
}

----------------------
超时退出
----------------------
import (
	"fmt"
	"time"
)

func main(){

	ch := make(chan struct{})

	// 5秒后超时
	go Worker(time.Second * 5, ch)

	// 等待任务完成
	<- ch
}

func Worker(timeout time.Duration, ch chan <- struct{}){
	timeCh := time.After(timeout)
	for {
		select {
			case <- timeCh: {
				fmt.Print("结束了")
				ch <- struct{}{} // 任务结束，通知主线程
				return
			}
			default: {
				// 一直在循环执行
				fmt.Println("开始执行了")
				time.Sleep(time.Second)
			}
		}
	}
}

		
----------------------
context 退出
----------------------
	# 看Context笔记


		
----------------------
判断是否可以写入
----------------------

func Writable(data any, ch chan any) bool {
	select {
	case ch <- data:
		return true
	default:
		return false
	}
}