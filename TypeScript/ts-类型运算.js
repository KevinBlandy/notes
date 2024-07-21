-----------------------------
TS 类型运算
-----------------------------
	# keyof 
		* 单目运算符，接受一个对象类型作为参数，返回该对象的所有 KEY 名组成的联合类型。
		
			type Obj = {
				name: string
				age: number
			}

			// // 'foo'|'bar'
			type ObkType = keyof Obj
		
		* 由于 JavaScript 对象的 KEY 名只有三种类型，所以对于任意对象的 KEY 名的联合类型就是 string | number | symbol

			// string | number | symbol
			type KeyT = keyof any;
	
		* 没有自定义键名的类型使用 keyof 运算符，返回never类型
			type KeyT = keyof object;  // never

			// object类型没有自身的属性，也就没有 KEY 名，所以keyof object返回never类型。
		
		* 如果对象属性名采用索引形式，keyof 会返回属性名的索引类型。
			// 示例一
			interface T {
				// 属性索引
			  [prop: number]: number;
			}

			// number
			type KeyT = keyof T;

			// 示例二
			interface T {
			  [prop: string]: number;
			}

			// string | number
			type KeyT = keyof T

			// keyof T 返回的类型是string|number，原因是 JavaScript 属性名为字符串时，包含了属性名为数值的情况，因为数值属性名会自动转为字符串
		
		* keyof 运算符用于数组或元组类型，会返回数组的所有键名，包括数字键名和继承的键名。
			
			type Result = keyof ['a', 'b', 'c'];
			// 返回 number | "0" | "1" | "2"
			// | "length" | "pop" | "push" | ···
		
		* 对于联合类型，keyof 返回成员共有的键名，交集。
			type A = { a: string; z: boolean };
			type B = { b: string; z: boolean };

			// 返回 'z'
			type KeyT = keyof (A | B);
		
		* 对于交叉类型，keyof 返回所有键名，并集
			type A = { a: string; x: boolean };
			type B = { b: string; y: number };

			// 返回 'a' | 'x' | 'b' | 'y'
			type KeyT = keyof (A & B);

			// 相当于
			keyof (A & B) ≡ keyof A | keyof B
		
		* keyof 取出的是键名组成的联合类型，如果想取出键值组成的联合类型，可以像下面这样写。
			type MyObj = {
			  foo: number,
			  bar: string,
			};
			
			//取出的是 KEY 名称组成的联合类型
			type Keys = keyof MyObj;

			// 取出键值组成的联合类型
			type Values = MyObj[Keys]; // number|string
								

								
		* keyof 运算符往往用于精确表达对象的属性类型
			
			// K extends keyof Obj表示K是Obj的一个属性名，传入其他字符串会报错。返回值类型Obj[K]就表示K这个属性值的类型。
			function prop<Obj, K extends keyof Obj>(
				obj: Obj, 
				key: K
			): Obj[K] {
				return obj[key];
			}
			// prop 有 2 个泛型
			// Obj 是 any 类型
			// K 是 Obj 的 Key 的类型之一（或者子类）
			// 返回的类型 Obj 的 K 的类型
		
		
		* 还可以用于属性映射，即将一个类型的所有属性逐一映射成其他值。
			type NewProps <Obj> = {
				[Prop in keyof Obj]: boolean;
			};

			// 用法
			type MyObj = { foo: number; };

			// 等于 { foo: boolean; }
			type NewObj = NewProps<MyObj>;
		
			
			// 类型 NewProps 是类型 Obj 的映射类型，前者继承了后者的所有属性，但是把所有属性值类型都改成了boolean。
		
	# in 运算符
		*  TS 中 in 运算符，用来取出（遍历）联合类型的每一个成员类型。

			type U = 'a' | 'b' | 'c';

			type Foo = {
				[Prop in U]: number;
			};

			// 等同于
			type Foo = {
				a: number,
				b: number,
				c: number
			};

			// [Prop in U]表示依次取出联合类型U的每一个成员。
	
	# 方括号运算符
		* 方括号运算符（[]）用于取出对象的键值类型
			* 比如 T[K] 会返回对象 T 的属性 K 的类型。

			type Person = {
				age: number;
				name: string;
				alive: boolean;
			};

			// Age 的类型是 number
			type Age = Person['age'];
		
			// Person['age'] 返回属性age的类型，本例是number。
		
		* 方括号的参数如果是联合类型，那么返回的也是联合类型。
			// number|string
			type T = Person['age'|'name'];

			// number|string|boolean
			type A = Person[keyof Person];
		
		* 如果属性不存在，会报错
		* 括号参数也可以是属性名的索引类型。

			type Obj = {
			  [key:string]: number,
			};

			// number
			type T = Obj[string];

			// Obj 的属性名是字符串的索引类型，所以可以写成Obj[string]，代表所有字符串属性名，返回的就是它们的类型number。
		

		* 对于数组也适用，可以使用 number 作为方括号的参数。
			// MyArray 的类型是 { [key:number]: string }
			const MyArray = ['a', 'b', 'c'];

			// 等同于 (typeof MyArray)[number]
			// 返回 string
			type Person = typeof MyArray[number];
		
			
			// typeof MyArray[number] 的typeof运算优先级高于方括号，所以返回的是所有数值键名的键值类型string。
		
		* 方括号里面不能有值的运算。
	
	# extends...?: 条件运算符
		* 类似 JS 的 ?: 运算符这样的三元运算符，但多出了一个 extends 关键字。
		* 根据当前类型是否符合某种条件，返回不同的类型。
			T extends U ? X : Y
		
			// extends 用来判断，类型 T 是否可以赋值给类型U，即 T 是否为 U 的子类型，这里的 T 和 U 可以是任意类型。
		
			// 如果 T 能够赋值给类型 U，表达式的结果为类型 X，否则结果为类型 Y。
		
			// true
			type T = 1 extends number ? true : false;
			
	
		* 如果判断的类型是联合类型，条件运算符会展开它

			(A|B) extends U ? X : Y

			// 等同于
			(A extends U ? X : Y) | (B extends U ? X : Y)

			// 相当于A和B分别进行运算符，返回结果组成一个联合类型。

		* 如果不希望联合类型被条件运算符展开，可以把extends两侧的操作数都放在方括号里面。
			// 示例一
			// ToArray<Type>的类型参数是一个联合类型，所以会被展开，返回的也是联合类型
			type ToArray<Type> = Type extends any ? Type[] : never;

			// string[]|number[]
			type T = ToArray<string | number>;

			// 示例二
			// extends 两侧的运算数都放在方括号里面，所以传入的联合类型不会展开，返回的是一个数组。
			type ToArray<Type> =
				[Type] extends [any] ? Type[] : never;

			// (string | number)[]
			type T = ToArray<string | number>;
		

		* 条件运算符还可以嵌套使用。

			type LiteralTypeName<T> =
				T extends undefined ? "undefined" :
				T extends null ? "null" :
				T extends boolean ? "boolean" :
				T extends number ? "number" :
				T extends bigint ? "bigint" :
				T extends string ? "string" :
				 never;
				
		
			// 用法
			// "bigint"
			type Result1 = LiteralTypeName<123n>;

			// "string" | "number" | "boolean"
			type Result2 = LiteralTypeName<true | 1 | 'a'>;
		
	
	# infer 关键字
		* 用来定义泛型里面推断出来的类型参数，而不是外部传入的类型参数。
		* 通常跟条件运算符一起使用，用在extends关键字后面的父类型之中。

			type Flatten<Type> = Type extends Array<infer Item> ? Item : Type;

			// infer Item 表示 Item 这个参数是 TypeScript 自己推断出来的，不用显式传入
			// 而 Flatten<Type> 则表示Type这个类型参数是外部传入的。
	
	# is 运算符
		* 函数返回布尔值的时候，可以使用is运算符，限定返回值与参数之间的关系。
	
		* is运算符用来描述返回值属于true还是false。
			
			function isFish(
				pet: Fish | Bird
			): pet is Fish {
				return (pet as Fish).swim !== undefined;
			}
		
			// isFish()的返回值类型为pet is Fish，表示如果参数pet类型为Fish，则返回true，否则返回false。
	
	# 模板字符串
		* 可以使用模板字符串，构建类型。

			type World = "world";
			// "hello world"
			type Greeting = `hello ${World}`;
		* 模板字符串可以引用的类型一共7种，分别是 string、number、bigint、boolean、null、undefined、Enum。引用这7种以外的类型会报错。

	
		* 模板字符串里面引用的类型，如果是一个联合类型，那么它返回的也是一个联合类型，即模板字符串可以展开联合类型。

			type T = 'A'|'B';

			// "A_id"|"B_id"
			type U = `${T}_id`;
		
		* 如果模板字符串引用两个联合类型，它会交叉展开这两个类型。
			
			type T = 'A'|'B';

			type U = '1'|'2';

			// 'A1'|'A2'|'B1'|'B2'
			type V = `${T}${U}`;
		
			
			// T和U都是联合类型，各自有两个成员，模板字符串里面引用了这两个类型，最后得到的就是一个4个成员的联合类型。
		
		* satisfies 运算符
			* TS 4.9 添加，用来检测某个值是否符合指定类型
			* 有时候，不方便将某个值指定为某种类型，但是希望这个值符合类型条件，这时候就可以用satisfies运算符对其进行检测。
