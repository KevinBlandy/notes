------------------------
errors
------------------------


------------------------
var
------------------------

------------------------
type
------------------------


------------------------
func
------------------------
    func As(err error, target interface{}) bool
		* target ������ָ�룬����Ϊnil�����ұ�����ʵ����error�Ľӿ�
		* �ж� err �ǲ�������target����
			// �쳣ʵ��
			var err = &http.MaxBytesError{}
			// �쳣����ָ��
			var errType *http.MaxBytesError
			// ���
			fmt.Println(errors.As(err, &errType))

    func Is(err, target error) bool
		* ���� targer ���쳣���У��Ƿ���err����
		* ���targetʵ���� Is ��������ͨ������ӿ��жϣ����targetʵ����Unwrap�������᲻�ϰ������װ���쳣���бȽ�

		* �ж�err�ǲ��ǵ��� target


    func New(text string) error
		* ����text����һ���쳣

    func Unwrap(err error) error
		* ����쳣��һ����װ�쳣��Ҳ����ʵ����: Unwrap() error 
		* ��ô�ͻ᷵�ذ�װ���쳣�����򷵻�nil
	
	func Join(errs ...error) error
		

------------------------
demo
------------------------
	# As��ʹ��
		type MyError struct {
			Message string
			Err error
		}

		func (m MyError) Error() string{
			return m.Message
		}
		func (m MyError) Unwrap() error {
			return m.Err
		}

		func main(){
			err1 := errors.New("error1")
			err2 := fmt.Errorf("catch err [%w]", &MyError{  // ���ﴴ������ MyError��ָ�룬���Դ�������
				Message: "����MyError",
				Err:     err1,
			})
			err3 := fmt.Errorf("catch err [%w]", err2)
			var x *MyError  // ������������MyError��ָ�룬������������
			fmt.Println(errors.As(err3, &x)) // ���ݸ�As����ָ���ָ�룬���Դ��ݶ����ָ��
		}


