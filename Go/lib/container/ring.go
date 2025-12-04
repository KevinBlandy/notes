---------------------
ring
---------------------
	# 循环链表，没有开头和结尾

---------------------
变量
---------------------

---------------------
type
---------------------
	# type Ring struct {
			Value interface{} // for use by client; untouched by this library
			// contains filtered or unexported fields
		}

		func New(n int) *Ring

		func (r *Ring) Do(f func(interface{}))
			* 把环上的每个元素，都传递给参数f调用一次

		func (r *Ring) Len() int
			* 获取长度

		func (r *Ring) Link(s *Ring) *Ring
		func (r *Ring) Move(n int) *Ring
		func (r *Ring) Next() *Ring
			* 获取下一个元素，因为是环，这个可以无限次调用

		func (r *Ring) Prev() *Ring
			* 获取上一个元素

		func (r *Ring) Unlink(n int) *Ring


---------------------
func
---------------------
	