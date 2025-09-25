------------------------------------------
模块
------------------------------------------
	
	# 全局导入

		import "filename";

		* filename 中的所有全局成员，都会被导入到当前的全局作用域中（可能会导致污染）
	
	# 别名导入
		
		import * as foo from "filename";

			* 导入 filename	中的所有成员到 foo 对象
			* 通过 foo.[symbol] 来访问 filename 中的成员
		
		import "filename" as foo;
			
			* 同上，便捷语法
		
	
		import {symbolA as foo, symbolB} from "filename";
			
			* 只局部性地导入 filename 中的部分成员（symbolA、symbolB）
			* 如果和当前作用域存在命名冲突，则可以通过 as 关键字设置别名（symbolA 别名为 foo）
		
	
	# 导入的路径
		
		* 导入路径并不直接指向主机文件系统中的文件，而是编译器维护一个内部数据库（简称 虚拟文件系统 或 VFS）。
		* 其中每个源单元被分配一个唯一的 源单元名称，这是一个不透明且无结构的标识符。 
		* 在 import 语句中指定的导入路径被转换为源单元名称，并用于在该数据库中查找相应的源单元。




		

		

		

