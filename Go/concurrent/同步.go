------------------
sync
------------------
	# 同步锁
		* 建议使用channel来通信，但是也提供了锁机制，用来并发的访问同一个内存
		* 顶层的 Lokcer 接口
			type Locker interface {
				Lock()
				Unlock()
			}

		* 在 sync 包中提供了2种锁，他们都实现了 Locker 接口

		* 互斥锁
			struct Mutex
				func (m *Mutex) Lock()
				func (m *Mutex) Unlock()
					* 加锁，释放锁
					
		
		* 读写锁，读写互斥，读读不互斥

		struct RWMutex
			func (rw *RWMutex) RLock()
			func (rw *RWMutex) RUnlock()
				* 读加锁，读释放锁

			func (rw *RWMutex) Lock() 
			func (rw *RWMutex) Unlock()
				* 写加锁，写释放锁

			func (rw *RWMutex) RLocker() Locker
				* 获取到读锁
	
	
	# 全局唯一性调用操作
		* 从全局的角度来说，只需要运行一次的代码，比如全局初始化操作
		* sync包，提供了一个Once类型来保证全局的唯一性操作
			
		struc Once 
			func (o *Once) Do(f func())

		
		* demo
			var val string
			// 获取once
			var once sync.Once
			func Init(){
				val = "Hello World"
				fmt.Println("init")
			}
			func Test(){
				// 通过once执行 函数
				once.Do(Init)
				fmt.Println(val)
			}
			func main(){
				go Test()
				go Test()
			}
			
			* once的Do()方法可以保证在全局范围内只调用指定的函数一次（这里指setup()函数），
	
	# WaitGroup，全局等待，类似于Java的 CountDownLatch
		struct WaitGroup
			func (wg *WaitGroup) Add(delta int)
				* 本质上上是初始化一个值，必须在开始异步执行之前初始化完毕

			func (wg *WaitGroup) Done()
				* 对值进行 -1 操作

			func (wg *WaitGroup) Wait() 
				* 阻塞，直到group中的值被减为了0
	

		

		
