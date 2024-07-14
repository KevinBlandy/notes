-------------------------
declare
-------------------------
	# declare 关键字用来告诉编译器，某个类型是存在的，可以在当前文件中使用。
		* 只能用来描述已经存在的变量和数据结构，不能用来声明新的变量和数据结构。
		* 所有 declare 语句都不会出现在编译后的文件里面。
	
	# declare 关键字可以描述以下类型。
		* 变量（const、let、var 命令声明）
		* type 或者 interface 命令声明的类型
		* class
		* enum
		* 函数（function）
		* 模块（module）
		* 命名空间（namespace）
	
	# declare 变量
		* 例如，当前脚本使用了其他脚本定义的全局变量 x
			x = 123; // 报错

			// 因为 x 是其他脚本定义的，当前脚本不知道它的类型，编译器就会报错
		
			* 在当前脚本声明变量 x 的类型

				declare let x: number;

				// Ok
				x = 515;
	
			* 如果没有给 declare 变量指定类型的话，默认为 any
	
		* 例如，声明 document 全局变量
			
			// 告诉编译器，变量document的类型是外部定义的
			declare const document: Document

			const node = document.getRootNode();

			console.log(node);
		
		* declare 关键字只用来给出类型描述，不能涉及到值
			// 报错
			declare let x:number = 1;
	
	# declare 函数
		* declare 关键字可以给出外部函数的类型描述。
			
			// 个函数是由外部文件定义的，因此这里可以直接使用该函数。
			declare function sayHello(
				name: string
			): void;

			sayHello('张三');
		
		* declare 关键字后面也不能带有函数的具体实现。
	
	# declare 类
		* 类的定义如下		

			declare class Animal {
				constructor(name: string);
				eat(): void;
				sleep(): void;
			}
		
		* 一个复杂一点的例子。
			declare class C {
				// 静态成员
				public static s0(): string;
				private static s1: string;

				// 属性
				public a: number;
				private b: number;

				// 构造函数
				constructor(arg: number);

				// 方法
				m(x: number, y: number): number;

				// 存取器
				get c(): number;
				set c(value: number);

				// 索引签名
				[index: string]: any;
			}
		
		* 同样地， declare 类 不能有具体的实现
	
	# declare 模块
		* 可以将 declare 与 module 一起使用，把变量、函数、类组织在一起

			// 声明 AnimalLib 模块中的类型
			declare module AnimalLib {
				class Animal {
					constructor(name: string);
					eat(): void;
					sleep(): void;
				}

				type Animals = 'Fish' | 'Dog';
			}
		
		* 加不加 export 关键字都可以。
			declare module 'io' {
				// 暴露函数的类型
				export function readFile(filename:string):string;
			}
		
		* 示例
			// 声明 myLib 模块下的类型定义
			declare module myLib {
			  function makeGreeting(s:string): string;
			  let numberOfGreetings: number;
			}

			// 在其他文件中导入 myLib 使用
			let result = myLib.makeGreeting('你好');

			console.log('欢迎词：' + result);

			let count = myLib.numberOfGreetings;
		
		* 还可以为外部模块添加属性和方法时，给出新增部分的类型描述。
			// 从 moduleA 导入 Foo，重命名为 Bar
			import { Foo as Bar } from 'moduleA';
			
			// 通过 declare 为 moduleA 声明类型
			declare module 'moduleA' {
			  interface Foo {
				// 为 Foo 增加一个属性 custom
				custom: {
				  prop1: string;
				}
			  }
			}

			// 虽然接口Foo改名为Bar，但是扩充类型时，还是扩充原始的接口Foo，因为同名 interface 会自动合并类型声明。

	
	# declare global
		* 可以使用 declare global {} 语法。为 JavaScript 引擎的原生对象添加属性和方法
				
			// 空导出语句，作用是强制编译器将这个脚本当作模块处理。这是因为declare global必须用在模块里面。
			export { };

			declare global {
				// 为 JavaScript 原生的String对象添加了toSmallString()方法
				// clare global 给出这个新增方法的类型描述。
				interface String {
					toSmallString(): string;
				}
			}

			String.prototype.toSmallString = (): string => {
				// 具体实现
				return '';
			};
		
		* declare global 只能扩充现有对象的类型描述，不能增加新的顶层类型。
	
	# declare enum
		* declare 关键字给出 enum 类型描述的例子如下
			declare enum E1 {
				A,
				B,
			}

			declare enum E2 {
				A = 0,
				B = 1,
			}

			declare const enum E3 {
				A,
				B,
			}

			declare const enum E4 {
				A = 0,
				B = 1,
			}
		
	# declare module 用于类型声明文件
		* 我们可以为每个模块脚本，定义一个.d.ts文件，把该脚本用到的类型定义都放在这个文件里面。
			* 方便的做法是为整个项目，定义一个大的.d.ts文件，在这个文件里面使用 declare module 定义每个模块脚本的类型。
		
		* node.d.ts 文件的部分示例如下
			// 模块
			declare module "url" {
			  // 模块中的类型
			  export interface Url {
				protocol?: string;
				hostname?: string;
				pathname?: string;
			  }

			  export function parse(
				urlStr: string,
				parseQueryString?,
				slashesDenoteHost?
			  ): Url;
			}

			declare module "path" {
			  export function normalize(p: string): string;
			  export function join(...paths: any[]): string;
			  export var sep: string;
			}
		
			
			// url 和 path 都是单独的模块脚本，但是它们的类型都定义在 node.d.ts 这个文件里面。

		* 使用时，自己的脚本使用三斜杠命令，加载这个类型声明文件。
			/// <reference path="node.d.ts"/>					// 导入 node.d.ts 中各个模块定义的类型
			import type { UserResponse } from "userApi"			// 从 url 模块（定义在 node.d.ts 中）中导入 UserResponse 类型

			// 如果没有上面这一行命令，自己的脚本使用外部模块时，就需要在脚本里面使用 declare 命令单独给出外部模块的类型。
