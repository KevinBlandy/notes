------------------------
����
------------------------
	# �����Ķ���
		func [��������]([�����б�]) ([����ֵ]) {

		}
	
		* �������Ʋ����ظ�
		* �βΣ��β����ƣ��β�������ɣ��������ʹ�ö��ŷָ�������ʡ��
		* ����ֵ������ж����ʹ��С���Ű���������ʹ�ö��ŷָ�������ʡ�ԣ���ʾ�޷���ֵ������з���ֵ������Ҫд return �ؼ���
	
		func test(name string, age int)(int, string){
			return age, name
		}
	
	# ��������ֵ����
		func sum(p1 int, p2 int)(ret1 int, ret2 int){ // ����ֵ��������
			// ��������
			ret1 = 1
			ret2 = 2
			// ֱ��дreturn������ָ��Ҫ���صı�����ָ��Ҳ�����return ret1, ret2��
			return 
		}
		
		* �൱���ں�����������һ����������� 
		* �����ں����ڲ�ʹ�ã�����ִ����Ϻ���������ͻ���Ϊ����ֵ
		* ���ڶ������ֵ��Ҫô��������Ҫô��������

		* ����ֵ������Ҳ���Ժ��Ե���ʹ�ã����ص�ʱ�򣬷�����ͬ���͵ı���Ҳ����
			func main() {
				r := test()
				fmt.Println(r)  // 1
			}
			func test() (y int) { // ����������y�����Ǻ��������������ûʹ��
				x := 1
				return x
			}
		
		* �ں������ص�ʱ��û����ȷ��ֵ�ķ���ֵ���ᱻ����ΪĬ��ֵ
			func foo() (result int, err error) {
				return 
			}
			func main() {
				result, err := foo()
				fmt.Println(result, err) // 0 <nil>
			}
		
		* �����������ֵ���ڵ�ʱ��return�����ݱ���Ҫ������������һ��
			// ����������������һ��
			func foo() (result int, err error) {
				return 1 // not enough arguments to return
			}


	# ���������������ͬʱ�����Լ�д
		func main() {
			r1, r2, r3, r4 := f1(1, 2, "3", "4")
			fmt.Println(r1, r2, r3, r4)
		}
		func f1(a, b int, c, d string) (e, f int, g , h string) {
			fmt.Println(a, b, c, d)
			e = a
			f = b
			g = c
			h = d
			return 
		}
				
		* ����֮��ʹ�ö��ŷָ������������������
		* ���ڷ��ز����б�Ҳͬ��ʹ��
	
	
	# �ɱ䳤����
		func f1(a, b int, c ...string) {
			fmt.Println(a, b)		// 1 2
			fmt.Printf("%T\n",  c)  // []string
		}

		* �䳤����ֻ�������һ����������ǰʹ������.����
		* ���ں����У�ʵ�ʻᱻ��װ��һ������Ƭ
		* �䳤�������������û��ֵ�����ǿ���Ƭ
		
		* ���õ�ʱ��Ҫʹ�� ...�⹹��ֵ��Ƭ��ֻ������Ƭ
			func foo(val ...int){
				fmt.Println(val)
			}
			
			foo([]int{1, 2, 3}...)		// ֱ��д��Ƭ
			arr := [...]int {1, 2}
			// foo(arr...) // ����������cannot use arr (type [2]int) as type []int in argument to foo
			foo(arr[:]...)		// ������ת��Ϊ��Ƭ
		
		* ����Ҳ����Ƭ��ʱ��Ҫע��ʹ�ý⹹���ʽ
			func main(){
				val := []interface {}{1, 2, 3}
				foo(val)			// [[1 2 3]]
				foo(val...)			// [1 2 3]
			}
			func foo(val ...interface{}){
				fmt.Println(val)
			}

	
	# ������
		* ȫ��������
			* �����ⲿ����ı��������к������ܷ��ʣ�����ȫ��

		* �ֲ�������
			* ����/�������ж���ı�����ֻ���ڵ�ǰ����/�������з��ʣ����Ǿֲ�
			* ����/�������в������������Ȳ��Ҿֲ����ֲ�û�ҵ������⣬һֱ�ҵ�ȫ��
	
