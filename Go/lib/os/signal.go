------------------------
signal
------------------------

------------------------
变量
------------------------

------------------------
type
------------------------

------------------------
func
------------------------
	func Ignore(sig ...os.Signal)
	func Ignored(sig os.Signal) bool
	func Notify(c chan<- os.Signal, sig ...os.Signal)
		* 把信号sig传递给c，如果不指定sig，则所有的信号都会被传递
		* 一般 c 的缓冲区大小设置为1，足够

	func Reset(sig ...os.Signal)
	func Stop(c chan<- os.Signal)

	func NotifyContext(parent context.Context, signals ...os.Signal) (ctx context.Context, stop context.CancelFunc)
		* 在收到信号的时候，会自动触发 ctx 的 Done 


------------------------
Demo
------------------------
	# 处理2种信号
		import (
			"fmt"
			"os"
			"os/signal"
			"time"
		)

		func main(){
			// 创建信号通道
			signalChan := make(chan os.Signal, 1)
			// 注册通道，绑定要监听的信号
			signal.Notify(signalChan, os.Interrupt, os.Kill)
			// 监听信号事件
			go func() {
				for {
					sig := <- signalChan
					switch sig {
						case os.Interrupt: {
							fmt.Println("被Interrupt")
						}
						case os.Kill: {
							fmt.Println("被Kill")
						}
					}
				}
			}()

			for {
				fmt.Printf("runing ...")
				time.Sleep(20 * time.Second)
			}
		}

	
	# 使用NotifyContext自动cancel
		func Work (ctx context.Context){
			for {
				select {
					case <- ctx.Done(): {
						fmt.Println("服务退出...")
						return
					}
					default: {
						time.Sleep(time.Second * 1)
						fmt.Println("运行中...")
					}
				}
			}
		}
		func main(){
			ctx, cancel := signal.NotifyContext(context.Background(), os.Kill, os.Interrupt)
			defer func() {
				cancel()
				fmt.Println("cancel 执行")
			}()
			Work(ctx)
			fmt.Println("bye")
		}
