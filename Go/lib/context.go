-------------------------
context
-------------------------
	# 类似于Java中的 Future
	# 用来简化对于处理单个请求的多个Goroutine之间与请求域的数据、超时和退出等操作
	
	# Context的简要
		* 层层递进，每一次调用，都是一层
		* 每次创建一个Goroutine，要么将原有的Context传递给Goroutine，要么创建一个子Context并传递给Goroutine。
		* 每一个层的context，都应该是由上级给定
		* 上级可以主动取消下级的context，引发连锁取消
		* Value 查找是回溯树的方式 （从下到上）
	
	# 使用原则
		不要把Context放在结构体中，要以参数的方式传递
		以Context作为参数的函数方法，应该把Context作为第一个参数，放在第一位。
		给一个函数方法传递Context的时候，不要传递nil，如果不知道传递什么，就使用context.TODO
		Context的Value相关方法应该传递必须的数据，不要什么数据都使用这个传递
		Context是线程安全的，可以放心的在多个goroutine中传递

-------------------------
变量
-------------------------
	var Canceled = errors.New("context canceled")
		* 错误的原因，父级主动取消

	var DeadlineExceeded error = deadlineExceededError{}
		* 错误的原因，超时

-------------------------
type
-------------------------
	# type CancelFunc func()
		* 父Goroutine用于取消子context的函数
		* 子节点需要添加代码来判断，父节点是否执行cancel了
			select {
				case <-cxt.Done():
					// do some clean...
			}


	# type Context interface {
			Deadline() (deadline time.Time, ok bool)
				* 返回超时时间，到了这个时间点，就会自动执行cancel()
				* ok==false 时表示没有设置截止时间，不会自动执行cancel()

			Done() <-chan struct{}
				* 当这个chan收到消息，表示上级执行了cancel()，并且这个chan已经被关闭

			Err() error
				* 返回错误原因，也就是为什么上级把我cencel()了
				* 无非就是2个: Canceled/DeadlineExceeded

			Value(key interface{}) interface{}
				* 共享的数据，获取数据是安全的，并且key必须是可hash的
				* 但是使用这个数据(Value)需要注意线程安全问题
		}
		
		func Background() Context
			* 返回空的Context，它不能被取消、没有值、也没有过期时间
			* 用于创建根节点

		func TODO() Context
			* 没有 context，却需要调用一个 context 的函数的场景
			* 不可取消，没有设置截止时间，没有携带任何值

		func WithValue(parent Context, key, val interface{}) Context
			* 包装一个带key/value的context
			* 子孙节点中可以加入新的值，注意若存在Key相同，则会被覆盖。
			* 覆盖，仅仅只会影响子孙节点
				// 一级ctx，添加了3个key1，2，3
				ctx1 := context.WithValue(context.Background(), "key1", "val1")
				ctx1 = context.WithValue(ctx1, "key2", "val2")

				// 二级ctx，进行覆盖了key1，新增了一个key4
				ctx2 := context.WithValue(ctx1, "key1", "new val1")
				ctx2 = context.WithValue(ctx2, "key4", "val4")

				// 一级ctx访问到的是没被覆盖的属性
				log.Println(ctx1.Value("key1"))	// val1

				// 二级ctx访问到的是被覆盖的属性
				log.Println(ctx2.Value("key1"))	// new val1

				// 二级ctx可以访问到一级ctx属性
				log.Println(ctx2.Value("key2"))	// val2

				// 一级ctx不能访问到二级ctx属性
				log.Println(ctx1.Value("key4"))	// <nil>
		
-------------------------
func
-------------------------
	func WithCancel(parent Context) (ctx Context, cancel CancelFunc)
		* 从父节点获取一个context，用于传递给子任务

	func WithDeadline(parent Context, d time.Time) (Context, CancelFunc)
		* 从父节点获取一个context，指定超时时间点，用于传递给子任务

	func WithTimeout(parent Context, timeout time.Duration) (Context, CancelFunc) 
		* 从父节点获取一个context，指定超时的时间单位，用于传递给子任务


-------------------------
demo
-------------------------
	# 基本使用
		import (
			"context"
			"fmt"
			"time"
		)

		func main(){

			// 根context
			rootCtx := context.Background()

			// 创建给下级的context
			ctx, cancel := context.WithCancel(rootCtx)

			// 执行多个任务
			go Worker(ctx)
			go Worker(ctx)
			go Worker(ctx)

			// 5秒后，停止所有任务
			time.Sleep(5 * time.Second)
			cancel()

			// 暂停2秒，等待任务结束
			time.Sleep(2 * time.Second)
		}

		func Worker(ctx context.Context){
			for {
				select {
					case <- ctx.Done(): {
						fmt.Println("任务结束")
						return
					}
					default: {
						// 一直在循环执行
						fmt.Println("任务执行...")
						time.Sleep(time.Second)
					}
				}
			}
		}
	
	# 监听退出
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
			ctx, cancel := context.WithCancel(context.Background())
			notify := make(chan os.Signal)
			signal.Notify(notify, os.Kill, os.Interrupt)
			go func() {
				<- notify
				cancel()
			}()
			Work(ctx)
			fmt.Println("bye")
		}
