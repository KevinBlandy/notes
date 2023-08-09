-------------------------
�쳣����
-------------------------
	# Go������У������еĴ��󣬶�����ֵ�����д���Ŀǰ(V1.12)û���쳣�������

	#һ��ʹ��panic/recover ģʽ�������쳣
		func panic(v interface{})
			* �����׳��쳣���������κεط�����
			* �쳣�׳��󣬳��򲻻�����ִ��
				func main() {
					fmt.Println("start")
					panic("�쳣��")
					// ����Ĵ��벻��ִ��
				}
				// -----------
				start
				panic: �쳣��

				goroutine 1 [running]:
				main.main()
					d:/golang/main
			

		
		func recover() interface{}
			* ��������������Ի�ȡpanic�׳����쳣��Ϣ
			* ֻ���� defer ���õĺ�������Ч������Ҫ�����ڿ������� panic �����֮ǰ
			
				func main() {
					fmt.Println("start")
					defer func(){
						err := recover()
						if err != nil {
							fmt.Println(err)
							fmt.Printf("%T\n", err)
						}
					}()
					panic("�쳣��")
					// ����Ĵ��벻��ִ��
				}
				// -------------
				start
				�쳣��
				string
			
			* ���������޸��쳣�ķ���ֵ
				func foo() (err error) {
					defer func (){
						if p := recover(); p != nil{
							// ��װ�쳣��Ϣ������ֵ
							err = fmt.Errorf("�쳣�ˣ�%v", p)
						}
					}()
					// ����ִ�п��ܻ��쳣�Ĵ���
				}

			* һ��panic��Ӧһ��recover
			* һ��panicֻ�ᱻ�Լ����������һ��recover����
			* ��Ƕ�׵�defer�����е���recoverҲ�������޷������쳣
				func main() {
					defer func() {
						defer func() {
							// �޷������쳣
							if r := recover(); r != nil {
								fmt.Println(r)
							}
						}()
					}()
					panic(1)
				}
			* defer�е��õ���recover�����İ�װ�����Ļ����쳣�Ĳ�������ʧ��
				func main() {
					defer func() {
						// �޷������쳣
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
			* ����Ҫ�����쳣��ջֻ֡��һ��ջ֡��recover�����������������쳣
			* Ҳ������defer�м���panic����ô���panicҲ��ֻ�ᱻ�Լ����������һ��recover����
	
	# ���������е��쳣���ܱ� recover()
		* ����� runtime.panic() �׳����쳣���Ա� recover()
		* ����� runtime.throw() / runtime.fatal() �׳����쳣�����ܱ� recover() ����
	
	# �����panic�� nil������� recover ���յ� PanicNilError 
		type PanicNilError struct {
		 _ [0]*PanicNilError
		}

		func (*PanicNilError) Error() string { return "panic called with nil argument" }
		func (*PanicNilError) RuntimeError() {}
	

-------------------------
error �ӿ�
-------------------------
	# Go�����ж����Error�ӿ�
		type error interface {
			Error() string
		}

	# ϵͳ��errors��Ԥ������һЩ�쳣��صķ���
		func New(text string) error 
			* ��������ϵͳ�ṩ��һ��errorʵ��
				type errorString struct {
					s string
				}
				func (e *errorString) Error() string {
					return e.s
				}

		func As(err error, target interface{}) bool
			* target ����Ϊnil�����ұ�����ʵ����error�Ľӿ�

		func Is(err, target error) bool
			* ���� targer ���쳣���У��Ƿ���err����
			* ���targetʵ���� Is ��������ͨ������ӿ��жϣ����targetʵ����Unwrap�������᲻�ϰ������װ���쳣���бȽ�

		func Unwrap(err error) error
			* ����쳣��һ����װ�쳣��Ҳ����ʵ����: Unwrap() error 
			* ��ô�ͻ᷵�ذ�װ���쳣�����򷵻�nil

	
	# ͨ�� fmt��Errorf��ʽ��һ��error����
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


	# ����ʱ�쳣�������� rumtime ��
		type Error interface {
			error
			RuntimeError()
		}
	
	# ��װ�쳣
		// ԭʼ�쳣
		e1 := errors.New("����ԭʼ�쳣")
		// ͨ�� %w ���쳣���а�װ
		we := fmt.Errorf("���ǰ�װ�쳣���Ҳ�����: %w", e1)
		fmt.Println(we.Error())		// ���ǰ�װ�쳣���Ҳ�����:����ԭʼ�쳣

		r := errors.Unwrap(we)		// ����ԭʼ�쳣
		fmt.Println(r == e1)		// true

		fmt.Println(errors.Is(we, e1))	// true

		fmt.Println(errors.As(we, e1))	// panic: errors: *target must be interface or implement error ???


	# ��������if else ����
		* �����Ĵ���
			func parse(r io.Reader) (*Point, error) {

				var p Point

				if err := binary.Read(r, binary.BigEndian, &p.Longitude); err != nil {
					return nil, err
				}
				if err := binary.Read(r, binary.BigEndian, &p.Latitude); err != nil {
					return nil, err
				}
				if err := binary.Read(r, binary.BigEndian, &p.Distance); err != nil {
					return nil, err
				}
				if err := binary.Read(r, binary.BigEndian, &p.ElevationGain); err != nil {
					return nil, err
				}
				if err := binary.Read(r, binary.BigEndian, &p.ElevationLoss); err != nil {
					return nil, err
				}
			}
		
		* �ú���ʽ��̵ķ�ʽ
			func parse(r io.Reader) (*Point, error) {
				var p Point
				var err error
				read := func(data interface{}) {
					if err != nil {
						return
					}
					err = binary.Read(r, binary.BigEndian, data)
				}

				read(&p.Longitude)
				read(&p.Latitude)
				read(&p.Distance)
				read(&p.ElevationGain)
				read(&p.ElevationLoss)

				if err != nil {
					return &p, err
				}
				return &p, nil
			}
		
		* �ýṹ��ķ�ʽ
			type Reader struct {
				r   io.Reader
				err error
			}

			func (r *Reader) read(data interface{}) {
				if r.err == nil {
					r.err = binary.Read(r.r, binary.BigEndian, data)
				}
			}
			
			func parse(input io.Reader) (*Point, error) {
				var p Point
				r := Reader{r: input}

				r.read(&p.Longitude)
				r.read(&p.Latitude)
				r.read(&p.Distance)
				r.read(&p.ElevationGain)
				r.read(&p.ElevationLoss)

				if r.err != nil {
					return nil, r.err
				}

				return &p, nil
			}

			* buffio.Scanner �ײ�Ҳ���������
				scanner := bufio.NewScanner(input)

				for scanner.Scan() {
					token := scanner.Text()
					// process token
				}

				if err := scanner.Err(); err != nil {
					// process the error
				}