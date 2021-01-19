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
		* target 不能为nil，并且必须是实现了error的接口

    func Is(err, target error) bool
		* 返回 targer 的异常链中，是否有err错误
		* 如果target实现了 Is 方法，会通过这个接口判断，如果target实现了Unwrap方法，会不断剥离出包装的异常进行比较

    func New(text string) error
		* 根据text返回一个异常

    func Unwrap(err error) error
		* 如果异常是一个包装异常，也就是实现了: Unwrap() error 
		* 那么就会返回包装的异常，否则返回nil
