-------------------------
异常处理
-------------------------
	# Go的设计中，把所有的错误，都当作值来进行处理，目前(V1.12)没有异常处理机制

	#一般使用panic/recover 模式来处理异常
		func panic(v interface{})
			* 用于抛出异常，可以在任何地方触发
			* 异常抛出后，程序不会往下执行
				func main() {
					fmt.Println("start")
					panic("异常了")
					// 后面的代码不会执行
				}
				// -----------
				start
				panic: 异常了

				goroutine 1 [running]:
				main.main()
					d:/golang/main
			

		
		func recover() interface{}
			* 这个方法用来尝试获取panic抛出的异常信息
			* 只能在 defer 调用的函数中有效，并且要定义在可能引发 panic 的语句之前
			
				func main() {
					fmt.Println("start")
					defer func(){
						err := recover()
						if err != nil {
							fmt.Println(err)
							fmt.Printf("%T\n", err)
						}
					}()
					panic("异常了")
					// 后面的代码不会执行
				}
				// -------------
				start
				异常了
				string
			
			* 可以用于修改异常的返回值
				func foo() (err error) {
					defer func (){
						if p := recover(); p != nil{
							// 封装异常信息给返回值
							err = fmt.Errorf("异常了：%v", p)
						}
					}()
					// 下面执行可能会异常的代码
				}

			* 一个panic对应一个recover
			* 一个panic只会被自己上面最近的一个recover捕获到
			* 在嵌套的defer函数中调用recover也将导致无法捕获异常
				func main() {
					defer func() {
						defer func() {
							// 无法捕获异常
							if r := recover(); r != nil {
								fmt.Println(r)
							}
						}()
					}()
					panic(1)
				}
			* defer中调用的是recover函数的包装函数的话，异常的捕获工作将失败
				func main() {
					defer func() {
						// 无法捕获异常
						if r := MyRecover(); r != nil {
							fmt.Println(r)
						}
					}()
					panic(1)
				}

				func MyRecover() interface{} {
					log.Println("trace...")
						return recover()
				}
			* 必须要和有异常的栈帧只隔一个栈帧，recover函数才能正常捕获异常
			* 也可以在defer中继续panic，那么这个panic也是只会被自己上面最近的一个recover捕获到


-------------------------
error 接口
-------------------------
	# Go语言中定义的Error接口
		type error interface {
			Error() string
		}

	# 系统在errors包预定义了一些异常相关的方法
		func New(text string) error 
			* 本质上是系统提供的一个error实现
				type errorString struct {
					s string
				}
				func (e *errorString) Error() string {
					return e.s
				}

		func As(err error, target interface{}) bool
			* target 不能为nil，并且必须是实现了error的接口

		func Is(err, target error) bool
			* 返回 targer 的异常链中，是否有err错误
			* 如果target实现了 Is 方法，会通过这个接口判断，如果target实现了Unwrap方法，会不断剥离出包装的异常进行比较

		func Unwrap(err error) error
			* 如果异常是一个包装异常，也就是实现了: Unwrap() error 
			* 那么就会返回包装的异常，否则返回nil

	
	# 通过 fmt的Errorf格式化一个error对象
		func Errorf(format string, a ...interface{}) error {
			p := newPrinter()
			p.wrapErrs = true
			p.doPrintf(format, a)
			s := string(p.buf)
			var err error
			if p.wrappedErr == nil {
				err = errors.New(s)
			} else {
				err = &wrapError{s, p.wrappedErr}
			}
			p.free()
			return err
		}
		type wrapError struct {
			msg string
			err error
		}

		func (e *wrapError) Error() string {
			return e.msg
		}

		func (e *wrapError) Unwrap() error {
			return e.err
		}


	# 运行时异常，定义在 rumtime 包
		type Error interface {
			error
			RuntimeError()
		}


		