------------------------
defer ���
------------------------
	# defer ��䣬������java�е� finally �����һ��
		* ������ defer �������䲢��������ִ�У����ǻᡰ����ӵ�һ�����С���������FILO��ջ��
		* �������� return ǰ�������������е���䣬����ִ�У����defer����䣬����ִ��
		
			// ��������
			func test(){
				fmt.Println("start")
				defer fmt.Println("1")
				defer fmt.Println("2")
				defer fmt.Println("3")
				fmt.Println("end")
			}

			// ������
			start
			end
			3
			2
			1
		
		* ���������ر���Դ
		* ������Ƚϸ��ӣ�����ʹ����������ִ�к���
			func test()  {
				fmt.Println("��ʼ")
				defer  func(){
					fmt.Println("defer1 ִ��")
				}()
				defer  func(){
					fmt.Println("defer2 ִ��")
				}()
				fmt.Println("����")
				return
			}
	
	# defer ��ִ��ʱ��
		* ��go�У������� return���ײ㲢����һ��ԭ�Ӳ��������Ƿ�Ϊ��2��
			1. ������ֵ����ֵ
			2. ִ��RETָ�����ֵ
		
		*  defer��ִ��ʱ���������ڸ�����ֵ��ֵ��RETָ��ִ��֮ǰ
	
		* ����������������Ӱ����
			func main() {
				r := test()
				fmt.Println(r) // 1
			}
			func test() int {
				val := 1	
				defer  func(){
					val ++	
				}()
				return val
			}

		* �������������ܻ�Ӱ����
			func main() {
				r := test()
				fmt.Println(r) // 2
			}
			func test() (retVal int) {
				val := 1
				defer  func(){
					retVal ++
				}()
				retVal = val
				return
			}

	# �������ؾֲ�����ָ�룬���õ��ı����ڴ汻�ͷ�
		func main(){
			fmt.Printf("%p\n", foo())	// 0xc00009c058
			fmt.Printf("%p\n", foo())	// 0xc00009c090
		}
		func foo () *int {
			retVal := 1
			return &retVal
		}

		* ��Ϊ��ָ��ָ��������������������ᱻ����
		* ���������Զ�ѡ����ջ�ϻ����ڶ��Ϸ���ֲ������Ĵ洢�ռ�
	
	# ����ֱ�ӻ�ȡ������ָ�룬���Ƿ������͵ı�������
		func main(){
			var call func(string) int = foo
			_ = call("Hello")	// Hello

			// ��ȡ������ָ��
			var p *func(string) int = &call
			fmt.Printf("%T\n", p)  // *func(string) int
			// ͨ��ָ�룬ִ�з���
			(*p)("Hi")				// Hi

			// ����ֱ�ӻ�ȡ������ָ��
			// fmt.Println(&foo)  // cannot take the address of foo
		}
		func foo (val string) int {
			fmt.Println(val)
			return 5
		}
	
	# �������ص���ֵ�����Ǳ��������ܻ�ȡ����ָ��

	
	# ���ͷ���Դ��ʱ�������Դ�ᱻ���³�ʼ����Ҫע����Դй¶����

		package main

		import "fmt"

		type Bar struct {
			Name string
		}

		func (b Bar) Close() {
			fmt.Printf("%s close\n", b.Name)
		}

		func main() {
			// ��ʼ�� b1 ��Դ
			b1 := &Bar{Name: "b1"}

			defer func(b *Bar) {
				b.Close() // b1 close
			}(b1)

			defer func() {
				b1.Close() // b2 close����Դй¶�����ַ�ʽ�رյ�������ʼ���� b2 ��Դ���ʼ�� b1 ��Դй¶
			}()

			defer b1.Close() //b1 close

			// �޸� b1 Ϊ b2
			b1 = &Bar{Name: "b2"}
		}


