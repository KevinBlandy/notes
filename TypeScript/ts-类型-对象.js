-----------------------
对象
-----------------------
	# 使用大括号表示对象类型，在大括号内部声明每个属性和方法的类型。
		type OBJ_TYPE = {name: string, age: number}

		let obj: OBJ_TYPE = {
			name: 'kk', age: 25
		}
	
		
		* 属性的类型可以用分号结尾，也可以用逗号结尾。
			type OBJ_TYPE = {
				name: string;       // 分号也可以
				age: number,        // 逗号也行
			}
		
		* 最后一行后面的符号是可以省略的
		* 类型声明了后，对象赋值时，属性不能多，也不能少
	
		* 读、写、类型定义中不存在的属性会异常，删除类型中存在的属性也会异常
	
	# 对象中的方法使用函数的类型描述方法
		type OBJ_TYPE = {
			name: string;
			age: number;
			// 方法的类型
			say(preffix: string): string;
			
			// 使用下面这种方式也可以
			say: (preffix: string) => string;
		}

		let obj: OBJ_TYPE = {
			name: 'KK',
			age: 24,
			say(preffix) {
				return `${preffix} - ${this.name}/${this.age}`
			},
		}

		console.log(obj.say('[]')); // [] - KK/24
	

	# 对象类型可以使用方括号读取属性的类型
		// name 字段的类型
		type NAME_TYPE = OBJ_TYPE['name']  // 这里的字段名称是用的字符串
	

	# 还可以使用 interface 命令 把对象类型提炼为一个接口
		interface OBJ_TYPE {
			name: string;
			age: number;
		}
	

	# TS 不区分对象自身的属性和继承的属性，一律视为对象的属性。
		type OBJ_TYPE = {
			name: string;
			toString(): string,
		}


		// 未定义 toStrig 方法，因为已经从 Object 类继承了
		let obj: OBJ_TYPE = {name: ''}
	
	# 可选属性
		* 使用 ? 标识可选属性
			const obj: {
			  x: number;
			  y?: number;		// y 是可选的
			} = { x: 1 };		// 不声明 y，没问题
		
		* 可选属性等同于允许赋值为 undefined，即可选属性的默认值

			lastName?: string;

			// 等同于
			lastName: string | undefined;
		
		* 注意，可选属性与允许设为 undefined 的必选属性是不等价的。

			type A = { x:number, y?:number };
			type B = { x:number, y:number|undefined }; // y 是必须的属性，只是说值可以为 undefined，必须出现在对象中

			const ObjA:A = { x: 1 }; // 正确
			const ObjB:B = { x: 1 }; // 报错
		
		* 在操作可选属性之前，最好先进行 undefined 判断
	
		* TypeScript 提供编译配置项 ExactOptionalPropertyTypes
			* 只要同时打开这个设置和 strictNullChecks，可选属性就不能设为 undefined。
	
	# 只读属性
		* 属性名前面加上 readonly 关键字，表示这个属性是只读属性，初始化后就不能修改。
		

			type OBJ_TYPE = {
				// 注意，加在了属性名称的前面
				readonly name: string;
			}
			let obj: OBJ_TYPE = {name: 'const'}
			obj.name = '213';  // 无法为“name”赋值，因为它是只读属性
		
		* 还可以在对象后面加上只读断言 as const
			
			// 对象中的所有属性都不能修改
			const myObj = {
				title: 'MyTitle'
			} as const;
		
			* as const 属于 TypeScript 的类型推断，如果变量明确地声明了类型，那么 TypeScript 会以声明的类型为准。

				const myUser:{ name: string } = {
				  name: "Sabrina",
				} as const;

				myUser.name = "Cynthia"; // Ok，声明的类型中属性不是 readonly 的

	# 属性名的索引类型
		* 如果对象的属性非常多，或者是 API 接口返回来的对象，你没法为其属性一一定义类型

		* 可以使用 “属性名的索引类型” property 为对象定义属性类型
	
			type FOO_OBJ = {
				// 只要对象的 key 是字符串，value 是字符串就 OK
				[property: string]: string;

				// 只要对象的 key 是 symbol，value 是任意类型就 Ok
				[property: symbol]: any;

				// 不能重复定义
				// [property: string]: string; 错误
			}

			let obj: FOO_OBJ = {
				name: '',                   // 合法
				[Symbol.for('foo')]: []     // 合法
				age: 123,                   // 非法，没有 [property: string]: number 的类型声明
			}
		
		* 对象的属性名除了 string/symbol 还有 number

			type INT_ARRAY = {
				[property: number]: number
			}
			
			// 数组
			let arr: INT_ARRAY = [0, 2, 3, 4]           // OK
			// 对象
			let obj: INT_ARRAY = {0: 1, 1: 2, 2: 3}     // Ok
			
			// 不宜用来声明数组，因为采用这种方式声明数组，就不能使用各种数组方法以及length属性，因为类型里面没有定义这些东西。
		
		* 对象可以同时有多种类型的属性名索引，但是不能冲突

			* 数值索引不能与字符串索引发生冲突

				type MyType = {
				  // 在 JavaScript 语言内部，所有的数值属性名都会自动转为字符串属性名。
				  [x: number]: boolean; // 报错，boolean 类型的值和 string 类型的值冲突了
				  [x: string]: string;
				}
		
		* 可以声明属性名索引，也声明具体的单个属性名，一样是不能冲突的
			type MyType = {
			  // foo 其实也是字符串
			  foo: boolean; // 报错，boolean 类型的值和 string 类型的值冲突了
			  [x: string]: string;
			}
		

		* 尽量别用
	
	# 解构赋值
		* 解构赋值的类型写法，跟为对象声明类型是一样的
			const {title, site, size}: {title: string, site: string, size: number} 
				=
			{title: 'Hi', site: 'hi.com', size: 12}
		

		* 目前没法为解构变量指定类型，因为对象解构里面的冒号，JavaScript 指定了其他用途。

			let { x: foo, y: bar } = obj;

			// 等同于
			let foo = obj.x;
			let bar = obj.y;
		
			// 如果要为x和y指定类型，不得不写成下面这样。
			let { x: foo, y: bar }: { x: string; y: number } = obj;
		
		* 要千万注意，目前没法给解构变量设置类型，不要理解错了

			// 这里的 string 并不是类型，而是解构赋值中的变量名称
			// 把参数中的 sub 属性赋值给变量 string
			function foo ({sub: string}){
				// console.log(sub);       // ReferenceError: sub is not defined
				console.log(string);
			}

			foo ({sub: {string: 'Hi'}});  // {string: 'Hi'}

	# 结构类型原则
		* 结构类型”原则（structural typing）：只要对象 B 满足 对象 A 的结构特征，TypeScript 就认为对象 B 兼容对象 A 的类型
			type A = {
			  x: number;		// 有属性值类型为 number 的 x 属性
			};

			type B = {
			  x: number;		// 有属性值类型为 number 的 x 属性
			  y: number;
			};
			
			// 对象 B，兼容对象A
			// 只要可以使用A的地方，就可以使用B。
		
		* TS 检查某个值是否符合指定类型时，并不是检查这个值的类型名
		* 而是检查这个值的结构是否符合要求（即“结构类型”）。
			
			const val = {
				x: 1,			// 有属性值类型为 number 的 x 属性
				y: 'Hi',
			};
			
			// 符合解构，Ok
			let obj: {x: number} = val;
		
		* 凡是可以使用父类型的地方，都可以使用子类型，即子类型兼容父类型。

	
	# 严格字面量检查
		* 如果对象使用字面量表示，就会触发严格字面量检查（strict object literal checking）。
		* 也就是说字面量的结构跟类型定义的不一样（比如多出了/少了未定义的属性），就会报错。

			let obj: {x: number} = {
				x: 1,
				y: 'Hi',        // 错误，多了 y属性
			};
		
		* 如果等号右边不是字面量，而是一个变量，根据结构类型原则，上面的代码是不会报错的。
			// 变量
			const val = {
				x: 1,
				y: 'Hi',
			};
			
			let obj: {x: number} = val;
		
		* 可以使用类型断言规避严格字面量检查

			type OBJ_TYPE = {
				x: number;
				y: string;
			}

			const obj: OBJ_TYPE = {
				x: 1		
				// 缺少 y 属性
			} as OBJ_TYPE  // 告诉编译器，字面量符合 OBJ_TYPE 类型，就能规避这条规则。
		
		* 编译选项 suppressExcessPropertyErrors 可以关闭多余属性检查。

			{
			  "compilerOptions": {
				"suppressExcessPropertyErrors": true
			  }
			}

	# 最小可选属性规则
		* 根据“结构类型”原则，如果一个对象的所有属性都是可选的，那么其他对象跟它都是结构类似的。
			// Options的所有属性都是可选的，所以它可以是一个空对象
			type Options = {
				a?:number;
				b?:number;
				c?:number;
			};

			const obj: Options = {}	// OK

		* TS 2.4 引入了一个 “最小可选属性规则”，也称为“弱类型检测”（weak type detection）。
		* 也就是说，如果某个类型的所有属性都是可选的，那么该类型的对象必须至少存在一个可选属性，不能所有可选属性都不存在。

			const opts = { d: 123 };

			// 对象 opts与 类型 Options 没有共同属性，赋值给该类型的变量就会报错。
			const obj:Options = opts; // 报错，类型“{ d: number; }”与类型“Options”不具有相同的属性
		
		* 如果想规避这条规则，要么在类型里面增加一条索引属性（[propName: string]: someType），要么使用类型断言（opts as Options）。
	
	# 空对象
		* 空对象是 TS 的一种特殊值，也是一种特殊类型。

			const obj = {};
			obj.prop = 123; // 报错

			// TS 会推断变量 obj 的类型为空对象，实际执行的是下面的代码。
			const obj:{} = {};
		
		* 空对象没有自定义属性，所以对自定义属性赋值就会报错。
		* 空对象只能使用继承的属性，即继承自原型对象 Object.prototype 的属性。

		* 所以，JS 中常用的先声明空对象，再动态给属性的方法不能在 TS 中用了

		* 空对象作为类型，其实是 Object 类型的简写形式，除了 null 和 undefined 都可以赋值给空对象类型
			let d:{};
			// 等同于
			// let d:Object;

			d = {};
			d = { x: 1 };
			d = 'hello';
			d = 2;
		



		


