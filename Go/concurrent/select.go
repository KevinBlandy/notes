----------------------
select
----------------------
	# select��������switch���﷨
		var ch chan int = make(chan int)
		select {
			case ch  <- 1 :{
				fmt.Println("д��������")
			}
			case val :=  <- ch :{
				fmt.Printf("��ȡ���ˣ�%d\n", val)
			}
			default :{
				fmt.Println("û�¼�")
			}
		}
		
		* select�бȽ϶�����ƣ���������һ�����ƾ���ÿ��case����������һ��IO���������磺����channel�Ĳ�����
		* ��ǰ��������һ�� case �е���䣬io�������ݣ��ͻ�ִ�д����
		* ��� select ������ж�� channels ׼���ã���ô Go ����ʱ�ͻ�����Щ׼���õ� channles �����ѡ��һ����
		* ���case��channel��nil������Զ����ִ��
		* �˳�select����ʹ�� goto break �﷨
		* default ���Ǳ����


	# ��ʱ����
		* Go����û���ṩֱ�ӵĳ�ʱ������ƣ������ǿ�������select����
		* ��Ȼselect���Ʋ���רΪ��ʱ����Ƶģ�ȴ�ܷܺ���ؽ����ʱ����
			// ����ͨ��
			var ch = make(chan int, 10)

			// ��ʱͨ��
			var timeout = make(chan bool)
			func(){
				time.Sleep(1e9) // ��ͣ1s
				timeout <- true	// д��
			}()

			select {
				case ch <- 10 :{
					// ����ͨ��д��������
					fmt.Println("д��������")
				}
				case val := <- ch :{
					// ����ͨ����ȡ��������
					fmt.Println(val)
				}
				case <- timeout :{
					// �ӳ�ʱͨ����ȡ��������
					fmt.Println("��ʱ����")
				}
			}

		* ��Ϊselect���ص���ֻҪ����һ��case�Ѿ���ɣ�����ͻ��������ִ�У������ῼ������case�����
		* ����ʹ��select���ƿ��Ա������õȴ������⣬��Ϊ�������timeout�л�ȡ��һ�����ݺ����ִ�У����۶�ch�Ķ�ȡ�Ƿ񻹴��ڵȴ�״̬���Ӷ����1�볬ʱ��Ч��

		* ����ʹ�� time.After(time.Second * 3) �������Զ���ĳ�ʱchan
			select {
				case v := <-in:
					fmt.Println(v)
				case <-time.After(time.Second):
					return // ��ʱ
			}
		
----------------------
�����˳�
----------------------
import (
	"fmt"
	"time"
)

func main(){

	// ����֪ͨ�õ�Chanel
	var channel = make(chan struct{})
	go Worker(channel)

	// ���߳�ģ��һ��ʱ������˳�ָ��
	time.Sleep(5 * time.Second)
	channel <- struct{}{}

	// �ȴ������˳����
	time.Sleep(1 * time.Second)
}

func Worker(end <- chan struct{}){
	for {
		select {
			default: {
				// һֱ��ѭ��ִ��
				fmt.Println("��ʼִ����")
				time.Sleep(time.Second)
			}
			case <- end: {
				// �յ�֪ͨ���˳�
				fmt.Print("������")
				return
			}
		}
	}
}

----------------------
��ʱ�˳�
----------------------
import (
	"fmt"
	"time"
)

func main(){

	ch := make(chan struct{})

	// 5���ʱ
	go Worker(time.Second * 5, ch)

	// �ȴ��������
	<- ch
}

func Worker(timeout time.Duration, ch chan <- struct{}){
	timeCh := time.After(timeout)
	for {
		select {
			case <- timeCh: {
				fmt.Print("������")
				ch <- struct{}{} // ���������֪ͨ���߳�
				return
			}
			default: {
				// һֱ��ѭ��ִ��
				fmt.Println("��ʼִ����")
				time.Sleep(time.Second)
			}
		}
	}
}

		
----------------------
context �˳�
----------------------
	# ��Context�ʼ�


		
----------------------
�ж��Ƿ����д��
----------------------

func Writable(data any, ch chan any) bool {
	select {
	case ch <- data:
		return true
	default:
		return false
	}
}