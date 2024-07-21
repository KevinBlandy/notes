-------------------
模块
-------------------
	# S6 模块之中，顶层的 this 指向 undefined，即不应该在顶层代码使用this。

-------------------
export 指令
-------------------
	# export 命令用于规定模块的对外接口
		* 一个模块，就是一个独立的 js 文件。
		* 必须通过 export 对模块内的 "变量" （函数、类也可以）进行暴露，其他文件才可以读取。
	
	# 在声明变量的时候暴露
		export const v1 = 1;
		export const foo = function(){
			console.log('foo');
		}
	
	# 也可以统一地进，以对象形式进行暴露
		const v1 = 1;
		const foo = function(){
			console.log('foo');
		}
		
		// 暴露
		export {
			v1, foo
		}
		
	# 不能重复暴露，即暴露的变量的名称不能一样
	
		* 可以使用 as 指令对变量进行重命名
		
		export {
			v1, foo, foo as bar
		}
	
	# export 命令是对外的接口，必须与模块内部的变量建立一一对应关系。
		export 1; // 直接暴露变量值，异常： SyntaxError: Unexpected token 'export' (at module.js:10:1)

		const val = 1;
		export val; // 直接暴露变量，异常：SyntaxError: Unexpected token 'export' (at module.js:7:1)


		* 实质是，需要在接口名与模块内部变量之间，建立了一一对应的关系。


	
	# export语 句输出的接口，与其对应的值是动态绑定关系
		* 即通过该接口，可以取到模块内部实时的值。
	
		export var foo = 'bar';
		setTimeout(() => foo = 'baz', 500);
		// 输出变量foo，值为bar，500 毫秒之后变成baz。
	

	# export 命令可以出现在模块的任何位置，只要处于模块顶层就可以。

	# export default 命令导出默认变量
		* export default 可以直接导出字面量值、变量、匿名/具名变量
			// 变量
			const val = 'foo';
			export default val;

			let val;
			export default val = 555;

			// 字面量值
			export default 10010;
			
			// 匿名函数
			export default () => {}

			// 具名函数
			export default function bar (){}

			// 对象
			export default  {name: 'hi'};
			
		* export default 一个模块只能写一个
		
			export default 1;
			export default 2; // SyntaxError: Identifier '.default' has already been declared (at module.js:11:8)
		
		* export default 可以和 export 一起使用
			export const val = 13;
			export default () => "h1";
		
		* 其实 export default 命令其实只是输出一个叫 做 default 的变量，所以它后面不能跟变量声明语句。

			// 变量
			const val = 'foo';
			export default val; // 将变量 val 的值赋给变量default。
		

----------------
import 指令
----------------
	# 过 import 命令加载模块。
	
	# 通过 {} 指定要从模块中加载那些变量

		import { foo, v1, bar } from "./module.js";

		* {} 中的变量名称，必须和 export 中的一致。
		* 可以使用 as 关键字对导入的变量进行重命名

			import { foo as bar, v1 } from "./module.js";
	
	# import 命令输入的变量都是只读的，不允许修改
		import { foo as bar, v1 } from "./module.js";
		bar = null; // TypeError: Assignment to constant variable.
	
		* 但是修改其本身的属性是可以的，和 const 一样（但是，建议别改）。
	
	# from 的模块，可以是相对路径也可以是绝对路径

		* 如果不带有路径，只是一个模块名，那么必须有配置文件，告诉 JavaScript 引擎该模块的位置。
			import { myMethod } from 'util';
		
	# import 命令是编译阶段执行的，在代码运行之前。
		* 所以， import 命令具有提升效果，会提升到整个模块的头部，首先执行。

			foo();		// 不会异常
			import { foo } from 'my_module';

	
		* 由于 import 是静态执行，所以不能使用表达式和变量，这些只有在运行时才能得到结果的语法结构。
	
	# import 语句会执行所加载的模块
		* 也就是说，如果你如果不需要导入任何资源，只是想执行模块，可以如下

			// 仅仅是执行 loadash，但是不从这个模块中加载任何变量
			import 'lodash'; 
		
		* 多次重复执行同一句 import 语句，只会执行一次，而不会执行多次。
	
			import { foo } from 'my_module';
			import { bar } from 'my_module';

			// 等同于
			import { foo, bar } from 'my_module';
		
	
	# 加载整个模块
		* 用星号（*）指定一个对象，所有导出的值都加载在这个对象上面。
			
			// 通过 as 取别名为 mod
			import * as mod from "./module.js";
			console.log(mod);  // Module {Symbol(Symbol.toStringTag): 'Module'}
			// 通过 mod 引用模块导出的变量
			mod.foo(); // foo
		
		* import * 可以通过 default 关键字访问默认导出
			import * as f from './foo'
			console.log(f.default);
		
		* 模块的对象，也是不允许修改的
			import * as mod from "./module.js";
			mod.bar = '123'; // TypeError: Cannot add property bar, object is not extensible
	
	
	# import default 导出
		* 对于 default 导出，可以使用任意名称接受，且不需要 {} 大括号
			import myModule from "./module.js";
		
		* 也可以同时 import default 导出和命名导出
			import  func, {val} from "./module.js";
		

