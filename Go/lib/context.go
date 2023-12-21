-------------------------
context
-------------------------
	# ������Java�е� Future
	# �����򻯶��ڴ���������Ķ��Goroutine֮��������������ݡ���ʱ���˳��Ȳ���
	
	# Context�ļ�Ҫ
		* ���ݽ���ÿһ�ε��ã�����һ��
		* ÿ�δ���һ��Goroutine��Ҫô��ԭ�е�Context���ݸ�Goroutine��Ҫô����һ����Context�����ݸ�Goroutine��
		* ÿһ�����context����Ӧ�������ϼ�����
		* �ϼ���������ȡ���¼���context����������ȡ��
		* Value �����ǻ������ķ�ʽ �����µ��ϣ�
	
	# ʹ��ԭ��
		��Ҫ��Context���ڽṹ���У�Ҫ�Բ����ķ�ʽ����
		��Context��Ϊ�����ĺ���������Ӧ�ð�Context��Ϊ��һ�����������ڵ�һλ��
		��һ��������������Context��ʱ�򣬲�Ҫ����nil�������֪������ʲô����ʹ��context.TODO
		Context��Value��ط���Ӧ�ô��ݱ�������ݣ���Ҫʲô���ݶ�ʹ���������
		Context���̰߳�ȫ�ģ����Է��ĵ��ڶ��goroutine�д���

-------------------------
����
-------------------------
	var Canceled = errors.New("context canceled")
		* �����ԭ�򣬸�������ȡ��

	var DeadlineExceeded error = deadlineExceededError{}
		* �����ԭ�򣬳�ʱ

-------------------------
type
-------------------------
	# type CancelCauseFunc func(cause error)

	# type CancelFunc func()
		* ��Goroutine����ȡ����context�ĺ���
		* �ӽڵ���Ҫ��Ӵ������жϣ����ڵ��Ƿ�ִ��cancel��
			select {
				case <-cxt.Done():
					// do some clean...
			}


	# type Context interface {
			Deadline() (deadline time.Time, ok bool)
				* ���س�ʱʱ�䣬�������ʱ��㣬�ͻ��Զ�ִ��cancel()
				* ok==false ʱ��ʾû�����ý�ֹʱ�䣬�����Զ�ִ��cancel()

			Done() <-chan struct{}
				* �����chan�յ���Ϣ����ʾ�ϼ�ִ����cancel()���������chan�Ѿ����ر�

			Err() error
				* ���ش���ԭ��Ҳ����Ϊʲô�ϼ�����cencel()��
				* �޷Ǿ���2��: Canceled/DeadlineExceeded

			Value(key interface{}) interface{}
				* ��������ݣ���ȡ�����ǰ�ȫ�ģ�����key�����ǿ�hash��
				* ����ʹ���������(Value)��Ҫע���̰߳�ȫ����
		}
		
		func Background() Context
			* ���ؿյ�Context�������ܱ�ȡ����û��ֵ��Ҳû�й���ʱ��
			* ���ڴ������ڵ�

		func TODO() Context
			* û�� context��ȴ��Ҫ����һ�� context �ĺ����ĳ���
			* ����ȡ����û�����ý�ֹʱ�䣬û��Я���κ�ֵ

		func WithValue(parent Context, key, val interface{}) Context
			* ��װһ����key/value��context
			* ����ڵ��п��Լ����µ�ֵ��ע��������Key��ͬ����ᱻ���ǡ�
			* ���ǣ�����ֻ��Ӱ������ڵ㣨����key��ʱ�������ϵݹ�������������ˣ�
				// һ��ctx�������3��key1��2��3
				ctx1 := context.WithValue(context.Background(), "key1", "val1")
				ctx1 = context.WithValue(ctx1, "key2", "val2")

				// ����ctx�����и�����key1��������һ��key4
				ctx2 := context.WithValue(ctx1, "key1", "new val1")
				ctx2 = context.WithValue(ctx2, "key4", "val4")

				// һ��ctx���ʵ�����û�����ǵ�����
				log.Println(ctx1.Value("key1"))	// val1

				// ����ctx���ʵ����Ǳ����ǵ�����
				log.Println(ctx2.Value("key1"))	// new val1

				// ����ctx���Է��ʵ�һ��ctx����
				log.Println(ctx2.Value("key2"))	// val2

				// һ��ctx���ܷ��ʵ�����ctx����
				log.Println(ctx1.Value("key4"))	// <nil>
		
		func WithoutCancel(parent Context) Context
			* WithoutCancel ���ظ��ڵ�ĸ����������ڵ㱻ȡ��ʱ���ø������ᱻȡ����
			* ���ص������Ĳ����� Deadline �� Err���� Done channel Ϊ nil���Է��ص������ĵ��� Cause �᷵�� nil��

	
		
