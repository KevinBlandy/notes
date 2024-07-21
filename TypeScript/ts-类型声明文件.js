-----------------------
类型声明文件
-----------------------
	# d.ts 文件是一个独立的文件，定义了某个模块中的所有类型。
	
		* 文件名一般为 [模块名].d.ts 的形式，其中的 d 表示 declaration（声明）。
	

	# 类型文件
		// 通过 export 直接输出类型就行
		export function getArrayLength(arr: any[]): number;
		export const maxInterval: 12;


		* 可以使用 'export =' 命令，输出对外接口
			
			// declare 声明模块
			declare module 'moment' {
			   // 定义类型
			  function moment(): any;

			  // 导出类型
			  export = moment;
			}
		
		* 也可以使用 export default 表示

			// 模块输出(应该是 Commonjs 规范)，类似于 ES 中的 export default
			module.exports = 3.142;

			// 类型输出文件
			// 写法一
			declare const pi: number;
			export default pi;

			// 写法二
			declare const pi: number;
			export= pi;
		
		* 使用类型声明文件

			// 类型文件：types.d.ts

			export interface Character {
			  catchphrase?: string;
			  name: string;
			}


			// 在 index.ts 脚本里面导入该文件声明的类型。

			// index.ts

			// 导入声明的 Character 类型
			import { Character } from "./types";
			
			// 用这个类型，声明变量
			export const character:Character = {
			  catchphrase: "Yee-haw!",
			  name: "Sandy Cheeks",
			};
	
	# 类型声明文件也可以包括在项目的 tsconfig.json 文件里
		* 这样的话，编译器打包项目时，会自动将类型声明文件加入编译，而不必在每个脚本里面加载类型声明文件。

			{
			  "compilerOptions": {},
			  "files": [
				"src/index.ts",
				  // moment 模块的声明文件
				"typings/moment.d.ts"
			  ]
			}

	# 外部类型声明文件
		* 三方库自带了类型声明文件。
			* 如果库的源码包含了 [vendor].d.ts 文件（库名称 vendor），那就表示该库声明了类型文件。
			* 使用的时候就可能需要单独加载它的类型文件。
		
		* 如果三方库没有自带，可以去社区寻找。
			https://github.com/DefinitelyTyped/DefinitelyTyped

			* 这些声明文件都会作为一个单独的库，发布到 npm 的 @types 名称空间之下。
			* 比如，jQuery 的类型声明文件就发布成@types/jquery这个库，使用时安装这个库就可以了。
				$ npm install @types/jquery --save-dev

				// @types/jquery 这个库就安装到项目的 node_modules/@types/jquery 目录，里面的 index.d.ts 文件就是 jQuery 的类型声明文件。
			
			* TypeScript 会自动加载node_modules/@types目录下的模块，但可以使用编译选项typeRoots改变这种行为。

				{
				  "compilerOptions": {
					// TypeScript 不再去node_modules/@types目录，而是去跟当前tsconfig.json同级的typings和vendor/types子目录，加载类型模块了。
					"typeRoots": ["./typings", "./vendor/types"]
				  }
				}

	# declare 
		* 类型声明文件非常适合使用 declare 语句来描述类型。
		* 类型声明文件里面，变量的类型描述必须使用 declare 命令，否则会报错，因为变量声明语句是值相关代码。

			declare let foo: string;
		
		* interface 类型有没有declare都可以，因为 interface 是完全的类型代码。
			interface Foo {} // 正确
			declare interface Foo {} // 正确
		
		* 类型声明文件里面，顶层可以使用 export 命令，也可以不用，除非使用者脚本会显式使用 export 命令输入类型。
						
			export interface Data {
				version: string;
			}

		* 一些例子
			//------------------------
			// moment 模块的类型声明
			declare module 'moment' {
			  // 输出 Moment 接口
			  export interface Moment {
				format(format:string): string;

				add(
				  amount: number,
				  unit: 'days' | 'months' | 'years'
				): Moment;

				subtract(
				  amount:number,
				  unit:'days' | 'months' | 'years'
				): Moment;
			  }

			  // 定义类型 moment
			  function moment(
				input?: string | Date
			  ): Moment;

			  // 以 default 形式输出
			  export default moment;
			}

			// ------------
			declare namespace D3 {
			  export interface Selectors {
				select: {
				  (selector: string): Selection;
				  (element: EventTarget): Selection;
				};
			  }

			  export interface Event {
				x: number;
				y: number;
			  }

			  // 继承
			  export interface Base extends Selectors {
				event: Event;
			  }
			}

			declare var d3: D3.Base;
	
	# 模块发布
		* 发布自己的模块的时候，可以在 package.json 文件里面添加一个types字段或typings字段，指明类型声明文件的位置。

			{
			  "name": "awesome",
			  "author": "Vandelay Industries",
			  "version": "1.0.0",
			  "main": "./lib/main.js",
				  // 类型文件的位置
			  "types": "./lib/main.d.ts"
			}
		
		* 如果类型声明文件名为index.d.ts，且在项目的根目录中，那就不需要在package.json里面注明了。

		* 有时，类型声明文件会单独发布成一个 npm 模块，这时用户就必须同时加载该模块。
	
	# 三斜杠命令
		* 如果类型声明文件的内容非常多，可以拆分成多个文件，然后入口文件使用三斜杠命令，加载其他拆分后的文件。
			// main.d.ts 类型文件中，载入其他的类型文件
			/// <reference path="./interfaces.d.ts" />
			/// <reference path="./functions.d.ts" />
		
		* 三斜杠命令也可以用于普通脚本加载类型声明文件。
		
		* 三斜杠命令主要包含三个参数，代表三种不同的命令。
			path
				* 告诉编译器在编译时需要包括的文件，常用来声明当前脚本依赖的类型文件。
				* 路径是可以相对路径，也可以是绝对路径
				* 文件必须存在，且不能指向当前文件
			
			types
				* 告诉编译器当前脚本依赖某个 DefinitelyTyped 类型库，通常安装在node_modules/@types目录。
				* 作用类似于import命令。

				* 这个命令只在自己手写类型声明文件（.d.ts文件）时，才有必要用到，也就是说，只应该用在.d.ts文件中
				* 普通的.ts脚本文件不需要写这个命令。如果是普通的.ts脚本，可以使用tsconfig.json文件的types属性指定依赖的类型库。
	
			lib
				* 允许脚本文件显式包含内置 lib 库，等同于在tsconfig.json文件里面使用lib属性指定 lib 库。
				* 这些库文件位于 TypeScript 安装目录的lib文件夹中，它们描述了 JavaScript 语言和引擎的标准 API。
				* 比如 lib="es2015 "就表示加载库文件 lib.es2015.d.ts。
