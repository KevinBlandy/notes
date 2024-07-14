----------------------
TS 模块
----------------------
	# 任何包含 import 或 export 语句的文件，就是一个模块（module）。
		* 如果不包含 export 语句，就是一个全局的脚本文件。
	
	# TS 模块除了支持所有 ES 模块的语法，特别之处在于允许输出和输入类型。
		// test.ts 导出一个 type 是 bool 类型
		export type Bool = true | false
		
		// 导入 ts 中的 type
		import { Bool } from './test.ts'
		const val: Bool = true
	
	# import type 语句
		* import 在一条语句中，可以同时 import 类型（TS）和正常接口（JS）。
			// ----test.ts
			// 导出类型
			export type Bool = true|false
			// 导出正常数据
			export const Port = 1024
			
			// ---- 同时导入类型和接口
			import { Bool, Port } from './test.ts'

		* 这样很不利于区分类型和正常接口，容易造成混淆，TS 引入了两个解决方法。


			1. 在 import 语句输入的类型前面加上 type 关键字。
				
				import { type Bool, Port } from './test.ts'
			
			2. 使用 import type 语句来只导入类型，不用来输入正常接口。
				
				// 导入类型
				import type { Bool } from './test.ts'
				// 导入接口
				import { Port } from './test.ts'

		
	# 导出类型
		type Bool = true | false
		interface Foo {}
		enum Color {}

		export type  {
			Bool,
			Foo,
			Color
		}
		
		// 下面这种方式也可以
		export {
			type Bool,
			type Foo,
			type Color
		}
				

	# 导入类型
		
		import type { Bool, Color, Foo} from './test.ts'
		
		// 也可以使用 import * 语法
		import type * as Types from './test.ts'
		let x: Types.Bool = true;

	# importsNotUsedAsValues 编译设置
		* TS 提供了 importsNotUsedAsValues 编译设置项，有三个可能的值。
			（1）remove		: 这是默认值，自动删除输入类型的 import 语句。
			（2）preserve	:保留输入类型的 import 语句。
			（3）error		:保留输入类型的 import 语句（与preserve相同），但是必须写成 import type 的形式，否则报错。
		
	
	# 模块定位
		* 算法，即用来确定 import 语句和 export 语句里面的模块文件位置。

			// 相对模块
			import { TypeA } from './a';

			// 非相对模块
			import * as $ from "jquery";
		
		* 编译参数 moduleResolution ，用来指定具体使用哪一种定位算法。常用的算法有两种：Classic 和 Node。

		* 相对模块，路径以 '/'、'./'、'../' 开头的模块

			import Entry from "./components/Entry";
			import { DefaultHeaders } from "../constants/http";
			import "/mod";

		* 非相对模块指的是不带有路径信息的模块

			import * as $ from "jquery";
			import { Component } from "@angular/core";
		
			// 非相对模块的定位，是由 baseUrl 属性或模块映射而确定的，通常用于加载外部模块。
	
	# 路径映射
		* TS 允许开发者在 tsconfig.json 文件里面，手动指定脚本模块的路径。

		baseUrl
			* 手动指定脚本模块的基准目录。
				{
				  "compilerOptions": {
					"baseUrl": "."  // 表示基准目录就是tsconfig.json所在的目录。
				  }
				}
		
		paths
			* 指定非相对路径的模块与实际脚本的映射。

				{
				  "compilerOptions": {
					"baseUrl": ".",
					"paths": {
						// 加载模块 jquery 时，实际加载的脚本是 node_modules/jquery/dist/jquery
						// 它的位置要根据 baseUrl 字段计算得到。
					  "jquery": ["node_modules/jquery/dist/jquery"]
					}
				  }
				}
		
		rootDirs
			* 指定模块定位时必须查找的其他目录。

				{
				  "compilerOptions": {
					  // 指定了模块定位时，需要查找的不同的国际化目录。
					"rootDirs": ["src/zh", "src/de", "src/#{locale}"]
				  }
				}
							
		





