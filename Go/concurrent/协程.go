--------------------
Э��
--------------------
	# goroutine���������߳�
		* ������ֻ��һ���������̣߳����ǿ����ڶ��������ִ��ջ��������֮���л�
	

	#  �����������ʱ�򣬲���ȴ����� goroutine������goroutine������
		func Add(x, y int){
			sum := x + y
			fmt.Println(sum)
		}
		func main(){
			// Э��ִ��
			go Add(1, 1)
		}

		* ����ִ����ϣ�û�г��򻯵ķ�����һ�� goroutine ��ֹͣ��һ�� goroutine

	

--------------------
channel
--------------------
	# channel�����ͳ�ʼ��
		var [name] chan T

		var ch chan int
		var m map[string] chan bool
	
		* channel ��ʼ����ʹ��make
			ch := make(chan int)
	
	# д��Ͷ�ȡ����
		ch <- value		// д��
		value := <- ch	// ��ȡ�����մ洢
		<- ch			// ��ȡ�����Ҷ���
		
		* ��channelд������ͨ���ᵼ�³���������ֱ��������goroutine�����channel�ж�ȡ���ݡ�
		* ���channel��û�����ݣ���ô��channel�ж�ȡ����Ҳ�ᵼ�³���������ֱ��channel�б�д������Ϊֹ��

		* ���channel��nil�����д����һֱ������������

		* Ĭ�����޻���ģ����Ͷ���һ�������ڽ��ն������֮ǰ�����ն���һ�������ڷ��Ͷ������֮ǰ��
		
	
	# ����channel
		* ����channel������ָ����������С
			ch := make(chan int, 15)
		
		* ��������С��Ĭ��ֵ��0��Ҳ����Ĭ�ϵ��޻�����ͨ����Ҳ����Ϊͬ��ͨ��
		
		* д��/��ȡ���ݵ�ʱ�������������û��/�������ݣ���������
		* �Ӵ������channel�ж�ȡ���ݿ���ʹ���볣��ǻ���channel��ȫһ�µķ�����Ҳ����ʹ��range�ؼ���ʵ�ָ�Ϊ����ѭ����ȡ
			for v := range ch {
				fmt.Println(v)
			}

			* for ���� channel�����ͷ�����ɷ��ͺ������� close()��for �Ż��˳�
			* ������ͷ������� close���� for ���ܻᵼ�������쳣
		
		* ��������������һ�����У�������ݣ����뵽β������ȡ���ݴ�ͷ����ȡ
		* ͨ�� cap �鿴���еĻ�������С
		* ͨ�� len �鿴��������ЧԪ�صĴ�С��Ҳ������δ����ȡ��Ԫ�ص�������
			var ch = make(chan int, 10)
			ch <- 1
			fmt.Println(cap(ch))	// 10
			fmt.Println(len(ch))	// 1

	# Channel ��һ����������
		var ch1 chan int = nil
		fmt.Println(ch1) // <nil>

		* Ĭ��ֵΪ<nil>
		* ͨ�������໥�Ƚϣ�������õ���ͬһ��ͨ��������true
	
	# ����Channel
		* channel�����Ȼ֧�ֶ�д�����ֻ�ܶ�����˭��д��ֻ��д����˭������
		* ����channel�Ĵ����ͳ�ʼ��
				var ch1 chan int = make(chan int)			// ��д
				var ch2 chan<- int = make(chan<- int)		// ֻд
				var ch3 <-chan int = make(<-chan int)		// ֻ��
				fmt.Printf("%T,%T,%T\n", ch1, ch2, ch3)		// chan int,chan<- int,<-chan int
			
			* ֻ��/дchannel����������ͻᷢ������ʱ�쳣
				<- ch2		// invalid operation: <-ch2 (receive from send-only type chan<- int)
				ch3 <- 1	// invalid operation: ch3 <- 1 (send to receive-only type <-chan int)
			
		* ����ת������channel�Ķ�д���ͽ���ת��
			var ch1 chan int = make(chan int)
			var ch2 chan<- int = (chan<- int)(ch1)		// �Ѷ�дת��Ϊֻд
			var ch3 <-chan int = (<-chan int)(ch1)		// �Ѷ�дת��Ϊֻ��
			fmt.Printf("%T,%T,%T\n", ch1, ch2, ch3)		// chan int,chan<- int,<-chan int
			
			* Ȼ����ֻ��/ֻд�ܵ�֮�䲻���໥ת
				// ֻ��תֻд���쳣
				var ch4 chan<- int = (chan<- int)(ch3)	// cannot convert ch3 (type <-chan int) to type chan<- int

				// ֻдתֻ�����쳣
				var ch5 <-chan int = (<-chan int)(ch2)		// cannot convert ch2 (type chan<- int) to type <-chan int
			
			* �ں����βΣ������Զ���ת��
				// ����˫���channel
				var ch = make(chan int)
				func (ch <- chan int ){
					// �ں����ڲ����ǵ����channel
					fmt.Printf("%T\n", ch) // <-chan int
				}(ch)
		
		* ����closeֻ��channel��close����������Ƿ��ͷ�֪ͨ���շ���Ϣ�Ѿ����������
		* ֻ����channel�����ܷ��ͣ�����Ҳ�Ͳ���close
			close(make(<- chan int)) // invalid operation: close(make(<-chan int)) (cannot close receive-only channel)

	
	# channel�Ĺر� 
		close(ch)

		* ע�⣬close��chan����Ϊnil��Ҳ����Ϊ�Ѿ��ر��˵�chan�������쳣
		* ͨ���رպ�����ٽ���д��������׳��쳣�����ǿ��Լ���������ȡ��Ϻ󣬻᷵��ͨ�����Ͷ�Ӧ��Ĭ��ֵ
		* ͨ����һ��Ҫ�رգ��رյ�����ֻ�Ǹ��������ߣ������Ѿ�ȫ���������
		
		* 0ֵ���⣬�����һЩ���壬���� ch chan int����ȡ��0���㲻֪�������Ƕ�ȡ����ˣ����Ǳ���ֵ����0
		* ���Բ�����һ���﷨
			var ch  = make(chan int)
			val, ok := <- ch

			* �����ȡ�ɹ���ok = true�� �����ȡʧ�ܣ�Ҳ����û�������ˣ� ok = false
		
		* �� close һ�� channel��ʱ��������������� channel �ϵ� Goroutine �����յ�֪ͨ
	
	# ��ֹChannel���ڴ�й©����
		// ��ȡ3����վ������һ����Ӧ���
		func res() string {
			ch := make(chan string, 3)
			go func() {ch <- request("https://baidu.ocm")}()
			go func() {ch <- request("https://google.ocm")}()
			go func() {ch <- request("https://springboot.io")}()
			// ��channel�е�һ�����ݺ󣬾ͻ���������
			return <- ch
		}
		func request(url string) string  {
			// TODO ����HTTP���󣬻�ȡ����Ӧ��
			return url
		}

		* �������ʹ���޻����channel����ôres�����У�����2������request�������Ϊû�������ߣ���һֱ���ڡ���channel����ֵ������
		* ��Ϊһֱ��ס�ģ��������channel��Ҳ���ᱻ���գ��ͷ������ڴ�й©
		* һ��Ҫ��֤����Ҫ gorotine��ʱ��ÿ��channel�����������˳�


	# Channel����������


	# ��goroutine�д����쳣����

	# goroutine���˳�
		
	
	# ��˲��л�������
		* ��ǰ�汾��Go�����������ܺ����ܵ�ȥ���ֺ����ö�˵����ơ�
		* ��Ȼȷʵ�����˶��goroutine�����Ҵ�����״̬����ЩgoroutineҲ���ڲ������У���ʵ����������Щgoroutine��������ͬһ��CPU�����ϣ�
		* ��һ��goroutine�õ�ʱ��Ƭִ�е�ʱ������goroutine���ᴦ�ڵȴ�״̬��
		* ��Ȼgoroutine��������д���д���Ĺ��̣���ʵ������������Ч�ʲ����������ڵ��̳߳���

		* ��Go����������Ĭ��֧�ֶ�CPU��ĳ���汾֮ǰ��������ͨ�����û�������GOMAXPROCS��ֵ������ʹ�ö��ٸ�CPU����
		* ʹ��runtime����GOMAXPROCS����
			runtime.GOMAXPROCS(15)

			* ͨ�� NumCPU() ��ȡ�ں�����
				count := runtime.NumCPU()
		
	
	# �ó�ʱ��Ƭ
		* goroutine�п��ƺ�ʱ��������ʱ��Ƭ������goroutine
		* ʹ��runtime����Gosched()����
			runtime.Gosched()
		

		* ��������goroutine�ڵ��õ������ʱ�������ȱ�������ֱ��ȫ��Ψһ��once.Do()���ý�����ż�����





