----------------------------
结构体						|
----------------------------
	# 数组允许定义可存储相同类型数据项的变量,结构是 C 编程中另一种用户自定义的可用的数据类型,它允许存储不同类型的数据项
	# 结构体的变量,不能在初始化的时候赋值
		* 结构体只是一个类型,没有定义变量前没有分配空间,没空间就不能赋值
	# 结构体的声明与定义和值的初始化
		struct Book {
			int id;
			char name[50];
			char *author;
			double price;
		};

		struct Book java,python;

		java.id = 1;	
		strcpy(java.name,"Java入门到精通");		//字符串数组可以使用copy
		java.author = "KevinBlandy";			//字符指针,可以直接赋值
		java.price = 15.6;

		printf("id=%d,name=%s,author=%s,price=%f",java.id,java.name,java.author,java.price);
		//id=1,name=Java入门到精通,author=KevinBlandy,price=15.600000
	
	# 使用大括号进行初始化也是可以的
		struct User {
			int id;
			char *name;
			char hobby[20];
		};
		struct User user = {1,"KevinBlandy"};
		printf("id=%d,name=%s,hobby=%s",user.id,user.name,"Java & Python",user.hobby);	//id=1,name=KevinBlandy,hobby=Java & Python

		* 使用大括号进行初始化的时候,可以直接对数组类型赋值,底层通过copy完成
	
	# c99和c11新的初始化方式
		struct Lang {
			int id;
			char name[10];
		};
		struct Lang java = {		//通过 .属性名 = 值 来初始化
			.id = 1,
			.name = "Java"
		};
		printf("id=%d,name=%s", java.id, java.name);	//id=1,name=Java

	# 结构作为函数参数
		* 值传递,这种方式跟普通的变量值传递是一样的
			struct Book {
				int id;
				char name[50];
				char *author;
				double price;
			};
			void func(struct Book book){
				book.id = 15;
				printf("id=%d,name=%s,author=%s,price=%f\n",book.id,book.name,book.author,book.price);
			}
			int main(){
				struct Book java;
				java.id = 1;
				strcpy(java.name,"Java入门到精通");
				java.author = "KevinBlandy";
				java.price = 15.6;

				//把结构体的值复制给函数的形参使用的,所以这种方式在函数的内部没法修改结构体的数据
				func(java);	//id=1,name=Java入门到精通,author=KevinBlandy,price=15.600000
				printf("%d",java.id);	//1

				//引用传递,这种方式,在函数内部可以修改结构体的数据
				func(&java);
				return EXIT_SUCCESS;
			}

		* 指针传递的函数也可以这样定义
			void func(struct Book *p){
				*p->id = 15;
			}
		
		* 指针传递这种方式也适用于 const 指针的规则
		* const 修饰指针变量名称,该变量只读
		* const 修饰指针,该指针指向的内存数据不能修改
	
	# 结构体的浅拷贝
		* 如果结构体有指针,下面的这种方式就会发生浅拷贝
			*to = *from;		
		
		* 结构体可以直接通过变量赋值,但是不要使用这种方式
		* 要给结构体中的成员一个一个拷贝
	
	# 指向结构的指针
		struct Book {
			int id;
		};
		int main(){
			//初始化结构体
			struct Book java;
			//初始化属性值
			java.id = 15;

			//定义一个指向结构体的指针
			struct Book *p;
			//获取结构体的地址
			p = &java;
			
			//通过 -> 操作符来通过结构体指针访问结构体属性
			printf("id=%d\n",p -> id);
	
			//也可以通过 -> 操作符来赋值
			p -> id = 255;

			//也可以通过取地址来操作结构体的变量
			(*p).id = 15;
			printf("id=%d\n",(*p).id);		//15

			return EXIT_SUCCESS;
		}

		* 结构体的指针与首元素的位置相同
			struct User {
				int id;
				char *name;
				char hobby[20];
			};
			struct User user = {1,"KevinBlandy"};
			printf("%p %p",&user,&user.id);	//0061FF14 0061FF14
	
	# 结构体内存分配原则
		* 原则一:结构体中元素按照定义顺序存放到内存中,但并'不是紧密排列'
		* 从结构体存储的首地址开始 ,每一个元素存入内存中时,它都会认为内存是以自己的宽度来划分空间的
		* 因此'元素存放的位置一定会在自己大小的整数倍上开始'
			

		* 原则二:在原则一的基础上,检查计算出的'存储单元是否为所有元素中最宽的元素长度的整数倍'
		* 若是,则结束:否则,将其'补齐'为它的整数倍

		* 计算demo
			struct Data{
				char a;			//1 -> 1个字节
				int b;			//8 -> 以为所有元素都是int,前面四个字节已经有人用了,所以从4字节后开辟内存
				char c;			//9 -> 1个字节
				double d;		//24 -> 以为所有元素都是double,前面9个字节都被人用了,所以从18字节后开辟内存
				short f;		//26 -> 2字节
			} data;

			/*
				26 % 8 != 0
				(26 + 6) % 8 == 0;
				26 + 6 = 32
			*/
			printf("d1 = %d\n",sizeof(data));		//32
		
	
	# 结构体数组
		struct User {
			int id;
			char *name;
		};

		struct User users[5] = {
				{1,"KevinBlandy"},
				{2,"Litch"},
				{3,"Rooco"},
		};

		int size = sizeof(users) / sizeof(users[0]);

		printf("size=%d\n",size);		//5

		//通过.操作
		users[0].id = 1;
		users[0].name = "KevinBlandy";
		printf("id=%d,name=%s\n",users[0].id,users[0].name);	//id=1,name=KevinBlandy

		//通过->操作
		(users+1)->id = 2;
		(users+1)->name = "Litch";
		printf("id=%d,name=%s\n",(users+1)->id,(users+1)->name);	//id=2,name=Litch

		//也可以这样操作
		(*(users + 2)).id = 3;
		(*(users + 2)).name = "Rocco";
		printf("id=%d,name=%s\n",(*(users + 2)).id,(*(users + 2)).name);	//id=3,name=Rocco
	
	# 结构体的嵌套
		* 结构体里面的成员还是一个结构体
			struct Role {
				int id;
				char *name;
			};
			struct User {
				int id;
				char *name;
				struct Role role;
			};
			
			//直接初始化结构体成员
			struct User user = {1,"Kevin",{1,"admin"}};
			printf("id=%d,role=%s",user.id,user.role.name);	//id=1,role=admin
			
			//先定义在初始化结构体成员
			struct User user = {1,"Kevin"};
			struct Role role = {1,"ADMIN"};
			user.role = role;
			printf("%s 的角色是 %s",user.name,user.role.name);//Kevin 的角色是 ADMIN

	
	# 指针指向堆空间
		struct User {
			int id;
			char *name;
		};
		struct User *p = (struct User *)malloc(sizeof(struct User));
		p -> id = 1;
		p -> name = "KevinBlandy";
		printf("id=%d,name=%s",p[0].id,(*p).name);	//id=1,name=KevinBlandy
		free(p);		//使用完成之后,记得释放
	
	# 成员指针指向堆区空间
		struct User {
			int id;
			char *name;
		};
		struct User user;
		user.name = (char *)malloc(strlen("KevinBlandy") + 1);
		strcpy(user.name, "KevinBlandy");
		printf("%s",user.name);
		free(user.name);

	# 如果结构体与结构体成员都指向了堆内存,那么释放的时候要先小后大,由里到外
		struct User {
			char *name;
		};
		//申请堆内存,存放结构体
		struct User *user = (struct User *)malloc(sizeof(struct User));
		//申请堆内存,存放结构体中的name属性值
		user -> name = malloc(sizeof("KevinBlandy") + 1);
		strcpy(user -> name,"KevinBlandy");
		printf("name=%s",user[0].name);		//name=KevinBlandy
		//先释放属性的堆内存
		free(user -> name);
		//最后释放结构体的堆内存
		free(user);
		
		* 如果先释放掉了结构体,那么 user->name,就找不到地址了,没法儿释放属性值占用的堆内存,导致内存泄漏
	
	# 结构体字面量
		struct Lang {
			int id;
			char name[10];
		};
		struct Lang lang;
		lang = (struct Lang ) { 1, "Java" };				//字面量，相当于一个匿名的结构体
		printf("id=%d,name=%s", lang.id, lang.name);		//id=1,name=Java
		return EXIT_SUCCESS;
	
	# 伸缩形数组成员
		* c99特性,利用这个特性,结构体的最后一个数组成员具备一些特性
		* 这个特性的意图不是让你声明一个结构体变量,而是声明一个指针,通过 malloc()来 分配足够的空间
		* 伸缩形数组成员的规则
			- 伸缩形数组成员必须是结构体的最后一个成员
			- 结构体中至少有一个其他的成员
			- 伸缩数组类似于普通数组,只是不声明长度
		* 合法的声明
			struct Lang {
				int id;
				char name[];		//伸缩型数组成员
			};

			struct Lang * lang;
			lang = (struct Lang *) calloc(1, sizeof(struct Lang) + 5 * sizeof(char));//为伸缩数组开辟5字节内存

			//复制四个字符串到该数组
			strncpy(lang->name, "Java", 4);

			printf("id=%d,name=%s", lang->id, lang->name);		//id=0,name=Java

----------------------------
合法的结构体定义与声明		|
----------------------------
	# 同时定义结构体以及变量
		struct Book {
			int id;
			char name[50];
			char author[50];
			double price;
		} java,python;
		
	
	# 匿名结构体
		struct {
			int id;
			char name[50];
			char author[50];
			double price;
		} java,python = {1,"KevinBlandy","Litch"};
	
	# 仅仅定义结构体
		struct Book {
			int id;
			char name[50];
			char author[50];
			double price;
		};

		* 声明该结构体的变量
			struct Book t1, t2[20],*t3;
 
	# 用typedef创建新类型
		typedef struct {
			int id;
			char name[50];
			char author[50];
			double price;
		} Book;

		* 现在可以用Book作为类型声明新的结构体变量
			Book t1, t2[20],*t3;
	


----------------------------
位域						|
----------------------------
	# 有些信息在存储时,并不需要占用一个完整的字节,而只需占几个或一个二进制位
	# 例如在存放一个开关量时,只有 0 和 1 两种状态,用 1 位二进位即可
	# 为了节省存储空间,并使处理简便,C 语言又提供了一种数据结构,称为"位域"或"位段"
	# 定义与赋值
		struct Bits {
			int a :8;				//a属性占了8bit
			unsigned int b :2;		//b属性占了2bit
			int c :6;				//c属性占了6bit
		} bits,*p;			

		bits.a = 2;			//初始化属性值

		p = &bits;			//获取地址
	
		//通过对象/指针访问值
		printf("%d %d\n",bits.a,p -> a);		//2 2
	

	# 一个位域必须存储在同一个字节中,不能跨两个字节
		* 如'一个字节所剩空间不够存放另一位域时,应从下一单元起存放该位域',也可以有意使某位域从下一单元开始
		struct Bits {
			unsigned a :4;	//一个数据存储用了4bit
			unsigned   :4;	//剩下的4bit空域
			unsigned b :4;	//从下一个单元开始存放
			unsigned c :4;
		};
	
	# 由于位域不允许跨两个字节,因此位域的长度不能大于一个字节的长度,也就是说不能超过8位二进位
		* 如果最大长度大于计算机的整数字长，一些编译器可能会允许域的内存重叠
		* 另外一些编译器可能会把大于一个域的部分存储在下一个字中

	# 位域可以是无名位域,这时它只用来作填充或调整位置,无名的位域是不能使用的
		struct k{
			int a:1;
			int  :2;    /* 该 2 位不能使用 */
			int b:3;
			int c:2;
		};

		

	# bit位数必须小于等于声明的类型
		struct Bits{
			int a:1;		//1小于等于int的位数,这个声明是合法的
		};
	
	
	# 赋值如果超出位数,可能会丢失经度
		struct {
			unsigned int age :3;
		} Age;

		Age.age = 8; // 二进制表示为 1000 有四4位,超出
		printf("Age.age : %d\n", Age.age);		//0