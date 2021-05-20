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
		* target 必须是指针，不能为nil，并且必须是实现了error的接口
		* 判断 err 是不是属于target类型

    func Is(err, target error) bool
		* 返回 targer 的异常链中，是否有err错误
		* 如果target实现了 Is 方法，会通过这个接口判断，如果target实现了Unwrap方法，会不断剥离出包装的异常进行比较

		* 判断err是不是等于 target


    func New(text string) error
		* 根据text返回一个异常

    func Unwrap(err error) error
		* 如果异常是一个包装异常，也就是实现了: Unwrap() error 
		* 那么就会返回包装的异常，否则返回nil

------------------------
demo
------------------------
	# As的使用
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
			err2 := fmt.Errorf("catch err [%w]", &MyError{  // 这里创建的是 MyError的指针，可以创建对象
				Message: "我是MyError",
				Err:     err1,
			})
			err3 := fmt.Errorf("catch err [%w]", err2)
			var x *MyError  // 这里声明的是MyError的指针，可以声明对象
			fmt.Println(errors.As(err3, &x)) // 传递给As的是指针的指针，可以传递对象的指针
		}


