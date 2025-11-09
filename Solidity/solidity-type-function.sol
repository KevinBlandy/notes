--------------------------
function
--------------------------
	# 函数类型的表示

		function name(<parameter types>) <visibility> <stateMutability> [returns (<return types>) 

		parameter
			* 参数的名称可以省略，值仍然存在于栈上，只是说不能通过名称访问
				function demo(uint, uint foo) public pure  returns (uint){
					return foo;
				}

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
				* 典型的 internal types 包括：mapping、function internal、storage、某些复杂动态类型（如 bytes storage）、含有 internal 成员的 struct
			
			* 此限制不适用于库函数，因为它们具有不同的内部 ABI。

		stateMutability
			pure
				* 纯函数，不能读写区块链状态（不能访问 state 变量、block、msg 等）。
				* 可以读取 msg.data 和 msg.sig。
				* 不消耗 gas。

			view
				* 视图函数，允许读取但不能修改合约状态（不能写入状态变量）。
				* 不消耗 gas。

			payable
				* 允许函数接收以太（msg.value > 0）。
				* 不标注 payable 的函数如果被发送以太会失败（revert）。


		[returns (<return types>)]
			* 返回值类型，如果无返回值，则删除整个 return 语句
			* 返回参数，也可以声明变量名称，这样的话返回值就有初始化值了，可以省略 return 语句
			* 如果要声明 return 语句，那么必须要 return 和返回值参数一样的数量的值
			* return 返回的值也支持隐式转换
				function foo()external  pure returns(bytes16 ) {
					return "??";
				}
					
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


	# 合约外部的函数，即自由函数
		* 函数可以定义在合约的外部，始终具有隐式 internal 可见性（不能声明可见性）。
		* 其代码会被包含在所有调用它们的合约中，类似于内部库函数的行为。

			function test ()  pure returns(string memory s){
				s = "hello";
			}

		* 自由函数始终在合约上下文中执行。它们仍可调用其他合约、向其发送以太币，并终止调用它们的合约等。
		* 与合约内部定义的函数主要区别在于：自由函数无法直接访问变量 this、storage 变量及其作用域外的函数。

------------------------
函数调用
------------------------
    # 内部函数调用
        * 只有同一个合约实例的函数可以被内部调用
        * 直接声明函数调用即可，这些调用会被转换为 EVM 内部的简单跳转
        * 要避免过度递归，每个内部函数调用至少使用一个栈槽、最多可用的栈槽只有 1024 个

    
    # 外部函数调用

        * 使用 this.<func>(<args>); 或者是 <instance>.<func>(<args>); 语法
        * 外部调用不是普通的跳转，而是 Message Call，被调用的合约本身抛出异常或耗尽 gas，函数调用也会导致异常。
        * 注意在构造函数中不能使用 this，因为这个时候合约还未创建完毕
        * 合约调用其他合约，并不会创建额外的交易
        * 在调用其他合约的时候，可以指定特殊的 modifier
            * gas: 不建议指定，未来可能会发生变化
            * value: 目标函数需要使用 payable 修饰，否则此选项不可用
        * EVM 默认认为调用一个地址不会出错，不管该地址是否指向一个实际存在的合约
            * 为了避免调用不存在的合约 SOL 会在调用前使用 extcodesize 操作码来检测目标地址是否是一个合约，如果不是就会抛出异常
            * 如果调用后需要解码返回的数据，SOL 就会跳过这个检查，例如使用 call、delegatecall、staticcall 等。
            * 要当心 “预编译合约”，SOL 的检查会认为它不存在，尽管它可以执行并返回数据。

    # 具名参数的调用 

        * 使用 {} 声明函数名称即可，顺序任意

        mapping(address => uint) balance;

        function set(address account, uint position)public {
            balance[account] = position;
        }
        function setBalanace()public {
            set({account: msg.sender, position: 10000});
        }
        

