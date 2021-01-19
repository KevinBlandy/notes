---------------------
heap
---------------------
	# 堆

---------------------
变量
---------------------

---------------------
type
---------------------
	# type Interface interface {
			sort.Interface
			Push(x interface{}) // 添加元素到 len() 位置
			Pop() interface{}   // 从 Len() - 1 位置删除元素并且返回
		}
		
		* 它实现了 sort 包的排序接口



---------------------
func
---------------------
	func Fix(h Interface, i int)

	func Init(h Interface)

	func Pop(h Interface) interface{}
	func Push(h Interface, x interface{})
	func Remove(h Interface, i int) interface{}



---------------------
堆容器的实现
---------------------
import (
	"container/heap"
	"fmt"
)

// 自定义类型
type Value []int

// 实现 heap.Interface 接口
func (v *Value) Pop() interface{}{
	arr := *v
	lastIndex := len(arr) - 1
	retVal := arr[lastIndex]
	*v = arr[0: lastIndex]
	return retVal
}
func (v *Value) Push(val interface{}){
	*v = append(*v, val.(int))
}

// 实现 sort.Interface 接口
func (v Value) Len () int {
	return len(v)
}

// 这个方法是决定大堆还是小堆的关键
func (v Value) Less(i, j int) bool{
	return v[i] < v[j]
}
func (v Value) Swap(i, j int){
	v[i], v[j] = v[j], v[i]
}
func main(){
	h := &Value{1,5,4,7,9,3,6,2,8,0}
	fmt.Println(h)	// &[1 5 4 7 9 3 6 2 8 0]
	// 初始化
	heap.Init(h)
	// 已经按照大小堆进行排序了
	fmt.Println(h)	// &[0 1 3 2 5 4 6 7 8 9]
	// 添加元素
	heap.Push(h, 10)
	fmt.Println(h)	// &[0 1 3 2 5 4 6 7 8 9 10]
	for len(*h) > 0 {
		fmt.Print(heap.Pop(h), ", ") // 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
	}
}