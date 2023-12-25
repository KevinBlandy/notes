--------------------
协程
--------------------
	# goroutine，轻量级线程
		* 本质上只是一个独立的线程，但是可以在多个函数的执行栈的上下文之间切换
	

	#  主程序结束的时候，不会等待其他 goroutine（非主goroutine）结束
		func Add(x, y int){
			sum := x + y
			fmt.Println(sum)
		}
		func main(){
			// 协程执行
			go Add(1, 1)
		}

		* 除了执行完毕，没有程序化的方法让一个 goroutine 来停止另一个 goroutine

	

--------------------
channel
--------------------
	# channel声明和初始化
		var [name] chan T

		var ch chan int
		var m map[string] chan bool
	
		* channel 初始化，使用make
			ch := make(chan int)
	
	# 写入和读取数据
		ch <- value		// 写入
		value := <- ch	// 读取，接收存储
		<- ch			// 读取，并且丢弃
		
		* 向channel写入数据通常会导致程序阻塞，直到有其他goroutine从这个channel中读取数据。
		* 如果channel中没有数据，那么从channel中读取数据也会导致程序阻塞，直到channel中被写入数据为止。

		* 如果channel是nil，则读写都会一直阻塞导致死锁

		* 默认是无缓冲的，发送动作一定发生在接收动作完成之前，接收动作一定发生在发送动作完成之前。
		
	
	# 缓冲channel
		* 创建channel，可以指定缓冲区大小
			ch := make(chan int, 15)
		
		* 缓冲区大小的默认值是0，也就是默认的无缓冲区通道，也被成为同步通道
		
		* 写入/读取数据的时候，如果缓冲区还没满/还有数据，不会阻塞
		* 从带缓冲的channel中读取数据可以使用与常规非缓冲channel完全一致的方法，也可以使用range关键来实现更为简便的循环读取
			for v := range ch {
				fmt.Println(v)
			}

			* for 遍历 channel，发送方在完成发送后必须调用 close()，for 才会退出
			* 如果发送方不调用 close，则 for 可能会导致死锁异常
		
		* 缓冲区本质上是一个队列，添加数据，插入到尾部，读取数据从头部读取
		* 通过 cap 查看队列的缓冲区大小
		* 通过 len 查看队列中有效元素的大小（也就是尚未被读取的元素的数量）
			var ch = make(chan int, 10)
			ch <- 1
			fmt.Println(cap(ch))	// 10
			fmt.Println(len(ch))	// 1

	# Channel 是一个引用类型
		var ch1 chan int = nil
		fmt.Println(ch1) // <nil>

		* 默认值为<nil>
		* 通道可以相互比较，如果引用的是同一个通道，返回true
	
	# 单向Channel
		* channel本身必然支持读写，如果只能读，那谁来写？只能写？那谁来读？
		* 单向channel的创建和初始化
				var ch1 chan int = make(chan int)			// 读写
				var ch2 chan<- int = make(chan<- int)		// 只写
				var ch3 <-chan int = make(<-chan int)		// 只读
				fmt.Printf("%T,%T,%T\n", ch1, ch2, ch3)		// chan int,chan<- int,<-chan int
			
			* 只读/写channel反向操作，就会发生编译时异常
				<- ch2		// invalid operation: <-ch2 (receive from send-only type chan<- int)
				ch3 <- 1	// invalid operation: ch3 <- 1 (send to receive-only type <-chan int)
			
		* 类型转换，对channel的读写类型进行转换
			var ch1 chan int = make(chan int)
			var ch2 chan<- int = (chan<- int)(ch1)		// 把读写转换为只写
			var ch3 <-chan int = (<-chan int)(ch1)		// 把读写转换为只读
			fmt.Printf("%T,%T,%T\n", ch1, ch2, ch3)		// chan int,chan<- int,<-chan int
			
			* 然而，只读/只写管道之间不能相互转
				// 只读转只写，异常
				var ch4 chan<- int = (chan<- int)(ch3)	// cannot convert ch3 (type <-chan int) to type chan<- int

				// 只写转只读，异常
				var ch5 <-chan int = (<-chan int)(ch2)		// cannot convert ch2 (type chan<- int) to type <-chan int
			
			* 在函数形参，可以自动的转换
				// 创建双向的channel
				var ch = make(chan int)
				func (ch <- chan int ){
					// 在函数内部，是单向的channel
					fmt.Printf("%T\n", ch) // <-chan int
				}(ch)
		
		* 不能close只读channel，close本身意义就是发送方通知接收方消息已经发送完毕了
		* 只读的channel，不能发送，所以也就不能close
			close(make(<- chan int)) // invalid operation: close(make(<-chan int)) (cannot close receive-only channel)

	
	# channel的关闭 
		close(ch)

		* 注意，close的chan不能为nil，也不能为已经关闭了的chan，否则异常
		* 通道关闭后，如果再进行写，否则会抛出异常，但是可以继续读，读取完毕后，会返回通道类型对应的默认值
		* 通道不一定要关闭，关闭的作用只是告诉消费者，数据已经全部发送完毕
		
		* 0值问题，会带来一些歧义，例如 ch chan int，读取到0，你不知道究竟是读取完毕了，才是本身值就是0
		* 可以采用另一个语法
			var ch  = make(chan int)
			val, ok := <- ch

			* 如果读取成功，ok = true， 如果读取失败，也就是没有数据了， ok = false
		
		* 当 close 一个 channel的时候，所有阻塞在这个 channel 上的 Goroutine 都会收到通知
	
	# 防止Channel的内存泄漏问题
		// 获取3个网站中最快的一个响应结果
		func res() string {
			ch := make(chan string, 3)
			go func() {ch <- request("https://baidu.ocm")}()
			go func() {ch <- request("https://google.ocm")}()
			go func() {ch <- request("https://springboot.io")}()
			// 当channel有第一个数据后，就会立即返回
			return <- ch
		}
		func request(url string) string  {
			// TODO 发起HTTP请求，获取到响应体
			return url
		}

		* 如果这里使用无缓冲的channel，那么res方法中，其他2个慢的request结果会因为没有消费者，而一直卡在“往channel设置值”这里
		* 因为一直卡住的，所以这个channel，也不会被回收，就发生了内存泄漏
		* 一定要保证不需要 gorotine的时候，每个channel可以正常的退出


	# Channel死锁的问题


	# 在goroutine中处理异常问题

	# goroutine的退出
		
	
	# 多核并行化的问题
		* 当前版本的Go编译器还不能很智能地去发现和利用多核的优势。
		* 虽然确实创建了多个goroutine，并且从运行状态看这些goroutine也都在并行运行，但实际上所有这些goroutine都运行在同一个CPU核心上，
		* 在一个goroutine得到时间片执行的时候，其他goroutine都会处于等待状态。
		* 虽然goroutine简化了我们写并行代码的过程，但实际上整体运行效率并不真正高于单线程程序

		* 在Go语言升级到默认支持多CPU的某个版本之前，可以先通过设置环境变量GOMAXPROCS的值来控制使用多少个CPU核心
		* 使用runtime包的GOMAXPROCS方法
			runtime.GOMAXPROCS(15)

			* 通过 NumCPU() 获取内核数量
				count := runtime.NumCPU()
		
	
	# 让出时间片
		* goroutine中控制何时主动出让时间片给其他goroutine
		* 使用runtime包的Gosched()方法
			runtime.Gosched()
		

		* 所有其他goroutine在调用到此语句时，将会先被阻塞，直至全局唯一的once.Do()调用结束后才继续。





