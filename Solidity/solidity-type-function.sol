--------------------------
function
--------------------------
	# 函数类型的表示

		function name(<parameter types>) <visibility> <stateMutability> [returns (<return types>) 

		
		visibility

			private
				* 只能内部调用，派生合约不能调用

			internal
				* 只能内部调用，或者派生合约调用
				* 函数类型定义（比如类型声明或变量声明时）的时候，internal 是默认的，可以省略。
					function ()  x ; // 声明变量，省略 internal|external
			
			external
				* 只能外部调用，当前合约调用需要用 this （外部调用的形式）

			public
				* 内部、外部都能调用
			
			* 不允许在 public 或 external 函数的参数或返回值中使用 internal 类型

				* 函数是 public 或 external，参数和返回值都必须是可 ABI 编码的类型（external type）。
				* 内部类型（如 mapping、storage 引用、internal function）只能用于 internal 或 private 函数中。
				* 内部类型（internal type）”，这些类型不能跨合约边界使用，因为 ABI（Application Binary Interface）无法编码或解码它们。
				* 典型的 internal types 包括：mapping、struct、function internal、storage、某些复杂动态类型（如 bytes storage）、含有 internal 成员的 struct

		stateMutability
			pure
				* 纯函数，不能读写区块链状态（不能访问 state 变量、block、msg 等）。
				* 不消耗 gas。

			view
				* 视图函数，允许读取但不能修改合约状态（不能写入状态变量）。
				* 不消耗 gas。

			payable
				* 允许函数接收以太（msg.value > 0）。
				* 不标注 payable 的函数如果被发送以太会失败（revert）。


		[returns (<return types>)]
			* 返回值类型，如果无返回值，则删除整个 return 语句
	
	# 函数本身也是一个类型
		* 可以作为变量、函数参数和函数返回值
		* 函数类型的变量必须先要初始化后才能调用，否则异常
		* 函数类型（指针）只有两类

			function (...) internal		// 内部函数指针，只能在当前合约或继承链中使用，不能 ABI 编码
			function (...) external		// 指向一个合约外部函数（带地址信息），可以 ABI 编码


	# 函数的类型转换
		
		* 函数 A 隐式转换为函数 B 需要满足的条件：
			
			* 参数类型相同
			* 返回类型相同
			* internal/external 相同
			* A 的状态比 B 更加严格
				
				* pure 函数可以转换为 view 和 non-payable 函数
				* view 函数可以转换为 non-payable 函数
				* payable 函数可以转换为 non-payable 函数

	
	# 函数成员
		
		address 
			* 函数的合约地址
		
		selector 
			* 函数的 ABI Selector

		
	
	# 调用方式
		* 内部调用：在编译期直接跳转，编译器直接生成内部 JUMP 指令（不通过 ABI 编码），msg.sender 不变，读写当前合约状态。
		* 外部调用：通过消息调用（Message Call），即发送 calldata 到合约地址，msg.sender 变为调用者，触发 fallback 时也可执行其他逻辑。


	


