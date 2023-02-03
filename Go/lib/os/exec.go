-----------------
exec
-----------------

-----------------
var
-----------------
	var ErrNotFound = errors.New("executable file not found in $PATH")
		* 可执行文件没有找到

-----------------
type
-----------------
	# type Cmd struct {
			Path string
				* 执行命令的路径，绝对路径或者相对路径  

			Args []string
				* 命令的参数

			Env []string
				* 进程环境，如果环境为空，则使用当前进程的环境  

			Dir string
				* 指定command的工作目录，如果dir为空，则comman在调用进程所在当前目录中运行  

			Stdin io.Reader
				* 标准输入，如果stdin是nil的话，进程从null device中读取（os.DevNull）
				* stdin也可以是一个文件，否则的话则在运行过程中再开一个goroutine去读取标准输入  

			Stdout io.Writer
				* 标准输出  

			Stderr io.Writer
				* 错误输出，如果这两个（Stdout和Stderr）为空的话，则command运行时将响应的文件描述符连接到os.DevNull  

			ExtraFiles []*os.File
				* 指定由新进程继承的其他 open files
				* windows 上不支持
				
			SysProcAttr *syscall.SysProcAttr
				* 系统进程信息
			
			Process *os.Process
				* Process 底层进程，只启动一次  

			ProcessState *os.ProcessState
				* ProcessState包含一个退出进程的信息，当进程调用Wait或者Run时便会产生该信息．  
			
			Err error // LookPath error, if any.
			Cancel func() error
			WaitDelay time.Duration
		}

		func Command(name string, arg ...string) *Cmd
		func CommandContext(ctx context.Context, name string, arg ...string) *Cmd
			* 提供一个Context，可以用来杀死进程(调用 os.Process.Kill)

		func (c *Cmd) CombinedOutput() ([]byte, error)
			* 执行命令，并且返回标准输出和异常输出的合并结果

		func (c *Cmd) Output() ([]byte, error)
			* 执行命令，返回标准输出的结果
			* 如果c.Stderr为nil，Output会填充ExitError.Stderr。
		
		func (c *Cmd) Run() error
			* 启动指定的命令并等待其完成
			* 本质上是调用了start
				if err := c.Start(); err != nil {
					return err
				}
				return c.Wait()

		func (c *Cmd) Start() error
			* 不会等待进程执行完毕，这个方法会立即返回
			* 如果这个方法成功的执行，那么Process属性就会被设置

		func (c *Cmd) StderrPipe() (io.ReadCloser, error)
			* 获取到异常输出流的管道，可以从这个Reader读取

		func (c *Cmd) StdinPipe() (io.WriteCloser, error)
			* 获取到标准输入流的管道，可以把数据从这个Writer写入

		func (c *Cmd) StdoutPipe() (io.ReadCloser, error)
			* 获取到标准输出流的管道，可以从这个Reader读取

		func (c *Cmd) String() string
		func (c *Cmd) Wait() error
			* 命令必须是以 srart 方法启动的，那么可以调用这个方法阻塞，直到任务结束
			* 如果c.Stdin、c.Stdout或c.Stderr中的任何一个不是*os.File，Wait也会等待各自的I/O循环复制到进程中或从进程中复制出来完成
			* Wait会释放任何与Cmd.Stdin.File相关的资源

		func (c *Cmd) Environ() []string
			* Environ返回一个环境的副本，该命令将在该环境中运行，因为它是当前配置的。

	# type Error struct {
			Name string
			Err error
		}
		func (e *Error) Error() string
		func (e *Error) Unwrap() error
	
	# type ExitError struct {
			*os.ProcessState
			Stderr []byte
				* 如果标准错误信息没有被其他地方收集，那么会收集到这里
				* 这个收集可能只会存储一部分，省略大部分信息
		}
		
		* 执行异常后，会返回这个异常对象
		* 继承了ProcessState，可以获取到进程的状态信息

		func (e *ExitError) Error() string

	


-----------------
func
-----------------
	func LookPath(file string) (string, error)
		* 尝试从path中国解析出执行文件的绝对路径


-----------------
demo
-----------------