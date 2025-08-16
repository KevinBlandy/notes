----------------------
sync
----------------------

----------------------
����
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
		
		* ������ȫ��Map

		func (m *Map) CompareAndDelete(key, old any) (deleted bool)
		func (m *Map) CompareAndSwap(key, old, new any) bool
		func (m *Map) Delete(key interface{})
			* ɾ��KEY

		func (m *Map) Load(key interface{}) (value interface{}, ok bool)
			* ����KEY��ok ��ʾ�Ƿ����

		func (m *Map) LoadAndDelete(key interface{}) (value interface{}, loaded bool)
			* ɾ��KEY�����ҷ���ɾ�����VALUE��loaded ��ʾKEY�Ƿ����
			
		func (m *Map) LoadOrStore(key, value interface{}) (actual interface{}, loaded bool)
			* ���key�����ڣ����� value, false
			* ���key�Ѿ����ڣ��滻������ oldValue, true

		func (m *Map) Range(f func(key, value interface{}) bool)
			* ��������KEY/VAKUE
			* �������false��ֹͣ����
			
		func (m *Map) Store(key, value interface{})
			* �洢KEY
		
		func (m *Map) Swap(key, value any) (previous any, loaded bool)
		func (m *Map) Clear()
			* ɾ��������Ŀ��ʹ Map Ϊ�ա�
	
	# type Mutex struct {}
		func (m *Mutex) Lock()
		func (m *Mutex) Unlock()
		func (m *Mutex) TryLock() bool
	
	# type Once struct {}
		func (o *Once) Do(f func())
	
	# type Pool struct {
			New func() interface{} // contains filtered or unexported fields
		}

		* ����أ���������͸�����ʱ�����Լ����ڴ���䣬����CGѹ����

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
			* ��ݷ�ʽ��Դ��
					wg.Add(1)
					go func() {
						defer wg.Done()
						f()
					}()

		func (wg *WaitGroup) Wait()


----------------------
����
----------------------

	func OnceFunc(f func()) func()
		* ����һ������ g
		* ��ִ��һ��
			onceFunc := sync.OnceFunc(func() {
				fmt.Println("Hello World")
			})

			go onceFunc()
			go onceFunc()
			go onceFunc()
			go onceFunc()
			go onceFunc()
		
		* ���fִ��ʱpanic, ����������������g������ִ��f,����ÿ�ε��ö���panic��
		
	func OnceValue(f func() T) func() T
		* ������ OnceFunc��֧�ַ���һ�������panicԭ��ͬ�ϡ�

	func OnceValues(f func() (T1, T2)) func() (T1, T2)
		* ͬ�ϣ����� 2 ��ֵ


	