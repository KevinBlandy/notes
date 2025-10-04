---------------------
Contract
---------------------
	# 合约类型
		* 每个合约都有自己的类型，可以隐式地将合约转换为父合约（多态）。
		
	
	# 合约数据层面（例如赋值、传参、存储）中，它的底层表示与 address 是相同的。
	
		* 合约可以显式地转换为 address 类型和从 address 类型转换。
		* 只有当合约类型具有接收或可支付的回退函数时，才能显式转换为 address payable 类型。
	
			MyContract c = ...;
			payable(c);					// 具有可支付功能的合约，可以直接转换为 payable address
			payable(address(c));		// 没有可支付功能的合约，需要先转换为 address
		
		* 合约类型在 EVM 中的底层表示 = address，因此，它也可以作为 ABI 中的地址字段使用。
		* 地址转换为合约类型
			
			address addr = ...;
			MyContract c = MyContract(addr); // address 必须是与 MyContract 类型匹配的地址。
	

	# 成员
		* 即合约的外部函数，包括任何标记为 public 的状态变量。
	
	
	# 使用 type(C) 访问有关合约的类型信息
	