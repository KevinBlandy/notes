-------------------------
type �Զ�������
-------------------------
	# �Զ�������
		type [name] [underlying-type]

		* name				��������
		* underlying-type	�Ѿ����ڵ�����
		
		type myInt int
		var val myInt = 15
		fmt.Printf("%T\n", val) // main.myInt
		
		* �����ں����ڲ���ȫ�ֶ���
		* ����Ƕ��
			type myInt int
			type uInt myInt
			var val uInt = 15
			fmt.Printf("%T\n", val) // main.uInt
	

	# �Զ������ͺ��Ѿ����ڵ�����֮�䣬����ǿ��ת��
		type User struct {
			name string
		}
		type myUser User

		func (user *User) foo(){
			fmt.Println(user.name)
		}
		func main(){
			var user myUser = myUser{"Coco"}
			fmt.Printf("%T=%v\n", user, user)  								// main.myUser={Coco}
			fmt.Printf("%T=%v\n", User(user), User(user))  					// main.User={Coco}
			fmt.Printf("%T=%v\n", myUser(User(user)), myUser(User(user)))  	// main.myUser={Coco}

			// �Զ������ͣ�����ֱ��ʹ�õײ����͵ķ��������쳣
			// user.foo() // user.foo undefined (type myUser has no field or method foo)

			var u = User(user)		// Coco
			u.foo()
		}

		* �Զ������ͣ�����ֱ��ʹ�õײ����͵ķ��������쳣����Ҫ��ǿ��ת��
				
	
	# �Զ������ͣ�����ʹ����ײ�������ͬ�����㷨
		type myInt1 = int
		type myInt2 = int
		func main(){
			var i1 myInt1
			var i2 myInt2
			fmt.Println(i1 == 0)		// true
			fmt.Println(i1 == i2)		// true
			fmt.Println(i2 == 0)		// true
		}

	
	# ���ڽӿ����ʹ����� type ������ԭ�ӿ����͵ķ���������һ�µ�
		
		* �������Զ���ǽӿ����ʹ����� type ������û�С��̳С�ԭ���͵ķ������ϣ��µ� type ���͵ķ��������ǿյ�

		type Foo interface {
			M()  // �ӿ��и� M ����
		}

		type Bar struct{}

		func (b Bar) M() {}// struct �и� M ����

		type MyFoo Foo // �ӿڵ� Type ����
		type MyBar Bar // struct �� Type ����

		func main() {
			var v1 MyFoo
			v1.M() // �ɵ���

			var v2 MyBar
			v2.M() // �쳣���ǽӿڵ� Type ���ͣ�����̳����ķ���
		}


-------------------------
type ���ͱ���
-------------------------
	# �������ͱ���
		type [alias name]=[name]

		* alias name	����
		* name			�Ѿ����ڵ�����
	
		type myInt = int
		var val myInt = 5
		fmt.Printf("%T\n", val) // int
		
		* �����ں����ڲ���ȫ�ֶ���
		* ����Ƕ��
			type myInt = int
			type uInt = myInt
			var val uInt = 5
			fmt.Printf("%T\n", val) // int
		
	
	# runne/byte ��ʵ���Ǳ���
		var v rune = '1'
		fmt.Printf("%T", v)	// int32

		var v byte = 255
		fmt.Printf("%T", v)	// uint8
	
	
	# �������ڱ�д����, ��ߴ���ɶ���
		* ����󣬸���ʲô���ͣ�����ʲô����
	
	
	# ���ͱ�����ԭ����ӵ����ȫ��ͬ�ķ������ϣ�����ԭ�����ǽӿ����ͻ��Ƿǽӿ����͡�
