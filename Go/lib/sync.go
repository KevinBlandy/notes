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
		func (m *Map) Delete(key interface{})
		func (m *Map) Load(key interface{}) (value interface{}, ok bool)
		func (m *Map) LoadAndDelete(key interface{}) (value interface{}, loaded bool)
		func (m *Map) LoadOrStore(key, value interface{}) (actual interface{}, loaded bool)
		func (m *Map) Range(f func(key, value interface{}) bool)
		func (m *Map) Store(key, value interface{})
	
	# type Mutex struct {}
		func (m *Mutex) Lock()
		func (m *Mutex) Unlock()
	
	# type Once struct {}
		func (o *Once) Do(f func())
	
	# type Pool struct {
			New func() interface{} // contains filtered or unexported fields
		}
		func (p *Pool) Get() interface{}
		func (p *Pool) Put(x interface{})
	
	# type RWMutex struct {}
		func (rw *RWMutex) Lock()
		func (rw *RWMutex) RLock()
		func (rw *RWMutex) RLocker() Locker
		func (rw *RWMutex) RUnlock()
		func (rw *RWMutex) Unlock()
	
	# type WaitGroup struct {}
		func (wg *WaitGroup) Add(delta int)
		func (wg *WaitGroup) Done()
		func (wg *WaitGroup) Wait()


----------------------
方法
----------------------
	