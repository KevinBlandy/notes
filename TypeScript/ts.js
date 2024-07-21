--------------------
TS
--------------------
	# JS 的超集，完全兼容任何 Javascript 代码，相当于 JavaScript 语言的 “语法糖”
		* TypeScript 语言是跨平台，TypeScript 程序经过编译后可以在任意的浏览器、JavaScript宿主环境和操作系统上运行。
	
	# 在线学习
		* https://www.typescriptlang.org/zh/
		* https://www.patrickzhong.com/TypeScript/
		* https://wangdoc.com/typescript
		
	# 在线运行环境
		* https://www.typescriptlang.org/zh/play/
	
	
	# 安装 TS 编译器
		npm install -g typescript

		* 安装 OK 后查看 TS 编译器版本号

		tsc --version
		Version 5.5.3
	
	

	# 注释
		* 和 JS 一样，但是多了一个区域注释

			//#region 区域描述
			function foo (){

			}
			//#region

		* #region 是固定的，“区域描述” 用于描述该折叠区域
	

	# 变量
		* TypeScript 规定，变量只有赋值后才能使用，否则就会报错。
		
			let val:number
			console.log(val) // 在赋值前使用了变量“val”
	
--------------------
编译器
--------------------
	* 查看帮助信息
		tsc -h
		tsc --all	// 查看完整的信息
	
	* 编译脚本
		tsc app.ts
			* 当前目录下，生成一个 app.js 文件，即编译后的 JS 文件。
			* 可以一次性编译多个 TS 文件（空格分割），都会在当前目录下生成对应的 JS 文件。
	
		
		--outFile
			* 指定输出文件，可以把多个 TypeScript 脚本编译成一个 JavaScript 文件。
				tsc file1.ts file2.ts --outFile app.js
			
		--outDir
			* 指定编译后 JS 的输出目录
		
		--target
			* 指定编译后的 JavaScript 版本。建议使用 es2015，或者更新版本。
				tsc --target es2015 app.ts
		--noEmitOnError
			* 在编译错误的时候，停止生成 JS 文件。
			* 默认情况下 TS 就算是遇到了编译错误，仍然还是会生成 JS 文件。
		
		--noEmit
			* 只检查类型是否正确，不生成 JavaScript 文件。

		--noImplicitAny
			* 如果 TS 不能推断出变量类型就会报错（只要推断出为 any 类型，就报错）。
			* 除了一种情况，即声明变量，但不赋值也不指定类型，不会报错
				var x; // 不报错
				let y; // 不报错
		
