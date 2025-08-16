----------------------
sync
----------------------

----------------------
变量
----------------------



----------------------
type
----------------------
	# type Cond struct {
			L Locker
		}
		func NewCond(l Locker) *Cond
		func (c *Cond) Broadcast()
		func (c *Cond) Signal()
		func (c *Cond) Wait()
	
	# type Locker interface {
			Lock()
			Unlock()
		}
	
	# type Map struct {}
		
		* 并发安全的Map

		func (m *Map) CompareAndDelete(key, old any) (deleted bool)
		func (m *Map) CompareAndSwap(key, old, new any) bool
		func (m *Map) Delete(key interface{})
			* 删除KEY

		func (m *Map) Load(key interface{}) (value interface{}, ok bool)
			* 检索KEY，ok 表示是否存在

		func (m *Map) LoadAndDelete(key interface{}) (value interface{}, loaded bool)
			* 删除KEY，并且返回删除后的VALUE，loaded 表示KEY是否存在
			
		func (m *Map) LoadOrStore(key, value interface{}) (actual interface{}, loaded bool)
			* 如果key不存在，返回 value, false
			* 如果key已经存在，替换，返回 oldValue, true

		func (m *Map) Range(f func(key, value interface{}) bool)
			* 遍历所有KEY/VAKUE
			* 如果返回false，停止遍历
			
		func (m *Map) Store(key, value interface{})
			* 存储KEY
		
		func (m *Map) Swap(key, value any) (previous any, loaded bool)
		func (m *Map) Clear()
			* 删除所有条目，使 Map 为空。
	
	# type Mutex struct {}
		func (m *Mutex) Lock()
		func (m *Mutex) Unlock()
		func (m *Mutex) TryLock() bool
	
	# type Once struct {}
		func (o *Once) Do(f func())
	
	# type Pool struct {
			New func() interface{} // contains filtered or unexported fields
		}

		* 对象池，用来保存和复用临时对象，以减少内存分配，降低CG压力。

		func (p *Pool) Get() interface{}
		func (p *Pool) Put(x interface{})

	
	# type RWMutex struct {}
		func (rw *RWMutex) Lock()
		func (rw *RWMutex) RLock()
		func (rw *RWMutex) RLocker() Locker
		func (rw *RWMutex) RUnlock()
		func (rw *RWMutex) TryLock() bool
		func (rw *RWMutex) TryRLock() bool
		func (rw *RWMutex) Unlock()
	
	# type WaitGroup struct {}
		func (wg *WaitGroup) Add(delta int)
		func (wg *WaitGroup) Done()
		func (wg *WaitGroup) Go(f func())
			* 便捷方式，源码
					wg.Add(1)
					go func() {
						defer wg.Done()
						f()
					}()

		func (wg *WaitGroup) Wait()


----------------------
方法
----------------------

	func OnceFunc(f func()) func()
		* 返回一个函数 g
		* 仅执行一次
			onceFunc := sync.OnceFunc(func() {
				fmt.Println("Hello World")
			})

			go onceFunc()
			go onceFunc()
			go onceFunc()
			go onceFunc()
			go onceFunc()
		
		* 如果f执行时panic, 则后续调用这个函数g不会再执行f,但是每次调用都会panic。
		
	func OnceValue(f func() T) func() T
		* 类似于 OnceFunc，支持返回一个结果，panic原理同上。

	func OnceValues(f func() (T1, T2)) func() (T1, T2)
		* 同上，返回 2 个值


	