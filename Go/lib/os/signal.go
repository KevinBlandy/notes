------------------------
signal
------------------------

------------------------
����
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
		* ���ź�sig���ݸ�c�������ָ��sig�������е��źŶ��ᱻ����
		* һ�� c �Ļ�������С����Ϊ1���㹻

	func Reset(sig ...os.Signal)
	func Stop(c chan<- os.Signal)

	func NotifyContext(parent context.Context, signals ...os.Signal) (ctx context.Context, stop context.CancelFunc)
		* �����������һ�� ctx
		* ��parentִ���� cancel(), ���������յ� sinals �����źŵ�ʱ�򣬻��Զ�ִ�з��� ctx �� cancel


------------------------
Demo
------------------------
	# ����2���ź�
		import (
			"fmt"
			"os"
			"os/signal"
			"time"
		)

		func main(){
			// �����ź�ͨ��
			signalChan := make(chan os.Signal, 1)
			// ע��ͨ������Ҫ�������ź�
			signal.Notify(signalChan, os.Interrupt, os.Kill)
			// �����ź��¼�
			go func() {
				for {
					sig := <- signalChan
					switch sig {
						case os.Interrupt: {
							fmt.Println("��Interrupt")
						}
						case os.Kill: {
							fmt.Println("��Kill")
						}
					}
				}
			}()

			for {
				fmt.Printf("runing ...")
				time.Sleep(20 * time.Second)
			}
		}

	
	# ʹ��NotifyContext�Զ�cancel
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
			ctx, cancel := signal.NotifyContext(context.Background(), os.Kill, os.Interrupt)
			defer func() {
				cancel()
				fmt.Println("cancel ִ��")
			}()
			Work(ctx)
			fmt.Println("bye")
		}
