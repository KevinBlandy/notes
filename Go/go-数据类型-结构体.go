-------------------------
�ṹ��
-------------------------
	# ���ƶ����﷨
		type [name] struct {
			[field] [type]
		}

		* name	�������ƣ�ͬһ��package�ڲ����ظ�
		* field	�ֶ����ƣ�ͬһ���ṹ���в����ظ�
		* type	�ֶ�����
	

		type User struct {
			name  string
			age int8
			skills []string
		}
		
		func main(){
			// �����������ʼ��
			var me User
			me.name = "KevinBlandy"
			me.age = 27
			me.skills = []string {"Java", "Javascript", "Python", "Go"}
			fmt.Println(me)			// {KevinBlandy 27 [Java Javascript Python Go]}
			fmt.Printf("%T\n", me)	// main.User

		}

		* ���������ʱ��û��ָ��Ĭ��ֵ�����ʹ�����͵�Ĭ��ֵ
		* �ṹ���ڲ����ֶ��ڴ棬�����������
	
	# ����ֶ�������ͬ������дһ��
		type User struct {
			name, desc  string
			age int8
			skills []string
		} 
	
	# ��ʼ������
		* map��ʽ�ĳ�ʼ��
			me := User {
				name: "Rocck",
				age: 23,
				skills: []string{"JAVA"},
			}
			fmt.Println(me)			// {Rocck 23 [JAVA]}

		* ������ʽ�ĳ�ʼ��
			user := User {
				"Vin",
				23,
				[]string{"Java", "Rubyt"},
			}
			fmt.Println(user) // {Vin 23 [Java Rubyt]}

			* ������Ҫ�������ã�һ�������٣������쳣
				user := User { "Vin", 23} // .\main.go:13:24: too few values in User literal
		

		* ֱ�Ӵ���������ʼ��
			var x = User{}	// ��ʱ�ֶ����Ͷ���Ĭ��ֵ
			fmt.Println(x)
		
		* ������ʲô��ʼ������Ҫ���ǵ����ֶ��Ƿ��з���Ȩ��

	
	# �����ṹ��
		*  �����ͽṹ��ͬʱ������ֻ��һ��

		var tmp struct {
			name string
			age int8
		}
		tmp.name = "Jonathan"
		tmp.age = 25
		fmt.Println(tmp)			// {Jonathan 25}
		fmt.Printf("%T\n", tmp)		// struct { name string; age int8 }
		
		* ������ʱ��ֱ�Ӹ�ֵ����ʼ����ʽ������������ʽ��Ҳ������Map��ʽ
		    user := struct {
				Name string
				Age  int
			}{
				"����", 20,
			}
			fmt.Println(user) 			// {���� 20}
			fmt.Printf("%T", user)		// struct { Name string; Age int }
		
	# �սṹ��
		* �������κ��ֶεĽṹ�壬���ǿսṹ�壬���Ĵ�С��0
		* �ڰ�map����set��ʱ�򣬾Ϳ���ʹ�ÿսṹ����Ϊvalue
			set := make(map[string]struct{})
			set["name"] = struct{}{}
		
	
	# �ṹ����ֵ���� 
		u1 := User{
			name: "name1",
		}

		u2 := u1
		u2.name = "name2"
		
		fmt.Println(u1.name) // name1
		fmt.Println(u2.name) // name2
	
	# �ṹ����ָ��
		* ָ�����ͨ�� new ��ȡ��Ҳ����ͨ�� & ��ȡ

		var user *User = new(User)
		user.name = "С��"
		user.age = 23
		user.skills = []string {"��", "��", "rap", "����"}
		fmt.Println(user)	// &{С�� 23 [�� �� rap ����]}
		fmt.Println(*user)	// {С�� 23 [�� �� rap ����]}

		* �ṹ��ĳ�Ա������Ҳ����ͨ��ָ�����
			type User struct {
				name string
			}
			func main(){
				var user = User{"�ܲ�"}
				var p *string = &user.name
				fmt.Println(*p)		// �ܲ�

				*p = "����"
				fmt.Println(user.name)		// ����
			}

	# �ṹ��ָ����﷨��
		// �����ṹ��
		user := User{"Kevin", 27, []string{"Java", "Python"}}

		// ��ȡ�ṹ��ָ��
		var p *User = &user;

		fmt.Printf("%p\n", p)	// 0xc0000b2330

		// ԭʼָ�����
		(*p).name = "���Ĳ�����"
		fmt.Println(user)		// {���Ĳ����� 27 [Java Python]}

		// �﷨��ָ�����
		p.name = "KevinBlandy"
		fmt.Println(user)		// {KevinBlandy 27 [Java Python]}

		* ͨ��˵�����ǽṹ��ָ�룬����ͨ��.������ֱ�Ӷ�д����
	
	# ���췽��
		* �ṹ��û�ù��췽��������ֱ��ģ��һ������
			type User struct {
				name  string
				age int8
				skills []string
			}

			func newUser(name string, age int8, skills []string) *User {
				return &User{
					name, age, skills, 
				}
			}

			func main(){
				var user *User = newUser("С��", 23, []string{"��", "��", "rap", "����"})
				fmt.Println(user) // &{С�� 23 [�� �� rap ����]}
			}

		
		* һ�㽨�鷵��ָ��
		* ��Ϊ���ص��ǽṹ�壬��ô����нṹ��Ŀ���������ṹ���ֶκܶ࣬�����Ķ��������
		* ���ص��ǽṹ��ָ�룬������п���������ֻ�Ƿ���һ���̶���С��ָ�����
	

	# ����
		* ��������OOP�е��෽��һ�����У�ֻ�ܱ������/��������
			func (type Type) name(){
			
			}
	
			* ���ĵĶ����������� ��������ǰ���������ܵ������������������
			* ʹ��ָ��Ҳ���ԣ������Լ�����������
				func (this *User) say(){
				}
			
			func (this User) say(){
				fmt.Printf("��Һã�����%s������%d��ϲ��%s", this.name, this.age, this.skills)
			}

			func main(){
				var user *User = newUser("������", 23, []string{"��", "��", "rap", "����"})
				user.say() // ��Һã����ǲ�����������23��ϲ��[�� �� rap ����]
			}

			* ˭���÷�����thisָ��˭
		
		
		* �������͵��ã����쳣
			type Foo struct {}
			func main(){
				foo := Foo{}
				foo.say() // foo.say undefined (type Foo has no field or method say)
			}
		
		
		* ֻ�ܸ��Լ����ﶨ������ͣ���ӡ������������ܸ�������������ӣ������Ҳ���У�
			// string �����Լ����ģ��쳣
			func (this string) test() string {		// .\main.go:8:6: cannot define new methods on non-local type string
				return "Hello" + this
			}
			
			// �Զ���һ��string�����ͣ��������߾ȹ�
			type myString string
			func (this myString) test()  {
				fmt.Println("Hello " + this)
			}

			func main(){
				var val myString = "World"
				val.test()  // Hello World	
			}

			* ��������Ϊ����漰��һ���������̳С��İ�ȫ���⣬������Ե��ø���ķ���
			* �������û���string����һЩΣ�յķ��������˿��ܻ������ɶ��
	
		* ����ͨ���޶��ֱ࣬�ӵ��÷���������ִ�ж��������ڷ���
			type myString string
			func (this myString) test(p string)  {
				fmt.Println(p + "Hello " + string(this) + " ")
			}
			func main(){
				// ����ʵ��
				var val myString = "World"
				// ֱ��ͨ�����͵��ã���һ����������ʵ������
				myString.test(val, "hi�� ")
			}
		
		* nilҲ��һ���Ϸ��Ľ�����
			type IntList struct {
				Val int				// ��ǰ�ڵ��ֵ
				Next *IntList		// ��һ���ڵ�
			}
			func (i *IntList) Sum() int {
				if i == nil { // this ������nil
					return 0
				}
				return i.Val + i.Next.Sum()
			}
			func main(){
				linked := IntList{1, &IntList{2, &IntList{3, &IntList{4, &IntList{5, nil}}}}}
				sum := linked.Sum()
				fmt.Println(sum)	// 15
			
				// ͨ�׵��д��
				var p *IntList = nil
				fmt.Println(p.Sum()) // 0
			}
		
		* ������Ҳ���Ե�������һ������
			type User struct {
				Name string
			}
			func (u User) Say(){
				fmt.Println(u.Name)
			}

			func main(){
				u := User{"Java"}
				u.Say()		// Java

				// ͨ��ʵ����ȡ
				var f1 func() = u.Say
				f1()			// Java

				// ͨ�����ȡ��������������ʵ�����	T.F
				var f2 func(User) = User.Say
				f2(u)				// Java

				// ͨ�����ȡ��������������ָ��	(*T).F������������Զ����ɣ�
				var f3 func(*User) = (*User).Say
				f3(&u)		// Java
			}

			* ��ȡ���������������������ʵ�壬ʹ�ã�T.F�����������������ָ��ʹ�ã�(*T).F
			* ��ͬ�ķ������ͣ������໥��ֵ
				type User struct {
					Name string
				}
				func (u *User) F1(val string) string {
					return "f1-" + u.Name + val
				}
				func (u *User) F2(val string) string {
					return "f2-" + u.Name + val
				}
				func main(){
					u := User{"Java"}
					
					// �������ͱ���
					var f func(*User, string) string

					f = (*User).F1
					fmt.Println(f(&u, " 1"))		// f1-Java 1

					f = (*User).F2
					fmt.Println(f(&u, " 2"))		// f2-Java 2
				}

	# �����ֶ�
		* ͨ�������ǣ��ṹ����ֶβ���Ҫ��������
		* �ֶε����ƾ��ǣ����͵�����

			type User struct {
				string
				int
				bool
			}
			func main(){
				user := User{"Coco", 15, true}
				fmt.Println(user)			// {Coco 15 true}
				fmt.Println(user.string)  	// Coco

				user.bool = true
				fmt.Println(user.bool)  	// true
			}
			
		* ���ڽṹ����ֶ����Ʋ������ظ������������ֶΣ����������ڽṹ��������Ψһ��
		* ���Ի�϶��壬ֻҪ�����Ʋ��ظ���OK
			type User struct {
				bar string
				int
				bool
				string
				foo int
			}
			func main(){
				user := User{"Coco", 15, true, "Ruby", 25}
				fmt.Println(user)			// {Coco 15 true Ruby 25}

				user = User{
					bar: "",
					int: 21,  // �������ֶζ���ֵ
					foo: 25,
				}
				fmt.Println(user)			// { 21 false  25}
			}

			
			// �������ظ���
			type User struct {
				string string
				string			// duplicate field string
			}
		
		* �漰������Ȩ�޵�ʱ�����˵������Ա�������ǹ����ģ���ôҲ�ǿ���ֱ�ӷ��ʵ�
			* foo ��
				package foo
				type foo struct {
					FooName string
				}
				type Bar struct {
					foo
				}
			* main��
				bar := foo.Bar{}
				bar.FooName = "Hello"
				fmt.Println(bar)
		
	# ���ԵĿɼ�������
		* ����ֶ��Ǵ�д��ͷ����ʾ���κΰ��¶����Է���
		* ����ֶ���Сд��ͷ����ʾ�������ֻ���ڵ�ǰ���²��ܷ���

	# ���Ե�Tag
		* ������Java�е�ע��
		* ���Կ������Tag��������ĳЩ�����£���������һЩ����Ĵ���ֱ�������Ժ���ʹ�� `` ����
			`[tag]:[config]`

		* ���磬һЩ��Tag
			type Foo struct {
				Name string `json:"name"`		// �����Name�ֶΣ����л���json��json�ֶ����ƽ�����name
			}
			type Foo struct {
				name string `db:"u_name"`		// ��DBӳ���ʱ�򣬰�name�ֶΣ�ӳ��Ϊ u_name ����
			}
			type Foo struct {
				Name string `db:"u_name" json:"name"`		// ����һ���Զ�������ʹ�ÿո�ָ�
			}
		
		* ����ͨ��reflect�����ȡ����Щ�ֶ��ϵ�tag
	
	# �ṹ��ıȽ�
		* ����ṹ���ȫ����Ա���ǿ��ԱȽϵģ���ô�ṹ��Ҳ�ǿ��ԱȽϵ�
			type User struct {
				Name string
			}
			func main() {
				fmt.Println(User{} == User{}) // true
			}
		
		* ����ṹ������˲��ܱȽϵ����ԣ��ͻ�����쳣
			type User struct {
				Name string
				Hobby []string
			}
			func main() {
				fmt.Println(User{} == User{}) // invalid operation: User literal == User literal (struct containing []string cannot be compared)
			}
		
		* ��ͬ���͵Ľṹ��֮�䲻�ܽ��бȽ�
		* �ֶ�������˳��Ҳ������һ���ģ����������˳��һ����Ҳ�ᱻ��Ϊ�ǲ��ɱ�
		* �ɱȽϵĽṹ�壬������Ϊmap��key
		* �ṹ�岻�ܺ�nil���бȽϣ���Ϊ��������Ϊnil������������


