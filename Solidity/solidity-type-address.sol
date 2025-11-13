-----------------------------------
Address
-----------------------------------
	# 地址类型
		* 一个 20 字节的值（以太坊地址的大小）
	
		* address 普通地址类型
			
			* 不应该向普通的 address 发送以太币，例如因为它可能是一个不支持接受以太币的智能合约
		
		* address payable 地址类型
			
			* 与 address 相同，但具有额外的成员 transfer 和 send
			* 是一个可以发送以太币的地址
	
	# 类型转换
		
		* address payable 隐式转换为 address
		
		* address 显示转换为 address payable
			
			address payable myAddress = payable(<address>);
			
			* 只有 address 以及合约类型可以通过显式转换 payable() 转换为 address payable 类型
			* 对于合约，只有在合约可以接收以太币时才允许此转换，即合约要么具有 receive，要么具有可支付的回退函数（fallback () external payable）。
			
			* 注意 payable(0) 是有效的，并且是此规则的例外。

		* uint160\整数字面量\bytes20\合约类型 显式转换为 address
			
			// uint160
			uint160 uintValue = 0x1234567890ABCDEF1234567890ABCDEF12345678;
			address myAddress = address(value);
			
			// 整数字面量，十进制和十六进制都可以
			address myAddress = address(0x1234567890123456789012345678901234567890);
			
			// bytes20
			bytes20 bytesValue = hex"1234567890123456789012345678901234567890";
			address myAddress = address(bytesValue);
			
			// 合约类型
			SimpleContract simpleContract = new SimpleContract();
			address myAddress = address(simpleContract);
			
			* 如果使用更大字节大小的类型转换为 address，则 address 会被截断。
				
				// uint256 原始值，32字节
				uint b = 0x111122223333444455556666777788889999AAAABBBBCCCCDDDDEEEEFFFFCCCC
				
				address(uint160(bytes20(b)))	// 0x111122223333444455556666777788889999aAaa
				address(uint160(uint256(b)))	// 0x777788889999AaAAbBbbCcccddDdeeeEfFFfCcCc
	
	# 运算符
		* 支持相等、不相等和大小判断
	
	
	# 成员
		
		balance (uint256)
			* 查询地址的余额
			* 合约可以转换为 address 类型，可以使用 address(this).balance 查询当前合约的余额。
		
		transfer(uint256 amount)
			* 向可支付地址发送以太币（以 wei 为单位），限制了 gas 是 2300。
			* 如果当前合约的余额不足，或者以太币转账被接收账户拒绝，则 transfer 函数会 revert。
			* transfer 函数在失败时会回退。
		
		send(uint256 amount) returns (bool)
			* transfer 对应的低级函数，限制了 gas 是 2300。
			* 如果执行失败，当前合约不会以异常停止，只是 send 将返回 false。
			* 注意，如果调用栈深度达到 1024，则转账失败（这总是可以被调用者强制），如果接收者耗尽 gas 也会失败。
			* 为了安全地转账以太币，必须始终检查 send 的返回值。
		
		(bool success, bytes memory returnData) call(bytes memory)
			* 调用目标合约，没有 gas 限制，还会转发剩余 Gas。
			* 支持使用 value 和 gas modifier。
			
		(bool success, bytes memory returnData) delegatecall(bytes memory)
			* 调用目标合约。
			* 仅使用给定地址的代码，所有其他方面（存储、余额等）都来自当前合约。
			* 其目的是使用存储在另一个合约中的库代码。 用户必须确保两个合约中的存储布局适合使用 delegatecall（变量类型、顺序，和变量名称无关）。
			* 支持使用 gas modifier。
			
		(bool success, bytes memory returnData) staticcall(bytes memory)
			* 调用目标合约。
			* 基本上与 call 相同，但如果被调用的函数以任何方式修改状态，则会回退。
			* 支持使用 gas modifier。

		code (bytes memory)
			* 获取 EVM 字节码作为 bytes memory，这可能是空的。
			
		codehash (bytes32)
			* 获取该代码的 Keccak-256 哈希（作为 bytes32）。
			* 该属性比使用 keccak256(addr.code) 更便宜。
			
			* 如果与地址关联的账户为空或不存在（即没有代码、零余额和零 nonce，如 EIP-161 所定义），则 addr.codehash 的输出可能为 0。 
			* 如果账户没有代码但有非零余额或 nonce，则 addr.codehash 将输出空数据的 Keccak-256 哈希（即 keccak256("")，其值等于 c5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470）。


	# 地址字面量
		* 通过地址校验和测试的十六进制字面量（如：0x00Ff75705c140f92C29F81CD5906C2b499999999）是 address 类型
		* 注意，可能需要区分大小写
		* 长度在 39 到 41 位之间且未通过校验和测试的十六进制字面量会产生错误，可以在前面添加（对于整数类型）或在后面添加零（对于 bytesNN 类型）以消除错误。
		
			address addr = 0x00Ff75705c140f92C29F81CD5906C2b499999999;

		
		
	
	
	