------------------------------
export 与 import 的复合写法
------------------------------
	# export和import语句可以结合在一起，写成一行。
		// 一行的表达式
		export { foo, bar } from 'my_module';

		// 可以简单理解为
		import { foo, bar } from 'my_module';
		export { foo, bar };

		* 需要注意的是，写成一行以后，foo 和 bar 实际上并没有被导入当前模块，只是相当于对外转发了这两个接口，导致当前模块不能直接使用 foo 和 bar。
	
	# 可以对 import 的模块进行改名后 export
		
		// 接口改名
		export { foo as myFoo } from 'my_module';

		// 整体导出
		export * from 'my_module';
	
	# 具名接口改为默认接口
		// 把 es6 导入后，以 default 形式暴露出去
		export { es6 as default } from './someModule';

		// 等同于
		import { es6 } from './someModule';
		export default es6;
	
	# 默认接口改为具名接口
		// 导入 default 后改名为 es6 暴露出去
		export { default as es6 } from './someModule';
	
	# 导出所有未具名接口
		// 导入 mod 中的所有导出，为 ns 变量后导出
		export * as ns from "mod";

		// 等同于
		import * as ns from "mod";
		export {ns};

------------------------------
模块的继承 
------------------------------
	# 模块之间也可以继承。
		// 当前文件：circleplus.js

		// 导入 circle 中所有的导出，然后再次导出
		export * from 'circle';
		// 声明自己的导出
		export var e = 2.71828182846;
		export default function(x) {
		  return Math.exp(x);
		}
	
	
	# 如果当前模块导出了和 '父' 模块相同的变量，则会覆盖父级的导出。
		// md1
		export const v1 = 1;
		export const v2 = 2;
		export const v3 = 3;
		export default  "Hi";

		// md2
		export * from '/js/md1.js';
		export const v1 = -1;  // 覆盖了 md1 中的 v1 变量

		// index.js
		import  {v1, v2, v3} from '/js/md2.js';
		console.log(v1, v2, v3) // -1 2 3
	
	# export * from 不会继承父模块的 default 导出
		
		// index.js
		import  defVal, {v1, v2, v3} from '/js/md2.js';
		console.log(defVal)  // SyntaxError: The requested module '/js/md2.js' does not provide an export named 'default' (at index.js:1:9)
		
		* 可以在子模块中先导入，再导出
			// md2
			export * from '/js/md1.js';
			
			import defaultVal from '/js/md1.js'; //导入父模块的默认导出
			export default defaultVal;	// 再次默认导出
		
			// index.js
			import  defVal, {v1, v2, v3} from '/js/md2.js';
			console.log(defVal)  // Hi

------------------------------
import()
------------------------------
	# 运行时 import
		* import 命令会被 JavaScript 引擎静态分析，先于模块内的其他语句执行（import 命令叫做“连接” binding 其实更合适）。
		* 所以，下面的代码会报错。
			// 报错
			if (x === 2) {
			  // 引擎处理import语句是在编译时，这时不会去分析或执行if语句，所以import语句放在if代码块之中毫无意义，因此会报句法错误，而不是执行时错误。
			  // 也就是说，import和export命令只能在模块的顶层，不能在代码块之中（比如，在if代码块之中，或在函数之中）。
			  import MyModual from './myModual';
			}
	
	# ES2020提案 引入 import() 函数，支持动态加载模块。
		
		import(specifier)
			* import()返回一个 Promise 对象。
			* 参数
				specifier
					* 要加载的模块的位置。
			
			* import 命令能够接受什么参数，import() 函数就能接受什么参数，两者区别主要是后者为动态加载。
		

		* import()函数可以用在任何地方，不仅仅是模块，非模块的脚本也可以使用。
		* 由于 import() 返回 Promise 对象，可以使用 then，更推荐使用 await

			const mod = await import('./module.js')
			
			// 房屋具名导出的变量
			console.log(mod.val);
			
			// 房屋默认的变量
			console.log(mod.default);
		
-------------------------
import.meta
-------------------------
	# ES2020 为 import 命令添加了一个元属性import.meta，返回当前模块的元信息。
		
		* import.meta 只能在模块内部使用，如果在模块外部使用会报错。
		* import.meta 属性是一个对象，该对象的各种属性就是当前运行的脚本的元信息。
		* 具体包含哪些属性，标准没有规定，由各个运行环境自行决定。一般来说，import.meta 至少会有下面两个属性。
			
			import.meta.url
				* 返回当前模块的 URL 路径
				* 例来说，当前模块主文件的路径是 https://foo.com/main.js import.meta.url 就返回这个路径。
			
			import.meta.scriptElement
				* 是浏览器特有的元属性，返回加载模块的那个 <script> 元素，相当于 document.currentScript 属性。
			
		

-------------------
浏览器中的模块加载
-------------------
	# <script> 标签可以设置两个属性，可以设置异步加载方式（默认为同步、阻塞加载执行）。

		defer
			* 等到整个页面在内存中正常渲染结束（DOM 结构完全生成，以及其他脚本执行完成），才会执行。
			* 渲染完再执行。
			* 如果有多个 defer 脚本，会按照它们在页面出现的顺序加载。

		async
			* 一旦下载完，渲染引擎就会中断渲染，执行这个脚本以后，再继续渲染。
			* 下载完就执行
			* 多个 async 脚本不能保证加载顺序的（不知道谁先下载完毕）。
		
		
		* 渲染引擎遇到异步属性后，就会开始下载外部脚本，但不会等它下载和执行，而是直接执行后面的命令。
	
	# 浏览器加载 ES6 模块，要加入type="module"属性。

		<script type="module" src="./foo.js"></script>

		* type="module"，是异步加载，不会造成堵塞浏览器，即等到整个页面渲染完，再执行模块脚本。
		* 等同于打开了 <script> 标签的 defer 属性。

			<script type="module" src="./foo.js"></script>
			// 等于
			<script type="module" src="./foo.js" defer></script>
		
		* 如果使用了 async 属性，<script type="module"> 就不会按照在页面出现的顺序执行，而是只要该模块加载完成，就执行该模块。
	
		* 也可以执行嵌入式模块代码
			<script type="module">
			  import utils from "./utils.js";

			  // 其他代码
			</script>
		
			