-------------------------
OOP
-------------------------
	# ��������ģ��ʵ�֣�ʹ�ýṹ���Ƕ��
		* ��ȷ�������ù�ϵ��������ȷ�İ��յ����﷨���з���
	
	# ����Ƕ�ף�������ֵ������ָ�룬�����Ͼ���ͨ����ϣ�ʵ���˼̳�
		type Foo struct {
			name string
		}

		type Bar struct{
			*Foo
		}
		func main() {
			b := Bar{&Foo{"Foo Name"}}
			fmt.Println(b)
			fmt.Println(b.name)		// Foo Name
		}	
	
	# ����Ƕ�׵Ľṹ�壬����ֱ�ӷ��ʵ��ڲ��ṹ������ԣ�����Ǽ̳���
			type Bar struct {
				bar string
			}
			type Foo struct {
				foo string
				Bar
			}
			type User struct {
				Foo
				name string
				val int
			}
			func main(){
				user := User{Foo{"Foo name", Bar{"Bar name"}}, "Coco", 15}
				fmt.Println(user)					// {{Foo name {Bar name}} Coco 15}
				fmt.Println(user.Foo.Bar.bar)		// Bar name
				// ֱ�ӷ����ڲ������ṹ��������ֶ�
				fmt.Println(user.foo)				// Foo name
				// ֱ�ӷ��ʶ��Ƕ��ҲOK
				fmt.Println(user.bar)				// Bar name
			}
	
	# �ṹ�����ԣ����ܰ�����ǰ����
		type User struct {  // invalid recursive type User
			Name string
			Parent User
		}

		* ָ�����
			type User struct {
				Name string
				Parent *User
			}
		
		
	# ������Ҳ���Ա��̳е��ã�Ҳ���Ա���д
		type Animal struct {
			name string
		}
		type Dog struct {
			Animal
		}
		type Cat struct {
			Animal
		}
		// �����ķ���
		func (this Animal) say(){
			fmt.Printf("Im %s\n", this.name)
		}

		// ���าд
		func (this Cat) say(){
			// ���ø���ķ���
			this.Animal.say()
			// ִ���Լ��ķ���
			fmt.Println("miao miao")
		}

		func main(){
			cat := Cat{Animal{"cat"}}
			// ������ã�����ִ�и���ķ����������ִ���Լ���
			cat.say()		// Im cat \r\n miao miao

			dog := Dog{Animal{"Dog"}}
			// �������
			dog.say()		// Im Dog
		}
	
	
	# ������ȵ�����
		* ������⣺���˵�ⲿ�ṹ���ĳ���ֶ����ƺ��ڲ��ṹ������Ƴ�ͻ����Խ���ⲿ��Խ����
			// ���� ==========================
			type Foo struct {
				name string
			}

			type User struct {
				Foo
				name string
				foo int
			}
			func main(){
				user := User{Foo{"Foo name"}, "Coco", 15}
				fmt.Println(user.Foo.name)	// Foo name

				// ͬ�����ԣ��ⲿ�ṹ���������������
				fmt.Println(user.name)		// Coco
			}

			// ���� ==========================
			type Animal struct {
				name string
			}
			type Cat struct {
				Animal
			}
			// ��д�ĺ���
			func (this Cat) say(){
				fmt.Printf("Im %s, I say: miao~\n", this.name)
			}
			// �����ĺ���
			func (this Animal) say(){
				fmt.Printf("Im %s\n", this.name)
			}
			func main(){
				cat := Cat{Animal{"cat"}}
				cat.say()		// Im cat, I say: miao~
			} 

			* ������������⣬��ʵ���Ǹ�д�����ˣ�Խ�ӽ���Ȩ��Խ��
			* ͬ���ĺ��������ǵ��÷��������Ͳ�ͬ�����Ǹ�д

	
		* ������⣺���˵������ͬ�㼶��ͬ���Ƶ������ṹ�壬���ʵ�ʱ�򣬱���ָ��Ҫ���ʵ����ͣ���Ȼ�쳣
			// ���� ==========================
			type Bar struct {
				val string
			}
			type Foo struct {
				val string
			}
			type User struct {
				Foo
				Bar
			}
			func main(){
				user := User{Foo{"foo val"}, Bar{"bar val"}}
				// ��ָ��Ҫ���ʵ����ͣ����쳣
				// fmt.Println(user.val)					//ambiguous selector user.valtln()

				// ָ��Ҫ�����ֶε�����
				fmt.Println(user.Foo.val)					// foo val
			}
			
			// ���� ==========================
			type Super1 struct {
				name string
			}
			type Super2 struct {
				name string
			}
			type Sub struct {
				Super1
				Super2
			}
			func (this Super1) foo(){
				fmt.Printf("Super1 im��%s\n", this.name)
			}
			func (this Super2) foo(){
				fmt.Printf("Super2 im��%s\n", this.name)
			}
			func main (){
				val := Sub{
					Super1{"s1"}, Super2{"s2"},
				}
				// ��ָ��Ҫ���������ͣ����쳣
				// val.foo()  // ambiguous selector val.foo
				// ָ�����ʷ���������
				val.Super1.foo();		// Super1 im��s1
				val.Super2.foo();		// Super2 im��s2
			}
		
	
	# ע�⣬struct�ķ������ֶΣ����ֲ���һ���������쳣
		type MyError struct {
			Val string
			Error error
		}

		func (this *MyError) Error () string {  // type MyError has both field and method named Error
			return "�쳣��"
		}