-------------------------
func
-------------------------
	func AfterFunc(ctx Context, f func()) (stop func() bool)
		* ָ���� Context ���� done(��ʱ����ȡ��)����� context �Ѿ� done,��ôf���������á�
		* ���ص� stop ��������ֹͣf�ĵ��ã���� stop �����ò��ҷ��� true��f���ᱻ���á�

	func Cause(c Context) error
		* ��ctx��ȡ���쳣ԭ��

	func WithDeadlineCause(parent Context, d time.Time, cause error) (Context, CancelFunc)
	func WithTimeoutCause(parent Context, timeout time.Duration, cause error) (Context, CancelFunc)
		* �����ڡ�������ޡ��򡰼�ʱ�����ڡ�ʱ���� ctx ȡ��ԭ���쳣���ķ���������ʹ�� Cause ��������ȡ��ԭ��

	func WithCancel(parent Context) (ctx Context, cancel CancelFunc)
		* �Ӹ��ڵ��ȡһ��context�����ڴ��ݸ�������

	func WithDeadline(parent Context, d time.Time) (Context, CancelFunc)
		* �Ӹ��ڵ��ȡһ��context��ָ����ʱʱ��㣬���ڴ��ݸ�������

	func WithTimeout(parent Context, timeout time.Duration) (Context, CancelFunc) 
		* �Ӹ��ڵ��ȡһ��context��ָ����ʱ��ʱ�䵥λ�����ڴ��ݸ�������
	
	func WithCancelCause(parent Context) (ctx Context, cancel CancelCauseFunc)
		* ���� parent ����һ�� context �� cancel ���������cancel����������ȡ����ʱ�򴫵�һ����ԭ��
		* ��ԭ�����ͨ�� context.Cause ������ȡ
		* ʾ��
			ctx, cancel := context.WithCancelCause(parent)
			cancel(myError)  // �����쳣
			ctx.Err() // returns context.Canceled
			context.Cause(ctx) // returns myError

-------------------------
demo
-------------------------
	# ����ʹ��
		import (
			"context"
			"fmt"
			"time"
		)

		func main(){

			// ��context
			rootCtx := context.Background()

			// �������¼���context
			ctx, cancel := context.WithCancel(rootCtx)

			// ִ�ж������
			go Worker(ctx)
			go Worker(ctx)
			go Worker(ctx)

			// 5���ֹͣ��������
			time.Sleep(5 * time.Second)
			cancel()

			// ��ͣ2�룬�ȴ��������
			time.Sleep(2 * time.Second)
		}

		func Worker(ctx context.Context){
			for {
				select {
					case <- ctx.Done(): {
						fmt.Println("�������")
						return
					}
					default: {
						// һֱ��ѭ��ִ��
						fmt.Println("����ִ��...")
						time.Sleep(time.Second)
					}
				}
			}
		}
	
	# �����˳�
		func Work (ctx context.Context){
			for {
				select {
					case <- ctx.Done(): {
						fmt.Println("�����˳�...")
						return
					}
					default: {
						time.Sleep(time.Second * 1)
						fmt.Println("������...")
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