------------------------
��������
------------------------
	# ����Ҳ�������͵ĸ�����ĵ�Ԫ�ؾ��ǣ��β����ͣ�����ֵ����
		func func1(){
			fmt.Println("Hello")
		}
		func func2(val int){
			fmt.Println("Hello")
		}
		func func3(val ...int) []int {
			fmt.Println("Hello")
			return  val
		}
		func main() {

			f1 := func1
			var f2 func(int) = func2
			var f3 func(...int)[]int = func3

			fmt.Printf("%T\n", f1) // func()
			fmt.Printf("%T\n", f2) // func(int)
			fmt.Printf("%T\n", f3) // func(...int) []int
		}

		* ��������Ĭ��ֵΪnil��������ֻ�ܺ�nil���� == �Ƚ�
		* ������ͬ��������ͬ����ֵ�ĺ�������Ϊ��ͬ���͵ĺ���
		* ��ͬ���͵ĺ����������໥��ֵ
		* func(val ...int) �� func(val []int)��������ͬһ�����ͣ������໥��ֵ

	# ������Ϊ����
		func call(v1, v2 int, v3 string){
			fmt.Println(v1,  v2, v3)
		}
		func test(call func(int, int, string)){
			call(1, 2, "123")
		}
		func main() {
			test(call)
		}
	
	# ������Ϊ����ֵ
		* �����ڲ�����ʹ��func�ٴζ�����������
			func bar(){
				func foo(){  // unexpected foo, expecting (
				}
			}
		
		* �������غ�����ֻ�ܷ����ⲿ�Ѿ������˵������������������ڲ���������������
	
	# ����ִ�к���
		* ��������������ɺ����������ţ��ͱ�ʾֱ�ӵ���
			func main() {
				func(){
					fmt.Println("Hello")
				}()
			}
		
		* �����з���ֵ�Ͳ���
			func main() {
				retVal := func(val string) string {
					return "Hello" + val
				}("World")
				fmt.Println(retVal) // HelloWorld
			}
		

	# ��������
		* ����û���ֵĺ���������ʹ�ñ�������
		* Ȼ����Դ��ݸñ���
			var fun = func (){
				fmt.Println("Hello")
			}
			func main() {

				fun()
				
				// ����������������
				var sum func(int, int) int = func(p1, p2 int)(int){
					return p1 + p2
				}
				fmt.Println(sum(3, 5))
			}

			
	
		* �����������������ں����ڲ�����
			func main() {
				var f = func() string {
					fmt.Println("Hello")
					return "233"
				}()
				fmt.Println(f)
			}
		
		* ����һ�㶼�����ں����ڲ���


	# �հ�
		* �����ڲ�Ƕ�׺����������ֲ�����
			func outter() func() int {
				val := 0
				return func () int {
					val ++
					return val
				}
			}

			func main() {
				call := outter()
				fmt.Println(call())	// 1
				fmt.Println(call())	// 2
				fmt.Println(call())	// 3
			}

		* �ڲ�����������Ҫע��һ�������������
			var arr []func()
			for _, v := range []int {1, 2, 3} {
				// v := v û�����д��룬������ȫ����3
				arr = append(arr, func(){
					fmt.Println(v)
				})
			}
			for _, f := range arr {
				f() // ���ȫ����3
			}
		
------------------------
����
------------------------
	# ʹ��defer��ͳ�ƺ���ִ��ʱ��
		func foo() {
			defer bar()()
			// ģ���ӳ�
			time.Sleep(2 * time.Second)
		}

		func bar () func() {
			start := time.Now()
			fmt.Printf("��ʼִ�У�%v\n", start)
			return func(){
				fmt.Printf("ִ����ϣ�%v\n", time.Now())
			}
		}
		// ��ʼִ�У�2020-12-10 20:19:41.6448597 +0800 CST m=+0.007009001
		// ִ����ϣ�2020-12-10 20:19:43.6709946 +0800 CST m=+2.033143901